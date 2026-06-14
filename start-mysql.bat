@echo off
setlocal enabledelayedexpansion

echo ==================================================
echo   KHOI CHAY MYSQL SERVER APPHOCTU CUC BO
echo ==================================================
echo.

:: 1. Xac dinh thu muc luu tru DB cuc bo (su dung duong dan tuong doi)
set "DATA_DIR=%~dp0db_data"

:: 2. Kiem tra xem MySQL da chay tren cong 3306 chua
netstat -ano | findstr "3306" > nul
if %errorlevel% equ 0 goto mysql_is_running

:: Neu chua chay, tim kiem tep mysqld.exe
echo [INFO] MySQL chua chay. Dang tu dong tim kiem mysqld.exe...
set "MYSQL_BIN="

:: Thu tim trong bien moi truong PATH
where mysqld.exe > nul 2>nul
if %errorlevel% neq 0 goto check_default_paths
for /f "tokens=*" %%i in ('where mysqld.exe') do (
    set "MYSQL_BIN=%%i"
    goto prepare_db_dir
)

:check_default_paths
:: Thu tim o cac duong dan cai dat mac dinh pho bien
for %%v in (8.4 8.0 8.1 8.2 8.3 9.0 9.1) do (
    if exist "C:\Program Files\MySQL\MySQL Server %%v\bin\mysqld.exe" (
        set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server %%v\bin\mysqld.exe"
        goto prepare_db_dir
    )
)
goto no_mysql_bin

:prepare_db_dir
if not defined MYSQL_BIN goto no_mysql_bin

:: Tu dong tao thu muc db_data neu chua co
if not exist "%DATA_DIR%" (
    echo [INFO] Khong tim thay thu muc db_data. Dang tao moi...
    mkdir "%DATA_DIR%"
)

:: Tu dong khoi tao database he thong neu thu muc trong rong
if not exist "%DATA_DIR%\mysql" (
    echo [INFO] Dang khoi tao database he thong (Database Initialization)...
    echo [INFO] Vui long cho trong giay lat...
    "%MYSQL_BIN%" --initialize-insecure --datadir="%DATA_DIR%"
    echo [SUCCESS] Khoi tao du lieu he thong hoan tat!
)

:start_mysql
echo [INFO] Tim thay MySQL tai: "%MYSQL_BIN%"
echo Dang khoi chay MySQL Server cuc bo...
start "" /b "%MYSQL_BIN%" --datadir="%DATA_DIR%"
echo [SUCCESS] Da kick hoat chay ngam MySQL Server!
goto end

:mysql_is_running
echo [INFO] Phat hien MySQL dang hoat dong tren cong 3306.
goto end

:no_mysql_bin
echo [ERROR] Khong tim thay mysqld.exe.
echo Vui long cai dat MySQL hoac bat service MySQL truoc.

:end
echo.
pause
