package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.CommandEvent
import com.rs2.game.npcs.NPCDefinition
import com.rs2.game.npcs.drops.ItemDrop
import com.rs2.game.npcs.drops.NPCDropsHandler
import com.rs2.game.players.Player
import com.rs2.util.NpcDrop
import org.apollo.cache.def.ItemDefinition

@SubscribesTo(CommandEvent::class)
class Drops : EventSubscriber<CommandEvent> {

    companion object {
        private const val INTERFACE_ID = 8134
        private const val TITLE_LINE_ID = 8144
        private const val SUBTITLE_LINE_ID = 8145
        private const val MAX_SCROLL_LINES = 199
        private const val MAX_DISPLAY_RESULTS = 196
        private val CLEAR_RANGES = arrayOf(8144..8195, 12174..12223, 14945..15044)

        private fun formatChance(chance: Int): String = when {
            chance <= 1 -> "@gre@Always@bla@"
            chance <= 32 -> "@blu@1/$chance@bla@"
            chance <= 128 -> "@yel@1/$chance@bla@"
            else -> "@red@1/$chance@bla@"
        }

        private fun formatAmount(amounts: IntArray?): String {
            if (amounts == null || amounts.isEmpty()) return ""
            return if (amounts.size >= 2 && amounts[0] != amounts[1]) "${amounts[0]}-${amounts[1]}x " else "${amounts[0]}x "
        }

        private fun ItemDefinition.displayName(): String {
            val noteTag = if (isNote) " (noted)" else ""
            return "$name$noteTag"
        }

        private fun String.toTitleCase(): String =
            split(" ")
                .filter { it.isNotEmpty() }
                .joinToString(" ") { word ->
                    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                }

        private fun findCacheItemName(query: String): String? {
            val defs = ItemDefinition.getDefinitions() ?: return null
            return defs.firstOrNull { it?.name.equals(query, ignoreCase = true) }?.displayName()
        }

        private fun formatDisplayTitle(input: String, searchId: Int?, matchedItemNames: Set<String>): String {
            if (searchId != null) {
                ItemDefinition.forId(searchId)?.takeIf { !it.name.isNullOrEmpty() }?.let { return it.displayName() }
            }

            return findCacheItemName(input)
                ?: matchedItemNames.singleOrNull()
                ?: input.toTitleCase()
        }
    }

    private class ScrollInterface(private val player: Player) {
        var linesWritten = 0
            private set

        fun open(title: String, subtitle: String) {
            for (range in CLEAR_RANGES) {
                for (i in range) {
                    player.packetSender.sendString("", i)
                }
            }
            player.packetSender.sendString("@dre@$title", TITLE_LINE_ID)
            player.packetSender.sendString(subtitle, SUBTITLE_LINE_ID)
        }

        fun writeLine(text: String): Boolean {
            if (linesWritten >= MAX_SCROLL_LINES) return false
            val lineId = when {
                linesWritten < 49 -> 8147 + linesWritten
                linesWritten < 99 -> 12174 + (linesWritten - 49)
                else -> 14945 + (linesWritten - 99)
            }
            player.packetSender.sendString(text, lineId)
            linesWritten++
            return true
        }

        fun show() {
            player.packetSender.showInterface(INTERFACE_ID)
        }
    }

    private data class ItemDropResult(
        val npcName: String,
        val combatLevel: Int,
        val itemDisplayName: String,
        val amountStr: String,
        val chance: Int
    ) {
        fun toDisplayText(): String =
            "@blu@$npcName@bla@ (lvl $combatLevel) - $amountStr$itemDisplayName (${formatChance(chance)})"
    }

    private data class NpcDropEntry(
        val itemId: Int,
        val chance: Int,
        val amountStr: String
    )

    private class NpcGroup(val combat: Int) {
        val items = ArrayList<ItemDrop>()
    }

    override fun subscribe(context: EventContext, player: Player?, event: CommandEvent) {
        if (player == null) return

        when (event.name.lowercase()) {
            "drops", "searchdrops" -> {
                context.breakSubscriberChain()
                handleDropsCommand(player, event.arguments)
            }
        }
    }

