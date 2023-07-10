package latosinska.elzbieta.invoicegenerator.repository;

import latosinska.elzbieta.invoicegenerator.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
