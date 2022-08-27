package tugasakhir.pemesanan.exporter;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import tugasakhir.pemesanan.model.Sale;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PdfSaleExporter {
    private List<Sale> listSale;

    public PdfSaleExporter(List<Sale> listSales) {
        this.listSale = listSales;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.PINK);
        cell.setPadding(2);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Sale ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total Pay", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Customer", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Sale sale : listSale) {
            table.addCell(String.valueOf(sale.getId_sale()));
            table.addCell(String.valueOf(sale.getTransactionId()));
            table.addCell(sale.getTransactionDate());
            table.addCell(String.valueOf(sale.getTransaction().getOrdering().getTotalCost()));
            table.addCell(String.valueOf(sale.getTransaction().getOrderId()));
            table.addCell(sale.getTransaction().getUser().getName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(15);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("List of Sales", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.0f, 3.0f, 3.0f, 1.5f, 3.2f });
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