    private fun handleDropsCommand(player: Player, arguments: Array<String>) {
        if (arguments.isEmpty()) {
            sendUsage(player)
            return
        }

        val subcommand = arguments[0].lowercase()
        val query = arguments.drop(1).joinToString(" ").trim()

        when (subcommand) {
            "item", "i" -> {
                if (query.isEmpty()) {
                    player.packetSender.sendMessage("Usage: ::drops item <id or name>")
                    return
                }
                val drops = NPCDropsHandler.getNpcDrops()
                if (drops.isNullOrEmpty()) {
                    player.packetSender.sendMessage("NPC drops are not currently loaded.")
                    return
                }
                handleItemDropsCommand(player, drops, query)
            }
            "npc", "n" -> {
                if (query.isEmpty()) {
                    player.packetSender.sendMessage("Usage: ::drops npc <id or name>")
                    return
                }
                val drops = NPCDropsHandler.getNpcDrops()
                if (drops.isNullOrEmpty()) {
                    player.packetSender.sendMessage("NPC drops are not currently loaded.")
                    return
                }
                handleNpcDropsCommand(player, drops, query)
            }
            else -> sendUsage(player)
        }
    }

    private fun sendUsage(player: Player) {
        player.packetSender.sendMessage("Usage: ::drops item <id/name> or ::drops npc <id/name>")
    }

    private fun handleItemDropsCommand(player: Player, drops: Array<NpcDrop>, query: String) {
        val searchId = query.toIntOrNull()
        val results = LinkedHashSet<ItemDropResult>()
        val matchedItemNames = LinkedHashSet<String>()
        var totalVariants = 0

        for (npcDrop in drops) {
            val items = npcDrop.items ?: continue
            val npcDef = NPCDefinition.forId(npcDrop.id) ?: continue
            if (npcDef.combat <= 0) continue

            val npcName = npcDef.name ?: "Unknown"
            val combat = npcDef.combat

            for (itemDrop in items) {
                if (searchId != null && itemDrop.item_id != searchId) {
                    continue
                }

                val itemDef = ItemDefinition.forId(itemDrop.item_id) ?: continue
                val rawName = itemDef.name ?: continue

                if (searchId == null && !rawName.contains(query, ignoreCase = true)) {
                    continue
                }

                totalVariants++
                val displayName = itemDef.displayName()
                matchedItemNames.add(displayName)

                results.add(
                    ItemDropResult(
                        npcName = npcName,
                        combatLevel = combat,
                        itemDisplayName = displayName,
                        amountStr = formatAmount(itemDrop.amounts),
                        chance = itemDrop.chance
                    )
                )
            }
        }

        val displayTitle = formatDisplayTitle(query, searchId, matchedItemNames)

        if (results.isEmpty()) {
            player.packetSender.sendMessage("No NPCs found dropping: '$displayTitle'")
            return
        }

        val sortedResults = results.sortedWith(
            compareBy({ it.combatLevel }, { it.npcName }, { it.chance })
        )

        val scroll = ScrollInterface(player)
        scroll.open("NPC Drops: $displayTitle", "Showing NPCs that drop this item:")

        val displayLimit = sortedResults.size.coerceAtMost(MAX_DISPLAY_RESULTS)
        for (i in 0 until displayLimit) {
            scroll.writeLine(sortedResults[i].toDisplayText())
        }

        scroll.writeLine("")
        if (sortedResults.size > MAX_DISPLAY_RESULTS) {
            scroll.writeLine("@red@Only $MAX_DISPLAY_RESULTS results can be displayed (out of ${sortedResults.size} matches).")
            player.packetSender.sendMessage(
                "Found ${sortedResults.size} drop matches for '$displayTitle'. Only $MAX_DISPLAY_RESULTS results can be displayed."
            )
        } else {
            val variantsText = if (totalVariants > sortedResults.size) " ($totalVariants NPC variants)" else ""
            scroll.writeLine("@dre@Total: ${sortedResults.size} unique drop match${if (sortedResults.size == 1) "" else "es"}$variantsText")
            player.packetSender.sendMessage(
                "Found ${sortedResults.size} drop match${if (sortedResults.size == 1) "" else "es"} for '$displayTitle'."
            )
        }

        scroll.show()
    }

