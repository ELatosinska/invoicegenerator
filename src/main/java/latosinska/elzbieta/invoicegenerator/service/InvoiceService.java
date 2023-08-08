package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceItemDTO;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchInvoiceException;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Resource
    private InvoiceRepository invoiceRepository;
    @Resource
    private ProductService productService;
    @Resource
    private InvoiceItemService invoiceItemService;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Company vendor, Company vendee, Collection<InvoiceItemDTO> items) { //TODO: implement
        return invoiceRepository.save(new Invoice(vendor, vendee));
    }

    public Invoice addItemToInvoice( Long id, Product product, int quantity) throws NoSuchInvoiceException {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(NoSuchInvoiceException::new);
        invoice.addItemToInvoice(product, quantity);
        return invoiceRepository.save(invoice);
    }

    public void deleteAllInvoices() {
        invoiceRepository.deleteAll();
    }

    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }

}
