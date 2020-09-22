import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFrame extends JFrame {
    private JTextField textField = new JTextField("Введите своё имя", 50);

    public ConnectFrame() {

        JPanel panel = new JPanel();

        var button = new JButton("Ввести");
        var textAction = new ConnectFrame.TextAction();

        panel.add(button);
        panel.add(textField);
        button.addActionListener(textAction);

        add(panel);
        pack();

    }

    private class TextAction implements ActionListener
    {

        public TextAction() {
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            Runner.name = textField.getText();
            Runner.checkRegistration = true;

        }
    }

}
