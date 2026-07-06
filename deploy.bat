@echo off
setlocal

REM ==========================================
REM  Ddeonabom Deploy Script
REM  Usage: deploy.bat            (full deploy)
REM         deploy.bat backend    (backend only)
REM         deploy.bat admin      (admin only)
REM ==========================================

set PROJECT_ROOT=D:\7_SpringBoot_workspace\ddeonabom
set ADMIN_DIR=%PROJECT_ROOT%\src\main\admin
set KEY_PATH=C:\aws-key\my-key.pem
set SERVER_IP=3.34.10.120
set SERVER_USER=ubuntu
set JAR_NAME=ddeonabom-0.0.1-SNAPSHOT.jar
set SERVICE_NAME=ddeonabom

if "%1"=="admin" goto :admin_only
if "%1"=="backend" goto :backend_only
goto :full_deploy

:full_deploy
echo.
echo === STEP 1/6 Backend build ===
cd /d "%PROJECT_ROOT%"
call gradlew.bat clean build
if errorlevel 1 (
    echo.
    echo [FAILED] Backend build error. Stopping deploy.
    goto :end
)

echo.
echo === STEP 2/6 Upload backend jar ===
scp -i "%KEY_PATH%" "%PROJECT_ROOT%\build\libs\%JAR_NAME%" %SERVER_USER%@%SERVER_IP%:/home/ubuntu/
if errorlevel 1 (
    echo.
    echo [FAILED] Jar upload error. Stopping deploy.
    goto :end
)

echo.
echo === STEP 3/6 Restart backend service ===
ssh -i "%KEY_PATH%" %SERVER_USER%@%SERVER_IP% "sudo systemctl restart %SERVICE_NAME% && sleep 2 && sudo systemctl status %SERVICE_NAME% --no-pager"

echo.
echo === STEP 4/6 Admin build ===
cd /d "%ADMIN_DIR%"
call npm run build
if errorlevel 1 (
    echo.
    echo [FAILED] Admin build error. Stopping deploy.
    goto :end
)

echo.
echo === STEP 5/6 Upload admin static files ===
scp -i "%KEY_PATH%" -r "%ADMIN_DIR%\dist\." %SERVER_USER%@%SERVER_IP%:/home/ubuntu/admin-dist/
if errorlevel 1 (
    echo.
    echo [FAILED] Admin upload error.
    goto :end
)

echo.
echo === STEP 6/6 Deploy complete ===
echo   Backend: http://%SERVER_IP%
echo   Admin:   http://%SERVER_IP%/admin/
goto :end

:backend_only
echo.
echo === Backend-only deploy ===
cd /d "%PROJECT_ROOT%"
call gradlew.bat clean build
if errorlevel 1 (
    echo [FAILED] Build error. Stopping.
    goto :end
)
scp -i "%KEY_PATH%" "%PROJECT_ROOT%\build\libs\%JAR_NAME%" %SERVER_USER%@%SERVER_IP%:/home/ubuntu/
ssh -i "%KEY_PATH%" %SERVER_USER%@%SERVER_IP% "sudo systemctl restart %SERVICE_NAME% && sleep 2 && sudo systemctl status %SERVICE_NAME% --no-pager"
echo.
echo === Backend deploy complete ===
goto :end

:admin_only
echo.
echo === Admin-only deploy ===
cd /d "%ADMIN_DIR%"
call npm run build
if errorlevel 1 (
    echo [FAILED] Build error. Stopping.
    goto :end
)
scp -i "%KEY_PATH%" -r "%ADMIN_DIR%\dist\." %SERVER_USER%@%SERVER_IP%:/home/ubuntu/admin-dist/
echo.
echo === Admin deploy complete ===
goto :end

:end
endlocal
pause