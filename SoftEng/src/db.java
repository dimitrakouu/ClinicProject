import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class db {
	private Connection myConn = null;
	private String result = null;

	public db() throws SQLException {

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mydb19?useTimezone=true&serverTimezone=UTC", "root", "1234");
			
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myConn != null) {
				// myConn.close();
			}
		}

	}

	/*
	 * Function: gets the global timetable from the database
	 */
	public String returnTimetable() {
		Statement myStmt = null;
		ResultSet myRs = null;
		String timetable = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select ttable from timetable where id = 1";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				timetable = myRs.getString("ttable");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timetable;
	}

	/*
	 * Input: The timetable as a String Function: Updates the db with the
	 * new global timetable
	 */
	public void saveTimetable(String timetable) {
		Statement myStmt = null;

		try {
			// update the timetable in the database
			myStmt = myConn.createStatement();
			String query = "update timetable set ttable = \"" + timetable + "\" where id = 1";

			myStmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Input: the name of a field, the value to be saved in that field and the value
	 * that satisfies the condition Function: updates-saves the given field with the
	 * given saveValue if the condition is satisfied
	 */
	public void saveFieldDoctor(String field, String saveValue, String conditionValue) {
		Statement myStmt = null;

		try {
		//update
			myStmt = myConn.createStatement();
			String query = "update doctor set " + field + " = \"" + saveValue + "\" where RN = \"" + conditionValue
					+ "\"";

			myStmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Input: A list that will contain all the data from all the doctors 
	 * Function: Gets all the doctors from the database and all their data and puts them in
	 * the ArrayList that was sent as input 
	 * Output: The list that contains the doctors
	 */
	public void getAllDoctors(ArrayList<Doctor> doctors) {
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = myConn.createStatement();
			// Execute SQL query
			String query = "select * from doctor";
			myRs = myStmt.executeQuery(query);

			// Process the result set and put the data in the doctor list
			Doctor d;
			while (myRs.next()) {

				d = new Doctor(myRs.getString("First Name"), myRs.getString("Last Name"), myRs.getString("RN"),
						myRs.getString("Password"), myRs.getString("Timetable"));

				// add the doctor to the doctor's list
				doctors.add(d);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				try {
					myRs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Input: The name of the table Function: counts how many entries exist in the
	 * given table Output: returns: -1 in the case of an error a positive integer
	 * that states the number entries
	 */
	public int getNumberOfEntries(String table) {
		int Number = -1;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			// create and send the query
			String query = "select count(*) as Number from " + table;
			myRs = myStmt.executeQuery(query);

			// get the result of the database
			while (myRs.next())
				Number = myRs.getInt("Number");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Number;
	}

	/*
	 * Input: The name of the table, the field to count, the value of the condition
	 * Function: counts how many entries exist in the given table that satisfies a
	 * certain condition Output: returns: -1 in the case of an error a positive
	 * integer that states the number of satisfied entries
	 */
	public int getNumberOfEntriesWithCondition(String table, String field, String value) {
		int Number = -1;

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();

			// create and send the query
			String query = "select count(*) as Number from " + table + " where " + field + " = \"" + value + "\"";
			myRs = myStmt.executeQuery(query);

			// get the result of the database
			while (myRs.next())
				Number = myRs.getInt("Number");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Number;
	}

	/*
	 * INPUT: the doctor to be inserted to the database 
	 * Function: inserts doctor to the database with null values in password and timetable 
	 * 
	 */
	public void addDoctor(Doctor d) {
		Statement myStmt = null;

		try {
			myStmt = myConn.createStatement();

			myStmt.executeUpdate("Insert into doctor (`First Name`, `Last Name`, `RN`)  Values ('" + d.getFirstName()
					+ "', '" + d.getLastName() + "', '" + d.getRn() + "')");

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Input: The RN of the doctor that will be deleted Function: Deletes from the
	 * database the doctor with the given RN
	 */
	public void removeDoctor(String value) {
		Statement myStmt = null;

		try {
			// 2. Create a statement
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "delete from doctor where RN = \"" + value + "\"";

			myStmt.executeUpdate(query);
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Input: the AM of the doctor Function: Gets the personal password of the given
	 * doctor from the database Output: The password of the given doctor: null if a
	 * password hasn't been defined yet otherwise the password itself is returned
	 */
	public String returnDoctorPassword(String AM) {
		Statement myStmt = null;
		ResultSet myRs = null;
		String pass = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select Password from doctor where RN = \"" + AM + "\"";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				pass = myRs.getString("Password");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pass;
	}

	/*
	 * Input: the AM of the doctor Function: Gets the personal timetable of the
	 * given doctor from the database Output: The timetable of the given doctor:
	 * null if a timetable hasn't been defined yet otherwise the timetable itself is
	 * returned
	 */
	public String returnDoctorTimetable(String AM) {
		Statement myStmt = null;
		ResultSet myRs = null;
		String timetable = null;

		try {
			myStmt = myConn.createStatement();
			// 3. Execute SQL query
			String query = "select Timetable from doctor where RN = \"" + AM + "\"";
			myRs = myStmt.executeQuery(query);

			while (myRs.next())
				timetable = myRs.getString("Timetable");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timetable;
	}

	/*
	 * Input: the code of the wanted entity (1: manager, 2: pharmacist)
	 * Function: gets the password of the wanted entity from the database 
	 * Output: the password of the entity
	 */
	public String returnPasswordUser(int code) {

		Statement myStmt = null;
		ResultSet myRs = null;
		String pass = null;

		try {
			myStmt = myConn.createStatement();
			// Execute SQL query
			String query = "select Password from privileged_user where Code = " + code;
			myRs = myStmt.executeQuery(query);

			if (myRs.next())
				pass = myRs.getString("Password");
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				try {
					myRs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return pass;
	}

	
	
	 /* INPUT: the drug to be inserted to the database and a connection with the database
		 * Function: inserts drug to the database
		 * Output: -----------------------
		 */
	 public void addDrug (Drug d) {
			Statement myStmt = null;
				
			
			try {
				// 2. Create a Statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				myStmt.executeUpdate("Insert into drug (`id`, `Availability`, `Price`, `Name`, `SoldUnits`)  Values ('" + d.getId() + "', '" + d.getAvailability() + "', '" + d.getPrice() + "', '" + d.getName() + "', '" + d.getSoldUnits() + "')");
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
				if (myStmt != null) {
					try {
						myStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 } 
	 
	/*
	 * INPUT: a list of drugs to be deleted from the database and a connection with the
	 * database Function: deletes drugs from the database 
	 * Output:-----------------------
	 */
	public void removeDrug(String drugName) {
		PreparedStatement myStmt = null;

		try {
			
				// 2. Prepare Statement
				myStmt = myConn.prepareStatement("delete from drug where Name = ?");

				// 3. Set the Parameters
				myStmt.setString(1, drugName);

				// 4. Execute SQL query
				myStmt.executeUpdate();

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	 	/*  Input: A list that will contain all the data from all the drugs
		 * 		   and a connection with the database
		 *  Function: Gets all the drugs from the database and all their data
		 *            and puts them in the ArrayList that was sent as input
		 *  Output: The list that contains the drugs
		 */
		public void getAllDrugs(ArrayList<Drug> drugs)
		{
			Statement myStmt = null;
			ResultSet myRs = null;
			try {
				// 2. Create a Statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				String query = "select * from drug";
				myRs = myStmt.executeQuery(query);
				
				// 4. Process the result set and put the data in the drug list
				Drug d;
				while (myRs.next()) {
					
					
					d = new Drug(myRs.getString("Name"), myRs.getString("id"), myRs.getDouble("Price")
							, myRs.getInt("Availability"));
					d.setSoldUnits(myRs.getInt("SoldUnits"));
					// add the drug to the drug's list
					drugs.add(d);
				}
				
			}catch (Exception exc) {
					exc.printStackTrace();
				}finally {
					if (myRs != null) {
						try {
							myRs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (myStmt != null) {
						try {
							myStmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}		
		}
		
		/*  Input: A list that will contain all the data from all the drugs
		 *  Function: Updates all the drugs from the database and all their data
		 *            and puts them in the ArrayList that was sent as input
		 *  Output: The list that contains the updated drugs 
		 */
		public void updateDrugList(ArrayList<Drug> drugs)
		{
			PreparedStatement myStmt = null;
			
			try {
				for(Drug d : drugs) {
					// 2. Prepare Statement
					myStmt = myConn.prepareStatement("UPDATE drug SET `Availability` = ?, `Price` = ?, `Name` = ?, `SoldUnits` = ? WHERE (`id` = ?);");

					// 3. Set the Parameters
					myStmt.setInt(1, d.getAvailability());
					myStmt.setDouble(2, d.getPrice());
					myStmt.setString(3, d.getName());
					myStmt.setInt(4, d.getSoldUnits());
					myStmt.setString(5, d.getId());

					// 4. Execute SQL query
					myStmt.executeUpdate();
		        }
			}catch (Exception exc) {
					exc.printStackTrace();
				}finally {
					if (myStmt != null) {
						try {
							myStmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			}	
		}
		
		/* INPUT: Order inserts into the database and the type of Order (Prescription(true) or Supply(false))
		 * Function: inserts order into the database table supply and supply_has_drug or prescription and pres_has_drug
		 * Output: -----------------------
		 */
	 public void updateOrderDataBase (Order o, boolean typeOfOrder) {
		 	
		 
			Statement myStmt = null;

			if(typeOfOrder == true) 
				o = (Prescription) o;
			else if(typeOfOrder == false) 
				o = (Supply) o;

			try {
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date utilDate1 = format.parse(o.getDate());
				java.sql.Date sqlDate = new java.sql.Date(utilDate1.getTime());
				
				// Create a Statement
				myStmt = myConn.createStatement();
				
				// Execute SQL query
				
				if(typeOfOrder == true) {
					
					myStmt.executeUpdate("INSERT INTO prescription (`id`, `Price`, `Date`) VALUES ('" + o.getCode() + "', '" + o.getTotalCost() + "', '" + sqlDate + "');");
				
					for(int i=0; i<o.getListOfMedicines().size(); i++) {

						myStmt.executeUpdate("INSERT INTO pres_has_drug (`pid`, `Quantity`, `drugid`) VALUES ('" + o.getCode() + "', '" + o.getQuantityOfMedicines().get(i) + "', '" + o.getListOfMedicines().get(i).getId() + "');");
					}
				
				}else if(typeOfOrder == false) {
					
					myStmt.executeUpdate("INSERT INTO supply (`id`, `Price`, `Date`) VALUES ('" + o.getCode() + "', '" + o.getTotalCost() + "', '" + sqlDate + "');");
					
					for(int i=0; i<o.getListOfMedicines().size(); i++) {

						myStmt.executeUpdate("INSERT INTO supply_has_drug (`Quantity`, `sid`, `did`) VALUES ('" + o.getQuantityOfMedicines().get(i) + "', '" + o.getCode() + "', '" + o.getListOfMedicines().get(i).getId() + "');");
					}

				}
		
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			finally {
				if (myStmt != null) {
					try {
						myStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 }
	 
	 	/*
		 * Input: the type of Order (Prescription(true) or Supply(false)) 
		 * Function: counts how many entries exist in the given table
		 * Output: returns: -1 in the case of an error or a positive integer that states the number entries
		 */
		public int getNextOrderCode(boolean typeOfOrder)
		{
			Statement myStmt = null;
			ResultSet myRs = null;
			String orderName = null;
			
			int id = -1;
			
			if(typeOfOrder == true) {
				orderName = "prescription";
			}else if(typeOfOrder == false) {
				orderName = "supply";
			}
			try {
				// 2. Create a Statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				String query = "select count(id) from " + orderName;
				myRs = myStmt.executeQuery(query);
				
				// 4. Get the result of the database
				while (myRs.next())
					 id = myRs.getInt("count(id)");
				
			}catch (Exception exc) {
					exc.printStackTrace();
				}finally {
					if (myRs != null) {
						try {
							myRs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (myStmt != null) {
						try {
							myStmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}	
			return id;
		}
		
		/*  Input: the type of Order (Prescription(true) or Supply(false)), the 2 dates the the program wants info and 2 array lists
		 *  Function: after the execution of the query info enters into the 2 array lists for statistic needs
		 *  Output: 2 array lists id and Quantity
		 */
		public void getInfoFromOrderDataBaseForSpecificDates(boolean typeOfOrder, String date1, String date2, ArrayList<Integer> id, ArrayList<Integer> Quantity)
		{
			Statement myStmt = null;
			ResultSet myRs = null;
			
			try {
				
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date utilDate1 = format.parse(date1);
				java.util.Date utilDate2 = format.parse(date2);
			
				
				java.sql.Date sqlfirstDate = new java.sql.Date(utilDate1.getTime());
				java.sql.Date sqlSecondDate = new java.sql.Date(utilDate2.getTime());
				
				// 2. Create a Statement
				myStmt = myConn.createStatement();
				
				// 3. Execute SQL query
				String query = null;
				if(typeOfOrder == true) { 
					query = "SELECT ALL drugid ,sum(Quantity) from mydb19.pres_has_drug INNER JOIN mydb19.prescription\r\n" + 
							"ON mydb19.prescription.id = mydb19.pres_has_drug.pid\r\n" + 
							"WHERE mydb19.prescription.Date between '" + sqlfirstDate + "' and '" + sqlSecondDate + "'\r\n" + 
							"GROUP BY drugid;";
				}else if(typeOfOrder == false) {
					query = "SELECT ALL did ,sum(Quantity) from mydb19.supply_has_drug INNER JOIN mydb19.supply\r\n" + 
							"ON mydb19.supply.id = mydb19.supply_has_drug.sid\r\n" + 
							"WHERE mydb19.supply.Date between '" + sqlfirstDate + "' and '" + sqlSecondDate + "'\r\n" + 
							"GROUP BY did;";
				}
				
				myRs = myStmt.executeQuery(query);
				
				// 4. Process the result set and put the data in the 2 array lists
				if(typeOfOrder == true) {
					while (myRs.next()) {
					
						int i = myRs.getInt("drugid");
						int q = myRs.getInt("sum(Quantity)");
						// add id to id's list and quantity to quantity's list
						id.add(i);
						Quantity.add(q);
					}
				}else if(typeOfOrder == false) {
					while (myRs.next()) {
						
						int i = myRs.getInt("did");
						int q = myRs.getInt("sum(Quantity)");
						// add id to id's list and quantity to quantity's list
						id.add(i);
						Quantity.add(q);
					}
				}
				
			}catch (Exception exc) {
					exc.printStackTrace();
				}finally {
					if (myRs != null) {
						try {
							myRs.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (myStmt != null) {
						try {
							myStmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}		
		}
		

	public Connection getMyConn() {
		return myConn;
	}

	public String getResult() {
		return result;
	}

	
}
