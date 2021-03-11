package com.wangyou.nio.tcpnonblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @author 王游
 * @date 2021/3/11 17:30
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        // 1) 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 2) 切换非阻塞模式
        socketChannel.configureBlocking(false);

        // 3) 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 4) 发送数据给服务端
        buffer.put(new Date().toString().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();

        // 5) 关闭通道
        socketChannel.close();
    }
}
