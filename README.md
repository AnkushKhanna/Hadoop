PDF HADOOP READER

PDFREADER:
This small example reads a txt file which has all the names of the pdf's
    "Name of File"


Convert it into:
	"Name of File", "Content of file"


Matches it with a word (regex)
and gives the output:-

    "Name of File", "Count (Regex match in content)".

Thus giving the idea of maximum word match in which File (generally pdf).
    
INSPIRATION:
Reading numerous papers during your thesis is a regular job (as was for me). But remembering which part is in which paper was a task on its own. All I remembered was keywords which was my source of backtrack to the paper.
Doing that was a cumbersome job going through many papers. Thus I made a hadoop cascading application which reads all papers (in input format specified above, which I referred and act as a source of example for this application) and matches with a word (regex) provided. Thus outputting the count of the word for each paper. Giving a good approximation to a subset of papers which should be referred for backtracking.


NOTES:
Change the path of the pdfbox.jar in marven. In pom.xml to your system path of pdfbox.jar


HADOOP CASCADING:
PDFReadFlow.java in com.pdfReader.flow, creates the flow for reading the input, converting it using each into:-

    "Name", "Word (Regex match)"

Which is then aggregated on "Name" and returns:-

    "Name", "Count"

INPUT FILE: 
	pdfFileName.txt

PDF FILES:
	pdf/
	The pdf files currently are just examples. A replacement would be required.

OUTPUT FILE: 
	result/part-xxxxx
