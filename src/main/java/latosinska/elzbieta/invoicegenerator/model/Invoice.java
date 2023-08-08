package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.service.PriceService;
import lombok.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Invoice {

    private static int timeToPayForInvoiceInDays = 14;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "due_date")
    private Date dueDate;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Company vendor;
    @ManyToOne
    @JoinColumn(name = "vendee_id", referencedColumnName = "id")
    private Company vendee;
    @OneToMany(mappedBy = "invoice")
    private Collection<InvoiceItem> items;

    {
        createdDate = new Date(System.currentTimeMillis());
        dueDate = calculateDueDate(createdDate);
    }


    public Invoice(Company vendor, Company vendee) {
        this.vendor = vendor;
        this.vendee = vendee;
    }

    public void setTimeToPayForInvoiceInDays(int daysToPayInvoice) {
        if (daysToPayInvoice < 0)
            throw new IllegalArgumentException("You cannot set time to pay less tan 0 days");
        timeToPayForInvoiceInDays = daysToPayInvoice;
    }


    private Date calculateDueDate(Date createdDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.add(Calendar.DATE, timeToPayForInvoiceInDays);
        return calendar.getTime();
    }

    public void addItemToInvoice(Product product, int quantity) {
        if (isProductOnTheInvoice(product)) {
            addQuantityOfExistingProduct(product, quantity);
        } else {
            items.add(new InvoiceItem(product, quantity, this));
        }
    }

    public Double calculateTotalNetPrice(Invoice invoice) {
        return PriceService.roundPrice(items.stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*item.getQuantity())
                .sum());
    }
    public Double calculateTotalTaxPrice(Invoice invoice) {
        return PriceService.roundPrice(items.stream()
                .mapToDouble(item -> item.getProduct().getNetPrice()*((double) item.getProduct().getCategory().getTaxRateInPercent() /100)*item.getQuantity())
                .sum());
    }
    public Double calculateTotalGrossPrice(Invoice invoice) {
        return PriceService.roundPrice(calculateTotalNetPrice(invoice)+calculateTotalTaxPrice(invoice));
    }

    private void addQuantityOfExistingProduct(Product product, int quantity) {
        items.stream()
                .filter(item -> item.getProduct().equals(product))
                .forEach(item -> item.setQuantity(item.getQuantity() + quantity));
    }

    private boolean isProductOnTheInvoice(Product product) {
        return items.stream().map(InvoiceItem::getProduct).anyMatch(invoiceProduct -> invoiceProduct.equals(product));
    }

}
