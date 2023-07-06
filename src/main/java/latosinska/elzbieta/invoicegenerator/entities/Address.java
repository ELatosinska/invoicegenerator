package latosinska.elzbieta.invoicegenerator.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "Addresses")
public class Address {
    private @Id @GeneratedValue Long id;
    @Column
    private String street;
    @Column(nullable = false)
    private Integer buildingNumber;
    @Column
    @Nullable
    private Integer apartmentNumber;
    @Column
    private String city;
    @Column
    private String postalCode;
    @Column
    private String country;

}
