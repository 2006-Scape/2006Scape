package plugin.npc.banker

import com.rs2.game.dialogues.DialoguePlugin
import com.rs2.game.dialogues.DialogueFactoryPlugin
import com.rs2.game.dialogues.ExpressionPlugin

/**
 * The Dialogue Class for Gnome and Human Banker NPCs
 * Does NOT include dialogue for Ghost Bankers
 * @author Qweqker
 */
class BankerDialogue : DialoguePlugin() {

    override fun sendDialogues(factory: DialogueFactoryPlugin) {

        factory
                .sendNPCChat(ExpressionPlugin.HAPPY, "Good day. How may I help you?")
                .sendOption("I'd like to access my bank account, please.", {
                    factory
                            .onAction {
                                factory.player.packetSender.openUpBank()
                            }
                }, "I'd like to check my PIN settings.", {
                    factory
                            .onAction {
                                factory.player.bankPin.bankPinSettings()
                            }
                }, "What is this place?") {
                    factory
                            .sendPlayerChat(ExpressionPlugin.DEFAULT,"What is this place?")
                            .sendNPCChat(ExpressionPlugin.DEFAULT,"This is the bank of <servername>. We have many branches in many towns.")
                            .sendPlayerChat(ExpressionPlugin.DEFAULT,"And what do you do?")
                            .sendNPCChat(ExpressionPlugin.DEFAULT,"We will look after your items and money for you. Leave your valuables with us if you want to keep them safe.")
                }
                .execute()
    }
}