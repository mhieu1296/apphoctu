@echo off
set MVN_PATH=C:\Program Files\JetBrains\IntelliJ IDEA 2025.2.1\plugins\maven\lib\maven3\bin\mvn.cmd

if exist "%MVN_PATH%" (
    call "%MVN_PATH%" compile exec:java "-Dexec.mainClass=main.DBTestRunner"
) else (
    call mvn compile exec:java "-Dexec.mainClass=main.DBTestRunner"
)
pause
