package com.rs2.game.content.skills.farming;

import com.rs2.event.CycleEvent;
import com.rs2.event.CycleEventContainer;
import com.rs2.game.players.Player;

public class FarmingTask extends CycleEventContainer {

	public FarmingTask(Player player) {
		super("farming task".hashCode(), player, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				Farming.processCalc(player);				
			}

			@Override
			public void stop() {
			}
			
		}, 10);
	}
}