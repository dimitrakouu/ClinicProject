import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Order {
	
	protected ArrayList<Drug> medicines;
	protected ArrayList<Integer> quantityOfMedicines;
	protected double totalCost;
	protected String date;
	protected int code;
	
	/* Adds a new medicine in ArrayList of existing medicines and its quantity
	 * in the equivalent position in ArrayList of quantities of the existing medicines. */
	
	public void addMedicineInTheOrder (Drug orderedMedicine,int quantity) {
		
		medicines.add(orderedMedicine);
		quantityOfMedicines.add(quantity);
		
	}
	
	/* Searches and deletes a medicine from existing medicines in Order. 
	 * Input: Drug orderedMedicine
	 * Output: - */
	
	public abstract void deleteMedicineFronTheOrder(Drug orderedMedicine);
	
	/* Returns list of medicines. 
	 * Input: -
	 * Output: ArrayList<Drug> medicines. */
	
	public ArrayList<Drug> getListOfMedicines(){
		
		return medicines;
	}
	
	/* Returns list of quantities. 
	 * Input: -
	 * Output: ArrayList<Integer> quantityOfMedicines. */
	
	public ArrayList<Integer> getQuantityOfMedicines(){
		
		return quantityOfMedicines;
	}
	
	/* Abstract function which calculates the total cost of the order. 
	 * Input: -
	 * Output: double value of order's total cost. */
	
	public abstract double getTotalCost();
	
	/* Prints every medicine included in the order. 
	 * Input: -
	 * Output: Prints every medicine included in order. */
	
	public void printListOfMedicines() {
		
		System.out.println("Size of List: " + medicines.size());
		
		for(int i=0;i<medicines.size();i++) {
			
			
			System.out.println("Name: " + medicines.get(i).getName());
			System.out.println("Id: " + medicines.get(i).getId());
			System.out.println("Availability: " + medicines.get(i).getAvailability());
			System.out.println("Price: " + medicines.get(i).getPrice());
		}
	}
	
	/* Returns boolean value whether a medicine exists or not in the order. 
	 * Input: String medicineId
	 * Output: boolean value */
	
	public boolean searchForMedicineInOrder(String medicineId) {
		
		for(int i=0;i<medicines.size();i++) {
			
			if(medicines.get(i).getId().equals(medicineId))
				return true;
		}
		
		return false;
	}
	
	/* Clears ArrayList for medicines of the order and ArrayList for quantities of these medicines. 
	 * Input: -
	 * Output: - */
	
	public void clearOrder() {
		
		ArrayList<Drug> med = getListOfMedicines();
		ArrayList<Integer> quantityOfMed = getQuantityOfMedicines();
		
		med.clear();
		quantityOfMed.clear();
	}
	
	/* Returns a string that contains the current date. 
	 * Input: -
	 * Output: String type of current date. */ 
	
	public String getDateTime() {
		
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public int getCode() {
		
		return code;
	}
	
	public void setCode(int code) {
		
		this.code = code;
		
	}
	
	public String getDate() {
		
		return date;
	}

}


