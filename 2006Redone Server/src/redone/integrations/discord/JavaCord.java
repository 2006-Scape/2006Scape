package redone.integrations.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.util.logging.ExceptionLogger;
import redone.game.players.PlayerHandler;
import redone.integrations.discord.commands.*;

import java.io.IOException;

import static redone.integrations.SettingsLoader.loadSettings;

/**
 * @author Patrity || https://www.rune-server.ee/members/patrity/
 */

public class JavaCord {

    public static String serverName = "2006-ReBotted";
    public static String token;
    private static DiscordApi api = null;

    public static void init() throws IOException {
        if (token != null && !token.equals("")) { //If the token was loaded by loadSettings:
            new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
                JavaCord.api = api;
                //System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
                api.addListener(new Commands());
                api.addListener(new Forum());
                api.addListener(new Hiscores());
                api.addListener(new Online());
                api.addListener(new Players());
                api.addListener(new Vote());
                api.addListener(new Website());
                api.addMessageCreateListener(event -> {

                    if (event.getMessageContent().startsWith("::movehome")) {
                        if (event.getMessageAuthor().isServerAdmin()) {
                            System.out.println("perms");
                        } else {
                            event.getChannel().sendMessage("You do not have permission to preform this command");
                        }
                    }
                });
            })
                    // Log any exceptions that happened
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