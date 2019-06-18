/* Class56_Sub1_Sub2 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

final class Class56_Sub1_Sub2 extends Class56_Sub1 implements Runnable
{
    private static Runnable_Impl1 aRunnable_Impl1_1852;
    private static boolean aBoolean1853;
    private static boolean aBoolean1854;
    private static int anInt1855;
    private static int anInt1856;
    private Class11 aClass11_1857 = new Class11();
    private static int[] anIntArray1858 = new int[256];
    
    private static final void method845(int i, int i_1_, int i_2_, int i_3_) {
    	if (anIntArray1858.length <= anInt1856) {
    		aRunnable_Impl1_1852.method10(anIntArray1858, anInt1856);
    		anInt1856 = 0;
    	}
    	anIntArray1858[anInt1856++] = i_1_ - anInt1855;
    	anInt1855 = i_1_;
    	anIntArray1858[anInt1856++] = i_2_ << 8 | i | i_3_ << 16;
    }
    
    private static final void method846() {
    	if (anInt1856 > 0) {
    		aRunnable_Impl1_1852.method10(anIntArray1858, anInt1856);
    		anInt1856 = 0;
    	}
    }
    
    final void method836(int i, int i_4_, int i_5_, long l) {
    	method845(i, (int) l, i_4_, i_5_);
    }
    
    final synchronized void method827(int i, byte[] is, int i_6_,
				      boolean bool) {
		aClass11_1857.method525(is);
		boolean bool_7_ = true;
		aBoolean1854 = bool;
		anInt1855 = 0;
		aRunnable_Impl1_1852.method12(false);
		method835(i_6_, i, (long) anInt1855);
		int i_8_ = aClass11_1857.method533();
		for (int i_9_ = 0; i_9_ < i_8_; i_9_++) {
			aClass11_1857.method526(i_9_);
			while (!aClass11_1857.method521()) {
				aClass11_1857.method520(i_9_);
				if (aClass11_1857.anIntArray216[i_9_] != 0) {
					bool_7_ = false;
					break;
				}
				method847(i_6_ ^ 0x70, 0L, i_9_);
			}
		    aClass11_1857.method522(i_9_);
		}
		if (bool_7_) {
			if (aBoolean1854)
				throw new RuntimeException();
			method838((long) anInt1855);
		    aClass11_1857.method523();
		}
		method846();
    }
    
    final synchronized void method831(int i) {
		method840(i, (long) anInt1855);
		aRunnable_Impl1_1852.method10(anIntArray1858, anInt1856);
		anInt1856 = 0;
    }
    
    final synchronized void method832(int i) {
	if (aClass11_1857.method527()) {
	    int i_11_ = anInt1855;
	    int i_12_ = -200;
	    int i_13_ = aRunnable_Impl1_1852.method14(-29810);
	    long l = ((long) (i_11_ - (i_13_ + i_12_))
		      * (long) (aClass11_1857.anInt213 * 1000));
	    for (;;) {
		int i_14_ = aClass11_1857.method536();
		int i_15_ = aClass11_1857.anIntArray216[i_14_];
		long l_16_ = aClass11_1857.method532(i_15_);
		if (l < l_16_)
		    break;
		while (i_15_
		       == aClass11_1857.anIntArray216[i_14_]) {
		    aClass11_1857.method526(i_14_);
		    method847(126, l_16_, i_14_);
		    if (aClass11_1857.method521()) {
			aClass11_1857.method522(i_14_);
			if (aClass11_1857.method531()) {
			    if (aBoolean1854)
				aClass11_1857.method534(l_16_);
			    else {
				method838
				    ((long) (int) (l_16_
						   / (long) ((aClass11_1857.anInt213)
							     * 1000)));
				aClass11_1857.method523();
				method846();
				return;
			    }
			}
			break;
		    }
		    aClass11_1857.method520(i_14_);
		    aClass11_1857.method522(i_14_);
		}
	    }
	    if (i > -90)
		aRunnable_Impl1_1852 = null;
	    method846();
	}
    }
    
    final synchronized void method833() {
		aRunnable_Impl1_1852.method12(false);
		method838((long) anInt1855);
		aRunnable_Impl1_1852.method10(anIntArray1858, anInt1856);
		anInt1856 = 0;
		aClass11_1857.method523();
    }
    
    final void method828() {
    	synchronized (this) {
    		aBoolean1853 = true;
    	}
    	for (;;) {
    		synchronized (this) {
    			if (!aBoolean1853)
    				break;
    		}
    		Game.sleep(20L);
    	}
    	aRunnable_Impl1_1852.method11(true);
    }
    
    public final void run() {
    	try {
    		for (;;) {
    			synchronized (this) {
    				if (aBoolean1853) {
    					aBoolean1853 = false;
    					break;
    				}
    				method832(-126);
    			}
    			Game.sleep(100L);
    		}
    	} catch (Exception exception) {
		}
    }
    
    private final void method847(int i, long l, int i_17_) {
    	int i_18_ = aClass11_1857.method529(i_17_);
    	if (i_18_ != 1) {
    		if ((i_18_ & 0x80) != 0) {
    			int i_19_  = (int) (l / (long) (aClass11_1857.anInt213 * 1000));
    			int i_20_ = i_18_ & 0xff;
    			int i_21_ = (i_18_ & 0xffe7d5) >> 16;
				int i_22_ = (i_18_ & 0xfff6) >> 8;
			    if (!method837(i_20_, i_22_, i_21_, (long) i_19_))
			    	method845(i_20_, i_19_, i_22_, i_21_);
    		}
    	} else
    		aClass11_1857.method528();
    }
    
    Class56_Sub1_Sub2(Runnable_Impl1 runnable_impl1) {
		aRunnable_Impl1_1852 = runnable_impl1;
		aRunnable_Impl1_1852.method15((byte) 96);
		aRunnable_Impl1_1852.method12(false);
		method838((long) anInt1855);
		aRunnable_Impl1_1852.method10(anIntArray1858, anInt1856);
		anInt1856 = 0;
		Thread thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		thread.setPriority(10);
    }
    
    final synchronized void method830(int i, int i_23_) {
    	method835(i_23_, i, (long) anInt1855);
    }
}