    private fun handleNpcDropsCommand(player: Player, drops: Array<NpcDrop>, query: String) {
        val searchId = query.toIntOrNull()
        val matchingNpcDrops = ArrayList<Pair<NPCDefinition, NpcDrop>>()

        for (npcDrop in drops) {
            val npcDef = NPCDefinition.forId(npcDrop.id) ?: continue
            val npcName = npcDef.name ?: continue
            if (npcName.isEmpty() || npcDef.combat <= 0) continue

            val matches = if (searchId != null) {
                npcDrop.id == searchId
            } else {
                npcName.contains(query, ignoreCase = true)
            }

            if (matches) {
                matchingNpcDrops.add(Pair(npcDef, npcDrop))
            }
        }

        if (matchingNpcDrops.isEmpty()) {
            player.packetSender.sendMessage("No NPC found matching: '$query'")
            return
        }

        val targetDrops = if (searchId == null) {
            matchingNpcDrops.filter { it.first.name.equals(query, ignoreCase = true) }.ifEmpty { matchingNpcDrops }
        } else {
            matchingNpcDrops
        }

        val grouped = LinkedHashMap<String, NpcGroup>()
        for ((npcDef, drop) in targetDrops) {
            val group = grouped.getOrPut(npcDef.name) { NpcGroup(npcDef.combat) }
            val items = drop.items ?: continue
            group.items.addAll(items)
        }

        val singleNpc = grouped.size == 1
        val headerTitle = if (singleNpc) {
            val name = grouped.keys.first()
            val combat = grouped[name]?.combat ?: 0
            "$name (lvl $combat)"
        } else {
            query.toTitleCase()
        }

        val scroll = ScrollInterface(player)
        scroll.open("Drop Table: $headerTitle", "Showing all drops for this NPC:")

        var totalUniqueDrops = 0
        var dropsDisplayed = 0

        for ((npcName, group) in grouped) {
            if (!singleNpc && dropsDisplayed < MAX_DISPLAY_RESULTS) {
                scroll.writeLine("@dre@--- $npcName (lvl ${group.combat}) ---")
            }

            val distinctEntries = LinkedHashSet<NpcDropEntry>()
            for (item in group.items) {
                distinctEntries.add(
                    NpcDropEntry(
                        itemId = item.item_id,
                        chance = item.chance,
                        amountStr = formatAmount(item.amounts)
                    )
                )
            }

            val sortedEntries = distinctEntries.sortedWith(
                compareBy<NpcDropEntry>({ if (it.chance <= 1) 0 else 1 }, { it.chance })
                    .thenBy { ItemDefinition.forId(it.itemId)?.name.orEmpty() }
            )

            for (entry in sortedEntries) {
                val def = ItemDefinition.forId(entry.itemId) ?: continue
                if (def.name == null) continue

                totalUniqueDrops++
                if (dropsDisplayed < MAX_DISPLAY_RESULTS) {
                    val dropChance = formatChance(entry.chance)
                    scroll.writeLine("${entry.amountStr}${def.displayName()} ($dropChance)")
                    dropsDisplayed++
                }
            }

            if (!singleNpc && dropsDisplayed < MAX_DISPLAY_RESULTS) {
                scroll.writeLine("")
            }
        }

        scroll.writeLine("")
        if (totalUniqueDrops > MAX_DISPLAY_RESULTS) {
            scroll.writeLine("@red@Only $MAX_DISPLAY_RESULTS results can be displayed (out of $totalUniqueDrops drops).")
        } else {
            scroll.writeLine("@dre@Total: $totalUniqueDrops unique drop${if (totalUniqueDrops == 1) "" else "s"}")
        }

        scroll.show()
        player.packetSender.sendMessage("Found $totalUniqueDrops drops for '$headerTitle'.")
    }
}
