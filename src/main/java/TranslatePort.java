import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TranslatePort {
    private transient static Log log = LogFactory.getLog(TranslatePort.class);

    public static void main(String[] args) {
        try {
            if (args == null || args.length < 3) {
                log.error("输出参数不能为空，分别是 本地监听端口、远程IP、远程端口");
                return;
            }


            //获取本地监听端口、远程IP和远程端口
            int localPort = Integer.parseInt(args[0].trim());
            String remoteIp = args[1].trim();
            int remotePort = Integer.parseInt(args[2].trim());

            //启动本地监听端口
            ServerSocket serverSocket = new ServerSocket(localPort);
            log.error("localPort=" + localPort + ";remoteIp=" + remoteIp +
                    ";remotePort=" + remotePort + ";启动本地监听端口" + localPort + "成功！");

            while (true) {
                Socket clientSocket = null;
                Socket remoteServerSocket = null;
                try {
                    //获取客户端连接
                    clientSocket = serverSocket.accept();

                    log.error("accept one client");
                    //建立远程连接
                    remoteServerSocket = new Socket(remoteIp, remotePort);

                    log.error("create remoteip and port success");
                    //启动数据转换接口
                    (new TransPortData(clientSocket, remoteServerSocket, "1")).start();
                    (new TransPortData(remoteServerSocket, clientSocket, "2")).start();
                } catch (Exception ex) {
                    log.error("", ex);
                }
                //建立连接远程
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}