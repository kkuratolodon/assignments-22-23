package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    private boolean isDone = false;
    @Override
    public String doWork() {
        isDone = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public long getHarga(int berat) {
        return Math.max(2000, berat*500);
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
