package com.createInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class ConvertAllPdfToTextFile {
	private PrintWriter writer;
	
	public ConvertAllPdfToTextFile() throws FileNotFoundException {
		this.writer =  new PrintWriter("PDFText.txt");
	}

	public void readFolder(File folder) throws IOException {
		if (!folder.isDirectory()) {
			return;
		}
		for (String fileName : folder.list()) {
			File file = new File(folder, fileName);
			if (file.isDirectory()) {
				this.readFolder(file);
			} else {
				this.readFile(file);
			}
		}
	}

	private void readFile(File file) {
		PDDocument pdf;
		try {
			pdf = PDDocument.load(file);

			PDFTextStripper stripper = new PDFTextStripper();
			String plainText = stripper.getText(pdf);
			pdf.close();
			plainText = plainText.replaceAll("[,.\n]", "");
			String fileName = file.getName().replaceAll("[,.\n]", "-");
			writer.write(fileName+","+plainText+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public void finish(){
		this.writer.flush();
		this.writer.close();
	}
	
	public static void main(String[] args) throws IOException {
		String input = "INPUT FOLDER WITH ALL PDF";
		ConvertAllPdfToTextFile converter = new ConvertAllPdfToTextFile();
		converter.readFolder(new File(input));
		converter.finish();
	}
}
