package com.rs2.game.dialogues;

/**
 * A {@link AstraeusChainable} implementation that represents a player talking.
 *
 * @author Vult-R
 */
public class AstraeusPlayerDialogue implements AstraeusChainable {

    /**
     * The expression of this player.
     */
    private final AstraeusExpression expression;

    /**
     * The text for this dialogue.
     */
    private final String[] lines;

    /**
     * Creates a new {@link AstraeusPlayerDialogue} with a default expression of {@code DEFAULT}.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusPlayerDialogue(String... lines) {
        this(AstraeusExpression.DEFAULT, lines);
    }

    /**
     * Creates a new {@link AstraeusPlayerDialogue}.
     *
     * @param expression The expression for this dialogue.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusPlayerDialogue(AstraeusExpression expression, String... lines) {
        this.expression = expression;
        this.lines = lines;
    }

    @Override
    public void accept(AstraeusDialogueFactory factory) {
        factory.sendPlayerChat(this);
    }

    public AstraeusExpression getExpression() {
        return this.expression;
    }

    // Delombok'd Code Below
    public String[] getLines() {
        return this.lines;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AstraeusPlayerDialogue)) return false;
        final AstraeusPlayerDialogue other = (AstraeusPlayerDialogue) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$expression = this.getExpression();
        final Object other$expression = other.getExpression();
        if (this$expression == null ? other$expression != null : !this$expression.equals(other$expression))
            return false;
        if (!java.util.Arrays.deepEquals(this.getLines(), other.getLines())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AstraeusPlayerDialogue;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $expression = this.getExpression();
        result = result * PRIME + ($expression == null ? 43 : $expression.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getLines());
        return result;
    }

    public String toString() {
        return "AstraeusPlayerDialogue(expression=" + this.getExpression() + ", lines=" + java.util.Arrays.deepToString(this.getLines()) + ")";
    }
}
