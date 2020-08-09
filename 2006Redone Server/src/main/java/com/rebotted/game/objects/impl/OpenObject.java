package com.rebotted.game.objects.impl;

import com.rebotted.GameEngine;
import com.rebotted.game.objects.ObjectDefaults;
import com.rebotted.game.objects.Objects;
import com.rebotted.game.players.Player;
import com.rebotted.world.clip.Region;

/**
 * Feb 17, 2018 : 6:44:26 AM
 * OpenObject.java
 *
 * @author Andrew (Mr Extremez)
 */
public class OpenObject {

    /**
     * Object old
     * Object new
     */
    private static final int[][] OBJECT_DATA = {
            { 375, 378 }, { 6910, 378 }, { 3193, 3194 },
            { 2693, 3194 }, { 388, 389 }, { 350, 351 },
            { 348, 349 }, { 5622, 5623 }, { 2612, 2613 },
            { 352, 353 }, { 398, 399 }, { 376, 379 }
    };

    public static void interactObject(Player player, int objectType) {
        for (final int[] element : OBJECT_DATA) {
            if (objectType == element[0]) {
                GameEngine.objectHandler.placeObject(new Objects(element[1], player.objectX, player.objectY, player.getH(), ObjectDefaults.getObjectFace(player, objectType), 10, 0));
                Region.addObject(element[1], player.objectX, player.objectY, player.getH(), ObjectDefaults.getObjectFace(player, objectType), 10, false);
                player.startAnimation(832);
            } else if (objectType == element[1]) {
                GameEngine.objectHandler.placeObject(new Objects(element[0], player.objectX, player.objectY, player.getH(), ObjectDefaults.getObjectFace(player, objectType), 10, 0));
                Region.addObject(element[0], player.objectX, player.objectY, player.getH(), ObjectDefaults.getObjectFace(player, objectType), 10, false);
                player.startAnimation(832);
            }
        }
    }
}
