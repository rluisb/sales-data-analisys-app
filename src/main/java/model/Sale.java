package model;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Sale {

    private Long id;
    private String salesmanName;
    private List<Item> items;

    public Sale(Long id, String salesmanName, List<Item> items) {
        this.id = Stream.of(id)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Sale's id can't be null"));
        this.salesmanName = Stream.of(salesmanName)
                .filter(Objects::nonNull)
                .filter(salesmanNameValue -> !salesmanNameValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Sale's salesmanName can't be null or empty"));
        this.items = Stream.of(items)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Salesman's items can't be null"));
    }

    public Long getId() {
        return id;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", salesmanName='" + salesmanName + '\'' +
                ", items=" + items +
                '}';
    }
}
