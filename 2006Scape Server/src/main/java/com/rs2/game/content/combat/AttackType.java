package com.rs2.game.content.combat;

public enum AttackType {
        MELEE(0),
        RANGE(1),
        MAGE(2);

        private final int value;
        
        AttackType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }