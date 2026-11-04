import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.swing.JOptionPane;

public final class DesktopHooks {

    private DesktopHooks() {}

    public static void decodeImage(Sprite sprite, byte[] abyte0, Object component) {
        try {
            Component comp = (component instanceof Component) ? (Component) component : null;
            Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
            if (comp != null) {
                MediaTracker mediatracker = new MediaTracker(comp);
                mediatracker.addImage(image, 0);
                mediatracker.waitForAll();
                sprite.width = image.getWidth(comp);
                sprite.height = image.getHeight(comp);
            } else {
                Canvas canvas = new Canvas();
                MediaTracker mediatracker = new MediaTracker(canvas);
                mediatracker.addImage(image, 0);
                mediatracker.waitForAll();
                sprite.width = image.getWidth(canvas);
                sprite.height = image.getHeight(canvas);
            }
            sprite.trimWidth = sprite.width;
            sprite.trimHeight = sprite.height;
            sprite.anInt1442 = 0;
            sprite.anInt1443 = 0;
            sprite.pixels = new int[sprite.width * sprite.height];
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, sprite.width, sprite.height, sprite.pixels, 0, sprite.width);
            pixelgrabber.grabPixels();
        } catch (Exception _ex) {
            System.out.println("Error converting jpg");
        }
    }

    public static String getClipBoard() {
        String myString = "";
        try {
            myString = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String output = "";
        for (int i = 0; i < myString.length(); i++) {
            int j = (int) myString.charAt(i);
            if (j >= 32 && j <= 122) {
                output += (char) j;
            }
        }
        return output;
    }

    public static void takeScreenshot(Game game, boolean sendMessage, String... subfolders) {
        try {
            Window window = (Window) game.getGameComponent();
            Point point = window.getLocationOnScreen();
            int x = (int) point.getX();
            int y = (int) point.getY();
            int w = window.getWidth();
            int h = window.getHeight();
            Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
            Rectangle captureSize = new Rectangle(x, y, w, h);
            BufferedImage bufferedimage = robot.createScreenCapture(captureSize);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
            String dateTime = dateFormat.format(new Date());

            String fileExtension = game.myUsername != null && !game.myUsername.isEmpty() ? game.myUsername : ClientSettings.SERVER_NAME;
            String subfolderPath = String.join(File.separator, subfolders);
            if (!subfolderPath.isEmpty()) {
                subfolderPath += File.separator;
            }

            String screenshotDir = System.getProperty("user.home") + File.separatorChar + ClientSettings.SERVER_NAME + File.separatorChar + "screenshots" + File.separatorChar + subfolderPath;
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(screenshotDir, fileExtension + "_" + dateTime + ".png");
            if (!file.exists()) {
                ImageIO.write(bufferedimage, "png", file);
                if (sendMessage) {
                    game.pushMessage("A picture has been saved in your screenshots folder.", 0, "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showErrorScreen(Game game, boolean loadingError, boolean genericLoadingError, boolean rsAlreadyLoaded) {
        Component comp = game.getGameComponent();
        if (comp == null) return;
        Graphics g = comp.getGraphics();
        if (g == null) return;
        g.setColor(Color.black);
        g.fillRect(0, 0, 765, 503);
        game.method4(1);
        if (loadingError) {
            g.setFont(new Font("Helvetica", 1, 16));
            g.setColor(Color.yellow);
            int k = 35;
            g.drawString("Sorry, an error has occured whilst loading " + ClientSettings.SERVER_NAME + "", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, k);
            k += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
            k += 30;
            g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
            k += 30;
            g.drawString("3: Try using a different game-world", 30, k);
            k += 30;
            g.drawString("4: Try rebooting your computer", 30, k);
            k += 30;
            g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
        }
        if (genericLoadingError) {
            g.setFont(new Font("Helvetica", 1, 20));
            g.setColor(Color.white);
            g.drawString("Error - unable to load game!", 50, 50);
            g.drawString("To play " + ClientSettings.SERVER_NAME + " make sure you play from", 50, 100);
            g.drawString("" + ClientSettings.SERVER_WEBSITE + "", 50, 150);
        }
        if (rsAlreadyLoaded) {
            g.setColor(Color.yellow);
            int l = 35;
            g.drawString("Error a copy of " + ClientSettings.SERVER_NAME + " already appears to be loaded", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.drawString("To fix this try the following (in order):", 30, l);
            l += 50;
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", 1, 12));
            g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
            l += 30;
            g.drawString("2: Try rebooting your computer, and reloading", 30, l);
        }
    }

    public static void checkJavaVersion() {
        if (!System.getProperty("sun.arch.data.model", "").contains("64")) {
            JOptionPane.showMessageDialog(null, "You're running 32-bit java. This will definitely cause problems.\nYou can get the right Java 8 at AdoptOpenJDK.net", "You're running 32-bit Java!", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Please upgrade to 64-bit java to avoid problems! (AdoptOpenJDK.net)");
        }
        try {
            if (Double.parseDouble(System.getProperty("java.specification.version", "1.8")) >= 1.9) {
                JOptionPane.showMessageDialog(null, "You're not running Java 8. If you're using Parabot, this will cause problems!\nYou can get Java 8 from AdoptOpenJDK.net", "You're not running Java 8!", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Please downgrade to Java 8 to avoid problems! (AdoptOpenJDK.net)");
            }
        } catch (Exception ignored) {}
    }

    public static void playSound(byte[] buffer, int offset, int length, int level, int delay) {
        new SoundPlayer(new ByteArrayInputStream(buffer, offset, length), level, delay);
    }

    public static void setSoundVolume(int config) {
        SoundPlayer.setVolume(config);
    }

    public static String getParameter(String s) {
        if (Signlink.mainapp instanceof java.applet.Applet) {
            return ((java.applet.Applet) Signlink.mainapp).getParameter(s);
        }
        return "";
    }

    public static void showDocument(Object caller, String s) {
        try {
            if (caller instanceof java.applet.Applet) {
                java.applet.Applet a = (java.applet.Applet) caller;
                java.applet.AppletContext ctx = a.getAppletContext();
                if (ctx != null && caller instanceof Game) {
                    ctx.showDocument(new URL(((Game) caller).getCodeBase(), "loaderror_" + s + ".html"));
                }
            }
        } catch (Exception ignored) {}
    }

    // Midi support for Signlink
    public static Sequencer music = null;
    public static Synthesizer synthesizer = null;
    public static Sequence sequence = null;

    public static void playSignlinkMidi(String location, int midiVolume) {
        music = null;
        synthesizer = null;
        sequence = null;
        File midiFile = new File(location);
        try {
            sequence = MidiSystem.getSequence(midiFile);
            music = MidiSystem.getSequencer();
            music.open();
            music.setSequence(sequence);
        } catch (Exception e) {
            System.err.println("Problem loading MIDI file.");
            e.printStackTrace();
            return;
        }
        if (music instanceof Synthesizer) {
            synthesizer = (Synthesizer) music;
        } else {
            try {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                if (synthesizer.getDefaultSoundbank() == null) {
                    music.getTransmitter().setReceiver(MidiSystem.getReceiver());
                } else {
                    music.getTransmitter().setReceiver(synthesizer.getReceiver());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        music.start();
        setSignlinkVolume(midiVolume);
    }

    public static boolean setSignlinkVolume(int midiVolume) {
        if (synthesizer == null) {
            return false;
        }
        int CHANGE_VOLUME = 7;
        if (synthesizer.getDefaultSoundbank() == null) {
            try {
                ShortMessage volumeMessage = new ShortMessage();
                for (int i = 0; i < 16; i++) {
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, CHANGE_VOLUME, midiVolume);
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, 39, midiVolume);
                    MidiSystem.getReceiver().send(volumeMessage, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            try {
                MidiChannel[] channels = synthesizer.getChannels();
                for (int c = 0; channels != null && c < channels.length; c++) {
                    channels[c].controlChange(CHANGE_VOLUME, midiVolume);
                    channels[c].controlChange(39, midiVolume);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void stopSignlinkMidi() {
        if (music != null) {
            try {
                music.stop();
                music.close();
            } catch (Exception ignored) {}
            music = null;
        }
    }

    public static void showKeyboard() {}

    public static void hideKeyboard() {}
}
