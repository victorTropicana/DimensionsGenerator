package applianceMeasurements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HtmlParser {

	private File dimensionsFile;
	private HashMap<String, Appliance> map;
	private ArrayList<Appliance> appliances;
	private Document document;

	
	//initialize hashmap for appliances and list to add matches
	public HtmlParser(File f, String dimensions) throws IOException {
		setupDimensionsFile(dimensions);
		map = new MyFile(dimensionsFile).getMap();
		appliances = new ArrayList<>();
		document = Jsoup.parse(f, "UTF-8");
		
		
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
		
		//get 2nd table of page
		Elements tables = document.select("table");
		Element table = tables.get(1);
		
		//get all rows from page
		Elements rows = table.select("tr");
		
		Appliance appliance;
		
		//iterate through each row
		for(Element row: rows) {
			
			Elements columns = row.select("td");
			
			if(columns.size() == 10) {
				
				//get sku and quantity
				String sku = columns.get(1).text();
				String quantity = columns.get(5).text();
				//System.out.println("col 1 : " + column2.text());
				//System.out.println("col 6: " + column6.text());
				
				
				//if we have the appliance in list add to list
				if(map.containsKey(sku)) {
					appliance = map.get(sku);
					appliance.setQuantity(Integer.valueOf(quantity));
					appliances.add(appliance);
				}
				
			}
			
		}

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
				htmlContents.append("<td>" + app.getQuantity() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getName() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getSku() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getWidth() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getDepth() + "</td>").append(systemSeparator);
				htmlContents.append("<td>" + app.getLength() + "</td>").append(systemSeparator);
				htmlContents.append("<td> <img src=" + imageFolder + imageName + ".png alt= \"no\" ></td>" );
				htmlContents.append("<td> <input type = \"checkBox\" > </td>").append(systemSeparator);
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
