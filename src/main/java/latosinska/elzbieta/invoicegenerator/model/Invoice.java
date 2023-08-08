package latosinska.elzbieta.invoicegenerator.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import latosinska.elzbieta.invoicegenerator.service.PriceService;
import lombok.*;

import java.util.ArrayList;
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
    @ManyToOne @JsonBackReference
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Company vendor;
    @ManyToOne @JsonBackReference
    @JoinColumn(name = "vendee_id", referencedColumnName = "id")
    private Company vendee;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private Collection<InvoiceItem> items;

    {
        createdDate = new Date(System.currentTimeMillis());
        dueDate = calculateDueDate(createdDate);
        items = new ArrayList<>();
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
}
