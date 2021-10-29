package com.rs2.game.dialogues;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link AstraeusChainable} implementation that represents a dialogue in which options are given to the
 * player.
 *
 * @author Vult-R
 */
public final class AstraeusOptionDialogue implements AstraeusChainable {

    /**
     * The text for this dialogue.
     */
    private final String[] lines;

    /**
     * The list of actions for this dialogue.
     */
    private final List<Runnable> actions = new ArrayList<>();

    /**
     * Creates a new {@link AstraeusOptionDialogue}.
     *
     * @param option1 The text for the first option.
     *
     * @param action1 The action for the first action.
     *
     * @param option2 The text for the second option.
     *
     * @param action2 The action for the second action.
     */
    public AstraeusOptionDialogue(String option1, Runnable action1, String option2, Runnable action2) {
        lines = new String[] {option1, option2};
        actions.add(action1);
        actions.add(action2);
    }

    /**
     * Creates a new {@link AstraeusOptionDialogue}.
     *
     * @param option1 The text for the first option.
     *
     * @param action1 The action for the first action.
     *
     * @param option2 The text for the second option.
     *
     * @param action2 The action for the second action.
     *
     * @param option3 The text for the third option.
     *
     * @param action3 The action for the third action.
     */
    public AstraeusOptionDialogue(String option1, Runnable action1, String option2, Runnable action2,
                          String option3, Runnable action3) {
        lines = new String[] {option1, option2, option3};
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
    }

    /**
     * Creates a new {@link AstraeusOptionDialogue}.
     *
     * @param option1 The text for the first option.
     *
     * @param action1 The action for the first action.
     *
     * @param option2 The text for the second option.
     *
     * @param action2 The action for the second action.
     *
     * @param option3 The text for the third option.
     *
     * @param action3 The action for the third action.
     *
     * @param option4 The text for the four option.
     *
     * @param action4 The action for the four action.
     */
    public AstraeusOptionDialogue(String option1, Runnable action1, String option2, Runnable action2,
                          String option3, Runnable action3, String option4, Runnable action4) {
        lines = new String[] {option1, option2, option3, option4};
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
        actions.add(action4);
    }

    /**
     * Creates a new {@link AstraeusOptionDialogue}.
     *
     * @param option1 The text for the first option.
     *
     * @param action1 The action for the first action.
     *
     * @param option2 The text for the second option.
     *
     * @param action2 The action for the second action.
     *
     * @param option3 The text for the third option.
     *
     * @param action3 The action for the third action.
     *
     * @param option4 The text for the four option.
     *
     * @param action4 The action for the four action.
     *
     * @param option5 The text for the fifth option.
     *
     * @param action5 The action for the fifth action.
     */
    public AstraeusOptionDialogue(String option1, Runnable action1, String option2, Runnable action2,
                          String option3, Runnable action3, String option4, Runnable action4, String option5,
                          Runnable action5) {
        lines = new String[] {option1, option2, option3, option4, option5};
        actions.add(action1);
        actions.add(action2);
        actions.add(action3);
        actions.add(action4);
        actions.add(action5);
    }

    @Override
    public void accept(AstraeusDialogueFactory factory) {
        factory.sendOption(this);
    }

    // Delombok'd code below
    public String[] getLines() {
        return this.lines;
    }

    public List<Runnable> getActions() {
        return this.actions;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AstraeusOptionDialogue)) return false;
        final AstraeusOptionDialogue other = (AstraeusOptionDialogue) o;
        if (!java.util.Arrays.deepEquals(this.getLines(), other.getLines())) return false;
        final Object this$actions = this.getActions();
        final Object other$actions = other.getActions();
        if (this$actions == null ? other$actions != null : !this$actions.equals(other$actions)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getLines());
        final Object $actions = this.getActions();
        result = result * PRIME + ($actions == null ? 43 : $actions.hashCode());
        return result;
    }

    public String toString() {
        return "AstraeusOptionDialogue(lines=" + java.util.Arrays.deepToString(this.getLines()) + ", actions=" + this.getActions() + ")";
    }
}
