echo cd C:\Users\Hugo\eclipse-workspace\ > .next.bat
start "GITHUB TERMINAL" .next.bat

timeout /t 1

echo cd ./classes > .next.bat
start "JAVA CLASS TERMINAL" .next.bat

timeout /t 1

start "JAVA COMPILE TERMINAL" cmd