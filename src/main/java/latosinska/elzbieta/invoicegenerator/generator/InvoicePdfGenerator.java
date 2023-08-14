package latosinska.elzbieta.invoicegenerator.generator;

import latosinska.elzbieta.invoicegenerator.model.Invoice;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class InvoicePdfGenerator extends InvoiceGenerator {
    Path path = Path.of("C:\\Users\\876013\\Desktop\\Invoices");
    Invoice invoice;

    public InvoicePdfGenerator(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public void generate() {
        String fileTitle = "invoice" + invoice.getId() + ".pdf";
        try (
                PDDocument invoicePdf = new PDDocument();
                OutputStream output = new BufferedOutputStream(new FileOutputStream(path + "\\" + fileTitle))
                ) {
            PDDocumentInformation documentInformation = invoicePdf.getDocumentInformation();
            documentInformation.setAuthor(invoice.getVendor().getName());
            PDPage page = new PDPage();
            invoicePdf.addPage(page);
            invoicePdf.save(output);
        } catch (Exception ex) {
            System.out.println(fileTitle);
            System.out.println(ex.getMessage());
        }
    }
}
