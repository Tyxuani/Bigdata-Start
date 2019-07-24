package com.galaxy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

    private int port;
    private Selector selector;

    NIOServer(int port) {
        this.port = port;
    }

    public void initServer() throws IOException {

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(port));
        ssc.configureBlocking(false);

        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println("-----NIO Server Start------");
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handle(key);
            }
        }
    }

    private void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            handleAccept(key);
        } else if (key.isReadable()) {
            handleRead(key);
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);

        System.out.println("----new Client connect-----");
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //正确关闭流程
        int length = clientChannel.read(buffer);
        if (length > 0) {
            String msg = new String(buffer.array(), 0, length);
            System.out.println("--> " + msg);
            ByteBuffer out = ByteBuffer.wrap(("nio server return: " + msg + "\n\t").getBytes());
            clientChannel.write(out);
        } else if (length < 0) {
            System.out.println("---client closed-----");
            key.cancel();
        }
    }

    public static void main(String[] args) throws IOException {
        NIOServer nioServer = new NIOServer(8055);
        nioServer.initServer();
        nioServer.listen();
    }

}
