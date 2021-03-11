package com.rs2.game.content.skills.farming;

import com.rs2.game.players.Player;

public class Farming {

	public static void processCalc(Player p) {
		p.getAllotment().doCalculations();
		p.getBushes().doCalculations();
		p.getFlowers().doCalculations();
		p.getFruitTrees().doCalculations();
		p.getHerbs().doCalculations();
		p.getHops().doCalculations();
		p.getSpecialPlantOne().doCalculations();
		p.getSpecialPlantTwo().doCalculations();
		p.getTrees().doCalculations();
	}

	public static boolean prepareCrop(Player player, int item, int id, int x,
			int y) {
		// plant pot
		if (player.getSeedling().fillPotWithSoil(item, x, y)) {
			return true;
		}
		// // allotments
		if (player.getAllotment().curePlant(x, y, item)) {
			return true;
		}
		if (player.getAllotment().putCompost(x, y, item)) {
			return true;
		}
		if (player.getAllotment().clearPatch(x, y, item)) {
			return true;
		}
		if (item >= 3422 && item <= 3428 && id == 4090) {
			player.getItemAssistant().deleteItem(item, 1);
			player.getItemAssistant().addItem(item + 8, 1);
			player.startAnimation(832);
			player.getPacketSender()
					.sendMessage(
							"You put the olive oil on the fire, and turn it into sacred oil.");
			return true;
		}
		if (item <= 5340 && item > 5332) {
			if (player.getAllotment().waterPatch(x, y, item)) {
				return true;
			}
		}
		if (player.getAllotment().plantSeed(x, y, item)) {
			return true;
		}
		// flowers
		if (player.getFlowers().plantScareCrow(x, y, item)) {
			return true;
		}
		if (player.getFlowers().curePlant(x, y, item)) {
			return true;
		}
		if (player.getFlowers().putCompost(x, y, item)) {
			return true;
		}
		if (player.getFlowers().clearPatch(x, y, item)) {
			return true;
		}
		if (item <= 5340 && item > 5332) {
			if (player.getFlowers().waterPatch(x, y, item)) {
				return true;
			}
		}
		if (player.getFlowers().plantSeed(x, y, item)) {
			return true;
		}
		if (player.getCompost().handleItemOnObject(item, id, x, y)) {
			return true;
		}
		// herbs
		if (player.getHerbs().curePlant(x, y, item)) {
			return true;
		}
		if (player.getHerbs().putCompost(x, y, item)) {
			return true;
		}
		if (player.getHerbs().clearPatch(x, y, item)) {
			return true;
		}
		if (player.getHerbs().plantSeed(x, y, item)) {
			return true;
		}
		// hops
		if (player.getHops().curePlant(x, y, item)) {
			return true;
		}
		if (player.getHops().putCompost(x, y, item)) {
			return true;
		}
		if (player.getHops().clearPatch(x, y, item)) {
			return true;
		}
		if (item <= 5340 && item > 5332)
			if (player.getHops().waterPatch(x, y, item)) {
				return true;
			}
		if (player.getHops().plantSeed(x, y, item)) {
			return true;
		}
		// bushes
		if (player.getBushes().curePlant(x, y, item)) {
			return true;
		}
		if (player.getBushes().putCompost(x, y, item)) {
			return true;
		}

		if (player.getBushes().clearPatch(x, y, item)) {
			return true;
		}
		if (player.getBushes().plantSeed(x, y, item)) {
			return true;
		}
		// trees
		if (player.getTrees().pruneArea(x, y, item)) {
			return true;
		}
		if (player.getTrees().putCompost(x, y, item)) {
			return true;
		}
		if (player.getTrees().plantSapling(x, y, item)) {
			return true;
		}
		if (player.getTrees().clearPatch(x, y, item)) {
			return true;
		}
		// fruit trees
		if (player.getFruitTrees().pruneArea(x, y, item)) {
			return true;
		}
		if (player.getFruitTrees().putCompost(x, y, item)) {
			return true;
		}
		if (player.getFruitTrees().clearPatch(x, y, item)) {
			return true;
		}
		if (player.getFruitTrees().plantSapling(x, y, item)) {
			return true;
		}
		// special plant one
		if (player.getSpecialPlantOne().curePlant(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantOne().putCompost(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantOne().clearPatch(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantOne().plantSapling(x, y, item)) {
			return true;
		}
		// Special plant two
		if (player.getSpecialPlantTwo().curePlant(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantTwo().putCompost(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantTwo().clearPatch(x, y, item)) {
			return true;
		}
		if (player.getSpecialPlantTwo().plantSeeds(x, y, item)) {
			return true;
		}
		// player.sendMessage("Farming disabled - coming soon");
		return false;
	}

	public static boolean inspectObject(Player player, int x, int y) {
		// allotments
		if (player.getAllotment().inspect(x, y)) {
			return true;
		} // flowers
		if (player.getFlowers().inspect(x, y)) {
			return true;
		}
		// herbs
		if (player.getHerbs().inspect(x, y)) {
			return true;
		}
		// hops
		if (player.getHops().inspect(x, y)) {
			return true;
		}
		// bushes
		if (player.getBushes().inspect(x, y)) {
			return true;
		}
		// trees
		if (player.getTrees().inspect(x, y)) {
			return true;
		}
		// fruit trees
		if (player.getFruitTrees().inspect(x, y)) {
			return true;
		}
		// special plant one
		if (player.getSpecialPlantOne().inspect(x, y)) {
			return true;
		}
		// special plant two
		if (player.getSpecialPlantTwo().inspect(x, y)) {
			return true;
		}
		return false;
	}

	public static boolean guide(Player player, int x, int y) {
		// allotments
		if (player.getAllotment().guide(x, y)) {
			return true;
		} // flowers
		if (player.getFlowers().guide(x, y)) {
			return true;
		}
		// herbs
		if (player.getHerbs().guide(x, y)) {
			return true;
		}
		// hops
		if (player.getHops().guide(x, y)) {
			return true;
		}
		// bushes
		if (player.getBushes().guide(x, y)) {
			return true;
		}
		// trees
		if (player.getTrees().guide(x, y)) {
			return true;
		}
		// fruit trees
		if (player.getFruitTrees().guide(x, y)) {
			return true;
		}
		// special plant one
		if (player.getSpecialPlantOne().guide(x, y)) {
			return true;
		}
		// special plant two
		if (player.getSpecialPlantTwo().guide(x, y)) {
			return true;
		}
		return false;
	}

	public static boolean harvest(Player player, int x, int y) {
		// allotments

		if (player.getAllotment().harvest(x, y)) {
			return true;
		}
		// flowers
		if (player.getFlowers().harvest(x, y)) {
			return true;
		}
		// herbs
		if (player.getHerbs().harvest(x, y)) {
			return true;
		}
		// hops
		if (player.getHops().harvest(x, y)) {
			return true;
		}
		// bushes
		if (player.getBushes().harvestOrCheckHealth(x, y)) {
			return true;
		}
		// trees
		if (player.getTrees().checkHealth(x, y)) {
			return true;
		}
		if (player.getTrees().cut(x, y)) {
			return true;
		}
		// fruit trees
		if (player.getFruitTrees().harvestOrCheckHealth(x, y)) {
			return true;
		}
		// special plant one
		if (player.getSpecialPlantOne().harvestOrCheckHealth(x, y)) {
			return true;
		}
		// special plant two
		if (player.getSpecialPlantTwo().harvestOrCheckHealth(x, y)) {
			return true;
		}
		return false;
	}
}
