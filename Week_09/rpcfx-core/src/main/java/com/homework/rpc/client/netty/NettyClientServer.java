package com.homework.rpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Redick
 * @date 2020/12/15 11:12 下午
 */
@Slf4j
public class NettyClientServer {

    private String host;

    private int port;

    public NettyClientServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, String proxyServer) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            //b.remoteAddress(host, port);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //包含编码器和解码器
                    ch.pipeline().addLast(new HttpClientCodec());
                    //聚合
                    ch.pipeline().addLast(new HttpObjectAggregator(512 * 1024));
                    //解压
                    ch.pipeline().addLast(new HttpContentDecompressor());
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    // ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    // ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new NettyHttpClientOutboundHandler(ctx, fullRequest, proxyServer));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
