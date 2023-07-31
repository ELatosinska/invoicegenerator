package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class Company {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @PrimaryKeyJoinColumn
    @OneToOne
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name) && Objects.equals(address, company.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
