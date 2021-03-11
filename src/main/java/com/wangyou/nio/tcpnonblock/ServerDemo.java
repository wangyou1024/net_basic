package com.wangyou.nio.tcpnonblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author 王游
 * @date 2021/3/11 19:04
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        // 1) 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 2) 切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        // 3) 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));

        // 4) 获取选择器
        Selector selector = Selector.open();

        // 5) 将通道注册到选择器,并且指定监听事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("serverSocketChannel注册成功");
        // 6) 轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0){
            // 7) 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                // 8) 获取准备“就绪”的事件
                SelectionKey selectionKey = iterator.next();
                // 9) 判断具体是什么事件准备就绪
                if (selectionKey.isAcceptable()){
                    // 10) 若“接收就绪”，获取客户端连接
                    SocketChannel socketChannel = ((ServerSocketChannel)selectionKey.channel()).accept();

                    // 11) 切换非阻塞模式
                    socketChannel.configureBlocking(false);

                    // 12) 将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("socketChannel注册成功");
                } else if (selectionKey.isReadable()){
                    // 13) 获取当前选择器上“读就绪”状态的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    // 14) 读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = socketChannel.read(buffer)) > 0){
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                // 15) 取消选择键
                iterator.remove();
            }
        }
    }
}
