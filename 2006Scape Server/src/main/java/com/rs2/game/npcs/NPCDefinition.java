package com.rs2.game.npcs;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rs2.util.XStreamUtil;

public class NPCDefinition {
	
	private static HashMap<Integer, NPCDefinition> definitions = new HashMap<>();

	public static void init() throws IOException {
		Gson gson = new Gson();
		Type type = new TypeToken<List<NPCDefinition>>(){}.getType();
		List<NPCDefinition> defs;
		try (FileReader reader = new FileReader("data/cfg/npcDefinitions.json")) {
			defs = gson.fromJson(reader, type);
		}
		for (NPCDefinition def : defs) {
			definitions.put(def.getId(), def);
		}
	}

	public static NPCDefinition forId(int id) {
		NPCDefinition d = definitions.get(id);
		if (d == null) {
			d = produceDefinition(id);
		}
		return d;
	}

	private int id;
	private String name, examine;
	private int respawn = 0, combat = 0, hitpoints = 1, maxHit = 0, size = 1, attackSpeed = 4000, attackAnim = 422, defenceAnim = 404, deathAnim = 2304, attackBonus = 20, defenceMelee = 20, defenceRange = 20, defenceMage = 20;

	private boolean attackable = false;
	private boolean aggressive = false;
	private boolean retreats = false;
	private boolean poisonous = false;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getExamine() {
		return examine;
	}

	public int getRespawn() {
		return respawn;
	}

	public int getCombat() {
		return combat;
	}

	public int getHitpoints() {
		return hitpoints;
	}

	public int getMaxHit() {
		return maxHit;
	}

	public int getSize() {
		return size;
	}

	public boolean isAggressive() {
		return aggressive;
	}

	public boolean retreats() {
		return retreats;
	}

	public boolean isPoisonous() {
		return poisonous;
	}

	public static NPCDefinition produceDefinition(int id) {
		NPCDefinition def = new NPCDefinition();
		def.id = id;
		def.name = "NPC #" + def.id;
		def.examine = "It's an NPC.";
		return def;
	}

	public int getAttackSpeed() {
		return attackSpeed;
	}

	public int getAttackAnimation() {
		return attackAnim;
	}

	public int getDefenceAnimation() {
		return defenceAnim;
	}

	public int getDeathAnimation() {
		return deathAnim;
	}

	public boolean isAttackable() {
		return attackable;
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public int getDefenceRange() {
		return defenceRange;
	}

	public int getDefenceMelee() {
		return defenceMelee;
	}

	public int getDefenceMage() {
		return defenceMage;
	}

}
