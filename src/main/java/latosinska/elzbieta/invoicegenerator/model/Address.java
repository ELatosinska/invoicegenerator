package latosinska.elzbieta.invoicegenerator.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "Addresses")
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

    Pattern numbersPattern = Pattern.compile("\\d+[a-z]?");

    public Address() {
    }

    public Address(String street, String buildingNumber, String apartmentNumber, String city, String postalCode, String country) {
        if(!isValidNumber(buildingNumber) || !isValidNumber(apartmentNumber))  throw new IllegalArgumentException();
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        if(!isValidNumber(buildingNumber)) throw new IllegalArgumentException();
        this.buildingNumber = buildingNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {

        if(!isValidNumber(apartmentNumber)) throw new IllegalArgumentException();
        this.apartmentNumber = apartmentNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    private boolean isValidNumber(String number) {
        Matcher matcher = numbersPattern.matcher(number);
        return matcher.matches();
    }
}
