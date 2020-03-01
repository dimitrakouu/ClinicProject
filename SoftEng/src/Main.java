/*
 * 
 */



import java.sql.SQLException;


public class Main {

	public static void main(String[] args) {

		db conn = null;
		try {
			conn = new db();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SupplyChainMainFrame.count = 0;
		new GlobalHomeFrame(conn);
		
	}
	

}
