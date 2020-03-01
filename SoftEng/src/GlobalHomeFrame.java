import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GlobalHomeFrame extends JFrame { //This name was <Basic GUI> 
	
	private JButton pharmacist = new JButton("Pharmacist");
	private JButton manager = new JButton("Manager  ");
	private JButton doctor = new JButton("Doctor      ");
	private JPanel basicPanel = new JPanel(new BorderLayout());
	private JPanel panel = new JPanel();
	private db conn;
	
	private JLabel label;
	private JLabel user = new JLabel("Select user:");

	public GlobalHomeFrame(db connection) {
	
		conn = connection;
		label = new JLabel("Program managment of human & material resources");
		panel.add(label);
		panel.add(user);
		basicPanel.add(pharmacist, BorderLayout.WEST);
		basicPanel.add(manager, BorderLayout.CENTER);
		basicPanel.add(doctor, BorderLayout.EAST);
	
		ButtonListener listener = new ButtonListener();
		pharmacist.addActionListener(listener);
		manager.addActionListener(listener);
		doctor.addActionListener(listener);
		
		panel.add(basicPanel);
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(600, 200);
		this.setTitle("Welcome!");
		
		// Set frame in the center of the pc
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);
        
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setVisible(false);

			if(e.getSource() == pharmacist) { 
				new PharmacistLogInFrame(conn);

			}
			
			else if(e.getSource() == manager) { 		
				new ManagerLogInFrame(conn);
			}
			
			else if(e.getSource() == doctor) { 
				new DoctorLogInFrame(conn);
			}
			
		}
	}
	
}
