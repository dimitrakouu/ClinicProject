import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TimePeriodStatisticsFrame extends JFrame{
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;

	private JButton supplyStatisticsButton;
	private JButton prescriptionStatisticsButton;
	private JButton goBackButton;
	
	private JPanel buttonsPanel;
	private JPanel textFieldsPanel;
	
	private JTextField firstDateTextField;
	private JTextField secondDateTextField;

	JFrame frame = this;
	private db conn;

	public TimePeriodStatisticsFrame(db connection) {
		
		/* Creating the connection with base */
		
		conn= connection;
			
		/* Creating text fields with titles where the user will write the two dates.
		 * The two dates make the period that user is interesting to get information about orders ( Supply orders or Prescription orders ). */
		
		firstDateTextField = new JTextField();
		firstDateTextField.setBorder(new TitledBorder("First Date (ex 25-04-1999)"));
		firstDateTextField.setPreferredSize(new Dimension(200,54));
		
		secondDateTextField = new JTextField();
		secondDateTextField.setBorder(new TitledBorder("Second Date (ex 25-04-1999)"));
		secondDateTextField.setPreferredSize(new Dimension(200,54));
		
		
		/* Creating buttons.
		 * 1. supplyStatisticsButton --> Makes the supply statistics for the period that user wants.
		 * 2. prescriptionStatisticsButton --> Makes the prescription statistics for the period that user wants.
		 * 3. goBackButton --> Returns to the previous page. */
		
		supplyStatisticsButton = new JButton("Supply Time Period Statistics");
		prescriptionStatisticsButton = new JButton("Prescription Time Period Statistics");
		goBackButton = new JButton("Return to the previous page");
		
		/* Creating listeners for the buttons that has been created above */
		
		/* First button Listener */
		
		goBackButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
							
				new StatisticsFrame(conn);
				dispose();
			}
			
		});
		
		supplyStatisticsButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				/* Taking from the text fields what user has entered */
				
				String firstDateText = firstDateTextField.getText();
				String secondDateText = secondDateTextField.getText();
				
				/* Checking if the dates that user entered are valid */
				
				if( isValidDate(firstDateText) && isValidDate(secondDateText) ) {
					
					
					ArrayList<Integer> id = new ArrayList<Integer>();
					ArrayList<Integer> quantities = new ArrayList<Integer>();
					ArrayList<String> medicineNames = new ArrayList<String>();
					
					/* Taking information from the base about the orders that have been done in the specific period that user entered */
					
					conn.getInfoFromOrderDataBaseForSpecificDates(false,firstDateText , secondDateText, id, quantities);
					
					/* Taking the names of the drugs that have been in the orders.
					 * Talking about the orders that have been done in the specific period that user entered. */
					
					for(int j=0;j<id.size();j++) {
						for(int i=0;i<Storage.getMedicineList().size();i++) {
							if(Storage.getMedicineList().get(i).getId().equals(String.valueOf(id.get(j))))
								medicineNames.add(Storage.getMedicineList().get(i).getName());
							}
						}
					
					/* Creating bar chart by using the above information */
					
					DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
				    
				    for (int i=0; i<quantities.size(); i++) {
				    
				    	dataset.addValue(quantities.get(i), medicineNames.get(i), "");
				    }
				    
				    JFreeChart chart=ChartFactory.createBarChart(  
				            "Time Period Supply Quantity Chart", //Chart Title  
				            "Drug", // Category axis  
				            "Bought quantity", // Value axis  
				            dataset,  
				            PlotOrientation.VERTICAL,  
				            true,true,false  
				           ); 
				    
				    /* Adding the bar chart in the chart panel */
				    
				    ChartPanel BarPanel=new ChartPanel(chart);  
				    
				    /* Creating a frame for the bar chart */
				    
				    JFrame example = new JFrame("Bar Chart");
				    
				    /* Adding the chart panel in the content pane of the frame that has been created above */
				    
				    example.setContentPane(BarPanel);
				    
				    /* Adding trappings of the frame that has been created above */
				    
				    example.setSize(800, 400);  
				    example.setLocationRelativeTo(null);  
				    example.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    example.setVisible(true);
				    }
				}
			
		});
		
		
		// Second button Listener
		
		prescriptionStatisticsButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				/* Taking from the text fields what user has entered */
				
				String firstDateText = firstDateTextField.getText();
				String secondDateText = secondDateTextField.getText();
				
				/* Checking if the dates that user entered are valid */
		
				if( isValidDate(firstDateText) && isValidDate(secondDateText) ) {
					
					ArrayList<Integer> id = new ArrayList<Integer>();
					ArrayList<Integer> quantities = new ArrayList<Integer>();
					ArrayList<String> medicineNames = new ArrayList<String>();
					
					/* Taking information from the base about the orders that have been done in the specific period that user entered */
					
					conn.getInfoFromOrderDataBaseForSpecificDates(true,firstDateText , secondDateText, id, quantities);
					
					/* Taking the names of the drugs that have been in the orders.
					 * Talking about the orders that have been done in the specific period that user entered. */
					
					for(int j=0;j<id.size();j++) {
						for(int i=0;i<Storage.getMedicineList().size();i++) {
							if(Storage.getMedicineList().get(i).getId().equals(String.valueOf(id.get(j))))
								medicineNames.add(Storage.getMedicineList().get(i).getName());
							}
						}
					
					/* Creating bar chart by using the above information */
					
					DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
				    
				    for (int i=0; i<quantities.size(); i++) {
				    	dataset.addValue(quantities.get(i), medicineNames.get(i), "");
				    }
				    
				    JFreeChart chart=ChartFactory.createBarChart(  
				            "Time Period Prescription Quantity Chart", //Chart Title  
				            "Drug", // Category axis  
				            "Bought quantity", // Value axis  
				            dataset,  
				            PlotOrientation.VERTICAL,  
				            true,true,false  
				           ); 
				    
				    /* Adding the bar chart in the chart panel */
				    
				    ChartPanel BarPanel=new ChartPanel(chart);  
				    
				    /* Creating a frame for the bar chart */
				    
				    JFrame example = new JFrame("Bar Chart");
				    
				    /* Adding the chart panel in the content pane of the frame that has been created above */
				    
				    example.setContentPane(BarPanel);
				    
				    /* Adding trappings of the frame that has been created above */
				    
				    example.setSize(800, 400);  
				    example.setLocationRelativeTo(null);  
				    example.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				    example.setVisible(true);
				    }
				}
			
		});
		
		
		/* Creating menu bar for the panel */
		
		mb = new JMenuBar();
		
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
	    
	    /* Creating panels */
	    
	    menuPanel = new JPanel();
	    panel = new JPanel();
	    buttonsPanel = new JPanel();
	    textFieldsPanel = new JPanel();
	    
	    /* Adding menu bar in a panel that has been created only for menus.
	     * Setting alignment for the menuPanel. */
	    
	    menuPanel.add(mb);
	    menuPanel.setAlignmentX(Component.TOP_ALIGNMENT);
	    
	    /* Adding buttons in a panel that has been created only for buttons.
	     * Setting alignment for the buttonsPanel. */
	    
	    buttonsPanel.add(supplyStatisticsButton);
	    buttonsPanel.add(prescriptionStatisticsButton);
	    
	    buttonsPanel.setAlignmentX(Component.BOTTOM_ALIGNMENT);
	    
	    /* Adding text fields in a panel that has been created only for text fields.
	     * Setting alignment for the textFieldsPanel. */
	    
	    textFieldsPanel.add(firstDateTextField);
	    textFieldsPanel.add(secondDateTextField);
	    
	    textFieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    /* Adding all the panels in the main panel of the frame. */
	    
	    panel.add(menuPanel);
	    
	    panel.add(textFieldsPanel);
	    
	    panel.add(buttonsPanel);
	    
	    panel.add(goBackButton);
		
	    /* Adding main panel in the content pane of the frame */
		
		this.setContentPane(panel);
		
		// Set frame in the center of the screen
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		
		/* Adding trappings of the frame */
		
		this.setLocation(x, y);
		this.setVisible(true);;
		this.setSize(600,400);
		this.setTitle("/Supply Chain/Pharmacist/Statistics/Show Statistics/Time Period Statistics");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/* Method which returns a boolean value. This method checks if a date is valid according to a specific format.
	 * Input: String date
	 * Output: boolean */
	
	public static boolean isValidDate(String date) 
    { 
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        /* Input to be parsed should strictly follow the defined date format above. */
		
        format.setLenient(false);

        try {
        	
            format.parse(date);
            return true;
            
        } catch (ParseException e) {
        	
        	JOptionPane.showMessageDialog(null,"Wrong format of date!","Error..",JOptionPane.ERROR_MESSAGE);
        	return false;
        }
    } 
	
/* Listener that determines the communication and the metaphor among the frames of the program. */
	
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

