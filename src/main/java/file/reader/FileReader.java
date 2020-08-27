package file.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileReader {

    private final String homePath = System.getProperty("user.home");
    private final String dataPath = "data";
    private final String inputDir = "in";

    private static FileReader fileReaderInstance;
    private String fileName;
    private String fileExtension;

    public static synchronized FileReader getInstance() {
        if (Objects.isNull(fileReaderInstance)) {
            fileReaderInstance = new FileReader();
        }
        return fileReaderInstance;
    }

    public String readFileContent(String fileNameWithExtension) throws IOException {
        this.setFileName(fileNameWithExtension);
        this.setFileExtension(fileNameWithExtension);

        if (!Files.exists(getInputPath())) {
            throw new IOException("Directory " + getInputPath().toString() + " doesn't exists");
        }

        String content = Files.readString(getPathToFile(fileNameWithExtension));

        if (Objects.nonNull(content)) {
            Files.deleteIfExists(getPathToFile(fileNameWithExtension));
        }

        return content;
    }

    private void setFileName(String fileNameWithExtension) {
        this.fileName = fileNameWithExtension
                .substring(0, fileNameWithExtension.lastIndexOf("."));
    }

    private void setFileExtension(String fileNameWithExtension) {
        this.fileExtension = fileNameWithExtension
                .substring(fileNameWithExtension.lastIndexOf(".") - 1);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    private Path getPathToFile(String fileNameWithExtension) {
        return Paths.get(getInputPath().toString(), fileNameWithExtension);
    }

    public Path getInputPath() {
        return Paths.get(homePath, dataPath, inputDir);
    }
}
