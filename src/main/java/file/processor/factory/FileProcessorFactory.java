package file.processor.factory;

import file.processor.FileProcessor;
import file.processor.CustomerFileProcessor;
import file.processor.SaleFileProcessor;
import file.processor.SalesmanFileProcessor;

import java.util.Objects;

import static file.processor.factory.FileProcessorType.*;

public class FileProcessorFactory extends FileProcessorAbstractFactory {

    private static FileProcessorFactory fileProcessorFactoryInstance;

    public static synchronized FileProcessorFactory getInstance() {
        if (Objects.isNull(fileProcessorFactoryInstance)) {
            fileProcessorFactoryInstance = new FileProcessorFactory();
        }
        return fileProcessorFactoryInstance;
    }

    @Override
    public FileProcessor getFileProcessor(FileProcessorType fileProcessorType) throws Exception {
        if (fileProcessorType.equals(CUSTOMER)) {
            return CustomerFileProcessor.getInstance();
        }

        if (fileProcessorType.equals(SALE)) {
            return SaleFileProcessor.getInstance();
        }

        if (fileProcessorType.equals(SALESMAN)) {
            return SalesmanFileProcessor.getInstance();
        }

        throw new Exception("No FileProcessor found fot type: " + fileProcessorType.toString());
    }
}
