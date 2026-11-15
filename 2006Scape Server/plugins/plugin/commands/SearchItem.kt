package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.CommandEvent
import com.rs2.game.players.Player
import org.apollo.cache.def.ItemDefinition

@SubscribesTo(CommandEvent::class)
class SearchItem : EventSubscriber<CommandEvent> {

    companion object {
        /**
         * Revision 377 maximum item ID (7,956 items in cache: IDs 0..7955).
         */
        private const val MAX_ITEM_ID = 7955
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

            player.packetSender.sendString("@dre@Item Search: \"$query\"", TITLE_LINE_ID)

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

    override fun test(event: CommandEvent): Boolean = when (event.name.lowercase()) {
        "searchitem", "itemsearch" -> true
        else -> false
    }

    override fun subscribe(context: EventContext, player: Player?, event: CommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val arguments = event.arguments
        if (arguments.isEmpty()) {
            player.packetSender.sendMessage("Specify an item name or ID to search: ::searchitem dragon or ::searchitem 4151")
            return
        }

        val query = arguments.joinToString(" ").trim()
        val searchId = query.toIntOrNull()
        val count = ItemDefinition.count().coerceAtMost(MAX_ITEM_ID + 1)
        val notedId = if (searchId != null && searchId in 0 until count) ItemDefinition.itemToNote(searchId) else -1
        val unnotedId = if (searchId != null && searchId in 0 until count && ItemDefinition.lookup(searchId)?.isNote == true) {
            ItemDefinition.lookup(searchId)?.noteInfoId ?: -1
        } else -1
        val results = ArrayList<String>()

        for (id in 0 until count) {
            val def = ItemDefinition.lookup(id) ?: continue
            val defName = def.name ?: continue
            if (defName.isEmpty() || defName.equals("null", ignoreCase = true)) continue

            val matches = (searchId != null && (id == searchId || (notedId != searchId && id == notedId) || id == unnotedId))
                || defName.contains(query, ignoreCase = true)
            if (matches) {
                val tag = if (def.isNote) " @dre@(noted)@bla@" else ""
                results.add("@bla@$defName$tag - ID: @blu@$id")
            }
        }

        if (results.isEmpty()) {
            player.packetSender.sendMessage("No items found matching: $query")
        } else {
            showResults(player, query, results)
        }
    }
}
