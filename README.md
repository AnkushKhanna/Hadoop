PDF HADOOP READER

PDFREADER:
This small example reads a txt file which is in format
    "Name of File", "Content of file"
matches it with a word (regex)
and gives the output
    "Name of File", "Count (Regex match in content)"
Thus giving the idea of maximum word match in which File (generally pdf).
    
INSPIRATION:
Reading numerous papers during your thesis is a regular job (as was for me). But remembering which part is in which paper was a task on its own. All I remembered was keywords which was my source of backtrack to the paper.
Doing that was a cumbersome job going through many papers. Thus I made a hadoop cascading application which reads all papers (in input format specified above) and matches with a word provided. Thus outputting the max match of the word, giving a good approximation or a subset of papers which should be referred for backtracking.

CREATING INPUT:
ConvertAllPdfToTextFile.java in com.createInput, creates the input file "Don't forget to change the input file path".

HADOOP CASCADING:
PDFReadFlow.java in com.pdfReader.flow, creates the flow for reading the input, converting it using each into
    "Name", "Word (Regex match)"
Which is then aggregated on "Name" and returns
    "Name", "Count"

INPUT FILE: 
	PDFText.txt
OUTPUT FILE: 
	result/part-xxxxx
