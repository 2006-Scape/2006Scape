package plugin.quests.cooksassistant.dialogue

import com.rs2.game.content.quests.QuestAssistant
import com.rs2.game.content.quests.QuestRewards
import com.rs2.game.dialogues.DialogueFactoryPlugin
import com.rs2.game.dialogues.DialoguePlugin
import com.rs2.game.dialogues.ExpressionPlugin

/**
 * The Dialogue For The Lumbridge Castle Cook/Cooks Assistant Quest
 */
class LumbridgeCookDialogue : DialoguePlugin() {

    override fun sendDialogues(factory: DialogueFactoryPlugin) {
        if (factory.player.cookAss == 0) {
            factory
                .sendNPCChat(ExpressionPlugin.ANXIOUS, "What am I to do?")
                .sendOption("What's wrong?", {
                    factory
                        .onAction {
                            factory
                                .sendPlayerChat("What's wrong?")
                                .sendNPCChat(
                                    "Oh dear, oh dear, oh dear, I'm in a terrible terrible",
                                    "mess! It's the Duke's birthday today, and I should be",
                                    "making him a lovely big birthday cake!"
                                )
                                .sendNPCChat(
                                    "I've forgotten to buy the ingredients. I'll never get",
                                    "them in time now. He'll sack me! What will I do? I have",
                                    "four children and a goat to look after. Would you help",
                                    "me? Please?"
                                )
                                .sendOption("I'm always happy to help a cook in distress", {
                                    factory.onAction {
                                        factory
                                            .sendPlayerChat("Yes, I'll help you.")
                                            .sendNPCChat(
                                                "Oh thank you, thank you. I need milk, an egg, and",
                                                "flour. I'd be very grateful if you can get them for me."
                                            )
                                            .sendPlayerChat("So where do I find these ingredients then?")
                                            .sendNPCChat(
                                                "You can find flour in any of the shops here.",
                                                "You can find eggs by killing chickens.",
                                                "You can find milk by using a bucket on a cow"
                                            )
                                            .execute()
                                            .player.cookAss = 1
                                        QuestAssistant.sendStages(factory.player)
                                    }
                                },
                                    "I can't right now, Maybe later.", {
                                        factory.onAction {
                                            factory
                                                .sendPlayerChat("I can't right now, Maybe later.")
                                                .sendNPCChat("Oh please! Hurry then!")
                                                .execute()
                                        }

                                    })
                        }
                }, "Can you cook me a cake?", {
                    factory
                        .onAction {
                            factory
                                .sendNPCChat("Does it look like I have the time?")
                                .execute()
                        }
                }, "You don't look very happy.", {
                    factory
                        .sendPlayerChat(ExpressionPlugin.ANXIOUS, "You don't look so happy.")
                        .sendNPCChat(
                            "Oh dear, oh dear, oh dear, I'm in a terrible terrible",
                            "mess! It's the Duke's birthday today, and I should be",
                            "making him a lovely big birthday cake!"
                        )
                        .sendNPCChat(
                            "I've forgotten to buy the ingredients. I'll never get",
                            "them in time now. He'll sack me! What will I do? I have",
                            "four children and a goat to look after. Would you help",
                            "me? Please?"
                        )
                        .sendOption("I'm always happy to help a cook in distress", {
                            factory.onAction {
                                factory
                                    .sendPlayerChat("Yes, I'll help you.")
                                    .sendNPCChat(
                                        "Oh thank you, thank you. I need milk, an egg, and",
                                        "flour. I'd be very grateful if you can get them for me."
                                    )
                                    .sendPlayerChat("So where do I find these ingredients then?")
                                    .sendNPCChat(
                                        "You can find flour in any of the shops here.",
                                        "You can find eggs by killing chickens.",
                                        "You can find milk by using a bucket on a cow"
                                    )
                                    .execute()
                                    .player.cookAss = 1
                                QuestAssistant.sendStages(factory.player)
                            }
                        },
                            "I can't right now, Maybe later.", {
                                factory.onAction {
                                    factory
                                        .sendPlayerChat("I can't right now, Maybe later.")
                                        .sendNPCChat("Oh please! Hurry then!")
                                        .execute()
                                }

                            })
                }, "Nice hat.") {
                    factory
                        .sendNPCChat("I don't have time for your jibber-jabber!")
                }
                .execute()
        } else if(factory.player.cookAss == 1) {
            factory
                .sendNPCChat("How are you getting on with finding the ingredients?")
            if (factory.player.itemAssistant.playerHasItem(1944, 1)
                && factory.player.itemAssistant.playerHasItem(1927, 1)
                && factory.player.itemAssistant.playerHasItem(1933, 1)
            ) {
                factory.sendPlayerChat("Here's all the items!")
                factory.player.itemAssistant.deleteItem(1944, 1)
                factory.player.itemAssistant.deleteItem(1927, 1)
                factory.player.itemAssistant.deleteItem(1933, 1)
                factory.player.cookAss = 2
                factory.sendNPCChat("You brought me everything I need! I'm saved!", "Thank you!")
                    .sendPlayerChat("So do I get to go to the Duke's Party?")
                    .sendNPCChat("I'm afraid not, only the big cheeses get to dine with the", "Duke.")
                    .sendPlayerChat("Well, maybe one day I'll be important enough to sit on", "the Duke's table")
                    .sendNPCChat("Maybe, but I won't be holding my breath.")
                QuestRewards.cookReward(factory.player)
            } else {
                factory.sendPlayerChat("I don't have all the items yet.")
                    .sendNPCChat("Oh please! Hurry then!")
                    .execute()
            }
        } else if(factory.player.cookAss == 2) {
            factory
                .sendPlayerChat("So do I get to go to the Duke's Party?")
                .sendNPCChat("I'm afraid not, only the big cheeses get to dine with the", "Duke.")
                .sendPlayerChat("Well, maybe one day I'll be important enough to sit on", "the Duke's table")
                .sendNPCChat("Maybe, but I won't be holding my breath.")
            QuestRewards.cookReward(factory.player)
        } else if(factory.player.cookAss == 3) {
            factory.sendNPCChat("Thanks for helping me out friend!")
        }
    }
}