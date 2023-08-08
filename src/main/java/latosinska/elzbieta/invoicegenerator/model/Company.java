package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor @EqualsAndHashCode @ToString
public class Company {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @JoinColumn(name = "address", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Company(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
