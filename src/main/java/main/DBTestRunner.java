package main;

import dao.*;
import models.*;
import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBTestRunner {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("          HỆ THỐNG KIỂM TRA TỰ ĐỘNG APPHOCTU       ");
        System.out.println("==================================================");

        boolean connOk = checkConnection();
        if (!connOk) {
            System.out.println("\n[ERROR] Không thể kết nối tới Cơ sở dữ liệu.");
            System.out.println("-> Vui lòng kiểm tra:");
            System.out.println("   1. MySQL Server đã khởi chạy chưa.");
            System.out.println("   2. Cổng PORT (3306) hoặc HOST (localhost) có chính xác không.");
            System.out.println("   3. Username/Password trong file DBConnection.java có đúng không.");
            System.out.println("   4. Đã import file schema.sql để tạo database 'apphoctu' chưa.");
            System.exit(1);
        }

        System.out.println("\n--- BẮT ĐẦU KIỂM TRA CÁC LỚP DAO ---");
        boolean taiKhoanOk = testTaiKhoanDAO();
        boolean chuDeOk = testChuDeDAO();
        boolean tuVungOk = testTuVungDAO();
        boolean ketQuaOk = testKetQuaKiemTraDAO();

        System.out.println("\n==================================================");
        System.out.println("               TỔNG HỢP KẾT QUẢ TEST              ");
        System.out.println("==================================================");
        printStatus("Kết nối MySQL Database", true);
        printStatus("Tài khoản DAO (TaiKhoanDAO)", taiKhoanOk);
        printStatus("Chủ đề DAO (ChuDeDAO)", chuDeOk);
        printStatus("Từ vựng DAO (TuVungDAO)", tuVungOk);
        printStatus("Kết quả kiểm tra DAO (KetQuaKiemTraDAO)", ketQuaOk);
        System.out.println("==================================================");

        if (taiKhoanOk && chuDeOk && tuVungOk && ketQuaOk) {
            System.out.println("\n[SUCCESS] Tất cả các thành phần CSDL hoạt động bình thường!");
            System.out.println("-> Bạn có thể khởi chạy AppHocTu để manual test giao diện UI.");
        } else {
            System.out.println("\n[WARNING] Có một số thành phần kiểm tra thất bại.");
            System.out.println("-> Vui lòng kiểm tra lại cấu trúc bảng hoặc dữ liệu mẫu.");
        }
    }

    private static void printStatus(String name, boolean status) {
        if (status) {
            System.out.printf("[SUCCESS] %-40s : OK\n", name);
        } else {
            System.out.printf("[FAILED]  %-40s : THẤT BẠI\n", name);
        }
    }

    private static boolean checkConnection() {
        System.out.print("Đang kiểm tra kết nối CSDL... ");
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Thành công!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Thất bại! Chi tiết lỗi: " + e.getMessage());
        }
        return false;
    }

    private static boolean testTaiKhoanDAO() {
        System.out.print("Đang kiểm tra TaiKhoanDAO... ");
        try {
            TaiKhoanDAO dao = new TaiKhoanDAO();
            
            // Test selectAll
            List<TaiKhoan> before = dao.selectAll();
            
            // Test insert
            TaiKhoan temp = new TaiKhoan(0, "testuser_temp", "hash_temp", "User");
            boolean inserted = dao.insert(temp);
            if (!inserted) {
                System.out.println("Thất bại khi Insert!");
                return false;
            }
            
            // Test selectByUsername
            TaiKhoan found = dao.selectByUsername("testuser_temp");
            if (found == null) {
                System.out.println("Thất bại khi Select!");
                return false;
            }
            
            // Test update
            found.setMatKhau("hash_temp_updated");
            boolean updated = dao.update(found);
            if (!updated) {
                System.out.println("Thất bại khi Update!");
                return false;
            }
            
            // Test delete
            boolean deleted = dao.delete(found.getMaTaiKhoan());
            if (!deleted) {
                System.out.println("Thất bại khi Delete!");
                return false;
            }
            
            System.out.println("Thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Thất bại! Chi tiết lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testChuDeDAO() {
        System.out.print("Đang kiểm tra ChuDeDAO... ");
        try {
            ChuDeDAO dao = new ChuDeDAO();
            
            // Test selectAll
            List<ChuDe> before = dao.selectAll();
            
            // Test insert
            ChuDe temp = new ChuDe(0, "ChuDeTestTemp");
            boolean inserted = dao.insert(temp);
            if (!inserted) {
                System.out.println("Thất bại khi Insert!");
                return false;
            }
            
            // Test selectByName
            ChuDe found = dao.selectByName("ChuDeTestTemp");
            if (found == null) {
                System.out.println("Thất bại khi SelectByName!");
                return false;
            }
            
            // Test update
            found.setTenChuDe("ChuDeTestTempUpdated");
            boolean updated = dao.update(found);
            if (!updated) {
                System.out.println("Thất bại khi Update!");
                return false;
            }
            
            // Test delete
            boolean deleted = dao.delete(found.getMaChuDe());
            if (!deleted) {
                System.out.println("Thất bại khi Delete!");
                return false;
            }
            
            System.out.println("Thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Thất bại! Chi tiết lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testTuVungDAO() {
        System.out.print("Đang kiểm tra TuVungDAO... ");
        try {
            ChuDeDAO cdDao = new ChuDeDAO();
            TuVungDAO tvDao = new TuVungDAO();
            
            // Tạo chủ đề tạm để liên kết
            ChuDe tempCd = new ChuDe(0, "ChuDeTestTemp_TV");
            cdDao.insert(tempCd);
            ChuDe cdFound = cdDao.selectByName("ChuDeTestTemp_TV");
            
            // Test insert từ vựng
            TuVung tv = new TuVung(0, "test_word", "từ_test", cdFound.getMaChuDe());
            boolean inserted = tvDao.insert(tv);
            if (!inserted) {
                System.out.println("Thất bại khi Insert!");
                // Cleanup
                cdDao.delete(cdFound.getMaChuDe());
                return false;
            }
            
            // Test selectByChuDe
            List<TuVung> list = tvDao.selectByChuDe(cdFound.getMaChuDe());
            if (list.isEmpty() || !list.get(0).getTuTiengAnh().equals("test_word")) {
                System.out.println("Thất bại khi SelectByChuDe!");
                // Cleanup
                cdDao.delete(cdFound.getMaChuDe());
                return false;
            }
            
            // Test delete
            boolean deleted = tvDao.delete(list.get(0).getMaTu());
            if (!deleted) {
                System.out.println("Thất bại khi Delete!");
                // Cleanup
                cdDao.delete(cdFound.getMaChuDe());
                return false;
            }
            
            // Cleanup chủ đề tạm
            cdDao.delete(cdFound.getMaChuDe());
            System.out.println("Thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Thất bại! Chi tiết lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean testKetQuaKiemTraDAO() {
        System.out.print("Đang kiểm tra KetQuaKiemTraDAO... ");
        try {
            TaiKhoanDAO tkDao = new TaiKhoanDAO();
            ChuDeDAO cdDao = new ChuDeDAO();
            KetQuaKiemTraDAO kqDao = new KetQuaKiemTraDAO();
            
            // Tạo tài khoản và chủ đề tạm
            TaiKhoan tempUser = new TaiKhoan(0, "testuser_kq", "hash", "User");
            tkDao.insert(tempUser);
            TaiKhoan userFound = tkDao.selectByUsername("testuser_kq");
            
            ChuDe tempCd = new ChuDe(0, "ChuDe_kq");
            cdDao.insert(tempCd);
            ChuDe cdFound = cdDao.selectByName("ChuDe_kq");
            
            // Test insert kết quả kiểm tra
            KetQuaKiemTra kq = new KetQuaKiemTra(0, userFound.getMaTaiKhoan(), cdFound.getMaChuDe(), 8, 10, null);
            boolean inserted = kqDao.insert(kq);
            if (!inserted) {
                System.out.println("Thất bại khi Insert kết quả!");
                // Cleanup
                cdDao.delete(cdFound.getMaChuDe());
                tkDao.delete(userFound.getMaTaiKhoan());
                return false;
            }
            
            // Test count và get trung bình
            int count = kqDao.countChuDeDaLam(userFound.getMaTaiKhoan());
            double maxScore = kqDao.getMaxDiemChuDe(userFound.getMaTaiKhoan(), cdFound.getMaChuDe());
            
            if (count != 1 || maxScore != 8.0) {
                System.out.println("Thất bại khi tính toán Thống kê!");
                // Cleanup
                cdDao.delete(cdFound.getMaChuDe());
                tkDao.delete(userFound.getMaTaiKhoan());
                return false;
            }
            
            // Cleanup (do cascade delete tài khoản và chủ đề nên kết quả sẽ tự động xóa trong DB)
            cdDao.delete(cdFound.getMaChuDe());
            tkDao.delete(userFound.getMaTaiKhoan());
            
            System.out.println("Thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Thất bại! Chi tiết lỗi: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
