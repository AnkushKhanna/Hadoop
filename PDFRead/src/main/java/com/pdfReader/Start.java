package com.pdfReader;

import com.pdfReader.flow.PDFReadFlow;

import cascading.flow.Flow;

public class Start {
	public static void main(String[] args) {
		String input = "PDFText.txt";
		String output = "result/";
		String regex = "(^|)WSN";
		Flow pdfRead = PDFReadFlow.getPDFReadFlow(input, output, regex);
		pdfRead.complete();
	}
}
