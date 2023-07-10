package latosinska.elzbieta.invoicegenerator.controller;

import latosinska.elzbieta.invoicegenerator.repository.AddressRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "localhost:8081")
@RestController
@RequestMapping("/api/addresses")
public class AddressController{
    AddressRepository addressRepository;


}
