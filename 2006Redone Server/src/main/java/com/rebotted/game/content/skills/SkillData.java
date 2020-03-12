package com.rebotted.game.content.skills;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Syndicate
 */

public enum SkillData {
	ATTACK(0, 6247, 6248, 6249, 4004, 4005, 4044, 4045),
	DEFENCE(1, 6253, 6254, 6255, 4008, 4009, 4056, 4057),
	STRENGTH(2, 6206, 6207, 6208, 4006, 4007, 4050, 4051),
	HITPOINTS(3, 6216, 6217, 6218, 4016, 4017, 4080, 4081),
	RANGED(4, 4443, 5453, 6114, 4010, 4011, 4062, 4063),
	PRAYER(5, 6242, 6243, 6244, 4012, 4013, 4068, 4069),
	MAGIC(6, 6211, 6212, 6213, 4014, 4015, 4074, 4075),
	COOKING(7, 6226, 6227, 6228, 4034, 4035, 4134, 4135),
	WOODCUTTING(8, 4272, 4273, 4274, 4038, 4039, 4146, 4147),
	FLETCHING(9, 6231, 6232, 6233, 4026, 4027, 4110, 4111),
	FISHING(10, 6258, 6259, 6260, 4032, 4033, 4128, 4129),
	FIREMAKING(11, 4282, 4283, 4284, 4036, 4037, 4140, 4141),
	CRAFTING(12, 6263, 6264, 6265, 4024, 4025, 4104, 4105),
	SMITHING(13, 6221, 6222, 6223, 4030, 4031, 4122, 4123),
	MINING(14, 4416, 4417, 4438, 4028, 4029, 4116, 4117),
	HERBLORE(15, 6237, 6238, 6239, 4020, 4021, 4092, 4093),
	AGILITY(16, 4277, 4278, 4279, 4018, 4019, 4086, 4087),
	THIEVING(17, 4261, 4263, 4264, 4022, 4023, 4098, 4099),
	SLAYER(18, 12122, 12123, 12124, 12166, 12167, 12171, 12172),
	FARMING(19, 12122, 12123, 12124, 13926, 13927, 13921, 13922),
	RUNECRAFTING(20, 4267, 4268, 4269, 4152, 4153, 4157, 4159);

	/**
	 * The identifier for this skill.
	 */
	private final int skill;
	
	/**
	 * The first frame id for this skill.
	 */
	private final int frame;
	
	/**
	 * The second frame id for this skill.
	 */
	private final int frame2;
	
	/**
	 * The third frame id for this skill.
	 */
	private final int frame3;
	
	/**
	 * The fourth frame id for this skill.
	 */
	private final int frame4;
	
	/**
	 * The fifth frame id for this skill.
	 */
	private final int frame5;
	
	/**
	 * The sixth frame id for this skill.
	 */
	private final int frame6;
	
	/**
	 * The seventh frame id for this skill.
	 */
	private final int frame7;

	/**
	 * Constructs a new {@link SkillData}.
	 * 
	 * @param skill
	 * @param frame
	 * @param frame2
	 * @param frame3
	 * @param frame4
	 * @param frame5
	 * @param frame6
	 * @param frame7
	 */
	SkillData(int skill, int frame, int frame2,
			int frame3, int frame4, int frame5, int frame6, int frame7) {
		this.skill = skill;
		this.frame = frame;
		this.frame2 = frame2;
		this.frame3 = frame3;
		this.frame4 = frame4;
		this.frame5 = frame5;
		this.frame6 = frame6;
		this.frame7 = frame7;
	}

	public final int getId() {
		return skill;
	}
	
	public final int getFrame1() {
		return frame;
	}
	
	public final int getFrame2() {
		return frame2;
	}
	
	public final int getFrame3() {
		return frame3;
	}
	
	public final int getFrame4() {
		return frame4;
	}
	
	public final int getFrame5() {
		return frame5;
	}
	
	public final int getFrame6() {
		return frame6;
	}
	
	public final int getFrame7() {
		return frame7;
	}
	
	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
	
	public static Optional<SkillData> getSkill(int identifier) {
		return Arrays.stream(values()).filter(s -> s.skill == identifier).findFirst();
	}
}