import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

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
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class ManagerHomePageFrame extends JFrame { //This name was < GUIEfimeries>
	
	private JPanel centralPanel,mainPanel ,secondPanel, menuPanel;
	private JPanel EmployeeMainPanel, EmployeePanel_1,  EmployeePanel_2,  EmployeePanel_3;
	private JPanel ProgramMainPanel, ProgramPanel_1, ProgramPanel_2, ProgramPanel_3,ProgramPanel_4;
	
	private JMenuBar menubar;
	private JMenu employeeMenu;
	private JMenu programMenu;
	private JMenuItem change,create;
	
	private JButton add, remove;
	private JTextField FindField;
	private JScrollPane scrollPane, scrollPane2;
	private JTable doctorsTable, program;
	private JLabel hrLabel;
	private db conn ;
	
	private String rnDoctDB = null;
	
	private DefaultTableModel model, model2;
	
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>(); 
	// this list contains all the doctors

	public ManagerHomePageFrame(db connection) {
		conn= connection;
		centralPanel = new JPanel(new BorderLayout());
		mainPanel= new JPanel(new BorderLayout());
		secondPanel= new JPanel();
		menuPanel=new JPanel();
		
		menubar= new JMenuBar();
		employeeMenu= new JMenu("Employee");
		programMenu= new JMenu("Program");
		
		change= new JMenuItem("Change");
		create= new JMenuItem("Create");
		
		employeeMenu.add(change);
		programMenu.add(create);
		
		menubar.add(employeeMenu);
		menubar.add(programMenu);
	
		menuPanel.add(menubar);
		
		
		// Gets all the doctors from the database, put them in the ArrayList doctors
		conn.getAllDoctors(doctors);
		
		
		//Insert image to return at the Home Page
		ImageIcon icon = new ImageIcon("hospital1.png");
		JLabel lb = new JLabel(icon);
		mainPanel.add(lb, BorderLayout.NORTH);
		
		mainPanel.add(menuPanel, BorderLayout.AFTER_LAST_LINE);
		
		lb.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
		    {	 
				dispose();
		        new GlobalHomeFrame(conn);           
		    }
		});
		
		
		ButtonListener listener = new ButtonListener();
		change.addActionListener(listener);
		create.addActionListener(listener);
		
		centralPanel.add(mainPanel, BorderLayout.NORTH);
		centralPanel.add(secondPanel, BorderLayout.CENTER);
		
		this.setContentPane(centralPanel);
		
		// Set frame in the center of the pc
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 6;
		int y = (screenSize.height - this.getHeight()) / 6;
		this.setLocation(x, y);
		
		this.setVisible(true);
		this.setSize(800, 600);
		this.setTitle("Manager/Shifts");
		
	}
    
	

	public void setDoctorsTimeTable(String rn, String tb) {
		 for(Doctor  doct: doctors) {
			 if(doct.getRn().equals(rn)) {
				 doct.setTimetable(tb);
				
			 }
		 }
	}



	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
		
			secondPanel.removeAll();
			
			//Button: Employee->Change
			if(e.getSource()== change ) {
				
				hrLabel = new JLabel ("Workforce availability");
				EmployeeMainPanel=new JPanel(new BorderLayout());
				EmployeePanel_1= new JPanel();
				EmployeePanel_2= new JPanel();
				EmployeePanel_3= new JPanel();
				
				// get all doctors from the database
				
				model = new DefaultTableModel();
				model.addColumn("First Name");
				model.addColumn("Last Name");
				model.addColumn("RN");
				String firstname_db=null; 
				String lastname_db=null;
				String rn_db=null;
				
				for(Doctor doct: doctors) {
					firstname_db= doct.getFirstName();
					lastname_db=doct.getLastName();
					rn_db=doct.getRn();
					model.addRow(new Object[] {firstname_db, lastname_db, rn_db});
				}
			
			    doctorsTable = new JTable(model);
				
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(doctorsTable.getModel());
			    doctorsTable.setRowSorter(sorter);
				
			    
				FindField= new JTextField("Search Employee"); 
				
				add =new JButton("Add"); 
				remove =new JButton("Remove");
				
				//Search Employee in all Fields (LastName, FirstName, RN)
	
				FindField.getDocument().addDocumentListener(new DocumentListener() {

					public void changedUpdate(DocumentEvent e) {
						throw new UnsupportedOperationException("Not supported yet.");
					}

					public void insertUpdate(DocumentEvent e) {
						
						String text = FindField.getText();

		                if (text.trim().length() == 0) {
		                    sorter.setRowFilter(null);
		                } else {
		                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                }
						
					}

					public void removeUpdate(DocumentEvent e) {
						
						String text = FindField.getText();

		                if (text.trim().length() == 0) {
		                    sorter.setRowFilter(null);
		                } else {
		                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		                }
					}
				});
				
				doctorsTable.setAlignmentX(Component.LEFT_ALIGNMENT);
			  
				// Create ScrollPane for the table
				   
			    scrollPane = new JScrollPane(doctorsTable);
				scrollPane.setBounds(36, 37, 407, 79);
			
				EmployeePanel_1.add(hrLabel);
				EmployeePanel_2.add(scrollPane);
				EmployeePanel_3.add(FindField);
				EmployeePanel_3.add(add);
				EmployeePanel_3.add(remove);
				EmployeeMainPanel.add(EmployeePanel_1, BorderLayout.NORTH);
				EmployeeMainPanel.add(EmployeePanel_2, BorderLayout.CENTER);
				EmployeeMainPanel.add(EmployeePanel_3, BorderLayout.SOUTH);
				secondPanel.add(EmployeeMainPanel);
				
				pack(); 
				
				
				//Add a new employee in database
				add.addActionListener(new ActionListener()
			    {	
				      public void actionPerformed(ActionEvent e)
				      {
				    	  
				    	  secondPanel.removeAll();
				    	//Insert image to return at the Home Page
				  		 ImageIcon icon = new ImageIcon("back1.png");
				  		 JLabel lb = new JLabel(icon);
				  		 mainPanel.add(lb);
				  		
				  		EmployeeMainPanel=new JPanel(new BorderLayout());
				  		EmployeePanel_1= new JPanel();
						EmployeePanel_2= new JPanel(); 
				  		 
				  		 lb.addMouseListener(new MouseAdapter() 
				  		 {
				  		 	public void mouseClicked(MouseEvent e) 
				  		    {	 
				  		 		dispose();
				  		        new ManagerHomePageFrame(conn);           
				  		    }
				  		  });
				  		 
				    	  JLabel label = new JLabel("Create a New Employee");
				    	  JButton add_employee = new JButton("Add Employee");
				    	  JTextField firstNameField = new JTextField("First Name");
				    	  JTextField lastNameField = new JTextField("Last  Name");
				    	  JTextField amField = new JTextField("ал");
				    	  
				    	  EmployeePanel_1.add(label);
				    	  EmployeePanel_2.add(firstNameField);
				    	  EmployeePanel_2.add(lastNameField);
				    	  EmployeePanel_2.add(amField);
				    	  EmployeePanel_2.add(add_employee);
				    	  EmployeeMainPanel.add(EmployeePanel_1, BorderLayout.NORTH);
				    	  EmployeeMainPanel.add(EmployeePanel_2, BorderLayout.CENTER);
				    
				    	  secondPanel.add(EmployeeMainPanel);
				    	  
				    	  pack();
				    	  
				    	  //add_employee -> Button to add a new employee
				    	  add_employee.addActionListener(new ActionListener()
						    {	
							      public void actionPerformed(ActionEvent e)
							      { 
							    	 
							    	  String AM, firstName, lastName;
							    	  int NumberOfDocs;
							    	  
							    	  firstName = firstNameField.getText();
							    	  lastName = lastNameField.getText();
							    	  AM = amField.getText();
							    	  
							    	  // ----------SEARCH IF THE DOCTOR ALREADY EXISTS------------
							    	  //check if the typed AM already exists
							    	  NumberOfDocs =  conn.getNumberOfEntriesWithCondition("doctor", "RN", AM);
					
				    	  			  if (NumberOfDocs == -1)
				    	  			  {
				    	  				 JOptionPane.showMessageDialog(secondPanel, "Error", "Inane error", JOptionPane.ERROR_MESSAGE);
				    	  			  }
				    	  			  else if (NumberOfDocs == 1 )
				    	  			  {
				    	  				// show that the doctor with the given RN already exists
				    	  				  JOptionPane.showMessageDialog(secondPanel, " The doctor with the given RN already exists", "Inane error", JOptionPane.ERROR_MESSAGE);
				    	  				  
				    	  			  }
				    	  			  else
				    	  			  {
								    	  //-----------INSERT DOCTOR-----------
					    	  			  Doctor d = new Doctor(firstName, lastName, AM);
					    	  			  
					    	  			  // add the doctor in database 
					    	  			  conn.addDoctor(d);
					    	  			  // add the doctor in ArrayList doctors 
					    	  			  doctors.add(d);
					    	  			  dispose();
					    	  			  new ManagerHomePageFrame(conn);
				    	  			  }	
							      }
						    });
				    	  
				      }
			    });
				
				 //remove -> button to remove an employee

		    	  remove.addActionListener(new ActionListener()
				    {	
					      public void actionPerformed(ActionEvent e){
					    	  
					    	  String valueInCell=null;
					    	  int row_remove= doctorsTable.getSelectedRow();
					    	  
					    	  if(row_remove!=-1) {
						    	  //Get RN from the selected employee
						    	  valueInCell = (String)doctorsTable.getModel().getValueAt(row_remove, 2);
						    	 
						    	  //Delete the selected employee from JTable
			
						    	  DefaultTableModel model =
						    			  (DefaultTableModel)doctorsTable.getModel();
						    			model.removeRow(doctorsTable.getSelectedRow());
						    		
						    	  //Delete the selected employee(with RN=(String)valueInCell) from List doctors
						        	doctors = Doctor.RemoveDoctor(valueInCell, doctors);
						   
						       	 //UPDATE THE DATABASE
						        	conn.removeDoctor(valueInCell);}
					    	  else {
					    		  JOptionPane.showMessageDialog(secondPanel, "Choose One Doctor from the table to remove", "Inane warning", JOptionPane.WARNING_MESSAGE);
					    	  }
					    	
					      }
					   
					      
					   });
				
				
			}
			//Button: Program-> Create
			else if(e.getSource() == create) {
				
				ProgramMainPanel= new JPanel(new BorderLayout());
				ProgramPanel_1=new JPanel();
				ProgramPanel_2= new JPanel();
				ProgramPanel_3= new JPanel();
				ProgramPanel_4= new JPanel();
				
				JLabel label= new JLabel("Create - Export TimeTable");
				JLabel text1 = new JLabel("Number of doctors in data base: ");
				JButton show_program = new JButton("Show Program");
				JButton cancel_but = new JButton("Cancel");
				
				// Get the number of the doctors that exist in the database
				int NumberOfDocs = conn.getNumberOfEntries("doctor");
				
				if (NumberOfDocs == -1) // an error occurred during the db query
				{
					// AN ERROR HAS OCCURRED
				}
				else // query was successful
				{
					// add the number to the text
					text1.setText(text1.getText() + " '" + NumberOfDocs + "' , choose maximum 7.");
				}
			
				model = new DefaultTableModel();
				model.addColumn("First Name");
				model.addColumn("Last Name");
				model.addColumn("RN");
				model.addColumn("Check");
				String firstname_db=null; 
				String lastname_db=null;
				String rn_db=null;
				Boolean check;
				
				for(Doctor doct: doctors) {
					firstname_db= doct.getFirstName();
					lastname_db=doct.getLastName();
					rn_db=doct.getRn();
					check=false;
					model.addRow(new Object[] {firstname_db, lastname_db, rn_db, check});
				}
				
				//doctorsTable = new JTable(model);
				
				   doctorsTable = new JTable(model) {
			        	private static final long serialVersionUID = 1L;
			        	 @Override
			             public Class getColumnClass(int column) {
			                     return getValueAt(0,column).getClass();
			        	 }
			        };
		      
	

			    scrollPane = new JScrollPane(doctorsTable);
				scrollPane.setBounds(36, 37, 407, 79);
			    
				
				
				secondPanel.add(label, BorderLayout.NORTH);
				secondPanel.add(text1, BorderLayout.NORTH);
				secondPanel.add(scrollPane, BorderLayout.WEST);
				secondPanel.add(show_program, BorderLayout.SOUTH);
				secondPanel.add(cancel_but, BorderLayout.SOUTH);
				
				/*ProgramPanel_1.add(label);
				ProgramPanel_2.add(text1);
				ProgramPanel_3.add(scrollPane);
				ProgramPanel_4.add(show_program);
				ProgramPanel_4.add(cancel_but);
				ProgramMainPanel.add(ProgramPanel_1, BorderLayout.NORTH);
				ProgramMainPanel.add(ProgramPanel_2, BorderLayout.AFTER_LAST_LINE);
				ProgramMainPanel.add(ProgramPanel_3, BorderLayout.CENTER);
				ProgramMainPanel.add(ProgramPanel_4, BorderLayout.SOUTH);
				secondPanel.add(ProgramMainPanel, BorderLayout.CENTER);*/
				pack();
				
				//Message that there are no doctors in the data base
				if(NumberOfDocs==0) {
					JOptionPane.showMessageDialog(centralPanel, "There are no doctors!", "Inane error", JOptionPane.ERROR_MESSAGE);
				}
				
				show_program.addActionListener(new ActionListener()
			    {	
			      public void actionPerformed(ActionEvent e)
			      {
			    	  int count=0;
			    	  ArrayList<Doctor> doctorsWITHprefer = new ArrayList<Doctor>();
			    	  ArrayList<Doctor> doctorsWITHOUTprefer = new ArrayList<Doctor>();
			    	  
			    	  for(int i=0; i<doctors.size(); i++) {
							 
						if(doctorsTable.getModel().getValueAt(i,3).toString() =="true") {
							count++;
						}
						
						if(count>7 && NumberOfDocs>6) {
							JOptionPane.showMessageDialog(centralPanel, "Put only 7 choices!", "Inane warning", JOptionPane.WARNING_MESSAGE);
							break;
						}
			    	  }
			    	  
			    	  if(count<7 && NumberOfDocs >6) {
							JOptionPane.showMessageDialog(centralPanel, "You have less than 7 choices!", "Inane warning", JOptionPane.WARNING_MESSAGE);
						
						}
			    	  if(NumberOfDocs<7 && count<NumberOfDocs) {
			    		  JOptionPane.showMessageDialog(centralPanel, "Select all the doctors!", "Inane warning", JOptionPane.WARNING_MESSAGE);
			    	  }
			    	  
			    	  if(count==7 || (NumberOfDocs==count && NumberOfDocs<7)) {
			    		  for(int i=0; i<doctors.size(); i++) {
								 
								if(doctorsTable.getModel().getValueAt(i,3).toString() =="true") {
									
									for(Doctor doct: doctors) {
										if(doct.getRn().equals(doctorsTable.getModel().getValueAt(i,2).toString())) {
											if(doct.getTimetable()!=null) {
												// A List with the selected doctors by the manager who have preferences
												doctorsWITHprefer.add(doct);
											}
											else {
												//A List with the selected doctors by the manager who haven't preferences
												doctorsWITHOUTprefer.add(doct);
											}
										}
									}
								}
			    		  }
			    		  
			    		  secondPanel.removeAll();
				    	  JLabel label= new JLabel();
				    	  JButton save = new JButton("Save");
				    	  
				    	  label.setText("Shift's Program ");
				    	 
				    	  model2 = new DefaultTableModel();
				  		  model2.addColumn("Schedule");
				  		  model2.addColumn("Monday");
				  		  model2.addColumn("Tuesday");
				  		  model2.addColumn("Wednesday");
				  		  model2.addColumn("Thursday");
				  		  model2.addColumn("Friday");
				  		  model2.addColumn("Saturday");
				  		  model2.addColumn("Sunday");
				  		  model2.addRow( new Object[] {"06:00-14:00", "-" , "-", "-", "-", "-", "-", "-" });
				  		  model2.addRow( new Object[] {"14:00-22:00", "-" , "-", "-", "-", "-", "-", "-" });
				  		  model2.addRow( new Object[] {"22:00-00:00", "-" , "-", "-", "-", "-", "-", "-" });
				  		  program = new JTable(model2);
				  		
				  		  program.setAlignmentX(Component.LEFT_ALIGNMENT);
				  
			
				  	      program.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				  	       // Create ScrollPane for the table
				   

				  	      scrollPane2 = new JScrollPane(program);
				  	      scrollPane2.setBounds(36, 37, 407, 79);
				  	
				  	    
				  	      int time=-1; //working hour
				  	      int day=1; //day
				  	      
				  	
				  	      Collections.shuffle(doctorsWITHprefer);   //Random List 
				  	      Collections.shuffle(doctorsWITHOUTprefer); //Random List
				  	      
				  	    
				  	      for(Doctor doct: doctorsWITHprefer) {
				  	    	 
				  	    	String prefer =  doct.getTimetable();  //Preference
				  	    	int countfor1=0;  
				  	    	StringBuilder pref = new StringBuilder(prefer);
				  	    	for(int k=0; k<pref.length(); k++) { //pref.lenght() ==21, because we have 7 days with 3 working hours
				  	    		boolean flag=false;
				  	    		time = k%3; // Working hour
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
				  	    		  
				  	    		 /* if preference = 1 
				  	    		  *  check if this day is available
				  	    		  */
				  	    		 
				  	    		if(pref.charAt(k) == '1') {
				  	    			  countfor1 ++; 

				  	    			  if((model2.getValueAt(time, day) == (Object)"-") )  {
						  	    		  model2.setValueAt(doct.getFirstName()  + " " +  doct.getLastName(), time, day);
						  	    		  
						  	    	  }
				  	    			  else {
				  	    				  //If this day isn't available, doctor will work at the first free woriking hour
				  	    				  for(int d=1;d<8; d++) {
				  	    					  for(int h=0;h<3;h++) {
				  	    						  if(model2.getValueAt(h, d) == (Object)"-") {
				  	    							model2.setValueAt(doct.getFirstName()  + " " +  doct.getLastName(), h, d);
				  	     							
				  	    							flag=true;
				  	    							
				  	    							break;
				  	    						  }
				  	    						
				  	    					  }
				  	    					if(flag) {
				  	    
			  	    							break;
			  	    							
			  	    						}
				  	    				  }
				  	    			  }

				  	    		 }
				  	    		//the 3 preferences have been read.
				  	    		if(countfor1==3) {
			  	    				 break;
			  	    			  }   		  
				  	    	}
				  	      }
				  	      
				  	    //Doctors without preference
				  	    //They'll work in serial
				  	      
				  	    for(Doctor doct: doctorsWITHOUTprefer) {
				  	    	int counter=0;
				  	    	for(int d=1;d<8; d++) {
	  	    					  for(int h=0;h<3;h++) {
	  	    						  if(model2.getValueAt(h, d) == (Object)"-") {
	  	    							model2.setValueAt(doct.getFirstName() + " " +  doct.getLastName(), h, d);
	  	    							
	  	    							counter++;
	  	    							if(counter==3) {
	  	    								break;}
	  	    						  }
	  	    						
	  	    					  }if(counter==3) {
	    								break;}
	  	    				  }
				  	    }
				  	    
				  	  //Create a String rnDoctDB with Doctors_RN for database 
				  	  String findrn=null;
				  	  for(int d=1;d<8; d++) {
	    					  for(int h=0;h<3;h++) {
	    						  
	    						  for(Doctor doct: doctors) {
	    							  if(((String)model2.getValueAt(h, d)).equals(doct.getFirstName() + " " +  doct.getLastName())) {
	    								  findrn=doct.getRn();
	    								  break;
	    							  }
	    						  }
	    						  if(((String)model2.getValueAt(h, d)).equals("-")) {
	    							  findrn="-";
	    						  }
	    						  if(d==1 && h==0) {
	    							  rnDoctDB = findrn;
	    						  }
	    						  else {
	    							  rnDoctDB += findrn;
	    						  }
	    						  if(h!=2) {
	    							  rnDoctDB += ",";
	    						  }
	    					  }
	    					  rnDoctDB+="/";
				  	  }
				  	  
				    	  secondPanel.add(label);
				    	  secondPanel.add(scrollPane2);
				    	  secondPanel.add(save);	
				         
				          pack();
				    	   
				    	  save.addActionListener(new ActionListener()
						    {	
						      public void actionPerformed(ActionEvent e)
						      {
						    	  //Save the global timetable in the database
						    	  //rnDoctDB
						    	  conn.saveTimetable(rnDoctDB);

						    	  JOptionPane.showMessageDialog(secondPanel, "Program saved successfully");
						      }
						    });
				    	  
				    	
			    		  
			    	  }
			    	  
			    	  
			    	  
			    	  
			    	  
			    	  
			    	  
			    	   
			    	  }
			    });
				
				cancel_but.addActionListener(new ActionListener()
			    {	
			      public void actionPerformed(ActionEvent e)
			      {	  
			    	  
			    	  dispose();
		  		      new ManagerHomePageFrame(conn);
			    	
			      }
			    });

			}
	
		}	
	}
		
}