package flashcards;

public class Flashcard {
    String term;
    String definition;

    Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
