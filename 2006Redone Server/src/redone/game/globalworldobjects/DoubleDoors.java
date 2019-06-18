package redone.game.globalworldobjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import redone.Server;
import redone.game.objects.Objects;

/**
 * 
 * @author Killamess
 *
 */
public class DoubleDoors {
	
	private static DoubleDoors singleton = null;

	private List<DoubleDoors> doors = new ArrayList<DoubleDoors>();

	private File doorFile;
	
	public static DoubleDoors getSingleton() {
		if (singleton == null) {
			singleton = new DoubleDoors("./data/doubledoors.txt");
		}
		return singleton;
	}

	private DoubleDoors(String file){
		doorFile = new File(file);  
	}
	
	private DoubleDoors getDoor(int id, int x, int y, int z) {
		for (DoubleDoors d : doors) {
			if (d.doorId == id) {
				if (d.x == x && d.y == y && d.z == z) {
					return d;
				}
			}
		}
		return null;
	}
	
	public boolean handleDoor(int id, int x, int y, int z) {
		DoubleDoors doorClicked = getDoor(id, x, y, z);	
		
		if (doorClicked == null) {
			return true;
		}
		if (doorClicked.doorId > 12000) {
			return true; //nearly all of these are not opened
		}
		if (doorClicked.open == 0) { 
			if (doorClicked.originalFace == 0) {
				DoubleDoors lowerDoor = getDoor(id - 3, x, y -1, z);
				DoubleDoors upperDoor = getDoor(id + 3, x, y +1, z);
				if (lowerDoor != null) {
					changeLeftDoor(lowerDoor);
					changeRightDoor(doorClicked);
				} else if (upperDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(upperDoor);
				}
			} else if (doorClicked.originalFace == 1) {
				DoubleDoors westDoor = getDoor(id - 3, x -1, y, z);
				DoubleDoors eastDoor = getDoor(id + 3, x +1, y, z);
				if (westDoor != null) {
					changeLeftDoor(westDoor);
					changeRightDoor(doorClicked);
				} else if (eastDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(eastDoor);
				}
			} else if (doorClicked.originalFace == 2) {
				DoubleDoors lowerDoor = getDoor(id - 3, x, y +1, z);
				DoubleDoors upperDoor = getDoor(id + 3, x, y -1, z);
				if (lowerDoor != null) {
					changeLeftDoor(lowerDoor);
					changeRightDoor(doorClicked);
				} else if (upperDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(upperDoor);
				}
			} else if (doorClicked.originalFace == 3) {
				DoubleDoors westDoor = getDoor(id + 3, x -1, y, z);
				DoubleDoors eastDoor = getDoor(id - 3, x +1, y, z);
				if (westDoor != null) {
					changeLeftDoor(westDoor);
					changeRightDoor(doorClicked);
				} else if (eastDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(eastDoor);
				}
			}
		} else if (doorClicked.open == 1) { 
			if (doorClicked.originalFace == 0) {
				DoubleDoors westDoor = getDoor(id - 3, x -1, y, z);
				DoubleDoors upperDoor = getDoor(id + 3, x +1, y, z);
				if (westDoor != null) {
					changeLeftDoor(westDoor);
					changeRightDoor(doorClicked);
				} else if (upperDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(upperDoor);
				}
			} else if (doorClicked.originalFace == 1) {
				DoubleDoors northDoor = getDoor(id - 3, x, y + 1, z);
				DoubleDoors southDoor = getDoor(id + 3, x, y -1, z);
				if (northDoor != null) {
					changeLeftDoor(northDoor);
					changeRightDoor(doorClicked);
				} else if (southDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(southDoor);
				}
			} else if (doorClicked.originalFace == 2) {
				DoubleDoors westDoor = getDoor(id - 3, x -1, y, z);
				DoubleDoors eastDoor = getDoor(id + 3, x, y -1, z);
				if (westDoor != null) {
					changeLeftDoor(westDoor);
					changeRightDoor(doorClicked);
				} else if (eastDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(eastDoor);
				}
			} else if (doorClicked.originalFace == 3) {
				DoubleDoors northDoor = getDoor(id - 3, x, y + 1, z);
				DoubleDoors southDoor = getDoor(id + 3, x, y -1, z);
				if (northDoor != null) {
					changeLeftDoor(northDoor);
					changeRightDoor(doorClicked);
				} else if (southDoor != null) {
					changeLeftDoor(doorClicked);
					changeRightDoor(southDoor);
				}
			}	
		} 
		return true;
	}

