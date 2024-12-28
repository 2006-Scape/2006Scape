package com.rs2.game.dialog;

import com.google.common.collect.ImmutableList;
import com.rs2.game.players.Player;

/**
 * Represents an abstract dialogue, in which extending classes will be able to construct and send dialogues
 * to a player.
 * 
 * @author Vult-R
 */
public abstract class Dialogue {

    private static Player player;

    /**
     * The action buttons responsible for dialogues.
     */
    public static final ImmutableList<Integer> DIALOGUE_BUTTONS = ImmutableList.of(9157, 9167, 9178, 9190,
            9158, 9168, 9179, 9191, 9169, 9180, 9192, 9181, 9193, 9194);

    /**
     * Sends a player a dialogue.
     *
     * @param factory
     *      The factory for this dialogue.
     */
    public abstract void sendDialogues(DialogueFactory factory);

    /**
     * Checks if the button triggered is an optional dialogue button.
     *
     * @param button
     *            The index of the button being checked.
     *
     * @return The result of the operation.
     */
    public static final boolean isDialogueButton(int button) {
        if (player.dialoguePlugin) {
            return DIALOGUE_BUTTONS.stream().anyMatch(search -> search == button);
        } else {
            return false;
        }
    }
}