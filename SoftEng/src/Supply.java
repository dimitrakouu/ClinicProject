import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Supply extends Order {

	/* Creates a new object type of "Supply". In constructor the two 
	 * list of the hyperclass "Order" take value "null".  */
	
	public Supply(db connection) {
			
		medicines = new ArrayList<Drug>();
		quantityOfMedicines = new ArrayList<Integer>();
		totalCost = 0;
		date = super.getDateTime();
		code = connection.getNextOrderCode(false) + 1;
		
	}
	
		
	/* Adds a new medicine in ArrayList of existing medicines and its quantity
	 * in the equivalent position in ArrayList of quantities of the existing medicines. 
	 * At the same time its availability is increased. 
	 * Input: Drug orderedMedicine, int quantity
	 * Output: - */
		
	public void addMedicineInTheOrder (Drug orderedMedicine, int quantity) {
			
		super.addMedicineInTheOrder(orderedMedicine, quantity);
		orderedMedicine.setAvailability(orderedMedicine.getAvailability() + quantity);
		
	}
	
	/* Searches and deletes a medicine from existing medicines in Order. 
	 * Input: Drug orderedMedicine
	 * Output: - */
	
	public void deleteMedicineFronTheOrder(Drug orderedMedicine) {
		
		Iterator<Drug> iterator = medicines.iterator();
		int i = 0;
		
		while( iterator.hasNext() ) {
		
		    Drug medicine = iterator.next();
		    
		    if(medicine.getId().equals(orderedMedicine.getId())) {
		        iterator.remove();
		        orderedMedicine.setAvailability(orderedMedicine.getAvailability() - quantityOfMedicines.get(i));
		        quantityOfMedicines.remove(i);
		        i++;
		    }
		}
	}
		
	/* Calculates and returns total cost of Supply.
	 * Acceptance that every medicine supply costs 1/3 of its market price. 
	 * Input: -
	 * Output: double value of order's total cost. */
	
	public double getTotalCost() {

		totalCost = 0;
		
		for(int i = 0;i<medicines.size();i++) 
			totalCost += (medicines.get(i).getPrice() * quantityOfMedicines.get(i)) / 3;
        
		return totalCost;
	}
	
}
	

