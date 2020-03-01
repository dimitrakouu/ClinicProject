import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class DoctorPreferenceFrame extends JFrame{ //This  name was <GUIProgramma> 
	
	private JPanel centralPanel;
	
	private JButton prefer;
	private JScrollPane scrollPane;
	private JTable table;
	
	private JLabel label;
	
	private String preference;
	private String AM;
	private db conn;


	
	public DoctorPreferenceFrame(db connection, String AM) {
		this.AM=AM;
		conn=connection;
		centralPanel=new JPanel();
		label=new JLabel("Doctor's preference");
		
		 
		//πινακας ωρών για επιλογή απο τον γιατρό
		Object[] columnNames = {"Schedule" , "Monday" , "Tuesday", "Wednesday", "Thusday", "Friday", "Saturday", "Sunday"};
	    Object[][] rowData = { {"06:00-14:00", false , false, false, false, false, false, false },
	    					   {"14:00-22:00", false, false, false, false, false, false, false},
	    					   {"22:00-06:00", false, false, false, false, false, false, false} };
	   
	  
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        table = new JTable(model) {
        	private static final long serialVersionUID = 1L;
        	 @Override
             public Class getColumnClass(int column) {
                     return getValueAt(0, column).getClass();
        	 }
        };
        
	
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);
	 
	   // scrollPane.setBounds(36, 37, 407, 79);
		
		prefer=new JButton("Input 3 preferences");
		centralPanel.add(label);
		centralPanel.add(scrollPane);
		centralPanel.add(prefer);
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		centralPanel.add(lb);
		
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
				dispose();
		        new GlobalHomeFrame(conn);           
		    }
		});
		
		ButtonListener listener = new ButtonListener();
		prefer.addActionListener(listener);
		
		
		this.setContentPane(centralPanel);
		
		// Set frame in the center of the pc
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 10;
		int y = (screenSize.height - this.getHeight()) / 10;
		this.setLocation(x, y);
				
		this.setVisible(true);
		this.setSize(950, 600);
		this.setTitle("Doctor/Sign-up/Preference");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			//Μετατροπή των καταχωρήσεων του γιατρού σε μορφή 0(false) και 1(true)
			
			
			
			if(e.getSource() == prefer) {
			int count=0;
			
				for(int i=1; i<8; i++) {
					for(int j=0; j<3; j++) {
						if(table.getModel().getValueAt(j,i).toString() =="true") {
                            if(i==1 && j==0) {
                            	preference = "1";}
                            else {
                            	preference += "1";}
                           }
						else {
							if(i==1 && j==0) {
								preference = "0";}
							else {
								preference += "0";}
						}
					}
				}
				
				String help = preference;
				char ch;
				for(int j=0; j<help.length(); j++) {
					
					ch=help.charAt(j);
						if(ch== '1') {
							count++;
						}
						if(count>3) {
							JOptionPane.showMessageDialog(centralPanel, "Put only 3 choices!","Inane warning",JOptionPane.WARNING_MESSAGE);
							break;
						}
			
				}
					if(count<3) {
						JOptionPane.showMessageDialog(centralPanel, "You have less than 3 choices!","Inane warning",JOptionPane.WARNING_MESSAGE);
					
					}
		
					if(count==3) {
						//save the doctor's preferences in the database
						conn.saveFieldDoctor("timetable", preference, AM); 
						ManagerHomePageFrame mn = new ManagerHomePageFrame(conn);
						mn.dispose();
						dispose();
						mn.setDoctorsTimeTable(AM, preference);
						new DoctorHomePageFrame(conn, AM);}
					}
				}
		}
}