package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="categories")
public class Category {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "tax_rate_in_percent")
    private Integer taxRateInPercent;
    @OneToMany(mappedBy = "category")
    private Collection<Product> products;

    public Category() {
    }

    public Category(String name, int taxRateInPercent) {
        this.name = name;
        this.taxRateInPercent = taxRateInPercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTaxRateInPercent() {
        return taxRateInPercent;
    }

    public void setTaxRateInPercent(int taxRateInPercent) {
        this.taxRateInPercent = taxRateInPercent;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
