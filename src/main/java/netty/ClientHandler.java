package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author v_guojiafeng
 * @time 2020/12/18 下午5:20
 * @info
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext channelHandlerContext;
    private byte[] bytes;

    public ClientHandler(ChannelHandlerContext channelHandlerContext, byte[] bytes) {
        this.channelHandlerContext = channelHandlerContext;
        this.bytes = bytes;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("正在连接....");
        ctx.writeAndFlush(bytes);

    }

    // when pipeline have read event
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("正在发送....");
        channelHandlerContext.writeAndFlush(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
