package plugin.integration.discord

import com.rs2.Constants
import com.rs2.game.players.Client
import com.rs2.game.players.PlayerHandler
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.requests.GatewayIntent
import plugin.integration.discord.commands.*
import plugin.integration.discord.commands.admin.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

object DiscordBot {

    var commandPrefix: String = "::w" + Constants.WORLD
    var jda: JDA? = null

    private var activityScheduler: ScheduledExecutorService? = null

    fun isAdmin(member: Member?): Boolean {
        return member?.hasPermission(Permission.ADMINISTRATOR) == true
    }

    fun findOnlinePlayer(name: String): Client? {
        return PlayerHandler.players.filterNotNull().firstOrNull {
            it.playerName != null && it.playerName.equals(name, ignoreCase = true)
        } as? Client
    }

    fun init() {
        val currentToken = DiscordConfig.botToken
        if (currentToken.isNotEmpty()) {
            Executors.newSingleThreadExecutor { r ->
                Thread(r, "DiscordBot-Init").apply { isDaemon = true }
            }.execute {
                try {
                    val listeners = mutableListOf<Any>(
                        Online(),
                        Players(),
                        GameKick(),
                        MoveHome(),
                        Update()
                    )
                    if (Constants.WORLD == 1) {
                        listeners.addAll(
                            listOf(
                                Commands(),
                                AdminCommands(),
                                Forum(),
                                Issues(),
                                Link(),
                                Website(),
                                Purge(),
                                WelcomeMessage()
                            )
                        )
                    }

                    val builder = JDABuilder.createDefault(currentToken)
                        .enableIntents(
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MEMBERS
                        )
                        .setStatus(OnlineStatus.ONLINE)
                        .setActivity(Activity.playing(DiscordActivity.getCurrentActivityText()))
                        .addEventListeners(*listeners.toTypedArray())

                    val builtJda = builder.build()
                    builtJda.awaitReady()
                    jda = builtJda
                    startActivityScheduler()
                    DiscordActivity.updateActivity(force = true)
                    println("Discord Bot Successfully Connected & Online as: ${builtJda.selfUser.asTag}")
                } catch (e: Exception) {
                    System.err.println("Failed to start Discord Bot (JDA 5): ${e.message}")
                }
            }
        } else {
            println("Discord Token Not Set So Bot Not Loaded")
        }
    }

    @Synchronized
    fun startActivityScheduler() {
        if (activityScheduler == null || activityScheduler!!.isShutdown) {
            val scheduler = Executors.newSingleThreadScheduledExecutor { r ->
                Thread(r, "DiscordActivityUpdater").apply { isDaemon = true }
            }
            activityScheduler = scheduler
            scheduler.scheduleAtFixedRate({
                try {
                    DiscordActivity.updateActivity()
                } catch (e: Exception) {
                    System.err.println("Error updating Discord activity: ${e.message}")
                }
            }, 10, 15, TimeUnit.SECONDS)
        }
    }

    @Synchronized
    fun stopActivityScheduler() {
        activityScheduler?.shutdownNow()
        activityScheduler = null
    }

    fun shutdown() {
        stopActivityScheduler()
        jda?.let {
            try {
                it.shutdown()
                println("Discord Bot Disconnected.")
            } catch (e: Exception) {
                System.err.println("Error disconnecting Discord Bot: ${e.message}")
            }
            jda = null
        }
    }

    fun sendMessage(channelName: String, msg: String) {
        try {
            jda?.let { currentJda ->
                val channels = currentJda.getTextChannelsByName(channelName, true)
                if (channels.isNotEmpty()) {
                    channels[0].sendMessage(msg).queue()
                }
            }
        } catch (e: Exception) {
            System.err.println("Error sending message to channel $channelName: ${e.message}")
        }
    }

    fun sendLog(msg: String) {
        try {
            if (DiscordConfig.logChannelId.isNotEmpty()) {
                val channel = jda?.getTextChannelById(DiscordConfig.logChannelId)
                channel?.sendMessage(msg)?.queue()
            }
        } catch (e: Exception) {
            System.err.println("Error sending Discord log message: ${e.message}")
        }
    }
}
