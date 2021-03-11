package com.wangyou.tcp.single;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 王游
 * @date 2021/3/9 21:55
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建Socket对象
        Socket socket = new Socket("127.0.0.1", 10000);

        // 2) 获取一个IO流开始写数据
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world".getBytes());

        // 3) 释放资源
        outputStream.close();
        socket.close();
    }
}
