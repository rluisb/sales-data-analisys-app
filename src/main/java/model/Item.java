package model;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Item {

    private final Long id;
    private final Integer amount;
    private final Double price;

    public Item(Long id, Integer amount, Double price) {
        this.id = Stream.of(id)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Item's id can't be null"));
        ;
        this.amount = Stream.of(amount)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Item's amount can't be null"));

        this.price = Stream.of(price)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Item's price can't be null"));
    }

    public Long getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
