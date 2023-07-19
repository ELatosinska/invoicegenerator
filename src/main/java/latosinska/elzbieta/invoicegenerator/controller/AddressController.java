package latosinska.elzbieta.invoicegenerator.controller;


import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.AddressDTO;
import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.repository.AddressRepository;
import latosinska.elzbieta.invoicegenerator.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/addresses")

public class AddressController {
    @Resource
    AddressService addressService;
    @Resource
    AddressRepository addressRepository;
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<AddressDTO> addresses = addressService.getAddresses().stream()
                .map(addressService::getDTOFromAddress)
                .toList();
        return addresses.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long id) {
        return addressService.getAddressById(id)
                .map(addressService::getDTOFromAddress)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        try {
            return new ResponseEntity<>(addressRepository.save(address), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @PathVariable("id") Long id) {
        try {
            Optional<Address> addressToUpdate = addressRepository.findById(id);
            if (addressToUpdate.isPresent()) {
                Address updatingAddress = addressToUpdate.get();
                if (address.getCity() != null) updatingAddress.setCity(address.getCity());
                if (address.getCountry() != null) updatingAddress.setCountry(address.getCountry());
                if (address.getPostalCode() != null) updatingAddress.setPostalCode(address.getPostalCode());
                if (address.getApartmentNumber() != null)
                    updatingAddress.setApartmentNumber(address.getApartmentNumber());
                if (address.getBuildingNumber() != null) updatingAddress.setBuildingNumber(address.getBuildingNumber());
                if (address.getStreet() != null) updatingAddress.setStreet(address.getStreet());
                return new ResponseEntity<>(addressRepository.save(updatingAddress), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllAddresses() {
        addressRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddressById(@PathVariable("id") Long id) {
        try {
            Optional<Address> addressToDelete = addressRepository.findById(id);
            if (addressToDelete.isPresent()) {
                addressRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }
