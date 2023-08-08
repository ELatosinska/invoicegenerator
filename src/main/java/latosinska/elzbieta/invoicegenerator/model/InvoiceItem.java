package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "invoice_items")
@Getter
@Setter
@NoArgsConstructor @EqualsAndHashCode
@ToString
public class InvoiceItem {
    @Id @GeneratedValue Long id;
    @ManyToOne
    private Product product;
    private Integer quantity;
    @ManyToOne()
    @JoinColumn(name="invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    public InvoiceItem(Product product, Integer quantity, Invoice invoice) {
        if(quantity<1) throw new IllegalArgumentException("Quantity cannot be less than 1");
        this.product = product;
        this.quantity = quantity;
        this.invoice = invoice;
    }

    public void setQuantity(Integer quantity) {
        if(quantity<1) throw new IllegalArgumentException("Quantity cannot be less than 1");
        this.quantity = quantity;
    }

}

