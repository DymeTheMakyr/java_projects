set initDirectory=%cd%

cd C:\Users\Hugo\eclipse-workspace\roomgen\src\main
erase /Q /S classes
rmdir classes\main
rmdir classes\classes
javac -d ./classes ./java/main/renderer.java ./java/classes/classes.java
cd bin
jar cvf renderer.jar ../classes/main/renderer.class" "../classes/classes/classes.class"
cd %initDirectory%