import java.net.ServerSocket;
import java.net.Socket;

public class ActionProtocol {
    private static final int port = 48700;
    private static final String host = "127.0.0.0";
    private SendProtocol sendProtocol;
    private static ServerSocket serverSocket;
    private static Socket incoming;
    private static Socket connect;

    public SendProtocol serverConnect (String name, MainFrame mainFrame) throws Exception {

        try {

            serverSocket = new ServerSocket(port);

            incoming = serverSocket.accept();

            sendProtocol = new SendProtocol(incoming.getInputStream(), incoming.getOutputStream(), name);

            var threadIn = new Thread(new ThreadIn(sendProtocol, mainFrame));

            threadIn.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return sendProtocol;

    }

    public SendProtocol clientConnect (String name, MainFrame mainFrame) throws Exception{

        try {

            connect = new Socket(host, port);

            sendProtocol = new SendProtocol(connect.getInputStream(), connect.getOutputStream(), name);

            var threadIn = new Thread(new ThreadIn(sendProtocol, mainFrame));

            mainFrame.show_MainPanel();

            threadIn.start();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return sendProtocol;

    }

}
