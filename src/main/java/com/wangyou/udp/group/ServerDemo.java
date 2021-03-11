package com.wangyou.udp.group;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author 王游
 * @date 2021/3/9 19:43
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建组播接收端并
        int port = 10000;
        MulticastSocket multicastSocket = new MulticastSocket(port);

        // 2) 绑定组播地址，即把当前计算机加入到这一组中
        multicastSocket.joinGroup(InetAddress.getByName("224.0.1.0"));

        // 3) 创建接收数据包
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);

        // 4) 接收数据
        multicastSocket.receive(datagramPacket);
        byte[] data = datagramPacket.getData();
        System.out.println(new String(data, 0, datagramPacket.getLength()));

        // 5) 关闭资源
        multicastSocket.close();

    }
}
