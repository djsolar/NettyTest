package com.company;

import io.netty.buffer.ByteBuf;

/**
 * Created by zhouyiran on 2016/12/22.
 * Action
 * Update on 2016/12/22 10:41
 */

public class SocketUtils {

    private static final boolean IS_NEW_COMPRESS_PROTOCOL_VERSION = true;

    /**
     * 把整型转化为一个四个字节的数组
     *
     * @param length
     * @return byte[]
     */
    public static byte[] intToByte(int length) {
        byte[] array = new byte[4];
        if (IS_NEW_COMPRESS_PROTOCOL_VERSION) {
            array[0] = (byte) (0xff & length);
            array[1] = (byte) ((0xff00 & length) >> 8);
            array[2] = (byte) ((0xff0000 & length) >> 16);
            array[3] = (byte) ((0xff000000 & length) >> 24);
        } else {
            array[3] = (byte) (0xff & length);
            array[2] = (byte) ((0xff00 & length) >> 8);
            array[1] = (byte) ((0xff0000 & length) >> 16);
            array[0] = (byte) ((0xff000000 & length) >> 24);
        }
        return array;
    }

    /**
     * 把字节数组转化为整型
     *
     * @param bytes 字节数组
     * @return int 长度
     */
    public static int byte2Int(byte[] bytes) {
        byte[] temp = new byte[4];
        byte b;
        int size = 0;
        if (IS_NEW_COMPRESS_PROTOCOL_VERSION) {
            for (int j = 0; j < 4; j++) {
                b = bytes[j];
                size += (b & 0xFF) << (8 * j);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                temp[3 - i] = bytes[i];
            }
            for (int j = 0; j < 4; j++) {
                b = temp[j];
                size += (b & 0xFF) << (8 * j);
            }
        }
        return size;
    }

}
