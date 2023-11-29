package applianceMeasurements;

public class Appliance {
	private double width, depth, length;
	private String sku,name;
	
	public Appliance(String sku, String name, double width2, double depth2, double height) {
		this.width = width2;
		this.depth = depth2;
		this.length = height;
		this.sku = sku;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String toString() {
		return sku + " " + name + " " + width + " " + depth + " " + length;
	}
	
	
	
}
