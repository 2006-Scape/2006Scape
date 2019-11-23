package redone.net;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import redone.game.players.Client;

public class ConnectionHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageReceived(IoSession arg0, Object arg1) throws Exception {
		if (arg0.getAttachment() != null) {
			Client plr = (Client) arg0.getAttachment();
			plr.queueMessage((Packet) arg1);
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		if (arg0.getAttachment() != null) {
			Client plr = (Client) arg0.getAttachment();
			plr.disconnected = true;
		}
		HostList.getHostList().remove(arg0);
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
		if (!HostList.getHostList().add(arg0)) {
			arg0.close();
		} else {
			arg0.setAttribute("inList", Boolean.TRUE);
		}
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		arg0.close();
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
		arg0.setIdleTime(IdleStatus.BOTH_IDLE, 60);
		arg0.getFilterChain().addLast("protocolFilter",
				new ProtocolCodecFilter(new CodecFactory()));
	}

}
