import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame {
    private volatile JTextArea InputText = new JTextArea(10, 100);
    private JTextField OutputText = new JTextField("Вводите своё сообщение.", 100);
    private SendProtocol sendProtocol;
    private JFileChooser chooser = new JFileChooser();

    {
        chooser.setCurrentDirectory(new File("C:\\"));
    }

    public MainFrame(SendProtocol sendProtocol) {
        this.sendProtocol = sendProtocol;

        JPanel panel = new JPanel();

        var button_send = new JButton("Отправить");
        var button_send_file = new JButton("Отправить файл");
        var sendAction = new SendAction();
        var fileSendAction = new FileSendAction();
        var scrollPane = new JScrollPane(InputText);

        panel.add(button_send);
        add(scrollPane, BorderLayout.CENTER);
        panel.add(OutputText);
        panel.add(button_send_file);

        button_send.addActionListener(sendAction);
        button_send_file.addActionListener(fileSendAction);
        InputText.setEnabled(false);
        InputText.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        InputText.setForeground(Color.BLACK);

        add(panel, BorderLayout.SOUTH);
        pack();

    }

    public synchronized void addInputText(String text) {

        InputText.append('\n' + text);

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

            addInputText("Вы: " + OutputText.getText());
            sendProtocol.sendMessage(OutputText.getText());
            OutputText.setText("");

        }
    }

}
