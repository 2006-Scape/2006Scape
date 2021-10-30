package com.rs2.game.dialogues;


/**
 * The {@link ChainablePlugin} implementation that represents dialogue in which an NPC is talking.
 *
 * @author Vult-R
 */
public final class NPCDialogue implements ChainablePlugin {

    /**
     * The id of this npc.
     */
    private int id = -1;

    /**
     * The expression of this NPC.
     */
    private final ExpressionPlugin expression;

    /**
     * The text for this dialogue.
     */
    private final String[] lines;

    /**
     * Creates a new {@link NPCDialogue}
     *
     * @param lines The text for this dialogue.
     */
    public NPCDialogue(String... lines) {
        this(ExpressionPlugin.DEFAULT, lines);
    }

    /**
     * Creates a new {@link NPCDialogue}
     *
     * @param expression The expression of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public NPCDialogue(ExpressionPlugin expression, String... lines) {
        this.expression = expression;
        this.lines = lines;
    }

    /**
     * Creates a new {@link NPCDialogue}
     *
     * @param id The id of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public NPCDialogue(int id, String... lines) {
        this(id, ExpressionPlugin.DEFAULT, lines);
    }

    /**
     * Creates a new {@link NPCDialogue}
     *
     * @param id The id of this npc.
     *
     * @param expression The expression of this npc.
     *
     * @param lines The text for this dialogue.
     */
    public NPCDialogue(int id, ExpressionPlugin expression, String... lines) {
        this.id = id;
        this.expression = expression;
        this.lines = lines;
    }

    @Override
    public void accept(DialogueFactoryPlugin factory) {
        factory.sendNPCChat(this);
    }

    public int getId() {
        return this.id;
    }

    public ExpressionPlugin getExpression() {
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
        if (!(o instanceof NPCDialogue)) return false;
        final NPCDialogue other = (NPCDialogue) o;
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

