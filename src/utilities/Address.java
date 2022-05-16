package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;
import java.util.logging.Logger;

import mainPackage.MainApplication;
import utilities.JdbcConnection;

public class Address {

	int addressid;
	String houseNo, street, locality, area, state, country;
	int pin;

	//static Logger logger = Logger.getLogger(Address.class.getName());
	static Logger logger = Logger.getLogger(MainApplication.class.getName());

	public static void setInsertAddress() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			Scanner sc1 = new Scanner(System.in);
			System.out.print("enter addressid :");
			int addressid = sc.nextInt();
			System.out.print("enter houseno :");
			String houseNo = sc1.nextLine();
			System.out.print("enter street :");
			String street = sc1.nextLine();
			System.out.print("enter locality :");
			String locality = sc1.nextLine();
			System.out.print("enter area :");
			String area = sc1.nextLine();
			System.out.print("enter state :");
			String state = sc1.nextLine();
			System.out.print("enter country :");
			String country = sc1.nextLine();
			System.out.print("enter pin :");
			int pin = sc.nextInt();

			stmt = con.prepareStatement("insert into address values(?,?,?,?,?,?,?,?)");
			stmt.setInt(1, addressid);
			stmt.setString(2, houseNo);
			stmt.setString(3, street);
			stmt.setString(4, locality);
			stmt.setString(5, area);
			stmt.setString(6, state);
			stmt.setString(7, country);
			stmt.setInt(8, pin);
			int n = stmt.executeUpdate();
			if (n != 0) {
				logger.info("Values Inserted successfully in address");
			}
			con.close();

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					con = null;
				}

			}
		}
	}

	public static void getAddressInsertdetails() {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from address";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery(sql);

			ResultSetMetaData resultSetMetadata = rs.getMetaData();
			int columnCount = resultSetMetadata.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(resultSetMetadata.getColumnName(i) + "   ");
			}
			while (rs.next()) {
				System.out.printf("\n%d", rs.getInt(1));
				System.out.printf("%14s\t", rs.getString(2));
				System.out.printf("%12s", rs.getString(3));
				System.out.printf("%12s", rs.getString(4));
				System.out.printf("%12s", rs.getString(5));
				System.out.printf("%12s", rs.getString(6));
				System.out.printf("%12s", rs.getString(7));
				System.out.printf("%12d", rs.getInt(8));
			}
			System.out.println();

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7)
						+ "\t" + rs.getInt(8));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					con = null;
				}

			}
		}

	}
}
