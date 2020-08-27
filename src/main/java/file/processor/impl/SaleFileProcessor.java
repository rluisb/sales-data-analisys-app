package file.processor.impl;

import file.processor.FileProcessor;
import model.Item;
import model.Sale;
import repository.SaleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaleFileProcessor implements FileProcessor {

    private static SaleFileProcessor saleFileProcessorInstance;
    private static final SaleRepository saleRepository = SaleRepository.getInstance();
    private static final String saleRegex = "003ç([0-9]+)ç(.*)ç(.*)";
    private static final String itemRegex = "([-+]?[0-9]*\\.?[0-9]*)-([-+]?[0-9]*\\.?[0-9]*)-([-+]?[0-9]*\\.?[0-9]*)";

    public static synchronized SaleFileProcessor getInstance() {
        if (Objects.isNull(saleFileProcessorInstance)) {
            saleFileProcessorInstance = new SaleFileProcessor();
        }
        return saleFileProcessorInstance;
    }

    @Override
    public void process(String saleLine) {
        Stream.of(saleLine)
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .map(line -> this.getMatcherFromPatternForLine(saleRegex, line))
                .filter(Matcher::find)
                .map(this::getSaleFromContent)
                .forEach(saleRepository::save);
    }

    private Sale getSaleFromContent(Matcher matcher) {
        return new Sale(Long.valueOf(matcher.group(1)),
                matcher.group(3),
                getItemListFromSaleContent(matcher.group(2)));
    }

    private List<Item> getItemListFromSaleContent(String itemContent) {
        return Stream.of(itemContent)
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .map(line -> this.getMatcherFromPatternForLine(itemRegex, line))
                .filter(Matcher::find)
                .map(this::getItemFromLineContent)
                .collect(Collectors.toUnmodifiableList());
    }

    private Item getItemFromLineContent(Matcher matcher) {
        return new Item(Long.valueOf(matcher.group(1)),
                Integer.valueOf(matcher.group(2)),
                Double.valueOf(matcher.group(3)));
    }

    private Matcher getMatcherFromPatternForLine(String regex, String line) {
        return Pattern.compile(regex).matcher(line);
    }
}
