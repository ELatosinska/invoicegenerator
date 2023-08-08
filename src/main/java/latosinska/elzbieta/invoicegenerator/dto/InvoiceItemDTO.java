package latosinska.elzbieta.invoicegenerator.dto;

import latosinska.elzbieta.invoicegenerator.model.Product;

public record InvoiceItemDTO(Long productId, int quantity) {}
