package service;

import model.Report;
import model.Sale;
import model.Salesman;
import repository.CustomerRepository;
import repository.SaleRepository;
import repository.SalesmanRepository;

import java.util.Comparator;

public class ReportService {

    private final SalesmanRepository salesmanRepository;
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    public ReportService(SalesmanRepository salesmanRepository,
                         SaleRepository saleRepository,
                         CustomerRepository customerRepository) {
        this.salesmanRepository = salesmanRepository;
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
    }

    public Report mountReport() {
        int customerAmount = customerRepository.count();
        int salesmanAmount = salesmanRepository.count();

        Long mostValuableSaleId = saleRepository.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getSaleTotalPrice))
                .map(Sale::getId)
                .get();

        Salesman worstSalesman = saleRepository.findAll()
                .stream()
                .min(Comparator.comparing(Sale::getSaleTotalPrice))
                .map(Sale::getSalesmanName)
                .map(salesmanRepository::findSalesmanByName)
                .get();

        return new Report(customerAmount, salesmanAmount,
                mostValuableSaleId, worstSalesman);

    }
}
