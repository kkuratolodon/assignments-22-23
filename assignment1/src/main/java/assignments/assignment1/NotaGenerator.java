package assignments.assignment1;

import java.util.Scanner;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // TODO: Implement interface menu utama
        boolean exit = false;
        while (!exit){
            // print menu dan input pilihan
            printMenu();
            System.out.printf("Pilihan : ");
            String pilihan = input.nextLine();
            System.out.println("================================");
            // cek pilihan ke berapa
            switch(pilihan){
                // generate id
                case "1":
                    System.out.println("Masukkan nama Anda:");
                    String nama = input.nextLine();
                    System.out.println("Masukkan nomor handphone Anda:");
                    String noHp = input.nextLine();
                    // cek no Hp, kalau ada selain angka langsung false
                    boolean isValid = true;
                    for(int i=0; i<noHp.length(); i++){
                        char digit = noHp.charAt(i);
                        if(digit > '9' || digit < '0'){
                            isValid = false;
                            break;
                        }
                    }
                    // minta input terus selama masih tidak valid, cara ceknya sama
                    while(!isValid){
                        System.out.println("Nomor hp hanya menerima digit");
                        noHp = input.nextLine();
                        isValid = true;
                        for(int i=0; i<noHp.length(); i++){
                            char digit = noHp.charAt(i);
                            if(digit > '9' || digit < '0'){
                                isValid = false;
                                break;
                            }
                        }
                    }
                    // generate nota dan print
                    String id = generateId(nama, noHp);
                    System.out.println("ID Anda : " + id);
                    break;
                // generate nota
                case "2": 
                    System.out.println("Masukkan nama Anda:");
                    nama = input.nextLine();
                    System.out.println("Masukkan nomor handphone Anda:");
                    noHp = input.nextLine();
                    // cek no hp valid sama seperti yang sebelumnya
                    isValid = true;
                    for(int i=0; i<noHp.length(); i++){
                        char digit = noHp.charAt(i);
                        if(digit > '9' || digit < '0'){
                            isValid = false;
                            break;
                        }
                    }
                    while(!isValid){
                        System.out.println("Nomor hp hanya menerima digit");
                        noHp = input.nextLine();
                        isValid = true;
                        for(int i=0; i<noHp.length(); i++){
                            char digit = noHp.charAt(i);
                            if(digit > '9' || digit < '0'){
                                isValid = false;
                                break;
                            }
                        }
                    }
                    id = generateId(nama, noHp);
                    
                    System.out.println("Masukkan tanggal terima:");
                    String tanggal = input.nextLine();
                    
                    System.out.println("Masukkan paket laundry:");
                    String paket = input.nextLine();
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
                    }
                    System.out.println("Masukkan berat cucian Anda [Kg]:");
                    String strBerat = input.nextLine(); 
                    // ngecek beratnya sama seperti nomor hp
                    // karena bulat positif pasti cuma angka dari 0-9
                    isValid = true;
                    for(int i=0; i<strBerat.length(); i++){
                        char digit = strBerat.charAt(i);
                        if(digit > '9' || digit < '0'){
                            isValid = false;
                            break;
                        }
                    }
                    while(!isValid){
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        strBerat = input.nextLine();
                        isValid = true;
                        for(int i=0; i<strBerat.length(); i++){
                            char digit = strBerat.charAt(i);
                            if(digit > '9' || digit < '0'){
                                isValid = false;
                                break;
                            }
                        }
                    }
                    // convert ke int
                    int berat = Integer.parseInt(strBerat);
                    // cek < 2 apa tidak
                    if (berat < 2){
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                        berat = 2;
                    } 
                    // generate nota dan diprint
                    String notaLaundry = generateNota(id, paket, berat, tanggal);
                    System.out.println(notaLaundry);
                // exit jika 0
                case "0":
                    exit = true;
                    System.out.println("Terima kasih telah menggunakan NotaGenerator!");
                    break;
                // selain itu ulang.
                default:
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        nama += " ";
        // tambah spasi biar kalau cuma satu kata nggak error
        int idxSpasi = nama.indexOf(" ");
        // ambil kata pertamanya aja
        nama = nama.substring(0, idxSpasi);
        // kapitalin
        nama = nama.toUpperCase();
        // hitung checksumnya
        int result = 0;
        for(int i=0; i < nama.length(); i++){
            char chara = nama.charAt(i);
            if (chara >= '0' && chara <= '9'){
                result += chara - '0';
            } else if (chara >= 'A' && chara <= 'Z'){
                result += chara - 'A' + 1;
            } else{
                result += 7;
            }
        } 
        for(int i=0; i<nomorHP.length(); i++){
            result += (nomorHP.charAt(i) - '0');
        }
        // tambah 7 untuk "-"
        result += 7;
        // convert ke string, tambah 0 di depan jika 1 digit
        String checkSum = Integer.toString(result);
        if (checkSum.length() == 1){
            checkSum = "0" + checkSum;
        }
        // return hasil idnya
        String id = nama + "-" + nomorHP + "-" + checkSum; 
        return id;
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // ambil tanggal, bulan dan tahunnya
        String strTgl = tanggalTerima.substring(0,2);
        String strBln = tanggalTerima.substring(3,5);
        String strThn = tanggalTerima.substring(6,10);
        int tanggal = Integer.parseInt(strTgl);
        int bulan = Integer.parseInt(strBln);
        int tahun = Integer.parseInt(strThn);
        LocalDate startDate = LocalDate.of(tahun, bulan, tanggal);
        // cek paketnya untuk menentukan harga dan tanggal akhir
        LocalDate endDate;
        int hargaPaketPerKg;
        if (paket.equals("express")){
            hargaPaketPerKg = 12000;
            endDate = startDate.plus(1, ChronoUnit.DAYS);
        } else if (paket.equals("fast")){
            hargaPaketPerKg = 10000;
            endDate = startDate.plus(2, ChronoUnit.DAYS);
        } else{
            hargaPaketPerKg = 7000;
            endDate = startDate.plus(3, ChronoUnit.DAYS);
        }
        // convert tanggal ke string, dan tambahkan leading zero
        String tglAkhir = Integer.toString(endDate.getDayOfMonth());
        if(tglAkhir.length() == 1) tglAkhir = "0"+ tglAkhir;
        String blnAkhir = Integer.toString(endDate.getMonthValue());
        if(blnAkhir.length() == 1) blnAkhir = "0" + blnAkhir;
        String thnAkhir = Integer.toString(endDate.getYear());
        while(thnAkhir.length() < 4) thnAkhir = "0" + thnAkhir;

        // buat notanya
        String nota =   "ID    : " + id + "\n" +
                        "Paket : " + paket + "\n"+
                        "Harga :\n" +
                        String.format("%d kg x %d = %d\n", berat, hargaPaketPerKg, berat*hargaPaketPerKg) +
                        "Tanggal Terima  : " + tanggalTerima + "\n" +
                        "Tanggal Selesai : " + tglAkhir + "/" + blnAkhir + "/" + thnAkhir;
        return nota;
    }
}
