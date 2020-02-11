package com.rebotted.console;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.rebotted.GameConstants;

/**
 * 
 * @author RS-Emulators
 *
 */
public class CommandConsole implements Runnable {

	private static CommandConsole cc;
	private Thread self;
	private Scanner scanner;
	// Lazy, not intending to add runtime class loading.
	private ArrayList<CommandProcessor> cmds = new ArrayList<CommandProcessor>();

	private CommandConsole() {
		scanner = new Scanner(System.in);
		cc = this;
		self = new Thread(cc);
		self.start();
	}

	@Override
	public void run() {
		System.out.println("Welcome to " + GameConstants.SERVER_NAME + ".");
		while (true) {
			System.out.print("> ");
			String input = null;
			try {
				input = scanner.nextLine();
				String[] splited = input.split("\\s+");
				if (splited.length >= 1) {
					if (splited[0].equalsIgnoreCase("help")) {
						for (CommandProcessor cmd : cmds) {
							System.out.println(cmd.help());
						}
					}
					for (CommandProcessor cmd : cmds) {
						if (cmd.command(splited)) {
							break;
						}
					}
					System.out.println("Command not recognized. Try 'help'.");
				}
			} catch (NoSuchElementException | NullPointerException e) {
			}
		}
	}

	public static CommandConsole getInstance() {
		if (cc == null) {
			cc = new CommandConsole();
		}
		return cc;
	}

}
