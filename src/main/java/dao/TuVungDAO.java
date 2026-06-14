package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.TuVung;
import utils.DBConnection;

public class TuVungDAO {

    public List<TuVung> selectAll() {
        List<TuVung> list = new ArrayList<>();
        String sql = "SELECT * FROM tu_vung";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TuVung tv = new TuVung(
                    rs.getInt("ma_tu"),
                    rs.getString("tu_tieng_anh"),
                    rs.getString("nghia_tieng_viet"),
                    rs.getInt("ma_chu_de")
                );
                list.add(tv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TuVung> selectByChuDe(int maChuDe) {
        List<TuVung> list = new ArrayList<>();
        String sql = "SELECT * FROM tu_vung WHERE ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maChuDe);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TuVung tv = new TuVung(
                        rs.getInt("ma_tu"),
                        rs.getString("tu_tieng_anh"),
                        rs.getString("nghia_tieng_viet"),
                        rs.getInt("ma_chu_de")
                    );
                    list.add(tv);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(TuVung tv) {
        String sql;
        boolean hasManualId = tv.getMaTu() > 0;
        if (hasManualId) {
            sql = "INSERT INTO tu_vung (ma_tu, tu_tieng_anh, nghia_tieng_viet, ma_chu_de) VALUES (?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO tu_vung (tu_tieng_anh, nghia_tieng_viet, ma_chu_de) VALUES (?, ?, ?)";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (hasManualId) {
                pstmt.setInt(1, tv.getMaTu());
                pstmt.setString(2, tv.getTuTiengAnh());
                pstmt.setString(3, tv.getNghiaTiengViet());
                pstmt.setInt(4, tv.getMaChuDe());
            } else {
                pstmt.setString(1, tv.getTuTiengAnh());
                pstmt.setString(2, tv.getNghiaTiengViet());
                pstmt.setInt(3, tv.getMaChuDe());
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(TuVung tv) {
        String sql = "UPDATE tu_vung SET tu_tieng_anh = ?, nghia_tieng_viet = ?, ma_chu_de = ? WHERE ma_tu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tv.getTuTiengAnh());
            pstmt.setString(2, tv.getNghiaTiengViet());
            pstmt.setInt(3, tv.getMaChuDe());
            pstmt.setInt(4, tv.getMaTu());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maTu) {
        String sql = "DELETE FROM tu_vung WHERE ma_tu = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTu);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countTotal() {
        String sql = "SELECT COUNT(*) FROM tu_vung";
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

    public int countByChuDe(int maChuDe) {
        String sql = "SELECT COUNT(*) FROM tu_vung WHERE ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maChuDe);
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
}
