package com.wangyou.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 王游
 * @date 2021/3/11 17:13
 */
public class ServerDemo {
    public static String copy = "src\\main\\java\\com\\wangyou\\nio\\copy.txt";

    public static void main(String[] args) throws IOException {
        // 1) 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 2) 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));

        // 3) 获取客户端连接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 4) 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 5) 接收客户端的数据，并保存到本地
        FileChannel outChannel = FileChannel.open(Paths.get(copy), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        while (socketChannel.read(buffer) != -1){
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }

        // 6) 反馈信息
        buffer.put("receive success".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.shutdownOutput();

        // 7) 关闭通道
        socketChannel.close();
        outChannel.close();
        serverSocketChannel.close();
    }
}
