import java.nio.file.*;
import java.util.List;

public class FileReader {
    public static void main(String[] args) throws Exception {
        String homePath = System.getProperty("user.home");
        String dataPath = "data";
        String inputDir = "in";
        String outputDir = "out";

        String processingStatus = "-processing";

        Path path = Paths.get(homePath, dataPath, inputDir);

        if (!Files.exists(path)) {
            throw new Exception("Directory doesn't exists");
        }

//        List<String> fileContent = Files.readAllLines(path);
//
//        String processingFileName = path.getFileName().toString().concat(processingStatus);
//
//        Files.move(path, path.resolveSibling(processingFileName));
//
//        Path processingFilePath = Paths.get(homePath, dataPath, inputDir, processingFileName);
//
//        fileContent.stream()
//                .forEach(System.out::println);
//
//        Path processedFilePath = Paths.get(homePath, dataPath, outputDir, path.getFileName().toString());
//
//        Files.move(processingFilePath, processedFilePath);

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileName = event.context().toString();

                if (!fileName.contains(processingStatus)) {
                    Path pathToFile = Paths.get(path.toString(), fileName);
                    List<String> fileContent = Files.readAllLines(pathToFile);

                    String processingFileName = fileName.concat(processingStatus);

                    Files.move(pathToFile, pathToFile.resolveSibling(processingFileName));

                    Path processingFilePath = Paths.get(homePath, dataPath, inputDir, processingFileName);

                    fileContent.stream()
                            .forEach(System.out::println);

                    Path processedFilePath = Paths.get(homePath, dataPath, outputDir, fileName);

                    Files.move(processingFilePath, processedFilePath);
                }
            }
            key.reset();
        }
    }
}

