package com.rs2.game.content.skills.farming;

import com.rs2.game.players.Player;
import com.rs2.tick.Tick;

public class FarmingTask extends Tick {

	private Player player;

	public FarmingTask(Player player) {
		super(10);
		this.player = player;
	}

	@Override
	protected void execute() {
		Farming.processCalc(player);
	}
}