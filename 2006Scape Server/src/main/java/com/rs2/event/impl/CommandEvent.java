package com.rs2.event.impl;

import com.rs2.event.Event;

/**
 * Represents a command event.
 * 
 * @author Vult-R
 */
public final class CommandEvent implements Event {

	/**
	 * The name for this command.
	 */
	private final String name;
	
	/**
	 * The input for this command.
	 */
	private final String input;
	
	/**
	 * Creates the command.
	 */
	public CommandEvent(String name, String input) {
		this.name = name;
		this.input = input;
	}	
	
	/**
	 * Gets the name of this command.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the input for this command.
	 */
	public String getInput() {
		return input;
	}
}
