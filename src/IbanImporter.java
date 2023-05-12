import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class IbanImporter {
    public static ArrayList<String> getIbans(String path) {
        ArrayList<String> ibans = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get(path).toFile())) {
            while (scanner.hasNextLine()) {
                String iban = scanner.nextLine();
                ibans.add(iban);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ibans;
    }
}
