/**
 * This product Future will extend from Product since its a kind of Product
 * In this case, the ProductServicePricing interface is implemented
 */
public class Future extends  Product implements ProductPricingService{
    private String exchange, contractCode;
    private int month, year;

    public Future(String id, String exchange, String contractCode, int month, int year) {
        super(id);
        this.exchange = exchange;
        this.contractCode = contractCode;
        this.month = month;
        this.year = year;
    }

    /**
     * This getPrice is inherited from the Product class.
     * The implementation uses a suitable price method from ProductPricingService interface
     * The necessary parameters are passed
     * @return double
     */
    @Override
    public double getPrice() {
        return this.price(this.exchange, this.contractCode, this.month, this.year);
    }

    /**
     * This is a price method inherited from ProductPricingService interface.
     * This method signature is not suitable for Future product so we dont call it in getPrice
     * @param exchange
     * @param ticker
     * @return double
     */
    @Override
    public double price(String exchange, String ticker) {
        return 0;
    }

    /**
     * This is a price method inherited from ProductPricingService interface.
     * This method signature is  suitable for Future product so we call it in getPrice
     * @param exchange
     * @param contractCode
     * @param month
     * @param year
     * @return
     */
    @Override
    public double price(String exchange, String contractCode, int month, int year) {
        return 0;
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
