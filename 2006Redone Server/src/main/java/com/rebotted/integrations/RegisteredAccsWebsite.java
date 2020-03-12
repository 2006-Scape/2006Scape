package com.rebotted.integrations;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class RegisteredAccsWebsite {
    static String password;
    private static boolean hasntwarned = true;

    private static void setAccountsRegistered(int amount) throws IOException {
        URL url;
        url = new URL("https://rsrebotted.com/accountsregistered.php?pass=" + password + "&amount=" + amount);
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
