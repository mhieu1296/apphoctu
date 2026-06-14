@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

echo ==================================================
echo   KHỞI CHẠY THỬ NGHIỆM CƠ SỞ DỮ LIỆU APPHOCTU
echo ==================================================
echo.

echo [INFO] Đang tìm kiếm trình biên dịch Maven (mvn)...

:: 1. Kiểm tra lệnh mvn toàn cục
where mvn > nul 2>nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện Maven toàn cục (System Path).
    set "MVN_CMD=mvn"
    goto run_test
)

:: 2. Tìm kiếm trong các thư mục của IntelliJ IDEA
echo [INFO] Không tìm thấy Maven trong biến môi trường. Đang tìm kiếm Maven tích hợp của IntelliJ...
set "INTELIJ_DIR=C:\Program Files\JetBrains"
if exist "!INTELIJ_DIR!" (
    for /r "!INTELIJ_DIR!" %%f in (mvn.cmd) do (
        if exist "%%f" (
            set "MVN_CMD=%%f"
            echo [INFO] Tìm thấy Maven của IntelliJ tại: "!MVN_CMD!"
            goto run_test
        )
    )
)

:: Nếu không tìm thấy Maven
echo [ERROR] Không thể tự động tìm thấy Maven (mvn) trên máy tính của bạn.
echo Vui lòng cài đặt Maven hoặc chạy kiểm thử trực tiếp bằng IDE của bạn.
pause
exit /b

:run_test
echo.
echo [INFO] Đang biên dịch và chạy kiểm thử kết nối CSDL...
call "!MVN_CMD!" compile exec:java "-Dexec.mainClass=main.DBTestRunner"
pause
