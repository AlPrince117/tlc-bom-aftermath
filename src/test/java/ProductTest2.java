import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest2 {
    /**
     * We need an instance of the interface so we can mock it.
     * It is placed here so we can globally access it in all the tests
     */
    ProductPricingService productPricingService;
    /**
     * This is a concrete implementation for trading related activities
     */
    MontrealTradedProductsImplementation montrealTradedProductsImplementation;

    /**
     * This method is called just before each test methods are run.
     * Before each test runs, we mock the pricing interface and create a new trading class
     */
    @BeforeEach
    void setUp() {
        // mock the class since its an interface, a concrete class cannot be used
        // NB: wherever you call Stock2 or Future2, you need to pass this productPricingService
        productPricingService = mock(ProductPricingService.class);
        montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();

        // stock mock price
        // whenever the stock product calls price, always return this price
        when(productPricingService.price(anyString(), anyString())).thenReturn(10.0);
        // future mock price
        // whenever the future product calls price, always return this price
        when(productPricingService.price(anyString(), anyString(), anyInt(), anyInt())).thenReturn(20.0);
    }


    @Test //NB: Not directly needed in the exams. Just something to play with
    public void productsCanBeAdded() throws ProductAlreadyRegisteredException {
        //To test if a product can be added, add a product and check if the total registered product
        // is one
        Product product = mock(Product.class); // mock it since we dont need to test any other feature

        // add the product
        montrealTradedProductsImplementation.addNewProduct(product);

        // expected registered product count should be 1, else the logic is not valid
        assertEquals(1, montrealTradedProductsImplementation.registeredProductSize(), "Product could not be registered");
    }

    @Test //NB: Not directly needed in the exams. Just something to play with
    public void addingDuplicateProductsThrowProductAlreadyRegisteredException() throws ProductAlreadyRegisteredException {
        // To test if adding a duplicate product throws an exception,
        // create only one product and add it twice
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);

        // specify the exception to be thrown
        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            // it is important to add the portion of the code that should
            //throw the exception here
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Duplicate product did not throw exception");
    }

    @Test
    public void productsCanBeAddedAndDuplicateProductThrowsException() throws ProductAlreadyRegisteredException {
        // This test combines the logic for the two tests above in one
        // read on them to understand this one
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);

        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            montrealTradedProductsImplementation.addNewProduct(product);
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Products adding failed and duplicate product did not throw exception");
    }

    @Test //NB: Not directly needed in the exams. Just something to play with
    public void unRegisteredProductCannotBeTraded() {
        // If  a product is not registered, it should not be able to trade
        // to achieve that, trade a product without registering it
        // the total traded product count should be 0
        Product product = mock(Product.class); // just mock a product and trade 2 quantities
        montrealTradedProductsImplementation.trade(product, 2);
        assertEquals(0, montrealTradedProductsImplementation.tradedProductsSize(), "Un-registered product was traded.");
    }

    @Test //NB: Not directly needed in the exams. Just something to play with
    public void registeredProductCanBeTraded() throws ProductAlreadyRegisteredException {
        // We should be a able to trade a registered product
        // create a sample stock product
        Product product = new Stock2("13", "AAPL", "EXCH1", productPricingService);
        // add the product (register)
        montrealTradedProductsImplementation.addNewProduct(product);
        // trade 4 quantities, the traded product size should be 1 if successful
        montrealTradedProductsImplementation.trade(product, 4);
        assertEquals(1, montrealTradedProductsImplementation.tradedProductsSize(), "Registered product was not traded.");
    }

    @Test
    public void productCanBeTraded() throws ProductAlreadyRegisteredException {
        // To trade a product, create one, register and trade
        // NB: this appears to be what the exams demand except its hard to check
        // if the test passes or the features work
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, anyInt());
    }

    @Test
    public void productTradedQuantityIsValid() throws ProductAlreadyRegisteredException {
        // To check the quantity of the traded product is valid,
        // trade a product with known quantity, ie 4
        //we should expect that the method totalTradeQuantityForDay should return 4
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 4);
        assertEquals(4, montrealTradedProductsImplementation.totalTradeQuantityForDay(), "Product traded quantity is not valid");
    }

    @Test
    public void totalValueOfDaysTradedProductsIsValid() throws ProductAlreadyRegisteredException {
        // To check the total value of traded products,
        // register the product, trade with a know quantity
        //multiply the price of the product with the quantity and keep the result
        // calling the totalValueOfDaysTradedProducts should return the same result
        //NB: you can use multiple products as well, just be sure to calculate for all of them
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 4);

        //NB: The price to return has been mocked already in the beforeEach method, use that for the
        //calculations

        // calculate what is expected, multiply quantity by price
        //NB: if you have multiple products, you must test for all of them
        // expected value = 4 * 10 = 40.0
        assertEquals(40.0, montrealTradedProductsImplementation.totalValueOfDaysTradedProducts(), "Total values for traded product is not valid");
    }

}