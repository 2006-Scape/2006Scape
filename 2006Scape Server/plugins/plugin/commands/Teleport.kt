package plugin.commands

import com.rs2.Constants
import com.rs2.event.EventContext
import com.rs2.event.EventSubscriber
import com.rs2.event.SubscribesTo
import com.rs2.event.impl.AdminCommandEvent
import com.rs2.game.content.combat.magic.SpellTeleport
import com.rs2.game.content.music.sound.SoundList
import com.rs2.game.players.Player

@SubscribesTo(AdminCommandEvent::class)
class Teleport : EventSubscriber<AdminCommandEvent> {

    companion object {
        private const val ANIMATION_MODERN_START = 714
        private const val ANIMATION_MODERN_END = 715
        private const val GFX_MODERN = 308
        private const val TIMER_MODERN = 10

        private const val ANIMATION_ANCIENT_START = 1979
        private const val GFX_ANCIENT = 392
        private const val TIMER_ANCIENT = 9

        private val CITIES = SpellTeleport.values().associateBy { it.name.lowercase().replace('_', ' ') }

        private val CITY_ALIASES = mapOf(
            "lumby" to "lumbridge",
            "fally" to "falador",
            "cammy" to "camelot",
            "ardy" to "ardougne",
            "ape" to "ape atoll",
            "apeatoll" to "ape atoll"
        )

        private val VALID_CITIES_MESSAGES = listOf(
            "Valid destinations:",
            "Modern: varrock, lumbridge (lumby), falador (fally), camelot (cammy),",
            "        ardougne (ardy), watchtower, trollheim, ape atoll (ape)",
            "Ancient: paddewwa, senntisten, kharyll, lassar,",
            "         dareeyak, carrallangar, annakarl, ghorrock"
        )
    }

    override fun test(event: AdminCommandEvent): Boolean =
        event.name.equals("teleport", ignoreCase = true) || event.name.equals("tele", ignoreCase = true)

    override fun subscribe(context: EventContext, player: Player?, event: AdminCommandEvent) {
        if (player == null) return

        context.breakSubscriberChain()

        val rawArguments = event.arguments
        if (rawArguments.isEmpty()) {
            player.packetSender.sendMessage("Usage: ::teleport <x> <y> [z] or ::teleport <city>")
            player.packetSender.sendMessage("Examples: ::teleport 3222 3218 0 or ::teleport varrock")
            return
        }

        val arguments = rawArguments.map { it.trimEnd(',') }

        val x = arguments[0].toIntOrNull()
        if (x == null) {
            val normalized = arguments.joinToString(" ").lowercase().replace('_', ' ').replace('-', ' ').trim()
            val resolvedCity = CITY_ALIASES[normalized] ?: normalized
            val city = CITIES[resolvedCity]
            if (city == null) {
                player.packetSender.sendMessage("Unknown city '$normalized'.")
                VALID_CITIES_MESSAGES.forEach { player.packetSender.sendMessage(it) }
                return
            }
            val isAncient = city.type.equals("ancient", ignoreCase = true)
            val displayName = city.name.lowercase().replace('_', ' ').split(" ").joinToString(" ") { it.replaceFirstChar(Char::titlecase) }
            teleport(player, city.destX, city.destY, city.destZ, isAncient)
            player.packetSender.sendMessage("Teleporting to $displayName...")
            return
        }

        val y = arguments.getOrNull(1)?.toIntOrNull()
        if (y == null) {
            player.packetSender.sendMessage("Must specify coordinates: ::teleport <x> <y> [z]")
            return
        }

        val z = arguments.getOrNull(2)?.toIntOrNull() ?: player.heightLevel
        val isAncient = player.playerMagicBook == 1
        teleport(player, x, y, z, isAncient)
        player.packetSender.sendMessage("Teleporting to $x, $y${if (z != 0) ", $z" else ""}...")
    }

    private fun teleport(player: Player, x: Int, y: Int, z: Int, isAncient: Boolean) {
        if (player.isDead || player.teleTimer > 0) return

        with(player) {
            if (playerIndex > 0 || npcIndex > 0) {
                combatAssistant.resetPlayerAttack()
            }
            if (clickedTree) {
                clickedTree = false
            }
            stopMovement()
            packetSender.closeAllWindows()
            teleX = x
            teleY = y
            npcIndex = 0
            playerIndex = 0
            faceUpdate(0)
            refresh = heightLevel != z
            teleHeight = z
            isTeleporting = true

            if (Constants.SOUND) {
                packetSender.sendSound(SoundList.TELEPORT, 100, 700)
            }

            if (isAncient) {
                startAnimation(ANIMATION_ANCIENT_START)
                teleGfx = 0
                teleTimer = TIMER_ANCIENT
                teleEndAnimation = 0
                gfx0(GFX_ANCIENT)
            } else {
                startAnimation(ANIMATION_MODERN_START, 10)
                teleTimer = TIMER_MODERN
                teleGfx = GFX_MODERN
                teleEndAnimation = ANIMATION_MODERN_END
            }
        }
    }
}
