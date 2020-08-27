package repository;

import model.Sale;

import java.util.*;
import java.util.stream.Collectors;

public class SaleRepository implements Repository<Sale> {

    private static SaleRepository saleRepositoryInstance;

    private final Map<Long, Sale> saleDatabase;

    private SaleRepository() {
        this.saleDatabase = new HashMap<>();
    }

    public static synchronized SaleRepository getInstance() {
        if (Objects.isNull(saleRepositoryInstance)) {
            saleRepositoryInstance = new SaleRepository();
        }
        return saleRepositoryInstance;
    }

    @Override
    public List<Sale> findAll() {
        return saleDatabase.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Sale save(Sale sale) {
        return saleDatabase.put(sale.getId(), sale);
    }
}
