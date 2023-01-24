package plugin.npc.manwoman

import com.rs2.game.dialogues.DialoguePlugin
import com.rs2.game.dialogues.DialogueFactoryPlugin
import com.rs2.game.dialogues.ExpressionPlugin

/**
 * The Dialogue Class for Man and Woman NPCs
 * @author Qweqker
 */
class ManWomanDialogue(private val randomDialogue: Int) : DialoguePlugin() {

    override fun sendDialogues(factory: DialogueFactoryPlugin) {

        // Since Man and Woman NPCs provide a random dialogue every interaction, use a switch statement
        // There are 23 dialogues, but a random number is rolled between 0 and 22 in the FirstClick.kt file
        // as part of the calling process
        when(randomDialogue) {
            0 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Not too bad, but I'm a little worried about the increase of goblins these days.")
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Don't worry, I'll kill them.")
                        .execute()
            1 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "How can I help you?")
                        .sendOption("Do you want to trade? ", {
                                    factory
                                            .sendPlayerChat(ExpressionPlugin.DEFAULT,"Do you want to trade?")
                                            .sendNPCChat(ExpressionPlugin.DEFAULT,"No, I have nothing I wish to get rid of. If you want to do some trading, there are plenty of shops and market stalls around though.")
                                }, "I'm in search of a quest.", {
                                    factory
                                            .sendPlayerChat(ExpressionPlugin.DEFAULT,"I'm in search of a quest.")
                                            .sendNPCChat(ExpressionPlugin.DEFAULT,"I'm sorry I can't help you there.")
                                }, "I'm in search of enemies to kill.") {
                                    factory
                                            .sendPlayerChat(ExpressionPlugin.DEFAULT, "I'm in search of enemies to kill.")
                                            .sendNPCChat(ExpressionPlugin.DEFAULT,"I've heard there are many fearsome creatures that dwell under the ground...")
                                }
                        .execute()
            2 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.ANNOYED, "Get out of my way, I'm in a hurry!")
                        .execute()
            3 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.HAPPY, "I'm fine, how are you?")
                        .sendPlayerChat(ExpressionPlugin.HAPPY, "Very well thank you.")
                        .execute()
            4 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.HAPPY, "Hello there! Nice weather we've been having.")
                        .execute()
            5 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.HAPPY, "I'm very well thank you.")
                        .execute()
            6 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Who are you?")
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "I'm a bold adventurer.")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Ah, a very noble profession.")
                        .execute()
            7 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.ANNOYED, "Do I know you? I'm in a hurry!")
                        .execute()
            8 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "I think we need a new king. The one we've got isn't very good.")
                        .execute()
            9 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Not too bad thanks.")
                        .execute()
            10 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Are you asking for a fight?")
                        /* TODO: Have NPC Attack Player
                            .onAction {

                        }*/
                        .execute()
            11 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "I'm busy right now.")
                        .execute()
            12 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Hello.")
                        .execute()
            13 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "None of your business.")
                        .execute()
            14 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendPlayerChat(ExpressionPlugin.DEFAULT,"Do you wish to trade?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "No, I have nothing I wish to get rid of. If you want to do some trading, there are plenty of shops and market stalls around though.")
                        .execute()
            15 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendPlayerChat(ExpressionPlugin.DEFAULT,"I'm in search of a quest.")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "I'm sorry I can't help you there.")
                        .execute()
            16 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendPlayerChat(ExpressionPlugin.DEFAULT,"I'm in search of enemies to kill.")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "I've heard there are many fearsome creatures that dwell under the ground...")
                        .execute()
            17 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.ANNOYED, "No I don't have any spare change.")
                        .execute()
            18 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DISTRESSED, "I'm a little worried - I've heard there's lots of people going about, killing citizens at random.")
                        .execute()
            19 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DISTRESSED, "No, I don't want to buy anything!")
                        .execute()
            20 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "That is classified information.")
                        .execute()
            21 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Have this flyer...")
                        .onAction {
                            factory.player.itemAssistant.addOrDropItem(956, 1)
                        }
                        .execute()
            22 ->
                factory
                        .sendPlayerChat(ExpressionPlugin.DEFAULT, "Hello, how's it going?")
                        .sendNPCChat(ExpressionPlugin.DEFAULT, "Yo, wassup!")
                        .execute()
        }
    }
}