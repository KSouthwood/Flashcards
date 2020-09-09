package flashcards;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final Flashcard flashcards = new Flashcard();

    public static void main(String[] args) {
        boolean running = true;
        String filename;
        while (running) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            switch (input.nextLine()) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    System.out.println("File name:");
                    filename = input.nextLine();
                    flashcards.importCardsFromFile(filename);
                    break;
                case "export":
                    System.out.println("File name:");
                    filename = input.nextLine();
                    flashcards.exportCardsToFile(filename);
                    break;
                case "ask":
                    askQuestions(getNumberOfQuestions());
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
                    break;
            }
            System.out.println();
        }
    }

    private static void addCard() {
        System.out.println("The card:");
        String term = input.nextLine();
        if (flashcards.hasTerm(term)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            return;
        }

        System.out.println("The definition of the card:");
        String definition = input.nextLine();
        if (flashcards.hasDefinition(definition)) {
            System.out.printf("The definition \"%s\" already exists.\n", definition);
            return;
        }

        flashcards.addCard(term, definition);
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
    }

    private static int getNumberOfQuestions() {
        while (true) {
            System.out.println("How many times to ask?");
            String numOfCards = input.nextLine();
            if (numOfCards.matches("\\b([1-9]|[1-9][0-9]+)\\b")) {
                return Integer.parseInt(numOfCards);
            } else {
                System.out.println("Please input a positive integer greater than 0.");
            }
        }
    }

    private static void askQuestions(int numOfQuestions) {
        Random rnd = new Random();
        String[] questionSet = flashcards.getSetOfTerms().toArray(new String[0]);
        for (int question = 0; question < numOfQuestions; question++) {
            String term = questionSet[rnd.nextInt(questionSet.length)];
            String definition = flashcards.getDefinitionOfTerm(term);

            System.out.printf("Print the definition of \"%s\":\n", term);
            String answer = input.nextLine();
            if (answer.equals(definition)) {
                System.out.println("Correct!");
            } else if (flashcards.hasDefinition(answer)) {
                System.out.printf("Wrong! The right answer is \"%s\", but your definition is correct" +
                        " for \"%s\".\n", definition, flashcards.getTermOfDefinition(answer));
            } else {
                System.out.printf("Wrong! The right answer is \"%s\".\n", definition);
            }
        }
    }

    private static void removeCard() {
        System.out.println("The card:");
        String term = input.nextLine();
        if (flashcards.hasTerm(term)) {
            flashcards.removeCard(term);
            System.out.println("The card has been removed.");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", term);
        }
    }
}
