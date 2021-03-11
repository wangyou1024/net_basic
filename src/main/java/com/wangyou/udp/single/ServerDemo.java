package com.wangyou.udp.single;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @author 王游
 * @date 2021/3/9 15:20
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建接收端
        int port = 10000;
        DatagramSocket datagramSocket = new DatagramSocket(port);

        // 2) 数据打包
        byte[] data = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);

        // 3) 接收数据
        datagramSocket.receive(datagramPacket);

        // 4) 获取数据，可以先获得有效长度
        int length = datagramPacket.getLength();
        System.out.println(new String(data, 0, length));

        // 5) 释放资源
        datagramSocket.close();
    }
}
