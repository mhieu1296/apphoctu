# Báo cáo Phân tích và Đánh giá Repository AppHocTu

Tài liệu này cung cấp cái nhìn tổng quan về cấu trúc, tính năng hiện tại, các thiếu sót kỹ thuật của dự án **AppHocTu**, đồng thời đưa ra phương án kết nối hệ thống với cơ sở dữ liệu MySQL.

---

## 1. Cấu trúc thư mục (Directory Structure)

Dự án được cấu trúc theo mô hình chuẩn của một ứng dụng Maven Java Swing, cụ thể như sau:

```text
LTJava/
│
├── pom.xml                 # File cấu hình Maven (quản lý thư viện phụ thuộc)
├── nbactions.xml           # File cấu hình chạy ứng dụng trên NetBeans IDE
│
└── src/
    └── main/
        ├── java/
        │   ├── main/
        │   │   └── AppHocTu.java           # Điểm khởi chạy ứng dụng (Main class)
        │   │
        │   ├── models/                     # Lớp chứa cấu trúc dữ liệu đối tượng
        │   │   ├── TaiKhoan.java
        │   │   ├── ChuDe.java
        │   │   ├── TuVung.java
        │   │   └── KetQuaKiemTra.java
        │   │
        │   ├── dao/                        # Lớp truy cập cơ sở dữ liệu (Database Access Objects)
        │   │   ├── TaiKhoanDAO.java        # (Hiện đang trống)
        │   │   ├── ChuDeDAO.java           # (Hiện đang trống)
        │   │   ├── TuVungDAO.java          # (Hiện đang trống)
        │   │   └── KetQuaKiemTraDAO.java   # (Hiện đang trống)
        │   │
        │   ├── services/                   # Các nghiệp vụ xử lý logic
        │   │   └── TaiKhoanService.java
        │   │
        │   ├── ui/                         # Các thành phần giao diện người dùng
        │   │   ├── MainFrame.java          # Cửa sổ chính chia Layout (CardLayout)
        │   │   ├── MainFrame.form
        │   │   │
        │   │   ├── frame/
        │   │   │   ├── LoginFrame.java     # Khung đăng nhập chính
        │   │   │   └── LoginFrame.form
        │   │   │
        │   │   ├── dialog/                 # Các Dialog bật lên khi CRUD
        │   │   │   ├── CRUDTaiKhoan.java
        │   │   │   ├── CRUDChuDe.java
        │   │   │   └── CRUDTu.java
        │   │   │
        │   │   └── panel/                  # Giao diện con hiển thị chi tiết nghiệp vụ
        │   │       ├── LoginPanel.java             # Giao diện nhập Tài khoản & Mật khẩu
        │   │       ├── MenuAdminPanel.java         # Thanh menu bên trái dành cho Admin
        │   │       ├── MenuUserPanel.java          # Thanh menu bên trái dành cho User thường
        │   │       ├── QuanLyTaiKhoan.java         # Quản lý Tài khoản (Bảng, nút CRUD)
        │   │       ├── ThemTaiKhoan.java           # Form thêm tài khoản mới
        │   │       ├── CapNhatTaiKhoan.java        # Form cập nhật tài khoản
        │   │       ├── QuanLyChuDe.java            # Quản lý Chủ đề từ vựng
        │   │       ├── ThemChuDe.java              # Form thêm chủ đề mới
        │   │       ├── CapNhatChuDe.java           # Form cập nhật chủ đề
        │   │       ├── QuanLyTu.java               # Quản lý Từ vựng
        │   │       ├── ThemTu.java                 # Form thêm từ vựng mới
        │   │       ├── CapNhatTu.java              # Form cập nhật từ vựng
        │   │       └── DanhSachTKDaLamChuDePanel.java # Báo cáo kết quả kiểm tra
        │   │
        │   └── utils/                      # Công cụ hỗ trợ hệ thống
        │       ├── DBConnection.java       # Kết nối CSDL (Hiện đang trống)
        │       └── XuatTXT.java            # Xuất dữ liệu ra file text (Hiện đang trống)
        │
        └── resources/
            └── icon/                       # Các tài nguyên hình ảnh, biểu tượng (Icons)
```

---

## 2. Các chức năng dự án đang thực hiện (Implemented Features)

Hiện tại, dự án chủ yếu mới hoàn thiện phần **thiết kế giao diện (UI)** và một số xử lý logic cơ bản:

