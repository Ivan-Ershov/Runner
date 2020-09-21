import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Runner {
    public final static Scanner inCommand = new Scanner(new InputStreamReader(System.in));
    public static MainFrame mainFrame;
    public volatile static  boolean checkRegistration = false;
    public static String name;
    private static ConnectFrame connectFrame = new ConnectFrame();
    public static SendProtocol sendProtocol;

    public static void main(String[] args) {

        try(ServerSocket server = new ServerSocket(1000)){

            EventQueue.invokeLater(() ->
            {
                try {
                    connectFrame.setTitle("Runner:" + InetAddress.getLocalHost());
                } catch (UnknownHostException e) {
                    connectFrame.setTitle("Runner");
                }

                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                connectFrame.setLocation(screenSize.width/2 - connectFrame.getWidth()/2, screenSize.height/2 - connectFrame.getHeight()/2);
                connectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                connectFrame.setVisible(true);
            });

            while (!checkRegistration) {
                Thread.onSpinWait();
            }

            connectFrame.dispose();

            try (Socket incoming = server.accept()) {

                sendProtocol = new SendProtocol(incoming.getInputStream(), incoming.getOutputStream(), name);
                mainFrame = new MainFrame(sendProtocol);

                EventQueue.invokeLater(() ->
                {
                    mainFrame.setTitle("Runner");
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Dimension screenSize = toolkit.getScreenSize();
                    mainFrame.setLocation(screenSize.width/2 - mainFrame.getWidth()/2, screenSize.height/2 - mainFrame.getHeight()/2);
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.setVisible(true);
                });

                var threadIn = new Thread(new ThreadIn(sendProtocol, mainFrame));

                threadIn.start();
                threadIn.join();

                mainFrame.dispose();

            } catch (Exception ex) {
                //System.out.println(ex.getMessage());
            }

        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
        } finally {
            inCommand.close();
        }

    }
}
