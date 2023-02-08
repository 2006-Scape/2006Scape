package com.rs2.integrations;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.rs2.Constants;

public class RegisteredAccsWebsite {
    public static String password;
    private static boolean hasntwarned = true;

    private static void setAccountsRegistered(int amount) throws IOException {
        URL url;
        url = new URL(Constants.WEBSITE_LINK + "/accountsregistered.php?pass=" + password + "&amount=" + amount + "&world=" + Constants.WORLD);
        url.openStream().close();
    }

    private static int count = 25;
    public static void addUpdateRegisteredUsersTask() {
        if (password != null && !password.equals("")) {
            if (count == 0) {
                try {
                    setAccountsRegistered(new File("data/characters/").list().length);
                    count = 150;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                count--;
            }
        } else if (hasntwarned) {
            hasntwarned = false;
        }
    }
}
