package com.homework.rpc.client.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Redick
 * @date 2020/12/15 11:16 下午
 */
public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext ctx1;

    private FullHttpRequest fullRequest;

    private String backUrl;

    public NettyHttpClientOutboundHandler(ChannelHandlerContext ctx1, FullHttpRequest fullRequest, String url) {
        this.ctx1 = ctx1;
        this.fullRequest = fullRequest;
        this.backUrl = url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
