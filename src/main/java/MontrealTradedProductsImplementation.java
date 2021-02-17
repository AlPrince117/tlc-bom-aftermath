import java.util.*;

/**
 * We created a class to handle trading logics, this class implements the MontrealTradedProducts
 * interface.
 */
public class MontrealTradedProductsImplementation implements MontrealTradedProducts {

    /**
     * Holds a list of products registered
     */
    private final List<Product> registeredProductsList = new ArrayList<>();
    /**
     * Holds a map of traded products and the quantities traded
     */
    private final Map<Product, Integer> tradedProductsMap = new HashMap<>();

    /**
     * Gets the size of the registered products list
     * NB: This is not part of the exams requirement. It is added to play with different testing options
     *
     * @return int
     */
    public int registeredProductSize() {
        return this.registeredProductsList.size();
    }

    /**
     * Gets the size of the traded products list
     * NB: This is not part of the exams requirement. It is added to play with different testing options
     *
     * @return int
     */
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
        //1. To add a new product, ensure the product has not already been added
        Optional<Product> optionalProduct = this.registeredProductsList
                // stream all registered products
                .stream()
                // get all products matching the id of the product we want to add
                .filter(p -> p.getId().equals(product.getId()))
                // if there are any matches, get the first product match. This returns an optional product since there may be no matches
                .findFirst();

        //2. if the product already exists, throw an exception
        if (optionalProduct.isPresent()) {
            throw new ProductAlreadyRegisteredException("Product with id " + product.getId() + " already exist");
        }

        //3. if the product does not already exist, add it now
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
        //1. Only traded products can be traded, if the product is not registered, return
        if (!this.registeredProductsList.contains(product)) {
            return;
        }

        // if the product has been traded already, just increment the quantity
        // ie. if product id 34 has been traded with quantity 3 already and user still wants
        // to trade with quantity 4, get the product and update the quantity to 3 + 4 = 7
        if (this.tradedProductsMap.containsKey(product)) {
            // set the product as the key again, get the previous traded quantity and add it to the
            //new quantity as explained above
            this.tradedProductsMap.put(product, this.tradedProductsMap.get(product) + quantity);
        } else {
            // if the product is yet to be traded, add product as key and provide the quantity
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
        // To get all the total quantities traded, get the list of the products traded
        // then extract only their quantities as a stream, note that the products are the map keys and
        // the quantities are the values
        // map the quantities to integer stream so you can use sum method on it.
        // ie product1 - 4, product2 - 3, product3 - 5 picks only 4, 3, 5 and sums it to return 12
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
        // To get the total values traded, we need to get all the products
        // get each product price and multiply it with the quantities traded (lets call the result value)
        // then the value for all the products are summed up
        //ie product1 (price = 1.0) - 4, product2(price = 2.0) - 3, after the value calculation for each, it becomes
        // product1 - 4.0, product2, 6.0, the values for each is taken and summed up
        //4.0 + 6.0 = 10.0 is the total value for traded products
        return this.tradedProductsMap.keySet()
                // get the keys which is the products
                //stream them
                .stream()
                // for each product, get the price,
                // use the product, been a key to get the quantity traded,
                // multiply the price with the quantity
                // stream the values as doubles
                .mapToDouble(p -> p.getPrice() * this.tradedProductsMap.get(p))
                //sum them up
                .sum();
    }
}
