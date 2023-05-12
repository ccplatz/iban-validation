import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> ibans = IbanImporter.getIbans("testibans.txt");
        StringBuilder passphrase = new StringBuilder();

        for (String iban: ibans) {
            if (IBANValidator.isValid(iban)) {
                passphrase.append(iban.charAt(1));
            }
        }

        System.out.println(passphrase);
    }
}