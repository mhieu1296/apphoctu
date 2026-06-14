# Cấu trúc Thư mục và Chức năng Mã nguồn AppHocTu

Tài liệu này mô tả chi tiết sơ đồ thư mục của dự án **AppHocTu** và vai trò nhiệm vụ của từng thành phần trong mã nguồn.

---

## 1. Sơ đồ thư mục dự án

```text
LTJava/
│
├── pom.xml                 # Cấu hình Maven (Quản lý thư viện dependency và build)
├── README.md               # Hướng dẫn cài đặt và sử dụng tổng quan
│
├── database/
│   └── schema.sql          # Script SQL khởi tạo cơ sở dữ liệu và dữ liệu mẫu ban đầu
│
├── docs/
│   └── structure.md        # Tài liệu cấu trúc thư mục này
│
├── src/
│   └── main/
│       ├── java/
│       │   ├── main/
│       │   │   └── AppHocTu.java           # Điểm khởi chạy chương trình (Main class)
│       │   │
│       │   ├── models/                     # Các lớp định nghĩa thực thể dữ liệu (Java POJO)
│       │   │   ├── TaiKhoan.java           # Thông tin tài khoản người dùng
│       │   │   ├── ChuDe.java              # Thông tin chủ đề từ vựng
│       │   │   ├── TuVung.java             # Thông tin từ vựng tiếng Anh
│       │   │   └── KetQuaKiemTra.java      # Lịch sử thi/làm bài trắc nghiệm
│       │   │
│       │   ├── dao/                        # Lớp truy cập cơ sở dữ liệu (Database Access Objects)
│       │   │   ├── TaiKhoanDAO.java        # Thực thi CRUD bảng tai_khoan
│       │   │   ├── ChuDeDAO.java           # Thực thi CRUD bảng chu_de
│       │   │   ├── TuVungDAO.java          # Thực thi CRUD bảng tu_vung
│       │   │   └── KetQuaKiemTraDAO.java   # Thực thi CRUD bảng ket_qua_kiem_tra
│       │   │
│       │   ├── services/                   # Các nghiệp vụ xử lý logic trung gian
│       │   │   └── TaiKhoanService.java    # Xử lý đăng ký, đăng nhập và mã hóa mật khẩu
│       │   │
│       │   ├── ui/                         # Thành phần giao diện đồ họa (Swing GUI)
│       │   │   ├── MainFrame.java          # Cửa sổ chính quản lý điều phối các màn hình con
│       │   │   │
│       │   │   ├── frame/
│       │   │   │   └── LoginFrame.java     # Cửa sổ đăng nhập của hệ thống
│       │   │   │
│       │   │   ├── dialog/                 # Các Dialog biểu mẫu tương tác động (CRUD)
│       │   │   │   ├── CRUDTaiKhoan.java   # Dialog Thêm/Sửa thông tin tài khoản
│       │   │   │   ├── CRUDChuDe.java      # Dialog Thêm/Sửa chủ đề học tập
│       │   │   │   └── CRUDTu.java         # Dialog Thêm/Sửa từ vựng
│       │   │   │
│       │   │   └── panel/                  # Các màn hình con đặt trong CardLayout
│       │   │       ├── LoginPanel.java     # Màn hình đăng nhập
│       │   │       ├── MenuAdminPanel.java # Menu điều hướng cho tài khoản Admin
│       │   │       ├── MenuUserPanel.java  # Menu điều hướng cho tài khoản User
│       │   │       ├── QuanLyTaiKhoan.java # Giao diện bảng quản trị tài khoản
│       │   │       ├── QuanLyChuDe.java    # Giao diện bảng quản trị danh mục chủ đề
│       │   │       ├── QuanLyTu.java       # Giao diện bảng quản trị kho từ vựng
│       │   │       ├── DanhSachTKDaLamChuDePanel.java # Bảng thống kê điểm số người học
│       │   │       │
│       │   │       ├── TongQuan.java       # Dashboard xem tiến trình, kết quả học viên
│       │   │       ├── ChuDe.java          # Giao diện học viên chọn và xem từ theo chủ đề
│       │   │       ├── HocTu.java          # Giao diện học từ vựng bằng Flashcards tương tác
│       │   │       └── GiaoDienLamBai.java # Giao diện làm bài kiểm tra trắc nghiệm
│       │   │
│       │   └── utils/                      # Tiện ích bổ trợ hệ thống
│       │       ├── DBConnection.java       # Quản lý kết nối MySQL qua JDBC
│       │       └── XuatTXT.java            # Hỗ trợ ghi dữ liệu ra file text cục bộ
│       │
│       └── resources/
│           └── icon/                       # File ảnh icon định dạng PNG/SVG cho GUI
```

---

## 2. Mô tả vai trò các tầng thiết kế (Design Layers)

Hệ thống được thiết kế theo cấu trúc phân tầng rõ rệt:

1. **Tầng Giao Diện (Presentation Layer - `ui`)**:
   - Sử dụng Java Swing kết hợp giao diện phẳng **FlatLaf** để tối ưu hóa trải nghiệm người dùng.
   - Quản lý các sự kiện click chuột, cập nhật các bảng hiển thị động và thu thập thông tin người dùng từ biểu mẫu.
2. **Tầng Nghiệp Vụ (Service Layer - `services`)**:
   - Đóng vai trò trung gian xử lý nghiệp vụ logic. Ví dụ: Mã hóa SHA-256 đối với mật khẩu người dùng trước khi chuyển giao đến tầng CSDL hoặc so sánh khi xác thực đăng nhập.
3. **Tầng Cơ Sở Dữ Liệu (DataAccess Layer - `dao`)**:
   - Thực thi trực tiếp các câu lệnh SQL (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) thông qua API kết nối **JDBC** để làm việc trực tiếp với MySQL.
4. **Tầng Mô Hình Dữ Liệu (Model Layer - `models`)**:
   - Chứa các POJO (Plain Old Java Objects) làm khuôn mẫu định hình dữ liệu trao đổi giữa các tầng trong ứng dụng.
