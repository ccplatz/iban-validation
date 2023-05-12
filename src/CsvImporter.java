import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class CsvImporter {
    private static final String DELIMITER = ";";

    public static HashMap<String, CountryPattern> importCsv(String path) {
        HashMap<String, CountryPattern> countryPatternHashMap = new HashMap<>();

        try (Scanner scanner = new Scanner(Paths.get(path).toFile())) {

            // skip first line
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(DELIMITER);
                String countryName = line[0];
                int length = Integer.parseInt(line[1]);
                String pattern = line[2];
                String countryCode = line[3];
                CountryPattern countryPattern = new CountryPattern(countryCode, length, pattern);

                countryPatternHashMap.put(countryCode, countryPattern);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return countryPatternHashMap;
    }
}
