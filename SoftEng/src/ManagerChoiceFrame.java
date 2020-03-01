import java.awt.BorderLayout;
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
import javax.swing.JPanel;


class ManagerChoiceFrame extends JFrame{ //This name was <GUIDieuthinti2> 
		private JButton s_chain =new JButton("Supply Chain");
		private JButton shift =new JButton("Shift Work");

		private JPanel D_Panel, SecondPanel;
		
		private db conn;

					 
		public ManagerChoiceFrame(db connection) {
			conn=connection;
			D_Panel = new JPanel(new BorderLayout());
			SecondPanel=new JPanel();
			
			ImageIcon icon = new ImageIcon("hospital1.png");
			JLabel lb = new JLabel(icon);
			D_Panel.add(lb, BorderLayout.NORTH);
			
			SecondPanel.add(s_chain);
			SecondPanel.add(shift);
			D_Panel.add(SecondPanel);
			
			ButtonListener listener = new ButtonListener();
			s_chain.addActionListener(listener);
			shift.addActionListener(listener);
			

		/*	ImageIcon icon = new ImageIcon("hospital1.png");
			JLabel lb = new JLabel(icon);
			D_Panel.add(lb);*/
			
			lb.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
			    {	 
					dispose();
			        new GlobalHomeFrame(conn);           
			    }
			});
			
			this.setContentPane(D_Panel);
			
			// Set frame in the center of the pc
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screenSize.width - this.getWidth()) / 3;
			int y = (screenSize.height - this.getHeight()) / 3;
			this.setLocation(x, y);
			
			this.setVisible(true);
			this.setSize(400, 300);
			this.setTitle("Manager");
						 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		
		
		class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
				//Button: Supply Chain
				if(e.getSource() == s_chain) {
					dispose();
					new SupplyChainMainFrame(conn);

				}
				//Button: Shift Work
				else if(e.getSource() == shift){
					dispose();
					new ManagerHomePageFrame(conn);
				}			
			}
		}
	}

