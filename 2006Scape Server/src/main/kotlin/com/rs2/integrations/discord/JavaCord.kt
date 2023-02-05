package com.rs2.integrations.discord

import com.rs2.GameConstants
import com.rs2.integrations.discord.commands.*
import com.rs2.integrations.discord.commands.admin.*
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.entity.message.MessageBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.util.logging.ExceptionLogger
import java.io.IOException
import java.util.*


/**
 * @author Patrity || https://www.rune-server.ee/members/patrity/
 */
object JavaCord {
    var serverName: String = GameConstants.SERVER_NAME
    var commandPrefix = "::w" + GameConstants.WORLD
    @JvmField
    var token: String? = null
    var api: DiscordApi? = null
    @JvmStatic
    @Throws(IOException::class)
    fun init() {
        if (token != null && token != "") { //If the token was loaded by loadSettings:
            DiscordApiBuilder().setToken(token).login().thenAccept { api: DiscordApi ->
                try {
                    JavaCord.api = api
                    //System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
                    api.addListener(Commands())
                    api.addListener(Forum())
                    api.addListener(Hiscores())
                    api.addListener(Issues())
                    api.addListener(Link())
                    api.addListener(Online())
                    api.addListener(Players())
                    api.addListener(Vote())
                    api.addListener(Website())
                    //Admin Commands
                    api.addListener(AdminCommands())
                    api.addListener(GameKick())
                    api.addListener(MoveHome())
                    api.addListener(Update())
                    api.addListener(Pin())
                    api.addListener(Purge())
                    //api.addListener(new Link());
                    //api.addListener(new WelcomeMessage());
                    if (!DiscordActivity.playerCount) {
                        api.updateActivity(GameConstants.WEBSITE_LINK)
                    }
                    api.addMessageCreateListener { event: MessageCreateEvent? -> }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } // Log exceptions (might not work now that we try(catch)
                .exceptionally(ExceptionLogger.get())
        } else {
            println("Discord Token Not Set So Bot Not Loaded")
        }
    }

    fun sendMessage(channel: String?, msg: String?) {
        try {
            MessageBuilder()
                .append(msg)
                .send(api!!.getTextChannelsByNameIgnoreCase(channel).toTypedArray()[0] as TextChannel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}