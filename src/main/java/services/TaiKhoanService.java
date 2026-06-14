/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ui.MainFrame;
import ui.panel.LoginPanel;
import static ui.panel.LoginPanel.hashPassword;

/**
 *
 * @author Admin
 */
public class TaiKhoanService {
    private MainFrame mainframe;
    private LoginPanel loginpanel;
    
    public TaiKhoanService(LoginPanel loginpanel) {
        this.loginpanel = loginpanel;
    }
    
    public void dangNhap(String username, char[] password){
        String hashedPassword = hashPassword(password); // để lưu vào csdl
        System.out.println("[DEBUG LOGIN] Đang đăng nhập với username: '" + username + "'");
        System.out.println("[DEBUG LOGIN] Mật khẩu đã hash: '" + hashedPassword + "'");
        if (password.length==0 || "".equals(username)) {
            JOptionPane.showMessageDialog(null, "Username và mật khẩu không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
            dao.TaiKhoanDAO taiKhoanDAO = new dao.TaiKhoanDAO();
            models.TaiKhoan user = taiKhoanDAO.selectByUsername(username);
            
            if (user != null) {
                System.out.println("[DEBUG LOGIN] Tìm thấy user trong DB: '" + user.getTenDangNhap() + "'");
                System.out.println("[DEBUG LOGIN] Mật khẩu trong DB:      '" + user.getMatKhau() + "'");
                System.out.println("[DEBUG LOGIN] Khớp mật khẩu? " + user.getMatKhau().equals(hashedPassword));
            } else {
                System.out.println("[DEBUG LOGIN] KHÔNG tìm thấy user '" + username + "' trong DB!");
            }
            
            if (user != null && user.getMatKhau().equals(hashedPassword)) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                FlatLightLaf.setup();
                mainframe = new MainFrame(user);
                mainframe.setVisible(true);
                mainframe.setResizable(false);
                mainframe.setLocationRelativeTo(null);
                Window window = SwingUtilities.getWindowAncestor(loginpanel);
                mainframe.getContentPane().setBackground(Color.WHITE);
                window.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public void dangXuat(){
        
    }
}
