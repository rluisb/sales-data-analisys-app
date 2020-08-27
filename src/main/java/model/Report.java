package model;

public class Report {

    private final int customerAmount;
    private final int salesmanAmount;
    private final Long mostValuableSale;
    private final Salesman worstSalesman;

    public Report(int customerAmount, int salesmanAmount, Long mostValuableSale, Salesman worstSalesman) {
        this.customerAmount = customerAmount;
        this.salesmanAmount = salesmanAmount;
        this.mostValuableSale = mostValuableSale;
        this.worstSalesman = worstSalesman;
    }

    public byte[] getReportInBytes() {
        return this.toString().getBytes();
    }

    @Override
    public String toString() {
        return "report: {\n" +
                "\tcustomerAmount: " + customerAmount + ",\n" +
                "\tsalesmanAmount: " + salesmanAmount + ",\n" +
                "\tmostValuableSale: " + mostValuableSale + ",\n" +
                "\tworstSalesman: " + worstSalesman + ",\n" +
                '}';
    }
}
