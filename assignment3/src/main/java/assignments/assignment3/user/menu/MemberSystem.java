package assignments.assignment3.user.menu;
import java.util.Arrays;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch(choice){
            case 1 -> buatNota();
            case 2 -> detailNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        memberList = Arrays.copyOf(memberList, memberList.length+1);
        memberList[memberList.length - 1] = member;
    }
    private void buatNota(){
        System.out.println("Masukan paket laundry:");
        showPaket();
        String paket = in.nextLine();
        System.out.println("Masukan berat cucian anda [Kg]:");
        int berat = Integer.parseInt(in.nextLine());
        if(berat < 2){
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }
        String tanggal = NotaManager.fmt.format(NotaManager.cal.getTime());
        Nota nota = new Nota(loginMember, berat, paket, tanggal);

        System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\nHanya tambah 1000 / kg :0");
        System.out.print("[Ketik x untuk tidak mau]: ");
        String input = in.nextLine();
        if(!input.equals("x")){
            nota.addService(new SetrikaService());
        }
        System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\nCuma 2000 / 4kg, kemudian 500 / kg");
        System.out.print("[Ketik x untuk tidak mau]: ");
        input = in.nextLine();
        if(!input.equals("x")){
            nota.addService(new AntarService());
        }
        NotaManager.addNota(nota);
        loginMember.addNota(nota);
        System.out.println("Nota berhasil dibuat!");
    }
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    private void detailNota(){
        for(Nota nota : loginMember.getNotaList()){
            System.out.println(nota);
        }
    }
}