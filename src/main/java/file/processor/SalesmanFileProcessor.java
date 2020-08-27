package file.processor;

import model.Salesman;
import repository.SalesmanRepository;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SalesmanFileProcessor implements FileProcessor {

    private static SalesmanFileProcessor salesmanFileProcessorInstance;
    private static final SalesmanRepository salesmanRepository = SalesmanRepository.getInstance();
    private static final String salesmanRegex = "001ç([0-9]+)ç([ a-zA-Z áç]+)ç([-+]?[0-9]*\\.?[0-9]*)";

    public static synchronized SalesmanFileProcessor getInstance() {
        if (Objects.isNull(salesmanFileProcessorInstance)) {
            salesmanFileProcessorInstance = new SalesmanFileProcessor();
        }
        return salesmanFileProcessorInstance;
    }

    @Override
    public void process(String line) {
        Stream.of(line)
                .map(this::getMatcherFromPatternForLine)
                .filter(Matcher::find)
                .map(this::getSalesmanFromContent)
                .forEach(salesmanRepository::save);
    }

    private Salesman getSalesmanFromContent(Matcher matcher) {
        return new Salesman(matcher.group(1),
                matcher.group(2),
                new BigDecimal(matcher.group(3)));
    }

    private Matcher getMatcherFromPatternForLine(String line) {
        return Pattern.compile(salesmanRegex).matcher(line);
    }
}
