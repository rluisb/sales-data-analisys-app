package file.processor.factory;

import java.util.Arrays;

public enum FileProcessorType {
    SALESMAN("001"),
    CUSTOMER("002"),
    SALE("003");


    private final String dataIdentifierPattern;

    FileProcessorType(String dataIdentifierPattern) {
        this.dataIdentifierPattern = dataIdentifierPattern;
    }

    public static FileProcessorType getFileProcessorType(String line) throws Exception {
        return Arrays.stream(FileProcessorType.values())
                .filter(fileProcessorType ->
                        line.contains(fileProcessorType.getDataIdentifierPattern()))
                .findFirst()
                .orElseThrow(() -> new Exception("No pattern found"));
    }

    public String getDataIdentifierPattern() {
        return dataIdentifierPattern;
    }
}