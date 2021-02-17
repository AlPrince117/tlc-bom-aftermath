/**
 * This product Stock2 will extend from Product since its a kind of Product
 * In this case, the ProductServicePricing interface is used as a member variable
 */
public class Stock2 extends Product  {
    private String ticker, exchange;
    /**
     * The product pricing service interface is declared as a member variable and expected to be
     * passed via the constructor whenever the Stock2 is to be created
     */
    private final ProductPricingService productPricingService;

    public Stock2(String id, String ticker, String exchange, ProductPricingService productPricingService) {
        super(id);
        this.ticker = ticker;
        this.exchange = exchange;
        this.productPricingService = productPricingService;
    }

    /**
     * This getPrice is inherited from the Product class.
     * The implementation uses a suitable price method from ProductPricingService interface
     * Because the interface is treated as a member variable, it is called and the price suitable
     * for this Class Stock2 is called and the parameters passed to it.
     * @return double
     */
    @Override
    public double getPrice() {
        return this.productPricingService.price(this.exchange, this.ticker);
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
