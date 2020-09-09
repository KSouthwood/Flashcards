package flashcards;

import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static Flashcard flashcards;

    public static void main(String[] args) {
        int cards = getNumberOfCards();
        flashcards = new Flashcard();
        defineCards(cards);
        askQuestions();
    }

    private static int getNumberOfCards() {
        while (true) {
            System.out.println("Input the number of cards:");
            String numOfCards = input.nextLine();
            if (numOfCards.matches("\\b([1-9]|[1-9][0-9]+)\\b")) {
                return Integer.parseInt(numOfCards);
            } else {
                System.out.println("Please input a positive integer greater than 0.");
            }
        }
    }

    private static void defineCards(int cards) {
        for (int card = 1; card <= cards; card++) {
            String term;
            String definition;

            System.out.printf("The card #%d:\n", card);
            while (true) {
                term = input.nextLine();
                if (!flashcards.hasTerm(term)) {
                    break;
                }
                System.out.printf("The card \"%s\" already exists. Try again:\n", term);
            }

            System.out.printf("The definition of card #%d\n", card);
            while (true) {
                definition = input.nextLine();
                if (!flashcards.hasDefinition(definition)) {
                    break;
                }
                System.out.printf("The definition \"%s\" already exists. Try again:\n", definition);
            }

            flashcards.addCard(term, definition);
        }
    }

    private static void askQuestions() {
        for (var term : flashcards.getSetOfTerms()) {
            System.out.printf("Print the definition of \"%s\":\n", term);
            String answer = input.nextLine();
            if (answer.equals(flashcards.getDefinitionOfTerm(term))) {
                System.out.println("Correct!");
            } else if (flashcards.hasDefinition(answer)) {
                System.out.printf("Wrong! The right answer is \"%s\", but your definition is correct" +
                        " for \"%s\".\n", flashcards.getDefinitionOfTerm(term), flashcards.getTermOfDefinition(answer));
            } else {
                System.out.printf("Wrong! The right answer is \"%s\".\n", flashcards.getDefinitionOfTerm(term));
            }
        }
    }
}
