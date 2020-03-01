import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;



public class DeleteFrame extends JFrame {
	
	private JPanel panel;
	private JButton confirm;
	private JLabel storageTitle, deleteTitle;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private JTable storageTable;
	private JTable deleteTable;
	private DefaultTableModel storageModel,deleteModel;
	private Order order;
	private db conn;

	
	public DeleteFrame(db connection) {
				
				/* Creating the connection with base */
		
				conn=connection;
		
				/* Creating menu bar */
				
				mb = new JMenuBar();
	
				/* Create delete button and adding listener for it */
				
				confirm = new JButton("Delete Medicine(s)");
				ButtonListenerDeleteButton deleteButton = new ButtonListenerDeleteButton();
				confirm.addActionListener(deleteButton);
				
				/* Creating and adding picture and listener for it in this frame */
				
				ImageIcon icon = new ImageIcon("hospital1.png");
				JLabel lb = new JLabel(icon);
			
				lb.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) 
				    {	 
						dispose();
						new GlobalHomeFrame(conn);   ;           
				                
				    }
					
				}	);
						
	

			    /* Creating model for the storage and the delete table */
				
				storageModel = new DefaultTableModel();
			    deleteModel = new DefaultTableModel();
			    
			    /* Adding columns in every model of the tables ( storage model and delete model) */
				
			    storageModel.addColumn("Id");
			    storageModel.addColumn("Name");
			    storageModel.addColumn("Availability");
			    
			    deleteModel.addColumn("Id");
			    deleteModel.addColumn("Name");
			    deleteModel.addColumn("Quantity");
			    
			    
			    /* Adding drugs information in the storage Model (name, id, availability). All these information are 
			     * being taken from the list that exists in class "Storage" 
			     * which list has all the drugs of the clinic */
				
			    String medicineName = null;
			    String medicineId = null;
			    int medicineAvailability = -1;
			    
			    for(int i=0;i<Storage.getMedicineList().size();i++) {
			    	
			    	medicineName = Storage.getMedicineList().get(i).getName();
			    	medicineId = Storage.getMedicineList().get(i).getId();
			    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
			    	storageModel.addRow(new Object[] {medicineId,medicineName,medicineAvailability});
			    }
				
			    /* Creating tables by using the models that have been created further up ( storage and delete table) */
			    
			    storageTable = new JTable(storageModel);
			    deleteTable = new JTable(deleteModel);
		 
			    /* Setting Alignment for the tables */
			    
			    storageTable.setAlignmentX(Component.LEFT_ALIGNMENT);
			    deleteTable.setAlignmentX(Component.RIGHT_ALIGNMENT);
			    
			    /* Creating scroll pane for every table */
			    
			    JScrollPane storageScrollPane = new JScrollPane(storageTable);
			    JScrollPane orderScrollPane = new JScrollPane(deleteTable);
				storageScrollPane.setBounds(36, 37, 407, 79);
				orderScrollPane.setBounds(36, 37, 407, 79);
			    
			    /* Creating label for both two tables (storage table and delete table) */
				
			    storageTitle = new JLabel("STORAGE");
			    deleteTitle = new JLabel("Delete");

				/* Creating menus */
			    
			    centralMenu_Menu = new JMenu("Central Menu");
				orderMenu = new JMenu("Order");
				storageMenu = new JMenu("Storage");
				statisticsMenu = new JMenu("Statistics");
				
				
				/* Creating menu items for every menu */
	
				i1 = new JMenuItem("Prescription");  
			    i2 = new JMenuItem("Supply");  
			    i3 = new JMenuItem("Add");
			    i4 = new JMenuItem("Delete"); 
			    i5 = new JMenuItem("Show Statistics");
			    iCentralMenu = new JMenuItem("Go to Central Menu");
			    
			    /* Adding ActionListener for every item of the menus */
			    
			    JTablePopupMenuListener menuListener = new JTablePopupMenuListener();
			    i1.addActionListener(menuListener);
			    i2.addActionListener(menuListener);
			    i3.addActionListener(menuListener);
			    i4.addActionListener(menuListener);
			    i5.addActionListener(menuListener);
			    iCentralMenu.addActionListener(menuListener);
				
			    /* Adding items in their menus */
			    
			    centralMenu_Menu.add(iCentralMenu);
			    orderMenu.add(i1);
			    orderMenu.add(i2);
			    storageMenu.add(i3);
			    storageMenu.add(i4);
			    statisticsMenu.add(i5);
			    
			    /* Adding all the menus in the menu bar */
			    
			    mb.add(iCentralMenu);
			    mb.add(orderMenu);
			    mb.add(storageMenu);
			    mb.add(statisticsMenu);
			    mb.add(lb);	
			    
			    /* Set Alignment to the labels of the tables */
			    
			    storageTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
			    deleteTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
			    

				/* Creating a main panel and a panel that in this will be added all menus */
			    
				panel = new JPanel();
				menuPanel = new JPanel();
			   
				/* Set Alignment to the main panel */
			    
			    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
				panel.setAlignmentX(Component.LEFT_ALIGNMENT);
						
			    
			    /* Adding menu bar in the menu panel */
			    
			    menuPanel.add(mb);
					    
			    
				/* Adding menu panel, titles of the tables, tables (scroll panes) and confirm button in the main panel */
			    
			    panel.add(menuPanel);
			    
			    panel.add(storageTitle);
			    panel.add(storageScrollPane);
				
				panel.add(deleteTitle);
			    panel.add(orderScrollPane);
			 
				panel.add(confirm);
				
				
				/* Creating a variable type of Supply */
			    
				order = new Supply(conn);
				
				/* Creating communication between the two tables
				 *  Adding ListSelectionListener  */
				
				storageTable.setCellSelectionEnabled(true);
		 	    ListSelectionModel cellSelectionModel = storageTable.getSelectionModel();
		 	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						
				
		 	   cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		 	    	
		  	      public void valueChanged(ListSelectionEvent e) {
		  	    	  
		  	    	/* !e.getValueIsAdjusting is for disable double click  */
			    
		  	    	if( !e.getValueIsAdjusting() ) {
		  	    		
		  	    	
		  	    	  String medicineName = "";
		  	    	  String medicineId= "";
		  	    	  int selectedRow = storageTable.getSelectedRow();
		  	    	  
		  	    	  if(selectedRow != -1) {
		  	    		 
		  	    		 /* Taking only the two fields of a row that is having a drug(id, name) */
		  	    		  
		  	    		 medicineId = (String) storageTable.getModel().getValueAt(selectedRow,0);
		  	        	  	
		  	    		 medicineName = (String) storageTable.getModel().getValueAt(selectedRow, 1);
		  	    		 
		  	    		 /* Creating a drug by using method searchMedicine from the class Storage */
		  	    		 
		  	    		 Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineId);
		  	    		 
		  	    		 /* Searching for a drug in the order ( order for deleting drug(s) ) */
		  	    		 
		  	    		 if(!order.searchForMedicineInOrder( clickedMedicine.getId())) {
		  	    			 
		  	    			 order.addMedicineInTheOrder(clickedMedicine, 1);				// Adding drug in the deleting order
		  	    			 
		  	    			 DefaultTableModel modelForOrderTable = (DefaultTableModel) deleteTable.getModel();		
		  	    			 
		  	    			 modelForOrderTable.addRow(new Object[]{clickedMedicine.getId(), clickedMedicine.getName(), 1});   // Adding row in the deleting table
		  	    			
		  	    			 }
		  	    		 }
		  	    	 }
		  	    	}
		  	      
		 	   } );
		 	   
		 	   
		 	  /* Add Listener in the delete table ( right click, deleting from there) */
		 	   
		 	  deleteTable.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	
		 	    	public void mousePressed (MouseEvent e) {
		 	    		
		 	    		 String medicineName = "";
		 	    		 String medicineId = "";
		 	    		 
		 	    		 /* Getting selected row */
		 	    		 
		 		    	 int selectedRowFromOrderTable = e.getY()/deleteTable.getRowHeight();
		 		    	  
		 		    	 /* Getting information from the drug that exists in the row that has been selected */
		 		    	  
		 		    	 for (int j = 0; j < 2; j++) {
		 		        	  
		 		    		 
		 		        	  	if( j==0 )
		 		        	  		medicineId = (String) deleteTable.getModel().getValueAt(selectedRowFromOrderTable,j);
		 		        	  	else if( j==1 )
		 		        	  		medicineName = (String) deleteTable.getModel().getValueAt(selectedRowFromOrderTable, j);
		 		        	  	
		 		    	  }
		 		    	 
		 		    	/* Creating a drug variable by using method searchMedicine from the class Storage */
		 		    	  
		 		    	Drug clickedMedicine = Storage.searchMedicine(medicineName,medicineId);
		 		    	
		 		    	/* Checking ifn right click was pressed */
		 	    		
		 	    		if( e.getButton() == MouseEvent.BUTTON3) {
		 	    			
		 	    			/* Print a message to double ckeck his choice */
		 	    			
		 	    			String message = "Would you like to delete this medicine from the list?\n";
		 	    	        int returnValue = JOptionPane.showConfirmDialog(null, message,"Delete",JOptionPane.YES_NO_OPTION);
		 	    	        
		 	    	        /* Checking his answer in the further up window */
		 	    	        
		 	    	        if (returnValue == JOptionPane.YES_OPTION) {
		 	    			
		 	    			order.deleteMedicineFronTheOrder(clickedMedicine);		// Deleting medicine from the delete table 
		 	    			
		 	    			String medId = "";
		 	    			
		 	    			/* Getting information of drug in the delete table when this is right clicked from there */
		 	    			
		 	    			for(int i=0;i<Storage.getMedicineList().size();i++) {
		 	    				
		 	    				medId = (String) storageTable.getModel().getValueAt(i,1);
		 	    				
		 	    				if(clickedMedicine.getId().equals(medId)) {
		 	    					
		 	    					DefaultTableModel model = (DefaultTableModel)storageTable.getModel();
		 	    					model.setValueAt(clickedMedicine.getAvailability(),i, 2);
		 	    					
		 	    					}
		 	    				}
		 	    			
		 	    			/* Right clicked row is deleted from the delete table */
		 	    			
		 	    			((DefaultTableModel) deleteTable.getModel()).removeRow(selectedRowFromOrderTable);
		 	   	    	  	
		 	    			/* Changing information of a drug in the storage table 
		 	    			 * when this drug is right clicked from the delete table */
		 	    			
		 	   	    	  	for(int i=0;i<Storage.getMedicineList().size();i++) {
		 	   	    	  		
		 	   	    	  		int medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
		 	   	    	  		storageTable.getModel().setValueAt(medicineAvailability, i, 2);
		 	   	    	  		
		 	   	    	  	}
		 	    	       }
		 	    	      }
		 	            }
		 	    	
		 	    	} 	);
		 	    

				/* Adding main panel in the content pane of the frame */
						
				this.setContentPane(panel);
				
				/* Set frame in the center of the screen */
				
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (screenSize.width - this.getWidth()) / 3;
		        int y = (screenSize.height - this.getHeight()) / 3;
		        this.setLocation(x, y);
						
						
				/* Adding trappings of the frame */
						
				this.setSize(600,400);
				this.setTitle("/Supply Chain/Pharmacist/Storage/Delete");
				this.setVisible(true);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			}
	
			/* menu listener */
			
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
			
			/* Button listener (delete button) */
			
			
			class ButtonListenerDeleteButton implements ActionListener {
				
				public void actionPerformed(ActionEvent e) {
					
					/* Deleting drug from the list of the Storage class and inform base for this deleting */
					
					for (int i = 0 ; i<deleteTable.getRowCount(); i++) {
						String name = (String) deleteTable.getValueAt(i, 1);
						String id = (String) deleteTable.getValueAt(i, 0);
						Storage.removeMedicine(name, id);
						conn.removeDrug(name);
						
					
					}
					
					
					
					
					
					dispose();
					new DeleteFrame(conn);
					
				}
			}	
			
			
		}
