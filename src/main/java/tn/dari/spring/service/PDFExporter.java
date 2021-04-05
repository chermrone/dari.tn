package tn.dari.spring.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import tn.dari.spring.entity.SubscriptionOrdred;
import tn.dari.spring.repository.SubscriptionOrderRepository;

public class PDFExporter {
	@Autowired
	SubscriptionOrderRepository sor;

	private Set<SubscriptionOrdred> orders;

	public PDFExporter(Set<SubscriptionOrdred> orders2) {
		super();
		this.orders = orders2;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(4);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		// user name + subscription id + subscription price + paying date
		cell.setPhrase(new Phrase("User name", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("Subscription id", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Paying date", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {

		for (SubscriptionOrdred o : orders) {
			table.addCell(o.getUs().getUserName());
			table.addCell(String.valueOf(o.getSubscription().getSubscriptionId()));
			table.addCell(String.valueOf(o.getSubscription().getPrice()));
			table.addCell(o.getPayingDate().toString());

		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Facture", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 4f, 1.5f, 1.5f, 3.0f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		writeTableData(table);

		document.add(table);

		document.close();

	}

}
