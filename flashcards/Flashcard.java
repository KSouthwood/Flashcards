package flashcards;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Flashcard {
    private Map<String, String> cards = new LinkedHashMap<>();

    public Set<String> getSetOfTerms() {
        return cards.keySet();
    }

    public void addCard(String term, String definition) {
        cards.put(term, definition);
    }

    public boolean hasTerm(String term) {
        return cards.containsKey(term);
    }

    public boolean hasDefinition(String definition) {
        return cards.containsValue(definition);
    }

    /**
     * @param definition we need to find the matching term for
     * @return term of the definition, empty string if value not found
     */
    public String getTermOfDefinition(String definition) {
        String result = "";

        if (this.hasDefinition(definition)) {
            for (var term : this.getSetOfTerms()) {
                if (this.getDefinitionOfTerm(term).equals(definition)) {
                    result = term;
                    break;
                }
            }
        }

        return result;
    }

    public String getDefinitionOfTerm(String term) {
        return cards.get(term);
    }

    public void removeCard(String term) {
        if (this.hasTerm(term)) {
            cards.remove(term);
        }
    }

    public void importCardsFromFile(String filename) {
        try (BufferedReader dataIn = new BufferedReader(new FileReader(filename))) {
            boolean moreData = true;
            int cardsRead = 0;
            while (moreData) {
                String term = dataIn.readLine();
                String def  = dataIn.readLine();
                if (term == null || def == null) {
                    moreData = false;
                    continue;
                }
                this.addCard(term, def);
                cardsRead++;
            }
            System.out.printf("%d cards have been loaded.%n", cardsRead);
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void exportCardsToFile(String filename) {
        try (BufferedWriter dataOut = new BufferedWriter(new FileWriter(filename))) {
            for (String term : this.getSetOfTerms()) {
                dataOut.write(term);
                dataOut.newLine();
                dataOut.write(getDefinitionOfTerm(term));
                dataOut.newLine();
            }
            System.out.printf("%d cards have been saved.", cards.size());
        } catch (IOException e) {
            System.out.printf("Error: %s%n", e);
        }
    }
}
