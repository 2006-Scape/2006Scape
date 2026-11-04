import java.util.ArrayList;
import java.util.List;

public final class ClientArgs {

    private ClientArgs() {}

    public static void apply(String rawArgs, Game game) {
        if (rawArgs == null || rawArgs.trim().isEmpty()) {
            return;
        }
        apply(splitArgs(rawArgs), game);
    }

    public static void apply(String[] args, Game game) {
        if (args == null || args.length == 0) {
            return;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) continue;
            String arg = args[i].trim().toLowerCase();

            switch (arg) {
                case "-dev":
                case "-local":
                case "-offline":
                    ClientSettings.SERVER_IP = "localhost";
                    ClientSettings.CHECK_CRC = false;
                    break;
                case "-no-crc":
                case "-no-cache-crc":
                    ClientSettings.CHECK_CRC = false;
                    break;
                case "-crc":
                case "-check-crc":
                    ClientSettings.CHECK_CRC = true;
                    break;
                case "-qol":
                case "-fixes":
                    ClientSettings.CUSTOM_SETTINGS_TAB = true;
                    ClientSettings.BILINEAR_MINIMAP_FILTERING = true;
                    ClientSettings.FIX_TRANSPARENCY_OVERFLOW = true;
                    ClientSettings.FULL_512PX_VIEWPORT = true;
                    if (!ClientSettings.ANDROID) {
                        ClientSettings.CONTROL_KEY_ZOOMING = true;
                    }
                    break;
                case "-no-nav":
                case "-disable-nav":
                    ClientSettings.SHOW_NAVBAR = false;
                    break;
                case "-no-snow":
                case "-hide-snow":
                case "-disable-snow":
                    ClientSettings.SNOW_FLOOR_ENABLED = false;
                    ClientSettings.SNOW_FLOOR_FORCE_ENABLED = false;
                    ClientSettings.SNOW_OVERLAY_FORCE_ENABLED = false;
                    ClientSettings.SNOW_OVERLAY_ENABLED = false;
                    break;
                case "-no-roofs":
                case "-hide-roofs":
                case "-disable-roofs":
                    ClientSettings.HIDE_ROOFS = true;
                    break;
                case "-roofs":
                case "-show-roofs":
                    ClientSettings.HIDE_ROOFS = false;
                    break;
                case "-show-zoom":
                    ClientSettings.SHOW_ZOOM_LEVEL_MESSAGES = true;
                    break;
                case "-screenshots":
                case "-enable-screenshots":
                    ClientSettings.SCREENSHOTS_ENABLED = true;
                    break;
                case "-auto-screenshots":
                case "-enable-auto-screenshots":
                    ClientSettings.AUTOMATIC_SCREENSHOTS_ENABLED = true;
                    break;
            }

            if (args[i].startsWith("-") && (i + 1) < args.length && !args[i + 1].startsWith("-")) {
                String val = args[++i];
                switch (arg) {
                    case "-s":
                    case "-server":
                    case "-ip":
                        ClientSettings.SERVER_IP = val;
                        break;
                    case "-w":
                    case "-world":
                        try {
                            ClientSettings.SERVER_WORLD = Integer.parseInt(val);
                        } catch (NumberFormatException ignored) {}
                        break;
                    case "-u":
                    case "-user":
                    case "-username":
                        if (game != null) {
                            game.myUsername = val;
                        }
                        break;
                    case "-p":
                    case "-pass":
                    case "-password":
                        if (game != null) {
                            game.myPassword = val;
                        }
                        break;
                }
            }
        }
    }

    public static String[] splitArgs(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new String[0];
        }
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '"' || c == '\'') {
                insideQuotes = !insideQuotes;
            } else if (Character.isWhitespace(c) && !insideQuotes) {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list.toArray(new String[0]);
    }
}
