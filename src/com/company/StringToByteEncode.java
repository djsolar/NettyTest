package com.company;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringToByteEncode extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        byte[] prefixArray = SocketUtils.intToByte(0x90785634);
        byte[] midArray = SocketUtils.intToByte(0x00000000);

        byte[] buffer = ZLibUtils.compress(msg.getBytes("UTF-8"));
        int size = buffer.length;
        byte[] array = SocketUtils.intToByte(size);
        byte[] sendContent = new byte[4 + 4 + 4 + size];
        System.arraycopy(prefixArray, 0, sendContent, 0, 4);
        System.arraycopy(midArray, 0, sendContent, 4, 4);
        System.arraycopy(array, 0, sendContent, 8, 4);
        System.arraycopy(buffer, 0, sendContent, 12, size);
        out.writeBytes(sendContent);
    }
}
