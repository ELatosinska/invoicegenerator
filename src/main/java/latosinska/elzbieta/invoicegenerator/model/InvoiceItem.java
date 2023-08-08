package latosinska.elzbieta.invoicegenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.exception.LessThanOneQuantityException;
import lombok.*;


@Entity
@Table(name = "invoice_items")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class InvoiceItem {
    @Id
    @GeneratedValue()
    Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    private Integer quantity;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    public InvoiceItem(Product product, Integer quantity, Invoice invoice) throws LessThanOneQuantityException {
        if (quantity < 1) throw new LessThanOneQuantityException();
        this.product = product;
        this.quantity = quantity;
        this.invoice = invoice;
    }

    public void setQuantity(Integer quantity) throws LessThanOneQuantityException {
        if (quantity < 1) throw new LessThanOneQuantityException();
        this.quantity = quantity;
    }

}

