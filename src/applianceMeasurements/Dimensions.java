package applianceMeasurements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Dimensions {
	
	public static void main(String [] args) throws IOException {
		//get current project directory
		String currentDirectory = System.getProperty("user.dir");
		String dimensionsFile = currentDirectory+"/Resources/Cutouts.csv";
		System.out.println("Make sure file is located in " + currentDirectory);
		String fileName;
		if(args.length > 0) {
			fileName = args[0];
		} else {
			Scanner kb = new Scanner(System.in);
			System.out.println("Enter Name of File");
			fileName = kb.nextLine();
			kb.close();
		}
	 
		if(fileName != null) {
			System.out.println("Enter a filename");
		} 
		
		
		File f = new File(currentDirectory + "/" + fileName);
		
		//search if file exists
		System.out.println("Searching for: " + f);
		if(!f.exists()) {
			System.out.println("File was not found");
			
		} else {
			fileName = fileName.substring(0, fileName.indexOf('.'));//name for outputFile
			fillForm(f,dimensionsFile,fileName);
		}
		
	} 
	
	private static void fillForm(File f,String dimensionsFile, String outputName) throws IOException {
		HtmlParser parser = new HtmlParser(f,dimensionsFile);
		parser.parse();
		parser.createFile(outputName+"Dimensions.html");
		
		
	}

}
