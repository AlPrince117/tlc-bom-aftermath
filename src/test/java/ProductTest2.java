import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductTest2 {
    ProductPricingService productPricingService;
    MontrealTradedProductsImplementation montrealTradedProductsImplementation;

    @BeforeEach
    void setUp() {
        productPricingService = mock(ProductPricingService.class);
        montrealTradedProductsImplementation = new MontrealTradedProductsImplementation();

        // stock mock price
        when(productPricingService.price(anyString(), anyString())).thenReturn(10.0);
        // future mock price
        when(productPricingService.price(anyString(), anyString(), anyInt(), anyInt())).thenReturn(20.0);
    }

    @Test
    public void productsCanBeAdded() throws ProductAlreadyRegisteredException {
        Product product = mock(Product.class);
        montrealTradedProductsImplementation.addNewProduct(product);

        assertEquals(1, montrealTradedProductsImplementation.registeredProductSize(), "Product could not be registered");
    }

    @Test
    public void addingDuplicateProductsThrowProductAlreadyRegisteredException() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);

        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Duplicate product did not throw exception");
    }

    @Test
    public void productsCanBeAddedAndDuplicateProductThrowsException() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);

        assertThrows(ProductAlreadyRegisteredException.class, () -> {
            montrealTradedProductsImplementation.addNewProduct(product);
            montrealTradedProductsImplementation.addNewProduct(product);
        }, "Products adding failed and duplicate product did not throw exception");
    }

    //UnRegisteredProductCannotBeTraded
    @Test
    public void unRegisteredProductCannotBeTraded() {
        Product product = mock(Product.class);
        montrealTradedProductsImplementation.trade(product, 2);
        assertEquals(0, montrealTradedProductsImplementation.tradedProductsSize(), "Un-registered product was traded.");
    }
    //RegisteredProductCanBeTraded
    @Test
    public void registeredProductCanBeTraded() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("13", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 4);
        assertEquals(1, montrealTradedProductsImplementation.tradedProductsSize(), "Registered product was not traded.");
    }

    //productCanBeTraded
    @Test
    public void productCanBeTraded() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 3);
    }

    @Test
    public void productTradedQuantityIsValid() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 4);
        assertEquals(4, montrealTradedProductsImplementation.totalTradeQuantityForDay(), "Product traded quantity is not valid");
    }

    @Test
    public void totalValueOfDaysTradedProductsIsValid() throws ProductAlreadyRegisteredException {
        Product product = new Stock2("12", "AAPL", "EXCH1", productPricingService);
        montrealTradedProductsImplementation.addNewProduct(product);
        montrealTradedProductsImplementation.trade(product, 4);

        // expected value = 4 * 10 = 40.0
        assertEquals(40.0, montrealTradedProductsImplementation.totalValueOfDaysTradedProducts(), "Total values for traded product is not valid");
    }

}