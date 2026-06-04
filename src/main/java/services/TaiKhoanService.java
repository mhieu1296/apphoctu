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
        if (password.length==0 || "".equals(username)) {
            JOptionPane.showMessageDialog(null, "Username và mật khẩu không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        } else {
            if (1 == 1) { // viết logic kiểm tra mật khẩu trong db vào đây
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                new MainFrame().setVisible(true);
                FlatLightLaf.setup();
                mainframe = new MainFrame();
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
