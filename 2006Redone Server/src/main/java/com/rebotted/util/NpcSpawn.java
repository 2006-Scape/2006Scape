package com.rebotted.util;

/**
 * @author SandroC
 */
public class NpcSpawn {
    private final int id;
    private final int x;
    private final int y;
    private final int height;
    private final int walk;
    private final int maxHit;
    private final int attack;
    private final int strength;

    public NpcSpawn(int id, int x, int y, int height, int walk, int maxHit, int attack, int strength) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.height = height;
        this.walk = walk;
        this.maxHit = maxHit;
        this.attack = attack;
        this.strength = strength;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int isWalk() {
        return walk;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getAttack() {
        return attack;
    }

    public int getStrength() {
        return strength;
    }
}
