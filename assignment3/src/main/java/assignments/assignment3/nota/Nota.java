package assignments.assignment3.nota;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
public class Nota {
    private Member member;
    private String paket;
    private ArrayList<LaundryService>services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    public Nota(Member member, int berat, String paket, String tanggal) {
        // Constructor, mengassign tiap attribute
        this.member = member;
        this.paket = paket;
        this.services = new ArrayList<LaundryService>();
        services.add(new CuciService());
        this.baseHarga = findBaseHarga(paket);
        this.sisaHariPengerjaan = findSisaHari();
        this.berat = berat;
        this.id = totalNota;
        this.tanggalMasuk = tanggal;
        this.isDone = false;
        totalNota++;
    }
    // Method untuk mencari harga awal berdasarkan jenis paket
    private static long findBaseHarga(String paket){
        if(paket.equalsIgnoreCase("express"))
            return 12000;
        if(paket.equalsIgnoreCase("fast"))
            return 10000;
        return 7000;
    }
    // Method untuk mencari sisa hari berdasarkan jenis paket
    public int findSisaHari(){
        if(paket.equalsIgnoreCase("express"))
            return 1;
        if(paket.equalsIgnoreCase("fast"))
            return 2;
        return 3;
    }

    public void addService(LaundryService service){
        services.add(service);
    }

    public String kerjakan(){
        for(int i=0; i<services.size(); i++){
            if(i == services.size()-1)
                isDone = true;
            if(!services.get(i).isDone())
                return "Nota " + id + " : " + services.get(i).doWork();
        }
        isDone = true;
        return "Nota " + id + " : Sudah selesai.";
    }
    public void toNextDay() {
        // Mengurangi sisa hari d
        if(!isDone) 
            sisaHariPengerjaan--;
    }

    public long calculateHarga(){
        // Menghitung total harga
        long result = 0;
        result += baseHarga * berat;
        for(LaundryService service : services){
            result += service.getHarga(berat);
        }
        if(sisaHariPengerjaan < 0) 
            result += sisaHariPengerjaan * 2000;
        return Math.max(result, 0);
    }

    public String getNotaStatus(){
        // Mereturn status nota
        return "Nota " + id + " : " + (isDone ? "Sudah selesai." : "Belum selesai.");
    }

    @Override
    public String toString(){
        // membuat variabel baru result untuk menyimpan hasil nota
        String result = "";
        result += "[ID Nota = " + id + "]" + "\n";
        result += "ID    : " + member.getId() + "\n";
        result += "Paket : " + paket + "\n";
        result += "Harga :" + "\n";
        result += String.format("%d kg x %d = %d\n", berat, baseHarga, baseHarga * berat) + "\n";
        result += "tanggal terima  : " + tanggalMasuk + "\n";
        // newCal untuk menyimpan tanggal akhir
        Calendar newCal = Calendar.getInstance();
        try {
            newCal.setTime(NotaManager.fmt.parse(tanggalMasuk));
        } catch (ParseException e) {}
        newCal.add(Calendar.DATE, findSisaHari());
        // menyimpan tanggal selesai
        tanggalSelesai = NotaManager.fmt.format(newCal.getTime());

        result += "tanggal selesai : " + tanggalSelesai + "\n";
        result += "--- SERVICE LIST ---" + "\n";
        result += "-Cuci @ Rp.0" + "\n";
        // loop setiap service untuk dapat berat
        for(LaundryService service: services){
            if(service instanceof SetrikaService){
                result += "-Setrika @ Rp." + service.getHarga(berat) + "\n";
                break;
            }
        }
        for(LaundryService service: services){
            if(service instanceof AntarService){
                result += "-Antar @ Rp." + service.getHarga(berat) + "\n";
                break;
            }
        }
        result += "Harga Akhir: ";
        if(sisaHariPengerjaan < 0)
            result += String.format("%d Ada kompensasi keterlambatan %d * 2000 hari\n", calculateHarga(), -sisaHariPengerjaan);
        else
            result += calculateHarga();
        return result;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }
    public LaundryService[] getServices(){
        return services.toArray(new LaundryService[0]);
    }
    public int getId() {
        return id;
    }
    public long getBaseHarga() {
        return baseHarga;
    }
}

