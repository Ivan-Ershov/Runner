import javax.swing.*;
import java.awt.*;

public class Runner {
    private static ActionProtocol actionProtocol = new ActionProtocol();
    public static MainFrame mainFrame = new MainFrame(actionProtocol);

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

    }
}
