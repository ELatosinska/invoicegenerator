package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {
    @ManyToOne
    private @Id Product product;
    private Integer quantity;
    @ManyToOne()
    @JoinColumn(name="invoice_id", referencedColumnName = "id")
    private @Id Invoice invoice;

    public InvoiceItem() {}

    public InvoiceItem(Product product, Integer quantity, Invoice invoice) {
        if(quantity<1) throw new IllegalArgumentException("Quantity cannot be less than 1");
        this.product = product;
        this.quantity = quantity;
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
