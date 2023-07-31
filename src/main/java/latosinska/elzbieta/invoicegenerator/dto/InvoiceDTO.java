package latosinska.elzbieta.invoicegenerator.dto;



import latosinska.elzbieta.invoicegenerator.model.InvoiceItem;

import java.util.Collection;

public record InvoiceDTO(Long vendorId, Long vendeeId, Collection<InvoiceItem> items) {
}
