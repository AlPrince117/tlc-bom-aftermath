/**
 * This product Stock will extend from Product since its a kind of Product
 * In this case, the ProductServicePricing interface is implemented
 */
public class Stock extends Product implements ProductPricingService {
    private String ticker, exchange;

    public Stock(String id, String ticker, String exchange) {
        super(id);
        this.ticker = ticker;
        this.exchange = exchange;
    }

    /**
     * This getPrice is inherited from the Product class.
     * The implementation uses a suitable price method from ProductPricingService interface
     * The necessary parameters are passed
     * @return double
     */
    @Override
    public double getPrice() {
        return this.price(this.exchange, this.ticker);
    }

    /**
     * This is a price method inherited from ProductPricingService interface.
     * This method signature is  suitable for Stock product so we call it in getPrice
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
     * This method signature is not  suitable for Stock product so we dont call it in getPrice
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
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }


}
