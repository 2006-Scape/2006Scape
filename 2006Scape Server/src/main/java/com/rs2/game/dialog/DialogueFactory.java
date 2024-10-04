package com.rs2.game.dialog;

import com.rs2.Constants;
import com.rs2.game.npcs.NPCDefinition;
import com.rs2.game.npcs.Npc;
import com.rs2.game.players.Player;
import com.rs2.util.LoggerUtils;
import com.rs2.util.Misc;
import org.apollo.cache.def.NpcDefinition;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a factory class that contains important functions for building dialogues.
 * 
 * @author Vult-R <https://github.com/Vult-R>
 */
public final class DialogueFactory {
	
	/**
	 * The single logger for this class.
	 */
	private static final Logger logger = LoggerUtils.getLogger(DialogueFactory.class);

	/**
	 * The queue of dialogues in this factory.
	 */
	private final Queue<Chainable> chain = new ArrayDeque<>();	

	/**
	 * The maximum length of a single line of dialogue.
	 */
	private static final int MAXIMUM_LENGTH = 100;

	/**
	 * The player who owns this factory.
	 */
	private final Player player;
	
	/**
	 * The flag that denotes dialogue is active.
	 */
	private boolean active;

	/**
	 * The next action in the dialogue chain.
	 */
	private Optional<Runnable> nextAction = Optional.empty();

	/**
	 * Creates a new {@link DialogueFactory}.
	 *
	 * @param player
	 * 		The player who owns this factory.
	 */
	public DialogueFactory(Player player) {
		this.player = player;
	}

	/**
	 * Sends a player a dialogue.
	 *
	 * @param dialogue
	 *            The dialogue to sent.
	 */
	public final DialogueFactory sendDialogue(Dialogue dialogue) {
		player.setDialogue(Optional.of(dialogue));
		dialogue.sendDialogues(this);
		return this;
	}

	/**
	 * Sets an {@code action} so this action can be executed after dialogues are done.
	 *
	 * @param action
	 * 		The action to set.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory onAction(Runnable action) {
		setNextAction(Optional.of(action));
		return this;
	}

	/**
	 * Accepts the next dialogue in the chain.
	 *
	 * @return The instance of this factory.
	 */
	public DialogueFactory onNext() {
		if (getChain().peek() != null) {
			Chainable chain = getChain().poll();

			chain.accept(this);
		} else {
			player.getPacketSender().closeAllWindows();
		}
		return this;
	}

	/**
	 * Executes an {@code option} for a {@code player}.
	 *
	 * @param type
	 * 		The type of option.
	 *
	 * 	@param option
	 * 		The option to execute.
	 */
	public final void executeOption(int type, Optional<OptionDialogue> option) {
		if (option != null) {
			option.ifPresent($it -> $it.getActions().get(type).run());
			execute();
		}
	}

	/**
	 * Clears the current dialogue {@code chain}.
	 *
	 * @return The instance of this factory.
	 */
	public void clear() {
		chain.clear();
		nextAction = Optional.empty();
		player.setDialogue(Optional.empty());
		player.setOptionDialogue(Optional.empty());
		setActive(false);
	}

	/**
	 * Appends a {@code chain} to this factory.
	 *
	 * @return The instance of this factory.
	 */
	private final DialogueFactory append(Chainable chain) {
		this.chain.add(chain);
		return this;
	}

	/**
	 * Gets the current chain.
	 * 
	 * @return The queue of dialogues.
	 */
	public final Queue<Chainable> getChain() {
		return chain;
	}

