import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static void main(String[] args) throws IOException {
        String homePath = System.getProperty("user.home");
        String dataPath = "data";
        String inputDir = "in";
        String outputDir = "out";

        String processingStatus = "-processing";

        Path path = Paths.get(homePath, dataPath, inputDir, "test2.txt");

        List<String> fileContent = Files.readAllLines(path);

        String processingFileName = path.getFileName().toString().concat(processingStatus);

        Files.move(path, path.resolveSibling(processingFileName));

        Path processingFilePath = Paths.get(homePath, dataPath, inputDir, processingFileName);

        fileContent.stream()
                .forEach(System.out::println);

        Path processedFilePath = Paths.get(homePath, dataPath, outputDir, path.getFileName().toString());

        Files.move(processingFilePath, processedFilePath);
    }
}

