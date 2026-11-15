package com.rs2.event.impl;

/**
 * Represents a moderator command event.
 */
public class ModeratorCommandEvent extends CommandEvent {

	public ModeratorCommandEvent(String name, String input) {
		super(name, input);
	}

	public ModeratorCommandEvent(String name, String input, String[] arguments) {
		super(name, input, arguments);
	}
}
