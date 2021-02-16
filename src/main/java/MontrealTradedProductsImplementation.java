import java.util.*;

public class MontrealTradedProductsImplementation implements MontrealTradedProducts {

    private List<Product> registeredProductsList = new ArrayList<>();
    private Map<Product, Integer> tradedProductsMap = new HashMap<>();

    public int registeredProductSize() {
        return this.registeredProductsList.size();
    }

    public int tradedProductsSize() {
        return this.tradedProductsMap.size();
    }

    /**
     * Adds a new product to the system that
     * the class will track statistics for
     *
     * @param product add a product available for trading
     * @throws ProductAlreadyRegisteredException thrown
     *                                           when a product is registered twice
     */
    @Override
    public void addNewProduct(Product product) throws ProductAlreadyRegisteredException {
        Optional<Product> optionalProduct = this.registeredProductsList
                .stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();

        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyRegisteredException("Product with id " + product.getId() + " already exist");
        }

        this.registeredProductsList.add(product);
    }

    /**
     * Books a quantity against the product traded. If the
     * product
     * has not been registered, no quantity is recorded as
     * <p>
     * it is not a product we are required to track.
     *
     * @param product  the product traded
     * @param quantity the quantity traded
     */
    @Override
    public void trade(Product product, int quantity) {
        // if not registered, return
        if (!this.registeredProductsList.contains(product)) {
            return;
        }

        if (this.tradedProductsMap.containsKey(product)) {
            // increment
            this.tradedProductsMap.put(product, this.tradedProductsMap.get(product) + quantity);
        } else {
            // add
            this.tradedProductsMap.put(product, quantity);
        }
    }

    /**
     * Calculates the total quantity of all the registered
     * traded products so far today
     *
     * @return the total quantity traded
     */
    @Override
    public int totalTradeQuantityForDay() {
        return this.tradedProductsMap.values().stream().mapToInt(q -> q).sum();
    }

    /**
     * Calculates the total value of all the registered traded
     * products
     * so far today. This is done by multiplying the value by
     * the quantity
     * traded.
     *
     * @return the total value of today&#39;s traded products that
     * are
     * registered in the system
     */
    @Override
    public double totalValueOfDaysTradedProducts() {
        return this.tradedProductsMap.keySet()
                .stream()
                .mapToDouble(p -> p.getPrice() * this.tradedProductsMap.get(p))
                .sum();
    }
}
