package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    // inisiasi variabel" penting
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<Nota>();
    private static ArrayList<Member> memberList = new ArrayList<Member>();
    private static ArrayList<String> idList = new ArrayList<String>();
    public static void main(String[] args) {
        // loop selama masih belum exit
        boolean isRunning = true;
        while (isRunning) {
            // print menu dan input pilihan     
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        System.out.println("Masukkan nama Anda:");
        String nama = input.nextLine();
        System.out.println("Masukkan nomor handphone Anda:");
        String noHp = input.nextLine();
        // cek no Hp, kalau ada selain angka langsung false
        boolean isValid = cekDigit(noHp);
        
        // minta input terus selama masih tidak valid, cara ceknya sama
        while(!isValid){
            System.out.println("Field nomor hp hanya menerima digit");
            noHp = input.nextLine();
            isValid = cekDigit(noHp);
        }
        // buat member baru dan cek sudah ada apa nggak
        Member newMember = new Member(nama, noHp);
        if(!idList.contains(newMember.getId())){
            // kalo membernya baru masukan ke ArrayList
            idList.add(newMember.getId());
            memberList.add(newMember);
            System.out.println("Berhasil membuat member dengan ID " + newMember.getId() + "!");
        } else{
            System.out.println("Member dengan nama " + nama + " dan nomor hp " + noHp + " sudah ada!");
        }
    }

    private static void handleGenerateNota() {
        System.out.println("Masukan ID member:");
        String id = input.nextLine();
        // cek idnya ada di list apa nggak
        if(!idList.contains(id)){
            System.out.println("Member dengan ID " + id + " tidak ditemukan!");
            return;
        }
        System.out.println("Masukkan paket laundry:");
        String paket = input.nextLine();
        paket = paket.toLowerCase();
        // cek selama inputnya bkn dari 3 paket yang ada, minta input terus
        while(!paket.equals("express") && !paket.equals("fast") && !paket.equals("reguler")){
            // kalau ? print jenis jenis paket
            if (paket.equals("?")){
                showPaket();       
            } else{ // kalau tidak diketahui
                System.out.println("Paket " + paket + " tidak diketahui");
                System.out.println("[ketik ? untuk mencari tahu jenis paket]");
            }
            System.out.println("Masukkan paket laundry:");
            paket = input.nextLine();
            paket = paket.toLowerCase();
        }
        System.out.println("Masukkan berat cucian Anda [Kg]:");
        String strBerat = input.nextLine(); 
        // validasi input berat
        boolean isValid = cekDigit(strBerat) && !strBerat.equals("0");
        while(!isValid){
            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            strBerat = input.nextLine();
            isValid = cekDigit(strBerat) && !strBerat.equals("0");
        }
        // convert ke int
        int berat = Integer.parseInt(strBerat);
        // cek < 2 apa tidak
        if (berat < 2){
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            berat = 2;
        }  
        // cari index dari id tersebut di arraylistnya
        int idx = idList.indexOf(id);
        // dapatkan object membernya dari index tersebut
        Member member = memberList.get(idx);
        // menambahkan counter bonusnya
        member.setBonusCounter(member.getBonusCounter() + 1);
        // buat notanya kemudian masukkan ke arrayList
        Nota newNota = new Nota(member, paket, berat, fmt.format(cal.getTime()));
        notaList.add(newNota);
        // buat variable endDate & harga untuk menyimpan tanggal akhir dan harganya 
        Calendar endDate = (Calendar)cal.clone();
        int hargaPaketPerKg;
        // cek endDate dan harganya
        if(paket.equals("express")){
            hargaPaketPerKg = 12000;
            endDate.add(Calendar.DATE, 1);
        } else if(paket.equals("fast")){
            hargaPaketPerKg = 10000;
            endDate.add(Calendar.DATE, 2);
        } else{
            hargaPaketPerKg = 7000;
            endDate.add(Calendar.DATE, 3);
        }
        // string diskon akan berisikan harga setelah diskon (kalau counternya 3)
        String diskon = "";
        if(member.getBonusCounter() == 3){
            member.setBonusCounter(0);
            diskon = String.format(" = %d (Discount member 50%%!!!)", hargaPaketPerKg*berat/2);
        }
        // print notanya
        System.out.println("Berhasil menambahkan nota!");
        System.out.println("[ID Nota = " + newNota.getIdNota() + "]");
        String strNota =   "ID    : " + id + "\n" +
                        "Paket : " + paket + "\n"+
                        "Harga :\n" +
                        String.format("%d kg x %d = %d%s\n", berat, hargaPaketPerKg, berat*hargaPaketPerKg, diskon) +
                        "Tanggal Terima  : " + fmt.format(cal.getTime()) + "\n" +
                        "Tanggal Selesai : " + fmt.format(endDate.getTime()) + "\n" + 
                        "Status      	: Belum bisa diambil :(";
        System.out.println(strNota);
    }

    private static void handleListNota() {
        // print semua notanya
        System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem.");
        for(Nota x : notaList){
            System.out.println(x);
        }
    }

    private static void handleListUser() {
        // print semua usernya
        System.out.println("Terdaftar " + memberList.size() + " member dalam sistem.");
        for(Member x : memberList){
            System.out.println(x);
        }
    }

    private static void handleAmbilCucian() {
        // minta input id nota
        System.out.println("Masukan ID nota yang akan diambil:");
        String idNota = input.nextLine();
        boolean isValid = cekDigit(idNota);
        // validasi id nota
        while(!isValid){
            System.out.println("ID nota berbentuk angka!");
            idNota = input.nextLine();
            isValid = cekDigit(idNota);
        }
        int idNotaInt = Integer.parseInt(idNota);
        // cek idnya ada di list apa nggak (dengan loop)
        boolean found = false;
        Nota notaDiambil = null;
        for(Nota x: notaList){
            if(x.getIdNota() == idNotaInt){
                notaDiambil = x;
                found = true;
                break;
            }
        }
        // jika tidak ditemukan
        if(!found){
            System.out.println("Nota dengan ID " + idNotaInt + " tidak ditemukan!");
            return;
        } 
        // jika ditemukan cek apakah sudah ready apa nggak
        if(!notaDiambil.getIsReady()){
            System.out.println("Nota dengan ID " + idNotaInt + " gagal diambil!");
        } else{
            // jika sudah, hapus notanya dari list
            System.out.println("Nota dengan ID " + idNotaInt + " berhasil diambil!");
            notaList.remove(notaDiambil);
        }
    }

    private static void handleNextDay() {
        // tambah 1 hari
        cal.add(Calendar.DATE, 1);
        System.out.println("Dek Depe tidur hari ini... zzz...");
        // loop setiap notanya
        for(Nota x: notaList){
            // kurangi sisa hari
            x.decSisaHari();
            if(x.getSisaHari() == 0){
                x.setIsReady();
            }
            // cek kalau sudah ready
            if(x.getIsReady()){
                System.out.println("Laundry dengan nota ID " + x.getIdNota() + " sudah dapat diambil!");
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    // method untuk mengecek apakah digit semua, kalau iya return true
    private static boolean cekDigit(String string){
        for(int i=0; i<string.length(); i++){
            char digit = string.charAt(i);
            if(digit > '9' || digit < '0'){
                return false;
            }
        }
        return true;
    }
}
