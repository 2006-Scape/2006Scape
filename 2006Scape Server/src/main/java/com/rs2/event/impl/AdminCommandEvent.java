package com.rs2.event.impl;

/**
 * Represents an administrator command event.
 */
public class AdminCommandEvent extends CommandEvent {

	public AdminCommandEvent(String name, String input) {
		super(name, input);
	}

	public AdminCommandEvent(String name, String input, String[] arguments) {
		super(name, input, arguments);
	}
}
