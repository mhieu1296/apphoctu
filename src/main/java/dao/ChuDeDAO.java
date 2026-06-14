package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.ChuDe;
import utils.DBConnection;

public class ChuDeDAO {

    public List<ChuDe> selectAll() {
        List<ChuDe> list = new ArrayList<>();
        String sql = "SELECT * FROM chu_de";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChuDe cd = new ChuDe(
                    rs.getInt("ma_chu_de"),
                    rs.getString("ten_chu_de")
                );
                list.add(cd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChuDe selectById(int id) {
        String sql = "SELECT * FROM chu_de WHERE ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ChuDe(
                        rs.getInt("ma_chu_de"),
                        rs.getString("ten_chu_de")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChuDe selectByName(String name) {
        String sql = "SELECT * FROM chu_de WHERE ten_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ChuDe(
                        rs.getInt("ma_chu_de"),
                        rs.getString("ten_chu_de")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(ChuDe cd) {
        String sql;
        boolean hasManualId = cd.getMaChuDe() > 0;
        if (hasManualId) {
            sql = "INSERT INTO chu_de (ma_chu_de, ten_chu_de) VALUES (?, ?)";
        } else {
            sql = "INSERT INTO chu_de (ten_chu_de) VALUES (?)";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (hasManualId) {
                pstmt.setInt(1, cd.getMaChuDe());
                pstmt.setString(2, cd.getTenChuDe());
            } else {
                pstmt.setString(1, cd.getTenChuDe());
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ChuDe cd) {
        String sql = "UPDATE chu_de SET ten_chu_de = ? WHERE ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cd.getTenChuDe());
            pstmt.setInt(2, cd.getMaChuDe());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maChuDe) {
        String sql = "DELETE FROM chu_de WHERE ma_chu_de = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maChuDe);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countTotal() {
        String sql = "SELECT COUNT(*) FROM chu_de";
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
