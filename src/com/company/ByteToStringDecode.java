package com.company;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ByteToStringDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 12)
            return;
        in.markReaderIndex();
        byte[] prefixArray = new byte[4];
        in.readBytes(prefixArray);
        int prefix = SocketUtils.byte2Int(prefixArray);
        byte[] middleArray = new byte[4];
        in.readBytes(middleArray);
        int midContent = SocketUtils.byte2Int(middleArray);
        System.out.println("prefix = " + prefix + ", midContent = " + midContent);
        byte[] lengthArray = new byte[4];
        in.readBytes(lengthArray);
        int size = SocketUtils.byte2Int(lengthArray);
        System.out.println("readableSize = " + in.readableBytes());
        System.out.println("size = " + size);

        if (in.readableBytes() < size) {
            in.resetReaderIndex();
            return;
        }
        byte[] readSrcContent = new byte[size];
        in.readBytes(readSrcContent);
        byte[] buffer = ZLibUtils.decompress(readSrcContent);
        String msg = new String(buffer, "UTF-8");
        System.out.println("msg = " + msg);
        out.add(msg);
    }
}
