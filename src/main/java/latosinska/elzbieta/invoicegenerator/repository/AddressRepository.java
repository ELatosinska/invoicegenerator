package latosinska.elzbieta.invoicegenerator.repository;

import latosinska.elzbieta.invoicegenerator.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
