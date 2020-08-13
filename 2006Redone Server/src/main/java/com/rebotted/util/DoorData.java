package com.rebotted.util;

public class DoorData {
    private final int        id;
    private final Location[] locations;
    private final int        face;
    private final int        type;

    public DoorData(int id, Location[] locations, int face, int type) {
        this.id = id;
        this.locations = locations;
        this.face = face;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Location[] getLocations() {
        return locations;
    }

    public int getFace() {
        return face;
    }

    public int getType() {
        return type;
    }

    public static class Location {
        private int x;
        private int y;
        private int height;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getHeight() {
            return height;
        }
    }
}
