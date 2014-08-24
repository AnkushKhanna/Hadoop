package com.pdfReader.function;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import cascading.flow.AssemblyPlanner.Context;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

public class ConvertAllPdfToTextFile extends BaseOperation<Context> implements
		Function<Context> {

	private String folder;

	public ConvertAllPdfToTextFile(Fields fieldDeclaration, String folder) {
		super(1, fieldDeclaration);
		this.folder = folder;
	}

	public void operate(FlowProcess flowProcess,
			FunctionCall<Context> functionCall) {
		TupleEntry arguments = functionCall.getArguments();

		String fileName = arguments.getString("fileName");
		File file = new File(folder, fileName);
		PDDocument pdf;
		System.out.println(file.getName());
		try {
			pdf = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			String plainText = stripper.getText(pdf);
			pdf.close();
			plainText = plainText.replaceAll("[,.\n]", "");
			Tuple result = new Tuple();
			result.add(fileName);
			result.add(plainText);
			functionCall.getOutputCollector().add(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//
//	private PrintWriter writer;
//
//	public ConvertAllPdfToTextFile() throws FileNotFoundException {
//		this.writer = new PrintWriter("PDFText.txt");
//	}
//
//	public void readFolder(File folder) throws IOException {
//		if (!folder.isDirectory()) {
//			return;
//		}
//		for (String fileName : folder.list()) {
//			File file = new File(folder, fileName);
//			if (file.isDirectory()) {
//				this.readFolder(file);
//			} else {
//				this.readFile(file);
//			}
//		}
//	}
//
//	private void readFile(File file) {
//		PDDocument pdf;
//		try {
//			pdf = PDDocument.load(file);
//
//			PDFTextStripper stripper = new PDFTextStripper();
//			String plainText = stripper.getText(pdf);
//			pdf.close();
//			plainText = plainText.replaceAll("[,.\n]", "");
//			String fileName = file.getName().replaceAll("[,.\n]", "-");
//			writer.write(fileName + "," + plainText + "\n");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//		}
//	}
//
//	public void finish() {
//		this.writer.flush();
//		this.writer.close();
//	}
//
//	public static void main(String[] args) throws IOException {
//		String input = "INPUT FOLDER WITH ALL PDF";
//		ConvertAllPdfToTextFile converter = new ConvertAllPdfToTextFile();
//		converter.readFolder(new File(input));
//		converter.finish();
//	}
}
