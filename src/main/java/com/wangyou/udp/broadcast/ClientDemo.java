package com.wangyou.udp.broadcast;

import java.io.IOException;
import java.net.*;

/**
 * @author 王游
 * @date 2021/3/9 20:37
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        String content = "hello world";
        byte[] bytes = content.getBytes();
        // 广播地址
        InetAddress address = InetAddress.getByName("255.255.255.255");
        int port = 10000;
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, address, port);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }
}
