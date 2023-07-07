package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "companies")
public class Company {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String name;
    @PrimaryKeyJoinColumn
    @Column(name="address")
    @OneToOne
    private Address address;

    public Company() {
    }
    public Company(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
