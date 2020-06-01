package com.mercury.org.framework.utility;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mercury.org.framework.constants.FrameWorkConstants;

public class PdfUtility {

	public PdfUtility() {}
	/**
	 * Creates Label for Success Table 
	 * @return
	 */
	public PdfPTable successTable(){
		PdfPTable successTable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f });
		Paragraph p = new Paragraph("PASSED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
		p.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(4);
		cell.setBackgroundColor(Color.GREEN);
		successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Class"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Method"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Time (ms)"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Exception"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		successTable.addCell(cell);
		return successTable;
	}
	/**
	 * Creates Label for Failure Table
	 * @return
	 */
	public PdfPTable failTable(){
		PdfPTable failTable = new PdfPTable(new float[] { .3f, .3f, .1f, .3f, .3f });
		failTable.setTotalWidth(20f);
		Paragraph p = new Paragraph("FAILED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
		p.setAlignment(Element.ALIGN_CENTER);
		PdfPCell cell = new PdfPCell(p);
		cell.setColspan(5);
		cell.setBackgroundColor(Color.RED);
		failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Class"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Method"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Time (ms)"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("Exception"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("ScreenShot"));
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		failTable.addCell(cell);
		return failTable;
	}
	/**
	 * Initialize Pdf File
	 * @param contextName
	 * @return
	 */
	public Document initialisePdf(String contextName){
		Document document = new Document();
		try {
			PdfWriter.getInstance(document,new FileOutputStream(FrameWorkConstants.PDF_FILE_PATH + FrameWorkConstants.PDF_FILE_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		}
		document.open();
		Paragraph p = new Paragraph(contextName + " TESTNG RESULTS",FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));
		try {
			document.add(p);
			document.add(new Paragraph(new Date().toString()));
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return document;
	}
	/**
	 * Adds all the exception 
	 * @param nbExceptions
	 * @param document
	 * @param throwableMap
	 * @param anchorReference
	 * @return
	 */
	public Document exceptionSummaryAndClose(int nbExceptions,Document document,HashMap<Integer, Throwable> throwableMap,HashMap<Integer, String> anchorReference){
		if(nbExceptions > 0){
			Paragraph p = new Paragraph("EXCEPTIONS SUMMARY",
					FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
			try {
				document.add(p);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			Set<Integer> keys = throwableMap.keySet();
			assert keys.size() == nbExceptions;
			for (Integer key : keys) {
				Throwable throwable = throwableMap.get(key);
				Chunk chunk = new Chunk(throwable.toString(),
						FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(255, 0, 0)));
				chunk.setLocalDestination("" + key);
				Anchor anchorTarget = new Anchor(chunk);
				anchorTarget.setName(anchorReference.get(key));
				Paragraph throwTitlePara = new Paragraph(anchorTarget);
				try {
					document.add(throwTitlePara);
				} catch (DocumentException e3) {
					e3.printStackTrace();
				}
				StackTraceElement[] elems = throwable.getStackTrace();
				String exception = "";
				for (StackTraceElement ste : elems) {
					Paragraph throwParagraph = new Paragraph(ste.toString());
					try {
						document.add(throwParagraph);
					} catch (DocumentException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		document.close();
		return document;
	}
}