package com.company;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {
 
	@Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        
        // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(102400, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new ByteToStringDecode());
        pipeline.addLast("encoder", new StringToByteEncode());
        pipeline.addLast("handler", new SimpleChatClientHandler());
    }
}