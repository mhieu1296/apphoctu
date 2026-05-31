package models;

import java.sql.Timestamp;

public class KetQuaKiemTra {

    private int maKetQua;
    private int maTaiKhoan;
    private int maChuDe;
    private int diemSo;
    private int tongSoCau;
    private Timestamp thoiDiemLamBai;

    public KetQuaKiemTra() {
    }

    public KetQuaKiemTra(int maKetQua,
                         int maTaiKhoan,
                         int maChuDe,
                         int diemSo,
                         int tongSoCau,
                         Timestamp thoiDiemLamBai) {
        this.maKetQua = maKetQua;
        this.maTaiKhoan = maTaiKhoan;
        this.maChuDe = maChuDe;
        this.diemSo = diemSo;
        this.tongSoCau = tongSoCau;
        this.thoiDiemLamBai = thoiDiemLamBai;
    }

    public int getMaKetQua() {
        return maKetQua;
    }

    public void setMaKetQua(int maKetQua) {
        this.maKetQua = maKetQua;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public int getMaChuDe() {
        return maChuDe;
    }

    public void setMaChuDe(int maChuDe) {
        this.maChuDe = maChuDe;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }

    public int getTongSoCau() {
        return tongSoCau;
    }

    public void setTongSoCau(int tongSoCau) {
        this.tongSoCau = tongSoCau;
    }

    public Timestamp getThoiDiemLamBai() {
        return thoiDiemLamBai;
    }

    public void setThoiDiemLamBai(Timestamp thoiDiemLamBai) {
        this.thoiDiemLamBai = thoiDiemLamBai;
    }
}