import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest {

    @BeforeEach
    void setUp() {
        ProductPricingService productPricingService = mock(ProductPricingService.class);
        // stock mock price
        when(productPricingService.price(anyString(), anyString())).thenReturn(10.0);
        // future mock price
        when(productPricingService.price(anyString(), anyString(), anyInt(), anyInt())).thenReturn(20.0);
    }

    @Test
    public void productsCanBeAdded() throws ProductAlreadyRegisteredException {
        Product product = mock(Product.class);
        MontrealTradedProductsImplementation montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();
        montrealTradedProductsImplementation.addNewProduct(product);

        assertEquals(1, montrealTradedProductsImplementation.registeredProductSize(), "Product could not be registered");
    }

    @Test
    public void addingDuplicateProductsThrowProductAlreadyRegisteredException() throws ProductAlreadyRegisteredException {
        Product product = new Stock("12", "AAPL", "EXCH1");
        MontrealTradedProductsImplementation montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();
        montrealTradedProductsImplementation.addNewProduct(product);

        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Duplicate product did not throw exception");
    }

    @Test
    public void productsCanBeAddedAndDuplicateProductThrowsException() throws ProductAlreadyRegisteredException {
        Product product = new Stock("12", "AAPL", "EXCH1");
        MontrealTradedProductsImplementation montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();

        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            montrealTradedProductsImplementation.addNewProduct(product);
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Products adding failed and duplicate product did not throw exception");
    }

    //UnRegisteredProductCannotBeTraded
    @Test
    public void unRegisteredProductCannotBeTraded() {
        Product product = mock(Product.class);
        MontrealTradedProductsImplementation productsImplementation = new MontrealTradedProductsImplementation();
        productsImplementation.trade(product, anyInt());
        assertEquals(0, productsImplementation.tradedProductsSize(), "Un-registered product was traded.");
    }
    //RegisteredProductCanBeTraded
//    @Test
//    public void registeredProductCanBeTraded() throws ProductAlreadyRegisteredException {
//        Product product = new Stock("13", "AAPL", "EXCH1");
//        MontrealTradedProductsImplementation productsImplementation = new MontrealTradedProductsImplementation();
//        productsImplementation.addNewProduct(product);
//        productsImplementation.trade(product, anyInt());
//        assertEquals(1, productsImplementation.tradedProductsSize(), "Registered product was not traded.");
//    }

    //productCanBeTraded
    @Test
    public void productCanBeTraded() throws ProductAlreadyRegisteredException {
        Product product = new Stock("12", "AAPL", "EXCH1");
        MontrealTradedProductsImplementation productsImplementation = new MontrealTradedProductsImplementation();
        productsImplementation.addNewProduct(product);
        productsImplementation.trade(product, anyInt());
    }

    @Test
    public void productTradedQuantityIsValid() throws ProductAlreadyRegisteredException {
        Product product = new Stock("12", "AAPL", "EXCH1");
        MontrealTradedProductsImplementation productsImplementation = new MontrealTradedProductsImplementation();
        productsImplementation.addNewProduct(product);
        productsImplementation.trade(product, 4);
        assertEquals(4, productsImplementation.totalTradeQuantityForDay(), "Product traded quantity is not valid");
    }

    @Test
    public void totalValueOfDaysTradedProductsIsValid() throws ProductAlreadyRegisteredException {
         Product product = new Stock("12", "AAPL", "EXCH1");
        MontrealTradedProductsImplementation productsImplementation = new MontrealTradedProductsImplementation();
        productsImplementation.addNewProduct(product);
        productsImplementation.trade(product, 4);

        // expected value = 4 * 10 = 40.0
        assertEquals(40.0, productsImplementation.totalValueOfDaysTradedProducts(), "Total values for traded product is not valid");
    }
}