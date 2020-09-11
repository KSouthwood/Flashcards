package flashcards;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Flashcard flashcards = new Flashcard();
    private static final ArrayList<String> log = new ArrayList<>();

    public static void main(String[] args) {
        String importName = null;
        String exportName = null;

        if (args.length != 0) {
            for (int index = 0; index < args.length; index++) {
                if (args[index].equals("-import")) {
                    importName = args[index + 1];
                    index++;
                }
                if (args[index].equals("-export")) {
                    exportName = args[index + 1];
                    index++;
                }
            }
        }

        if (importName != null) {
            consoleOutput(flashcards.importCardsFromFile(importName));
        }

        controller();

        if (exportName != null) {
            consoleOutput(flashcards.exportCardsToFile(exportName));
        }
    }

    public static void controller() {
        boolean running = true;
        while (running) {
            consoleOutput("Input the action (add, remove, import, export, ask, log, hardest card, reset stats, exit):");
            String command = consoleInput();
            switch (command) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                case "export":
                case "log":
                    fileOperation(command);
                    break;
                case "ask":
                    askQuestions(getNumberOfQuestions());
                    break;
                case "exit":
                    consoleOutput("Bye bye!");
                    running = false;
                    break;
                case "reset stats":
                    flashcards.resetStats();
                    consoleOutput("Card statistics have been reset.");
                    break;
                case "hardest card":
                    consoleOutput(flashcards.hardestCard());
                    break;
                default:
                    consoleOutput("Unknown option.");
                    break;
            }
            consoleOutput("");
        }
    }

    private static void addCard() {
        consoleOutput("The card:");
        String term = consoleInput();
        if (flashcards.hasTerm(term)) {
            consoleOutput("The card \"" + term + "\" already exists.");
            return;
        }

        consoleOutput("The definition of the card:");
        String definition = consoleInput();
        if (flashcards.hasDefinition(definition)) {
            consoleOutput("The definition \"" + definition + "\" already exists.");
            return;
        }

        flashcards.addCard(term, definition, 0);
        consoleOutput("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    private static int getNumberOfQuestions() {
        while (true) {
            consoleOutput("How many times to ask?");
            String numOfCards = consoleInput();
            if (numOfCards.matches("\\b([1-9]|[1-9][0-9]+)\\b")) {
                return Integer.parseInt(numOfCards);
            } else {
                consoleOutput("Please input a positive integer greater than 0.");
            }
        }
    }

    private static void askQuestions(int numOfQuestions) {
        Random rnd = new Random();
        String[] questionSet = flashcards.getSetOfTerms().toArray(new String[0]);
        for (int question = 0; question < numOfQuestions; question++) {
            String term = questionSet[rnd.nextInt(questionSet.length)];
            String definition = flashcards.getDefinitionOfTerm(term);

            consoleOutput("Print the definition of \"" + term + "\":");
            String answer = consoleInput();
            if (answer.equals(definition)) {
                consoleOutput("Correct!");
            } else {
                flashcards.answeredWrong(term);
                if (flashcards.hasDefinition(answer)) {
                    consoleOutput("Wrong! The right answer is \"" + definition + "\", but your definition is correct" +
                            " for \"" + flashcards.getTermOfDefinition(answer) + "\".");
                } else {
                    consoleOutput("Wrong! The right answer is \"" + definition + "\".");
                }
            }
        }
    }

    private static void removeCard() {
        consoleOutput("The card:");
        String term = consoleInput();
        if (flashcards.hasTerm(term)) {
            flashcards.removeCard(term);
            consoleOutput("The card has been removed.");
        } else {
            consoleOutput("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    /**
     * @param msg the text that we're outputting to the console which we log at the same time
     */
    private static void consoleOutput(String msg) {
        log.add(msg);
        System.out.println(msg);
    }

    /**
     * Get input from the console and log it, then return it
     *
     * @return the text entered in the console as a String
     */
    private static String consoleInput() {
        String inputLine = new Scanner(System.in).nextLine();
        log.add("> " + inputLine);
        return inputLine;
    }

    private static void fileOperation(String command) {
        consoleOutput("File name:");
        String filename = consoleInput();
        switch (command) {
            case "import":
                consoleOutput(flashcards.importCardsFromFile(filename));
                break;
            case "export":
                consoleOutput(flashcards.exportCardsToFile(filename));
                break;
            case "log":
                writeLog(filename);
                break;
            default:
                break;
        }
    }

    private static void writeLog(String filename) {
        try (BufferedWriter logOut = new BufferedWriter(new FileWriter(filename))) {
            for (String line : log) {
                logOut.write(line + "\n");
            }
            consoleOutput("The log has been saved.");
        } catch (IOException e) {
            consoleOutput("File error: " + e);
        }
    }
}
