package latosinska.elzbieta.invoicegenerator.controller;


import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.AddressDTO;

import latosinska.elzbieta.invoicegenerator.exception.InvalidAddressNumberException;
import latosinska.elzbieta.invoicegenerator.exception.InvalidPostalCodeException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchAddressException;
import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api/addresses")

public class AddressController {
    @Resource
    AddressService addressService;

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
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO address) {
        try {
            Address newAddress = addressService.createAddress(address);
            return new ResponseEntity<>(addressService.getDTOFromAddress(newAddress), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO address, @PathVariable("id") Long id) {
        try {
            Address updatedAddress = addressService.updateAddress(address, id);
            return new ResponseEntity<>(addressService.getDTOFromAddress(updatedAddress), HttpStatus.OK);
        } catch (NoSuchAddressException ex) {
            try {
                Address newAddress = addressService.createAddressWithGivenId(address, id);
                return new ResponseEntity<>(addressService.getDTOFromAddress(newAddress), HttpStatus.CREATED);
            } catch (InvalidAddressNumberException e) {

                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (InvalidPostalCodeException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllAddresses() {
        addressService.deleteAllAddresses();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAddressById(@PathVariable("id") Long id) {
        try {
            addressService.deleteAddressById(id);
            return new ResponseEntity<>((HttpStatus.NO_CONTENT));
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

