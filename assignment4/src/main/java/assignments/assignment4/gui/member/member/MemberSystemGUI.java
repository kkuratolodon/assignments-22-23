package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";
    private SystemCLI systemCLI;
    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
        this.systemCLI = systemCLI;
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{ 
            // return 2 button yg dibutuhkan
            new JButton("Saya ingin laundry"), 
            new JButton("Lihat detail nota saya")
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        // mendapat nota list untuk membernya
        Nota[] notaList = loggedInMember.getNotaList();
        String text = "";
        // loop dan masukan textnya ke string
        for(Nota nota: notaList){
            text += nota + "\n\n";
        }
        // kalau listnya kosong
        if(notaList.length == 0){
            text = "Kamu belum pernah laundry di CuciCuci! :'(";
        }
        // masukkan textnya ke scrollpane dan di pop up
        TextArea textArea = new TextArea(text);
        textArea.setPreferredSize(new Dimension(400, 300));
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Detail Nota", JOptionPane.INFORMATION_MESSAGE);        
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        // method saat tekan button create nota, akan pergi ke page create nota
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

}
