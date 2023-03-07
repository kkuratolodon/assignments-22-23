package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    // atributes:
    private int idNota, berat, sisaHariPengerjaan;
    private String paket, tanggalMasuk;
    private Member member;
    private boolean isReady;
    // notaNow untuk ngecek banyaknya nota sampai saat ini (untuk membuat id)
    private static int notaNow = 0;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // assign value ke atributenya
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        if(paket.equals("express")){
            this.sisaHariPengerjaan = 1;
        } else if (paket.equals("fast")){
            this.sisaHariPengerjaan = 2;
        } else{
            this.sisaHariPengerjaan = 3;
        }
        this.isReady = false;
        this.idNota = notaNow;
        notaNow++;
    }
    // method method getter & setter
    public void decSisaHari(){
        this.sisaHariPengerjaan -= 1;
    }
    public int getIdNota(){
        return this.idNota;
    }
    public String getPaket(){
        return this.paket;
    }
    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }
    public int getBerat(){
        return this.berat;
    }
    public boolean getIsReady(){
        return this.isReady;
    }
    public int getSisaHari(){
        return this.sisaHariPengerjaan;
    }
    public void setIsReady(){
        this.isReady = true;
    }
    // method untuk print saat minta print list nota
    public String toString(){
        if(this.isReady){
            return "- [" + this.idNota + "] Status      	: Sudah dapat diambil!";
        } else{
            return "- [" + this.idNota + "] Status      	: Belum bisa diambil :(";
        }
    }
}
