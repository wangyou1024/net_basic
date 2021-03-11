package com.wangyou.tcp.complex;

import java.io.*;
import java.net.Socket;

/**
 * @author 王游
 * @date 2021/3/10 11:32
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10000);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello world".getBytes());
        // 仅仅关闭这个输出流，并加入一个结束标志，让接收端停止接收
        socket.shutdownOutput();

        /*
        使用字节流出现中文乱码：一个中文不是一个字节，单个字节读取会出现乱码
        InputStream inputStream = socket.getInputStream();
        int b;
        while ((b = inputStream.read()) != -1){
            System.out.print((char) b);
        }*/

        // 字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();

        // inputStream.close();
        bufferedReader.close();
        outputStream.close();
        socket.close();
    }
}
