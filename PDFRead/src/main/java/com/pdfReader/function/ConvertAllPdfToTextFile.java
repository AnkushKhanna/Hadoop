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
}
