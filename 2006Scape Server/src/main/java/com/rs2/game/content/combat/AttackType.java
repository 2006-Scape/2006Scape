package com.rs2.game.content.combat;

public enum AttackType {
        MELEE(0),
        RANGE(1),
        MAGIC(2),
        FIRE_BREATH(3);

        private final int value;
        
        AttackType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }