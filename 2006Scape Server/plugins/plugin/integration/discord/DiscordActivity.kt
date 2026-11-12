package plugin.integration.discord

import com.rs2.Constants
import com.rs2.game.players.PlayerHandler
import net.dv8tion.jda.api.entities.Activity

object DiscordActivity {

    private var lastActivityText: String? = null

    fun getCurrentActivityText(): String {
        return if (DiscordConfig.displayPlayerCount) {
            val count = PlayerHandler.getPlayerCount()
            if (count == 1) "1 Player Online" else "$count Players Online"
        } else {
            Constants.WEBSITE_LINK
        }
    }

    fun updateActivity(force: Boolean = false) {
        try {
            val jda = DiscordBot.jda ?: return
            val activityText = getCurrentActivityText()
            if (force || activityText != lastActivityText) {
                jda.presence.activity = Activity.playing(activityText)
                println("Discord Activity Updated: $activityText")
                lastActivityText = activityText
            }
        } catch (e: Exception) {
            System.err.println("Could not set Discord activity: $e")
        }
    }
}
