package com.rebotted.util;

public class NpcData {
    private final int    id;
    private final String name;
    private final int    combat;
    private final int hitpoints;

    public NpcData(int id, String name, int combat, int hitpoints) {
        this.id = id;
        this.name = name;
        this.combat = combat;
        this.hitpoints = hitpoints;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCombat() {
        return combat;
    }

    public int getHitpoints() {
        return hitpoints;
    }
}
