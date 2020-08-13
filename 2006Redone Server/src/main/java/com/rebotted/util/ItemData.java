package com.rebotted.util;

public class ItemData {
    private final int    id;
    private final String name;
    private final String examine;
    private final Values[] values;
    private final Bonuses[] bonuses;

    public ItemData(int id, String name, String examine, Values[] values, Bonuses[] bonuses) {
        this.id = id;
        this.name = name;
        this.examine = examine;
        this.values = values;
        this.bonuses = bonuses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExamine() {
        return examine;
    }

    public Values getValues() {
        return values[0];
    }

    public Bonuses getBonuses() {
        return bonuses[0];
    }

    public static class Values {
        int shopValue;
        int lowAlch;
        int highAlch;

        public int getShopValue() {
            return shopValue;
        }

        public int getLowAlch() {
            return lowAlch;
        }

        public int getHighAlch() {
            return highAlch;
        }
    }

    public static class Bonuses {
        int attackStab;
        int attackSlash;
        int attackCrush;
        int attackMagic;
        int attackRange;
        int defenceStab;
        int defenceSlash;
        int defenceCrush;
        int defenceMagic;
        int defenceRange;
        int strengthBonus;
        int prayerBonus;

        public int getAttackStab() {
            return attackStab;
        }

        public int getAttackSlash() {
            return attackSlash;
        }

        public int getAttackCrush() {
            return attackCrush;
        }

        public int getAttackMagic() {
            return attackMagic;
        }

        public int getAttackRange() {
            return attackRange;
        }

        public int getDefenceStab() {
            return defenceStab;
        }

        public int getDefenceSlash() {
            return defenceSlash;
        }

        public int getDefenceCrush() {
            return defenceCrush;
        }

        public int getDefenceMagic() {
            return defenceMagic;
        }

        public int getDefenceRange() {
            return defenceRange;
        }

        public int getStrengthBonus() {
            return strengthBonus;
        }

        public int getPrayerBonus() {
            return prayerBonus;
        }

        public int[] getBonuses() {
            int[] bonuses = new int[12];

            bonuses[0] = this.getAttackStab();
            bonuses[1] = this.getAttackSlash();
            bonuses[2] = this.getAttackCrush();
            bonuses[3] = this.getAttackMagic();
            bonuses[4] = this.getAttackRange();
            bonuses[5] = this.getDefenceStab();
            bonuses[6] = this.getDefenceSlash();
            bonuses[7] = this.getDefenceCrush();
            bonuses[8] = this.getDefenceMagic();
            bonuses[9] = this.getDefenceRange();
            bonuses[10] = this.getStrengthBonus();
            bonuses[11] = this.getPrayerBonus();

            return bonuses;
        }
    }
}
