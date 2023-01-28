// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class DrawingArea extends NodeSub {

	public static void initDrawingArea(int i, int j, int ai[]) {
		pixels = ai;
		width = j;
		height = i;
		setDrawingArea(i, 0, j, 0);
	}

	public static void defaultDrawingAreaSize() {
		topX = 0;
		topY = 0;
		bottomX = width;
		bottomY = height;
		if (ClientSettings.FULL_512PX_VIEWPORT) {
			centerX = bottomX;
		} else {
			centerX = bottomX - 1;
		}
		centerY = bottomX / 2;
	}

	public static void setDrawingArea(int i, int j, int k, int l) {
		if (j < 0) {
			j = 0;
		}
		if (l < 0) {
			l = 0;
		}
		if (k > width) {
			k = width;
		}
		if (i > height) {
			i = height;
		}
		topX = j;
		topY = l;
		bottomX = k;
		bottomY = i;
		if (ClientSettings.FULL_512PX_VIEWPORT) {
			centerX = bottomX;
		} else {
			centerX = bottomX - 1;
		}
		centerY = bottomX / 2;
		anInt1387 = bottomY / 2;
	}

	public static void setAllPixelsToZero() {
		int i = width * height;
		for (int j = 0; j < i; j++) {
			pixels[j] = 0;
		}

	}

	public static void fillArea(int _color, int _y, int _width, int _height, int _opacity, int _x) {
		if (_x < topX) {
			_width -= topX - _x;
			_x = topX;
		}
		if (_y < topY) {
			_height -= topY - _y;
			_y = topY;
		}
		if (_x + _width > bottomX) {
			_width = bottomX - _x;
		}
		if (_y + _height > bottomY) {
			_height = bottomY - _y;
		}
		int l1 = 256 - _opacity;
		int i2 = (_color >> 16 & 0xff) * _opacity;
		int j2 = (_color >> 8 & 0xff) * _opacity;
		int k2 = (_color & 0xff) * _opacity;
		int k3 = width - _width;
		int l3 = _x + _y * width;
		for (int i4 = 0; i4 < _height; i4++) {
			for (int j4 = -_width; j4 < 0; j4++) {
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}

	public static void fillArea(int _height, int _y, int _color, int _width, int _x) {
		if (_x < topX) {
			_width -= topX - _x;
			_x = topX;
		}
		if (_y < topY) {
			_height -= topY - _y;
			_y = topY;
		}
		if (_x + _width > bottomX) {
			_width = bottomX - _x;
		}
		if (_y + _height > bottomY) {
			_height = bottomY - _y;
		}
		int k1 = width - _width;
		int l1 = _x + _y * width;
		for (int i2 = -_height; i2 < 0; i2++) {
			for (int j2 = -_width; j2 < 0; j2++) {
				pixels[l1++] = _color;
			}

			l1 += k1;
		}

	}

	public static void fillPixels(int _y, int _height, int _color, int _x, int _width) {
		drawHorizontalLine(_y, _color, _width, _x);
		drawHorizontalLine(_y + _height - 1, _color, _width, _x);
		drawVerticalLine(_y, _color, _height, _x);
		drawVerticalLine(_y, _color, _height, _x + _width - 1);
	}

	public static void drawFrameRounded(int _y, int _height, int _opacity, int _color, int _width, int _x) {
		drawHorizontalLine(_color, _width, _y, _opacity, _x);
		drawHorizontalLine(_color, _width, _y + _height - 1, _opacity, _x);
		if (_height >= 3) {
			drawVerticalLine(_color, _x, _opacity, _y + 1, _height - 2);
			drawVerticalLine(_color, _x + _width - 1, _opacity, _y + 1, _height - 2);
		}
	}

	public static void drawHorizontalLine(int i, int j, int k, int l) {
		if (i < topY || i >= bottomY) {
			return;
		}
		if (l < topX) {
			k -= topX - l;
			l = topX;
		}
		if (l + k > bottomX) {
			k = bottomX - l;
		}
		int i1 = l + i * width;
		for (int j1 = 0; j1 < k; j1++) {
			pixels[i1 + j1] = j;
		}

	}

	private static void drawHorizontalLine(int _color, int _width, int _y, int _opacity, int _x) {
		if (_y < topY || _y >= bottomY) {
			return;
		}
		if (_x < topX) {
			_width -= topX - _x;
			_x = topX;
		}
		if (_x + _width > bottomX) {
			_width = bottomX - _x;
		}
		int j1 = 256 - _opacity;
		int k1 = (_color >> 16 & 0xff) * _opacity;
		int l1 = (_color >> 8 & 0xff) * _opacity;
		int i2 = (_color & 0xff) * _opacity;
		int i3 = _x + _y * width;
		for (int j3 = 0; j3 < _width; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	public static void drawVerticalLine(int _y, int _color, int _height, int _x) {
		if (_x < topX || _x >= bottomX) {
			return;
		}
		if (_y < topY) {
			_height -= topY - _y;
			_y = topY;
		}
		if (_y + _height > bottomY) {
			_height = bottomY - _y;
		}
		int j1 = _x + _y * width;
		for (int k1 = 0; k1 < _height; k1++) {
			pixels[j1 + k1 * width] = _color;
		}

	}

	private static void drawVerticalLine(int i, int j, int k, int l, int i1) {
		if (j < topX || j >= bottomX) {
			return;
		}
		if (l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if (l + i1 > bottomY) {
			i1 = bottomY - l;
		}
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for (int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}

	}

	DrawingArea() {
	}

	public static int pixels[];
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int topX;
	public static int bottomX;
	public static int centerX;
	public static int centerY;
	public static int anInt1387;

}
