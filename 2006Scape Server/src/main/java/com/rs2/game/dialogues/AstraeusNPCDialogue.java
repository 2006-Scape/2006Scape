package com.rs2.game.dialogues;


/**
 * The {@link AstraeusChainable} implementation that represents dialogue in which an NPC is talking.
 *
 * @author Vult-R
 */
public final class AstraeusNPCDialogue implements AstraeusChainable {

    /**
     * The id of this npc.
     */
    private int id = -1;

    /**
     * The expression of this NPC.
     */
    private final AstraeusExpression expression;

    /**
     * The text for this dialogue.
     */
    private final String[] lines;

    /**
     * Creates a new {@link AstraeusNPCDialogue}
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusNPCDialogue(String... lines) {
        this(AstraeusExpression.DEFAULT, lines);
    }

    /**
     * Creates a new {@link AstraeusNPCDialogue}
     *
     * @param expression The expression of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusNPCDialogue(AstraeusExpression expression, String... lines) {
        this.expression = expression;
        this.lines = lines;
    }

    /**
     * Creates a new {@link AstraeusNPCDialogue}
     *
     * @param id The id of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusNPCDialogue(int id, String... lines) {
        this(id, AstraeusExpression.DEFAULT, lines);
    }

    /**
     * Creates a new {@link AstraeusNPCDialogue}
     *
     * @param id The id of this npc.
     *
     * @param expression The expression of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public AstraeusNPCDialogue(int id, AstraeusExpression expression, String... lines) {
        this.id = id;
        this.expression = expression;
        this.lines = lines;
    }

    @Override
    public void accept(AstraeusDialogueFactory factory) {
        factory.sendNPCChat(this);
    }

    public int getId() {
        return this.id;
    }

    public AstraeusExpression getExpression() {
        return this.expression;
    }

    // Delombok'd Code Below
    public String[] getLines() {
        return this.lines;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AstraeusNPCDialogue)) return false;
        final AstraeusNPCDialogue other = (AstraeusNPCDialogue) o;
        if (this.getId() != other.getId()) return false;
        final Object this$expression = this.getExpression();
        final Object other$expression = other.getExpression();
        if (this$expression == null ? other$expression != null : !this$expression.equals(other$expression))
            return false;
        if (!java.util.Arrays.deepEquals(this.getLines(), other.getLines())) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $expression = this.getExpression();
        result = result * PRIME + ($expression == null ? 43 : $expression.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getLines());
        return result;
    }

    public String toString() {
        return "AstraeusNPCDialogue(id=" + this.getId() + ", expression=" + this.getExpression() + ", lines=" + java.util.Arrays.deepToString(this.getLines()) + ")";
    }
}

