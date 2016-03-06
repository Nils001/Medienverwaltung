
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Label;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class GUI
{
    private JFrame frame;
    private JTable table;
    private JTable table_1;
    private MV mv;
    private Object[][] medien;
    private Object[][] räume;
    private String nutzername;

    /**
     * Launch the application.
     */
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable() 
            {
                public void run() 
                {
                    try 
                    {
                        GUI window = new GUI();
                    } catch (Exception e) 
                    {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, e.toString(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
    }

    /**
     * Create the application.
     */
    public GUI() 
    {
        initialize_login();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() 
    {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setBounds(100, 100, 950, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try 
        {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } 
        catch (UnsupportedLookAndFeelException e) 
        {
            // handle exception
        }
        catch (ClassNotFoundException e) 
        {
            // handle exception
        }
        catch (InstantiationException e) 
        {
            // handle exception
        }
        catch (IllegalAccessException e) 
        {
            // handle exception
        }

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnBenutzer = new JMenu("Benutzer");
        menuBar.add(mnBenutzer);

        JMenuItem mntmAbmelden = new JMenuItem("Abmelden");
        mnBenutzer.add(mntmAbmelden);

        JMenu mnHilfe = new JMenu("Hilfe");
        menuBar.add(mnHilfe);
        frame.getContentPane().setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 914, 358);
        frame.getContentPane().add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Medium", null, panel, null);
        tabbedPane.setEnabledAt(0, true);
        panel.setLayout(null);

        JLabel lblTag = new JLabel("Tag");
        lblTag.setBounds(10, 11, 22, 14);
        panel.add(lblTag);

        JLabel label = new JLabel("Monat");
        label.setBounds(66, 11, 66, 14);
        panel.add(label);

        JLabel lblJahr = new JLabel("Jahr");
        lblJahr.setBounds(160, 11, 37, 14);
        panel.add(lblJahr);
        JLabel label_1 = new JLabel(" BEAMER ");
        label_1.setBounds(80, 101, 46, 14);
        panel.add(label_1);
        //monat combobox
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(66, 36, 66, 20);
        panel.add(comboBox);
        for(int i = 1;i<13;i++)
        {
            comboBox.addItem(i);
        }
        // tag combobox

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setBounds(10, 36, 40, 20);
        panel.add(comboBox_1);
        for(int i = 1;i<31;i++)
        {
            comboBox_1.addItem(i);
        }
        //jahrcombobox
        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setBounds(160, 36, 55, 20);
        panel.add(comboBox_2);
        comboBox_2.addItem(2016);
        comboBox_2.addItem(2017);
        comboBox_2.addItem(2015);

        JComboBox comboBox_3 = new JComboBox();
        // medien combobox

        comboBox_3.setBounds(259, 36, 100, 20);
        panel.add(comboBox_3);
        try{
            String[][] a = mv.getMedien();
            for(int i=0;i<a.length;i++)
            {
                comboBox_3.addItem(a[i][0]);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JButton btnSuchen = new JButton("Suchen");
        btnSuchen.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent arg0) 
                {
                    String datum = "" + comboBox_2.getSelectedItem() + "-" + comboBox.getSelectedItem() + "-" + comboBox_1.getSelectedItem();
                    String medienName = (String) comboBox_3.getSelectedItem();
                    label_1.setText(medienName);
                    try{
                        MedienTabelleUpdaten(mv.getBelegung(medienName,datum));
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        btnSuchen.setBounds(10, 67, 349, 23);
        panel.add(btnSuchen);

        JLabel lblMedium = new JLabel("Medium");
        lblMedium.setBounds(259, 11, 100, 14);
        panel.add(lblMedium);

        JLabel lblAusgewhlt = new JLabel("Ausgew\u00E4hlt:");
        lblAusgewhlt.setBounds(10, 101, 60, 14);
        panel.add(lblAusgewhlt);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(369, 11, 2, 308);
        panel.add(separator);
        // medien tabelle
        table = new JTable();
        table.setRowMargin(3);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"},
                    {"", "2016-01-01", "2016-01-02", "2016-01-03", "2016-01-04", "2016-01-05"},
                    {" 1. Stunde", null, null, null, null, null},
                    {" 2. Stunde", null, null, null, null, null},
                    {" 3. Stunde", null, null, null, null, null},
                    {" 4. Stunde", null, null, null, null, null},
                    {" 5. Stunde", null, null, null, null, null},
                    {" 6. Stunde", null, null, null, null, null},
                    {" 7. Stunde", null, null, null, null, null},
                    {" 8. Stunde", null, null, null, null, null},
                    {" 9. Stunde", null, null, null, null, null},
                    {" 10. Stunde", null, null, null, null, null},
                },
                new String[] { "Leer", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag" }
            ) 
            {
                Class[] columnTypes = new Class[] 
                    {
                        String.class, Object.class, Object.class, Object.class, Object.class, Object.class
                    };
                public Class getColumnClass(int columnIndex) 
                {
                    return columnTypes[columnIndex];
                }
            });
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(381, 11, 518, 300);
        panel.add(table);

        JButton btnBuchen = new JButton("Buchen");
        btnBuchen.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    for(int spalte=0; spalte<5;spalte++)
                    {
                        for(int zeile=1;zeile<11;zeile++ )
                        {
                            String tabelle = (String) table.getValueAt(zeile+1,spalte+1);
                            if (tabelle != null)
                            {
                                String tabelleLowerCase = tabelle.toLowerCase();
                                if (medien[spalte][zeile] == null)
                                {
                                    String vorher  = (String) medien[spalte][zeile];
                                    String datum = (String) medien[spalte][0];
                                    String medienName = (String) comboBox_3.getSelectedItem();
                                    String Stunde = String.valueOf(zeile);
                                    if(tabelleLowerCase.equals(nutzername))
                                    {
                                        //if(!tabelleLowerCase.equals(vorher) && vorher == null)
                                        //{                    
                                        mv.set(nutzername,medienName,datum,Stunde);
                                        //}
                                    }
                                }
                            }
                        }
                    }
                }
            });
        btnBuchen.setBounds(10, 296, 160, 23);
        panel.add(btnBuchen);

        JButton btnEntfernen = new JButton("Entfernen");
        btnEntfernen.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    for(int spalte=0; spalte<5;spalte++)
                    {
                        for(int zeile=1;zeile<11;zeile++ )
                        {
                            String tabelle = (String) table.getValueAt(zeile+1,spalte+1);
                            if (tabelle != null && !tabelle.equals(nutzername))
                            {
                                if (medien[spalte][zeile] != null)
                                {
                                    String vorher  = (String) medien[spalte][zeile];
                                    if (vorher != null)
                                    {
                                        vorher = vorher.toLowerCase();
                                        String datum = (String) medien[spalte][0];
                                        String medienName = (String) comboBox_3.getSelectedItem();
                                        String Stunde = String.valueOf(zeile);
                                        if(vorher.equals(nutzername))
                                        {
                                            //if(!tabelleLowerCase.equals(vorher) && vorher == null)
                                            //{   
                                            mv.unset(nutzername,medienName,datum,Stunde);
                                            //}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        btnEntfernen.setBounds(199, 296, 160, 23);
        panel.add(btnEntfernen);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        tabbedPane.addTab("Raum", null, panel_1, null);
        tabbedPane.setEnabledAt(1, true);

        JLabel label_2 = new JLabel("Tag");
        label_2.setBounds(10, 11, 22, 14);
        panel_1.add(label_2);

        JLabel label_3 = new JLabel("Monat");
        label_3.setBounds(66, 11, 66, 14);
        panel_1.add(label_3);

        JLabel label_4 = new JLabel("Jahr");
        label_4.setBounds(160, 11, 37, 14);
        panel_1.add(label_4);

        // monate
        JComboBox comboBox_4 = new JComboBox();
        comboBox_4.setBounds(66, 36, 66, 20);
        panel_1.add(comboBox_4);
        for(int i = 1;i<13;i++)
        {
            comboBox_4.addItem(i);
        }

        // tage 
        JComboBox comboBox_5 = new JComboBox();
        comboBox_5.setBounds(10, 36, 40, 20);
        panel_1.add(comboBox_5);
        for(int i = 1;i<31;i++)
        {
            comboBox_5.addItem(i);
        }

        //jahre
        JComboBox comboBox_6 = new JComboBox();
        comboBox_6.setBounds(160, 36, 55, 20);

        comboBox_6.addItem(2016);
        comboBox_6.addItem(2017);
        comboBox_6.addItem(2015);
        panel_1.add(comboBox_6);

        JLabel label_5 = new JLabel("Räume");
        label_5.setBounds(259, 11, 100, 14);
        panel_1.add(label_5);

        JComboBox comboBox_7 = new JComboBox();
        comboBox_7.setBounds(259, 36, 100, 20);
        panel_1.add(comboBox_7);
        try{
            String[][] b = mv.getRaum();
            for(int i=0;i<b.length;i++)
            {
                comboBox_7.addItem(b[i][0]);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel label_6 = new JLabel("Ausgew\u00E4hlt:");
        label_6.setBounds(10, 101, 60, 14);
        panel_1.add(label_6);

        JLabel label_7 = new JLabel(" RAUM ");
        label_7.setBounds(80, 101, 46, 14);
        panel_1.add(label_7);

        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setBounds(369, 11, 2, 308);
        panel_1.add(separator_1);

        JButton button = new JButton("Suchen");
        button.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    String datum = ""+comboBox_6.getSelectedItem()+"-"+comboBox_4.getSelectedItem()+"-"+comboBox_5.getSelectedItem();
                    String raumName = (String) comboBox_7.getSelectedItem();
                    label_7.setText(raumName);
                    try
                    {
                        MedienTabelleUpdaten(mv.getBelegung(raumName,datum));
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        button.setBounds(10, 67, 349, 23);

        // raum tabelle
        panel_1.add(button);
        table_1 = new JTable();
        table_1.setRowMargin(3);
        table_1.setRowHeight(25);
        table_1.setFillsViewportHeight(true);
        table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        table_1.setBounds(381, 11, 518, 300);
        table_1.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag"},
                    {"", "1. Jan", "2. Jan", "3. Jan", "4. Jan", "5. Jan"},
                    {" 1. Stunde", null, null, null, null, null},
                    {" 2. Stunde", null, null, null, null, null},
                    {" 3. Stunde", null, null, null, null, null},
                    {" 4. Stunde", null, null, null, null, null},
                    {" 5. Stunde", null, null, null, null, null},
                    {" 6. Stunde", null, null, null, null, null},
                    {" 7. Stunde", null, null, null, null, null},
                    {" 8. Stunde", null, null, null, null, null},
                    {" 9. Stunde", null, null, null, null, null},
                    {" 10. Stunde", null, null, null, null, null},
                },
                new String[] { "Leer", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag" }
            )
        );
        panel_1.add(table_1);

        JButton button_1 = new JButton("Buchen");
        button_1.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    for(int spalte=0; spalte<5;spalte++)
                    {
                        for(int zeile=1;zeile<11;zeile++ )
                        {
                            String tabelle = (String) table.getValueAt(zeile+1,spalte+1);
                            if (tabelle != null)
                            {
                                String tabelleLowerCase = tabelle.toLowerCase();
                                if (medien[spalte][zeile] == null)
                                {
                                    String vorher  = (String) medien[spalte][zeile];
                                    String datum = (String) medien[spalte][0];
                                    String medienName = (String) comboBox_3.getSelectedItem();
                                    String Stunde = String.valueOf(zeile);
                                    if(tabelleLowerCase.equals(nutzername))
                                    {
                                        //if(!tabelleLowerCase.equals(vorher) && vorher == null)
                                        //{                    
                                        mv.set(nutzername,medienName,datum,Stunde);
                                        //}
                                    }
                                }
                            }
                        }
                    }
                }
            });
        button_1.setBounds(10, 296, 160, 23);
        panel_1.add(button_1);

        JButton button_2 = new JButton("Entfernen");
        button_2.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
                    for(int spalte=0; spalte<5;spalte++)
                    {
                        for(int zeile=1;zeile<11;zeile++ )
                        {
                            String tabelle = (String) table.getValueAt(zeile+1,spalte+1);
                            if (tabelle != null && !tabelle.equals(nutzername))
                            {
                                if (medien[spalte][zeile] != null)
                                {
                                    String vorher  = (String) medien[spalte][zeile];
                                    if (vorher != null)
                                    {
                                        vorher = vorher.toLowerCase();
                                        String datum = (String) medien[spalte][0];
                                        String medienName = (String) comboBox_3.getSelectedItem();
                                        String Stunde = String.valueOf(zeile);
                                        if(vorher.equals(nutzername))
                                        {
                                            //if(!tabelleLowerCase.equals(vorher) && vorher == null)
                                            //{   
                                            mv.unset(nutzername,medienName,datum,Stunde);
                                            //}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        button_2.setBounds(199, 296, 160, 23);
        panel_1.add(button_2);
    }

    public void MedienTabelleUpdaten(Object[][] a)
    {
        medien = a;
        for(int zeile = 0; zeile<11; zeile++)
        {
            for(int spalte = 0 ; spalte<5;spalte++)
            {
                table.setValueAt(a[spalte][zeile],zeile+1,spalte+1);
            }
        }
    }

    public void RaumTabelleUpdaten(Object[][] a)
    {
        räume = a;
        for(int zeile = 0; zeile < 11; zeile++)
        {
            for(int spalte = 0; spalte < 5; spalte++)
            {
                table_1.setValueAt(a[spalte][zeile], zeile+1, spalte+1);
            }
        }
    }

    private void initialize_login() 
    {
        try
        {
            mv = new MV();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JFrame frmLogin = new JFrame();
        frmLogin.setTitle("Login");
        frmLogin.setResizable(false);
        frmLogin.setBounds(100, 100, 200, 193);
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.getContentPane().setLayout(null);

        JTextField  textField = new JTextField();
        textField.setBounds(10, 42, 174, 20);
        frmLogin.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblBenutzername = new JLabel("Benutzername");
        lblBenutzername.setBounds(10, 11, 93, 20);
        frmLogin.getContentPane().add(lblBenutzername);

        JLabel lblPasswort = new JLabel("Passwort");
        lblPasswort.setBounds(10, 73, 93, 20);
        frmLogin.getContentPane().add(lblPasswort);

        JPasswordField textField_1 = new JPasswordField();
        textField_1.setBounds(10, 101, 174, 20);
        frmLogin.getContentPane().add(textField_1);
        textField_1.setColumns(10);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.setActionCommand("Login");
        btnNewButton.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent arg0) 
                {
                    if(textField.getText() != null && textField_1.getText() != null)
                    {
                        String benutzername = textField.getText();
                        String passwort= textField_1.getText();
                        try
                        {
                            if(mv.login(benutzername, passwort)!= 0)
                            {
                                String h = benutzername.toLowerCase();
                                nutzername = h;
                                initialize();
                                frame.setVisible(true);
                                frmLogin.setVisible(false);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Falscher Nutzer oder Falsches Passwort", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        catch(Exception e)
                        {
                            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        btnNewButton.setBounds(10, 132, 174, 23);
        frmLogin.getContentPane().add(btnNewButton);
        frmLogin.setVisible(true);
    }

    public void unterschied_raum()
    {

    }
}
