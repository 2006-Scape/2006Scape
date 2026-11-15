package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.DeveloperCommandEvent
import com.rs2.game.players.Player
import org.apollo.cache.def.ItemDefinition
import java.text.NumberFormat

@SubscribesTo(DeveloperCommandEvent::class)
class SpawnItem : EventSubscriber<DeveloperCommandEvent> {

    companion object {
        /**
         * Revision 377 maximum item ID (7,956 items in cache: IDs 0..7955).
         */
        private const val MAX_ITEM_ID = 7955

        private fun parseAmount(str: String): Int? {
            val clean = str.trim().lowercase()
            if (clean.isEmpty()) return null

            val multiplier = when {
                clean.endsWith("b") -> 1_000_000_000L
                clean.endsWith("m") -> 1_000_000L
                clean.endsWith("k") -> 1_000L
                else -> 1L
            }

            val numPart = if (multiplier > 1L) clean.dropLast(1) else clean
            val num = numPart.toDoubleOrNull() ?: return null
            if (num <= 0.0) return null

            val total = (num * multiplier).toLong()
            return total.coerceIn(1L, Int.MAX_VALUE.toLong()).toInt()
        }

        private fun findItemIdByName(name: String): Int {
            var exact = -1
            var prefix = -1
            var substring = -1
            val count = ItemDefinition.count().coerceAtMost(MAX_ITEM_ID + 1)
            for (id in 0 until count) {
                val def = ItemDefinition.lookup(id) ?: continue
                val defName = def.name ?: continue
                if (defName.isEmpty() || defName.equals("null", ignoreCase = true)) continue

                if (defName.equals(name, ignoreCase = true)) {
                    if (!def.isNote) return id
                    if (exact == -1) exact = id
                } else if (exact == -1) {
                    if (defName.startsWith(name, ignoreCase = true)) {
                        if (prefix == -1 || (ItemDefinition.lookup(prefix)?.isNote == true && !def.isNote)) {
                            prefix = id
                        }
                    } else if (prefix == -1 && defName.contains(name, ignoreCase = true)) {
                        if (substring == -1 || (ItemDefinition.lookup(substring)?.isNote == true && !def.isNote)) {
                            substring = id
                        }
                    }
                }
            }
            return when {
                exact != -1 -> exact
                prefix != -1 -> prefix
                else -> substring
            }
        }
    }

    override fun test(event: DeveloperCommandEvent): Boolean =
        event.name.equals("spawnitem", ignoreCase = true) || event.name.equals("item", ignoreCase = true)

    override fun subscribe(context: EventContext, player: Player?, event: DeveloperCommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val arguments = event.arguments
        if (arguments.isEmpty()) {
            player.packetSender.sendMessage("Must specify an item id or name: ::spawnitem 995 10m, ::spawnitem coins 100k, or ::spawnitem noted gold ore 10m")
            return
        }

        val tokens = arguments.toMutableList()
        var requestedNote = false
        if (tokens.isNotEmpty() && (tokens.first().equals("noted", ignoreCase = true) || tokens.first().equals("note", ignoreCase = true))) {
            requestedNote = true
            tokens.removeAt(0)
        } else if (tokens.isNotEmpty() && (tokens.last().equals("noted", ignoreCase = true) || tokens.last().equals("note", ignoreCase = true))) {
            requestedNote = true
            tokens.removeAt(tokens.size - 1)
        }

        if (tokens.isEmpty()) {
            player.packetSender.sendMessage("Must specify an item id or name: ::spawnitem noted gold ore 10m")
            return
        }

        val firstArgId = tokens[0].toIntOrNull()
        var itemId: Int
        val itemAmount: Int

        if (firstArgId != null) {
            itemId = firstArgId
            if (tokens.size >= 2) {
                val parsed = parseAmount(tokens[1])
                if (parsed == null) {
                    player.packetSender.sendMessage("Invalid amount specified: ${tokens[1]}")
                    return
                }
                itemAmount = parsed
            } else {
                itemAmount = 1
            }
        } else {
            var amount = 1
            var nameTokens = tokens.toList()
            if (tokens.size >= 2) {
                val trailingAmount = parseAmount(tokens.last())
                if (trailingAmount != null) {
                    amount = trailingAmount
                    nameTokens = tokens.subList(0, tokens.size - 1)
                }
            }
            val itemName = nameTokens.joinToString(" ")
            val resolvedId = findItemIdByName(itemName)
            if (resolvedId == -1) {
                player.packetSender.sendMessage("No item found matching: $itemName")
                return
            }
            itemId = resolvedId
            itemAmount = amount
        }

        if (itemId !in 0..MAX_ITEM_ID || itemId >= ItemDefinition.count()) {
            player.packetSender.sendMessage("No such item (ID: $itemId).")
            return
        }

        var def = ItemDefinition.lookup(itemId)
        val defName = def?.name
        if (def == null || defName.isNullOrEmpty() || defName.equals("null", ignoreCase = true)) {
            player.packetSender.sendMessage("No such item (ID: $itemId).")
            return
        }

        if (requestedNote) {
            val notedId = ItemDefinition.itemToNote(itemId)
            if (notedId == itemId) {
                if (!def.isNote) {
                    player.packetSender.sendMessage("Note: $defName cannot be noted.")
                }
            } else {
                itemId = notedId
                def = ItemDefinition.lookup(notedId) ?: def
            }
        }

        val added = player.itemAssistant.addItem(itemId, itemAmount)
        if (!added) {
            return
        }

        if (player.isBusy) {
            player.packetSender.closeAllWindows()
        }

        val formattedAmount = NumberFormat.getIntegerInstance().format(itemAmount)
        val noteTag = if (def.isNote) " (noted)" else ""
        player.packetSender.sendMessage("You have spawned $formattedAmount x $defName$noteTag (ID: $itemId).")
    }
}
