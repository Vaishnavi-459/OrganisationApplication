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

public class Experience {
	public int expid;
	public String organisationName;
	public String designation;
	public int exp;

	static Connection con = null;
	static ResultSet rs = null;
	static PreparedStatement stmt = null;

	//static Logger logger = Logger.getLogger(Experience.class.getName());
	static Logger logger = Logger.getLogger(MainApplication.class.getName());

	public static void setInsertExperience() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			Scanner sc1 = new Scanner(System.in);
			System.out.println("Please enter Experience Details :");
			System.out.println();
			System.out.print("enter experience id :");
			int expid = sc.nextInt();
			System.out.print("Enter organisationName :");
			String organisationName = sc1.nextLine();
			System.out.print("Enter Designation :");
			String designation = sc1.nextLine();
			System.out.print("Enter employee experience :");
			int exp = sc.nextInt();

			stmt = con.prepareStatement("insert into experience values(?,?,?,?)");
			stmt.setInt(1, expid);
			stmt.setString(2, organisationName);
			stmt.setString(3, designation);
			stmt.setInt(4, exp);
			int n = stmt.executeUpdate();
			if (n != 0) {
				logger.info("Values Inserted successfully in experience");
			}
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

	public static void getExperienceInsertDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			String sql = "select * from experience";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery(sql);

			ResultSetMetaData resultSetMetadata = rs.getMetaData();
			int columnCount = resultSetMetadata.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(resultSetMetadata.getColumnName(i) + "  ");
			}
			while (rs.next()) {
				System.out.printf("\n%d", rs.getInt(1));
				System.out.printf("%13s\t", rs.getString(2));
				System.out.printf("%14s", rs.getString(3));
				System.out.printf("%8d", rs.getInt(4));
			}
			System.out.println();

			while (rs.next()) {
				System.out.println(
						rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getInt(4));
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
