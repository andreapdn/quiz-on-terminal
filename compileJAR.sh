javac Ask.java -d output/
cd output/
jar cfm quiz-on-terminal.jar ../MANIFEST.MF *
mv quiz-on-terminal.jar ../
cd ..
rm -r output