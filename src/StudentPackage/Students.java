package StudentPackage;

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
import utilities.Address;
import utilities.Education;

public class Students {
	public int stdid;
	public String stdname;
	public String stddob;
	public int stdpercentage;
	public int stdphy, stdche, stdmath;
	public int addressid;
	public int eduid;

	static Education studentEducation = new Education();
	static Address studentAddress = new Address();
	//static Logger logger = Logger.getLogger(Students.class.getName());
	static Logger logger = Logger.getLogger(MainApplication.class.getName());
	
	public void setStudentDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			Scanner sc1 = new Scanner(System.in);
			System.out.print("enter student id no :");
			int stdid = sc.nextInt();
			System.out.print("enter Student name :");
			String stdname = sc1.nextLine();
			System.out.print("enter student date of birth :");
			String stddob = sc1.nextLine();
			System.out.print("enter   percentage :");
			int stdpercentage = sc.nextInt();
			System.out.print("enter  physics marks :");
			int stdphy = sc.nextInt();
			System.out.print("enter  chemistry marks :");
			int stdche = sc.nextInt();
			System.out.print("enter  maths marks:");
			int stdmath = sc.nextInt();
			System.out.print("enter  address id:");
			int addressid = sc.nextInt();
			System.out.print("enter  education id:");
			int eduid = sc.nextInt();

			System.out.println(" Enter Student Education details :");
			Education.setEducationInsertDetails();

			System.out.println("Student Address Details :");
			Address.setInsertAddress();
			System.out.println();

