import java.util.ArrayList;

public class Storage {
	
	private static ArrayList<Drug> medicineList = new ArrayList<Drug>();
	
	
	/* Update of storage based on database. */
	
	public static void updateStorage(db connection) {
		connection.getAllDrugs(medicineList);
	}
	
	/* Adds a medicine in Storage.  
	 * Input: String name, String id, double price, int availability
	 * Output: - */
	
	public static void addMedicine(String name, String id, double price, int availability) {
		Drug med = new Drug(name, id, price, availability);
		medicineList.add(med);
	}
	
	/*  Removes a medicine from Storage. 
	 * Input: String name, String id
	 * Output: - */
	
	public static void removeMedicine(String name, String id) {
		
		if (Storage.searchMedicine(name, id) != null)
			medicineList.remove(Storage.searchMedicine(name, id));
		else 
			System.out.println("This medicine does not exist in storage");
	}
	
	/* Searches Storage whether a medicine exists or not.
	 * If the medicine does exist, it returns it, else return null. 
	 * Input: String name, String id
	 * Output: Drug if found, null if not. */
	
	public static Drug searchMedicine(String name, String id) {
		
		for (int i=0; i<medicineList.size(); i++) {
			if (medicineList.get(i).getName().equals(name) && (medicineList.get(i).getId().equals(id))) {
				return medicineList.get(i);
			}
		}
		
		return null;
		
	}

	public static ArrayList<Drug> getMedicineList(){
		
		return medicineList;
	}
}
