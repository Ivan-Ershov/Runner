import javax.swing.*;
import java.awt.*;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Runner {
    public final static Scanner inCommand = new Scanner(new InputStreamReader(System.in));
    public static MainFrame mainFrame = new MainFrame();
    public static SendProtocol sendProtocol;

    public static void main(String[] args) {

        EventQueue.invokeLater(() ->
        {
            mainFrame.setTitle("Runner");
            mainFrame.show_ConnectPanel();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            mainFrame.setLocation(screenSize.width/2 - mainFrame.getWidth()/2, screenSize.height/2 - mainFrame.getHeight()/2);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
        });

        try(ServerSocket server = new ServerSocket(1000)){

            try (Socket incoming = server.accept()) {

                sendProtocol = new SendProtocol(incoming.getInputStream(), incoming.getOutputStream(), name);
                mainFrame = new MainFrame(sendProtocol);



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
