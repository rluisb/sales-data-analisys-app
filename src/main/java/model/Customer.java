package model;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Customer {

    private String cnpj;
    private String name;
    private String businessArea;

    public Customer(String cnpj, String name, String businessArea) {
        this.cnpj = Stream.of(cnpj)
                .filter(Objects::nonNull)
                .filter(cnpjValue -> !cnpjValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Customer's document can't be null or empty"));
        this.name = Stream.of(name)
                .filter(Objects::nonNull)
                .filter(nameValue -> !nameValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Customer's name can't be null or empty"));
        ;
        this.businessArea = Stream.of(businessArea)
                .filter(Objects::nonNull)
                .filter(businessAreaValue -> !businessAreaValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Customer's business area can't be null or empty"));
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cnpj='" + cnpj + '\'' +
                ", name='" + name + '\'' +
                ", businessArea='" + businessArea + '\'' +
                '}';
    }
}
