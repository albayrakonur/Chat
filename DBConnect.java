import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DBConnect {

	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DBConnect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://146.185.162.54/chat", "root", "Qwerty.2580");
			st = (Statement) con.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("hata.");
			System.exit(0);
		}

	}

	public void getData() {

		try {

			String query = "select * from user";
			rs = st.executeQuery(query);
			System.out.println("Record from db");
			while (rs.next()) {
				String name = rs.getString("name");
				String id = rs.getString("id");
				String username = rs.getString("username");
				System.out.println(id + " " + name + " " + username);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean logIn(String id, String passwd) throws SQLException {
		String query = "select * from user";
		rs = st.executeQuery(query);
		while (rs.next()) {
			String username = rs.getString("username");
			String pw = rs.getString("password");
			if (id.equals(username) && passwd.equals(pw)) {
				return true;
			}
		}

		return false;

	}

	public void addMessage(String username, String message) {
		String query = "INSERT INTO chat " + "VALUES (null, '" + username + "', '" + message + "')";
		;
		try {
			st.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getMessage() throws SQLException {
		String chatHistory = "";
		String query = "select * from chat";
		rs = st.executeQuery(query);
		while (rs.next()) {
			String name = rs.getString("name");
			String message = rs.getString("message");
			chatHistory += name + ": " + message + "\n";			
			
		}
		return chatHistory;
	}

}