	/**
	 * Retrieves the next dialogue in the chain and executes it.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory execute() {
		// check to see if there are anymore dialogues.
		if (getChain().peek() != null) {

			// there is so, grab the next dialogue.
			Chainable entry = getChain().poll();

			// is this an option dialogue?
			if (entry instanceof OptionDialogue) {
				OptionDialogue option = (OptionDialogue) entry;
				player.setOptionDialogue(Optional.of(option));
			}
			setActive(true);
			// whatever dialogue it is, accept it.
			entry.accept(this);
		} else {
			// there are no dialogues in this chain.

				// is there an action?
				if (getNextAction().isPresent()) {
					// there is so, execute it.
					getNextAction().ifPresent($it -> $it.run());
					// we just used this action so empty it so it can't be used again.
					setNextAction(Optional.empty());
					return this;
			}
				setActive(false);
			// there are no more dialogues, so clear the screen.
			player.getPacketSender().closeAllWindows();
			player.talkingNpc = -1;
			player.nextChat = 0;
		}
		return this;
	}
	
	/**
	 * Retrieves the next dialogue in the chain and executes it.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory executeNoClose() {
		// check to see if there are anymore dialogues.
		if (getChain().peek() != null) {

			// there is so, grab the next dialogue.
			Chainable entry = getChain().poll();

			// is this an option dialogue?
			if (entry instanceof OptionDialogue) {
				OptionDialogue option = (OptionDialogue) entry;
				player.setOptionDialogue(Optional.of(option));
			}
			setActive(true);
			// whatever dialogue it is, accept it.
			entry.accept(this);
		} else {
			// there are no dialogues in this chain.

				// is there an action?
				if (getNextAction().isPresent()) {
					// there is so, execute it.
					getNextAction().ifPresent($it -> $it.run());
					// we just used this action so empty it so it can't be used again.
					setNextAction(Optional.empty());
					return this;
			}
				setActive(false);
				player.talkingNpc = -1;
				player.nextChat = 0;
		}
		return this;
	}

	/**
	 * Appends keywords to an existing dialogue text.
	 *
	 * @param line
	 *            The line to check for a keyword.
	 */
	private final String appendKeywords(String line) {
		// If the line contains #username, replace with a formatted player name.
		if (line.contains("<username>")) {
			line = line.replaceAll("<username>", Misc.formatPlayerName((player.playerName)));
		}
		if (line.contains("<servername>")) {
			line = line.replaceAll("<servername>", Constants.SERVER_NAME);
		}

		return line;
	}

