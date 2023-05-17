package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.GRAY);

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // buat gbc
        GridBagConstraints gbc = new GridBagConstraints();
        // set gbc awal utk label 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        // buat label id & dimasukan ke panel
        idLabel = new JLabel("Masukan ID Anda:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 15));
        idLabel.setForeground(Color.WHITE);
        mainPanel.add(idLabel, gbc);

        gbc.gridy = 1;
        // buat text field & dimasukan ke panel
        idTextField = new JTextField();
        idTextField.setPreferredSize(new Dimension(0, 25));
        mainPanel.add(idTextField, gbc);

        gbc.gridy = 2;
        // buat label password & dimasukkan ke panel
        passwordLabel = new JLabel("Masukan password Anda:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setForeground(Color.WHITE);
        mainPanel.add(passwordLabel, gbc);
        
        gbc.gridy = 3;
        // buat field password & dimasukan ke panel
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 25));
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // buat button login, action listenernya dan dimasukan ke panel
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 5;
        // buat back button, action listenernya dan dimasukan ke panel
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        mainPanel.add(backButton, gbc);
    }
    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // jika ditekan back akan menghapus semua text di field dan pergi ke page home
        clearAll();
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // mendapat id dan password
        String id = idTextField.getText();
        String password = new String(passwordField.getPassword());
        // clear semua field
        clearAll();
        // menggunakan method dari main frame untuk login
        if(!MainFrame.getInstance().login(id, password)){
            // kalau ternyata salah passwordnya
            JOptionPane.showMessageDialog(null, "ID atau Password Invalid!", "Invalid Login", JOptionPane.ERROR_MESSAGE);
        } 
    }
    // method clear semua field
    private void clearAll() {
        idTextField.setText("");
        passwordField.setText("");
    }
}
