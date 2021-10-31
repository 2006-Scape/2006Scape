package com.rs2.game.dialogues;


import java.util.function.Consumer;

/**
 * The chain-able interface that allows implementing dialogue factories the ability to chain
 * together.
 *
 * @author Vult-R
 */
public interface ChainablePlugin extends Consumer<DialogueFactoryPlugin> {

}