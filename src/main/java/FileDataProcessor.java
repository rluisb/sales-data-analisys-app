import file.processor.factory.FileProcessorFactory;
import file.processor.factory.FileProcessorType;
import file.reader.FileReader;
import file.writer.FileWriter;
import model.Report;
import model.Sale;
import model.Salesman;
import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalesmanRepository;
import service.ReportService;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class FileDataProcessor {
    public static void main(String[] args) throws Exception {
        SalesmanRepository salesmanRepository = SalesmanRepository.getInstance();
        SaleRepository saleRepository = SaleRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        FileReader fileReader = FileReader.getInstance();
        FileWriter fileWriter = FileWriter.getInstance();

        ReportService reportService = new ReportService(salesmanRepository, saleRepository, customerRepository);

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        fileReader.getInputPath()
                .register(
                        watchService,
                        StandardWatchEventKinds.ENTRY_CREATE);

        WatchKey key;
        while (Objects.nonNull(key = watchService.take())) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String fileName = event.context().toString();
                String fileContent = fileReader.readFileContent(fileName);

                Stream.of(fileContent)
                        .flatMap(content -> Arrays.stream(content.split("\n")))
                        .forEach(line -> {
                            try {
                                FileProcessorType lineFileProcessorType = FileProcessorType.getFileProcessorType(line);
                                FileProcessorFactory
                                        .getInstance()
                                        .getFileProcessor(lineFileProcessorType)
                                        .process(line);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });


                Report report = reportService.mountReport();

                fileWriter.writeFileContent(
                        fileReader.getFileName(),
                        fileReader.getFileExtension(),
                        report.getReportInBytes()
                );
            }
            key.reset();
        }
    }
}

