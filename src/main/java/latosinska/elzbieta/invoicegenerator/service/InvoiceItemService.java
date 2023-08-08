package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceItemDTO;
import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.model.InvoiceItem;
import latosinska.elzbieta.invoicegenerator.model.Product;
import latosinska.elzbieta.invoicegenerator.repository.InvoiceItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemService {
    @Resource
    private InvoiceItemRepository invoiceItemRepository;

    public List<InvoiceItem> getItemsByInvoice(Invoice invoice) {
        return invoiceItemRepository.findByInvoice(invoice);
    }

    public InvoiceItem createInvoiceItem(Product product, int quantity, Invoice invoice) {
        return invoiceItemRepository.save(new InvoiceItem(product, quantity, invoice));
    }

    public InvoiceItemDTO getDTOFromItem(InvoiceItem invoiceItem) {
        return new InvoiceItemDTO(invoiceItem.getProduct(), invoiceItem.getQuantity());
    }
}
