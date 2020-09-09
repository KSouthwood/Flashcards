package flashcards;

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
}
