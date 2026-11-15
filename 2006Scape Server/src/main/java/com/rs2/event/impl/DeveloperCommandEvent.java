package com.rs2.event.impl;

/**
 * Represents a developer command event.
 */
public class DeveloperCommandEvent extends CommandEvent {

	public DeveloperCommandEvent(String name, String input) {
		super(name, input);
	}

	public DeveloperCommandEvent(String name, String input, String[] arguments) {
		super(name, input, arguments);
	}
}
