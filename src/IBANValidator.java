import java.math.BigInteger;

public class IBANValidatorDE {
    public static boolean isValid(String iban) {
        if (hasWhitespace(iban)) iban = removeSpaces(iban);
        if (!lengthIsValid(iban)) return false;
        if (!localCodeIsValid(iban)) return false;
        if (!charactersAreValid(iban)) return false;
        BigInteger number = transformToNumber(iban);
        return checkCheckSum(number);
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
        return iban.replaceAll("\\s+","");
    }

    private static boolean lengthIsValid(String iban) {
        return iban.length() == 22;
    }

    private static boolean localCodeIsValid(String iban) {
        String localCode = iban.substring(0, 2);
        return localCode.equals("DE");
    }

    private static boolean charactersAreValid(String iban) {
        char[] chars = iban.substring(2).toCharArray();
        for (char c: chars) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private static BigInteger transformToNumber(String iban) {
        String checkSum = iban.substring(2, 4);
        String withoutCheckSum = iban.substring(4);
        String newNumberString = withoutCheckSum + "1314" + checkSum;

        BigInteger number = new BigInteger(newNumberString);

        return number;
    }

    private static boolean checkCheckSum(BigInteger number) {
        BigInteger operand = new BigInteger(String.valueOf(97));
        BigInteger BigIntResult = number.mod(operand);
        int result = BigIntResult.intValue();
        return result == 1;
    }
}
