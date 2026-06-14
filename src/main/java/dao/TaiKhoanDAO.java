package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.TaiKhoan;
import utils.DBConnection;

public class TaiKhoanDAO {

    public List<TaiKhoan> selectAll() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM tai_khoan";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getInt("ma_tai_khoan"),
                    rs.getString("ten_dang_nhap"),
                    rs.getString("mat_khau"),
                    rs.getString("vai_tro")
                );
                list.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public TaiKhoan selectByUsername(String username) {
        String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoan(
                        rs.getInt("ma_tai_khoan"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getString("vai_tro")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(TaiKhoan tk) {
        String sql = "INSERT INTO tai_khoan (ten_dang_nhap, mat_khau, vai_tro) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tk.getTenDangNhap());
            pstmt.setString(2, tk.getMatKhau());
            pstmt.setString(3, tk.getVaiTro());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(TaiKhoan tk) {
        String sql = "UPDATE tai_khoan SET ten_dang_nhap = ?, mat_khau = ?, vai_tro = ? WHERE ma_tai_khoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tk.getTenDangNhap());
            pstmt.setString(2, tk.getMatKhau());
            pstmt.setString(3, tk.getVaiTro());
            pstmt.setInt(4, tk.getMaTaiKhoan());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maTaiKhoan) {
        String sql = "DELETE FROM tai_khoan WHERE ma_tai_khoan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTaiKhoan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countTotal() {
        String sql = "SELECT COUNT(*) FROM tai_khoan";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countAdmins() {
        String sql = "SELECT COUNT(*) FROM tai_khoan WHERE vai_tro = 'Admin'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
