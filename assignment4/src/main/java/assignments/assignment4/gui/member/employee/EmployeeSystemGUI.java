package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // buat 2 buttonnya dan return sbg array
        return new JButton[]{
            new JButton("It's nyuci time"),
            new JButton("Display List Nota")
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        // loop semua nota, setiap statusnya akan di masukan ke text
        String text = "";
        for(Nota nota : NotaManager.notaList){
            text += nota.getNotaStatus() + "\n"; 
        }
        // kalau masih kosong
        if(NotaManager.notaList.length == 0){
            text = "Belum ada nota!";
            JOptionPane.showMessageDialog(this, text, "List Nota", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // setelah itu di pop up
        JOptionPane.showMessageDialog(this, text, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        // show pane pertama
        String text = "Stand back! " + loggedInMember.getNama() + " beginning to nyuci!";
        JOptionPane.showMessageDialog(this, text, "Nyuci Time", JOptionPane.INFORMATION_MESSAGE);
        // loop semua nota dikerjakan, dan nanti semuanya akan di popup kecuali kalau kosong
        text = "";
        for(Nota nota : NotaManager.notaList){
            text += nota.kerjakan() + "\n";
        }
        // cek kalau kosong
        if(NotaManager.notaList.length == 0){
            text = "Nothing to cuci here";
            JOptionPane.showMessageDialog(this, text, "Nota Kosong", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, text, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
    }
}
