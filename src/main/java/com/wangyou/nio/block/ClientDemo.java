package com.wangyou.nio.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 王游
 * @date 2021/3/11 17:04
 */
public class ClientDemo {
    public static String something = "src\\main\\java\\com\\wangyou\\nio\\something.txt";

    public static void main(String[] args) throws IOException {
        // 1) 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 2) 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 3) 读取本地文件，并发送到服务端
        FileChannel inChannel = FileChannel.open(Paths.get(something), StandardOpenOption.READ);
        while (inChannel.read(buffer) != -1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.shutdownOutput();

        // 4) 接收服务端反馈
        int len = 0;
        while ((len = socketChannel.read(buffer)) != -1){
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        // 5) 关闭通道
        inChannel.close();
        socketChannel.close();
    }
}
