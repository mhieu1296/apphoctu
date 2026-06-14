package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import models.KetQuaKiemTra;
import utils.DBConnection;

public class KetQuaKiemTraDAO {

    public List<KetQuaKiemTra> selectAll() {
        List<KetQuaKiemTra> list = new ArrayList<>();
        String sql = "SELECT * FROM ket_qua_kiem_tra ORDER BY thoi_diem_lam_bai DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                KetQuaKiemTra kq = new KetQuaKiemTra(
                    rs.getInt("ma_ket_qua"),
                    rs.getInt("ma_tai_khoan"),
                    rs.getInt("ma_chu_de"),
                    rs.getInt("diem_so"),
                    rs.getInt("tong_so_cau"),
                    rs.getTimestamp("thoi_diem_lam_bai")
                );
                list.add(kq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KetQuaKiemTra> selectByTaiKhoan(int maTaiKhoan) {
        List<KetQuaKiemTra> list = new ArrayList<>();
        String sql = "SELECT * FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ? ORDER BY thoi_diem_lam_bai DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KetQuaKiemTra kq = new KetQuaKiemTra(
                        rs.getInt("ma_ket_qua"),
                        rs.getInt("ma_tai_khoan"),
                        rs.getInt("ma_chu_de"),
                        rs.getInt("diem_so"),
                        rs.getInt("tong_so_cau"),
                        rs.getTimestamp("thoi_diem_lam_bai")
                    );
                    list.add(kq);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> selectDetailsByChuDe(int maChuDe) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT tk.ten_dang_nhap, kq.diem_so, kq.tong_so_cau, kq.thoi_diem_lam_bai " +
                     "FROM ket_qua_kiem_tra kq " +
                     "JOIN tai_khoan tk ON kq.ma_tai_khoan = tk.ma_tai_khoan " +
                     "WHERE kq.ma_chu_de = ? " +
                     "ORDER BY kq.thoi_diem_lam_bai DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maChuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[] {
                        rs.getString("ten_dang_nhap"),
                        rs.getInt("diem_so"),
                        rs.getInt("tong_so_cau"),
                        rs.getTimestamp("thoi_diem_lam_bai")
                    };
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(KetQuaKiemTra kq) {
        String sql = "INSERT INTO ket_qua_kiem_tra (ma_tai_khoan, ma_chu_de, diem_so, tong_so_cau, thoi_diem_lam_bai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kq.getMaTaiKhoan());
            pstmt.setInt(2, kq.getMaChuDe());
            pstmt.setInt(3, kq.getDiemSo());
            pstmt.setInt(4, kq.getTongSoCau());
            
            Timestamp ts = kq.getThoiDiemLamBai();
            if (ts == null) {
                ts = new Timestamp(System.currentTimeMillis());
            }
            pstmt.setTimestamp(5, ts);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countChuDeDaLam(int maTaiKhoan) {
        String sql = "SELECT COUNT(DISTINCT ma_chu_de) FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDiemTrungBinh(int maTaiKhoan) {
        String sql = "SELECT AVG(diem_so * 10.0 / tong_so_cau) FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double getMaxDiemChuDe(int maTaiKhoan, int maChuDe) {
        String sql = "SELECT MAX(diem_so * 10.0 / tong_so_cau) FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ? AND ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            pstmt.setInt(2, maChuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public int getSoLanLamChuDe(int maTaiKhoan, int maChuDe) {
        String sql = "SELECT COUNT(*) FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ? AND ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            pstmt.setInt(2, maChuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDiemTrungBinhChuDe(int maTaiKhoan, int maChuDe) {
        String sql = "SELECT AVG(diem_so * 10.0 / tong_so_cau) FROM ket_qua_kiem_tra WHERE ma_tai_khoan = ? AND ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            pstmt.setInt(2, maChuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
