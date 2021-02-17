/**
 * This product Future2 will extend from Product since its a kind of Product
 * In this case, the ProductServicePricing interface is used as a member variable
 */
public class Future2 extends Product {
    private String exchange, contractCode;
    private int month, year;
    /**
     * The product pricing service interface is declared as a member variable and expected to be
     * passed via the constructor whenever the Future2 is to be created
     */
    private final ProductPricingService productPricingService;

    public Future2(String id, String exchange, String contractCode, int month, int year, ProductPricingService productPricingService) {
        super(id);
        this.exchange = exchange;
        this.contractCode = contractCode;
        this.month = month;
        this.year = year;
        this.productPricingService = productPricingService;
    }

    /**
     * This getPrice is inherited from the Product class.
     * The implementation uses a suitable price method from ProductPricingService interface
     * Because the interface is treated as a member variable, it is called and the price suitable
     * for this Class Future2 is called and the parameters passed to it.
     * @return double
     */
    @Override
    public double getPrice() {
        return this.productPricingService.price(this.exchange, this.contractCode, this.month, this.year);
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
