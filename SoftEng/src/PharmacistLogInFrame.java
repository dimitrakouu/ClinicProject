import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;

public class PharmacistLogInFrame extends JFrame{
	
	private JLabel firstTitleOfPage;
	private JPasswordField textFieldForPassword;
	private JButton logInButton;
	private JPanel panel;
	private db conn;
	
	public PharmacistLogInFrame(db connection) {
		
		/* Creating the connection with base. */
		
		conn=connection;
		
		/* Creating panel. */
		
		panel = new JPanel();
		
		/* Creating necessary fields for the panel (Tite, TextField, confirm button, picture). */
		
		firstTitleOfPage = new JLabel("Central Page Of Pharmacist. Enter Password: ");
		textFieldForPassword = new JPasswordField(10);
		logInButton = new JButton("Log in");
		
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		
		/* Creating and adding mouse listener. */
		
		lb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) 
		    {	 
		    	dispose();
		    	new GlobalHomeFrame(conn);
		    	
		    	}
			
		});
		
		/* Adding title, textField and confirm button in panel. */
		
		panel.add(firstTitleOfPage);
		panel.add(textFieldForPassword);
		panel.add(logInButton);
		
		/* Creating and adding key listener for textField. */
		
		textFieldForPassword.addKeyListener(new KeyAdapter() {
	        
	        public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER){
	            	logInButton.doClick();
	            }
	        }

	    });
		
		/* Creating and adding listener for confirm button. */
		logInButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				/* Pharmacist's Code is 2222
				 * The given password must be the same with database's password */
				
				String result = textFieldForPassword.getText();
	
				String password = conn.returnPasswordUser(2);
		
				if (password.equals(result)) {
					dispose();
					new SupplyChainMainFrame(conn);
				}
				else {
					
					JOptionPane.showMessageDialog(null,"Wrong Password","Error..",JOptionPane.ERROR_MESSAGE);
					textFieldForPassword.setText("");
					
				}
			
			}
			
		});
		
		
		/* Adding all fields (title, spinner, buttons) in the main panel. */
		
		panel.add(lb);
		panel.add(firstTitleOfPage);


		panel.add(textFieldForPassword);

		panel.add(logInButton);
		
		/* Centered components. */
		
		panel.setLayout(new FlowLayout()); 

		/* Adding main panel in the content pane of the frame. */
		
		this.setContentPane(panel);
		
		// Set frame in the center of the screen. */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 3;
		int y = (screenSize.height - this.getHeight()) / 3;
		this.setLocation(x, y);
		
		this.setVisible(true);;
		this.setSize(400,400);
		this.setTitle("/Supply Chain/Pharmacist");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


