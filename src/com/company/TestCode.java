package com.company;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.io.UnsupportedEncodingException;

public class TestCode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // ByteBuf byteBuf = encode("中国");
        // String message = decode(byteBuf);
        // System.out.println("message = " + message);
        ByteBuf byteBuf = Unpooled.buffer(4);
        byteBuf.writeByte(1);
        byteBuf.writeByte(2);
        byteBuf.writeByte(3);
        byteBuf.writeByte(4);

        System.out.println(byteBuf.readByte());
        byteBuf.markReaderIndex();
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        byteBuf.resetReaderIndex();
        System.out.println(byteBuf.readByte());
    }

    public static ByteBuf encode(String message) throws UnsupportedEncodingException {
        byte[] prefixArray = SocketUtils.intToByte(0x90785634);
        byte[] midArray = SocketUtils.intToByte(0x00000000);

        byte[] buffer = ZLibUtils.compress(message.getBytes("UTF-8"));
        int size = buffer.length;
        byte[] array = SocketUtils.intToByte(size);
        byte[] sendContent = new byte[4 + 4 + 4 + size];
        System.arraycopy(prefixArray, 0, sendContent, 0, 4);
        System.arraycopy(midArray, 0, sendContent, 4, 4);
        System.arraycopy(array, 0, sendContent, 8, 4);
        System.arraycopy(buffer, 0, sendContent, 12, size);
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer(size + 12);
        byteBuf.writeBytes(sendContent);
        return byteBuf;
    }

    public static String decode(ByteBuf in) throws UnsupportedEncodingException {
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
        byte[] readSrcContent = new byte[size];
        in.readBytes(readSrcContent);
        byte[] buffer = ZLibUtils.decompress(readSrcContent);
        String msg = new String(buffer, "UTF-8");
        return msg;
    }
}
