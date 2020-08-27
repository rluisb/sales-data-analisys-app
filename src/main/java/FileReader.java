import file.processor.factory.FileProcessorFactory;
import file.processor.factory.FileProcessorType;
import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalesmanRepository;

import java.nio.file.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class FileReader {
    public static void main(String[] args) throws Exception {
        SalesmanRepository salesmanRepository = SalesmanRepository.getInstance();
        SaleRepository saleRepository = SaleRepository.getInstance();
        CustomerRepository customerRepository = CustomerRepository.getInstance();

        FileProcessorFactory fileProcessorFactory = FileProcessorFactory.getInstance();

        String homePath = System.getProperty("user.home");
        String dataPath = "data";
        String inputDir = "in";
        String outputDir = "out";

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

                Path pathToFile = Paths.get(path.toString(), fileName);
                String fileContent = Files.readString(pathToFile);

                Files.deleteIfExists(pathToFile);


                Stream.of(fileContent)
                        .flatMap(content -> Arrays.stream(content.split("\n")))
                        .forEach(line -> {
                            try {
                                FileProcessorType lineFileProcessorType = FileProcessorType.getFileProcessorType(line);
                                fileProcessorFactory
                                        .getFileProcessor(lineFileProcessorType)
                                        .process(line);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                System.out.println("Customer data:");
                customerRepository.findAll()
                        .forEach(System.out::println);

                System.out.println("Sale data:");
                saleRepository.findAll()
                        .forEach(System.out::println);

                System.out.println("Salesman data:");
                salesmanRepository.findAll()
                        .forEach(System.out::println);

//                    Files.write(Paths.get(homePath, dataPath, outputDir, fileName.concat("-Relatory")), salesmanContent);
            }
            key.reset();
        }
    }
}

