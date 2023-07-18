package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.service.PriceService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    private @Id
    @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "net_price")
    private Double netPrice;

    @ManyToOne()
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    public Product(String name, Double netPrice, Category category) {
        this.name = name;
        this.netPrice = netPrice;
        this.category = category;
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = PriceService.roundPrice(netPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(netPrice, product.netPrice) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, netPrice, category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", netPrice=" + netPrice +
                ", category=" + category +
                '}';
    }
}


