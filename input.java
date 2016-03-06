
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class input extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            input dialog = new input();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public input() {
        setTitle("Neuer Nutzer");
        setBounds(100, 100, 320, 214);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        textField = new JTextField();
        textField.setBounds(10, 42, 284, 20);
        contentPanel.add(textField);
        textField.setColumns(10);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblName.setBounds(10, 11, 284, 20);
        contentPanel.add(lblName);

        JLabel lblPasswort = new JLabel("Passwort");
        lblPasswort.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblPasswort.setBounds(10, 73, 284, 20);
        contentPanel.add(lblPasswort);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(10, 104, 284, 20);
        contentPanel.add(textField_1);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    public String[] getString()
    {
        String[] a = new String[2];
        a[1]= textField.getText();
        a[2]= textField_1.getText();
        return a;
    }
}
