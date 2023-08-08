package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.service.PriceService;
import lombok.*;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor @EqualsAndHashCode
@ToString
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
    public Product(Long id, String name, Double netPrice, Category category) {
        this.name = name;
        this.netPrice = netPrice;
        this.category = category;
    }

    public Product(Long id, String name, Category category) {
        this.name = name;
        this.category = category;
    }


    public void setNetPrice(Double netPrice) {
        this.netPrice = PriceService.roundPrice(netPrice);
    }


}


