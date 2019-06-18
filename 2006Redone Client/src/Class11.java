/* Class11 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

final class Class11
{
    private static byte[] aByteArray210
	= { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1,
	    1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
	    1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	    2, 2, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    private Stream aClass3_Sub12_211 = new Stream(null);
    private int[] anIntArray212;
    int anInt213;
    private int[] anIntArray214;
    private long aLong215;
    int[] anIntArray216;
    private int[] anIntArray217;
    private int anInt218;
    
    final void method520(int i) {
	int i_0_ = aClass3_Sub12_211.method428();
	anIntArray216[i] += i_0_;
    }
    
    final boolean method521() {
	if (aClass3_Sub12_211.currentOffset >= 0)
	    return false;
	return true;
    }
    
    final void method522(int i) {
	anIntArray214[i] = aClass3_Sub12_211.currentOffset;
    }
    
    final void method523() {
	aClass3_Sub12_211.buffer = null;
	anIntArray217 = null;
	anIntArray214 = null;
	anIntArray216 = null;
	anIntArray212 = null;
    }
    
    private final int method524(int i) {
	int i_1_ = (aClass3_Sub12_211.buffer
		    [aClass3_Sub12_211.currentOffset]);
	if (i_1_ < 0) {
	    i_1_ &= 0xff;
	    anIntArray212[i] = i_1_;
	    aClass3_Sub12_211.currentOffset++;
	} else
	    i_1_ = anIntArray212[i];
	if (i_1_ == 240 || i_1_ == 247) {
	    int i_2_ = aClass3_Sub12_211.method428();
	    if (i_1_ == 247 && i_2_ > 0) {
		int i_3_ = ((aClass3_Sub12_211.buffer
			     [aClass3_Sub12_211.currentOffset])
			    & 0xff);
		if (i_3_ >= 241 && i_3_ <= 243 || i_3_ == 246 || i_3_ == 248
		    || i_3_ >= 250 && i_3_ <= 252 || i_3_ == 254) {
		    aClass3_Sub12_211.currentOffset++;
		    anIntArray212[i] = i_3_;
		    return method535(i, i_3_);
		}
	    }
	    aClass3_Sub12_211.currentOffset += i_2_;
	    return 0;
	}
	return method535(i, i_1_);
    }
    
    final void method525(byte[] is) {
	aClass3_Sub12_211.buffer = is;
	aClass3_Sub12_211.currentOffset = 10;
	int i = aClass3_Sub12_211.readUnsignedWord();
	anInt213 = aClass3_Sub12_211.readUnsignedWord();
	anInt218 = 500000;
	anIntArray217 = new int[i];
	int i_4_ = 0;
	while (i_4_ < i) {
	    int i_5_ = aClass3_Sub12_211.readDWord();
	    int i_6_ = aClass3_Sub12_211.readDWord();
	    if (i_5_ == 1297379947) {
		anIntArray217[i_4_]
		    = aClass3_Sub12_211.currentOffset;
		i_4_++;
	    }
	    aClass3_Sub12_211.currentOffset += i_6_;
	}
	anIntArray214 = anIntArray217.clone();
	anIntArray216 = new int[i];
	anIntArray212 = new int[i];
    }
    
    final void method526(int i) {
	aClass3_Sub12_211.currentOffset = anIntArray214[i];
    }
    
    final boolean method527() {
	if (aClass3_Sub12_211.buffer == null)
	    return false;
	return true;
    }
    
    final void method528() {
	aClass3_Sub12_211.currentOffset = -1;
    }
    
    final int method529(int i) {
	int i_7_ = method524(i);
	return i_7_;
    }
    
    public static void reset() {
	aByteArray210 = null;
    }
    
    final boolean method531() {
	int i = anIntArray214.length;
	for (int i_8_ = 0; i_8_ < i; i_8_++) {
	    if (anIntArray214[i_8_] >= 0)
		return false;
	}
	return true;
    }
    
    final long method532(int i) {
	return aLong215 + (long) i * (long) anInt218;
    }
    
    final int method533() {
	return anIntArray214.length;
    }
    
    final void method534(long l) {
	aLong215 = l;
	int i = anIntArray214.length;
	for (int i_9_ = 0; i_9_ < i; i_9_++) {
	    anIntArray216[i_9_] = 0;
	    anIntArray212[i_9_] = 0;
	    aClass3_Sub12_211.currentOffset = anIntArray217[i_9_];
	    method520(i_9_);
	    anIntArray214[i_9_] = aClass3_Sub12_211.currentOffset;
	}
    }
    
    private final int method535(int i, int i_10_) {
	if (i_10_ == 255) {
	    int i_11_ = aClass3_Sub12_211.readUnsignedByte();
	    int i_12_ = aClass3_Sub12_211.method428();
	    if (i_11_ == 47) {
		aClass3_Sub12_211.currentOffset += i_12_;
		return 1;
	    }
	    if (i_11_ == 81) {
		int i_13_ = aClass3_Sub12_211.read3Bytes();
		i_12_ -= 3;
		int i_14_ = anIntArray216[i];
		aLong215 += (long) i_14_ * (long) (anInt218 - i_13_);
		anInt218 = i_13_;
		aClass3_Sub12_211.currentOffset += i_12_;
		return 2;
	    }
	    aClass3_Sub12_211.currentOffset += i_12_;
	    return 3;
	}
	byte i_15_ = aByteArray210[i_10_ - 128];
	int i_16_ = i_10_;
	if (i_15_ >= 1)
	    i_16_ |= aClass3_Sub12_211.readUnsignedByte() << 8;
	if (i_15_ >= 2)
	    i_16_ |= aClass3_Sub12_211.readUnsignedByte() << 16;
	return i_16_;
    }
    
    final int method536() {
	int i = anIntArray214.length;
	int i_17_ = -1;
	int i_18_ = 2147483647;
	for (int i_19_ = 0; i_19_ < i; i_19_++) {
	    if (anIntArray214[i_19_] >= 0
		&& anIntArray216[i_19_] < i_18_) {
		i_17_ = i_19_;
		i_18_ = anIntArray216[i_19_];
	    }
	}
	return i_17_;
    }
    
    public Class11() {
	/* empty */
    }
}
