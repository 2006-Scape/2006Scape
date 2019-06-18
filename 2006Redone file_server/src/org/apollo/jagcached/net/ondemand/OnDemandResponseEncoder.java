package org.apollo.jagcached.net.ondemand;


import org.apollo.jagcached.fs.FileDescriptor;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} for the 'on-demand' protocol.
 * @author Graham
 */
public final class OnDemandResponseEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof OnDemandResponse) {
			OnDemandResponse resp = (OnDemandResponse) msg;
			
			FileDescriptor fileDescriptor = resp.getFileDescriptor();
			int fileSize = resp.getFileSize();
			int chunkId = resp.getChunkId();
			ChannelBuffer chunkData = resp.getChunkData();
			
			ChannelBuffer buf = ChannelBuffers.buffer(6 + chunkData.readableBytes());
			buf.writeByte(fileDescriptor.getType() - 1);
			buf.writeShort(fileDescriptor.getFile());
			buf.writeShort(fileSize);
			buf.writeByte(chunkId);
			buf.writeBytes(chunkData);
			
			return buf;
		}
		return msg;
	}

}
