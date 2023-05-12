public class StructurePattern {
    String type;
    int numberOfCharacters;

    public StructurePattern(String type, int numberOfCharacters) {
        this.type = type;
        this.numberOfCharacters = numberOfCharacters;
    }

    public String getType() {
        return type;
    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }
}
