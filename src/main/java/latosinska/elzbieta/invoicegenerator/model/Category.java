package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name="categories")
public class Category {
    private @Id @GeneratedValue long id;
    @Column(unique = true)
    private String name;
    @Column(nullable = false, name = "tax_rate_in_percent")
    private Integer taxRateInPercent;

    public Category() {
    }

    public Category(String name, int taxRateInPercent) {
        this.name = name;
        this.taxRateInPercent = taxRateInPercent;
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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTaxRateInPercent(Integer taxRateInPercent) {
        this.taxRateInPercent = taxRateInPercent;
    }
}
