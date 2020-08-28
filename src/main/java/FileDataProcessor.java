import file.processor.factory.FileProcessorFactory;
import file.processor.factory.FileProcessorType;
import file.reader.FileReader;
import file.watcher.FileWatcher;
import file.writer.FileWriter;
import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalesmanRepository;
import service.ReportService;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class FileDataProcessor {
    public static void main(String[] args) {
        SalesmanRepository salesmanRepository = SalesmanRepository.getInstance();
        SaleRepository saleRepository = SaleRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();

        FileReader fileReader = FileReader.getInstance();
        FileWriter fileWriter = FileWriter.getInstance();


        ReportService reportService =
                new ReportService(salesmanRepository,
                        saleRepository, customerRepository);

        try {
            WatchService watchService = new FileWatcher()
                    .createWatcherService(fileReader.getInputPath());
            WatchKey key;
            while (Objects.nonNull(key = watchService.take())) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String fileName = event.context().toString();

                    if (Objects.nonNull(fileReader.getFileName())
                            && fileReader.getFileName().contains(fileName)) {
                        return;
                    }

                    String fileContent = fileReader.readFileContent(fileName);

                    Stream.of(fileContent)
                            .filter(Objects::nonNull)
                            .flatMap(content -> Arrays.stream(content.split("\n")))
                            .forEach(line -> {
                                try {
                                    FileProcessorType lineFileProcessorType =
                                            FileProcessorType.getFileProcessorType(line);

                                    FileProcessorFactory
                                            .getInstance()
                                            .getFileProcessor(lineFileProcessorType)
                                            .process(line);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                    fileWriter.writeFileContent(
                            fileReader.getFileName(),
                            fileReader.getFileExtension(),
                            reportService.mountReport().getReportInBytes());
                }
                key.reset();
                main(args);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

