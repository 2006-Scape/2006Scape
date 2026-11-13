package plugin.integration.website

import com.rs2.Constants
import com.rs2.game.players.PlayerHandler
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object PlayersOnlineWebsite {

    private var task: ScheduledFuture<*>? = null

    fun start() {
        if (!WebsiteConfig.playersOnline) {
            return
        }
        if (WebsiteConfig.password.isEmpty()) {
            WebsiteService.warnMissingPassword()
            return
        }

        stop()
        task = WebsiteService.getScheduler().scheduleAtFixedRate({
            try {
                update()
            } catch (e: Exception) {
                if (Constants.SERVER_DEBUG) {
                    e.printStackTrace()
                } else {
                    System.err.println("[WebsiteIntegration] Error in PlayersOnlineWebsite task: ${e.message}")
                }
            }
        }, 10, 30, TimeUnit.SECONDS)
    }

    fun stop() {
        task?.cancel(true)
        task = null
    }

    fun update() {
        val pass = WebsiteConfig.password
        if (pass.isEmpty()) return

        WebsiteService.sendGetRequest("playersonline.php", mapOf(
            "pass" to pass,
            "amount" to PlayerHandler.getPlayerCount(),
            "world" to Constants.WORLD
        ))
    }
}
