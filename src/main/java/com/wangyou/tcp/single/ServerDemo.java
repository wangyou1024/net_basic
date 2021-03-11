package com.wangyou.tcp.single;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 王游
 * @date 2021/3/10 10:30
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 1) 创建Socket对象
        int port = 10000;
        ServerSocket serverSocket = new ServerSocket(port);
        // 2) 等待客户端连接
        System.out.println("等待连接……");
        Socket accept = serverSocket.accept();
        // 3) 获得输入流对象
        InputStream inputStream = accept.getInputStream();
        int b;
        while ((b = inputStream.read()) != -1){
            System.out.println((char) b);
        }
        // 4) 释放资源
        inputStream.close();
        serverSocket.close();
    }
}