	public void changeLeftDoor(DoubleDoors d) {
		int xAdjustment = 0, yAdjustment = 0;
		
		if (d.open == 0) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				xAdjustment = -1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				yAdjustment = 1;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				xAdjustment = +1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				yAdjustment = -1;
			}
		} else if (d.open == 1) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				yAdjustment = -1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				xAdjustment = -1;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				xAdjustment = -1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				xAdjustment = -1;
			}
		}
		if (xAdjustment != 0 || yAdjustment != 0) { 
			Server.objectHandler.placeObject(new Objects(-1, d.x, d.y, d.z, 0, 0, 0));
		}
		if (d.x == d.originalX && d.y == d.originalY) {
			d.x += xAdjustment;
			d.y += yAdjustment;
		} else { 
			Server.objectHandler.placeObject(new Objects(-1, d.x, d.y, d.z, 0, 0, 0));
			d.x = d.originalX;
			d.y = d.originalY;
		}
		if (d.doorId == d.originalId) {
			if (d.open == 0) {
				d.doorId += 1;
			} else if (d.open == 1) {
				d.doorId -= 1;
			}
		} else if (d.doorId != d.originalId) {
			if (d.open == 0) {
				d.doorId = d.originalId;
			} else if (d.open == 1) {
				d.doorId = d.originalId;
			}
		}
		Server.objectHandler.placeObject(new Objects(d.doorId, d.x, d.y, d.z, getNextLeftFace(d), 0, 0));
	}
	
	private int getNextLeftFace(DoubleDoors d) {
		int f = d.originalFace;

		if (d.open == 0) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				f = 3;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				f = 0;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				f = 1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				f = 0;
			} else if (d.originalFace != d.currentFace){
				f = d.originalFace;
			}
		} else if (d.open == 1) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				f = 1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				f = 2;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				f = 1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				f = 2;
			} else if (d.originalFace != d.currentFace){
				f = d.originalFace;
			}
		}
		d.currentFace = f;
		return f;
	}
	
	public void changeRightDoor(DoubleDoors d) {
		int xAdjustment = 0, yAdjustment = 0;
		
		if (d.open == 0) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				xAdjustment = -1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				yAdjustment = 1;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				xAdjustment = +1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				yAdjustment = -1;
			}
		} else if (d.open == 1) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				xAdjustment = 1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				xAdjustment = -1;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				yAdjustment = -1;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				xAdjustment = -1;
			}
		}
		if (xAdjustment != 0 || yAdjustment != 0) { 
			Server.objectHandler.placeObject(new Objects(-1, d.x, d.y, d.z, 0, 0, 0));
		}
		if (d.x == d.originalX && d.y == d.originalY) {
			d.x += xAdjustment;
			d.y += yAdjustment;
		} else { 
			Server.objectHandler.placeObject(new Objects(-1, d.x, d.y, d.z, 0, 0, 0));
			d.x = d.originalX;
			d.y = d.originalY;
		}
		if (d.doorId == d.originalId) {
			if (d.open == 0) {
				d.doorId += 1;
			} else if (d.open == 1) {
				d.doorId -= 1;
			}
		} else if (d.doorId != d.originalId) {
			if (d.open == 0) {
				d.doorId = d.originalId;
			} else if (d.open == 1) {
				d.doorId = d.originalId;
			}
		}
		Server.objectHandler.placeObject(new Objects(d.doorId, d.x, d.y, d.z, getNextRightFace(d), 0, 0));
	}
	
	private int getNextRightFace(DoubleDoors d) {
		int f = d.originalFace;

		if (d.open == 0) {
			if (d.originalFace == 0 && d.currentFace == 0) {
				f = 1;
			} else if (d.originalFace == 1 && d.currentFace == 1) {
				f = 2;
			} else if (d.originalFace == 2 && d.currentFace == 2) {
				f = 3;
			} else if (d.originalFace == 3 && d.currentFace == 3) {
				f = 2;
			} else if (d.originalFace != d.currentFace){
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
			} else if (d.originalFace != d.currentFace){
				f = d.originalFace;
			}
		}
		d.currentFace = f;
		return f;
	}
	
	private int doorId;
	private int originalId;
	private int open;
	private int x;
	private int y;
	private int z;
	private int originalX;
	private int originalY;
	private int currentFace;
	private int originalFace;
	
	public DoubleDoors(int id, int x, int y, int z, int f, int open) {
		this.doorId = id;
		this.originalId = id;
		this.open = open;
		this.x = x;
		this.originalX = x;
		this.y = y;
		this.z = z;
		this.originalY = y;
		this.currentFace = f;
		this.originalFace = f;
	}
	
	public boolean isOpenDoor(int id){
		for (int i = 0; i < openDoors.length; i++) {
			if (id == openDoors[i] || id + 3 == openDoors[i]) {
				return true;
			}
		}
		return false;
	}
	
	//Have not found any others yet. Maybe only 1 type of double 
	//doors exist to operate.
	private static int[] openDoors = {
		1520, 1517
	};
	
	public void load() {
		//long start = System.currentTimeMillis();
		try {
			singleton.processLineByLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//System.out.println("Loaded "+ doors.size() +" Double doors in "+ (System.currentTimeMillis() - start) +"ms.");
	}
	
	private final void processLineByLine() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(doorFile));
	    	try {
	    		while(scanner.hasNextLine()) {
	    			processLine(scanner.nextLine());
	    		}
	  	 } finally {
	    		scanner.close();
	  	 }
	}
	
	protected void processLine(String line){
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(" ");
		try {
			while(scanner.hasNextLine()) {
				int id = Integer.parseInt(scanner.next());
				int x = Integer.parseInt(scanner.next());
				int y = Integer.parseInt(scanner.next());
		    		int f = Integer.parseInt(scanner.next());
		    		int z = Integer.parseInt(scanner.next());
		    		doors.add(new DoubleDoors(id, x, y, z, f, isOpenDoor(id) ? 1 : 0));
			}
		} finally {
			scanner.close();
		}
	}
}