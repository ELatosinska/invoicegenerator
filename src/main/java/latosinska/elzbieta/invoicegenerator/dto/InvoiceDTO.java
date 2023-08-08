package latosinska.elzbieta.invoicegenerator.dto;

import java.util.Collection;

public record InvoiceDTO(Long invoiceId, Long vendorId, Long vendeeId, Collection<InvoiceItemDTO> items) {
}
