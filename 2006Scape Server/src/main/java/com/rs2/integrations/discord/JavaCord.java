package com.rs2.integrations.discord;

import com.rs2.GameConstants;
import com.rs2.integrations.discord.commands.*;
import com.rs2.integrations.discord.commands.admin.AdminCommands;
import com.rs2.integrations.discord.commands.admin.GameKick;
import com.rs2.integrations.discord.commands.admin.MoveHome;
import com.rs2.integrations.discord.commands.admin.Update;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import java.io.IOException;

/**
 * @author Patrity || https://www.rune-server.ee/members/patrity/
 */

public class JavaCord {

    public static String serverName = GameConstants.SERVER_NAME;
    public static String commandPrefix = "::w" + GameConstants.WORLD;
    public static String token;
    public static DiscordApi api = null;

    public static void init() throws IOException {
        if (token != null && !token.equals("")) { //If the token was loaded by loadSettings:
            new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
                try {
                    JavaCord.api = api;
                    //System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
                    api.addListener(new Commands());
                    api.addListener(new Forum());
                    api.addListener(new Hiscores());
                    api.addListener(new Issues());
                    api.addListener(new Online());
                    api.addListener(new Players());
                    api.addListener(new Vote());
                    api.addListener(new Website());
                    //Admin Commands
                    api.addListener(new AdminCommands());
                    api.addListener(new GameKick());
                    api.addListener(new MoveHome());
                    api.addListener(new Update());
                    if(!DiscordActivity.playerCount) {
                        api.updateActivity(GameConstants.WEBSITE_LINK);
                    }
                    api.addMessageCreateListener(event -> {
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            })
                    // Log exceptions (might not work now that we try(catch)
                    .exceptionally(ExceptionLogger.get());
        } else {
            System.out.println("Discord Token Not Set So Bot Not Loaded");
        }
    }

    public static void sendMessage(String channel, String msg) {
        try {
            new MessageBuilder()
                    .append(msg)
                    .send((TextChannel) api.getTextChannelsByNameIgnoreCase(channel).toArray()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}