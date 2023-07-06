package latosinska.elzbieta.invoicegenerator.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @Column
    @Nullable
    private Double netPrice;

    @ManyToOne()
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;
}
