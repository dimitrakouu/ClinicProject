import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PrescriptionAndSupplyFrame extends JFrame {
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JTable storageTable,orderTable;
	private JLabel storageTitle,orderTitle,totalCostTitle;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private DefaultTableModel storageModel,basketModel;
	private boolean typeOfOrder;
	private JTextField textFieldForCost;
	private Order order = null;
	private JButton confirmButton;
	private db conn;
	
   
	public PrescriptionAndSupplyFrame(boolean aTypeOfOrder, db connection) {
		
		/* Creating the connection with base */
		
		conn=connection;
		
		/* Creating confirm button */
		
		confirmButton = new JButton("Confirm Order");
		
		/* Adding listener to the confirmButton.
		 * By the activation of the choice "Confirm Order", the table of the order goes empty at 
		 * at the same time the program inform a file with the information of the drugs that were 
		 * in the order. Specifically this file has the codes, the names and the
		 * quantities of the drugs that were in the order. Furthermore this listener inform the base with the new 
		 * updates and also it writes down in the base the information of the specific order */
		
		confirmButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				
				if(typeOfOrder == true) {
					
					/* Writing information of drugs that were in the order, to a file */
					
					PrescriptionOrdersTemporalBase.writeToOrderFile(order.getListOfMedicines(), order.getQuantityOfMedicines());
					
					/* Informing base with the updates(changes) */
					
					connection.updateDrugList(Storage.getMedicineList());
					
					/* Writing down in the base the information of the order */
					
					connection.updateOrderDataBase(order, typeOfOrder);
					}
				else if(typeOfOrder == false) {
					
					/* Informing base with the updates(changes) */
					
					connection.updateDrugList(Storage.getMedicineList());
					
					/* Writing down in the base the information of the order */
					
					connection.updateOrderDataBase(order,typeOfOrder);
				}
					
				DefaultTableModel dm = (DefaultTableModel) orderTable.getModel();
				int rowCount = dm.getRowCount();
				
				/* Removing rows one by one from the end of the table */
				
				for (int i = rowCount - 1; i >= 0; i--) {
				    dm.removeRow(i);
				}
				
				/* Setting "0" in the field of the total cost */
				
				textFieldForCost.setText("0");
				
				/* Creating new order ( Prescription or Supply ) */
				
				if(typeOfOrder == true)
					order = new Prescription(conn);
				else if(typeOfOrder == false)
					order = new Supply(conn);
	
			}
		});
		
		 /* Adding picture in the program and setting listener on it.
		  * By activation of this choice ( clicking the picture ) the program goes to 
		  * the main frame of the software. */
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
	
		lb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	dispose();
		    	new GlobalHomeFrame(conn);              
		    
		    }
			
		}	);
		
		
		/* Creating textField which will have the total cost of the orders */
		
		textFieldForCost = new JTextField("0");
		
		/* Setting value to the variable typeOfOrder ( 0 for Prescription and 1 for Supply )
		 * and defining  the type of the order */
		
		typeOfOrder = aTypeOfOrder;
		
		if( typeOfOrder == true) 
	    	order = new Prescription(conn);
		
	    else if ( typeOfOrder == false) 
	    	order = new Supply(conn);
	   
		/* Creating menu bar */
		
		mb = new JMenuBar();
		
		/* Creating four menus ( Order, Storage, Statistics, Central Menu ) */
		
		orderMenu = new JMenu("Order");
		storageMenu = new JMenu("Storage");
		statisticsMenu = new JMenu("Statistics");
		centralMenu_Menu = new JMenu("Central Menu");
		
		/* Creating items-choices for every menu ( Order, Storage, Statistics, Central Menu) 
		 * Central Menu --> Go to Central Menu
		 * Order --> Prescription, Supply
		 * Storage --> Add, Delete
		 * Statistics --> Show Statistics */
		
		
		i1 = new JMenuItem("Prescription");  
	    i2 = new JMenuItem("Supply");  
	    i3 = new JMenuItem("Add");  
	    i4 = new JMenuItem("Delete"); 
	    i5 = new JMenuItem("Show Statistics");
	    iCentralMenu = new JMenuItem("Go to Central Menu");
	    
	    /* Adding listener for every choice that has been created further up.
	     * For all the choices of the menus */
	    
	    JTablePopupMenuListener listener = new JTablePopupMenuListener();
	    i1.addActionListener(listener);
	    i2.addActionListener(listener);
	    i3.addActionListener(listener);
	    i4.addActionListener(listener);
	    i5.addActionListener(listener);
	    iCentralMenu.addActionListener(listener);
	    
	    /* Adding the items-choices to their menu */
	    
	    centralMenu_Menu.add(iCentralMenu);
	    
	    orderMenu.add(i1);
	    orderMenu.add(i2);
	    
	    storageMenu.add(i3);
	    storageMenu.add(i4);
	    
	    statisticsMenu.add(i5);
	    
	    /* Adding menus to the menu bar */
	    
	    mb.add(centralMenu_Menu);
	    mb.add(orderMenu);
	    mb.add(storageMenu);
	    mb.add(statisticsMenu);
	    mb.add(lb);
	    
	    /* Creating model and columns-categories for the two talbes ( Tables: Storage, Order) */
	    
	    storageModel = new DefaultTableModel();
	    basketModel = new DefaultTableModel();
	    
	    storageModel.addColumn("Id");
	    storageModel.addColumn("Name");
	    storageModel.addColumn("Availability");
	    
	    basketModel.addColumn("Id");
	    basketModel.addColumn("Name");
	    basketModel.addColumn("Quantity");
	    
	    /* Adding drugs that exist in the storage of the clinic, to the storage table */
	    
	    String medicineName = null;
	    String medicineCode = null;
	    int medicineAvailability = -1;
	    
	    for(int i=0;i<Storage.getMedicineList().size();i++) {
	    	
	    	/* Getting information for every drug that exist in the storage of the clinic ( Name, Id, Availability ) */
	    	
	    	medicineName = Storage.getMedicineList().get(i).getName();
	    	medicineCode = Storage.getMedicineList().get(i).getId();
	    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
	    	
	    	/* Adding new row to the storage model */
	    	
	    	storageModel.addRow(new Object[] {medicineCode,medicineName,medicineAvailability});
	    }
	    
	    /* Creating tables by using the models from further up ( Storage table and Order table )*/
	    
	    storageTable = new JTable(storageModel);
	    orderTable = new JTable(basketModel);
	    
	    
	    /* Setting alignment for both tables */
	    
	    storageTable.setAlignmentX(Component.LEFT_ALIGNMENT);
	    orderTable.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    /* Creating scrollPane for both tables  */
	    
	    JScrollPane storageScrollPane = new JScrollPane(storageTable);
	    JScrollPane orderScrollPane = new JScrollPane(orderTable);
		storageScrollPane.setBounds(36, 37, 407, 79);
		orderScrollPane.setBounds(36, 37, 407, 79);
	    
	    /* Creating label for both tables ( Storage table and Order table ) and also 
	     * creating label for the text field that will have the total cost of every order*/
		
	    storageTitle = new JLabel("STORAGE");
	    orderTitle = new JLabel("Basket");
	    totalCostTitle = new JLabel("Total Cost");
	    
	    
	    /* Setting alignment for storage and order title */
	    
	    storageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
	    orderTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    
	    /* Creating two panels. One panel for the menus and one panel that will be the main panel of the frame */
	    
	    menuPanel = new JPanel();
	    panel = new JPanel();
	    
	    /* Setting the way that objects will be put in every panel ( for example vertically, horizontally ) */
	    
	    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	    panel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
	    
	    /* Adding the menu bar in the menu panel */ 
	    
	    menuPanel.add(mb);
	    
	    /* Adding the menu panel in the main panel of the frame.
	     * Furthermore this part of the code it is adding storage and order table and their labels 
	     * in the main panel of the frame. */
	   
	    panel.add(menuPanel);
	 
	    panel.add(storageTitle);
	    panel.add(storageScrollPane);
	    
	    panel.add(orderTitle);
	    panel.add(orderScrollPane);
	    
	    panel.add(totalCostTitle);
	    panel.add(textFieldForCost);
	
	    panel.add(confirmButton);
	    
	    /* Creating the communication between the two tables ( Storage table and Order table).
	     * The way that the user can choose transfer one drug from the storage table
	     * to the order table. In this part of the code it is used a ListSelectionListener. */
	    
	    storageTable.setCellSelectionEnabled(true);
 	    ListSelectionModel cellSelectionModel = storageTable.getSelectionModel();
 	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

 	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
 	    	
 	      public void valueChanged(ListSelectionEvent e) {
 	    	 
 	    	/* This check succeeds to disable double clicking. */
 	    	  
 	    	if(!e.getValueIsAdjusting() ) {
 	    		
 	    	  
 	    	  String medicineName = "";
 	    	  String medicineCode = "";
 	    	  String totalCost = "";
 	    	 
 	    	 /* Getting the row that user has selected */
 	    	  
 	    	 int selectedRow = storageTable.getSelectedRow();
 	    	 	 
 	    	 if(selectedRow != -1) {
 	    		
 	    	 /* It is saved in to variables the code and the name of the drug 
 	    	  * that has been selected from the storage table by the user */
 	    	
 	    	 medicineCode = (String) storageTable.getModel().getValueAt(selectedRow,0);
 	        
 	         medicineName = (String) storageTable.getModel().getValueAt(selectedRow, 1);
 	        	  	
 	    	  
 	    	  /* Creating an object type of Drug by using the two variables that have been created further up */
 	       
 	    	  Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);		
 	    	  
 	    	 
 	    	  /* Checking if the object that it has been created further up exists in the list of a specific order */
 		    
 	    	  if(!order.searchForMedicineInOrder( clickedMedicine.getId())) {
 	    		  
 	    		  /* Showing a message that incites the user to write a number.
 	    		   * This number will be the quantity of the drug 
 	    		   * that will be added in the list of an order */
 	    		  
 	    		  String inputAvailabilityString = JOptionPane.showInputDialog(null,"Enter quantity of the medicine you want to buy: ");
 	    		  
 	    		  /* Checking if the user didn't write an integer number */
 	    		  
 	    		  if(inputAvailabilityString == null || inputAvailabilityString.equals("") )
 	    			  JOptionPane.showMessageDialog(null,"You dind't enter any number.","Error..",JOptionPane.ERROR_MESSAGE);
 	    		  
 	    		  else if(!PrescriptionAndSupplyFrame.isNumeric(inputAvailabilityString)) 
 	    			 JOptionPane.showMessageDialog(null,"You have to enter an integer number.","Error..",JOptionPane.ERROR_MESSAGE);

 	    		  else {
 	    			  
 	    			  /* Converting the quantity that user had wrote in the frame, from String to Integer. */
 	    			  
 	    			  int inputAvailabilityInteger = Integer.parseInt(inputAvailabilityString);
 	    			  
 	    			  /* Checking if the number that user had wrote in the frame, is less than zero. */
 	    			  
 	    			  if(inputAvailabilityInteger <= 0) 
 	    				  JOptionPane.showMessageDialog(null,"Invalid quantity of medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  /* Checking if the quantity that user had entered is valid, 
 	    			   * according to the availability of the drug that has been chosen. */
 	    			  
 	    			  else if(typeOfOrder == true && !(clickedMedicine.getAvailability() >= inputAvailabilityInteger))
	    			  		JOptionPane.showMessageDialog(null,"Not enough stocks for this medicine.","Error..",JOptionPane.ERROR_MESSAGE);
 	    			  
 	    			  else  {
 	    				  
 	    				  	/* Adding clicked drug ( from the storage ) in the order list. */
 	    		  
 	    			  		order.addMedicineInTheOrder(clickedMedicine, inputAvailabilityInteger); 	
 	    			  		
 	    			  		/* Setting a specific format about the total cost of an order that will be appeared. */
 	    			  		
 	    			  		DecimalFormat df = new DecimalFormat("##.##");
	    			
 	    			  		totalCost = String.valueOf(df.format(order.getTotalCost()));
 	    			  		
 	    			  		textFieldForCost.setText(totalCost);
 		    
 	    			  		DefaultTableModel modelForOrderTable = (DefaultTableModel) orderTable.getModel();
 	    			  		
 	    			  		/* Adding the clicked drug ( from the storage ) 
 	    			  		 * in the second table ( to the order table ). */
 	    			  		
 	    			  		modelForOrderTable.addRow(new Object[]{clickedMedicine.getId(), clickedMedicine.getName(), inputAvailabilityInteger}); 
 	    			  		
 	    			  		/* Changing the availability of the drug that has been clicked */
 	    			  		
 	    			  		for(int i=0;i<Storage.getMedicineList().size();i++) {
 	    		  
 	    	  						int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
 	    	  						storageTable.getModel().setValueAt(medicineAvailability, i, 2); 
 	    	  						}
 	    			  		}
 	    			  }
 	    		  }
 	    	  }
 	    	 }
 	    	}
 	      
 	    } ); 

 	    /* Creating Listener for the deletion of a drug for the order table. */
 	    
 	    orderTable.addMouseListener(new MouseAdapter() {
 	    	
 	    	
 	    	public void mousePressed (MouseEvent e) {
 	    		
 	    		  String medicineName = "";
 		    	  String medicineCode = "";
 		    	  String totalCost = "";
 		    	  
 		    	 /* Getting selected row */

 		    	 int selectedRowFromOrderTable = e.getY()/orderTable.getRowHeight();
 		    	  
 		    	 /* Getting information about the drug that has been chosen.
 		    	  * ( Code, Name ) */
 		    	 
 		    	 for (int j = 0; j < 2; j++) {
 		        	  
 		    		 
 		        	  	if( j==0 )
 		        	  		medicineCode = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable,j);
 		        	  	else if( j==1 )
 		        	  		medicineName = (String) orderTable.getModel().getValueAt(selectedRowFromOrderTable, j);
 		        	  	
 		    	  }
 		    	  
 		    	/* Creating an object type of Drug by using the two variables from further up ( medicineCode, medicineName ). */
 		    	 
 		    	Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineCode);
 		    	
 	    		/* MouseEvent.BUTTON3 for checking if right click has been confirmed from the user. */
 		    	
 	    		if( e.getButton() == MouseEvent.BUTTON3) {
 	    			
 	    			/* Printing message for the user to confirm his choice about deleting the clicked drug
 	    			 * from the order list. */
 	    			
 	    			String message = "Are you sure you want to delete this medicine from the list?\n";
 	    	        int returnValue = JOptionPane.showConfirmDialog(null, message,"Delete",JOptionPane.YES_NO_OPTION);
 	    	        
 	    	        /* Checking if the button yes has been chosen from the user. */
 	    	        
 	    	        if (returnValue == JOptionPane.YES_OPTION) { 	
 	    		        
 	    	        	/* Deleting the drug from the order list */
 	    	        	
 	    		    	order.deleteMedicineFronTheOrder(clickedMedicine);
 	 	    			
 	 	    			String medCode = "";
 	 	    			
 	 	    			/* Updating the storage table after the deletion of the drug. More specifically, this part of the code
 	 	    			 * it is updating the availability in the storage table of the drug 
 	 	    			 * that has been chosen from the user. */
 	 	    			 
 	 	    			for(int i=0;i<Storage.getMedicineList().size();i++) {
 	 	    				
 	 	    				medCode = (String) storageTable.getModel().getValueAt(i,1);
 	 	    				
 	 	    				if(clickedMedicine.getId().equals(medCode)) {
 	 	    					
 	 	    					DefaultTableModel model = (DefaultTableModel)storageTable.getModel();
 	 	    					model.setValueAt(clickedMedicine.getAvailability(),i, 2);
 	 	    					
 	 	    					}
 	 	    				}
 	 	    			
 	 	    			/* Deleting the drug, that has been chosen by the user, from the order table. */
 	 	    			
 	 	    			((DefaultTableModel) orderTable.getModel()).removeRow(selectedRowFromOrderTable);
 	 	    			
 	 	    			/* Setting a specific format about the total cost of an order that will be appeared. */
 	 	    			
 	 	    			DecimalFormat df = new DecimalFormat("#.##");
 	 	    			
 	 	    			totalCost = String.valueOf(df.format(order.getTotalCost()));
 	 	     		    
 	 	   	    	  	textFieldForCost.setText(totalCost);
 	 	   	    	  	
 	 	   	    	  	/* Changing the availability of the drug that has been clicked */
 	 	   	    	  	
 	 	   	    	  	for(int i=0;i<Storage.getMedicineList().size();i++) {
 	 	   	    	  		
 	 	   	    	  		int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
 	 	   	    	  		storageTable.getModel().setValueAt(medicineAvailability, i, 2); 
 	 	   	    	  		
 	 	   	    	  	}
 	 	   	    	  }
 	    		    }
 	    		}	
 	    	
 	    } 	);
 	    
 	    
 	   
	    /* Adding the main panel in the content pane. */
	
		this.setContentPane(panel);
		
		/* Set frame in the center of the screen */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		/* Adding trappings of the frame */
		
		this.setSize(600,400);
		
		if( typeOfOrder == true)
			this.setTitle("/Supply Chain/Pharmacist/Order/Prescription");
		else if(typeOfOrder == false)
			this.setTitle("/Supply Chain/Pharmacist/Order/Supply");
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
		
		
		}
	
	/* Method which returns a boolean value. This method checks if a String is Integer or not.
	 * Input: String str
	 * Output: boolean */
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Integer.parseInt(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
/* Listener that determines the communication and the metaphor among the frames of the program  */
	
class JTablePopupMenuListener implements ActionListener {


		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Go to Central Menu")){
				
				dispose();
				new SupplyChainMainFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Prescription") ) {
					
				dispose();
				new PrescriptionAndSupplyFrame(true,conn);
				
			}
			
			else if(e.getActionCommand().equals("Supply")){
				
				dispose();
				new PrescriptionAndSupplyFrame(false,conn);
				
			}
			
			else if(e.getActionCommand().equals("Add")) {
				
				dispose();
				new AddFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Delete")) {
				
				dispose();
				new DeleteFrame(conn);
			}
			
			else if(e.getActionCommand().equals("Show Statistics")) {
				
				dispose();
				new StatisticsFrame(conn);
			}
			
		}
			
	 }

}

