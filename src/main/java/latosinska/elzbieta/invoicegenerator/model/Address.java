package latosinska.elzbieta.invoicegenerator.model;


import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.exceptions.IllegalAddressNumberException;
import latosinska.elzbieta.invoicegenerator.service.AddressService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "Addresses")
@Getter @Setter @NoArgsConstructor
public class Address {
    private @Id @GeneratedValue Long id;
    @Column
    private String street;
    @Column(nullable = false, name = "building_number")
    private String buildingNumber;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Column
    private String country;


    public Address(String street, String buildingNumber, String apartmentNumber, String city, String postalCode, String country) {
        if(!AddressService.isValidNumber(buildingNumber) || !AddressService.isValidNumber(apartmentNumber))  throw new IllegalArgumentException();
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }


    public void setBuildingNumber(String buildingNumber) {
        if(!AddressService.isValidNumber(buildingNumber)) throw new IllegalAddressNumberException();
        this.buildingNumber = buildingNumber;
    }


    public void setApartmentNumber(String apartmentNumber) {

        if(!AddressService.isValidNumber(apartmentNumber)) throw new IllegalAddressNumberException();
        this.apartmentNumber = apartmentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(buildingNumber, address.buildingNumber) && Objects.equals(apartmentNumber, address.apartmentNumber) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, buildingNumber, apartmentNumber, city, postalCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", apartmentNumber=" + apartmentNumber +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
