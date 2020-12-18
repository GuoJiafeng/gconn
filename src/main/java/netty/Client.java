package netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * @author v_guojiafeng
 * @time 2020/12/18 下午5:20
 * @info
 */
public class Client {


    public static ChannelHandlerContext run(ChannelHandlerContext channelFromServer, byte[] s) {


        ChannelHandlerContext context = null;
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        try {

            // 设置相关参数
            bootstrap
                    .group(eventExecutors) // set thread group
                    .channel(NioSocketChannel.class)  // set clinet pipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler(channelFromServer,s ));
                            ch.pipeline().addLast(new ByteArrayDecoder());// String解码。
                            ch.pipeline().addLast(new ByteArrayEncoder());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 3305).sync();

            context = channelFuture.channel().pipeline().context(ClientHandler.class);


            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
        return context;
    }

}
