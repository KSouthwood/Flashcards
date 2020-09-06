package flashcards;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Flashcard> flashcards;

    public static void main(String[] args) {
        int cards = getNumberOfCards();
        flashcards = new ArrayList<>(cards);
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
            System.out.printf("The card #%d:\n", card);
            String term = input.nextLine();
            System.out.printf("The definition of card #%d\n", card);
            String definition = input.nextLine();
            flashcards.add(new Flashcard(term, definition));
        }
    }

    private static void askQuestions() {
        for (var card : flashcards) {
            System.out.printf("Print the definition of \"%s\":\n", card.getTerm());
            String answer = input.nextLine();
            if (answer.equals(card.getDefinition())) {
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong! The right answer is \"%s\".\n", card.getDefinition());
            }
        }
    }
}
