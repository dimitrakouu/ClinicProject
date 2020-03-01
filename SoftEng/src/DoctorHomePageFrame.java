import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class DoctorHomePageFrame extends JFrame { //This name was <GUIGiatros2>
	
	private JPanel centralPanel, mainPanel, secondPanel;
	
	private JScrollPane scrollPane;
	private JTable table;
	
	private JMenuBar menubar;
	private JMenu programMenu;
	private JMenuItem timetable,tt_shifts;
	
	private JLabel label1;
	private db conn;
	private String AM;
	
	private DefaultTableModel model;
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private ArrayList<String> rn = new ArrayList<String>();


	
	public DoctorHomePageFrame(db connection, String AM) {
		this.AM=AM;
		conn=connection;
		centralPanel = new JPanel(new BorderLayout());
		mainPanel= new JPanel();
		secondPanel= new JPanel();
	
		menubar= new JMenuBar();
		programMenu= new JMenu("Schedule");
		
		timetable= new JMenuItem("TimeTable");
		tt_shifts= new JMenuItem("TimeTable- Only My Shifts");
		label1= new JLabel("Weekly Program");
		
		programMenu.add(timetable);
		programMenu.add(tt_shifts);
		
		
		menubar.add(programMenu);
		
		mainPanel.add(menubar);
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		mainPanel.add(lb);
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
				dispose();
		        new GlobalHomeFrame(conn);           
		    }
		});
		
		//Button back
		ImageIcon icon2 = new ImageIcon("back1.png");
 	    JLabel lab = new JLabel(icon2);
 		mainPanel.add(lab);
 		
 		lab.addMouseListener(new MouseAdapter() 
 		 {
 			public void mouseClicked(MouseEvent e) 
 		    {	 
 				dispose();
 		        new DoctorPreferenceFrame(conn, AM);           
 		    }
 		  });

 		secondPanel.add(label1);
		ButtonListener listener = new ButtonListener();
		timetable.addActionListener(listener);
		tt_shifts.addActionListener(listener);
		
		model = new DefaultTableModel();
		model.addColumn("Schedule");
		model.addColumn("Monday");
		model.addColumn("Tuesday");
		model.addColumn("Wednesday");
		model.addColumn("Thursday");
		model.addColumn("Friday");
		model.addColumn("Saturday");
		model.addColumn("Sunday");
		model.addRow( new Object[] {"06:00-14:00", "-" , "-", "-", "-", "-", "-", "-" });
		model.addRow( new Object[] {"14:00-22:00", "-" , "-", "-", "-", "-", "-", "-" });
		model.addRow( new Object[] {"22:00-00:00", "-" , "-", "-", "-", "-", "-", "-" });
		table = new JTable(model);
		
		table.setAlignmentX(Component.LEFT_ALIGNMENT);
		  
		// Create ScrollPane for the table
		   
	    scrollPane = new JScrollPane(table);
		scrollPane.setBounds(36, 37, 407, 79);
		
		secondPanel.add(scrollPane);
			
		centralPanel.add(mainPanel, BorderLayout.NORTH);
		centralPanel.add(secondPanel, BorderLayout.CENTER);
		
		
		this.setContentPane(centralPanel);
		
		// Set frame in the center of the pc
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		        
		this.setVisible(true);
		this.setSize(500, 500);
		this.setTitle("Doctor/Schedule");
		
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String ttable =  conn.returnTimetable(); 
			
			if (ttable == null)
			{
				JOptionPane.showMessageDialog(centralPanel, "The program hasn't been created yet.","Inane warning", JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				String getRn=null;
				boolean flag =true;
				

				//Get all RNs from ttable in an arrayList<string> rn
				for(int i=0; i<ttable.length(); i++) {
					if((ttable.charAt(i)>47 && ttable.charAt(i)<58) || 
							(ttable.charAt(i)>96 && ttable.charAt(i)<123) || ttable.charAt(i)=='-') {
							if(flag) {
								getRn=String.valueOf(ttable.charAt(i));
								flag=false;
								}
							else {
								getRn+=ttable.charAt(i);
							}	
							
							
					}
					if(ttable.charAt(i) == ',' || ttable.charAt(i) == '/') {
						rn.add(getRn);
						flag=true;
					}
				
				}
				conn.getAllDoctors(doctors);
			}
			//κουμπί ωρολόγιο πρόγραμμα
			if(e.getSource()== timetable ) {
				// get the global timetable from the database
			
				
				    // Get the Global timetable from the database
				    // We already have the global timetable in the variable ttable and in the arraylist rn
					
					int time=-1;
					int day=-1;
					int i=-1;

					for(int k=0; k<rn.size(); k++) {
						i++;
						for(Doctor doct: doctors) {
							if(doct.getRn().equals(rn.get(i)) || "-".equals(rn.get(i))) {
								time=k%3;
								switch(k) {
			  	    		  	case 0:
			  	    		  		day=1; //Monday
			  	    		  		break;
					  	    	case 3:
					  	    	    day=2; //Tuesday
					  	    	    break;
					  	    	case 6:
			  	    		  		day=3; //Wednesday
			  	    		  		break;
					  	    	case 9:
					  	    	    day=4; //Thursday
					  	    	    break;
					  	    	case 12:
			  	    		  		day=5; //Friday
			  	    		  		break;
					  	    	case 15:
					  	    	    day=6; //Saturday
					  	    	    break;
					  	    	case 18:
					  	    	    day=7; //Sunday
					  	    	    break;
								}
								if(doct.getRn().equals(rn.get(i))) {
									model.setValueAt(doct.getFirstName()  + " " +  doct.getLastName(), time, day);
									break;}
							}
							
						}
					}
				}
				
	
			
			else {
				//Show only doctor's shift
				
				int time=-1;
				int day=-1;
				int i=-1;


				for(int k=0; k<rn.size(); k++) {
					i++;
					for(Doctor doct: doctors) {
						time=k%3;
						switch(k) {
	  	    		  	case 0:
	  	    		  		day=1; //Monday
	  	    		  		break;
			  	    	case 3:
			  	    	    day=2; //Tuesday
			  	    	    break;
			  	    	case 6:
	  	    		  		day=3; //Wednesday
	  	    		  		break;
			  	    	case 9:
			  	    	    day=4; //Thursday
			  	    	    break;
			  	    	case 12:
	  	    		  		day=5; //Friday
	  	    		  		break;
			  	    	case 15:
			  	    	    day=6; //Saturday
			  	    	    break;
			  	    	case 18:
			  	    	    day=7; //Sunday
			  	    	    break;
						}
						if(doct.getRn().equals(rn.get(i)) && doct.getRn().equals(AM)) {
						
							model.setValueAt(doct.getFirstName()  + " " +  doct.getLastName(), time, day);
							break;
						}
						if(doct.getRn().equals(rn.get(i)) && !(doct.getRn().equals(AM))) {
							model.setValueAt("-", time, day);
						}
						
					}
				}

				
			}
		}
	}
		
}
