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

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
