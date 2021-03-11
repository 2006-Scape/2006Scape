package com.rs2.game.dialogues;

/**
 * ChatEmotes.java
 * @author Andrew (Mr Extremez)
 */

public enum ChatEmotes {
	
		HAPPY_JOYFUL(588),
		CALM_TALK1(589),
		CALM_TALK2(590),
		DEFAULT(591),
		EVIL1(592),
		EVIL2(593),
		EVIL3(594),
		ANNOYED(595),
		DISTRESSED(596),
		DISTRESSED2(597),
		ALMOST_CRYING(598),
		BOWS_HEAD_SAD(598),
		DRUNK_LEFT(600),
		DRUNK_RIGHT(601),
		NOT_INTERESTED(602),
		SLEEPY(603),
		PLAIN_EVIL(604),
		LAUGH1(605),
		LAUGH2(606),
		LAUGH3(607),
		LAUGH4(608),
		EVIL_LAUGH(609),
		SAD(610),
		MORE_SAD(611),
		ON_ONE_HAND(612),
		NEARLY_CRYING(613),
		ANGRY1(614),
		ANGRY2(615),
		ANGRY3(616),
		ANGRY4(617);
		
		private ChatEmotes(int animId) {
			this.animId = animId;
		}
		
		private int animId;
		
		static int getAnimId(ChatEmotes ce) {
			return ce.animId;
		}
	}