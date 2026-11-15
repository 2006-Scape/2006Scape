package plugin.commands

import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.DeveloperCommandEvent
import com.rs2.game.npcs.NPCDefinition
import com.rs2.game.npcs.NpcHandler
import com.rs2.game.players.Player

@SubscribesTo(DeveloperCommandEvent::class)
class SpawnNpc : EventSubscriber<DeveloperCommandEvent> {

    companion object {
        /**
         * Revision 377 maximum NPC ID (3,852 NPCs in cache: IDs 0..3851).
         */
        private const val MAX_NPC_ID = 3851

        private fun findNpcIdByName(name: String): Int {
            var exact = -1
            var prefix = -1
            var substring = -1
            for (id in 0..MAX_NPC_ID) {
                try {
                    val def = NPCDefinition.forId(id) ?: continue
                    val defName = def.name ?: continue
                    if (defName.isEmpty() || defName.equals("null", ignoreCase = true) || defName.startsWith("NPC #")) continue

                    if (defName.equals(name, ignoreCase = true)) {
                        return id
                    }
                    if (prefix == -1 && defName.startsWith(name, ignoreCase = true)) {
                        prefix = id
                    } else if (prefix == -1 && substring == -1 && defName.contains(name, ignoreCase = true)) {
                        substring = id
                    }
                } catch (ignored: Exception) {
                    continue
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
        event.name.equals("spawnnpc", ignoreCase = true) || event.name.equals("npc", ignoreCase = true)

    override fun subscribe(context: EventContext, player: Player?, event: DeveloperCommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val arguments = event.arguments
        if (arguments.isEmpty()) {
            player.packetSender.sendMessage("You must specify an ID or name: ::spawnnpc 1000 or ::spawnnpc goblin")
            return
        }

        val firstArgId = arguments[0].toIntOrNull()
        val npcId = if (firstArgId != null) {
            firstArgId
        } else {
            val npcName = arguments.joinToString(" ")
            val resolvedId = findNpcIdByName(npcName)
            if (resolvedId == -1) {
                player.packetSender.sendMessage("No NPC found matching: $npcName")
                return
            }
            resolvedId
        }

        if (npcId !in 0..MAX_NPC_ID) {
            player.packetSender.sendMessage("No such NPC (ID: $npcId).")
            return
        }

        val def = try { NPCDefinition.forId(npcId) } catch (e: Exception) { null }
        val defName = def?.name
        if (def == null || defName.isNullOrEmpty() || defName.equals("null", ignoreCase = true) || defName.startsWith("NPC #")) {
            player.packetSender.sendMessage("No such NPC (ID: $npcId).")
            return
        }

        val maxHit = NpcHandler.getNpcListCombat(npcId) / 10
        val attack = NpcHandler.getNpcListCombat(npcId)
        val defence = NpcHandler.getNpcListCombat(npcId)
        val hp = NpcHandler.getNpcListHP(npcId).takeIf { it > 0 } ?: def.hitpoints.coerceAtLeast(1)

        NpcHandler.spawnNpc(player, npcId, player.absX, player.absY, player.heightLevel, 0, hp, maxHit, attack, defence, false, false)
        player.packetSender.sendMessage("You have spawned a $defName (ID: $npcId).")
    }
}
