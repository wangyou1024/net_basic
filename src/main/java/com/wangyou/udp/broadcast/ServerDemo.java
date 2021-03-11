package com.wangyou.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author 王游
 * @date 2021/3/9 21:43
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(10000);
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
        datagramSocket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        System.out.println(new String(data, 0, datagramPacket.getLength()));
    }
}
