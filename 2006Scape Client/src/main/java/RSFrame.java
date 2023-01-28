// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

final class RSFrame extends Frame {

	public RSFrame(RSApplet RSApplet_) {
		rsApplet = RSApplet_;
		setTitle(ClientSettings.SERVER_NAME + " World: " + ClientSettings.SERVER_WORLD);
		this.setResizable(false);

		this.setLayout(new BorderLayout());
		this.add(rsApplet);
		this.pack();

		this.setVisible(true);
		this.toFront();
		this.setLocationRelativeTo(null);
	}

	@Override
	public void update(Graphics g) {
		rsApplet.update(g);
	}

	@Override
	public void paint(Graphics g) {
		rsApplet.paint(g);
	}

	private final RSApplet rsApplet;
}
