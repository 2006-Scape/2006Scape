package com.rs2.game.globalworldobjects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rs2.GameEngine;
import com.rs2.game.objects.Objects;
import com.rs2.game.players.Player;
import com.rs2.util.DoorData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doors {

    private static Doors singleton = null;

    private final List<Doors> doors = new ArrayList<>();

    private File doorFile;

    public static Doors getSingleton() {
        if (singleton == null) {
            singleton = new Doors(System.getProperty("user.dir") + "/data/doors.json");
        }
        return singleton;
    }

    private Doors(String file) {
        doorFile = new File(file);
    }

    private Doors(int door, int x, int y, int z, int face, int type, int open) {
        this.doorId = door;
        this.originalId = door;
        this.doorX = x;
        this.doorY = y;
        this.originalX = x;
        this.originalY = y;
        this.doorZ = z;
        this.originalFace = face;
        this.currentFace = face;
        this.type = type;
        this.open = open;
    }

    private Doors getDoor(int id, int x, int y, int z) {
        for (Doors d : doors) {
            if (d.doorId == id) {
                if (d.doorX == x && d.doorY == y && d.doorZ == z) {
                    return d;
                }
            }
        }
        return null;
    }

    public boolean handleDoor(Player player, int id, int x, int y, int z) {
        Doors d = getDoor(id, x, y, z);

        if (d == null) {
            //System.out.println("D: " + id + " null debug x: " + x + " y: " + y + ".");
            return DoubleDoors.getSingleton().handleDoor(player, id, x, y, z);
        }

        //todo: improvment: if player manage to get to door then open the door.
        if (player.distanceToPoint(x, y) > 1) {
            //System.out.println("Door (single): " + id + " not in distance debug at x: " + x + " y: " + y + ".");
            return false;
        }

        //Remove clipping for old door (gets added back in placeObject)
        //Region.removeClipping(x, y, z);

        int xAdjustment = 0, yAdjustment = 0;
        if (d.type == 0) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = -1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    yAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    yAdjustment = -1;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    yAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    yAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            }
        } else if (d.type == 9) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    xAdjustment = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    xAdjustment = 1;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    xAdjustment = -1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    xAdjustment = -1;
                }
            }
        }
        if (xAdjustment != 0 || yAdjustment != 0) {
            Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
            GameEngine.objectHandler.placeObject(o);
        }
        if (d.doorX == d.originalX && d.doorY == d.originalY) {
            d.doorX += xAdjustment;
            d.doorY += yAdjustment;
        } else {
            Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
            GameEngine.objectHandler.placeObject(o);
            d.doorX = d.originalX;
            d.doorY = d.originalY;
        }
        if (d.doorId == d.originalId) {
            if (d.open == 0) {
                d.doorId += 1;
            } else if (d.open == 1) {
                d.doorId -= 1;
            }
        } else if (d.doorId != d.originalId) {
            if (d.open == 0) {
                d.doorId -= 1;
            } else if (d.open == 1) {
                d.doorId += 1;
            }
        }
        GameEngine.objectHandler.placeObject(new Objects(d.doorId, d.doorX, d.doorY, d.doorZ, getNextFace(d), d.type, 0));
        return true;
    }

    private int getNextFace(Doors d) {
        int f = d.originalFace;
        if (d.type == 0) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 1;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 3;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace) {
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace) {
                    f = d.originalFace;
                }
            }
        } else if (d.type == 9) {
            if (d.open == 0) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 2;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 0;
                } else if (d.originalFace != d.currentFace) {
                    f = d.originalFace;
                }
            } else if (d.open == 1) {
                if (d.originalFace == 0 && d.currentFace == 0) {
                    f = 3;
                } else if (d.originalFace == 1 && d.currentFace == 1) {
                    f = 0;
                } else if (d.originalFace == 2 && d.currentFace == 2) {
                    f = 1;
                } else if (d.originalFace == 3 && d.currentFace == 3) {
                    f = 2;
                } else if (d.originalFace != d.currentFace) {
                    f = d.originalFace;
                }
            }
        }
        d.currentFace = f;
        return f;
    }

    public void load() {
        Gson gson = new Gson();
        //long start = System.currentTimeMillis();
        try {
            Type collectionType = new TypeToken<DoorData[]>() {
            }.getType();
            DoorData[] doorData = gson.fromJson(new FileReader(doorFile), collectionType);

            for (DoorData data : doorData) {
                for (DoorData.Location location : data.getLocations()) {
                    doors.add(new Doors(data.getId(), location.getX(), location.getY(), location.getHeight(), data.getFace(), data.getType(), alreadyOpen(data.getId()) ? 1 : 0));
                }
            }
            //singleton.writeJsonDump();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println("Loaded "+ doors.size() +" doors in "+ (System.currentTimeMillis() - start) +" ms.");
    }

    private void writeJsonDump() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new FileReader(doorFile))) {
            while (scanner.hasNextLine()) {
                processLine(scanner.nextLine());
            }
        }
    }

    protected void processLine(String line) {
        JSONArray array   = new JSONArray();
        Scanner   scanner = new Scanner(line);
        scanner.useDelimiter(" ");
        try {
            while (scanner.hasNextLine()) {
                int id     = Integer.parseInt(scanner.next());
                int x      = Integer.parseInt(scanner.next());
                int y      = Integer.parseInt(scanner.next());
                int face   = Integer.parseInt(scanner.next());
                int height = Integer.parseInt(scanner.next());
                int type   = Integer.parseInt(scanner.next());

                JSONObject object = new JSONObject();

                object.put("id", id);

                JSONArray  jsonArray = new JSONArray();
                JSONObject object1   = new JSONObject();
                object1.put("x", x);
                object1.put("y", y);
                object1.put("height", height);
                jsonArray.put(0, object1);
                object.put("location", jsonArray);

                object.put("face", face);
                object.put("type", type);

                array.put(object);
            }
        } finally {
            scanner.close();

            try {
                FileWriter fileWriter = new FileWriter("doors-dump.json");
                fileWriter.write(array.toString());

                System.out.println(array.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean alreadyOpen(int id) {
        for (int openDoor : OPEN_DOORS) {
            if (openDoor == id) {
                return true;
            }
        }
        return false;
    }

    private int doorId;
    private int originalId;
    private int doorX;
    private int doorY;
    private int originalX;
    private int originalY;
    private int doorZ;
    private int originalFace;
    private int currentFace;
    private int type;
    private int open;

    private static final int[] OPEN_DOORS = {
            1504, 1514, 1517, 1520, 1531,
            1534, 2033, 2035, 2037, 2998,
            3271, 4468, 4697, 6101, 6103,
            6105, 6107, 6109, 6111, 6113,
            6115, 6976, 6978, 8696, 8819,
            10261, 10263, 10265, 11708, 11710,
            11712, 11715, 11994, 12445, 13002,
    };

}