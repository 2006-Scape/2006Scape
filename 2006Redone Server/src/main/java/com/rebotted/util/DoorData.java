package com.rebotted.util;

public class DoorData {

    private int id;
    private Location[] locations;
    private int face;
    private int type;

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
