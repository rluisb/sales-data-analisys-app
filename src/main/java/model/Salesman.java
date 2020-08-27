package model;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.stream.Stream;

public class Salesman {

    private String cpf;
    private String name;
    private BigDecimal salary;

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
        return "Salesman{" +
                "cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
