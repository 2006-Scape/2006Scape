package plugin.area.alkharid

import com.rs2.event.CycleEvent
import com.rs2.event.CycleEventContainer
import com.rs2.event.CycleEventHandler
import com.rs2.game.content.StaticItemList
import com.rs2.game.content.StaticNpcList
import com.rs2.game.content.StaticObjectList
import com.rs2.game.content.music.sound.SoundList
import com.rs2.game.players.Player

object AlKharidGateUtil {

    const val GATE_SOUTH = StaticObjectList.GATE_2882
    const val GATE_NORTH = StaticObjectList.GATE_2883
    const val BORDER_GUARD_1 = StaticNpcList.BORDER_GUARD
    const val BORDER_GUARD_2 = StaticNpcList.BORDER_GUARD_926
    const val TOLL_COINS = 10
    const val COINS_ID = StaticItemList.COINS_995
    const val DIALOGUE_ACTION_TOLL = 502
    const val DIALOGUE_ACTION_SUB_TOLL = 508

    fun Player.hasCompletedPrinceAli(): Boolean = this.princeAli >= 1

    fun Player.hasTollCoins(): Boolean = this.itemAssistant.playerHasItem(COINS_ID, TOLL_COINS)

    fun Player.payToll(): Boolean {
        if (!hasTollCoins()) return false
        this.itemAssistant.deleteItem(COINS_ID, this.itemAssistant.getItemSlot(COINS_ID), TOLL_COINS)
        return true
    }

    fun isNearGate(player: Player): Boolean {
        return (player.absX in 3266..3269) && (player.absY in 3225..3230)
    }

    fun buildPathToGate(player: Player, movingEast: Boolean): List<Pair<Int, Int>> {
        val gateY = if (player.absY >= 3228) 3228 else 3227
        val entranceX = if (movingEast) 3267 else 3268
        val exitX = if (movingEast) 3268 else 3267
        val bypassX = if (movingEast) 3266 else 3269

        val steps = mutableListOf<Pair<Int, Int>>()
        var curX = player.absX
        var curY = player.absY

        // 1. If player is directly behind a guard on the sentry line (X = 3267 or 3268, Y >= 3229 or Y <= 3226),
        // step outward to the bypass column first so we don't walk straight through the guard
        if (curX == entranceX && (curY >= 3229 || curY <= 3226)) {
            curX = bypassX
            steps.add(Pair(curX, curY))
        }

        // 2. Align Y onto the road (3227 or 3228)
        while (curY != gateY) {
            curY += if (gateY > curY) 1 else -1
            steps.add(Pair(curX, curY))
        }

        // 3. Align X to the gate entrance tile
        while (curX != entranceX) {
            curX += if (entranceX > curX) 1 else -1
            steps.add(Pair(curX, curY))
        }

        // 4. Step through the gate
        steps.add(Pair(exitX, gateY))

        return steps
    }

    fun passThroughGate(player: Player) {
        if (System.currentTimeMillis() - player.doorDelay < 1200) {
            return
        }
        player.doorDelay = System.currentTimeMillis()
        player.packetSender.closeAllWindows()

        val movingEast = player.absX <= 3267
        val steps = buildPathToGate(player, movingEast)

        // Play gate opening sound
        player.packetSender.sendSound(SoundList.OPEN_GATE, 100, 0)

        // Temporarily open gates visually
        // South gate (2882) at (3268, 3227) swings to face 3 (south)
        // North gate (2883) at (3268, 3228) swings to face 1 (north)
        player.packetSender.`object`(GATE_SOUTH, 3268, 3227, 3, 0)
        player.packetSender.`object`(GATE_NORTH, 3268, 3228, 1, 0)

        // Prevent client click packets from disrupting the transit
        player.stopPlayer = true

        // Clear walking queue history (prevents travelBack backwards step)
        player.resetWalkingQueue()

        // Populate newWalkCmd steps
        player.newWalkCmdSteps = steps.size
        for (i in steps.indices) {
            val (stepX, stepY) = steps[i]
            player.newWalkCmdX[i] = stepX - player.mapRegionX * 8
            player.newWalkCmdY[i] = stepY - player.mapRegionY * 8
        }

        val durationTicks = steps.size + 1
        CycleEventHandler.getSingleton().addEvent(player, object : CycleEvent() {
            override fun execute(container: CycleEventContainer) {
                // Restore closed gates (both face 0)
                player.packetSender.`object`(GATE_SOUTH, 3268, 3227, 0, 0)
                player.packetSender.`object`(GATE_NORTH, 3268, 3228, 0, 0)
                container.stop()
            }

            override fun stop() {
                player.stopPlayer = false
            }
        }, durationTicks)
    }
}
