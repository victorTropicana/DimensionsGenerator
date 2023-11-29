package applianceMeasurements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HtmlParser {

	private File f;
	private File dimensionsFile;
	private HashMap<String, Appliance> map;
	private ArrayList<Appliance> appliances;

	
	//initialize hashmap for appliances and list to add matches
	public HtmlParser(File f, String dimensions) throws FileNotFoundException {
		this.f = f;
		setupDimensionsFile(dimensions);
		map = new MyFile(dimensionsFile).getMap();
		appliances = new ArrayList<>();
	}
	
	

	//check if dimensions file exists
	private void setupDimensionsFile(String dimensions) {
		File f = new File(dimensions);
		if (!f.exists()) {
			System.out.println("Error when loading measuremenFilets file file was not found" + dimensions);
			System.exit(1);
		}
		System.out.println("dimensions file found " + f.getName());
		dimensionsFile = new File(dimensions);
	}

	/**
	 * 
	 * 
	 * @throws FileNotFoundException
	 */
	public void parse() throws FileNotFoundException {
		
		//read file until we find keyword "EXTENDED" then stop
		Scanner openFile = new Scanner(f);
		String line = "";
		boolean found = false;
		CharSequence cs = "EXTENDED";
		while (openFile.hasNext() && !found) {
			line = openFile.nextLine();
			if (line.contains(cs)) {
				openFile.nextLine();
				found = true;
			}
		}
		

		//read each line and eliminate < > create array delimited by a blank space
		//check if the current index contains a matching sku in the map
		//if so it adds the appliance object to the appliances arraylist
		String currString = "";
		String tempArray[];
		while (openFile.hasNextLine()) {

			while (openFile.hasNext()) {
				currString = openFile.next();
				currString = currString.replace("<", " ");
				currString = currString.replace(">", " ");
				tempArray = currString.split(" ");
				for (int i = 0; i < tempArray.length; ++i) {
					if (map.containsKey(tempArray[i])) {
						System.out.println("adding appliance " + map.get(tempArray[i]));
						appliances.add(map.get(tempArray[i]));
					}
				}

			}
		}
		openFile.close();

	}

	/** write file */
	public void createFile(String outputName) {
		String currDir = System.getProperty("user.dir");
		String templateFile = currDir + "/Resources/template.html";
		String imageFolder = currDir + "/Resources/images/";
		String systemSeparator = System.lineSeparator(); //check systems line delimiter
		String targetName = "CUSTOMER_NAME";
		String targetLine = "Start";//template file keyword to insert

		try {
			BufferedReader reader = new BufferedReader(new FileReader(templateFile));
			StringBuilder htmlContents = new StringBuilder();

			//search for file's target for customer name
			String currLine = reader.readLine();
			while (currLine != null && !currLine.contains(targetName)) {
				htmlContents.append(currLine).append(systemSeparator);
				currLine = reader.readLine();
			}
			
			//replace keyword in html with customer's name
			currLine = currLine.replace(targetName, outputName.toUpperCase() + " APPLIANCES");
			htmlContents.append(currLine).append(systemSeparator);
			
			
			//read file until we find the targetLine
			currLine = reader.readLine();
			while (currLine != null && !currLine.contains(targetLine)) {
				htmlContents.append(currLine).append(systemSeparator);
				currLine = reader.readLine();

			}

			//check if targetLine was not found
			if (currLine == null) {
				System.err.print("error target line was not found " + targetLine);
				System.exit(1);
			}
			htmlContents.append(currLine).append(systemSeparator);
			
			String imageName = "";

			
			//write contents to html file
			for (Appliance app : appliances) {
				System.out.println("writing: " + app);
				imageName = app.getSku();
				imageName = imageName.replace(".", "");
				imageName = imageName.replace("\\", "");
				imageName = imageName.replace("/", "");
				htmlContents.append("<tr>").append(systemSeparator);
				htmlContents.append("<td>" + app.getName() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getSku() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getWidth() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getDepth() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getLength() + "</td>").append(systemSeparator);
				htmlContents.append("<td> <img src=" + imageFolder + imageName + ".png alt= \"no\" ></td>" );
				htmlContents.append("</tr>").append(systemSeparator);
			}

			
			//write rest of file back. 
			while (currLine != null) {
				htmlContents.append(currLine).append(systemSeparator);
				currLine = reader.readLine();
			}
			reader.close();
			
			//write file to system. Replaces any existing file

			BufferedWriter writer = new BufferedWriter(new FileWriter(currDir + "/" + outputName));
			writer.write(htmlContents.toString());
			writer.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
