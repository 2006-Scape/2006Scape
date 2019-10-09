package redone.integrations.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.util.logging.ExceptionLogger;
import redone.game.players.PlayerHandler;

import java.io.IOException;

import static redone.integrations.SettingsLoader.loadSettings;

/**
 * @author Patrity || https://www.rune-server.ee/members/patrity/
 */

public class JavaCord {

    private static String serverName = "2006-ReBotted";
    public static String token;
    private static DiscordApi api = null;

    public static void init() throws IOException {
        loadSettings();
        if (token != null && !token.equals("")) { //If the token was loaded by loadSettings:
            new DiscordApiBuilder().setToken(token).login().thenAccept(api -> {
                JavaCord.api = api;
                //System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
                api.addMessageCreateListener(event -> {

                    if (event.getMessageContent().equalsIgnoreCase("::players")) {
                        if (PlayerHandler.getPlayerCount() > 1) {
                            event.getChannel().sendMessage("There are currently " + PlayerHandler.getPlayerCount() + " players online.");
                        } else {
                            event.getChannel().sendMessage("There is currently " + PlayerHandler.getPlayerCount() + " player online.");
                        }
                    }

                    if (event.getMessageContent().equalsIgnoreCase("::online")) {
                        event.getChannel().sendMessage(":tada: " + serverName + " is Online! :tada:");
                    }

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