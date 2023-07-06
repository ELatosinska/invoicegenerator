package latosinska.elzbieta.invoicegenerator.entities;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Collection;

@Entity
@Table(name="categories")
public class Category {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int taxRateInPercent;
    @OneToMany(mappedBy = "category")
    private Collection<Product> products;

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
