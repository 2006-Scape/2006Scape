package com.rebotted.game.content.combat.magic;

public enum SpellTeleport {

    VARROCK(25, 4140, 35, 3213, 3422, 0, new int[][]{{563, 1}, {554, 1}, {556, 3}}, "modern"),
    LUMBRIDGE(31,4143, 41, 3222, 3218, 0, new int[][]{{563, 1}, {557, 1}, {556, 3}}, "modern"),
    FALADOR(37, 4146, 48, 2965, 3378, 0, new int[][]{{563, 1}, {555, 1}, {556, 3}}, "modern"),
    CAMELOT(45, 4150, 55.5, 2757, 3479, 0, new int[][]{{563, 1}, {556, 5}}, "modern"),
    ARDOUGNE(51, 6004, 61, 2662, 3304, 0, new int[][]{{563, 2}, {555, 2}}, "modern"),
    WATCHTOWER(58, 6005, 68, 2547, 3112, 1, new int[][]{{563, 2}, {557, 2}}, "modern"),
    TROLLHEIM(61, 29031, 68, 2893, 3679, 0, new int[][]{{563, 2}, {554, 2}}, "modern"),
    APE_ATOLL(64, 72038, 74, 2798, 2798, 1, new int[][]{{563, 2}, {554, 2}, {555, 2}, {1963, 1}}, "modern"),
    PADDEWWA(54, 50235, 64, 3098, 9884, 0, new int[][] {{563, 2}, {554, 1}, {556, 1}}, "ancient"),
    SENNTISTEN(60, 50245, 70, 3321, 3335, 0, new int[][] {{566, 1}, {563, 2}}, "ancient"),
    KHARYLL(66, 50253, 76, 3493, 3472, 0, new int[][] {{565, 1}, {563, 2}}, "ancient"),
    LASSAR(72, 51005, 82, 3006, 3471, 0, new int[][] {{563, 2}, {555, 4}}, "ancient"),
    DAREEYAK(78, 51013, 88, 3161, 3671, 0, new int[][] {{563, 2}, {554, 3}, {556, 2}}, "ancient"),
    CARRALLANGAR(84, 51023, 94, 3157, 3669, 0, new int[][] {{566, 2}, {563, 2}}, "ancient"),
    ANNAKARL(90, 51031, 100, 3286, 3884, 0, new int[][] {{565, 2}, {563, 2}}, "ancient"),
    GHORROCK(96, 51039, 106, 2977, 3873, 0, new int[][] {{563, 2}, {555, 8}}, "ancient");

    int requiredLevel, buttonId, destX, destY, destZ;
    double experienceGained;
    int[][] requiredRunes;
    String type;

    SpellTeleport(int requiredLevel, int buttonId, double experienceGained, int destX, int destY, int destZ, int[][] requiredRunes, String type) {
        this.requiredLevel = requiredLevel;
        this.buttonId = buttonId;
        this.experienceGained = experienceGained;
        this.destX = destX;
        this.destY = destY;
        this.destZ = destZ;
        this.requiredRunes = requiredRunes;
        this.type = type;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getButtonId() {
        return buttonId;
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }

    public int getDestZ(){
        return destZ;
    }

    public int[][] getRequiredRunes() {
        return requiredRunes;
    }

    public double getExperienceGained() {
        return experienceGained;
    }

    public String getType() {
        return type;
    }

    public static SpellTeleport forButtonId(int id) {
        for (SpellTeleport t : values()) {
            if (t != null) {
                if (t.getButtonId() == id) {
                    return t;
                }
            }
        }
        return null;
    }


}
