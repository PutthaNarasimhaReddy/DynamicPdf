package com.spring.DynamicPdf;

import org.junit.jupiter.api.Test;

import com.spring.DynamicPdf.model.InvoiceItem;
import com.spring.DynamicPdf.model.InvoiceRequest;
import com.spring.DynamicPdf.service.InvoiceService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Arrays;

public class InvoiceServiceTest {
	@Test
	void testGenerateInvoice() throws Exception {
		InvoiceService service = new InvoiceService();
		InvoiceRequest request = new InvoiceRequest("XYZ Pvt. Ltd.", "29AABBCCDD121ZD", "New Delhi, India",
				"Vedant Computers", "29AABBCCDD131ZD", "New Delhi, India",
				Arrays.asList(new InvoiceItem("Product 1", "12 Nos", 123.00, 1476.00)));

		String filePath = service.generateInvoicePdf(request);
		assertNotNull(filePath);
		File file = new File(filePath);
		assertTrue(file.exists());
	}
}
