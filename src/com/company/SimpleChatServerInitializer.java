package com.company;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(10240 * 5, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new ByteToStringDecode());
        pipeline.addLast("encoder", new StringToByteEncode());
        pipeline.addLast("handler", new SimpleChatServerHandler());
        System.out.println("SimpleChatClient: " + ch.remoteAddress() + "连接上\n");
    }
}
