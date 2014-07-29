package com.pdfReader.flow;

import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.hadoop.HadoopFlowConnector;
import cascading.operation.Function;
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexGenerator;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.property.AppProps;
import cascading.scheme.Scheme;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;

public class PDFReadFlow {
	/*
	 * Name: getPDFReadFlow
	 * Arguments: input=Input Path of the source, output= Output Path of sink, regex=Regex expression to match
	 * Return: Flow
	 * */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Flow getPDFReadFlow(String input, String output, String regex){
		//Input Tap
		Fields sourceField = new Fields("Name","Text");
		Scheme sourceScheme = new TextDelimited(sourceField,",");
		Tap source = new Hfs(sourceScheme, input);
		
		//Output Tap
		Fields sinkField = new Fields("Name","Count");
		Scheme sinkScheme = new TextDelimited(sinkField);
		Tap sink = new Hfs(sinkScheme, output ,SinkMode.REPLACE);
		
		//Pipe
		Pipe assembly = new Pipe("Word Match");
		//Match Into words
		
		Function function = new RegexGenerator(new Fields("Words"), regex);
		assembly = new Each(assembly, new Fields("Text"), function, new Fields("Name","Words"));
		
		//GroupBy words
		assembly = new GroupBy(assembly, new Fields("Name"));
		//Count
		assembly = new Every(assembly, new Count(new Fields("Count")));
		//Flow
		Properties properties = new Properties();
		AppProps.setApplicationJarClass(properties,
				PDFReadFlow.class);

		FlowConnector flowConnector = new HadoopFlowConnector(properties);
		// FlowConnector flowConnector = new HadoopFlowConnector();

		Flow flow = flowConnector.connect("uitlityMatrix", source, sink,
				assembly);
		return flow;
	}
}
