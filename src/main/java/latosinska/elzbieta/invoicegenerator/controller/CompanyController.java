package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.repository.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins="localhost:8081")
@RestController
@RequestMapping(value="/api/companies")
public class CompanyController {
    @Resource
    private CompanyRepository companyRepository;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return new ResponseEntity<>(companyRepository.findAll(), HttpStatus.OK);
    }
}
