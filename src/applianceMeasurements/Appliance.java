package applianceMeasurements;

public class Appliance {
	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private String width, depth, length;
	private String sku,name;
	private String bundle;
	private String notes;
	
	public Appliance(String sku,String bundle, String name, String width2, String depth2, String height, String notes) {
		this.width = width2;
		this.depth = depth2;
		this.length = height;
		this.sku = sku;
		this.name = name;
		this.bundle = bundle;
		this.notes = notes;
	}

	public Appliance() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	
	public String getBundle() {
		return bundle;
	}
	
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public String toString() {
		return sku + " " + name + " " + width + " " + depth + " " + length + " " + notes;
	}
	
	
	
}
