package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class Invoice {

    private static int duePeriodInDays = 14;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "due_date")
    private Date dueDate;
    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name="vendor_id", referencedColumnName = "id")
    private Company vendor;
    @ManyToOne
    @JoinColumn(name="vendee_id", referencedColumnName = "id")
    private Company vendee;
    @OneToMany(mappedBy = "invoice")
    private Collection<InvoiceItem> items;

    {
        createdDate = new Date(System.currentTimeMillis());
        dueDate =setDueDate(createdDate);
    }

    public Invoice() {
    }

    public Invoice(Company vendor, Company vendee) {
        this.vendor = vendor;
        this.vendee = vendee;
    }

    public Collection<InvoiceItem> getItems() {
        return items;
    }

    public void setDuePeriodInDays(int daysToPayInvoice) {
        if(daysToPayInvoice >= 0) duePeriodInDays = daysToPayInvoice;
        // TODO: what to do when days less than 0
    }

    private Date setDueDate(Date createdDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.add(Calendar.DATE, duePeriodInDays);
        return calendar.getTime();
    }
}
