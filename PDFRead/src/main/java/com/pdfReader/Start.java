package com.pdfReader;

import com.pdfReader.flow.PDFReadFlow;

import cascading.flow.Flow;

public class Start {
	public static void main(String[] args) {
		String input = "pdfFileName.txt";
		String folder ="pdf";
		String output = "result/";
		String regex = "(^|)and";
		Flow pdfRead = PDFReadFlow.getPDFReadFlow(input, output,folder, regex);
		pdfRead.complete();
	}
}
