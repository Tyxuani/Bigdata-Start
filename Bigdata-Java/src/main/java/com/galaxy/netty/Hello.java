package com.galaxy.netty;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Hello {

    public static void main(String[] args) {

        //server class
        ServerBootstrap bootstrap = new ServerBootstrap();
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //socketchannel factory
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));

        //Pipeline factory
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {

                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("Hello", new HelloHandler());
                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(10101));
        System.out.println("---- BootStrap Start! ----");
    }


}

class HelloHandler extends SimpleChannelHandler {
    HelloHandler() {
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        //无解码器时
        /*ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
        System.out.println(new String(buffer.array()));*/

        //加入String解码器后
        System.out.println((String) e.getMessage());

        //回写数据
        ChannelBuffer channelBuffer = ChannelBuffers.copiedBuffer(("server get " + e.getMessage()).getBytes());
        ctx.getChannel().write(channelBuffer);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exceptionCaught " + e.getCause().getMessage());
        super.exceptionCaught(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelConnected " + e.getState() + "  " + e.getValue());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelDisconnected " + e.getState() + "  " + e.getValue());
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("channelClosed " + e.getState() + "  " + e.getValue());
    }
}
