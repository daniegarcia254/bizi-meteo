javac -d bin src/*.java
jar cvfm Print2columnas.jar manifest1.txt -C bin stw/ -C bin org/ -C bin com/ -C bin freemarker/ -C bin javax/ -C bin log4j.properties
jar cvfm Print2columnas_xml.jar manifest2.txt -C bin stw/ -C bin org/ -C bin com/ -C bin freemarker/ -C bin javax/ -C bin log4j.properties
