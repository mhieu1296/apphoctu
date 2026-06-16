package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.swing.JOptionPane;
import models.TuVung;

public class XuatTXT {
    public static void xuatDanhSachTuVung(String tenCD, List<TuVung> list) {
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Chủ đề này chưa có từ vựng để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        File dir = new File("report");
        if (!dir.exists()) dir.mkdirs();
        
        File file = new File(dir, "words_export_" + tenCD.toLowerCase().replace(" ", "_") + ".txt");
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.println("DANH SÁCH TỪ VỰNG CHỦ ĐỀ: " + tenCD.toUpperCase());
            writer.println("==========================================");
            int stt = 1;
            for (TuVung tv : list) {
                writer.printf("%02d. %-20s : %s\n", stt++, tv.getTuTiengAnh(), tv.getNghiaTiengViet());
            }
            JOptionPane.showMessageDialog(null, "Xuất file thành công!\nĐường dẫn: " + file.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Xuất file thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
