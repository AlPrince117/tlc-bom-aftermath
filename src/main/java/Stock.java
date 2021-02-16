public class Stock extends Product implements ProductPricingService {
    private String ticker, exchange;

    public Stock(String id, String ticker, String exchange) {
        super(id);
        this.ticker = ticker;
        this.exchange = exchange;
    }

    @Override
    public double getPrice() {
        return this.price(this.exchange, this.ticker);
    }

    @Override
    public double price(String exchange, String ticker) {
        return 0;
    }

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
