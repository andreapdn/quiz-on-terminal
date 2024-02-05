javac Ask.java -d output/
cd output/
jar cfm questions-on-terminal.jar ../MANIFEST.MF *
mv questions-on-terminal.jar ../
cd ..
rm -r output