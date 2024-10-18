package com.spring.DynamicPdf.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.DynamicPdf.model.InvoiceItem;
import com.spring.DynamicPdf.model.InvoiceRequest;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class InvoiceService {
	private static final String PDF_STORAGE_PATH = "C:\\uploads\\";

	public String generateInvoicePdf(InvoiceRequest request) throws Exception {
		String fileName = request.getSeller() + "_invoice.pdf";
		File file = new File(PDF_STORAGE_PATH + fileName);

		if (file.exists()) {
			return file.getAbsolutePath();
		}

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();

		document.add(new Paragraph("Seller: " + request.getSeller()));
		document.add(new Paragraph("Seller GSTIN: " + request.getSellerGstin()));
		document.add(new Paragraph("Buyer: " + request.getBuyer()));
		document.add(new Paragraph("Buyer GSTIN: " + request.getBuyerGstin()));

		document.add(new Paragraph(" "));

		PdfPTable table = new PdfPTable(4);
		table.addCell("Item");
		table.addCell("Quantity");
		table.addCell("Rate");
		table.addCell("Amount");

		List<InvoiceItem> items = request.getItems();
		for (InvoiceItem item : items) {
			table.addCell(item.getName());
			table.addCell(item.getQuantity());
			table.addCell(String.valueOf(item.getRate()));
			table.addCell(String.valueOf(item.getAmount()));
		}

		document.add(table);
		document.close();

		return file.getAbsolutePath();
	}

	public File getInvoicePdf(String fileName) {
		File file = new File(PDF_STORAGE_PATH + fileName);
		if (file.exists()) {
			return file;
		}
		return null;
	}
}
