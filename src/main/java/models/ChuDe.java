package models;

public class ChuDe {

    private int maChuDe;
    private String tenChuDe;

    public ChuDe() {
    }

    public ChuDe(int maChuDe, String tenChuDe) {
        this.maChuDe = maChuDe;
        this.tenChuDe = tenChuDe;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
    }
}