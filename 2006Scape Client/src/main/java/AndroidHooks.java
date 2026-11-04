/**
 * Desktop stub for AndroidHooks so that guarded code compiles cleanly under Maven.
 * In the Android build, this file is replaced by the actual Android implementation.
 */
public final class AndroidHooks {

    private AndroidHooks() {}

    public static boolean isAppInBackground() {
        return false;
    }

    public static void decodeImage(Sprite sprite, byte[] abyte0) {}

    public static String getClipBoard() {
        return "";
    }

    public static void takeScreenshot(Game game, boolean sendMessage, String... subfolders) {}

    public static void showErrorScreen(Game game, boolean loadingError, boolean genericLoadingError, boolean rsAlreadyLoaded) {}

    public static void playSound(byte[] buffer, int offset, int length, int level, int delay) {}

    public static void setSoundVolume(int config) {}

    public static String getCacheDir() {
        return "";
    }

    public static void playMidi(String location, int midiVolume) {}

    public static void stopMidi() {}

    public static boolean setMidiVolume(int value) {
        return false;
    }

    public static Object getMusicPlayer() {
        return null;
    }

    public static void pauseMidi() {}

    public static void resumeMidi() {}

    public static void showKeyboard() {}

    public static void hideKeyboard() {}
}
