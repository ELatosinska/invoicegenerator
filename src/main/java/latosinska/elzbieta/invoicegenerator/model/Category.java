package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name="categories")
@Getter @Setter @NoArgsConstructor
public class Category {
    private @Id @GeneratedValue long id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false, name = "tax_rate_in_percent")
    private Integer taxRateInPercent;


    public Category(String name, int taxRateInPercent) {
        this.name = name;
        this.taxRateInPercent = taxRateInPercent;
    }

    //TODO: add validation to taxRate
}
