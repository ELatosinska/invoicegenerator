package latosinska.elzbieta.invoicegenerator.service;

import latosinska.elzbieta.invoicegenerator.model.Product;

import java.util.Collection;

public class ProductService {
    private Collection<Product> products;

    public static Double calculateGrossPrice(Product product) {
        return product.getNetPrice()*(product.getCategory().getTaxRateInPercent()+1);
    }
}
