package repository;

import model.Salesman;

import java.util.*;
import java.util.stream.Collectors;

public class SalesmanRepository implements Repository<Salesman> {

    private static SalesmanRepository salesmanRepositoryInstance;

    private final Map<String, Salesman> salesmanDatabase;

    private SalesmanRepository() {
        this.salesmanDatabase = new TreeMap<>();
    }

    public static synchronized SalesmanRepository getInstance() {
        if (Objects.isNull(salesmanRepositoryInstance)) {
            salesmanRepositoryInstance = new SalesmanRepository();
        }
        return salesmanRepositoryInstance;
    }

    @Override
    public List<Salesman> findAll() {
        return salesmanDatabase.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Salesman save(Salesman salesman) {
        return salesmanDatabase.put(salesman.getCpf(), salesman);
    }
}
