javac -d bin\classes src\draughts\*.java
copy /Y res\* bin\classes
cd bin\classes
jar cfm ..\jar\Draughts.jar ..\..\Manifest.txt draughts\*.class *
cd ..\jar
java -jar Draughts.jar
cd ..\..