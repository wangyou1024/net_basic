package com.wangyou.udp.group;

import java.io.IOException;
import java.net.*;

/**
 * @author 王游
 * @date 2021/3/9 19:39
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建发送端
        DatagramSocket datagramSocket = new DatagramSocket();

        // 2) 数据封装
        String content = "hello world";
        byte[] bytes = content.getBytes();
        InetAddress address = InetAddress.getByName("224.0.1.0");
        int post = 10000;
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, post);

        // 3) 发送
        datagramSocket.send(datagramPacket);

        // 4) 释放资源
        datagramSocket.close();
    }
}
