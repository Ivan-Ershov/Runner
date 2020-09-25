import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {
    private volatile JTextArea inputText = new JTextArea(10, 100);
    private SendProtocol sendProtocol;
    private ActionProtocol actionProtocol;
    private JFileChooser chooser = new JFileChooser();

    public MainFrame (ActionProtocol actionProtocol) {
        this.actionProtocol = actionProtocol;
    }

    {
        chooser.setCurrentDirectory(new File("C:\\"));
    }

    public void show_ConnectPanel () {
        //removeAll();

        var panel = new JPanel();
        var buttonPanel = new JPanel();
        var buttonServer = new JButton("Server");
        var buttonClient = new JButton("Client");
        var outputText = new JTextField(100);
        var serverAction = new serverAction(outputText);
        var clientAction = new clientAction(outputText);

        panel.add(new JLabel("User name:"));
        panel.add(outputText);
        buttonPanel.add(buttonServer);
        buttonPanel.add(buttonClient);

        buttonServer.addActionListener(serverAction);
        buttonClient.addActionListener(clientAction);

        add(panel);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();

    }

    public void show_MainPanel () {
        removeAll();

        var panel = new JPanel();
        var outputText = new JTextField(100);
        var button_send = new JButton("Send text");
        var button_send_file = new JButton("Send file");
        var sendAction = new SendAction(outputText);
        var fileSendAction = new FileSendAction();
        var scrollPane = new JScrollPane(inputText);

        panel.add(button_send);
        panel.add(new JLabel("Text to send:"));
        panel.add(outputText);
        panel.add(button_send_file);

        button_send.addActionListener(sendAction);
        button_send_file.addActionListener(fileSendAction);
        inputText.setEnabled(false);
        inputText.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        inputText.setForeground(Color.BLACK);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        pack();

    }

    public synchronized void addInputText(String text) {

        inputText.append('\n' + text);

    }

    private class serverAction implements ActionListener
    {
        private JTextField outputText;

        public serverAction (JTextField outputText) {
            this.outputText = outputText;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            try {
                sendProtocol = actionProtocol.serverConnect(outputText.getText(), MainFrame.this);
            } catch (Exception e) {
                //e.printStackTrace();
            }

        }
    }

    private class clientAction implements ActionListener
    {
        private JTextField outputText;

        public clientAction (JTextField outputText) {
            this.outputText = outputText;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            sendProtocol = actionProtocol.clientConnect(outputText.getText(), MainFrame.this);

        }
    }

    private class FileSendAction implements ActionListener
    {

        public FileSendAction() {}

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            int result = chooser.showDialog(getParent(), "Отправить");

            if (result == JFileChooser.APPROVE_OPTION) {

                try {
                    sendProtocol.sendFile(chooser.getSelectedFile());
                } catch (IOException e) {
                    //e.printStackTrace();
                }

            }

        }

    }

    private class SendAction implements ActionListener
    {

        private JTextField outputText;

        public SendAction(JTextField outputText) {
            this.outputText = outputText;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            addInputText("Вы: " + outputText.getText());
            sendProtocol.sendMessage(outputText.getText());
            outputText.setText("");

        }
    }

}
