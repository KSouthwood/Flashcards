package flashcards;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Flashcard {
    private final Map<String, String> cards = new LinkedHashMap<>();
    private final Map<String, Integer> stats = new LinkedHashMap<>();

    public Set<String> getSetOfTerms() {
        return cards.keySet();
    }

    public void addCard(String term, String definition, int errors) {
        cards.put(term, definition);
        stats.put(term, errors);
    }

    public boolean hasTerm(String term) {
        return cards.containsKey(term);
    }

    public boolean hasDefinition(String definition) {
        return cards.containsValue(definition);
    }

    public void answeredWrong(String term) {
        stats.put(term, stats.get(term) + 1);
    }

    public void resetStats() {
        stats.replaceAll((t, v) -> 0);
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
            stats.remove(term);
        }
    }

    public String importCardsFromFile(String filename) {
        try (BufferedReader dataIn = new BufferedReader(new FileReader(filename))) {
            String line;
            int cardsRead = 0;
            while ((line = dataIn.readLine()) != null) {
                String[] split = line.split(":");
                String term = split[0].substring(1, split[0].length() - 1);
                String def  = split[1].substring(1, split[1].length() - 1);
                int    err  = Integer.parseInt(split[2].substring(1, split[2].length() - 1));
                this.addCard(term, def, err);
                cardsRead++;
            }
            return cardsRead + " cards have been loaded.";
        } catch (FileNotFoundException e) {
            return "File not found.";
        } catch (IOException e) {
            return "IOException: " + e;
        }
    }

    public String exportCardsToFile(String filename) {
        char QUOTE = '"';
        String DELIM = "\":\"";
        try (BufferedWriter dataOut = new BufferedWriter(new FileWriter(filename))) {
            for (String term : this.getSetOfTerms()) {
                String def = getDefinitionOfTerm(term);
                String err = String.valueOf(stats.get(term));
                dataOut.write(QUOTE + term + DELIM + def + DELIM + err + QUOTE + "\n");
            }
            return cards.size() + " cards have been saved.";
        } catch (IOException e) {
            return "Error: " + e;
        }
    }

    public String hardestCard() {
        int errorCount = 0;
        ArrayList<String> terms = new ArrayList<>();
        for (var term : stats.keySet()) {
            int errors = stats.get(term);
            if (errors == errorCount) {
                terms.add(term);
            }

            if (errors > errorCount) {
                terms.clear();
                terms.add(term);
                errorCount = errors;
            }
        }

        if (errorCount == 0) {
            return "There are no cards with errors.";
        }

        String errorPhrase = String.format("You have %d errors answering ", errorCount);
        String QUOTE = "\"";
        StringBuilder result = new StringBuilder("The hardest card");
        if (terms.size() > 1) {
            result.append("s are ");
            for (var term : terms) {
                result.append(String.format("%s%s%s, ", QUOTE, term, QUOTE));
            }
            result.replace(result.length() - 2, result.length(), ". ");
        } else {
            result.append(String.format(" is %s%s%s. ", QUOTE, terms.get(0), QUOTE));
        }
        result.append(String.format("%s%s", errorPhrase, terms.size() > 1 ? "them." : "it."));
        return result.toString();
    }
}
