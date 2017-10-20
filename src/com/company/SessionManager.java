package com.company;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionManager {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentMap<String, String> sessionMap = new ConcurrentHashMap<>();

    public void add(Channel channel) {
        channels.add(channel);
    }

    public void login(String mac, String id) {
        sessionMap.putIfAbsent(mac, id);
    }

    public void write(Channel channel, String message) {
        channel.write(message);
    }

    // 根据消息确定发送的通道
    public void write(String message) {

    }
}
