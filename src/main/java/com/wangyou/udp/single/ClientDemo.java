package com.wangyou.udp.single;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author 王游
 * @date 2021/3/9 15:01
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建发送端
        DatagramSocket datagramSocket = new DatagramSocket();

        // 2) 包装数据
        String s = "hello world";
        byte[] data = s.getBytes();
        // 目标地址
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 10000;
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, port);

        // 3) 发送数据
        datagramSocket.send(datagramPacket);

        // 4) 释放资源
        datagramSocket.close();

    }
}
