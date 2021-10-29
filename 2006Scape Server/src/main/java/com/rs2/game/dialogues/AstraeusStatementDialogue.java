package com.rs2.game.dialogues;

/**
 * The {@link AstraeusChainable} implementation that represents a dialogue with a single statement; which
 * has no models on the dialogue.
 *
 * @author Vult-R
 */
public class AstraeusStatementDialogue implements AstraeusChainable {

    /**
     * The text for this dialogue.
     */
    private final String[] lines;

    /**
     * Creates a new {@link AstraeusStatementDialogue}.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusStatementDialogue(String... lines) {
        this.lines = lines;
    }


    @Override
    public void accept(AstraeusDialogueFactory factory) {
        factory.sendStatement(this);
    }

    // Delombok'd code below
    public String[] getLines() {
        return this.lines;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AstraeusStatementDialogue)) return false;
        final AstraeusStatementDialogue other = (AstraeusStatementDialogue) o;
        if (!other.canEqual((Object) this)) return false;
        if (!java.util.Arrays.deepEquals(this.getLines(), other.getLines())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AstraeusStatementDialogue;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getLines());
        return result;
    }

    public String toString() {
        return "AstraeusStatementDialogue(lines=" + java.util.Arrays.deepToString(this.getLines()) + ")";
    }
}

