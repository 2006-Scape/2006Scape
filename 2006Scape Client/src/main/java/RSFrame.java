// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

final class RSFrame extends Frame {

	public RSFrame(RSApplet applet) {
		rsApplet = applet;

		setTitle(ClientSettings.SERVER_NAME + " World: " + ClientSettings.SERVER_WORLD + ((ClientSettings.SERVER_IP.equals("localhost") || ClientSettings.SERVER_IP.equals("127.0.0.1")) ?  " [Local]" : ""));
		this.setResizable(false);
		this.setBackground(Color.BLACK);

		this.setLayout(new BorderLayout());
		this.add(applet, BorderLayout.CENTER);
		this.pack();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.toFront();
		this.transferFocus();
	}

	private final RSApplet rsApplet;

}