1. **Giao diện quản lý của Admin**:
   - Sử dụng thư viện **FlatLaf** (Flat Light Look and Feel) để giao diện nhìn hiện đại, phẳng và sạch sẽ hơn mặc định của Java Swing.
   - Sử dụng **CardLayout** trên `MainFrame` để chuyển đổi nhanh giữa các màn hình quản trị.
   - Các chức năng quản trị bao gồm: xem danh sách, lọc, sắp xếp, mở dialog thêm/sửa/xóa tài khoản, chủ đề và từ vựng.
2. **Bảo mật mật khẩu sơ khai**:
   - Trong `LoginPanel.java` có tích hợp hàm `hashPassword(char[] password)` sử dụng mã hóa **SHA-256** để băm mật khẩu trước khi lưu trữ hoặc kiểm tra.
   - Hỗ trợ nút ẩn/hiện mật khẩu động trực quan.
3. **Menu Điều hướng**:
   - Đã liên kết các nút bấm trên `MenuAdminPanel` để chuyển đổi qua lại giữa: *Quản lý chủ đề*, *Quản lý từ vựng*, *Quản lý tài khoản*, và *Danh sách tài khoản đã làm chủ đề*.

---

## 3. Các thiếu sót và vấn đề hiện tại (Shortcomings & Bottlenecks)

### 3.1. Thiếu kết nối Cơ sở dữ liệu (CSDL) thực tế
* **Trạng thái**: Các lớp trong package `dao` (`TaiKhoanDAO`, `ChuDeDAO`, `TuVungDAO`, `KetQuaKiemTraDAO`) hoàn toàn trống rỗng và chỉ có chú thích (comments) nhắc nhở lập trình viên tự viết.
* **Hệ quả**: Dữ liệu hiển thị trên các bảng quản lý (`tableTaiKhoan`, `tableChuDe`, `tableTu`) là dữ liệu tĩnh (hardcoded mock data) được định nghĩa sẵn trong mã nguồn giao diện Swing. Mọi hành động Thêm/Sửa/Xóa chỉ hiển thị thông báo giả lập (`JOptionPane`) mà không tác động tới bất cứ nguồn lưu trữ nào.
* **Kết nối**: Lớp `utils.DBConnection` hoàn toàn rỗng. File `pom.xml` chưa hề khai báo thư viện Driver kết nối CSDL MySQL (`mysql-connector-j`).

### 3.2. Nghiệp vụ logic dở dang (Mocked Logic)
* **Xác thực**: Hàm `dangNhap` trong `TaiKhoanService.java` đang kiểm tra điều kiện đăng nhập bằng biểu thức luôn đúng `if (1 == 1)`, do đó nhập bất kỳ thông tin nào cũng được báo đăng nhập thành công.
* **Vai trò (Role)**: Phương thức khởi tạo `khoiTaoCardLayout` trong `MainFrame.java` cũng ép buộc sử dụng giao diện admin bằng biểu thức luôn đúng `if (1 == 1)`.

### 3.3. Các chức năng của vai trò User (Học viên) hoàn toàn chưa được lập trình
* `MenuUserPanel.java` định nghĩa các nút chức năng dành cho học viên như: *Tổng quan*, *Danh sách chủ đề*, *Học 1 chủ đề*, *Làm bài kiểm tra*.
* Tuy nhiên, **không có sự kiện click chuột nào được liên kết** cho các nút này và dự án cũng **thiếu hoàn toàn các Panel giao diện học tập** tương ứng của học viên.

### 3.4. Sai sót cấu hình dự án
* **Sai mainClass trong file pom.xml**:
  ```xml
  <exec.mainClass>com.mycompany.main.AppHocTu</exec.mainClass>
  ```
  Nhưng thực tế Package của lớp `AppHocTu.java` chỉ là `main;` chứ không có `com.mycompany.main;`. Điều này khiến lệnh chạy ứng dụng chuẩn của Maven (`mvn exec:java`) sẽ thất bại nếu không chạy thông qua bộ công cụ tích hợp NetBeans (sử dụng cấu hình bổ trợ `nbactions.xml`).
* **Phiên bản Java đích quá cao**:
  `pom.xml` đang đặt `<maven.compiler.release>24</maven.compiler.release>` (Java 24). Đây là phiên bản chưa phổ biến chính thức rộng rãi và có thể gây lỗi biên dịch trên môi trường chạy Java LTS thông thường (như JDK 17 hay JDK 21).

---

