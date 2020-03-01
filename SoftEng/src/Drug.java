public class Drug {
	
	private String name;
	private String id;
	private double price;
	private int availability;
	private int soldUnits;

	public Drug(String name, String Id, double price, int availability) {
		this.name = name;
		this.id = Id;
		this.price = price;
		this.availability = availability;
		this.soldUnits =  0 ;
	}
	
	public Drug(String name, String Id) {
		
		this.name = name;
		this.id = Id;
	}
	
	
	public String getName() {
		
		return name;
	}

	
	public String getId() {
		
		return id;
	}
	
	
	public double getPrice() {
		
		return price;
	}
	
	
	public void setPrice(double price) {
		
		this.price = price;
	}
	
	
	public int getAvailability() {
		
		return availability;
	}


	public void setAvailability(int availability) {
		
		this.availability = availability;
	}

	public void setSoldUnits(int soldUnits) {

		this.soldUnits = soldUnits;
	}

	public int getSoldUnits() {
		
		return soldUnits;
	}
	
}
