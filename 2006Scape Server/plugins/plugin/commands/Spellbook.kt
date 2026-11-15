package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.DeveloperCommandEvent
import com.rs2.game.players.Player

@SubscribesTo(DeveloperCommandEvent::class)
class Spellbook : EventSubscriber<DeveloperCommandEvent> {

    override fun test(event: DeveloperCommandEvent): Boolean =
        event.name.equals("spellbook", ignoreCase = true)

    override fun subscribe(context: EventContext, player: Player?, event: DeveloperCommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val arguments = event.arguments
        val toModern = if (arguments.isEmpty()) {
            player.playerMagicBook == 1
        } else {
            when (arguments[0].lowercase()) {
                "modern", "normal" -> true
                "ancient", "ancients" -> false
                else -> {
                    player.packetSender.sendMessage("Usage: ::spellbook [modern|ancient]")
                    return
                }
            }
        }

        if (toModern) {
            if (player.playerMagicBook == 0) {
                player.packetSender.sendMessage("You are already on the modern spellbook.")
                return
            }
            player.packetSender.setSidebarInterface(6, 1151)
            player.playerMagicBook = 0
            player.packetSender.sendMessage("You feel a drain on your memory.")
            player.playerAssistant.resetAutocast()
        } else {
            if (player.playerMagicBook == 1) {
                player.packetSender.sendMessage("You are already on the ancient spellbook.")
                return
            }
            player.playerMagicBook = 1
            player.packetSender.setSidebarInterface(6, 12855)
            player.packetSender.sendMessage("An ancient wisdom fills your mind.")
            player.playerAssistant.resetAutocast()
        }
    }
}
