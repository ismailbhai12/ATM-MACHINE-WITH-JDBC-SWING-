import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class finalcode extends JFrame {
    static int pin1;

    JTextField namefield;
    JTextField moneyField;

    JPanel firstpanel;

    JLabel namelabel;
    JLabel moneyLabel;

    JPanel buttonPanel;

    JButton withdrawbutton;
    JButton deposiButton;

    public finalcode() {

        firstpanel = new JPanel();
        firstpanel.setLayout(new GridLayout(3, 2, 10, 10));
        firstpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        namelabel = new JLabel("        NAME");
        namelabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        namefield = new JTextField();

        moneyLabel = new JLabel("      MONEY");
        moneyLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        moneyField = new JTextField();
        // Removed initialization of moneycount here

        firstpanel.add(namelabel);
        firstpanel.add(namefield);

        firstpanel.add(moneyLabel);
        firstpanel.add(moneyField);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        withdrawbutton = new JButton("WITHDRAW");
        withdrawbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Moved the initialization of moneycount here
                int moneycount = Integer.parseInt(moneyField.getText());
                String naming = namefield.getText();

                String url = "jdbc:mysql://localhost:3306/your_database";
                String user = "root";
                String password = "your_pass";

                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();

                    // Changed executeQuery to executeUpdate
                    int rowsAffected = statement.executeUpdate("update atmproject set MONEY = MONEY - " + moneycount +
                            " where pin = " + pin1 + ", NAME = " + naming);

                    if (rowsAffected > 0) {
                        System.out.println("WITHDRAWN SUCCESSFULLY");
                    } else {
                        System.out.println("Withdrawal failed");
                    }

                    connection.close();
                    statement.close();

                } catch (SQLException f) {
                    throw new RuntimeException(f);
                }
            }
        });

        deposiButton = new JButton("DEPOSIT");
        deposiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent f){
                int moneycount = Integer.parseInt(moneyField.getText());
                String naming = namefield.getText();
                String url = "jdbc:mysql://localhost:3306/your_database";
                String user = "root";
                String password = "your_pass";

                try {
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();

                    // Changed executeQuery to executeUpdate
                    int rowsAffected = statement.executeUpdate("update atmproject set MONEY = MONEY + " + moneycount +
                            " where pin = " + pin1 + ", NAME = " + naming);

                    if (rowsAffected > 0) {
                        System.out.println("WITHDRAWN SUCCESSFULLY");
                    } else {
                        System.out.println("Withdrawal failed");
                    }

                    connection.close();
                    statement.close();

                } catch (SQLException g) {
                    throw new RuntimeException(g);
                }
            }
        });

        buttonPanel.add(withdrawbutton);
        buttonPanel.add(deposiButton);

        add(firstpanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(350, 200);
        setTitle("ATM MACHINE PROJECT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/your_database";
        String user = "root";
        String password = "your_pass";

        try {
            JPasswordField pinField = new JPasswordField();
            int pin = JOptionPane.showConfirmDialog(null, pinField, "Enter Your PIN:",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pin == JOptionPane.OK_OPTION) {
                pin1 = Integer.parseInt(new String(pinField.getPassword()));
                // Continue with the rest of your code
            } else {
                // User pressed Cancel or closed the dialog
                System.exit(0); // Exit the program or handle it appropriately
            }

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from atmproject where pin = " + pin1);

            while (resultSet.next()) {
                finalcode m = new finalcode();
                m.setVisible(true);
            }
            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
