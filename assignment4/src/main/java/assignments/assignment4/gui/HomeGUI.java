package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;
    private JButton gameButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.DARK_GRAY);

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        // buat label judul
        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        // set gbcnya
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.insets = new Insets(10,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // dimasukan ke panel
        mainPanel.add(titleLabel, gbc);
        // buat button
        loginButton = new JButton("Login");
        loginButton.setBackground(Color.WHITE);
        // set gbc
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 100, 10, 100);
        // buat action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToLogin();
            }
        });
        // dimasukan ke panel
        mainPanel.add(loginButton, gbc);
        // buat button register
        registerButton = new JButton("Register");
        registerButton.setBackground(Color.WHITE);
        // set gbc
        gbc.gridy = 2;
        // buat action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
            }
        });
        // dimasukkan ke panel
        mainPanel.add(registerButton, gbc);
        // buat buton next day
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.setBackground(Color.WHITE);
        // set gbcnya
        gbc.gridy = 3;
        // buat action listener
        toNextDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextDay();
            }
        });
        // dimasukkan ke panel
        mainPanel.add(toNextDayButton, gbc);
        
        gameButton = new JButton("Play Game");
        gameButton.setBackground(Color.WHITE);
        gbc.gridy = 4;
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePlayGame();
            }
        });
        mainPanel.add(gameButton, gbc);
        
        
        // buat label datenya
        dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()));
        dateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dateLabel.setForeground(Color.WHITE);
        // set gbc
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 5;
        // dimasukkan ke panle
        mainPanel.add(dateLabel, gbc);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        // pindah ke page register
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        // pindah ke page login
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }
    
    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        // pakai method di tp 3 untuk next day dan di alert
        NotaManager.toNextDay();
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));
        JOptionPane.showMessageDialog(null, "Kamu sudah sangat capek mengerjakan tp dan memutuskan untuk tidur", "Zzz...", JOptionPane.INFORMATION_MESSAGE);
    }
    private void handlePlayGame() {
        String text = "Press \"W\" to go up, \"S\" to go down, and Space to shoot!";
        JOptionPane.showMessageDialog(null, text, "Start Game", JOptionPane.INFORMATION_MESSAGE);
        MainFrame.getInstance().navigateTo(GameGUI.KEY);
        GameGUI.startTimer();
    }
}
