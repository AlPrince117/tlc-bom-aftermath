import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest {

    /**
     * This is a concrete implementation for trading related activities
     */
    MontrealTradedProductsImplementation montrealTradedProductsImplementation;

    /**
     * This method is called just before each test methods are run.
     * Before each test runs, we create a new trading class
     */
    @BeforeEach
    void setUp() {
        // instantiate this class for trading activities
        montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();
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
        Product product = new Stock("12", "AAPL", "EXCH1");
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
        Product product = new Stock("12", "AAPL", "EXCH1");

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
        Product product = new Stock("13", "AAPL", "EXCH1");
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
        Product product = new Stock("12", "AAPL", "EXCH1");
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, anyInt());
    }

    @Test
    public void productTradedQuantityIsValid() throws ProductAlreadyRegisteredException {
        // To check the quantity of the traded product is valid,
        // trade a product with known quantity, ie 4
        //we should expect that the method totalTradeQuantityForDay should return 4
        Product product = new Stock("12", "AAPL", "EXCH1");
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

        Product product = mock(Stock.class); //mock the product
        when(product.getId()).thenReturn("34"); //mock the id to return if getId is called - this is needed since in adding a product, we need the id
        when(product.getPrice()).thenReturn(10.0); //mock the price to return when the getPrice is called

        montrealTradedProductsImplementation.addNewProduct(product); // register the product
        montrealTradedProductsImplementation.trade(product, 4); // trade the product

        // calculate what is expected, multiply quantity by price
        //NB: if you have multiple products, you must test for all of them
        // expected value = 4 * 10 = 40.0
        assertEquals(40.0, montrealTradedProductsImplementation.totalValueOfDaysTradedProducts(), "Total values for traded product is not valid");
    }
}