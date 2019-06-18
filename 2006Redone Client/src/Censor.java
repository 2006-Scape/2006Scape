// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Censor {

	public static void loadConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getDataForName("fragmentsenc.txt"));
		Stream stream_1 = new Stream(streamLoader.getDataForName("badenc.txt"));
		Stream stream_2 = new Stream(streamLoader.getDataForName("domainenc.txt"));
		Stream stream_3 = new Stream(streamLoader.getDataForName("tldlist.txt"));
		readValues(stream, stream_1, stream_2, stream_3);
	}

	private static void readValues(Stream stream, Stream stream_1, Stream stream_2, Stream stream_3) {
		readBadEnc(stream_1);
		readDomainEnc(stream_2);
		readFragmentsEnc(stream);
		readTldList(stream_3);
	}

	private static void readTldList(Stream stream) {
		int i = stream.readDWord();
		aCharArrayArray624 = new char[i][];
		anIntArray625 = new int[i];
		for (int j = 0; j < i; j++) {
			anIntArray625[j] = stream.readUnsignedByte();
			char ac[] = new char[stream.readUnsignedByte()];
			for (int k = 0; k < ac.length; k++) {
				ac[k] = (char) stream.readUnsignedByte();
			}

			aCharArrayArray624[j] = ac;
		}

	}

	private static void readBadEnc(Stream stream) {
		int j = stream.readDWord();
		aCharArrayArray621 = new char[j][];
		aByteArrayArrayArray622 = new byte[j][][];
		method493(stream, aCharArrayArray621, aByteArrayArrayArray622);
	}

	private static void readDomainEnc(Stream stream) {
		int i = stream.readDWord();
		aCharArrayArray623 = new char[i][];
		method494(aCharArrayArray623, stream);
	}

	private static void readFragmentsEnc(Stream stream) {
		anIntArray620 = new int[stream.readDWord()];
		for (int i = 0; i < anIntArray620.length; i++) {
			anIntArray620[i] = stream.readUnsignedWord();
		}
	}

	private static void method493(Stream stream, char ac[][], byte abyte0[][][]) {
		for (int j = 0; j < ac.length; j++) {
			char ac1[] = new char[stream.readUnsignedByte()];
			for (int k = 0; k < ac1.length; k++) {
				ac1[k] = (char) stream.readUnsignedByte();
			}

			ac[j] = ac1;
			byte abyte1[][] = new byte[stream.readUnsignedByte()][2];
			for (int l = 0; l < abyte1.length; l++) {
				abyte1[l][0] = (byte) stream.readUnsignedByte();
				abyte1[l][1] = (byte) stream.readUnsignedByte();
			}

			if (abyte1.length > 0) {
				abyte0[j] = abyte1;
			}
		}

	}

	private static void method494(char ac[][], Stream stream) {
		for (int j = 0; j < ac.length; j++) {
			char ac1[] = new char[stream.readUnsignedByte()];
			for (int k = 0; k < ac1.length; k++) {
				ac1[k] = (char) stream.readUnsignedByte();
			}

			ac[j] = ac1;
		}

	}

	private static void method495(char ac[]) {
		int i = 0;
		for (int j = 0; j < ac.length; j++) {
			if (method496(ac[j])) {
				ac[i] = ac[j];
			} else {
				ac[i] = ' ';
			}
			if (i == 0 || ac[i] != ' ' || ac[i - 1] != ' ') {
				i++;
			}
		}
		for (int k = i; k < ac.length; k++) {
			ac[k] = ' ';
		}

	}

	private static boolean method496(char c) {
		return c >= ' ' && c <= '\177' || c == ' ' || c == '\n' || c == '\t' || c == '\243' || c == '\u20AC';
	}

	public static String doCensor(String s) {
		System.currentTimeMillis();
		char ac[] = s.toCharArray();
		method495(ac);
		String s1 = new String(ac).trim();
		ac = s1.toLowerCase().toCharArray();
		String s2 = s1.toLowerCase();
		method505(ac);
		method500(ac);
		method501(ac);
		method514(ac);
		for (String exception : exceptions) {
			for (int k = -1; (k = s2.indexOf(exception, k + 1)) != -1;) {
				char ac1[] = exception.toCharArray();
				System.arraycopy(ac1, 0, ac, k, ac1.length);

			}

		}
		method498(s1.toCharArray(), ac);
		method499(ac);
		System.currentTimeMillis();
		return s; // xxx chat filter, return s to avoid new String(ac).trim()
	}

	private static void method498(char ac[], char ac1[]) {
		for (int j = 0; j < ac.length; j++) {
			if (ac1[j] != '*' && isUpperCaseLetter(ac[j])) {
				ac1[j] = ac[j];
			}
		}
	}

	private static void method499(char ac[]) {
		boolean flag = true;
		for (int j = 0; j < ac.length; j++) {
			char c = ac[j];
			if (isLetter(c)) {
				if (flag) {
					if (isLowerCaseLetter(c)) {
						flag = false;
					}
				} else if (isUpperCaseLetter(c)) {
					ac[j] = (char) (c + 97 - 65);
				}
			} else {
				flag = true;
			}
		}
	}

	private static void method500(char ac[]) {
		for (int i = 0; i < 2; i++) {
			for (int j = aCharArrayArray621.length - 1; j >= 0; j--) {
				method509(aByteArrayArrayArray622[j], ac, aCharArrayArray621[j]);
			}

		}
	}

	private static void method501(char ac[]) {
		char ac1[] = ac.clone();
		char ac2[] = {'(', 'a', ')'};
		method509(null, ac1, ac2);
		char ac3[] = ac.clone();
		char ac4[] = {'d', 'o', 't'};
		method509(null, ac3, ac4);
		for (int i = aCharArrayArray623.length - 1; i >= 0; i--) {
			method502(ac, aCharArrayArray623[i], ac3, ac1);
		}
	}

	private static void method502(char ac[], char ac1[], char ac2[], char ac3[]) {
		if (ac1.length > ac.length) {
			return;
		}
		int j;
		for (int k = 0; k <= ac.length - ac1.length; k += j) {
			int l = k;
			int i1 = 0;
			j = 1;
			while (l < ac.length) {
				int j1;
				char c = ac[l];
				char c1 = '\0';
				if (l + 1 < ac.length) {
					c1 = ac[l + 1];
				}
				if (i1 < ac1.length && (j1 = method511(c, ac1[i1], c1)) > 0) {
					l += j1;
					i1++;
					continue;
				}
				if (i1 == 0) {
					break;
				}
				if ((j1 = method511(c, ac1[i1 - 1], c1)) > 0) {
					l += j1;
					if (i1 == 1) {
						j++;
					}
					continue;
				}
				if (i1 >= ac1.length || !method517(c)) {
					break;
				}
				l++;
			}
			if (i1 >= ac1.length) {
				boolean flag1 = false;
				int k1 = method503(ac, ac3, k);
				int l1 = method504(ac2, l - 1, ac);
				if (k1 > 2 || l1 > 2) {
					flag1 = true;
				}
				if (flag1) {
					for (int i2 = k; i2 < l; i2++) {
						ac[i2] = '*';
					}

				}
			}
		}

	}

	private static int method503(char ac[], char ac1[], int j) {
		if (j == 0) {
			return 2;
		}
		for (int k = j - 1; k >= 0; k--) {
			if (!method517(ac[k])) {
				break;
			}
			if (ac[k] == '@') {
				return 3;
			}
		}

		int l = 0;
		for (int i1 = j - 1; i1 >= 0; i1--) {
			if (!method517(ac1[i1])) {
				break;
			}
			if (ac1[i1] == '*') {
				l++;
			}
		}

		if (l >= 3) {
			return 4;
		}
		return !method517(ac[j - 1]) ? 0 : 1;
	}

	private static int method504(char ac[], int i, char ac1[]) {
		if (i + 1 == ac1.length) {
			return 2;
		}
		for (int j = i + 1; j < ac1.length; j++) {
			if (!method517(ac1[j])) {
				break;
			}
			if (ac1[j] == '.' || ac1[j] == ',') {
				return 3;
			}
		}
		int k = 0;
		for (int l = i + 1; l < ac1.length; l++) {
			if (!method517(ac[l])) {
				break;
			}
			if (ac[l] == '*') {
				k++;
			}
		}

		if (k >= 3) {
			return 4;
		}
		return !method517(ac1[i + 1]) ? 0 : 1;
	}

	private static void method505(char ac[]) {
		char ac1[] = ac.clone();
		char ac2[] = {'d', 'o', 't'};
		method509(null, ac1, ac2);
		char ac3[] = ac.clone();
		char ac4[] = {'s', 'l', 'a', 's', 'h'};
		method509(null, ac3, ac4);
		for (int i = 0; i < aCharArrayArray624.length; i++) {
			method506(ac3, aCharArrayArray624[i], anIntArray625[i], ac1, ac);
		}

	}

	private static void method506(char ac[], char ac1[], int i, char ac2[], char ac3[]) {
		if (ac1.length > ac3.length) {
			return;
		}
		int j;
		for (int k = 0; k <= ac3.length - ac1.length; k += j) {
			int l = k;
			int i1 = 0;
			j = 1;
			while (l < ac3.length) {
				int j1;
				char c = ac3[l];
				char c1 = '\0';
				if (l + 1 < ac3.length) {
					c1 = ac3[l + 1];
				}
				if (i1 < ac1.length && (j1 = method511(c, ac1[i1], c1)) > 0) {
					l += j1;
					i1++;
					continue;
				}
				if (i1 == 0) {
					break;
				}
				if ((j1 = method511(c, ac1[i1 - 1], c1)) > 0) {
					l += j1;
					if (i1 == 1) {
						j++;
					}
					continue;
				}
				if (i1 >= ac1.length || !method517(c)) {
					break;
				}
				l++;
			}
			if (i1 >= ac1.length) {
				boolean flag1 = false;
				int k1 = method507(ac3, k, ac2);
				int l1 = method508(ac3, ac, l - 1);
				if (i == 1 && k1 > 0 && l1 > 0) {
					flag1 = true;
				}
				if (i == 2 && (k1 > 2 && l1 > 0 || k1 > 0 && l1 > 2)) {
					flag1 = true;
				}
				if (i == 3 && k1 > 0 && l1 > 2) {
					flag1 = true;
				}
				if (flag1) {
					int i2 = k;
					int j2 = l - 1;
					if (k1 > 2) {
						if (k1 == 4) {
							boolean flag2 = false;
							for (int l2 = i2 - 1; l2 >= 0; l2--) {
								if (flag2) {
									if (ac2[l2] != '*') {
										break;
									}
									i2 = l2;
								} else if (ac2[l2] == '*') {
									i2 = l2;
									flag2 = true;
								}
							}

						}
						boolean flag3 = false;
						for (int i3 = i2 - 1; i3 >= 0; i3--) {
							if (flag3) {
								if (method517(ac3[i3])) {
									break;
								}
								i2 = i3;
							} else if (!method517(ac3[i3])) {
								flag3 = true;
								i2 = i3;
							}
						}

					}
					if (l1 > 2) {
						if (l1 == 4) {
							boolean flag4 = false;
							for (int j3 = j2 + 1; j3 < ac3.length; j3++) {
								if (flag4) {
									if (ac[j3] != '*') {
										break;
									}
									j2 = j3;
								} else if (ac[j3] == '*') {
									j2 = j3;
									flag4 = true;
								}
							}

						}
						boolean flag5 = false;
						for (int k3 = j2 + 1; k3 < ac3.length; k3++) {
							if (flag5) {
								if (method517(ac3[k3])) {
									break;
								}
								j2 = k3;
							} else if (!method517(ac3[k3])) {
								flag5 = true;
								j2 = k3;
							}
						}

					}
					for (int k2 = i2; k2 <= j2; k2++) {
						ac3[k2] = '*';
					}

				}
			}
		}
	}

	private static int method507(char ac[], int j, char ac1[]) {
		if (j == 0) {
			return 2;
		}
		for (int k = j - 1; k >= 0; k--) {
			if (!method517(ac[k])) {
				break;
			}
			if (ac[k] == ',' || ac[k] == '.') {
				return 3;
			}
		}

		int l = 0;
		for (int i1 = j - 1; i1 >= 0; i1--) {
			if (!method517(ac1[i1])) {
				break;
			}
			if (ac1[i1] == '*') {
				l++;
			}
		}
		if (l >= 3) {
			return 4;
		}
		return !method517(ac[j - 1]) ? 0 : 1;
	}

	private static int method508(char ac[], char ac1[], int i) {
		if (i + 1 == ac.length) {
			return 2;
		}
		for (int j = i + 1; j < ac.length; j++) {
			if (!method517(ac[j])) {
				break;
			}
			if (ac[j] == '\\' || ac[j] == '/') {
				return 3;
			}
		}

		int k = 0;
		for (int l = i + 1; l < ac.length; l++) {
			if (!method517(ac1[l])) {
				break;
			}
			if (ac1[l] == '*') {
				k++;
			}
		}

		if (k >= 5) {
			return 4;
		}
		return !method517(ac[i + 1]) ? 0 : 1;
	}

	private static void method509(byte abyte0[][], char ac[], char ac1[]) {
		if (ac1.length > ac.length) {
			return;
		}
		int j;
		for (int k = 0; k <= ac.length - ac1.length; k += j) {
			int l = k;
			int i1 = 0;
			int j1 = 0;
			j = 1;
			boolean flag1 = false;
			boolean flag2 = false;
			boolean flag3 = false;
			while (l < ac.length && (!flag2 || !flag3)) {
				int k1;
				char c = ac[l];
				char c2 = '\0';
				if (l + 1 < ac.length) {
					c2 = ac[l + 1];
				}
				if (i1 < ac1.length && (k1 = method512(c2, c, ac1[i1])) > 0) {
					if (k1 == 1 && isDigit(c)) {
						flag2 = true;
					}
					if (k1 == 2 && (isDigit(c) || isDigit(c2))) {
						flag2 = true;
					}
					l += k1;
					i1++;
					continue;
				}
				if (i1 == 0) {
					break;
				}
				if ((k1 = method512(c2, c, ac1[i1 - 1])) > 0) {
					l += k1;
					if (i1 == 1) {
						j++;
					}
					continue;
				}
				if (i1 >= ac1.length || !method518(c)) {
					break;
				}
				if (method517(c) && c != '\'') {
					flag1 = true;
				}
				if (isDigit(c)) {
					flag3 = true;
				}
				l++;
				if (++j1 * 100 / (l - k) > 90) {
					break;
				}
			}
			if (i1 >= ac1.length && (!flag2 || !flag3)) {
				boolean flag4 = true;
				if (!flag1) {
					char c1 = ' ';
					if (k - 1 >= 0) {
						c1 = ac[k - 1];
					}
					char c3 = ' ';
					if (l < ac.length) {
						c3 = ac[l];
					}
					byte byte0 = method513(c1);
					byte byte1 = method513(c3);
					if (abyte0 != null && method510(byte0, abyte0, byte1)) {
						flag4 = false;
					}
				} else {
					boolean flag5 = false;
					boolean flag6 = false;
					if (k - 1 < 0 || method517(ac[k - 1]) && ac[k - 1] != '\'') {
						flag5 = true;
					}
					if (l >= ac.length || method517(ac[l]) && ac[l] != '\'') {
						flag6 = true;
					}
					if (!flag5 || !flag6) {
						boolean flag7 = false;
						int k2 = k - 2;
						if (flag5) {
							k2 = k;
						}
						for (; !flag7 && k2 < l; k2++) {
							if (k2 >= 0 && (!method517(ac[k2]) || ac[k2] == '\'')) {
								char ac2[] = new char[3];
								int j3;
								for (j3 = 0; j3 < 3; j3++) {
									if (k2 + j3 >= ac.length || method517(ac[k2 + j3]) && ac[k2 + j3] != '\'') {
										break;
									}
									ac2[j3] = ac[k2 + j3];
								}

								boolean flag8 = true;
								if (j3 == 0) {
									flag8 = false;
								}
								if (j3 < 3 && k2 - 1 >= 0 && (!method517(ac[k2 - 1]) || ac[k2 - 1] == '\'')) {
									flag8 = false;
								}
								if (flag8 && !method523(ac2)) {
									flag7 = true;
								}
							}
						}

						if (!flag7) {
							flag4 = false;
						}
					}
				}
				if (flag4) {
					int l1 = 0;
					int i2 = 0;
					int j2 = -1;
					for (int l2 = k; l2 < l; l2++) {
						if (isDigit(ac[l2])) {
							l1++;
						} else if (isLetter(ac[l2])) {
							i2++;
							j2 = l2;
						}
					}

					if (j2 > -1) {
						l1 -= l - 1 - j2;
					}
					if (l1 <= i2) {
						for (int i3 = k; i3 < l; i3++) {
							ac[i3] = '*';
						}

					} else {
						j = 1;
					}
				}
			}
		}

	}

	private static boolean method510(byte byte0, byte abyte0[][], byte byte2) {
		int i = 0;
		if (abyte0[i][0] == byte0 && abyte0[i][1] == byte2) {
			return true;
		}
		int j = abyte0.length - 1;
		if (abyte0[j][0] == byte0 && abyte0[j][1] == byte2) {
			return true;
		}
		do {
			int k = (i + j) / 2;
			if (abyte0[k][0] == byte0 && abyte0[k][1] == byte2) {
				return true;
			}
			if (byte0 < abyte0[k][0] || byte0 == abyte0[k][0] && byte2 < abyte0[k][1]) {
				j = k;
			} else {
				i = k;
			}
		} while (i != j && i + 1 != j);
		return false;
	}

	private static int method511(char c, char c1, char c2) {
		if (c1 == c) {
			return 1;
		}
		if (c1 == 'o' && c == '0') {
			return 1;
		}
		if (c1 == 'o' && c == '(' && c2 == ')') {
			return 2;
		}
		if (c1 == 'c' && (c == '(' || c == '<' || c == '[')) {
			return 1;
		}
		if (c1 == 'e' && c == '\u20AC') {
			return 1;
		}
		if (c1 == 's' && c == '$') {
			return 1;
		}
		return c1 != 'l' || c != 'i' ? 0 : 1;
	}

	private static int method512(char c, char c1, char c2) {
		if (c2 == c1) {
			return 1;
		}
		if (c2 >= 'a' && c2 <= 'm') {
			if (c2 == 'a') {
				if (c1 == '4' || c1 == '@' || c1 == '^') {
					return 1;
				}
				return c1 != '/' || c != '\\' ? 0 : 2;
			}
			if (c2 == 'b') {
				if (c1 == '6' || c1 == '8') {
					return 1;
				}
				return (c1 != '1' || c != '3') && (c1 != 'i' || c != '3') ? 0 : 2;
			}
			if (c2 == 'c') {
				return c1 != '(' && c1 != '<' && c1 != '{' && c1 != '[' ? 0 : 1;
			}
			if (c2 == 'd') {
				return (c1 != '[' || c != ')') && (c1 != 'i' || c != ')') ? 0 : 2;
			}
			if (c2 == 'e') {
				return c1 != '3' && c1 != '\u20AC' ? 0 : 1;
			}
			if (c2 == 'f') {
				if (c1 == 'p' && c == 'h') {
					return 2;
				}
				return c1 != '\243' ? 0 : 1;
			}
			if (c2 == 'g') {
				return c1 != '9' && c1 != '6' && c1 != 'q' ? 0 : 1;
			}
			if (c2 == 'h') {
				return c1 != '#' ? 0 : 1;
			}
			if (c2 == 'i') {
				return c1 != 'y' && c1 != 'l' && c1 != 'j' && c1 != '1' && c1 != '!' && c1 != ':' && c1 != ';' && c1 != '|' ? 0 : 1;
			}
			if (c2 == 'j') {
				return 0;
			}
			if (c2 == 'k') {
				return 0;
			}
			if (c2 == 'l') {
				return c1 != '1' && c1 != '|' && c1 != 'i' ? 0 : 1;
			}
			if (c2 == 'm') {
				return 0;
			}
		}
		if (c2 >= 'n' && c2 <= 'z') {
			if (c2 == 'n') {
				return 0;
			}
			if (c2 == 'o') {
				if (c1 == '0' || c1 == '*') {
					return 1;
				}
				return (c1 != '(' || c != ')') && (c1 != '[' || c != ']') && (c1 != '{' || c != '}') && (c1 != '<' || c != '>') ? 0 : 2;
			}
			if (c2 == 'p') {
				return 0;
			}
			if (c2 == 'q') {
				return 0;
			}
			if (c2 == 'r') {
				return 0;
			}
			if (c2 == 's') {
				return c1 != '5' && c1 != 'z' && c1 != '$' && c1 != '2' ? 0 : 1;
			}
			if (c2 == 't') {
				return c1 != '7' && c1 != '+' ? 0 : 1;
			}
			if (c2 == 'u') {
				if (c1 == 'v') {
					return 1;
				}
				return (c1 != '\\' || c != '/') && (c1 != '\\' || c != '|') && (c1 != '|' || c != '/') ? 0 : 2;
			}
			if (c2 == 'v') {
				return (c1 != '\\' || c != '/') && (c1 != '\\' || c != '|') && (c1 != '|' || c != '/') ? 0 : 2;
			}
			if (c2 == 'w') {
				return c1 != 'v' || c != 'v' ? 0 : 2;
			}
			if (c2 == 'x') {
				return (c1 != ')' || c != '(') && (c1 != '}' || c != '{') && (c1 != ']' || c != '[') && (c1 != '>' || c != '<') ? 0 : 2;
			}
			if (c2 == 'y') {
				return 0;
			}
			if (c2 == 'z') {
				return 0;
			}
		}
		if (c2 >= '0' && c2 <= '9') {
			if (c2 == '0') {
				if (c1 == 'o' || c1 == 'O') {
					return 1;
				}
				return (c1 != '(' || c != ')') && (c1 != '{' || c != '}') && (c1 != '[' || c != ']') ? 0 : 2;
			}
			if (c2 == '1') {
				return c1 != 'l' ? 0 : 1;
			} else {
				return 0;
			}
		}
		if (c2 == ',') {
			return c1 != '.' ? 0 : 1;
		}
		if (c2 == '.') {
			return c1 != ',' ? 0 : 1;
		}
		if (c2 == '!') {
			return c1 != 'i' ? 0 : 1;
		} else {
			return 0;
		}
	}

	private static byte method513(char c) {
		if (c >= 'a' && c <= 'z') {
			return (byte) (c - 97 + 1);
		}
		if (c == '\'') {
			return 28;
		}
		if (c >= '0' && c <= '9') {
			return (byte) (c - 48 + 29);
		} else {
			return 27;
		}
	}

	private static void method514(char ac[]) {
		int j;
		int k = 0;
		int l = 0;
		int i1 = 0;
		while ((j = method515(ac, k)) != -1) {
			boolean flag = false;
			for (int j1 = k; j1 >= 0 && j1 < j && !flag; j1++) {
				if (!method517(ac[j1]) && !method518(ac[j1])) {
					flag = true;
				}
			}

			if (flag) {
				l = 0;
			}
			if (l == 0) {
				i1 = j;
			}
			k = method516(ac, j);
			int k1 = 0;
			for (int l1 = j; l1 < k; l1++) {
				k1 = k1 * 10 + ac[l1] - 48;
			}

			if (k1 > 255 || k - j > 8) {
				l = 0;
			} else {
				l++;
			}
			if (l == 4) {
				for (int i2 = i1; i2 < k; i2++) {
					ac[i2] = '*';
				}

				l = 0;
			}
		}
	}

	private static int method515(char ac[], int i) {
		for (int k = i; k < ac.length && k >= 0; k++) {
			if (ac[k] >= '0' && ac[k] <= '9') {
				return k;
			}
		}

		return -1;
	}

	private static int method516(char ac[], int j) {
		for (int k = j; k < ac.length && k >= 0; k++) {
			if (ac[k] < '0' || ac[k] > '9') {
				return k;
			}
		}
		return ac.length;
	}

	private static boolean method517(char c) {
		return !isLetter(c) && !isDigit(c);
	}

	private static boolean method518(char c) {
		return c < 'a' || c > 'z' || c == 'v' || c == 'x' || c == 'j' || c == 'q' || c == 'z';
	}

	private static boolean isLetter(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	private static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private static boolean isLowerCaseLetter(char c) {
		return c >= 'a' && c <= 'z';
	}

	private static boolean isUpperCaseLetter(char c) {
		return c >= 'A' && c <= 'Z';
	}

	private static boolean method523(char ac[]) {
		boolean flag = true;
		for (int i = 0; i < ac.length; i++) {
			if (!isDigit(ac[i]) && ac[i] != 0) {
				flag = false;
			}
		}

		if (flag) {
			return true;
		}
		int j = method524(ac);
		int k = 0;
		int l = anIntArray620.length - 1;
		if (j == anIntArray620[k] || j == anIntArray620[l]) {
			return true;
		}
		do {
			int i1 = (k + l) / 2;
			if (j == anIntArray620[i1]) {
				return true;
			}
			if (j < anIntArray620[i1]) {
				l = i1;
			} else {
				k = i1;
			}
		} while (k != l && k + 1 != l);
		return false;
	}

	private static int method524(char ac[]) {
		if (ac.length > 6) {
			return 0;
		}
		int k = 0;
		for (int l = 0; l < ac.length; l++) {
			char c = ac[ac.length - l - 1];
			if (c >= 'a' && c <= 'z') {
				k = k * 38 + c - 97 + 1;
			} else if (c == '\'') {
				k = k * 38 + 27;
			} else if (c >= '0' && c <= '9') {
				k = k * 38 + c - 48 + 28;
			} else if (c != 0) {
				return 0;
			}
		}

		return k;
	}

	private static int[] anIntArray620;
	private static char[][] aCharArrayArray621;
	private static byte[][][] aByteArrayArrayArray622;
	private static char[][] aCharArrayArray623;
	private static char[][] aCharArrayArray624;
	private static int[] anIntArray625;
	private static final String[] exceptions = {"cook", "cook's", "cooks", "seeks", "sheet", "woop", "woops", "faq", "noob", "noobs"};

}
