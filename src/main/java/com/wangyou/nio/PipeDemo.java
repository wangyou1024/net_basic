package com.wangyou.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author 王游
 * @date 2021/3/11 20:53
 */
public class PipeDemo {
    public static void main(String[] args) throws IOException {
        // 1) 获取管道
        Pipe pipe = Pipe.open();

        // 2) 将缓冲区中的数据写入管道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        buffer.put("通过单向管道发送数据".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);

        // 3) 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer = ByteBuffer.allocate(1024);
        int len = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));

        // 4) 关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
