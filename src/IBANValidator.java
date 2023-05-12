import java.math.BigInteger;
import java.util.HashMap;

public class IBANValidator {
    public static boolean isValid(String iban) {
        HashMap<String, CountryPattern> countryPatterns = CsvImporter.importCsv("IBANSpec.csv");
        CountryPattern countryPattern = countryPatterns.get(iban.substring(0, 2));

        if (hasWhitespace(iban)) iban = removeSpaces(iban);
        BigInteger number = transformToNumber(iban);
        int checksum = Integer.parseInt(iban.substring(2, 4));

        if (iban.length() != countryPattern.getLength()) return false;
        if (!checkSumIsValid(number, checksum)) return false;
        if (!patternIsValid(iban.substring(4), countryPattern.getPattern())) return false;

        return true;
    }

    private static boolean hasWhitespace(String iban) {
        for (char c : iban.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }

        return false;
    }

    private static String removeSpaces(String iban) {
        return iban.replaceAll("\\s+", "");
    }

    private static BigInteger transformToNumber(String iban) {
        String charValues = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String countryCodeAndCheckSum = iban.substring(0, 4);
        String withoutCheckSum = iban.substring(4);
        String newString = withoutCheckSum + countryCodeAndCheckSum;

        StringBuilder newNumberString = new StringBuilder();
        for (char c : newString.toCharArray()) {
            if (Character.isDigit(c)) {
                newNumberString.append(c);
            } else {
                newNumberString.append(charValues.indexOf(c) + 10);
            }
        }

        BigInteger number = new BigInteger(newNumberString.toString());

        return number;
    }

    private static boolean checkSumIsValid(BigInteger number, int checksum) {

        if (checksum < 2 || checksum > 98) return false;

        BigInteger operand = new BigInteger(String.valueOf(97));
        BigInteger BigIntResult = number.mod(operand);
        int result = BigIntResult.intValue();

        return result == 1;
    }

    private static boolean patternIsValid(String bban, StructurePattern[] structurePatterns) {
        int first = 0;
        int last = 0;
        for (StructurePattern structurePattern : structurePatterns) {
            last = last + structurePattern.getNumberOfCharacters() - 1;
            if (structurePattern.type.equals("n")) {
                if (!digitsOnly(bban, first, last)) return false;
            }
            if (structurePattern.type.equals("a")) {
                if (!charsOnly(bban, first, last)) return false;
            }
            if (structurePattern.type.equals("c")) {
                if (!charsOrDigits(bban, first, last)) return false;
            }
            first = first + structurePattern.getNumberOfCharacters();
            last = first;
        }

        return true;
    }

    private static boolean digitsOnly(String bban, int first, int last) {
        for (Character c : bban.substring(first, last + 1).toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }

        return true;
    }

    private static boolean charsOnly(String bban, int first, int last) {
        for (Character c : bban.substring(first, last + 1).toCharArray()) {
            if (!Character.isUpperCase(c)) return false;
        }

        return true;
    }

    private static boolean charsOrDigits(String bban, int first, int last) {
        for (Character c : bban.substring(first, last + 1).toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }

        return true;
    }
}
