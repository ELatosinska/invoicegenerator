package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "invoices")
public class Invoice {

    private static int timeToPayForInvoiceInDays = 14;
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
        dueDate = calculateDueDate(createdDate);
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

    public static int getTimeToPayForInvoiceInDays() {
        return timeToPayForInvoiceInDays;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Long getId() {
        return id;
    }

    public Company getVendor() {
        return vendor;
    }

    public void setVendor(Company vendor) {
        this.vendor = vendor;
    }

    public Company getVendee() {
        return vendee;
    }

    public void setVendee(Company vendee) {
        this.vendee = vendee;
    }

    public void setTimeToPayForInvoiceInDays(int daysToPayInvoice) {
        if(daysToPayInvoice < 0)
            throw new IllegalArgumentException("You cannot set time to pay less tan 0 days");
        timeToPayForInvoiceInDays = daysToPayInvoice;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "createdDate=" + createdDate +
                ", dueDate=" + dueDate +
                ", id=" + id +
                ", vendor=" + vendor +
                ", vendee=" + vendee +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(createdDate, invoice.createdDate) && Objects.equals(dueDate, invoice.dueDate) && Objects.equals(id, invoice.id) && Objects.equals(vendor, invoice.vendor) && Objects.equals(vendee, invoice.vendee) && Objects.equals(items, invoice.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdDate, dueDate, id, vendor, vendee, items);
    }

    private Date calculateDueDate(Date createdDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.add(Calendar.DATE, timeToPayForInvoiceInDays);
        return calendar.getTime();
    }
}
