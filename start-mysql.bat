@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

echo ==================================================
echo   KHỞI CHẠY MYSQL SERVER APPHOCTU CỤC BỘ
echo ==================================================
echo.

:: 1. Xác định thư mục lưu trữ DB cục bộ (sử dụng đường dẫn tương đối)
set "DATA_DIR=%~dp0db_data"

:: 2. Kiểm tra xem MySQL đã chạy trên cổng 3306 chưa
netstat -ano | findstr "127.0.0.1:3306" > nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện dịch vụ MySQL đã hoạt động sẵn trên cổng 3306.
    goto end
)
netstat -ano | findstr "[::]:3306" > nul
if %errorlevel% equ 0 (
    echo [INFO] Phát hiện dịch vụ MySQL đã hoạt động sẵn trên cổng 3306.
    goto end
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
    echo [SUCCESS] Đã kích hoạt chạy ngầm MySQL Server!
) else (
    echo [ERROR] Không tìm thấy mysqld.exe cài đặt trên hệ thống. 
    echo Vui lòng cài đặt MySQL hoặc khởi động dịch vụ MySQL trên máy tính của bạn trước.
)

:end
echo.
pause
