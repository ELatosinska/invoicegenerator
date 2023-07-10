package latosinska.elzbieta.invoicegenerator.service;

import latosinska.elzbieta.invoicegenerator.model.Invoice;

public class InvoiceService {
    private Invoice invoice;

    public InvoiceService(Invoice invoice) {
        this.invoice = invoice;
    }

    public Double calculateTotalNetPrice() {
        return invoice.getItems().stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*item.getQuantity())
                .sum();
    }
    public Double calculateTotalTaxPrice() {
        return invoice.getItems().stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*((double) item.getProduct().getCategory().getTaxRateInPercent() /100)*item.getQuantity())
                .sum();
    }
    public Double calculateTotalGrossPrice() {
        return calculateTotalNetPrice()+calculateTotalTaxPrice();
    }


}
