import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InputDialog extends JDialog {
    private final JTextField dateField;
    private final JButton calculateButton;

    public InputDialog(JFrame parent) {
        super(parent, "Ввод данных", true);
        setSize(350, 150);
        setLayout(new FlowLayout());
        setLocationRelativeTo(parent);

        add(new JLabel("Введите дату (ДД.ММ.ГГГГ):"));

        dateField = new JTextField(15);
        add(dateField);

        calculateButton = new JButton("Рассчитать");
        add(calculateButton);

        setVisible(false);
    }

    public void setCalculateListener(ActionListener listener) {
        calculateButton.addActionListener(listener);
    }

    public String getDateInput() {
        return dateField.getText();
    }

    public void setDateInput(String date) {
        dateField.setText(date);
    }

    public void showDialog() {
        setVisible(true);
    }

    public void closeDialog() {
        setVisible(false);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}