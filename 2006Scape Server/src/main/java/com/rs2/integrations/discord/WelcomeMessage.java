package com.rs2.integrations.discord;

import com.rs2.Constants;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

public class WelcomeMessage implements ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent event) {
        String welcomeMessageChannel = "655143683083206667";
        if (Constants.WORLD == 1) {
            event.getApi().getTextChannelById(welcomeMessageChannel).get().
                    sendMessage("Hello " + event.getUser().getMentionTag() + " And Welcome To " + JavaCord.serverName + ".");
        }
    }
}