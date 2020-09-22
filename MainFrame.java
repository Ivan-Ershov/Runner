import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {
    private volatile JTextArea inputText = new JTextArea(10, 100);

    private SendProtocol sendProtocol;
    private JFileChooser chooser = new JFileChooser();

    {
        chooser.setCurrentDirectory(new File("C:\\"));
    }

    public void show_ConnectPanel () {
        removeAll();

        var panel = new JPanel();
        var button = new JButton("Ввести");
        var outputText = new JTextField(100);
        var textAction = new MainFrame.TextAction(outputText);

        panel.add(new JLabel("User name"));
        panel.add(outputText);
        panel.add(button);

        button.addActionListener(textAction);

        add(panel);
        pack();

    }

    public void show_MainPanel () {
        JPanel panel = new JPanel();

        var button_send = new JButton("Отправить");
        var button_send_file = new JButton("Отправить файл");
        var sendAction = new SendAction();
        var fileSendAction = new FileSendAction();
        var scrollPane = new JScrollPane(inputText);

        panel.add(button_send);
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

    private class TextAction implements ActionListener
    {

        private String name;
        private JTextField outputText;

        public TextAction(JTextField outputText) {
            this.outputText = outputText;
        }

        public String getName() {
            return name;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            name = outputText.getText();

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

        public SendAction() {
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            addInputText("Вы: " + outputText.getText());
            sendProtocol.sendMessage(outputText.getText());
            outputText.setText("");

        }
    }

}
