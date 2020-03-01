import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


public class ManagerLogInFrame extends JFrame  {// This name was <GUIDieuthinti1> 

	private JPasswordField code_pass = new JPasswordField(10);
	
	private JButton log_in_But =new JButton("Log In");

	private JLabel label= new JLabel();
	private JPanel CentralPanel, SecondPanel;

	private db conn;

	
	public ManagerLogInFrame(db connection) {
		conn = connection;
		CentralPanel=new JPanel(new BorderLayout());
		SecondPanel = new JPanel();
		
		label.setText("Please enter Manager's password");
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		CentralPanel.add(lb, BorderLayout.NORTH);
		
		SecondPanel.add(label);
		SecondPanel.add(code_pass);
		SecondPanel.add(log_in_But);
		CentralPanel.add(SecondPanel, BorderLayout.CENTER);
		
		code_pass.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	log_in_But.doClick();
	            }
	        }

	    });
		
		ButtonListener listener = new ButtonListener();
		log_in_But.addActionListener(listener);


		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
				dispose();
		        new GlobalHomeFrame(conn);           
		    }
		});

		this.setContentPane(CentralPanel);
		
		// Set frame in the center of the pc
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
				
		this.setVisible(true);
		this.setSize(500, 250);
		this.setTitle("Manager/Password");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e)  {
			
			if(e.getSource() == log_in_But) {
				String password= code_pass.getText();
			
				
				/*Manager Code is 1111
				 * The given password must be the same with database's password
				 * All the Managers have the same password
				 */
				String result = conn.returnPasswordUser(1);
		
				if (password.equals(result)) {
					dispose();
					new ManagerChoiceFrame(conn);
				}
				else {
					
					 JOptionPane.showMessageDialog(CentralPanel, "Wrong password","Inane error",JOptionPane.ERROR_MESSAGE);
				}
		
				
			}
			
		}
	}
	
	
	
}
