package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        String term = input.nextLine();
        String definition = input.nextLine();
        String answer = input.nextLine();

        if (definition.equals(answer)) {
            System.out.println("That is the right answer!");
        } else {
            System.out.println("That is the wrong answer!");
        }
    }
}