	/**
	 * Appends a {@link PlayerDialogue} to the current dialogue chain.
	 *
	 * @param lines
	 * 		The dialogue of the player talking.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendPlayerChat(String... lines) {
		return append(new PlayerDialogue(lines));
	}

	/**
	 * Appends a {@link PlayerDialogue} to the current dialogue chain.
	 *
	 * @param lines
	 * 		The dialogue of the player talking.
	 *
	 * @param expression
	 * 		The expression of this dialogue.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendPlayerChat(Expression expression, String... lines) {
		return append(new PlayerDialogue(expression, lines));
	}

	/**
	 * Sends a dialogue with a player talking.
	 *
	 * @param dialogue
	 * 		The player dialogue.
	 *
	 * 	@return The instance of this factory.
	 */
	final DialogueFactory sendPlayerChat(PlayerDialogue dialogue) {
		Expression expression = dialogue.getExpression();
		String[] lines = dialogue.getLines();

		validateLength(lines);
		switch (lines.length) {
			case 1:
				player.getPacketSender().sendDialogueAnimation(969, expression.getId());
				player.getPacketSender().sendString(Misc.formatPlayerName(player.playerName), 970);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 971);
				player.getPacketSender().sendPlayerDialogueHead(969);
				player.getPacketSender().sendChatInterface(968);
				break;

			case 2:
				player.getPacketSender().sendDialogueAnimation(974, expression.getId());
				player.getPacketSender().sendString(Misc.formatPlayerName(player.playerName), 975);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 976);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 977);
				player.getPacketSender().sendPlayerDialogueHead(974);
				player.getPacketSender().sendChatInterface(973);
				break;

			case 3:
				player.getPacketSender().sendDialogueAnimation(980, expression.getId());
				player.getPacketSender().sendString(Misc.formatPlayerName(player.playerName), 981);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 982);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 983);
				player.getPacketSender().sendString(appendKeywords(lines[2]), 984);
				player.getPacketSender().sendPlayerDialogueHead(980);
				player.getPacketSender().sendChatInterface(979);
				break;

			case 4:
				player.getPacketSender().sendDialogueAnimation(987, expression.getId());
				player.getPacketSender().sendString(Misc.formatPlayerName(player.playerName), 988);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 989);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 990);
				player.getPacketSender().sendString(appendKeywords(lines[2]), 991);
				player.getPacketSender().sendString(appendKeywords(lines[3]), 992);
				player.getPacketSender().sendPlayerDialogueHead(987);
				player.getPacketSender().sendChatInterface(986);
				break;

			default:
				logger.log(Level.SEVERE, String.format("Invalid player dialogue line length: %s", lines.length));
				break;
		}
		return this;
	}

	/**
	 * Appends an npc dialogue.
	 *
	 * @param lines
	 * 		The text of this dialogue.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendNpcChat(String... lines) {
		return append(new NpcDialogue(lines));
	}

	/**
	 * Appends an {@link NpcDialogue} to the current dialogue chain.
	 *
	 * @param expression
	 * 		The expression of this npc.
	 *
	 * @param lines
	 * 		The text of this dialogue.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendNpcChat(Expression expression, String... lines) {
		if (player.getNpcs() != null) { //TODO
			int id = player.npcType;
			return append(new NpcDialogue(id, expression, lines));
		}
		return append(new NpcDialogue(expression, lines));
	}

	/**
	 * Appends an {@link NpcDialogue} to the current dialogue chain.
	 *
	 * @param id
	 * 		The id of this npc.
	 *
	 * @param lines
	 * 		The text of this dialogue.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendNpcChat(int id, String... lines) {
		return append(new NpcDialogue(id, Expression.DEFAULT, lines));
	}

	/**
	 * Appends an {@link NpcDialogue} to the current dialogue chain.
	 *
	 * @param id
	 * 		The id of this npc.
	 *
	 * @param expression
	 * 		The expression of this npc.
	 *
	 * @param lines
	 * 		The text of this dialogue.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendNpcChat(int id, Expression expression, String... lines) {
		return append(new NpcDialogue(id, expression, lines));
	}

	/**
	 * Sends a dialogue with a npc talking.
	 *
	 * @param dialogue
	 * 		The dialogue.
	 *
	 * @return The instance of this factory.
	 */
	final DialogueFactory sendNpcChat(NpcDialogue dialogue) {
		Expression expression = dialogue.getExpression();
		String[] lines = dialogue.getLines();
		validateLength(lines);

		final NPCDefinition mob = NPCDefinition.forId(player.npcType);
		
		if (mob == null) {
			return this;
		}
		
		switch (lines.length) {
			case 1:
				player.getPacketSender().sendDialogueAnimation(4883, expression.getId());
				player.getPacketSender().sendString(mob.getName(), 4884);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 4885);
				player.getPacketSender().sendNPCDialogueHead(player.npcType, 4883);
				player.getPacketSender().sendChatInterface(4882);
				break;

			case 2:
				player.getPacketSender().sendDialogueAnimation(4888, expression.getId());
				player.getPacketSender().sendString(mob.getName(), 4889);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 4890);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 4891);
				player.getPacketSender().sendNPCDialogueHead(player.npcType, 4888);
				player.getPacketSender().sendChatInterface(4887);
				break;

			case 3:
				player.getPacketSender().sendDialogueAnimation(4894, expression.getId());
				player.getPacketSender().sendString(mob.getName(), 4895);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 4896);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 4897);
				player.getPacketSender().sendString(appendKeywords(lines[2]), 4898);
				player.getPacketSender().sendNPCDialogueHead(player.npcType, 4894);
				player.getPacketSender().sendChatInterface(4893);
				break;

			case 4:
				player.getPacketSender().sendDialogueAnimation(4901, expression.getId());
				player.getPacketSender().sendString(mob.getName(), 4902);
				player.getPacketSender().sendString(appendKeywords(lines[0]), 4903);
				player.getPacketSender().sendString(appendKeywords(lines[1]), 4904);
				player.getPacketSender().sendString(appendKeywords(lines[2]), 4905);
				player.getPacketSender().sendString(appendKeywords(lines[3]), 4906);
				player.getPacketSender().sendNPCDialogueHead(player.npcType, 4901);
				player.getPacketSender().sendChatInterface(4900);
				break;

			default:
				logger.log(Level.SEVERE,
						String.format("Invalid npc dialogue line length: %s", lines.length));
				break;
		}
		return this;
	}

	/**
	 * Appends the {@link OptionDialogue} onto the current dialogue chain.
	 *
	 * @param option1
	 * 		The text for the first option.
	 *
	 * @param action1
	 * 		The action for the first action.
	 *
	 * @param option2
	 * 		The text for the second option.
	 *
	 * @param action2
	 * 		The action for the second action.
	 */
	public final DialogueFactory sendOption(String option1, Runnable action1, String option2, Runnable action2) {
		return append(new OptionDialogue(option1, action1, option2, action2));
	}

	/**
	 * Appends the {@link OptionDialogue} onto the current dialogue chain.
	 *
	 * @param option1
	 * 		The text for the first option.
	 *
	 * @param action1
	 * 		The action for the first action.
	 *
	 * @param option2
	 * 		The text for the second option.
	 *
	 * @param action2
	 * 		The action for the second action.
	 *
	 * @param option3
	 * 		The text for the third option.
	 *
	 * @param action3
	 * 		The action for the third action.
	 */
	public final DialogueFactory sendOption(String option1, Runnable action1, String option2, Runnable action2, String option3, Runnable action3) {
		return append(new OptionDialogue(option1, action1, option2, action2, option3, action3));
	}

	/**
	 * Appends the {@link OptionDialogue} onto the current dialogue chain.
	 *
	 * @param option1
	 * 		The text for the first option.
	 *
	 * @param action1
	 * 		The action for the first action.
	 *
	 * @param option2
	 * 		The text for the second option.
	 *
	 * @param action2
	 * 		The action for the second action.
	 *
	 * @param option3
	 * 		The text for the third option.
	 *
	 * @param action3
	 * 		The action for the third action.
	 *
	 * @param option4
	 * 		The text for the four option.
	 *
	 * @param action4
	 * 		The action for the four action.
	 */
	public final DialogueFactory sendOption(String option1, Runnable action1, String option2, Runnable action2, String option3, Runnable action3, String option4, Runnable action4) {
		return append(new OptionDialogue(option1, action1, option2, action2, option3, action3, option4, action4));
	}

	/**
	 * Appends the {@link OptionDialogue} onto the current dialogue chain.
	 *
	 * @param option1
	 * 		The text for the first option.
	 *
	 * @param action1
	 * 		The action for the first action.
	 *
	 * @param option2
	 * 		The text for the second option.
	 *
	 * @param action2
	 * 		The action for the second action.
	 *
	 * @param option3
	 * 		The text for the third option.
	 *
	 * @param action3
	 * 		The action for the third action.
	 *
	 * @param option4
	 * 		The text for the four option.
	 *
	 * @param action4
	 * 		The action for the four action.
	 *
	 * @param option5
	 * 		The text for the fifth option.
	 *
	 * @param action5
	 * 		The action for the fifth action.
	 */
	public final DialogueFactory sendOption(String option1, Runnable action1, String option2, Runnable action2, String option3, Runnable action3, String option4, Runnable action4, String option5, Runnable action5) {
		return append(new OptionDialogue(option1, action1, option2, action2, option3, action3, option4, action4, option5, action5));
	}

	/**
	 * Sends a dialogue with options.
	 *
	 * @param dialogue
	 * 		The dialogue.
	 *
	 * @return The instance of this factory.
	 */
	final DialogueFactory sendOption(OptionDialogue dialogue) {
		String[] options = dialogue.getLines();
		validateLength(options);
		switch (options.length) {
			case 2:
				player.getPacketSender().sendString("Select an Option", 2460);
				player.getPacketSender().sendString(options[0], 2461);
				player.getPacketSender().sendString(options[1], 2462);
				player.getPacketSender().sendChatInterface(2459);
				return this;

			case 3:
				player.getPacketSender().sendString("Select an Option", 2470);
				player.getPacketSender().sendString(options[0], 2471);
				player.getPacketSender().sendString(options[1], 2472);
				player.getPacketSender().sendString(options[2], 2473);
				player.getPacketSender().sendChatInterface(2469);
				return this;

			case 4:
				player.getPacketSender().sendString("Select an Option", 2481);
				player.getPacketSender().sendString(options[0], 2482);
				player.getPacketSender().sendString(options[1], 2483);
				player.getPacketSender().sendString(options[2], 2484);
				player.getPacketSender().sendString(options[3], 2485);
				player.getPacketSender().sendChatInterface(2480);
				return this;

			case 5:
				player.getPacketSender().sendString("Select an Option", 2493);
				player.getPacketSender().sendString(options[0], 2494);
				player.getPacketSender().sendString(options[1], 2495);
				player.getPacketSender().sendString(options[2], 2496);
				player.getPacketSender().sendString(options[3], 2497);
				player.getPacketSender().sendString(options[4], 2498);
				player.getPacketSender().sendChatInterface(2492);
				return this;
		}
		return this;
	}

	/**
	 * Appends a {@link StatementDialogue} to the current dialogue chain.
	 *
	 * @param lines
	 * 		The text for this statement.
	 *
	 * @return The instance of this factory.
	 */
	public final DialogueFactory sendStatement(String... lines) {
		validateLength(lines);
		append(new StatementDialogue(lines));
		return this;
	}

	/**
	 * Sends a player a statement dialogue.
	 *
	 * @param dialogue
	 *            The statement dialogue.
	 */
	final DialogueFactory sendStatement(StatementDialogue dialogue) {
		validateLength(dialogue.getLines());
		switch (dialogue.getLines().length) {

			case 1:
				player.getPacketSender().sendString(dialogue.getLines()[0], 357);
				player.getPacketSender().sendString("Click here to continue", 358);
				player.getPacketSender().sendChatInterface(356);
				break;
			case 2:
				player.getPacketSender().sendString(dialogue.getLines()[0], 360);
				player.getPacketSender().sendString(dialogue.getLines()[1], 361);
				player.getPacketSender().sendString("Click here to continue", 362);
				player.getPacketSender().sendChatInterface(359);
				break;
			case 3:
				player.getPacketSender().sendString(dialogue.getLines()[0], 364);
				player.getPacketSender().sendString(dialogue.getLines()[1], 365);
				player.getPacketSender().sendString(dialogue.getLines()[2], 366);
				player.getPacketSender().sendString("Click here to continue", 367);
				player.getPacketSender().sendChatInterface(363);
				break;
			case 4:
				player.getPacketSender().sendString(dialogue.getLines()[0], 369);
				player.getPacketSender().sendString(dialogue.getLines()[1], 370);
				player.getPacketSender().sendString(dialogue.getLines()[2], 371);
				player.getPacketSender().sendString(dialogue.getLines()[3], 372);
				player.getPacketSender().sendString("Click here to continue", 373);
				player.getPacketSender().sendChatInterface(368);
				break;
			case 5:
				player.getPacketSender().sendString(dialogue.getLines()[0], 375);
				player.getPacketSender().sendString(dialogue.getLines()[1], 376);
				player.getPacketSender().sendString(dialogue.getLines()[2], 377);
				player.getPacketSender().sendString(dialogue.getLines()[3], 378);
				player.getPacketSender().sendString(dialogue.getLines()[4], 379);
				player.getPacketSender().sendString("Click here to continue", 380);
				player.getPacketSender().sendChatInterface(374);
			default:
				logger.log(Level.SEVERE, String.format("Invalid statement dialogue line length: %s",
						dialogue.getLines().length));
				break;
		}
		return this;
	}
	
	/**
	 * The method that validates the length of {@code text}.
	 *
	 * @param text
	 *            the text that will be validated.
	 * @throws IllegalStateException
	 *             if any lines of the text exceed a certain length.
	 */
	private final void validateLength(String... text) {
		if (Arrays.stream(text).filter(Objects::nonNull).anyMatch(s -> s.length() > MAXIMUM_LENGTH)) {
			throw new IllegalStateException("Dialogue length too long, maximum length is: " + MAXIMUM_LENGTH);
		}
	}

	/**
	 * The player that owns this factory.
	 *
	 * @return The player.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the {@link Optional} describing the next action in the dialogue chain.
	 *
	 * @return The optional describing the next action.
	 */
	public Optional<Runnable> getNextAction() {
		return nextAction;
	}

	/**
	 * Sets the next action in  the dialogue chain.
	 *
	 * @param nextAction
	 *		The action to set.
	 */
	public void setNextAction(Optional<Runnable> nextAction) {
		this.nextAction = nextAction;
	}

	/**
	 * Determines if dialogues are currently open.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the flag that indicates dialogues are present.
	 * 
	 * @param active
	 * 		The flag to set.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}	
	
}

