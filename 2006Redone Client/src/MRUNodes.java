// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class MRUNodes {

	public MRUNodes(int i) {
		emptyNodeSub = new NodeSub();
		nodeSubList = new NodeSubList();
		initialCount = i;
		spaceLeft = i;
		nodeCache = new NodeCache();
	}

	public NodeSub insertFromCache(long l) {
		NodeSub nodeSub = (NodeSub) nodeCache.findNodeByID(l);
		if (nodeSub != null) {
			nodeSubList.insertHead(nodeSub);
		}
		return nodeSub;
	}

	public void removeFromCache(NodeSub nodeSub, long l) {
		try {
			if (spaceLeft == 0) {
				NodeSub nodeSub_1 = nodeSubList.popTail();
				nodeSub_1.unlink();
				nodeSub_1.unlinkSub();
				if (nodeSub_1 == emptyNodeSub) {
					NodeSub nodeSub_2 = nodeSubList.popTail();
					nodeSub_2.unlink();
					nodeSub_2.unlinkSub();
				}
			} else {
				spaceLeft--;
			}
			nodeCache.removeFromCache(nodeSub, l);
			nodeSubList.insertHead(nodeSub);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47547, " + nodeSub + ", " + l + ", " + (byte) 2 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void unlinkAll() {
		do {
			NodeSub nodeSub = nodeSubList.popTail();
			if (nodeSub != null) {
				nodeSub.unlink();
				nodeSub.unlinkSub();
			} else {
				spaceLeft = initialCount;
				return;
			}
		} while (true);
	}

	private final NodeSub emptyNodeSub;
	private final int initialCount;
	private int spaceLeft;
	private final NodeCache nodeCache;
	private final NodeSubList nodeSubList;
}
