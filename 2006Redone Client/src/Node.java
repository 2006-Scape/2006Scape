// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Node {

	public final void unlink() {
		if (next == null) {
		} else {
			next.prev = prev;
			prev.next = next;
			prev = null;
			next = null;
		}
	}

	public Node() {
	}

	public long id;
	public Node prev;
	public Node next;
}
