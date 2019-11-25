package com.rebotted.integrations;

import org.json.JSONObject;

import com.rebotted.GameEngine;
import com.rebotted.integrations.discord.JavaCord;

import java.io.*;
import java.util.stream.Collectors;

public class SettingsLoader {
    private static void initialize() {
        JSONObject main = new JSONObject();
        main
                .put("bot-token", "")
                .put("websitepass", "")
                .put("erssecret", "");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("data/secrets.json"));
            br.write(main.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() throws IOException {
        if (!new File("data/secrets.json").exists()) {
            initialize();
            System.out.println("Please open \"data/secrets.json\" file and enter your discord token bot there!");
            System.out.println("Please open \"data/secrets.json\" file and enter your Website Password there!");

        } else {
            BufferedReader br = new BufferedReader(new FileReader("data/secrets.json"));
            String out = br.lines().collect(Collectors.joining("\n"));
            JSONObject obj = new JSONObject(out);

            JavaCord.token = obj.getString("bot-token");
            PlayersOnlineWebsite.password = obj.getString("websitepass");
            RegisteredAccsWebsite.password = obj.getString("websitepass");
            GameEngine.ersSecret = obj.getString("erssecret");

        }
    }
}
