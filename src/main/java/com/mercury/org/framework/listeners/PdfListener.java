package com.mercury.org.framework.listeners;

import java.io.IOException;
import java.util.HashMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.lowagie.text.Anchor;
import com.lowagie.text.Annotation;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.mercury.org.framework.constants.FrameWorkConstants;
import com.mercury.org.framework.driver.Driver;
import com.mercury.org.framework.mail.Gmail;
import com.mercury.org.framework.utility.PdfUtility;

public class PdfListener implements ITestListener {
	private Gmail gmail ;
	private PdfUtility pdfUtility ;
	private Document document = null;
	PdfPTable successTable = null, failTable = null;
	private HashMap<Integer, Throwable> throwableMap = null;
	private HashMap<Integer, String> anchorReference = null;
	private int nbExceptions = 0;
	/**
	 * Initialise PDF
	 */
	public PdfListener() {
		this.document = new Document();
		this.throwableMap = new HashMap<Integer, Throwable>();
		this.anchorReference = new HashMap<Integer, String>();
	}
	/**
	 * Create Pdf
	 */
	public void onStart(ITestContext context) {
		this.gmail = new Gmail();
		this.pdfUtility = new PdfUtility();
		this.document = pdfUtility.initialisePdf(context.getName());
	}
	/**
	 * Success Table
	 */
	public void onTestSuccess(ITestResult result) {
		if (successTable == null) this.successTable = pdfUtility.successTable();
		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.successTable.addCell(cell);
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			Integer key = new Integer(throwable.hashCode());
			String timeStamp = new String(System.currentTimeMillis() + "");
			this.throwableMap.put(key, throwable);
			this.anchorReference.put(key, timeStamp);
			this.nbExceptions++;
			Chunk anchorException = new Chunk(throwable.getStackTrace()[1] + "");
			Anchor exceptionLink = new Anchor(anchorException);
			exceptionLink.setReference("#" + timeStamp);
			Paragraph excep = new Paragraph(exceptionLink);
			cell = new PdfPCell(excep);
			this.successTable.addCell(cell);
		} else	this.successTable.addCell(new PdfPCell(new Paragraph("")));
	}
	/**
	 * Failure Table
	 */
	public void onTestFailure(ITestResult result) {
		String relativePathFile = "PdfScreenshot/screenshot" + System.currentTimeMillis() + ".png";
		String file = FrameWorkConstants.PDF_SCREEN_SHOT_FOLDER + relativePathFile;
		try {
			Driver.takeSnapShot(Driver.driver, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.failTable == null) this.failTable = pdfUtility.failTable();
		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis() - result.getStartMillis())));
		this.failTable.addCell(cell);
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			Integer key = new Integer(throwable.hashCode());
			String timeStamp = new String(System.currentTimeMillis() + "");
			this.throwableMap.put(key, throwable);
			this.anchorReference.put(key, timeStamp);
			this.nbExceptions++;
			Chunk anchorException = new Chunk(new Chunk(throwable.toString().split(":", -1)[0],
					new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.UNDERLINE))
					.setLocalGoto("" + throwable.hashCode()));
			Anchor exceptionLink = new Anchor(anchorException);
			exceptionLink.setReference("#" + timeStamp);
			Paragraph excep = new Paragraph(exceptionLink);
			cell = new PdfPCell(excep);
			this.failTable.addCell(cell);
		} else this.failTable.addCell(new PdfPCell(new Paragraph("")));
		Image image = null;
		try {
			image = Image.getInstance(file);
			Annotation a = new Annotation(0, 0, 0, 0, relativePathFile);
			image.setAnnotation(a);
		} catch (BadElementException e) {}
		 catch (IOException e) {}
		cell = new PdfPCell(image, true);
		this.failTable.addCell(cell);
	}

	public void onTestSkipped(ITestResult result) {
	}
	/**
	 * Summarize all Exception
	 */
	public void onFinish(ITestContext context) {
		try { if (this.failTable != null) {
				this.failTable.setSpacingBefore(15f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(15f);
			}
			if (this.successTable != null) {
				this.successTable.setSpacingBefore(15f);
				this.document.add(this.successTable);
				this.successTable.setSpacingBefore(15f);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		this.document = pdfUtility.exceptionSummaryAndClose(this.nbExceptions, this.document, this.throwableMap, this.anchorReference);
		gmail.sendGMailWithAttachement("Mercury Tours Report", "PFA of the Mercury Tours Pdf Report",
				FrameWorkConstants.PDF_FILE_PATH);
	}
	@Override
	public void onTestStart(ITestResult result) {
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}
}