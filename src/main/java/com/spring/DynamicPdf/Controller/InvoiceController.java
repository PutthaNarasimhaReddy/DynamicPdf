package com.spring.DynamicPdf.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.itextpdf.html2pdf.HtmlConverter;
import com.spring.DynamicPdf.model.InvoiceRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/invoice")

public class InvoiceController {
	@Autowired
	private SpringTemplateEngine templateEngine;

	@PostMapping
	public ResponseEntity<String> generateInvoice(@RequestBody InvoiceRequest invoiceRequest) throws Exception {
		// Create PDF using the provided invoice data
		String filePath = generatePdf(invoiceRequest);

		return ResponseEntity.ok("Invoice generated: " + filePath);
	}

	@GetMapping("/download/{fileName}")
	public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable String fileName) throws IOException {
		// Retrieve the file from the stored location
		Path path = Paths.get("C:\\uploads\\" + fileName);
		InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.APPLICATION_PDF).body(resource);
	}

	private String generatePdf(InvoiceRequest invoiceRequest) throws Exception {
		Context context = new Context();
		context.setVariable("invoice", invoiceRequest);

		String htmlContent = templateEngine.process("invoice", context);

		String fileName = invoiceRequest.getSeller() + "_invoice.pdf";
		String filePath = "C:\\uploads\\" + fileName;
		File file = new File(filePath);
		file.getParentFile().mkdirs();

		OutputStream outputStream = new FileOutputStream(file);
		HtmlConverter.convertToPdf(htmlContent, outputStream);

		return filePath;
	}
}
