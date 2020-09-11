# Flashcards


## About
Typically, flashcards show a hint (a task, or a picture) on one side and the right answer on the other. Flashcards can
be used to remember any sort of data, so if you want to create a useful tool to help your learning and your programming
skills, this project is ideal.

#### Stage 1: Stage one, card one
As a start, implement a program that outputs a single term and its definition. You can print a term and a definition
you like in this stage.

#### Stage 2: What's on the card?
We cannot play Flashcards with only one card, right? Let's make our program dynamic! Implement a custom card creation
mechanism. Read a **_term_** and a **_definition_** from the console and create a card.

After that, a user inputs a line as an **answer** (a definition of the card). Compare the user's answer to the correct
definition and print the verdict.

#### Stage 3: Make it your own
Your program is able to play, but only uses one card. Let's make our game serious and implement a set of cards now!

Let the user decide how many cards they would like to keep. First, ask the player to enter the desired number of
cards. Then, ask to input the term and the definition of every card.

In the end, when all the cards are defined and saved, your program is finally ready to play! Question the player about
all the new words they have entered. The program should give the term and ask for a definition. Let the game begin!

#### Stage 4: A good stack
Imagine a situation: the answer is wrong for the given term, but it is correct for another term. Let's consider
situations like this.

You can use maps. Ask all card's definitions in the order of addition. If the definition is wrong for the current
term, but it is correct for another, output the original term.

When the user tries to add a duplicated term or definition, forbid it and ask again until the user inputs a unique one.
For now, you are able to implement this without a `try catch` construction. Use the rule: if you can avoid
exception-based logic, avoid it!

#### Stage 5: Menu, please!
Improve the interactivity of the program by asking the user for action. Support the following actions: `add`, `remove`,
`import`, `export`, `ask` and `exit`.

When choosing `export`, the program should request a file name and write all currently available cards into the file.
(You can use any format to save the cards to the file.) When choosing `import`, the program should read all the cards
written in the file.

For this stage, if you try to `add` a card with an existing term or definition, print an error message and do not add
it. However, while `import`ing cards from a file, update an existing term with the definition from the file.

#### Stage 6: Statistics
Add some statistics features, and the following new commands:

- `log` saves the application log to the given file. Save all lines that have been input or output to the console to
the file.
- `hardest card` prints the term of the card that has the most mistakes. If there are no cards with mistakes, you should
print `There are no cards with errors.`. List all cards if there are multiple that have the same amount of most mistakes.
- `reset stats` erases the mistake counts for all cards.

Also, you should update the import/export to store sets of three items (term, definition, mistakes) instead of
pairs (term, definition).

#### Stage 7: IMPORTant

Users often use files to save their progress and restore it the next time they run the program. It's tedious to print
the actions manually. Sometimes you can just forget to do it! So let's add run arguments that define which file to read
at the start and which file to save at the exit.

To read an initial cards set from an external file, you should pass the argument `-import` and follow it with the file
name. If the argument is present, the first line of your program should be `10 cards have been loaded.` (If the file
contained 10 cards.) If the argument is missing, the set of cards should be initially empty.

If the `-export` argument is present, and it is followed by the file name, you should write all the cards that are in the
program memory into this file after the user has entered `exit`, and the last line of your program should be `10 cards
have been saved.`