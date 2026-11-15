package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.DeveloperCommandEvent
import com.rs2.game.npcs.NPCDefinition
import com.rs2.game.players.Player

@SubscribesTo(DeveloperCommandEvent::class)
class SearchNpc : EventSubscriber<DeveloperCommandEvent> {

    companion object {
        /**
         * Revision 377 maximum NPC ID (3,852 NPCs in cache: IDs 0..3851).
         */
        private const val MAX_NPC_ID = 3851
        private const val INTERFACE_ID = 8134
        private const val TITLE_LINE_ID = 8144
        private const val SUBTITLE_LINE_ID = 8145
        private const val MAX_SCROLL_LINES = 199
        private val CLEAR_RANGES = arrayOf(8146..8195, 12174..12223, 14945..15044)

        private fun showResults(player: Player, query: String, results: List<String>) {
            for (range in CLEAR_RANGES) {
                for (i in range) {
                    player.packetSender.sendString("", i)
                }
            }

            player.packetSender.sendString("@dre@NPC Search: \"$query\"", TITLE_LINE_ID)

            val total = results.size
            val subtitle = if (total > MAX_SCROLL_LINES) {
                "Showing first $MAX_SCROLL_LINES of $total matches (refine search for more)"
            } else {
                "$total match${if (total == 1) "" else "es"} found"
            }
            player.packetSender.sendString(subtitle, SUBTITLE_LINE_ID)

            val display = results.take(MAX_SCROLL_LINES)
            for ((index, line) in display.withIndex()) {
                val lineId = when {
                    index < 49 -> 8147 + index
                    index < 99 -> 12174 + (index - 49)
                    else -> 14945 + (index - 99)
                }
                player.packetSender.sendString(line, lineId)
            }

            player.packetSender.showInterface(INTERFACE_ID)
        }
    }

    override fun test(event: DeveloperCommandEvent): Boolean = when (event.name.lowercase()) {
        "searchnpc", "npcsearch" -> true
        else -> false
    }

    override fun subscribe(context: EventContext, player: Player?, event: DeveloperCommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val arguments = event.arguments
        if (arguments.isEmpty()) {
            player.packetSender.sendMessage("Specify an NPC name or ID to search: ::searchnpc goblin or ::searchnpc 9")
            return
        }

        val query = arguments.joinToString(" ").trim()
        val searchId = query.toIntOrNull()
        val results = ArrayList<String>()

        for (id in 0..MAX_NPC_ID) {
            try {
                val def = NPCDefinition.forId(id) ?: continue
                val defName = def.name ?: continue
                if (defName.isEmpty() || defName.equals("null", ignoreCase = true) || defName.startsWith("NPC #")) continue

                val matches = (searchId != null && id == searchId) || defName.contains(query, ignoreCase = true)
                if (matches) {
                    val combatTag = if (def.combat > 0) " @yel@(Level: ${def.combat})@bla@" else ""
                    results.add("@bla@$defName$combatTag - ID: @blu@$id")
                }
            } catch (ignored: Exception) {
                continue
            }
        }

        if (results.isEmpty()) {
            player.packetSender.sendMessage("No NPCs found matching: $query")
        } else {
            showResults(player, query, results)
        }
    }
}
