import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrescriptionOrdersTemporalBase {
	
	
	private static ArrayList<Drug> medicines = new ArrayList<Drug>();
	private static ArrayList<Integer> quantityOfMedicines = new ArrayList<Integer>();
	private static String date = getDateTime();
	
	/* Getter for "medicines" list.
	 * Input: -
	 * Output: ArrayList<Drug> */
	
	public static ArrayList<Drug> getListOfMedicinesFromAllTheDailyOrders(){
		
		return medicines;
	}
	
	/* Getter for "quantityOfMedicines" list.
	 * Input: -
	 * Output: ArrayList<Integer> */
	
	public static ArrayList<Integer> getListOfQuantityOfMedicinesFromAllTheDailyOrders(){
		
		return quantityOfMedicines;
	}
	
	/* Method which is checking if a drug exists in a file where daily orders are saved. 
	 * Input: String name
	 * Output: Boolean value */
	
	public static boolean checkIfMedicineExistsInTheOrderFile(String name) {
		
		for(int i=0;i<medicines.size();i++) 
			if(medicines.get(i).getName().equals(name))
					return true;
		
		return false;
	}
	
	/* Method which is adding a medicine in the daily orders list. 
	 * Input: Drug medicine, int quantity
	 * Output: - */
	
	public static void addMedicineInTheListOfMedicinesFromAllTheDailyOrders(Drug medicine,int quantity) {
		
		/* Checking if input medicine exists in the daily orders file.
		 * If it doesn't exist, the method it is adding the medicine in the daily orders list. */
		
		if(!checkIfMedicineExistsInTheOrderFile(medicine.getName())) {
			medicines.add(medicine);
			quantityOfMedicines.add(quantity);
		}
		
		/* If the input medicine exists in the daily orders file, the method is trying to find the index of this medicine in the list and
		 * is changing the bought quantity of the specific medicine. */
		
		else{
			
			int index = PrescriptionOrdersTemporalBase.findAndReturnIndexOfMedicineInTheListOfMedicinesFromAllTheDailyOrders(medicine.getName());
			int newQuantityOfMedicine = quantityOfMedicines.get(index) + quantity;
			
			if(index != -1)
				quantityOfMedicines.set(index, newQuantityOfMedicine);
		
			}
		}
	
	/* Method which is writing daily orders list and the quantities of every medicine in a file.
	 * Input: ArrayList<Drug> medicinesInOrder, ArrayList<Integer> quantityOfMedicinesInOrder
	 * Output: - */
	
	public static void writeToOrderFile(ArrayList<Drug> medicinesInOrder,ArrayList<Integer> quantityOfMedicinesInOrder) {
		
		/* Informing the two list of the class with the daily orders information which are being saved in a file. */
		
		PrescriptionOrdersTemporalBase.readFromOrderFile();
		
		String fileName = "History For Prescription.txt";
		
		/* Adding medicines from the an order in the daily orders list. 
		 * If a medicine exists in the daily orders list the method is changing the bought quantity of this medicine. */
		
		for(int i=0;i<medicinesInOrder.size();i++) {
			
			PrescriptionOrdersTemporalBase.addMedicineInTheListOfMedicinesFromAllTheDailyOrders(medicinesInOrder.get(i), quantityOfMedicinesInOrder.get(i));
		}		
		
		/* Informing the file which saves the daily orders information with the updates of the daily orders list. */
		
		try
		{
		    String textToBeWritten = "";
		    
		    textToBeWritten = date + "\n\n";
		    for(int i = 0;i<medicines.size();i++) 
		    	textToBeWritten += (medicines.get(i).getId() + " " + medicines.get(i).getName() + 
		    						" " + String.valueOf(quantityOfMedicines.get(i)) + "\n");
		         
		    FileWriter fw = new FileWriter(fileName,false);  //the true will append the new data
		    fw.write(textToBeWritten);						 //appends the string to the file
		    fw.close();
		    
		}
		
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
		
	}
	
	/* Method which is returning the date ( With specific format ).
	 * Input: -
	 * Output: String */
	
	public static String getDateTime() {
		
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	/* Method which is searching a medicine in the daily orders list. If the medicine exists in the daily orders list the method
	 * is returning medicine's index. If the medicine does'nt exist the method is returning -1. 
	 * Input: String name
	 * Output: Int */
	
	public static int findAndReturnIndexOfMedicineInTheListOfMedicinesFromAllTheDailyOrders(String name) {
		
		int index = -1;
		
		/* Searching to find if the medicine exists in the daily orders list. */
		
		boolean exists = PrescriptionOrdersTemporalBase.checkIfMedicineExistsInTheOrderFile(name);
		
		/* Searching to find medicine's index in the daily orders list. */
		
		if(exists) {
			
			for(int i=0;i<medicines.size();i++)
				if(medicines.get(i).getName().equals(name))
					index = i;
		}
		
		return index;
			
		
	}
	
	/* Method which is reading from a file all the information about the daily orders. The method is writing the values
	 * from the file to the corresponding lists.
	 * Input: -
	 * Output: - */
	
	public static void readFromOrderFile() {
	
		int count = 0;
		String fileName = "History For Prescription.txt";

		
		File file = new File(fileName);
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			
			String line;
			while((line = br.readLine()) != null) {
				
				if(count > 1) {
					String temp[] = line.split(" ");
					Drug med = new Drug(temp[1],temp[0]);
					int quantity = Integer.parseInt(temp[2]);
					medicines.add(med);
					quantityOfMedicines.add(quantity);
					}
				else if(count == 0)
					date = line;
				
				
				count += 1;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/* Getter for "date" attribute.
	 * Input: -
	 * Output: String */
	
	public static String getDate(){
		
		return date;
	}

}
