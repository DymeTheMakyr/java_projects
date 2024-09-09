set initDirectory=%cd%

cd C:\Users\Hugo\eclipse-workspace\roomgen\src\main
erase /Q /S classes
rmdir classes\roomgen
rmdir classes\resources
javac -d ./classes ./java/roomgen/Main.java ./java/resources/roomutils.java
cd bin
jar cvf roomgen.jar "../classes/roomgen/Main.class" "../classes/resources/roomutils.class"
cd %initDirectory%