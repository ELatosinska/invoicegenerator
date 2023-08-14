package latosinska.elzbieta.invoicegenerator.generator;

import latosinska.elzbieta.invoicegenerator.model.Invoice;

import java.nio.file.Path;

public abstract class InvoiceGenerator {
    Path path;
    Invoice invoice;
    abstract void generate();
}
