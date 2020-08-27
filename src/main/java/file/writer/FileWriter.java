package file.writer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

public class FileWriter {

    private final String homePath = System.getProperty("user.home");
    private final String dataPath = "data";
    private final String outputDir = "out";

    private static FileWriter fileWriterInstance;

    public static synchronized FileWriter getInstance() {
        if (Objects.isNull(fileWriterInstance)) {
            fileWriterInstance = new FileWriter();
        }
        return fileWriterInstance;
    }

    public void writeFileContent(String fileName, String fileExtension, byte[] content) throws IOException {
        Path path = Paths.get(homePath, dataPath, outputDir);

        if (!Files.exists(path)) {
            throw new IOException("Directory " + path.toString() + " doesn't exists");
        }

        Path outputFile = Paths.get(path.toString(),
                getProcessedFileName(fileName, fileExtension));

        Files.write(outputFile, content);

        System.out.println("File saved in path: " + outputFile.toString());
    }

    private String getProcessedFileName(String fileName, String fileExtension) {
        long timestamp = Timestamp.valueOf(LocalDate.now().atStartOfDay()).getTime();
        return fileName.concat("-").concat(Long.toString(timestamp)).concat(fileExtension);
    }
}
