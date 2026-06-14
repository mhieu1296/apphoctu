@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

echo ==================================================
echo   KHỞI ĐỘNG ỨNG DỤNG APPHOCTU (JAVA SWING + MYSQL)
echo ==================================================
echo.

:: 1. Xác định thư mục lưu trữ DB cục bộ (sử dụng đường dẫn tương đối)
set "DATA_DIR=%~dp0db_data"

:: 2. Kiểm tra xem MySQL đã chạy trên cổng 3306 chưa
netstat -ano | findstr "127.0.0.1:3306" > nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện dịch vụ MySQL đã hoạt động sẵn trên cổng 3306.
    goto check_maven
)
netstat -ano | findstr "[::]:3306" > nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện dịch vụ MySQL đã hoạt động sẵn trên cổng 3306.
    goto check_maven
)

:: Nếu chưa chạy, tìm kiếm tệp mysqld.exe
echo [INFO] MySQL chưa chạy. Đang tự động tìm kiếm mysqld.exe trên hệ thống...
set "MYSQL_BIN="

:: Thử tìm trong biến môi trường PATH
where mysqld.exe > nul 2>nul
if %errorlevel% equ 0 (
    for /f "tokens=*" %%i in ('where mysqld.exe') do (
        set "MYSQL_BIN=%%i"
        goto start_mysql
    )
)

:: Thử tìm ở các đường dẫn cài đặt mặc định phổ biến
for %%v in (8.4 8.0 8.1 8.2 8.3 9.0 9.1) do (
    if exist "C:\Program Files\MySQL\MySQL Server %%v\bin\mysqld.exe" (
        set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server %%v\bin\mysqld.exe"
        goto start_mysql
    )
)

:start_mysql
if defined MYSQL_BIN (
    echo [INFO] Tìm thấy MySQL tại: "!MYSQL_BIN!"
    echo Đang khởi chạy MySQL Server cục bộ trỏ đến thư mục dữ liệu: "%DATA_DIR%"...
    start "" /b "!MYSQL_BIN!" --datadir="%DATA_DIR%"
    :: Đợi 4 giây để MySQL Server khởi động
    ping 127.0.0.1 -n 5 > nul
) else (
    echo [WARNING] Không tìm thấy mysqld.exe cài đặt trên hệ thống. 
    echo [WARNING] Nếu bạn đã cài đặt MySQL dưới dạng Service, vui lòng đảm bảo Service đang chạy.
)

:check_maven
echo.
echo [INFO] Đang tìm kiếm trình biên dịch Maven (mvn)...

:: 1. Kiểm tra lệnh mvn toàn cục
where mvn > nul 2>nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện Maven toàn cục (System Path).
    set "MVN_CMD=mvn"
    goto run_app
)

:: 2. Tìm kiếm trong các thư mục của IntelliJ IDEA
echo [INFO] Không tìm thấy Maven trong biến môi trường. Đang tìm kiếm Maven tích hợp của IntelliJ...
set "INTELIJ_DIR=C:\Program Files\JetBrains"
if exist "!INTELIJ_DIR!" (
    for /r "!INTELIJ_DIR!" %%f in (mvn.cmd) do (
        if exist "%%f" (
            set "MVN_CMD=%%f"
            echo [INFO] Tìm thấy Maven của IntelliJ tại: "!MVN_CMD!"
            goto run_app
        )
    )
)

:: Nếu không tìm thấy Maven
echo [ERROR] Không thể tự động tìm thấy Maven (mvn) trên máy tính của bạn.
echo.
echo Vui lòng thực hiện một trong các cách sau:
echo 1. Cài đặt Apache Maven và thêm vào biến môi trường PATH.
echo 2. Hoặc mở dự án trực tiếp bằng các IDE như NetBeans, VSCode, IntelliJ IDEA và bấm nút "Run" để chạy ứng dụng.
echo.
pause
exit /b

:run_app
echo.
echo [INFO] Đang biên dịch và khởi chạy ứng dụng AppHocTu...
call "!MVN_CMD!" compile exec:java "-Dexec.mainClass=main.AppHocTu"
pause