			stmt = con.prepareStatement("insert into students values(?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, stdid);
			stmt.setString(2, stdname);
			stmt.setString(3, stddob);
			stmt.setInt(4, stdpercentage);
			stmt.setInt(5, stdphy);
			stmt.setInt(6, stdche);
			stmt.setInt(7, stdmath);
			stmt.setInt(8, addressid);
			stmt.setInt(9, eduid);
			int n = stmt.executeUpdate();
			if (n != 0) {
				logger.info("Values Inserted successfully in students");
			}
				} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println(e.getMessage());
		}finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					con = null;
				}

			}
		}
	}

	public void getStudentDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from students";
			try {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery(sql);
			} catch (Exception e) {
				System.out.println(e);
			}
			ResultSetMetaData resultSetMetadata = rs.getMetaData();
			int columnCount = resultSetMetadata.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(resultSetMetadata.getColumnName(i) + "   ");
			}
			while (rs.next()) {
				System.out.printf("\n%d", rs.getInt(1));
				System.out.printf("%10s\t", rs.getString(2));
				System.out.printf("%10s", rs.getString(3));
				System.out.printf("%10d", rs.getInt(4));
				System.out.printf("%10d", rs.getInt(5));
				System.out.printf("%10d", rs.getInt(6));
				System.out.printf("%10d", rs.getInt(7));
				System.out.printf("%10d", rs.getInt(8));
				System.out.printf("%10d", rs.getInt(9));
				System.out.printf("%10d", rs.getInt(10));
			}
			System.out.println();

			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
						+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7) + "\t\t"
						+ rs.getInt(8) + "\t\t" + rs.getInt(9) + "\t\t" + rs.getInt(10));
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					con = null;
				}

			}
		}
	}

	public void getStudentInsertDetails() throws Exception {
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("enter 1 to  view complete students details :");
			System.out.println("enter 2 to view  students  address details:");
			System.out.println("enter 3 to view  students  education details:");
			System.out.println("Please enter your choice:");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("view complete Students details :");
				String sql = "select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from students s left join address a on s.addressid=a.addressid left  join education edu on s.eduid=edu.eduid";
				try {
					stmt = con.prepareStatement(sql);
					rs = stmt.executeQuery(sql);

					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%10s", rs.getString(3));
						System.out.printf("%10d", rs.getInt(4));
						System.out.printf("%10d", rs.getInt(5));
						System.out.printf("%10d", rs.getInt(6));
						System.out.printf("%10d", rs.getInt(7));

						System.out.printf("%14s\t", rs.getString(8));
						System.out.printf("%12s", rs.getString(9));
						System.out.printf("%12s", rs.getString(10));
						System.out.printf("%12s", rs.getString(11));
						System.out.printf("%12s", rs.getString(12));
						System.out.printf("%12s", rs.getString(13));
						System.out.printf("%12d", rs.getInt(14));

						System.out.printf("%10s", rs.getString(15));
						System.out.printf("%10s", rs.getString(16));
						System.out.printf("%12s", rs.getString(17));
						System.out.printf("%5d", rs.getInt(18));
						System.out.printf("%7d", rs.getInt(19));
						System.out.printf("%7s", rs.getString(20));

					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7)

								+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
								+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
								+ "\t\t" + rs.getInt(14)

								+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
								+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 2:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("view  students  address details:");
				String sqlsa = "select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin from students s left join address a on s.addressid=a.addressid";
				try {
					stmt = con.prepareStatement(sqlsa);
					rs = stmt.executeQuery(sqlsa);

					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%10s", rs.getString(3));
						System.out.printf("%10d", rs.getInt(4));
						System.out.printf("%10d", rs.getInt(5));
						System.out.printf("%10d", rs.getInt(6));
						System.out.printf("%10d", rs.getInt(7));

						System.out.printf("%14s\t", rs.getString(8));
						System.out.printf("%12s", rs.getString(9));
						System.out.printf("%12s", rs.getString(10));
						System.out.printf("%12s", rs.getString(11));
						System.out.printf("%12s", rs.getString(12));
						System.out.printf("%12s", rs.getString(13));
						System.out.printf("%12d", rs.getInt(14));

					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7)

								+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
								+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
								+ "\t\t" + rs.getInt(14));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 3:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("view  students  education details:");
				String sqlsedu = "select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from students s left  join education edu on s.eduid=edu.eduid";
				try {
					stmt = con.prepareStatement(sqlsedu);
					rs = stmt.executeQuery(sqlsedu);

					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%10s", rs.getString(3));
						System.out.printf("%10d", rs.getInt(4));
						System.out.printf("%10d", rs.getInt(5));
						System.out.printf("%10d", rs.getInt(6));
						System.out.printf("%10d", rs.getInt(7));

						System.out.printf("%10s", rs.getString(8));
						System.out.printf("%10s", rs.getString(9));
						System.out.printf("%12s", rs.getString(10));
						System.out.printf("%5d", rs.getInt(11));
						System.out.printf("%7d", rs.getInt(12));
						System.out.printf("%7s", rs.getString(13));

					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7)

								+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
								+ "\t\t" + rs.getInt(11) + "\t\t" + rs.getInt(12) + "\t\t" + rs.getString(13));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			}
		} while (choice <= 3);

	}

	public static void getStdUpdateDetails() throws Exception {
		Scanner scanner = new Scanner(System.in);
		int u;

		do {
			System.out.println("1.update student details");
			System.out.println("2.update student address details");
			System.out.println("3.update student education details");
			System.out.println("enter ur choice to update data in students or studentAddress/ studentEducation");
			u = scanner.nextInt();
			switch (u) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("update student details");
				String sql = "update students set stdname=?,stddob=?, stdpercentage=?, stdphy =?,stdche=?,stdmath=? where stdid=?";
				String sql1 = "select * from students";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					PreparedStatement ps1 = con.prepareStatement(sql1);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					System.out.println("enter  student name");
					ps.setString(1, sc.nextLine());
					System.out.println("enter  student dateOfBirth");
					ps.setString(2, sc1.nextLine());
					System.out.println("enter student percentage");
					ps.setInt(3, sc.nextInt());
					System.out.println("enter student physics marks");
					ps.setInt(4, sc.nextInt());
					System.out.println(" enter student chemistry marks");
					ps.setInt(5, sc.nextInt());
					System.out.println(" enter student maths marks");
					ps.setInt(6, sc.nextInt());
					System.out.println("enter  student id");
					ps.setInt(7, sc.nextInt());
					int update = ps.executeUpdate();
					logger.info("Number of updated records in students:" + update);

					 rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%10s", rs.getString(3));
						System.out.printf("%10d", rs.getInt(4));
						System.out.printf("%10d", rs.getInt(5));
						System.out.printf("%10d", rs.getInt(6));
						System.out.printf("%10d", rs.getInt(7));
						System.out.printf("%10d", rs.getInt(8));
						System.out.printf("%10d", rs.getInt(9));

					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7)
								+ "\t\t" + rs.getInt(8) + "\t\t" + rs.getInt(9));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 2:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("update student address details");
				String sqlua = "update address set houseNo=?,street=?,locality=?, area=?, state =?,country =?,pin=? where addressid=?";
				String sql1ua = "select * from address";
				try {
					PreparedStatement ps = con.prepareStatement(sqlua);
					PreparedStatement ps1 = con.prepareStatement(sql1ua);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);

					System.out.println("enter  houseNo");
					ps.setString(1, sc1.nextLine());
					System.out.println("enter  street");
					ps.setString(2, sc1.nextLine());
					System.out.println("enter locality");
					ps.setString(3, sc1.nextLine());
					System.out.println("enter area");
					ps.setString(4, sc1.nextLine());
					System.out.println(" enter state");
					ps.setString(5, sc1.nextLine());
					System.out.println(" enter country");
					ps.setString(6, sc1.nextLine());
					System.out.println("enter pin");
					ps.setInt(7, sc.nextInt());
					System.out.println("enter  address id");
					ps.setInt(8, sc.nextInt());
					int update = ps.executeUpdate();

					logger.info("Number of updated records in  student address:" + update);

				    rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
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
								+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t"
								+ rs.getString(7) + "\t" + rs.getInt(8));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}
					}
				}
				break;
			case 3:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("update student education details");
				String sqluedu = "update education set course=?,university=?, place=?, marks =?,yop =?,department=? where eduid=?";
				String sql1uedu = "select * from education";
				try {
					PreparedStatement ps = con.prepareStatement(sqluedu);
					PreparedStatement ps1 = con.prepareStatement(sql1uedu);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					System.out.println("enter  course");
					ps.setString(1, sc1.nextLine());
					System.out.println("enter university");
					ps.setString(2, sc1.nextLine());
					System.out.println("enter place");
					ps.setString(3, sc1.nextLine());
					System.out.println("enter marks");
					ps.setInt(4, sc.nextInt());
					System.out.println(" enter yop");
					ps.setInt(5, sc.nextInt());
					System.out.println(" enter department");
					ps.setString(6, sc1.nextLine());
					System.out.println("enter  education id");
					ps.setInt(7, sc.nextInt());
					int update = ps.executeUpdate();
					logger.info("Number of updated records in student education:" + update);

					 rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
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
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
			}
			break;
		} while (u <= 3);
	}

	public static void getRemainingStudentDetails() throws Exception {
		int d;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("1.delete student details");
			System.out.println("2.delete student address details");
			System.out.println("3.delete student education details");
			System.out.println("enter ur choice to delete data in students or studentAddress/ studentEducation");
			d = scanner.nextInt();
			switch (d) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("delete student deatils");
				String sql = "delete from students where stdid=?";
				String sql1 = "select * from students";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					PreparedStatement ps1 = con.prepareStatement(sql1);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					System.out.println("enter  student id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();

					logger.info("Number of deleted  records in students:" + delete);

					 rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%10s", rs.getString(3));
						System.out.printf("%10d", rs.getInt(4));
						System.out.printf("%10d", rs.getInt(5));
						System.out.printf("%10d", rs.getInt(6));
						System.out.printf("%10d", rs.getInt(7));
						System.out.printf("%10d", rs.getInt(8));
						System.out.printf("%10d", rs.getInt(9));

					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t" + rs.getInt(7)
								+ "\t\t" + rs.getInt(8) + "\t\t" + rs.getInt(9));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				 finally {
						if (con != null) {
							try {
								con.close();
							} catch (SQLException e) {
								con = null;
							}

						}
					}
				break;
			case 2:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("delete student address details");
				String sqlda = "delete from address where addressid=?";
				String sql1da = "select * from address";
				try {
					PreparedStatement ps = con.prepareStatement(sqlda);
					PreparedStatement ps1 = con.prepareStatement(sql1da);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);

					System.out.println("enter  address id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of   deleted records in student address:" + delete);

					 rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
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
								+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t"
								+ rs.getString(7) + "\t" + rs.getInt(8));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 3:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("delete student education details");
				String sqldedu = "delete from education where eduid=?";
				String sql1dedu = "select * from education";
				try {
					PreparedStatement ps = con.prepareStatement(sqldedu);
					PreparedStatement ps1 = con.prepareStatement(sql1dedu);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					System.out.println("enter  education id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of deleted  records in student education:" + delete);

					rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
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
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
				 finally {
						if (con != null) {
							try {
								con.close();
							} catch (SQLException e) {
								con = null;
							}

						}
					}
				break;
			}
		} while (d <= 3);
	}

	public static void getStudentsearchDetails() throws Exception {
		Scanner s = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		try {
			int ch;
			do {
				System.out.println("Enter your choice :");
				System.out.println("1-Student details,who secured the marks > 60 percent" + "\n"
						+ "2-Students details,who secured the marks >90 in Math, Physics and Chemistry subjects" + "\n"
						+ "3-Education details of the Students reside at Madhapur");
				ch = s.nextInt();

				switch (ch) {
				case 1:
					Connection con = JdbcConnection.getConnection();
					PreparedStatement stmt = null;
					ResultSet rs = null;
					logger.info("Student details,who secured the marks > 60 percent");
					String sqlm = " select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from students s left join address a on s.addressid=a.addressid left  join education edu on s.eduid=edu.eduid where edu.marks>60";
					try {
						stmt = con.prepareStatement(sqlm);
						rs = stmt.executeQuery(sqlm);

						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "\t");
						}
						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%10s", rs.getString(3));
							System.out.printf("%10d", rs.getInt(4));
							System.out.printf("%10d", rs.getInt(5));
							System.out.printf("%10d", rs.getInt(6));
							System.out.printf("%10d", rs.getInt(7));

							System.out.printf("%14s\t", rs.getString(8));
							System.out.printf("%12s", rs.getString(9));
							System.out.printf("%12s", rs.getString(10));
							System.out.printf("%12s", rs.getString(11));
							System.out.printf("%12s", rs.getString(12));
							System.out.printf("%12s", rs.getString(13));
							System.out.printf("%12d", rs.getInt(14));

							System.out.printf("%10s", rs.getString(15));
							System.out.printf("%10s", rs.getString(16));
							System.out.printf("%12s", rs.getString(17));
							System.out.printf("%5d", rs.getInt(18));
							System.out.printf("%7d", rs.getInt(19));
							System.out.printf("%7s", rs.getString(20));

						}
						System.out.println();

						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t"
									+ rs.getInt(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					 finally {
							if (con != null) {
								try {
									con.close();
								} catch (SQLException e) {
									con = null;
								}
							}
						}
					break;
				case 2:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info("Students details,who secured the marks >90 in Math, Physics and Chemistry subjects");
					String sqlm3 = "select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from students s left join address a on s.addressid=a.addressid left  join education edu on s.eduid=edu.eduid where s.stdphy>90 and s.stdche>90 and s.stdmath>90;";
					try {
						stmt = con.prepareStatement(sqlm3);
						rs = stmt.executeQuery(sqlm3);

						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "\t");
						}
						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%10s", rs.getString(3));
							System.out.printf("%10d", rs.getInt(4));
							System.out.printf("%10d", rs.getInt(5));
							System.out.printf("%10d", rs.getInt(6));
							System.out.printf("%10d", rs.getInt(7));

							System.out.printf("%14s\t", rs.getString(8));
							System.out.printf("%12s", rs.getString(9));
							System.out.printf("%12s", rs.getString(10));
							System.out.printf("%12s", rs.getString(11));
							System.out.printf("%12s", rs.getString(12));
							System.out.printf("%12s", rs.getString(13));
							System.out.printf("%12d", rs.getInt(14));

							System.out.printf("%10s", rs.getString(15));
							System.out.printf("%10s", rs.getString(16));
							System.out.printf("%12s", rs.getString(17));
							System.out.printf("%5d", rs.getInt(18));
							System.out.printf("%7d", rs.getInt(19));
							System.out.printf("%7s", rs.getString(20));

						}
						System.out.println();

						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t"
									+ rs.getInt(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					finally {
						if (con != null) {
							try {
								con.close();
							} catch (SQLException e) {
								con = null;
							}

						}
					}
					break;
				case 3:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info("Education details of the Students reside at Madhapur");
					String sqll = "select s.stdid,s.stdname,s.stddob,s.stdpercentage,s.stdphy,s.stdche,s.stdmath,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from students s left join address a on s.addressid=a.addressid left join education edu on s.eduid=edu.eduid where a.locality='madhapur'";
					try {
						stmt = con.prepareStatement(sqll);
						rs = stmt.executeQuery(sqll);

						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "\t");
						}
						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%10s", rs.getString(3));
							System.out.printf("%10d", rs.getInt(4));
							System.out.printf("%10d", rs.getInt(5));
							System.out.printf("%10d", rs.getInt(6));
							System.out.printf("%10d", rs.getInt(7));

							System.out.printf("%14s\t", rs.getString(8));
							System.out.printf("%12s", rs.getString(9));
							System.out.printf("%12s", rs.getString(10));
							System.out.printf("%12s", rs.getString(11));
							System.out.printf("%12s", rs.getString(12));
							System.out.printf("%12s", rs.getString(13));
							System.out.printf("%12d", rs.getInt(14));

							System.out.printf("%10s", rs.getString(15));
							System.out.printf("%10s", rs.getString(16));
							System.out.printf("%12s", rs.getString(17));
							System.out.printf("%5d", rs.getInt(18));
							System.out.printf("%7d", rs.getInt(19));
							System.out.printf("%7s", rs.getString(20));

						}
						System.out.println();

						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getInt(5) + "\t\t" + rs.getInt(6) + "\t\t"
									+ rs.getInt(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					 finally {
							if (con != null) {
								try {
									con.close();
								} catch (SQLException e) {
									con = null;
								}

							}
						}
					break;
				default:
					System.out.println("invalid");
					break;
				}
			} while (ch <= 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
