# Quiz on Terminal
Preparing for an exam but not sure how to review all the topics?
Try this tool!

## How to use
1. Install Java.
2. Download the `quiz-on-terminal.jar` file from the releases.
3. In the same folder as the downloaded file, create a `questions.txt` file.
4. Populate the text file with some questions. To format them correctly, read the next paragraph.
3. Open a terminal and write:

        java -jar quiz-on-terminal.jar


## Format your questions
Create a `questions.txt` file with your questions and answers.
It must be formatted this way:

```
Top Topic
Question
- Answer
+ Answer
- Answer
- Answer
Question
+ Answer
- Answer
- Answer
```

- When you want to specify a topic for your questions, just put 'Top ' before your questions
- To write an answer to a question, simply write it in a next line with the prefix '- '
- The answer with '+ ' is parsed as the correct answer
- You can write max 26 answers per question
- No blank line is permitted


## How to compile
1. Install Java (both JRE and JDK).
2. Clone the repository.
3. Open a terminal and write `javac Ask.java`.
4. To run: `java Ask`.
