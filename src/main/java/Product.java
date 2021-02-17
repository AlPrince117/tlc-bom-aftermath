/**
 * Make product an abstract class: so that the specific products extend and add more functionality
 */
public abstract class Product {
    /**
     * all products will have a product id
     */
    private String id;

    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Price calculation will depend on the specific product ie Stock or Future,
     * make this abstract so that the implementation is handled by the actual product
     */
    public abstract double getPrice();
}
