package CMPS280;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Reader {

	// ArrayList of String, used to get the list of Strings from a file, each word
	private ArrayList<String> textToRead = new ArrayList<String>();

	static String filePath;
	
	private String filesName = "employee.txt";

	private File file = new File(filesName);

	Reader() {
		
		
	}// end of default constructor

	Reader(String newFilesName, String filePathN) {
		filePath = filePathN;
		filesName = newFilesName;
		file = new File(filePathN + filesName);
	}// end of constructor
	
	Reader(String newFilesName) {
		filesName = newFilesName;
		file = new File(filePath + filesName);
	}// end of constructor

	Reader(File newFile) {
		file = newFile;
	}// end of constructor

	void readFile() throws FileNotFoundException {

		Scanner input = new Scanner(file);

		while (input.hasNext()) {

			textToRead.add(input.next());

		}

		input.close();

	}//end of readFile

	String getDataInfo(int index) {
		return textToRead.get(index);

	}//end of getDataInfo

	int getSize() {
		return textToRead.size();
	}//end of getSize

}
