package latosinska.elzbieta.invoicegenerator.service;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceItemDTO;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.model.InvoiceItem;
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

    public Invoice createInvoice(Company vendor, Company vendee) { //TODO: implement
        return invoiceRepository.save(new Invoice(vendor, vendee));
    }

    public void deleteAllInvoices() {
        invoiceRepository.deleteAll();
    }

    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Invoice addItemToInvoice(Long invoiceId, Long productId, int quantity) {
        Invoice invoice = getInvoiceById(invoiceId).get();
        addItem(productService.getProductById(productId).get(), quantity, invoice);
    return invoice;
    }

    public Double calculateTotalNetPrice(Invoice invoice) {
        return PriceService.roundPrice(invoiceItemService.getItemsByInvoice(invoice).stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*item.getQuantity())
                .sum());
    }
    public Double calculateTotalTaxPrice(Invoice invoice) {
        return PriceService.roundPrice(invoiceItemService.getItemsByInvoice(invoice).stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*((double) item.getProduct().getCategory().getTaxRateInPercent() /100)*item.getQuantity())
                .sum());
    }
    public Double calculateTotalGrossPrice(Invoice invoice) {
        return PriceService.roundPrice(calculateTotalNetPrice(invoice)+calculateTotalTaxPrice(invoice));
    }

    public void addItem(Product product, int quantity, Invoice invoice) {
        if(isProductOnTheInvoice(product, invoice)) {
            addQuantityOfExistingProduct(product, quantity, invoice);
        } else {
            invoiceItemService.createInvoiceItem(product, quantity, invoice);
        }
        invoiceRepository.save(invoice);
    }

    private void addQuantityOfExistingProduct(Product product, int quantity, Invoice invoice) {
        invoiceItemService.getItemsByInvoice(invoice).stream()
                .filter(item -> item.getProduct().equals(product))
                .forEach(item -> item.setQuantity(item.getQuantity() + quantity));
    }

    private boolean isProductOnTheInvoice(Product product, Invoice invoice) {
        return invoiceItemService.getItemsByInvoice(invoice).stream().map(InvoiceItem::getProduct).anyMatch(invoiceProduct -> invoiceProduct.equals(product));
    }

    public Collection<InvoiceItem> getInvoiceItems(Invoice invoice) {
        return invoiceItemService.getItemsByInvoice(invoice);
    }
}
