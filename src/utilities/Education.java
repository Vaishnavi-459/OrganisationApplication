package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientException;
import java.util.Scanner;
import java.util.logging.Logger;

import mainPackage.MainApplication;
import utilities.JdbcConnection;

public class Education {
	public int eduid;
	public String course;
	public String university;
	public String place;
	public int marks;
	public int yop;
	public String department;

	//static Logger logger = Logger.getLogger(Education.class.getName());
	static Logger logger = Logger.getLogger(MainApplication.class.getName());

	public static void setEducationInsertDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			Scanner sc1 = new Scanner(System.in);
			System.out.print("enter education id :");
			int eduid = sc.nextInt();
			System.out.print("enter course :");
			String course = sc1.nextLine();
			System.out.print("enter university :");
			String university = sc1.nextLine();
			System.out.print("enter place :");
			String place = sc1.nextLine();
			System.out.print("enter marks :");
			int marks = sc.nextInt();
			System.out.print("enter year of passing :");
			int yop = sc.nextInt();
			System.out.println("enter department :");
			String department = sc1.nextLine();

			stmt = con.prepareStatement("insert into education values(?,?,?,?,?,?,?)");
			stmt.setInt(1, eduid);
			stmt.setString(2, course);
			stmt.setString(3, university);
			stmt.setString(4, place);
			stmt.setInt(5, marks);
			stmt.setInt(6, yop);
			stmt.setString(7, department);
			int n = stmt.executeUpdate();
			if (n != 0)
				logger.info("Values Inserted successfully in education");
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

	public static void getEducationInsertdetails() {
		Connection con = JdbcConnection.getConnection();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			String sql = "select * from education";
			stmt = con.prepareStatement(sql);
			rs = stmt.executeQuery(sql);

			ResultSetMetaData resultSetMetadata = rs.getMetaData();
			int columnCount = resultSetMetadata.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(resultSetMetadata.getColumnName(i) + "  ");
			}
			while (rs.next()) {
				System.out.printf("\n%d", rs.getInt(1));
				System.out.printf("%10s", rs.getString(2));
				System.out.printf("%10s", rs.getString(3));
				System.out.printf("%12s", rs.getString(4));
				System.out.printf("%5d", rs.getInt(5));
				System.out.printf("%7d", rs.getInt(6));
				System.out.printf("%7s", rs.getString(7));
			}
			System.out.println();

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getInt(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7));
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