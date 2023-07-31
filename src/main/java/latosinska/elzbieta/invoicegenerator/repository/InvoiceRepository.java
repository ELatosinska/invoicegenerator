package latosinska.elzbieta.invoicegenerator.repository;

import latosinska.elzbieta.invoicegenerator.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
