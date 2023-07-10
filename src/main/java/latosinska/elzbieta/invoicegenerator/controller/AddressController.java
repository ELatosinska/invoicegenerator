package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.repository.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/addresses")
public class AddressController{
    @Resource
    AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = new ArrayList<>(addressRepository.findAll());
        return addresses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable("id") Long id) {
        return addressRepository.findById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        try {
            return new ResponseEntity<>(addressRepository.save(address), HttpStatus.CREATED);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
