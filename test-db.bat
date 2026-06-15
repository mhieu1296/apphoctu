@echo off
setlocal enabledelayedexpansion

echo ==================================================
echo   KHOI CHAY THU NGHIEM CO SO DU LIEU APPHOCTU
echo ==================================================
echo.

echo [INFO] Dang tim kiem Maven (mvn)...

:: 1. Kiem tra lenh mvn toan cuc
where mvn.cmd > nul 2>nul
if %errorlevel% neq 0 goto check_intellij_maven
echo [INFO] Phat hien Maven toan cuc (System Path).
set "MVN_CMD=mvn.cmd"
goto run_test

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
goto run_test

:maven_not_found
echo [ERROR] Khong tim thay Maven (mvn).
echo Vui long cai dat Maven hoac chay kiem thu trong IDE.
pause
exit /b

:run_test
echo.
echo [INFO] Dang bien dich va chay kiem thu...
call "%MVN_CMD%" compile exec:java "-Dexec.mainClass=main.DBTestRunner"
pause
