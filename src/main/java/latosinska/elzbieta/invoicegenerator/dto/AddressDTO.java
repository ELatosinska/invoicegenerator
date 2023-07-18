package latosinska.elzbieta.invoicegenerator.dto;

import latosinska.elzbieta.invoicegenerator.exceptions.IllegalAddressNumberException;
import latosinska.elzbieta.invoicegenerator.service.AddressService;
import lombok.Getter;

import java.util.Objects;

@Getter
public class AddressDTO {
    private Long id;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String city;
    private String postalCode;
    private String country;


    public AddressDTO(String street, String buildingNumber, String apartmentNumber, String city, String postalCode, String country) {
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
        AddressDTO that = (AddressDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(street, that.street) && Objects.equals(buildingNumber, that.buildingNumber) && Objects.equals(apartmentNumber, that.apartmentNumber) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, buildingNumber, apartmentNumber, city, postalCode, country);
    }
}
