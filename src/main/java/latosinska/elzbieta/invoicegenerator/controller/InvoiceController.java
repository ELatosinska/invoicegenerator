package latosinska.elzbieta.invoicegenerator.controller;

import jakarta.annotation.Resource;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceDTO;
import latosinska.elzbieta.invoicegenerator.dto.InvoiceItemDTO;
import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.service.CompanyService;
import latosinska.elzbieta.invoicegenerator.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<InvoiceDTO> invoices =  invoiceService.getAllInvoices().stream().map(invoice -> getDtoFromInvoice(invoice)).toList();
        return invoices.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public Invoice addItemToInvoice(@PathVariable("id") Long id, @RequestBody InvoiceItemDTO invoiceItemDTO) {
        return invoiceService.addItemToInvoice(id, invoiceItemDTO.productId(), invoiceItemDTO.quantity());
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.createInvoice(companyService.getCompanyById(invoiceDTO.vendorId()).get(), companyService.getCompanyById(invoiceDTO.vendeeId()).get());
    }

    private InvoiceDTO getDtoFromInvoice(Invoice invoice) {
        return new InvoiceDTO(invoice.getVendor().getId(), invoice.getVendee().getId(), invoiceService.getInvoiceItems(invoice));
    }
}
