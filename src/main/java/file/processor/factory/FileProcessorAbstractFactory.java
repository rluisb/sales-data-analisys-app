package file.processor.factory;

import file.processor.FileProcessor;

public abstract class FileProcessorAbstractFactory {
    abstract FileProcessor getFileProcessor(FileProcessorType fileProcessorType) throws Exception;
}
