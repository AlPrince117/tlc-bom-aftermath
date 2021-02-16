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

    @Override
    public double getPrice() {
        return this.price(this.exchange, this.contractCode, this.month, this.year);
    }

    @Override
    public double price(String exchange, String ticker) {
        return 0;
    }

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
