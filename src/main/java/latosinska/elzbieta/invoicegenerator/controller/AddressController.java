package latosinska.elzbieta.invoicegenerator.controller;

import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.model.Category;
import latosinska.elzbieta.invoicegenerator.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/addresses")
public class AddressController{
    @Autowired
    AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Address>> getAddresses() {
        List<Address> addresses = new ArrayList<>(addressRepository.findAll());
        return addresses.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(addresses, HttpStatus.OK);
    }

}
