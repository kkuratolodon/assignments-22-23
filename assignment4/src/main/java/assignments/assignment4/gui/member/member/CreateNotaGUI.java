package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;
        this.setBackground(Color.GRAY);

        // Set up main panel, Feel free to make any changes
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // set layout grid bag dan buat gbc
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // set gbc untuk label paket
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 15, 8, 0);
        // buat label dan dimasukan ke panel
        paketLabel = new JLabel("Paket Laundry");
        paketLabel.setForeground(Color.WHITE);
        add(paketLabel, gbc);
        // set gbc utk combobox
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // buat combobox dan dimasukkan ke panel
        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Reguler"});
        add(paketComboBox, gbc);
        // set gbc utk show paket
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 15, 8, 15);
        // buat button & action listenernya, lalu dimasukkan ke panel
        showPaketButton = new JButton("Show Paket");
        showPaketButton.setBackground(Color.WHITE);
        showPaketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });
        add(showPaketButton, gbc);
        // set gbc utk label berat
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 15, 8, 0);
        // buat labelnya & dimasukkan ke panel
        beratLabel = new JLabel("Berat Cucian (Kg):");
        beratLabel.setForeground(Color.WHITE);
        add(beratLabel, gbc);
        // set gbc utk textfield berat
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        // buat textfieldnya & dimasukkan ke panel
        beratTextField = new JTextField();
        add(beratTextField, gbc);
        // set gbc utk checkboxnya
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        // buata checkbox dan dimasukan ke panel
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000 / kg)");
        setrikaCheckBox.setBackground(Color.GRAY);
        setrikaCheckBox.setForeground(Color.WHITE);
        add(setrikaCheckBox, gbc);

        gbc.gridy = 3;
        // buat checkbox dan dimasukan ke panel
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000 / 4kg pertama, kemudian 500 / kg)");
        antarCheckBox.setBackground(Color.GRAY);
        antarCheckBox.setForeground(Color.WHITE);
        add(antarCheckBox, gbc);
        // set gbc utk buttonnya
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        // buat buttonnya & action listyernya
        createNotaButton = new JButton("Buat Nota");
        createNotaButton.setBackground(Color.WHITE);
        createNotaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });
        add(createNotaButton, gbc);
        
        gbc.gridy = 5;
        // buat buttonnya & action listenernya
        backButton = new JButton("Kembali");
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });
        add(backButton, gbc);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // mendapatkan member yg login
        Member member = memberSystemGUI.getLoggedInMember();
        // karena cuma bisa positif, loop dan cek apakah ada nondigit
        // note: untuk string ksoong dan 0 ditambah sembarang nondigit
        String strBerat = beratTextField.getText();
        if(strBerat.equals("") || strBerat.equals("0")){
            strBerat += "a";
        }
        for(int i = 0; i < strBerat.length(); i++){
            if(strBerat.charAt(i) > '9' || strBerat.charAt(i) < '0'){
                clearAll();
                JOptionPane.showMessageDialog(null, "Masukkan berat hanya bilangan positif!", "Invalid Weight", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        // mendapat berat, paket & tanggal
        int berat = Integer.parseInt(strBerat);
        String paket = (String) paketComboBox.getSelectedItem();
        String tanggal = fmt.format(cal.getTime());
        // ubah berat jadi 2 jika kurang
        if(berat < 2){
            berat = 2;
            JOptionPane.showMessageDialog(null, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "info", JOptionPane.INFORMATION_MESSAGE);
        }
        // buat notanya
        Nota nota = new Nota(member, berat, paket, tanggal);
        // cek service yg dipilih
        if(setrikaCheckBox.isSelected()){
            nota.addService(new SetrikaService());
        }
        if(antarCheckBox.isSelected()){
            nota.addService(new AntarService());
        }
        // masukan nota ke listnotanya
        NotaManager.addNota(nota);
        member.addNota(nota);
        // di alert
        JOptionPane.showMessageDialog(null, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearAll();
    }   

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        clearAll();
        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }

    private void clearAll() {
        // method untuk reset semua element
        paketComboBox.setSelectedIndex(0);
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
    }
}