## 4. Kế hoạch kết nối MySQL Database chi tiết

Để chuyển đổi dự án này từ trạng thái giao diện tĩnh sang hệ thống chạy thực tế với cơ sở dữ liệu MySQL, chúng ta cần triển khai các bước sau:

### Bước 4.1: Bổ sung thư viện JDBC Connector vào `pom.xml`
Thêm thư viện Driver MySQL vào phần `<dependencies>`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

### Bước 4.2: Thiết kế cơ sở dữ liệu MySQL (Database Schema)
Chúng ta sẽ tạo cơ sở dữ liệu có tên `apphoctu` chứa 4 bảng liên kết tương ứng với các Model trong mã nguồn:

```sql
CREATE DATABASE IF NOT EXISTS apphoctu DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE apphoctu;

-- 1. Bảng Tài khoản
CREATE TABLE tai_khoan (
    ma_tai_khoan INT AUTO_INCREMENT PRIMARY KEY,
    ten_dang_nhap VARCHAR(50) NOT NULL UNIQUE,
    mat_khau VARCHAR(256) NOT NULL, -- Lưu trữ chuỗi Hash SHA-256
    vai_tro VARCHAR(20) NOT NULL DEFAULT 'User' -- 'Admin' hoặc 'User'
);

-- 2. Bảng Chủ đề
CREATE TABLE chu_de (
    ma_chu_de INT AUTO_INCREMENT PRIMARY KEY,
    ten_chu_de VARCHAR(100) NOT NULL UNIQUE
);

-- 3. Bảng Từ vựng
CREATE TABLE tu_vung (
    ma_tu INT AUTO_INCREMENT PRIMARY KEY,
    tu_tieng_anh VARCHAR(100) NOT NULL,
    nghia_tieng_viet VARCHAR(200) NOT NULL,
    ma_chu_de INT NOT NULL,
    FOREIGN KEY (ma_chu_de) REFERENCES chu_de(ma_chu_de) ON DELETE CASCADE
);

-- 4. Bảng Kết quả kiểm tra
CREATE TABLE ket_qua_kiem_tra (
    ma_ket_qua INT AUTO_INCREMENT PRIMARY KEY,
    ma_tai_khoan INT NOT NULL,
    ma_chu_de INT NOT NULL,
    diem_so INT NOT NULL,
    tong_so_cau INT NOT NULL,
    thoi_diem_lam_bai TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ma_tai_khoan) REFERENCES tai_khoan(ma_tai_khoan) ON DELETE CASCADE,
    FOREIGN KEY (ma_chu_de) REFERENCES chu_de(ma_chu_de) ON DELETE CASCADE
);
```

### Bước 4.3: Thiết lập kết nối cơ sở dữ liệu trong `DBConnection.java`
Sử dụng JDBC DriverManager chuẩn để kết nối MySQL:
```java
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3006/apphoctu?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Thay đổi theo máy của bạn
    private static final String PASSWORD = "yourpassword"; // Thay đổi theo máy của bạn

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```

### Bước 4.4: Lập trình mã hóa truy vấn trong các lớp DAO
Hiện thực hóa các thao tác SQL cơ bản (CRUD) trong DAO. Ví dụ với `TaiKhoanDAO.java`:
* `selectAll()`: Lấy danh sách tài khoản đổ vào bảng.
* `insert(TaiKhoan tk)`: Thêm tài khoản mới (với mật khẩu đã hash SHA-256).
* `update(TaiKhoan tk)`: Cập nhật thông tin/vai trò.
* `delete(int id)`: Xóa tài khoản.
* `findByUsername(String username)`: Tìm kiếm tài khoản khi đăng nhập.

Tương tự cho `ChuDeDAO`, `TuVungDAO`, và `KetQuaKiemTraDAO`.

### Bước 4.5: Kết nối Giao diện người dùng (UI) với Database
* Thay thế dữ liệu khởi tạo tĩnh trong các constructor `initComponents()` bằng việc gọi danh sách từ DAO và nạp vào `DefaultTableModel`.
* Lập trình sự kiện click chuột cho các nút Thêm/Sửa/Xóa để thu thập dữ liệu từ các form cập nhật, gọi DAO lưu vào CSDL, rồi tải lại bảng hiển thị (refresh table).
* Cập nhật `TaiKhoanService` để đối chiếu chuỗi mật khẩu người dùng nhập (sau khi hash) với mật khẩu lưu trong bảng `tai_khoan` từ database MySQL.
