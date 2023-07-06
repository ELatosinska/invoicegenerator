package latosinska.elzbieta.invoicegenerator.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @PrimaryKeyJoinColumn
    @OneToOne
    private Address address;
}
