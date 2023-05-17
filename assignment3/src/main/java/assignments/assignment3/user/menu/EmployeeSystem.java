package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

import java.util.Arrays;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
            new Employee("Dek Depe", "akuDDP"),
            new Employee("Depram", "musiktualembut"),
            new Employee("Lita Duo", "gitCommitPush"),
            new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
    };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch(choice){
            case 1 -> nyuciTime();
            case 2 -> displayListNota();
            case 3 -> logout = true;
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }
    private void nyuciTime(){
        // mengerjakan pekerjaan setiap nota
        System.out.println("Stand back! " + loginMember.getNama() + " beginning to nyuci!");
        for(Nota nota : notaList){
            System.out.println(nota.kerjakan());
        }
    }
    private static void displayListNota(){
        // print setiap status nota
        for(Nota nota : notaList){
            System.out.println(nota.getNotaStatus());
        }
    }
    public void addEmployee(Employee[] employees){
        int n = memberList.length;
        memberList = Arrays.copyOf(memberList, memberList.length + employees.length);
        for(int i=n; i<memberList.length; i++){
            memberList[i] = employees[i-n];
        }
    }
}