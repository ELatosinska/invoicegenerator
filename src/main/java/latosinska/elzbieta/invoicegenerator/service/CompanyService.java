package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.CompanyDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchAddressException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCompanyException;
import latosinska.elzbieta.invoicegenerator.model.Address;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private AddressService addressService;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Company createCompany(CompanyDTO company) throws NoSuchAddressException {
        return companyRepository.save(getCompanyFromDTO(company));
    }

    public Company createCompanyWithGivenId(CompanyDTO companyDTO, Long id) throws NoSuchAddressException {
        Address newAddress = addressService.getAddressById(companyDTO.addressId()).orElseThrow(NoSuchAddressException::new);
        return companyRepository.save(new Company(id, companyDTO.name(), newAddress));
    }

    public Company updateCompany(CompanyDTO companyDTO, Long id) throws NoSuchCompanyException, NoSuchAddressException {
        if(!companyRepository.existsById(id)) throw new NoSuchCompanyException();
        Address newAddress = addressService.getAddressById(companyDTO.addressId()).orElseThrow(NoSuchAddressException::new);
        return companyRepository.save(new Company(id, companyDTO.name(), newAddress));
    }

    public void deleteAllCompanies() {
        companyRepository.deleteAll();
    }

    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    private Company getCompanyFromDTO(CompanyDTO company) throws NoSuchAddressException {
        Optional<Address> address = addressService.getAddressById(company.addressId());
        if (address.isEmpty()) throw new NoSuchAddressException();
        return new Company(company.name(), address.get());
    }

    public CompanyDTO getDtoFromCompany(Company company) {
        return new CompanyDTO(company.getId(), company.getName(), company.getAddress().getId());
    }
}
