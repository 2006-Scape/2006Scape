package com.rs2;

import com.rs2.GameConstants;
import com.rs2.integrations.PlayersOnlineWebsite;
import com.rs2.integrations.RegisteredAccsWebsite;
import org.json.JSONObject;

import com.rs2.GameEngine;
import com.rs2.integrations.discord.JavaCord;

import java.io.*;
import java.util.stream.Collectors;

public class ConfigLoader {
    /*private static void initialize() {
        JSONObject main = new JSONObject();
        main
                .put("bot-token", "")
                .put("websitepass", "")
                .put("erssecret", "");
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter("data/ServerConfig.json"));
            br.write(main.toString());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void loadSettings(String config) throws IOException {
        /*if (!new File("data/ServerConfig.json").exists()) {
            initialize();
            System.out.println("Please open \"data/ServerConfig.json\" file and enter your discord token bot there!");
            System.out.println("Please open \"data/ServerConfig.json\" file and enter your Website Password there!");

        } else {*/
            BufferedReader br = new BufferedReader(new FileReader(config));
            String out = br.lines().collect(Collectors.joining("\n"));
            JSONObject obj = new JSONObject(out);

            if(obj.has("server_name"))
            GameConstants.SERVER_NAME = obj.getString("server_name");
            if(obj.has("debug"))
            GameConstants.SERVER_DEBUG = obj.getBoolean("debug");
            if(obj.has("file_server"))
            GameConstants.FILE_SERVER = obj.getBoolean("file_server");
            if(obj.has("world_id"))
            GameConstants.WORLD = obj.getInt("world_id");

            /*
            * Sets External Services Vars
            */
            if(obj.has("bot-token"))
            JavaCord.token = obj.getString("bot-token");
            if(obj.has("websitepass"))
            PlayersOnlineWebsite.password = obj.getString("websitepass");
            RegisteredAccsWebsite.password = obj.getString("websitepass");
            if(obj.has("erssecret"))
            GameEngine.ersSecret = obj.getString("erssecret");

        //}
    }
}
