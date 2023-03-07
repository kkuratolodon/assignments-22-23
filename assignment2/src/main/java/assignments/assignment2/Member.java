package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    // atributes:
    private String nama, noHp, id;
    private int bonusCounter;

    public Member(String nama, String noHp) {
        // assign value ke attributenya
        this.nama = nama;
        this.noHp = noHp;
        this.bonusCounter = 0;
        this.id = NotaGenerator.generateId(nama, noHp);
    }

    // method getter dan setter
    public String getNama(){
        return this.nama;
    }
    public String getNoHp(){
        return this.noHp;
    }
    public String getId(){
        return this.id;
    }
    public int getBonusCounter(){
        return this.bonusCounter;
    }
    public void setBonusCounter(int x){
        this.bonusCounter = x;
    }
    // method untuk print member list
    public String toString(){
        return "- " + this.id + " : "+ this.nama;
    }
}
