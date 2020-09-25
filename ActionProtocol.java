import java.net.ServerSocket;
import java.net.Socket;

public class ActionProtocol {
    private static final int port = 1000;
    private static final String host = "127.0.0.0";
    private SendProtocol sendProtocol;

    public SendProtocol serverConnect (String name, MainFrame mainFrame) throws Exception {

        mainFrame.removeAll();
        mainFrame.show_MainPanel();

        try (ServerSocket serverSocket = new ServerSocket(port); Socket incoming = serverSocket.accept()) {

            sendProtocol = new SendProtocol(incoming.getInputStream(), incoming.getOutputStream(), name);

            var threadIn = new Thread(new ThreadIn(sendProtocol, mainFrame));

            threadIn.start();

        }

        return sendProtocol;

    }

    public SendProtocol clientConnect (String name, MainFrame mainFrame) throws Exception{

        try (Socket connect = new Socket(host, port)) {

            sendProtocol = new SendProtocol(connect.getInputStream(), connect.getOutputStream(), name);

            var threadIn = new Thread(new ThreadIn(sendProtocol, mainFrame));

            threadIn.start();

        }

        return sendProtocol;

    }

}
