import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * This class acts as the server and interacts with the client and database
 * @author neera
 *
 */
public class ThreadedDataObjectServer {
	
	public static void main(String[] arg){

		try {
			ServerSocket s = new ServerSocket(9999);
	         
	        for (;;) {
	        	Socket incoming = s.accept( );
	        	new ThreadedDataObjectHandler(incoming).start();
		   	 }
	      } catch (Exception e) {
	    	  System.out.println(e);
	      }  
	}
}

class ThreadedDataObjectHandler extends Thread {
	DataObject myObject = null;
	private Socket incoming;
	   
	public ThreadedDataObjectHandler(Socket i){
		incoming = i;
	}
	
	public void run() {
		try {
			ObjectOutputStream myOutputStream =	new ObjectOutputStream(incoming.getOutputStream());
			ObjectInputStream myInputStream = new ObjectInputStream(incoming.getInputStream());

			myObject = (DataObject)myInputStream.readObject();
			String message = myObject.getMessage();
			String[] msgStrArr = message.split("~");
			
			if ("login".equals(msgStrArr[0])) {
				String userGroup = validateUser(message);
				myObject.setMessage(userGroup);
			} else if ("search".equals(msgStrArr[0]) || "filteredSearch".equals(msgStrArr[0]) || "update".equals(msgStrArr[0]) || "filteredUpdate".equals(msgStrArr[0])) {
				String memberDetailsStr = loadAllMembers(message);
				myObject.setMessage(memberDetailsStr);
			} else if ("add".equals(msgStrArr[0])) {
				String statusMessage = addNewMember(message);
				myObject.setMessage(statusMessage);
			} else if ("Update".equals(msgStrArr[0])) {
				String statusMessage = UpdateMembers(message);
				myObject.setMessage(statusMessage);
			} else if ("Delete".equals(msgStrArr[0])) {
				String statusMessage = DeleteMembers(message);
				myObject.setMessage(statusMessage);
			} else if ("myAccount".equals(msgStrArr[0])) {
				String statusMessage = LoadUserData(message);
				myObject.setMessage(statusMessage);
			} else if ("editMyAccount".equals(msgStrArr[0])) {
				String statusMessage = EditUserAccount(message);
				myObject.setMessage(statusMessage);
			}
			
			myOutputStream.writeObject(myObject);
			myOutputStream.close();
			myInputStream.close();
	
		} catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * This method validates the user and returns the user group
	 * @param message
	 * @return -1 as user group if not a valid user
	 */
	public static String validateUser(String message) {
		String[] strArr = message.split("~");
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String user = "";
		
		String query = "select groupid, memberid from MEMBERS where USERNAME = ? and PASSWORD = ?";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, strArr[1]);
			ps.setString(2, strArr[2]);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				user = rs.getInt(1) + "~" + rs.getInt(2);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException in validateUser in Server " + sqle);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in validateUser " + sql);
			}
		}
		return user;
	}
	
	/**
	 * This method loads data of the logged in user
	 * @param message
	 * @return
	 */
	public static String LoadUserData(String message) {
		Connection conn = getConnection();
		ResultSet rs = null;
		Statement statement = null;
		
		String[] msgStrArr = message.split("~");
		String userDetails = "";
		
		try {
			String query = "select memberfname, memberlname, address, contactnumber, emailId, username, password from members where memberid = " + msgStrArr[1];

			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				userDetails = rs.getString(1) + "~" + rs.getString(2); 
				String addr = rs.getString(3);
				
				String[] addrArr = addr.split(",");
				for (int  i = 0; i < addrArr.length; i++) {
					userDetails = userDetails +  "~" + addrArr[i].trim();
				}
				
				userDetails = userDetails + "~" + rs.getString(4) + "~" + rs.getString(5) + "~" + rs.getString(6) + "~" + rs.getString(7);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException in LoadUserData in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in LoadUserData " + sql);
			}
		}
		return userDetails;
	}
	
	/**
	 * This method loads details of all the members
	 * @param message
	 * @return
	 */
	public static String loadAllMembers(String message) {
		Connection conn = getConnection();
		ResultSet rs = null;
		Statement statement = null;
		
		List<Object[]> rsList = new ArrayList<Object[]>();
		String[] msgStrArr = message.split("~");
		
		try {
			String query = "select memberid, memberfname, memberlname, address, contactnumber, emailId, username, groupId from members";
			if ("filteredSearch".equals(msgStrArr[0]) || "filteredUpdate".equals(msgStrArr[0])) {
				query = query + " where " + msgStrArr[1];
			}
			query = query + " order by memberid ";
			statement = conn.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {
				Object[] objArr = new Object[9];
				int  i = 0;
				String role = "Member";
				
				if ("update".equals(msgStrArr[0]) || "filteredUpdate".equals(msgStrArr[0])) {
					objArr[i] = Boolean.FALSE;
					i++;
				}
				objArr[i++] = rs.getString(1);
				objArr[i++] = rs.getString(2);
				objArr[i++] = rs.getString(3);
				objArr[i++] = rs.getString(4);
				objArr[i++] = rs.getString(5);
				objArr[i++] = rs.getString(6);
				objArr[i++] = rs.getString(7);
				if (Integer.parseInt(rs.getString(8)) == 1) {
					role = "Admin";
				}
				objArr[i++] = role;
				rsList.add(objArr);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException in loadAllMembers in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in loadAllMembers " + sql);
			}
		}
		Gson gson = new Gson();
		Object[][] obj = convertToArr(rsList);
		return gson.toJson(obj);
	}
	
	private static Object[][] convertToArr(List<Object[]> rsList) {
		Object[][] searchDetails = new Object[rsList.size()][];
		int  index = 0;
		
		for (int i = 0; i < rsList.size(); i++) {
			searchDetails[index] = rsList.get(i);
			index++;
		}
		return searchDetails;
	}
	
	/**
	 * This method add new member to the database
	 * @return
	 */
	public static String addNewMember(String inputMessage) {
		String status = "";
		String[] strArr = inputMessage.split("~");
		Connection conn = getConnection();
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement ps = null;
		Statement statement = null;
		Statement statement1 = null;
		int memberId = -1;
		int rowsUpdated = 0;
		boolean duplicate = false;
		
		String getUserId = "select max(memberId) from members";
		String getUserName = "select MEMBERID from members where USERNAME = '" + strArr[7] + "'";
		String insertUser = "insert into members (MEMBERID, MEMBERFNAME, MEMBERLNAME, ADDRESS, CONTACTNUMBER, GROUPID, EMAILID, USERNAME, PASSWORD) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			statement1 = conn.createStatement();
			rs1 = statement1.executeQuery(getUserName);
			
			while (rs1.next()) {
				duplicate = true;
			}
			
			if (!duplicate) {
				statement = conn.createStatement();
				rs = statement.executeQuery(getUserId);
				
				while (rs.next()) {
					memberId = rs.getInt(1);
				}
				
				ps = conn.prepareStatement(insertUser);
				ps.setInt(1, memberId + 1);
				ps.setString(2, strArr[1]);
				ps.setString(3, strArr[2]);
				ps.setString(4, strArr[3]);
				ps.setInt(5, Integer.parseInt(strArr[4]));
				ps.setInt(6, Integer.parseInt(strArr[5]));
				ps.setString(7, strArr[6]);
				ps.setString(8, strArr[7]);
				ps.setString(9, strArr[8]);
				
				rowsUpdated = ps.executeUpdate();
				if (rowsUpdated > 0) {
					status = "success";
				} else {
					status = "failure";
				}
			} else {
				status = "duplicate";
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException in addNewMember in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (statement1 != null) {
					statement1.close();
				}
				if (ps != null) {
					ps.close();
				} if (rs1 != null){
					rs1.close();
				}
				if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in addNewMember " + sql);
			}
		}
		return status;
	}
	
	/**
	 * This method updates the members
	 * @param inputMessage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String UpdateMembers(String inputMessage) {
		String status = "";
		String[] strArr = inputMessage.split("~");
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement statement = null;
		
		Gson gson = new Gson();
		ArrayList<ArrayList<Object>> rowData = gson.fromJson(strArr[1], ArrayList.class);
		
		String updateUser = "update MEMBERS set MEMBERFNAME = ?, MEMBERLNAME = ?, ADDRESS = ?, CONTACTNUMBER = ?, EMAILID = ?, USERNAME = ?, GROUPID = ? WHERE MEMBERID = ? ";
		try {
			statement = conn.createStatement();
			ps = conn.prepareStatement(updateUser);
			
			for (int  i = 0; i < rowData.size(); i++) {
				ArrayList<Object> row = rowData.get(i);
				ps.setString(1, row.get(1).toString());
				ps.setString(2, row.get(2).toString());
				ps.setString(3, row.get(3).toString());
				ps.setInt(4, Integer.parseInt(row.get(4).toString()));
				ps.setString(5, row.get(5).toString());
				ps.setString(6, row.get(6).toString());
				
				int role = 2;
				if ("admin".equalsIgnoreCase(row.get(7).toString())) {
					role = 1;
				}
				ps.setInt(7, role);
				ps.setInt(8, Integer.parseInt(row.get(0).toString()));
				
				ps.executeUpdate();
			}
			status = "success";
		} catch (SQLException sqle) {
			status = "failure";
			System.out.println("SQLException in UpdateMembers in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (ps != null) {
					ps.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in UpdateMembers " + sql);
			}
		}
		return status;
	}
	
	/**
	 * This method updates the logged in user data
	 * @param inputMessage
	 * @return
	 */
	public static String EditUserAccount(String inputMessage) {
		String status = "";
		String[] strArr = inputMessage.split("~");
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement statement = null;
		
		String updateUser = "update MEMBERS set MEMBERFNAME = ?, MEMBERLNAME = ?, ADDRESS = ?, CONTACTNUMBER = ?, EMAILID = ?, PASSWORD  = ? WHERE MEMBERID = ? ";
		try {
			statement = conn.createStatement();
			ps = conn.prepareStatement(updateUser);
			
			ps.setString(1, strArr[1]);
			ps.setString(2, strArr[2]);
			ps.setString(3, strArr[3]);
			ps.setInt(4, Integer.parseInt(strArr[4]));
			ps.setString(5, strArr[5]);
			ps.setString(6, strArr[6]);
			ps.setInt(7, Integer.parseInt(strArr[7]));
				
			ps.executeUpdate();
			status = "success";
		} catch (SQLException sqle) {
			status = "failure";
			System.out.println("SQLException in EditUserAccount in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (ps != null) {
					ps.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in EditUserAccount " + sql);
			}
		}
		
		return status;
	}
	
	/**
	 * This method deletes the members
	 * @param inputMessage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String DeleteMembers(String inputMessage) {
		String status = "";
		String[] strArr = inputMessage.split("~");
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Statement statement = null;
		
		Gson gson = new Gson();
		ArrayList<String> rowData = gson.fromJson(strArr[1], ArrayList.class);
		
		String deleteUser = "delete from MEMBERS WHERE MEMBERID = ? ";
		try {
			statement = conn.createStatement();
			ps = conn.prepareStatement(deleteUser);
			
			for (int  i = 0; i < rowData.size(); i++) {
				ps.setInt(1, Integer.parseInt(rowData.get(i).toString()));
				ps.executeUpdate();
			}
			status = "success";
		} catch (SQLException sqle) {
			status = "failure";
			System.out.println("SQLException in DeleteMembers in Server " + sqle);
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (ps != null) {
					ps.close();
				} if (rs != null){
					rs.close();
				} if (conn != null) {
					conn.close();
				}
			} catch (SQLException sql) {
				System.out.println("SQLException while closing connection in DeleteMembers " + sql);
			}
		}
		return status;
	}
	
	/**
	 * This method creates a connection to DB
	 * @return
	 */
	private static Connection getConnection (){ 
		Connection conn = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    String url = <<CONNECTION STRING>>;
		    conn = DriverManager.getConnection (url, <<USERNAME>>, <<PASSWORD>>);
		    
		} catch (SQLException sqle) {
			System.out.println("SQLException in getConnection in Server " + sqle);
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException in getConnection in Server " + cnfe);
		}
		return conn;
	  }
}
