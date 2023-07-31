package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.CompanyDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchAddressException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCompanyException;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies().stream().map(companyService::getDtoFromCompany).toList();
        if (companies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable("id") Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        if (company.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(companyService.getDtoFromCompany(company.get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO newCompany) {
        try {
            Company createdCompany = companyService.createCompany(newCompany);
            return new ResponseEntity<>(companyService.getDtoFromCompany(createdCompany), HttpStatus.CREATED);
        } catch (NoSuchAddressException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO company, @PathVariable("id") Long id) {
        try {
            Company updatedCompany = companyService.updateCompany(company, id);
        return new ResponseEntity<>(companyService.getDtoFromCompany(updatedCompany), HttpStatus.OK);
        } catch (NoSuchAddressException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchCompanyException ex) {
            try {
                Company createdCompany = companyService.createCompanyWithGivenId(company, id);
                return new ResponseEntity<>(companyService.getDtoFromCompany(createdCompany), HttpStatus.CREATED);
            } catch (NoSuchAddressException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCompanies() {
        companyService.deleteAllCompanies();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompanyById(@PathVariable("id") Long id) {
        companyService.deleteCompanyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
