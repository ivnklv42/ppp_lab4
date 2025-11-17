package onlinestore;

import java.util.Locale;

public class Product {
    private String title;
    private double price;
    private ProductType productType;

    public Product(String title, double price, ProductType productType) {
        this.title = title;
        this.price = price;
        this.productType = productType;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s - %.2f - %s", this.title, this.price, this.productType.toString());
    }
}
