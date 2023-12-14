package applianceMeasurements;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MyFile {
	
	private HashMap<String,Appliance> hash;
	
     public MyFile(File f) throws FileNotFoundException {
    	 hash = new HashMap<>();
    	 fillHashmap(f);
     }
     
     public HashMap<String,Appliance> getMap(){
    	 return hash;
     }
     
     private void fillHashmap(File f) throws FileNotFoundException {
    	 Scanner openFile = new Scanner(f);
    	 String sku1, sku2 = "";
    	 String name = "";
    	 String width, depth, height = "";
    	 Appliance tempAppliance;
    	 String curr;
    	 String[] currLine;
    	 openFile.nextLine();
    	 
    	 while(openFile.hasNextLine()) {
    		 curr = openFile.nextLine();
    		 currLine = curr.split(",");
    		 if(currLine.length > 0) {
		    	 sku1 = currLine[0];
		    	 sku2= currLine[1];
		    	 name = currLine[2];
		    	 width = currLine[3];
		    	 depth = currLine[4];
		    	 height= currLine[5];
		    	
		    	 if(!sku1.isBlank()) {
		    		 tempAppliance = new Appliance(sku1,name,width,depth,height);
		    		 hash.put(sku1+"", tempAppliance);
		    	 }
		    	 if(!sku2.isBlank()) {
		    		 hash.put(sku2, new Appliance(sku2,name,width,depth,height));
		    	 }
	    	 }
    	 }
    	 openFile.close();
     }
}
