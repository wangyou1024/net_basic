package com.wangyou.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.UUID;

/**
 * @author 王游
 * @date 2021/3/11 21:24
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8001), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                String str = UUID.randomUUID().toString();

                socketChannel.write(ByteBuffer.wrap(str.getBytes()), null, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println("write " + str + ", and wait response");
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                            @Override
                            public void completed(Integer result, ByteBuffer attachment) {
                                attachment.flip();
                                CharBuffer charBuffer = CharBuffer.allocate(1024);
                                CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
                                decoder.decode(attachment, charBuffer, false);
                                charBuffer.flip();
                                String data = new String(charBuffer.array(), 0, charBuffer.limit());
                                System.out.println("server said:" + data);
                                try {
                                    socketChannel.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void failed(Throwable exc, ByteBuffer attachment) {
                                System.out.println("read error " + exc.getMessage());
                            }
                        });
                        try {
                            socketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("write error");
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("fail");
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
