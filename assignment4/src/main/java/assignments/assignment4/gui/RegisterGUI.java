package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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
        // set gbc untuk label nama
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.weightx = 1;
        gbc.weighty = 1;
        // buat label nama & setting
        nameLabel = new JLabel("Masukan nama Anda:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setForeground(Color.WHITE);
        // masukkan label nama ke panel
        mainPanel.add(nameLabel, gbc);
        // set gbc utk text field nama
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        // buat text field nama
        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(0, 25));
        // dimasukkan ke panel
        mainPanel.add(nameTextField, gbc);
        // set gbc untuk phone label
        gbc.gridy = 2;
        // buat phone label
        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 15));
        phoneLabel.setForeground(Color.WHITE);
        // dimasukkan ke panel
        mainPanel.add(phoneLabel, gbc);
        // set gbc utk phone text field
        gbc.gridy = 3;
        // buat text field
        phoneTextField = new JTextField();
        phoneTextField.setPreferredSize(new Dimension(0, 25));
        // dimasukkan ke panel
        mainPanel.add(phoneTextField, gbc);
        // set gbc buat label pw
        gbc.gridy = 4;
        // buat label
        passwordLabel = new JLabel("Masukan password Anda:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setForeground(Color.WHITE);
        // masukan ke panel
        mainPanel.add(passwordLabel, gbc);
        // set gbc buat field pw
        gbc.gridy = 5;
        // buat fieldnya
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 25));
        // dimasukkan ke panel
        mainPanel.add(passwordField, gbc);
        // set gbc buat buttons
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        // buat buttonnya
        registerButton = new JButton("Register");
        // buat action listenernya, jika ditekan akan manggil method handle register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        // dimasukkan ke panel
        mainPanel.add(registerButton, gbc);
        // set gbc utk btn kembali
        gbc.gridy = 7;
        // buat buttonnya
        backButton = new JButton("Kembali");
        // buat action listenernya
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        // dimasukan ke panel
        mainPanel.add(backButton, gbc);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        clearAll();
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        String nama = nameTextField.getText();
        nama = nama.strip();
        String noHp = phoneTextField.getText();
        String password = new String(passwordField.getPassword());
        // cek kalau salah satu fieldnya kosong
        if(nama.equals("") || noHp.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Semua field wajib diisi!", "Empty FIeld", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // cek kalau no hpnya ada nondigit
        for(int i=0; i<noHp.length(); i++){
            if(noHp.charAt(i) > '9' || noHp.charAt(i) < '0'){
                phoneTextField.setText("");
                JOptionPane.showMessageDialog(null, "Nomor hp hanya diisi angka!", "Invalid Number", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // buat member baru
        Member newMember = loginManager.register(nama, noHp, password);
        // cek kalau member barunya null (sudah ada)
        if(newMember == null){
            String text = "Member dengan nama " + nama + " dan nomor hp " + noHp + " sudah ada!";
            JOptionPane.showMessageDialog(mainPanel, text, "Member Already Exist", JOptionPane.ERROR_MESSAGE);
            handleBack();
            return;
        }
        // terakhir kalau berhasil
        String text = "Berhasil membuat user dengan ID " + newMember.getId() + "!";
        JTextField field = new JTextField(text);
        field.setEditable(false);
        JOptionPane.showMessageDialog(null, field, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        handleBack();
            
    }
    // method untuk mengosongkan semua field
    private void clearAll(){
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
    }
}
