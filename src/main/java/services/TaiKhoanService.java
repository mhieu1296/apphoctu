package services;

import dao.TaiKhoanDAO;
import models.TaiKhoan;
import java.util.List;

public class TaiKhoanService {
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    
    public TaiKhoan authenticate(String username, char[] password) {
        String hashedPassword = ui.panel.LoginPanel.hashPassword(password);
        System.out.println("[DEBUG LOGIN] Đang đăng nhập với username: '" + username + "'");
        
        if (password.length == 0 || "".equals(username)) {
            return null;
        }
        
        TaiKhoan user = taiKhoanDAO.selectByUsername(username);
        if (user != null && user.getMatKhau().equals(hashedPassword)) {
            return user;
        }
        return null;
    }
    
    public List<TaiKhoan> getAllAccounts() {
        return taiKhoanDAO.selectAll();
    }
    
    public TaiKhoan getAccountByUsername(String username) {
        return taiKhoanDAO.selectByUsername(username);
    }
    
    public boolean addAccount(String username, String password, String role) {
        if (getAccountByUsername(username) != null) {
            return false;
        }
        // Hash password before inserting
        char[] pwdChars = password.toCharArray();
        String hashedPassword = ui.panel.LoginPanel.hashPassword(pwdChars);
        TaiKhoan tk = new TaiKhoan(0, username, hashedPassword, role);
        return taiKhoanDAO.insert(tk);
    }
    
    public boolean updateAccount(TaiKhoan account) {
        return taiKhoanDAO.update(account);
    }
    
    public boolean deleteAccount(int id) {
        return taiKhoanDAO.delete(id);
    }
    
    public int countTotal() {
        return taiKhoanDAO.countTotal();
    }
    
    public int countAdmins() {
        return taiKhoanDAO.countAdmins();
    }
}
