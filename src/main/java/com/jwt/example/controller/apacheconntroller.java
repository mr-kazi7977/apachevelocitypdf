package com.jwt.example.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jwt.example.model.certificate;
import com.jwt.example.repo.cerRepo;

@RestController
public class apacheconntroller {
	
	@Autowired
	cerRepo repo;
	

	@GetMapping("/genpdf/{fileName}")
	HttpEntity<byte[]> createPdf(
            @PathVariable("fileName") String fileName) throws IOException {

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		certificate cer = new certificate();

//	   cer =repo.findById(1);
		/* next, get the Template */
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		ve.init();
		
		
//		Template t = ve.getTemplate("hello");
		
		Template t = ve.getTemplate("templates/helloworld.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("name", "World");
		context.put("genDateTime", LocalDateTime.now().toString());
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
//		t.merge(context, writer);
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		baos = generatePdf(writer.toString());

		HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.APPLICATION_PDF);
	    header.set(HttpHeaders.CONTENT_DISPOSITION,
	                   "attachment; filename=" + fileName.replace(" ", "_"));
	    header.setContentLength(baos.toByteArray().length);

	    return new HttpEntity<byte[]>(baos.toByteArray(), header);

	}

	public ByteArrayOutputStream generatePdf(String html) {

		String pdfFilePath = "";
		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();
		try {

			document = new Document();
			// document header attributes
			document.addAuthor("Kinns");
			document.addAuthor("Kinns123");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("kinns123.github.io");
			document.addTitle("HTML to PDF using itext");
			document.setPageSize(PageSize.LETTER);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);

			// open document
			document.open();

			XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
			xmlWorkerHelper.getDefaultCssResolver(true);
			xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(
					html));
			// close the document
			document.close();
			System.out.println("PDF generated successfully");

			return baos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@GetMapping("/msg")
	public String printMesssage(){
		return "this is the message";
	}


}
