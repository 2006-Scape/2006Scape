package plugin.dialogue

import com.rs2.game.dialogues.AstraeusDialogue
import com.rs2.game.dialogues.AstraeusDialogueFactory
import com.rs2.game.dialogues.AstraeusExpression

/**
 * The Dialogue Class for Gnome and Human Banker NPCs
 * Does NOT include dialogue for Ghost Bankers
 * @author Qweqker
 */
class BankerDialogue : AstraeusDialogue() {

    override fun sendDialogues(factory: AstraeusDialogueFactory) {

        factory
                .sendNPCChat(AstraeusExpression.HAPPY, "Good day. How may I help you?")
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
                            .sendPlayerChat(AstraeusExpression.DEFAULT,"What is this place?")
                            .sendNPCChat(AstraeusExpression.DEFAULT,"This is the bank of <servername>. We have many branches in many towns.")
                            .sendPlayerChat(AstraeusExpression.DEFAULT,"And what do you do?")
                            .sendNPCChat(AstraeusExpression.DEFAULT,"We will look after your items and money for you. Leave your valuables with us if you want to keep them safe.")
                }
                .execute()
    }
}