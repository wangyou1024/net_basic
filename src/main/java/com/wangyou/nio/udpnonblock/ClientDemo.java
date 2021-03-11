package com.wangyou.nio.udpnonblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author 王游
 * @date 2021/3/11 20:40
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String str = scanner.next();
            buffer.put((new Date().toString() + ":\n" + str).getBytes());
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
            buffer.clear();
        }

        datagramChannel.close();
    }
}
