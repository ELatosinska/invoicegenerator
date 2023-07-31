package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.AddressDTO;

import latosinska.elzbieta.invoicegenerator.exception.InvalidAddressNumberException;

import latosinska.elzbieta.invoicegenerator.exception.InvalidPostalCodeException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchAddressException;
import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddressService {
    @Resource
    private AddressRepository addressRepository;

    private static final Pattern numbersPattern = Pattern.compile("\\d+[a-z]?");
    private static final Pattern postalCodePattern = Pattern.compile("\\d{2}-\\d{3}");

    public static boolean isValidNumber(String number) {
        Matcher matcher = numbersPattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isValidPostalCode(String postalCode) {
        Matcher matcher = postalCodePattern.matcher(postalCode);
        return matcher.matches();
    }

    public List<Address> getAddresses() {
        return new ArrayList<>(addressRepository.findAll());
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }


    public Address createAddress(AddressDTO address) throws InvalidAddressNumberException, InvalidPostalCodeException {
        return addressRepository.save(getAddressFromDTO(address));
    }

    public Address createAddressWithGivenId(AddressDTO address, Long id) throws InvalidAddressNumberException, InvalidPostalCodeException {

        return new Address(id, address);
    }



    public Address updateAddress(AddressDTO newAddress, Long addressToUpdateId) throws NoSuchAddressException, InvalidAddressNumberException, InvalidPostalCodeException {

        Optional<Address> addressToUpdate = addressRepository.findById(addressToUpdateId);
        if (addressToUpdate.isEmpty()) throw new NoSuchAddressException();
        return addressRepository.save(createAddressWithGivenId(newAddress, addressToUpdateId));
    }

    public void deleteAllAddresses() {
        addressRepository.deleteAll();
    }

    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    public AddressDTO getDTOFromAddress(Address address) {
        return new AddressDTO(address.getId(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber(),
                address.getCity(),
                address.getPostalCode(),
                address.getCountry());
    }
    public Address getAddressFromDTO(AddressDTO addressDTO) throws InvalidAddressNumberException, InvalidPostalCodeException {

        return new Address(
                addressDTO.street(),
                addressDTO.buildingNumber(),
                addressDTO.apartmentNumber(),
                addressDTO.city(),
                addressDTO.postalCode(),
                addressDTO.country());
    }


}
