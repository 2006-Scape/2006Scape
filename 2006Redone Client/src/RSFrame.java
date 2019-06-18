// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.Frame;
import java.awt.Graphics;

@SuppressWarnings("serial")
final class RSFrame extends Frame {

	public RSFrame(RSApplet RSApplet_, int i, int j) {
		rsApplet = RSApplet_;
		setTitle(ClientSettings.SERVER_NAME);
		setResizable(false);
		setVisible(true);
		toFront();
		setSize(i + 8, j + 28);
		setLocationRelativeTo(null);
	}

	@Override
	public Graphics getGraphics() {
		Graphics g = super.getGraphics();
		g.translate(4, 24);
		return g;
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
