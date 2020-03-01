
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StatisticsFrame extends JFrame{
	
	private JPanel panel;
	private JMenuBar mb;
	private JMenu orderMenu;
	private JMenu storageMenu;
	private JMenu statisticsMenu;
	private JMenu centralMenu_Menu;
	private JPanel menuPanel;
	private JMenuItem i1,i2,i3,i4,i5,iCentralMenu;

	private JButton dailyStatisticsButton;
	private JButton generalStatisticsButton;
	private JButton timePeriodStatisticsButton;
	
	private JPanel buttonsPanel;
	
	JFrame frame = this;
	private db conn;

	public StatisticsFrame(db connection) {
		
		/* Creating the connection with base */
		
		conn= connection;
		
		/* Creating buttons.
		 * 1. dailyStatisticsButton --> Makes the daily prescription statistics.
		 * 2. generalStatisticsButton --> Makes the general prescription statistics.
		 * 3. timePeriodStatisticsButton --> Open a new frame where are exist more choices for the user. ( For time period statistics ) */
		
		dailyStatisticsButton = new JButton("Daily Statistics");
		generalStatisticsButton = new JButton("General Statistics");
		timePeriodStatisticsButton = new JButton("Time Period Statistics");
		
		/* Creating listeners for the buttons that has been created above */
		
		/* First button Listener */
		
		timePeriodStatisticsButton.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent arg0) {

				new TimePeriodStatisticsFrame(conn);
				dispose();
				
			}
			
		});
		
		/* Second button Listener */
		
		generalStatisticsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/* Creating bar chart by using the "soldUnits" information of every drug in the storage of the clinic */
				
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
			    
			    for (int i=0; i<Storage.getMedicineList().size(); i++) {
			    	
			    	dataset.addValue(Storage.getMedicineList().get(i).getSoldUnits(), Storage.getMedicineList().get(i).getName(), "");
			    }
			    
			    JFreeChart chart=ChartFactory.createBarChart(  
			            "Bought Quantity Chart", //Chart Title  
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
		});
		
		/* Third button Listener */
		
		dailyStatisticsButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				/* Getting daily orders information from a file */
				
				PrescriptionOrdersTemporalBase.readFromOrderFile();
				
				/* Creating bar chart by using the above information */
				
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();   
			    
			    for (int i=0; i<PrescriptionOrdersTemporalBase.getListOfMedicinesFromAllTheDailyOrders().size(); i++) {
			    	
			    	dataset.addValue(PrescriptionOrdersTemporalBase.getListOfQuantityOfMedicinesFromAllTheDailyOrders().get(i).intValue(), PrescriptionOrdersTemporalBase.getListOfMedicinesFromAllTheDailyOrders().get(i).getName(), "");
			    }
			    
			    JFreeChart chart=ChartFactory.createBarChart(  
			            "Daily Bought Quantity Chart", //Chart Title  
			            "Drug", // Category axis  
			            "Bought quantity", // Value axis  
			            dataset,  
			            PlotOrientation.VERTICAL,  
			            true,true,false  
			           ); 
			    
			    /* Adding the bar chart in the chart panel */
			    
			    ChartPanel BarPanel=new ChartPanel(chart);  
			    
			    /* Creating a frame for the bar chart */
			    
			    JFrame example = new JFrame("Daily Bar Chart");
			    
			    /* Adding the chart panel in the content pane of the frame that has been created above */
			    
			    example.setContentPane(BarPanel);
			    
			    /* Adding trappings of the frame that has been created above */
			    
			    example.setSize(800, 400);  
			    example.setLocationRelativeTo(null);  
			    example.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    example.setVisible(true); 
				
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
	    
	    /* Adding menu bar in a panel that has been created only for menus.
	     * Setting alignment for the menuPanel. */
	    
	    menuPanel.add(mb);
	    menuPanel.setAlignmentX(Component.TOP_ALIGNMENT);
	    
	    /* Adding buttons in a panel that has been created only for buttons.
	     * Setting alignment for the buttonsPanel. */
	    
	    buttonsPanel.add(dailyStatisticsButton);
	    buttonsPanel.add(timePeriodStatisticsButton);
	    buttonsPanel.add(generalStatisticsButton);
	    
	    buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    /* Adding all the panels in the main panel of the frame. */
	    
	    panel.add(menuPanel);
	    
	    panel.add(buttonsPanel);

	    /* Adding main panel in the content pane of the frame */
	    
		this.setContentPane(panel);
		
		// Set frame in the center of the screen 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		/* Adding trappings of the frame */
		
		this.setVisible(true);;
		this.setSize(600,400);
		this.setTitle("/Supply Chain/Pharmacist/Statistics/Show Statistics");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

