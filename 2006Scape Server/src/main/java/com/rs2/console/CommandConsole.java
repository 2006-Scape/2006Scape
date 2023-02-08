package com.rs2.console;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.rs2.Constants;
import com.rs2.console.commands.ListPlayers;
import com.rs2.console.commands.Stop;

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
		cmds.add(new ListPlayers());
		cmds.add(new Stop());

		scanner = new Scanner(System.in);
		cc = this;
		self = new Thread(cc);
		self.start();
	}

	@Override
	public void run() {
		System.out.println("Welcome to " + Constants.SERVER_NAME + ".");
		while (true) {
			System.out.print("> ");
			String input = null;
			try {
				input = scanner.nextLine();
				String[] splited = input.split("\\s+");
				if (splited[0].isEmpty()) {
					System.out.println("Command not recognized. Try 'help'.");
					continue;
				}
				if (splited[0].equalsIgnoreCase("help")) {
					for (CommandProcessor cmd : cmds) {
						System.out.println(cmd.help());
					}
					continue;
				}
				for (CommandProcessor cmd : cmds) {
					if (cmd.command(splited)) {
						break;
					}
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
