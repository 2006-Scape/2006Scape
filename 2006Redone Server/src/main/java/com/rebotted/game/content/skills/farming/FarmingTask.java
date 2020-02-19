package com.rebotted.game.content.skills.farming;

import com.rebotted.game.players.Player;
import com.rebotted.tick.Tick;

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