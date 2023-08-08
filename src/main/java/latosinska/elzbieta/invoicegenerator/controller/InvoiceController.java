package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceDTO;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceItemDTO;
import latosinska.elzbieta.invoicegenerator.exception.LessThanOneQuantityException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchCompanyException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchInvoiceException;
import latosinska.elzbieta.invoicegenerator.exception.NoSuchProductException;
import latosinska.elzbieta.invoicegenerator.model.Company;
import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.service.CompanyService;
import latosinska.elzbieta.invoicegenerator.service.InvoiceItemService;
import latosinska.elzbieta.invoicegenerator.service.InvoiceService;
import latosinska.elzbieta.invoicegenerator.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private ProductService productService;
    @Resource
    private CompanyService companyService;
    @Resource
    private InvoiceItemService invoiceItemService;

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices().stream().map(invoice -> getDtoFromInvoice(invoice)).toList();
        return invoices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InvoiceDTO> addItemToInvoice(@PathVariable("id") Long id, @RequestBody InvoiceItemDTO invoiceItemDTO) {
        try {
            Invoice invoice = invoiceService.addItemToInvoice(id, productService.getProductById(invoiceItemDTO.productId()).orElseThrow(NoSuchProductException::new), invoiceItemDTO.quantity());
            return new ResponseEntity<>(getDtoFromInvoice(invoice), HttpStatus.OK);
        } catch (NoSuchProductException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (LessThanOneQuantityException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        try {
            Company vendor = companyService.getCompanyById(invoiceDTO.vendorId()).orElseThrow(NoSuchCompanyException::new);
            Company vendee = companyService.getCompanyById(invoiceDTO.vendeeId()).orElseThrow(NoSuchCompanyException::new);
            Collection<InvoiceItemDTO> items = invoiceDTO.items();
            Invoice invoice = invoiceService.createInvoice(vendor, vendee, items);
            return new ResponseEntity<>(getDtoFromInvoice(invoice), HttpStatus.CREATED);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private InvoiceDTO getDtoFromInvoice(Invoice invoice) {
        return new InvoiceDTO(invoice.getId(), invoice.getVendor().getId(), invoice.getVendee().getId(), invoice.getItems().stream().map(invoiceItemService::getDTOFromItem).toList());
    }
}
