/**
 * NOTICE: IF YOU CHANGE ANYTHING IN GAME.JAVA, PLEASE COPY-PASTE THE WHOLE CLASS OVER TO LOCALGAME.JAVA
 * THIS IS TO ALLOW LOCAL PARABOT TO CONTINUE TO WORK
 */

import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;

/**
 * NOTICE: IF YOU CHANGE ANYTHING IN GAME.JAVA, PLEASE COPY-PASTE THE WHOLE CLASS OVER TO LOCALGAME.JAVA
 * THIS IS TO ALLOW LOCAL PARABOT TO CONTINUE TO WORK
 */
@SuppressWarnings("serial")
public class LocalGame extends Game {
    public LocalGame() {
        super();
        server = "127.0.0.1";
    }
}
