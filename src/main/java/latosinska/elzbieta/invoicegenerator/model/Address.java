package latosinska.elzbieta.invoicegenerator.model;


import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.dto.AddressDTO;

import latosinska.elzbieta.invoicegenerator.exception.InvalidAddressNumberException;

import latosinska.elzbieta.invoicegenerator.exception.InvalidPostalCodeException;
import latosinska.elzbieta.invoicegenerator.service.AddressService;
import lombok.*;


@Entity
@Table(name = "Addresses")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
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
    @OneToOne(mappedBy = "address")
    private Company company;



    public Address(String street, String buildingNumber, String apartmentNumber, String city, String postalCode, String country) throws InvalidAddressNumberException, InvalidPostalCodeException {
        if(!AddressService.isValidNumber(buildingNumber) || !AddressService.isValidNumber(apartmentNumber))  throw new InvalidAddressNumberException();

        if(!AddressService.isValidPostalCode(postalCode)) throw new InvalidPostalCodeException();
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.apartmentNumber = apartmentNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }


    public Address(Long id, AddressDTO address) throws InvalidAddressNumberException, InvalidPostalCodeException {
        if(!AddressService.isValidNumber(buildingNumber) || !AddressService.isValidNumber(apartmentNumber))  throw new InvalidAddressNumberException();
        if(!AddressService.isValidPostalCode(postalCode)) throw new InvalidPostalCodeException();
        this.id = id;
        this.street = address.street();;
        this.buildingNumber = address.buildingNumber();
        this.apartmentNumber = address.apartmentNumber();
        this.city = address.city();
        this.postalCode = address.postalCode();
        this.country = address.country();
    }


    public void setBuildingNumber(String buildingNumber) throws InvalidAddressNumberException {
        if(!AddressService.isValidNumber(buildingNumber)) throw new InvalidAddressNumberException();
        this.buildingNumber = buildingNumber;
    }


    public void setApartmentNumber(String apartmentNumber) throws InvalidAddressNumberException {
        if(!AddressService.isValidNumber(apartmentNumber)) throw new InvalidAddressNumberException();
        this.apartmentNumber = apartmentNumber;
    }

}
