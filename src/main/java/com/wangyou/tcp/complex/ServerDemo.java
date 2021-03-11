package com.wangyou.tcp.complex;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 王游
 * @date 2021/3/10 16:14
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        Socket accept = serverSocket.accept();

        InputStream inputStream = accept.getInputStream();
        int b;
        while ((b = inputStream.read()) != -1){
            System.out.print((char) b);
        }
        System.out.println("\nreceive over");

        /* 字节流
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("receive success".getBytes());*/

        // 字符流
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
        bufferedWriter.write("接收成功");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        // outputStream.close();
        bufferedWriter.close();
        inputStream.close();
        accept.close();
        serverSocket.close();
    }
}
