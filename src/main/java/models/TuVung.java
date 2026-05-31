package models;

public class TuVung {

    private int maTu;
    private String tuTiengAnh;
    private String nghiaTiengViet;
    private int maChuDe;

    public TuVung() {
    }

    public TuVung(int maTu, String tuTiengAnh,
                  String nghiaTiengViet, int maChuDe) {
        this.maTu = maTu;
        this.tuTiengAnh = tuTiengAnh;
        this.nghiaTiengViet = nghiaTiengViet;
        this.maChuDe = maChuDe;
    }

    public int getMaTu() {
        return maTu;
    }

    public void setMaTu(int maTu) {
        this.maTu = maTu;
    }

    public String getTuTiengAnh() {
        return tuTiengAnh;
    }

    public void setTuTiengAnh(String tuTiengAnh) {
        this.tuTiengAnh = tuTiengAnh;
    }

    public String getNghiaTiengViet() {
        return nghiaTiengViet;
    }

    public void setNghiaTiengViet(String nghiaTiengViet) {
        this.nghiaTiengViet = nghiaTiengViet;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }
}