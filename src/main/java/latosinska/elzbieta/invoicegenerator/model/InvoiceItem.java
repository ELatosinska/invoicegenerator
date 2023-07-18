package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "invoice_items")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceItem {
    @ManyToOne
    private @Id Product product;
    private Integer quantity;
    @ManyToOne()
    @JoinColumn(name="invoice_id", referencedColumnName = "id")
    private @Id Invoice invoice;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        return Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, invoice);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", invoice=" + invoice +
                '}';
    }
}

