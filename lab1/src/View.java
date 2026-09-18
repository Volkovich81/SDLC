import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private final JButton inputButton;
    private final JLabel resultLabel;
    private final JLabel lastInputLabel;

    public View() {
        setTitle("Калькулятор населения мира");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Нажмите для ввода даты:"));

        inputButton = new JButton("Ввести данные");
        add(inputButton);

        resultLabel = new JLabel("Население: ");
        add(resultLabel);

        lastInputLabel = new JLabel("Последний ввод: ");
        add(lastInputLabel);

        loadSavedData();
        setVisible(true);
    }

    public void setInputButtonListener(ActionListener listener) {
        inputButton.addActionListener(listener);
    }

    public void updateResult(long population) {
        String formatted = String.format("%,d", population);
        resultLabel.setText("Население: " + formatted + " человек");
    }

    public void updateLastInput(String date) {
        lastInputLabel.setText("Последний ввод: " + date);
    }

    public void saveData(String date, String result) {
        try (java.io.FileWriter fw = new java.io.FileWriter("last_data.txt")) {
            fw.write(date + "\n" + result);
        } catch (Exception e) {
            // Игнорируем ошибки сохранения
        }
    }

    public void loadSavedData() {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("last_data.txt"))) {
            String date = br.readLine();
            String result = br.readLine();
            if (date != null && !date.isEmpty()) {
                lastInputLabel.setText("Последний ввод: " + date);
            }
            if (result != null && !result.isEmpty()) {
                resultLabel.setText("Население: " + result + " человек");
            }
        } catch (Exception e) {
            // Файла нет - игнорируем
        }
    }
}