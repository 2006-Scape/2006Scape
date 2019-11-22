package redone.game.bots;

import redone.Constants;
import redone.game.players.PlayerHandler;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotHandler
{
    static final List<Bot> botList = new ArrayList<>(BotConstants.MAX_BOTS);
    static final Random random = new SecureRandom();

    public static void connectBots(int botCount)
    {
        Bot bot;
        for (int bots = 0; bots < botCount; bots++)
        {
            if (PlayerHandler.playerCount >= Constants.MAX_PLAYERS)
            {
                System.out.println("Bot could not be connected, server is full.");
                return;
            }

            final String botName = "bot" + random.nextInt(9999) + random.nextInt(9999);

            bot = new Bot(botName);
            botList.add(bot);
        }

    }
}
