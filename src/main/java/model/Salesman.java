package model;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.stream.Stream;

public class Salesman {

    private final String cpf;
    private final String name;
    private final BigDecimal salary;

    public Salesman(String cpf, String name, BigDecimal salary) {
        this.cpf = Stream.of(cpf)
                .filter(Objects::nonNull)
                .filter(cpfValue -> !cpfValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Salesman's document can't be null or empty"));
        this.name = Stream.of(name)
                .filter(Objects::nonNull)
                .filter(nameValue -> !nameValue.isBlank())
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Salesman's name can't be null or empty"));
        this.salary = Stream.of(salary)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidParameterException("Salesman's salary can't be null"));
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "\t{\n" +
                "\t\tcpf': " + cpf + "',\n" +
                "\t\tname:'" + name + ",\n" +
                "\t\tsalary: " + salary + ",\n" +
                "\t}";
    }
}
