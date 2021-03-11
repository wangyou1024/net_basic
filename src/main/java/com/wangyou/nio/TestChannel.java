package com.wangyou.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 王游
 * @date 2021/3/10 19:57
 */
public class TestChannel {
    public static void main(String[] args) throws IOException {
        charset();
    }

    public static void charset() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");

        // 获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();

        // 获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你好，世界！");
        charBuffer.flip();

        // 编码
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);

        for (int i = 0; i < 12; i++) {
            System.out.println(byteBuffer.get());
        }

        // 解码
        byteBuffer.flip();
        CharBuffer charBufferDecode = charsetDecoder.decode(byteBuffer);
        System.out.println(charBufferDecode.toString());
    }

    /**
     * 分散和聚集
     */
    public static void scatterAndGather() throws IOException {
        RandomAccessFile rw1 = new RandomAccessFile("src\\main\\java\\com\\wangyou\\nio\\something.txt", "rw");

        // 1) 获取通道
        FileChannel channel1 = rw1.getChannel();

        // 2) 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3) 分散读取
        ByteBuffer[] buffers = {buf1, buf2};
        channel1.read(buffers);

        for (ByteBuffer buffer: buffers){
            buffer.flip();
        }

        System.out.println(new String(buffers[0].array(), 0, buffers[0].limit()));
        System.out.println("--------------");
        System.out.println(new String(buffers[1].array(), 0, buffers[1].limit()));

        // 4) 聚集写入
        RandomAccessFile rw2 = new RandomAccessFile("src\\main\\java\\com\\wangyou\\nio\\copy.txt", "rw");
        FileChannel channel2 =rw2.getChannel();
        channel2.write(buffers);

    }

    /**
     * 通道之间的数据传输
     */
    public static void channelTransfer() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("src\\main\\java\\com\\wangyou\\nio\\something.txt"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("src\\main\\java\\com\\wangyou\\nio\\copy.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        // inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 直接缓冲区完成文件复制
     */
    public static void direct() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("src\\main\\java\\com\\wangyou\\nio\\something.txt"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("src\\main\\java\\com\\wangyou\\nio\\copy.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();
    }

    /**
     * 非直接缓冲区完成文件复制
     */
    public static void  noDirect(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            // 1) 利用通道完成文件的复制
            inputStream = new FileInputStream("src\\main\\java\\com\\wangyou\\nio\\something.txt");
            outputStream = new FileOutputStream("src\\main\\java\\com\\wangyou\\nio\\copy.txt");

            // 2) 获取通道
            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();

            // 3) 分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 4) 将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1){
                // 切换为读取数据的模式
                buf.flip();
                // 将缓冲区中的数据写入通道中
                outChannel.write(buf);
                // 清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
