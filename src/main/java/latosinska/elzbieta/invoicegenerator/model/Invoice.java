package latosinska.elzbieta.invoicegenerator.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class Invoice {
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
}
