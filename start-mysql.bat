@echo off
chcp 65001 > nul
echo ==================================================
echo   KHỞI CHẠY MYSQL SERVER APPHOCTU
echo ==================================================
echo.

set MYSQL_BIN="C:\Program Files\MySQL\MySQL Server 8.4\bin\mysqld.exe"
set DATA_DIR="c:\Users\Kyz\Documents\LTJava\db_data"

tasklist /fi "imagename eq mysqld.exe" | find /i "mysqld.exe" > nul
if %errorlevel% equ 0 (
    echo [INFO] MySQL Server đang chạy sẵn rồi.
) else (
    echo Đang khởi chạy MySQL Server...
    start "" /b %MYSQL_BIN% --datadir=%DATA_DIR%
    echo [SUCCESS] Đã kích hoạt chạy ngầm MySQL Server!
)

echo.
pause
