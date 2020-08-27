package file.processor.impl;

import file.processor.FileProcessor;
import model.Customer;
import repository.CustomerRepository;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CustomerFileProcessor implements FileProcessor {

    private static CustomerFileProcessor customerFileProcessorInstance;
    private static final CustomerRepository customerRepository = CustomerRepository.getInstance();
    private static final String customerRegex = "002ç([0-9]+)ç([ a-zA-Z áç]+)ç([ a-zA-Z áç]+)";

    public static synchronized CustomerFileProcessor getInstance() {
        if (Objects.isNull(customerFileProcessorInstance)) {
            customerFileProcessorInstance = new CustomerFileProcessor();
        }
        return customerFileProcessorInstance;
    }

    @Override
    public void process(String line) {
        Stream.of(line)
                .map(this::getMatcherFromPatternForLine)
                .filter(Matcher::find)
                .map(this::getCustomerFromContent)
                .forEach(customerRepository::save);
    }

    private Customer getCustomerFromContent(Matcher matcher) {
        return new Customer(matcher.group(1),
                matcher.group(2),
                matcher.group(3));
    }

    private Matcher getMatcherFromPatternForLine(String line) {
        return Pattern.compile(customerRegex).matcher(line);
    }
}
