package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.exception.InvalidTaxRateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        if(taxRateInPercent < 0 || taxRateInPercent > 100) throw new InvalidTaxRateException();
        this.name = name;
        this.taxRateInPercent = taxRateInPercent;
    }

    public void setTaxRateInPercent(Integer taxRateInPercent) {
        if(taxRateInPercent < 0 || taxRateInPercent > 100) throw new InvalidTaxRateException();

        this.taxRateInPercent = taxRateInPercent;
    }


}
