import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CountryPattern {
    private String countryCode;
    private int length;
    private String pattern;

    public CountryPattern(String countryCode, int length, String pattern) {
        this.countryCode = countryCode;
        this.length = length;
        this.pattern = pattern;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getLength() {
        return length;
    }

    public StructurePattern[] getPattern() {
        String[] rawPattern = pattern.split(",");
        StructurePattern[] structurePatterns = new StructurePattern[rawPattern.length];
        int counter = 0;

        for (String pattern: rawPattern) {
            String numberOfCharactersAsString = "";
            String type = "";
            for (char c : pattern.toCharArray()) {
                if (Character.isDigit(c)) {
                    numberOfCharactersAsString = numberOfCharactersAsString + c;
                }
                if (Character.isLetter(c)) {
                    type = type + c;
                }
            }
            int numberOfCharacters = Integer.parseInt(numberOfCharactersAsString);
            StructurePattern structurePattern = new StructurePattern(type, numberOfCharacters);

            structurePatterns[counter] = structurePattern;
            counter++;
        }

        return structurePatterns;
    }
}
