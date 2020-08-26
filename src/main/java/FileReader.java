import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReader {
    public static void main(String[] args) throws Exception {
        String homePath = System.getProperty("user.home");
        String dataPath = "data";
        String inputDir = "in";
        String outputDir = "out";

        String salesmanRegex = "001ç([0-9]+)ç([ a-zA-Z áç]+)ç([-+]?[0-9]*\\.?[0-9]*)";
        String customerRegex = "002ç([0-9]+)ç([ a-zA-Z áç]+)ç([ a-zA-Z áç]+)";
        String saleRegex = "003ç([0-9]+)ç(.*)ç(.*)";
        String itemRegex = "([-+]?[0-9]*\\.?[0-9]*)-([-+]?[0-9]*\\.?[0-9]*)-([-+]?[0-9]*\\.?[0-9]*)";

        String processingStatus = "-processing";

        Path path = Paths.get(homePath, dataPath, inputDir);

        if (!Files.exists(path)) {
            throw new Exception("Directory doesn't exists");
        }

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while (Objects.nonNull(key = watchService.take())) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileName = event.context().toString();

                if (!fileName.contains(processingStatus)) {
                    Path pathToFile = Paths.get(path.toString(), fileName);
                    List<String> fileContent = Files.readAllLines(pathToFile);

                    String processingFileName = fileName.concat(processingStatus);

                    Files.move(pathToFile, pathToFile.resolveSibling(processingFileName));

                    Path processingFilePath = Paths.get(homePath, dataPath, inputDir, processingFileName);

                    String salesmanContent = fileContent.stream()
                            .flatMap(line -> Arrays.stream(line.split("\n")))
                            .filter(line -> line.contains("001ç"))
                            .map(line -> {
                                Pattern salesmanPattern = Pattern.compile(salesmanRegex);
                                Matcher salesmanMatcher = salesmanPattern.matcher(line);

                                if (!salesmanMatcher.find()) {
                                    return "";
                                }

                                return "\t CPF: ".concat(salesmanMatcher.group(1)) +
                                        "\t Name: ".concat(salesmanMatcher.group(2)) +
                                        "\t Salary: ".concat(salesmanMatcher.group(3));
                            })
                            .map(line -> line.concat("\n"))
                            .reduce(String::concat)
                            .get();

                    System.out.println(salesmanContent);

//                    Files.write(Paths.get(homePath, dataPath, outputDir, processingFileName), processedContent.getBytes());
                }
            }
            key.reset();
        }
    }
}

