package com.wangyou.nio;

import java.nio.ByteBuffer;

/**
 * @author 王游
 * @date 2021/3/10 19:03
 */
public class TestBuffer {
    public static void main(String[] args) {
        String str = "hello";
        // 1) 分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        getInfo(buffer, "allocate");

        // 2) 利用put()
        buffer.put(str.getBytes());
        getInfo(buffer, "put");

        // 3) 切换读取数据模式
        buffer.flip();
        getInfo(buffer, "flip");

        // 4) 读取数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println("content:" + new String(dst, 0, dst.length));
        getInfo(buffer, "get");

        // 5) 可重复读数据
        buffer.rewind();
        getInfo(buffer, "rewind");

        // 6) 清空：但是缓冲区的数据依然存在
        buffer.clear();
        getInfo(buffer, "clear");

        // 7) 缓冲区可操作的数量
        System.out.println("hasRemaining:" + buffer.hasRemaining() + "\nremaining:" + buffer.remaining());


    }

    public static void getInfo(ByteBuffer buffer, String status) {
        System.out.println("---------" + status + "----------");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("capacity:" + buffer.capacity());
    }
}
