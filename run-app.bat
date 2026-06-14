@echo off
set MYSQL_BIN=C:\Program Files\MySQL\MySQL Server 8.4\bin\mysqld.exe
set DATA_DIR=c:\Users\Kyz\Documents\LTJava\db_data
set MVN_PATH=C:\Program Files\JetBrains\IntelliJ IDEA 2025.2.1\plugins\maven\lib\maven3\bin\mvn.cmd

tasklist /fi "imagename eq mysqld.exe" | find /i "mysqld.exe" > nul
if %errorlevel% neq 0 (
    echo Starting MySQL Server...
    start "" /b "%MYSQL_BIN%" --datadir="%DATA_DIR%"
    ping 127.0.0.1 -n 4 > nul
)

if exist "%MVN_PATH%" (
    call "%MVN_PATH%" compile exec:java "-Dexec.mainClass=main.AppHocTu"
) else (
    call mvn compile exec:java "-Dexec.mainClass=main.AppHocTu"
)
pause
