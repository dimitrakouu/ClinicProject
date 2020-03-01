import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SupplyChainMainFrame extends JFrame{
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JTable storageTable;
	private JLabel title;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;
	private DefaultTableModel model;
	private db conn;
	public static int count;
	
   
	public SupplyChainMainFrame(db connection) {
		
		/* Creating the connection with base */
		
		conn = connection;
		
		/* Informing the program with information from base */
		
		if(count == 0) {
			Storage.updateStorage(conn);
			count = 1;
		}
	 
		/* Creating menu bar */
		
		mb = new JMenuBar();
		
		/* Checking if file ("History For Prescription.txt") last modified date is different from real date. 
		 * If it is different, the "History For Prescriptio.txt" file goes empty. */
		
		
		File file = new File("History For Prescription.txt");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if(!sdf.format(file.lastModified()).equals(PrescriptionOrdersTemporalBase.getDate())){
			
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}

		}
		
		/* Creating menus that will be added in the main menu ( menu bar ) */
		
		orderMenu = new JMenu("Order");
		storageMenu = new JMenu("Storage");
		statisticsMenu = new JMenu("Statistics");
		centralMenu_Menu = new JMenu("Central Menu");
		
		/* Creating menu items for every menu */
		
		i1 = new JMenuItem("Prescription");  
	    i2 = new JMenuItem("Supply");  
	    i3 = new JMenuItem("Add");  
	    i4 = new JMenuItem("Delete"); 
	    i5 = new JMenuItem("Show Statistics");
	    iCentralMenu = new JMenuItem("Go to Central Menu");
	    
	    /* Adding ActionListener for every item of the menus */
	    
	    JTablePopupMenuListener listener = new JTablePopupMenuListener();
	    i1.addActionListener(listener);
	    i2.addActionListener(listener);
	    i3.addActionListener(listener);
	    i4.addActionListener(listener);
	    i5.addActionListener(listener);
	    iCentralMenu.addActionListener(listener);
	    
	    /* Creating and adding picture and listener for it in this frame */
		
	 		ImageIcon icon = new ImageIcon("hospital1.png");
	 		JLabel lb = new JLabel(icon);
	 	
	 		lb.addMouseListener(new MouseAdapter() {
	 			public void mouseClicked(MouseEvent e) 
	 		    {	 
	 				dispose();
	 				new GlobalHomeFrame(conn);        
	 		    }
	 			
	 		}	);
	    
	 	/* Adding items in their menus */
	    
	    centralMenu_Menu.add(iCentralMenu);
	    
	    orderMenu.add(i1);
	    orderMenu.add(i2);
	    
	    storageMenu.add(i3);
	    storageMenu.add(i4);
	    
	    statisticsMenu.add(i5);
	    
	    
	    /* Adding all the menus in the menu bar */
	    
	    mb.add(centralMenu_Menu);
	    mb.add(orderMenu);
	    mb.add(storageMenu);
	    mb.add(statisticsMenu);
	    mb.add(lb);
	    
	    /* Adding drugs that exist in the storage of the clinic, to the storage table */
	    
	    model = new DefaultTableModel();
	    model.addColumn("Id");
	    model.addColumn("Name");
	    model.addColumn("Availability");
	    String medicineName = null;
	    String medicineId = null;
	    int medicineAvailability = -1;
	    
	    for(int i=0;i<Storage.getMedicineList().size();i++) {
	    	
	    	/* Getting information for every drug that exist in the storage of the clinic ( Name, Id, Availability ) */
	    	
	    	medicineName = Storage.getMedicineList().get(i).getName();
	    	medicineId = Storage.getMedicineList().get(i).getId();
	    	medicineAvailability = Storage.getMedicineList().get(i).getAvailability();
	    	
	    	/* Adding new row to the storage model */
	    	
	    	model.addRow(new Object[] {medicineId,medicineName,medicineAvailability});
	    }				 
	    
	    /* Creating table by using the model from further up ( Storage table ) */
	      
	    storageTable = new JTable(model);
	    
	    /* Creating search bar for the storage table. By writing the name of a drug in the
	     * search bar the user could find if a drug exists in the storage table. */
	    
	    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(storageTable.getModel());
	    storageTable.setRowSorter(sorter);
	    
	    JLabel label = new JLabel("Search...");
	    JTextField filterText = new JTextField();
	    
	    /* Creating listener for the search bar */
	    
	    filterText.getDocument().addDocumentListener(new DocumentListener(){

            public void insertUpdate(DocumentEvent e) {
            	
                String text = filterText.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
	   
            public void removeUpdate(DocumentEvent e) {
            	
                String text = filterText.getText();

                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            public void changedUpdate(DocumentEvent e) {
            	
                throw new UnsupportedOperationException("Not supported yet.");  //To change body of generated methods, choose Tools | Templates.
            }

        });

	    /* Setting alignment for storage table */
	    
	    storageTable.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    /* Creating scrollPane for storage table */
	    
	    JScrollPane scrollPane = new JScrollPane(storageTable);
		scrollPane.setBounds(36, 37, 407, 79);
	    
		/* Creating label for storage table */
		
	    title = new JLabel("STORAGE");
	    
	    /* Setting alignment for storage title */
	    
	    title.setAlignmentX(Component.LEFT_ALIGNMENT);
	    
	    
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
	    panel.add(title);
	    panel.add(scrollPane);
	
	    panel.add(label, BorderLayout.WEST);
	    panel.add(filterText, BorderLayout.CENTER);
	 
	    /* Adding the main panel in the content pane. */
	
		this.setContentPane(panel);
		
		/* Set frame in the center of the screen */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		/* Adding trappings of the frame */
		
		this.setSize(600,400);
		this.setTitle("/Supply Chain/Pharmacist/Central Menu");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
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
			new PrescriptionAndSupplyFrame(true, conn);
			
		}
		
		else if(e.getActionCommand().equals("Supply")){
			
			dispose();
			new PrescriptionAndSupplyFrame(false, conn);
			
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


