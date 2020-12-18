package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author v_guojiafeng
 * @time 2020/12/18 下午4:43
 * @info
 */
public class ServerHandler extends SimpleChannelInboundHandler {
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ChannelHandlerContext channelHandlerContext1 = null;


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // Channel channel = ctx.channel();
        // channelGroup.writeAndFlush("【客户端】" + channel.remoteAddress() + "加入聊天" + sdf.format(new Date()) + "\n");
        // channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线啦！");


    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线啦！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 接收客户端的数据
            ByteBuf in = (ByteBuf) msg;
            System.out.println("channelRead:" + in.toString(CharsetUtil.UTF_8));

            // 发送到客户端
            byte[] responseByteArray = "你好".getBytes("UTF-8");
            ByteBuf out = ctx.alloc().buffer(responseByteArray.length);
            out.writeBytes(responseByteArray);
            ctx.writeAndFlush(out);

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出现异常啦！");
        ctx.close();
    }


}
