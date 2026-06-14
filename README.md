# AppHocTu - Ứng Dụng Học Từ Vựng Tiếng Anh (Java Swing + MySQL)

Chào mừng bạn đến với **AppHocTu**, ứng dụng hỗ trợ học tập và quản lý từ vựng tiếng Anh trên giao diện đồ họa Java Swing, kết nối và đồng bộ hóa dữ liệu thời gian thực với hệ quản trị cơ sở dữ liệu MySQL.

---

## 1. Các Tính Năng Chính
* **Phân hệ Học viên (User):**
  * Theo dõi tổng quan tiến trình học tập, thống kê số chủ đề đã làm và điểm số trung bình.
  * Xem danh sách từ vựng theo từng chủ đề, tìm kiếm và xuất dữ liệu ra file văn bản `.txt`.
  * Học từ vựng thông qua Flashcards (thẻ ghi nhớ) có tính năng lật xem nghĩa tiếng Việt và điều hướng linh hoạt.
  * Làm bài kiểm tra trắc nghiệm ngẫu nhiên (chọn lọc câu hỏi ngẫu nhiên và xáo trộn đáp án nhiễu), tự động chấm điểm và lưu lịch sử thi.
* **Phân hệ Quản trị viên (Admin):**
  * Quản lý danh sách tài khoản người học (Thêm, Sửa, Xóa).
  * Quản lý danh mục chủ đề (Thêm, Sửa, Xóa).
  * Quản lý kho từ vựng thuộc từng chủ đề (Thêm, Sửa, Xóa).
  * Xem báo cáo thống kê danh sách học viên đã hoàn thành kiểm tra theo từng chủ đề kèm theo điểm số chi tiết.

---

## 2. Yêu Cầu Hệ Thống (Prerequisites)
* **Java Development Kit (JDK)**: Phiên bản 17 trở lên.
* **Apache Maven**: Dùng để quản lý thư viện và biên dịch dự án.
* **MySQL Server**: Phiên bản 8.0 trở lên.

---

## 3. Hướng Dẫn Cài Đặt & Thiết Lập CSDL

Ứng dụng hỗ trợ hai hình thức khởi động và cấu hình cơ sở dữ liệu tùy thuộc vào nhu cầu của bạn:

### Cách 1: Thiết lập tự động thông qua Script chạy nhanh (Khuyên dùng trên Windows)
Dự án được cấu hình sẵn môi trường MySQL cục bộ không cần cài đặt phức tạp:
1. Đảm bảo bạn đã cài đặt MySQL Server trên máy tính.
2. Click đúp vào **`run-app.bat`**: File script sẽ tự động kiểm tra, khởi tạo MySQL Server trỏ vào thư mục dữ liệu cục bộ [db_data](db_data), import cơ sở dữ liệu mẫu, tự động biên dịch và chạy ứng dụng.
3. Nếu muốn khởi chạy riêng dịch vụ cơ sở dữ liệu, bạn có thể click đúp vào **`start-mysql.bat`**.

### Cách 2: Thiết lập thủ công (Mọi hệ điều hành)
1. **Khởi tạo CSDL**: Mở công cụ quản trị MySQL của bạn (MySQL Workbench, phpMyAdmin hoặc CLI) và import tệp tin **[database/schema.sql](database/schema.sql)**. Lệnh này sẽ tự động tạo cơ sở dữ liệu `apphoctu`, thiết lập các bảng và nạp dữ liệu mẫu ban đầu.
2. **Cấu hình kết nối**: Mở tệp tin **[DBConnection.java](src/main/java/utils/DBConnection.java)** và cập nhật thông tin tài khoản MySQL của bạn:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/apphoctu?useSSL=false&serverTimezone=UTC";
   private static final String USER = "tên_đăng_nhập";
   private static final String PASSWORD = "mật_khẩu_của_bạn";
   ```

---

## 4. Hướng Dẫn Chạy Ứng Dụng

### Chạy bằng dòng lệnh Maven
Thực hiện các lệnh sau tại thư mục gốc của dự án:
```powershell
# Biên dịch dự án
mvn compile

# Khởi chạy ứng dụng đồ họa
mvn exec:java -Dexec.mainClass="main.AppHocTu"
```

### Chạy bằng IDE (VSCode / NetBeans / IntelliJ)
1. Mở thư mục dự án `LTJava` bằng IDE của bạn.
2. Tìm đến tệp chạy chính tại: **`src/main/java/main/AppHocTu.java`**.
3. Nhấp chọn **Run** để khởi động ứng dụng.

---

## 5. Tài Khoản Đăng Nhập Mặc Định
* **Tài khoản Admin (Quản trị viên):** 
  * Username: `admin` | Mật khẩu: `admin123`
* **Tài khoản User (Học viên):** 
  * Username: `user` | Mật khẩu: `user123`

---

## 6. Tài Liệu Hướng Dẫn Kỹ Thuật
Các tài liệu mô tả chi tiết về cấu trúc mã nguồn được đặt trong thư mục **[docs](docs)**:
* **[Cấu trúc thư mục & mã nguồn](docs/structure.md)**: Chi tiết cấu trúc các package, các lớp (Class), và phân tầng thiết kế của dự án AppHocTu.
