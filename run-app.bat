@echo off
setlocal enabledelayedexpansion

echo ==================================================
echo   KHOI DONG UNG DUNG APPHOCTU (JAVA SWING + MYSQL)
echo ==================================================
echo.

:: 1. Xac dinh thu muc db_data (duong dan tuong doi)
set "DATA_DIR=%~dp0db_data"

:: 2. Kiem tra xem MySQL da chay tren cong 3306 chua
netstat -ano | findstr "3306" > nul
if %errorlevel% equ 0 goto mysql_is_running

:: Neu chua chay, tim kiem file mysqld.exe
echo [INFO] MySQL chua chay. Dang tim kiem file mysqld.exe...
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
goto check_maven

:prepare_db_dir
if not defined MYSQL_BIN goto check_maven

:: Tu dong tao thu muc db_data neu chua co
if not exist "%DATA_DIR%" (
    echo [INFO] Khong tim thay thu muc db_data. Dang tao moi...
    mkdir "%DATA_DIR%"
)

:: Tu dong khoi tao database he thong neu thu muc trong rong
if not exist "%DATA_DIR%\mysql" (
    echo [INFO] Dang khoi tao database he thong - Database Initialization...
    echo [INFO] Vui long cho trong giay lat...
    "%MYSQL_BIN%" --initialize-insecure --datadir="%DATA_DIR%"
    echo [SUCCESS] Khoi tao du lieu he thong hoan tat!
)

:start_mysql
echo [INFO] Tim thay MySQL tai: "%MYSQL_BIN%"
echo Dang khoi chay MySQL Server...
start "" /b "%MYSQL_BIN%" --datadir="%DATA_DIR%"
:: Doi 4 giay de MySQL khoi dong
ping 127.0.0.1 -n 5 > nul
goto check_maven

:mysql_is_running
echo [INFO] Phat hien MySQL dang hoat dong tren cong 3306.

:check_maven
echo.
echo [INFO] Dang tim kiem Maven (mvn)...

:: 1. Kiem tra lenh mvn toan cuc
where mvn.cmd > nul 2>nul
if %errorlevel% neq 0 goto check_intellij_maven
echo [INFO] Phat hien Maven toan cuc (System Path).
set "MVN_CMD=mvn.cmd"
goto run_app

:check_intellij_maven
:: 2. Tim kiem trong cac thu muc cua IntelliJ IDEA
echo [INFO] Khong thay Maven toan cuc. Dang tim Maven trong IntelliJ...
set "INTELIJ_DIR=C:\Program Files\JetBrains"
if not exist "%INTELIJ_DIR%" goto maven_not_found

for /r "%INTELIJ_DIR%" %%f in (mvn.cmd) do (
    if exist "%%f" (
        set "MVN_CMD=%%f"
        goto found_intellij_maven
    )
)
goto maven_not_found

:found_intellij_maven
echo [INFO] Tim thay Maven tai: "%MVN_CMD%"
goto run_app

:maven_not_found
echo [ERROR] Khong tim thay Maven (mvn) tren may tinh.
echo.
echo Vui long:
echo 1. Cai dat Maven va them vao bien PATH.
echo 2. Hoac mo du an truc tiep bang IDE (VSCode, NetBeans, IntelliJ) va nhan Run.
echo.
pause
exit /b

:run_app
echo.
echo [INFO] Dang bien dich va khoi chay ung dung...
call "%MVN_CMD%" compile exec:java "-Dexec.mainClass=main.AppHocTu"
pause
