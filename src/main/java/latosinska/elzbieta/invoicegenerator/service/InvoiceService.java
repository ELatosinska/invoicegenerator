package latosinska.elzbieta.invoicegenerator.service;

import latosinska.elzbieta.invoicegenerator.model.Invoice;
import latosinska.elzbieta.invoicegenerator.model.InvoiceItem;

public class InvoiceService {
    private Invoice invoice;

    public InvoiceService(Invoice invoice) {
        this.invoice = invoice;
    }

    public Double calculateTotalNetPrice() {
        return PriceService.roundPrice(invoice.getItems().stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*item.getQuantity())
                .sum());
    }
    public Double calculateTotalTaxPrice() {
        return PriceService.roundPrice(invoice.getItems().stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*((double) item.getProduct().getCategory().getTaxRateInPercent() /100)*item.getQuantity())
                .sum());
    }
    public Double calculateTotalGrossPrice() {
        return PriceService.roundPrice(calculateTotalNetPrice()+calculateTotalTaxPrice());
    }

    public void addItem(InvoiceItem invoiceItem) {
        if(isProductOnTheInvoice(invoiceItem)) {
            addQuantityOfExistingProduct(invoiceItem);
        } else {
            invoice.getItems().add(invoiceItem);
        }
    }

    private void addQuantityOfExistingProduct(InvoiceItem invoiceItem) {
        invoice.getItems().stream()
                .filter(item -> item.getProduct().equals(invoiceItem.getProduct()))
                .forEach(item -> item.setQuantity(item.getQuantity()+ invoiceItem.getQuantity()));
    }

    private boolean isProductOnTheInvoice(InvoiceItem invoiceItem) {
        return invoice.getItems().stream().map(InvoiceItem::getProduct).anyMatch(product -> product.equals(invoiceItem.getProduct()));
    }

}
