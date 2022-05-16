package employeePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;
import java.util.logging.Logger;

import mainPackage.MainApplication;
import utilities.Address;
import utilities.Education;
import utilities.Experience;
import utilities.JdbcConnection;

public class Employee {
	public int empid;
	public String empname;
	public double empsalary;
	public int empdoj;
	public String empdob;
	public String empdesignation;
	public double empbonus = 0;

	public int addressid;
	public int eduid;
	public int expid;

	Address empAddress = new Address();
	Experience empExperience = new Experience();
	Education empEducation = new Education();

	Scanner sc = new Scanner(System.in);
	int choice;

	//static Logger logger = Logger.getLogger(Employee.class.getName());
	static Logger logger = Logger.getLogger(MainApplication.class.getName());
	
	public void setEmployeeDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Scanner sc = new Scanner(System.in);
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			System.out.print("Enter Employee ID :");
			int empid = sc.nextInt();
			System.out.print("Enter Employee Name :");
			String empname = sc1.nextLine();
			System.out.print("Enter Employee salary :");
			double empsalary = sc2.nextDouble();
			System.out.print("Enter date of joining year :");
			int empdoj = sc.nextInt();
			System.out.print("Enter dob :");
			String empdob = sc1.nextLine();
			System.out.print("Enter designation :");
			String empdesignation = sc1.nextLine();
			System.out.print("Enter bonus :");
			double empbonus = sc2.nextDouble();
			System.out.print("enter  address id:");
			int addressid = sc.nextInt();
			System.out.print("enter  education id:");
			int eduid = sc.nextInt();
			System.out.print("enter  experience id:");
			int expid = sc.nextInt();

			System.out.println("Enter Employee Education details :");
			Education.setEducationInsertDetails();

			System.out.println("Enter Employee Address details :");
			Address.setInsertAddress();

			System.out.println("Enter Employee Experience details :");
			Experience.setInsertExperience();

			// con = JdbcConnection.getConnection();
			stmt = con.prepareStatement("insert into employee values(?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, empid);
			stmt.setString(2, empname);
			stmt.setDouble(3, empsalary);
			stmt.setInt(4, empdoj);
			stmt.setString(5, empdob);
			stmt.setString(6, empdesignation);
			stmt.setDouble(7, empbonus);

			stmt.setInt(8, addressid);
			stmt.setInt(9, eduid);
			stmt.setInt(10, expid);
			int n = stmt.executeUpdate();
			if (n != 0) {
				logger.info("Values Inserted successfully in employee");
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

	public void calculateSalary() {
		if (empsalary < 10000) {
			empbonus = empsalary * 20 / 100;
		}
	}

	public void getEmployeeDetails() throws Exception {
		Connection con = JdbcConnection.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// con = JdbcConnection.getConnection();
			String sql = "select * from employee";
			try {
				stmt = con.prepareStatement(sql);
				rs = stmt.executeQuery(sql);
			} catch (Exception e) {
				System.out.println(e);
			}
			ResultSetMetaData resultSetMetadata = rs.getMetaData();
			int columnCount = resultSetMetadata.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(resultSetMetadata.getColumnName(i) + "  ");
			}
			while (rs.next()) {
				System.out.printf("\n%d", rs.getInt(1));
				System.out.printf("%10s\t", rs.getString(2));
				System.out.printf("%.3f", rs.getDouble(3));
				System.out.printf("%12s", rs.getString(4));
				System.out.printf("%12s", rs.getString(5));
				System.out.printf("%12s\t", rs.getString(6));
				System.out.printf("%4.2f", rs.getDouble(7));
				System.out.printf("%12d", rs.getInt(8));
				System.out.printf("%12d", rs.getInt(9));
				System.out.printf("%12d", rs.getInt(10));
			}
			System.out.println();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
						+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
						+ rs.getDouble(7) + "\t\t" + rs.getInt(8) + "\t\t" + rs.getInt(9) + "\t\t" + rs.getInt(10));
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

	public void getEmployeeInsertDetails() throws Exception {
		int choice;
		do {
			System.out.println("enter 1 to  view complete Employee details :");

			System.out.println("enter 2 to view  Employee  address details:");
			System.out.println("enter 3 to view  Employee  education details:");
			System.out.println("enter 4 to view  Employee  experience details:");
			System.out.println("Please enter your choice:");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("view complete Employee details :");
				String sql = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department,exp.organisationName,exp.designation,exp.exp from employee e left join address a on e.addressid=a.addressid  left join education edu on e.eduid=edu.eduid  left join experience exp on e.expid=exp.expid";
				try {

					stmt = con.prepareStatement(sql);
					try {
						rs = stmt.executeQuery(sql);

					} catch (Exception e) {
						e.printStackTrace();
					}
					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "  ");
					}

					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));

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

						System.out.printf("%13s\t", rs.getString(21));
						System.out.printf("%14s", rs.getString(22));
						System.out.printf("%8d", rs.getInt(23));
					}
					System.out.println();
					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7)

								+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
								+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
								+ "\t\t" + rs.getInt(14)

								+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
								+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20)

								+ "\t\t" + rs.getString(21) + "\t\t" + rs.getString(22) + "\t\t" + rs.getInt(23));
					}

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
				break;
			case 2:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("view  Employee  address details:");
				String sqlea = " select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin from employee e left join address a on e.addressid=a.addressid";
				try {
					stmt = con.prepareStatement(sqlea);
					try {
						rs = stmt.executeQuery(sqlea);

					} catch (Exception e) {
						System.out.println(e);
					}
					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "  ");
					}

					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));

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
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7)

								+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
								+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
								+ "\t\t" + rs.getInt(14));
					}

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

				break;
			case 3:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("view  Employee  education details:");
				String sqleedu = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from employee e left join education edu on e.eduid=edu.eduid";
				try {
					stmt = con.prepareStatement(sqleedu);
					try {
						rs = stmt.executeQuery(sqleedu);

					} catch (Exception e) {
						System.out.println(e);
					}
					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "  ");
					}

					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));

						System.out.printf("%10s", rs.getString(8));
						System.out.printf("%10s", rs.getString(9));
						System.out.printf("%12s", rs.getString(10));
						System.out.printf("%5d", rs.getInt(11));
						System.out.printf("%7d", rs.getInt(12));
						System.out.printf("%7s", rs.getString(13));

					}
					System.out.println();
					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7) + "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t"
								+ rs.getString(10) + "\t\t" + rs.getInt(11) + "\t\t" + rs.getInt(12) + "\t\t"
								+ rs.getString(13));
					}

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

				break;
			case 4:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("view  Employee  experience details:");
				String sqleexp = " select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,exp.organisationName,exp.exp from employee e left join experience exp on e.expid=exp.expid and e.empdesignation=exp.designation;";
				try {
					stmt = con.prepareStatement(sqleexp);
					try {
						rs = stmt.executeQuery(sqleexp);

					} catch (Exception e) {
						System.out.println(e);
					}
					ResultSetMetaData resultSetMetadata = rs.getMetaData();
					int columnCount = resultSetMetadata.getColumnCount();

					for (int i = 1; i <= columnCount; i++) {
						System.out.print(resultSetMetadata.getColumnName(i) + "  ");
					}

					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));
						System.out.printf("%13s\t", rs.getString(8));
						System.out.printf("%14s", rs.getString(9));

					}
					System.out.println();
					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7) + "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9));
					}

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

				break;
			}
		} while (choice <= 4);
	}

	public static void getEmpUpdateDetails() throws Exception {
		int u;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("1.update Employee details");
			System.out.println("2.update Employee address details");
			System.out.println("3.update Employee education details");
			System.out.println("4.update Employee experience details");
			System.out.println(
					"enter ur choice to update data in employee or employeeAddress/employeeEducation/empExeperience");
			u = scanner.nextInt();
			switch (u) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("update Employee details");
				String sql = "update employee set empname=?,empsalary=?, empdoj=?, empdob =?,empdesignation=?,empbonus=? where empid=?";

				String sql1 = "select * from Employee";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					PreparedStatement ps1 = con.prepareStatement(sql1);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					Scanner sc2 = new Scanner(System.in);
					System.out.println("enter  employee name");
					ps.setString(1, sc1.nextLine());
					System.out.println("enter  salary");
					ps.setDouble(2, sc2.nextDouble());
					System.out.println("enter date of joining");
					ps.setInt(3, sc.nextInt());
					System.out.println("enter emp dob");
					ps.setString(4, sc1.nextLine());
					System.out.println(" enter designation");
					ps.setString(5, sc1.nextLine());
					System.out.println(" enter bonus");
					ps.setDouble(6, sc2.nextDouble());
					System.out.println("enter the employee id");
					ps.setInt(7, sc.nextInt());
					int update = ps.executeUpdate();

					logger.info("Number of updated records in employee:" + update);

					rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));
						System.out.printf("%12d", rs.getInt(8));
						System.out.printf("%12d", rs.getInt(9));
						System.out.printf("%12d", rs.getInt(10));
					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7) + "\t\t" + rs.getInt(8) + "\t\t" + rs.getInt(9) + "\t\t"
								+ rs.getInt(10));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				} finally {
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
				logger.info("update Employee address details");
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

					logger.info("Number of updated records in employee address:" + update);

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
				} finally {
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
				logger.info("update Employee education details");
				String sqluedu = "update education set course=?,university=?, place=?, marks =?,yop =?,department=? where eduid=?";
				String sql1uedu = "select * from education";
				try {
					PreparedStatement ps = con.prepareStatement(sql1uedu);
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
					logger.info("Number of updated records in employee education:" + update);

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
				} finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 4:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("update Employee experience details");
				String sqluexp = "update experience set organisationName=?,designation=?,exp=? where  expid=?";
				String sql1uexp = "select * from experience";
				try {
					PreparedStatement ps = con.prepareStatement(sqluexp);
					PreparedStatement ps1 = con.prepareStatement(sql1uexp);
					Scanner sc = new Scanner(System.in);
					Scanner sc1 = new Scanner(System.in);
					System.out.println("enter  organisationName");
					ps.setString(1, sc1.nextLine());
					System.out.println("enter  designation");
					ps.setString(2, sc1.nextLine());
					System.out.println("enter exp");
					ps.setInt(3, sc.nextInt());
					System.out.println("enter expid");
					ps.setInt(4, sc.nextInt());
					int update = ps.executeUpdate();

					logger.info("Number of updated records in employee experience:" + update);

					rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%13s\t", rs.getString(2));
						System.out.printf("%14s", rs.getString(3));
						System.out.printf("%8d", rs.getInt(4));
					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
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
			break;
		} while (u <= 4);
	}

	public static void getRemainingEmpDetails() throws Exception {
		int d;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("1.delete Employee deatils");
			System.out.println("2.delete Employee address details");
			System.out.println("3.delete Employee education details");
			System.out.println("4.delete Employee experience details");
			System.out.println(
					"enter ur choice to delete data in employee or employeeAddress/employeeEducation/empExeperience");
			d = scanner.nextInt();
			switch (d) {
			case 1:
				Connection con = JdbcConnection.getConnection();
				PreparedStatement stmt = null;
				ResultSet rs = null;
				logger.info("delete Employee details");
				String sql = "delete from employee where empid=?";
				String sql1 = "select * from Employee";
				try {
					PreparedStatement ps = con.prepareStatement(sql);
					PreparedStatement ps1 = con.prepareStatement(sql1);
					Scanner sc = new Scanner(System.in);
					System.out.println("enter the employee id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of deleted records  in employee:" + delete);
					rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%10s\t", rs.getString(2));
						System.out.printf("%.3f", rs.getDouble(3));
						System.out.printf("%12s", rs.getInt(4));
						System.out.printf("%12s", rs.getString(5));
						System.out.printf("%12s\t", rs.getString(6));
						System.out.printf("%4.2f", rs.getDouble(7));
						System.out.printf("%12d", rs.getInt(8));
						System.out.printf("%12d", rs.getInt(9));
						System.out.printf("%12d", rs.getInt(10));
					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3) + "\t\t"
								+ rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t"
								+ rs.getDouble(7) + "\t\t" + rs.getInt(8) + "\t\t" + rs.getInt(9) + "\t\t"
								+ rs.getInt(10));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				} finally {
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
				logger.info("delete Employee address details");
				String sqlda = "delete from address where addressid=?";
				String sql1da = "select * from address";
				try {
					PreparedStatement ps = con.prepareStatement(sqlda);
					PreparedStatement ps1 = con.prepareStatement(sql1da);
					Scanner sc = new Scanner(System.in);
					System.out.println("enter  address id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of  deleted records in employee address:" + delete);

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
				} finally {
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
				logger.info("delete Employee education details");
				String sqldedu = "delete from education where eduid=?";
				String sql1dedu = "select * from education";
				try {
					PreparedStatement ps = con.prepareStatement(sqldedu);
					PreparedStatement ps1 = con.prepareStatement(sql1dedu);
					Scanner sc = new Scanner(System.in);
					System.out.println("enter  education id");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of deleted records in employee education:" + delete);

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
				} finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {
							con = null;
						}

					}
				}
				break;
			case 4:
				con = JdbcConnection.getConnection();
				stmt = null;
				rs = null;
				logger.info("delete Employee experience details");
				String sqldexp = "delete from experience where  expid=?";
				String sql1dexp = "select * from experience";
				try {
					PreparedStatement ps = con.prepareStatement(sqldexp);
					PreparedStatement ps1 = con.prepareStatement(sql1dexp);
					Scanner sc = new Scanner(System.in);
					System.out.println("enter expid");
					ps.setInt(1, sc.nextInt());
					int delete = ps.executeUpdate();
					logger.info("Number of deleted records in employee experience:" + delete);

					rs = ps1.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();
					int colCount = rsmd.getColumnCount();

					for (int i = 1; i <= colCount; i++) {
						System.out.print(rsmd.getColumnLabel(i) + "\t");
					}
					while (rs.next()) {
						System.out.printf("\n%d", rs.getInt(1));
						System.out.printf("%13s\t", rs.getString(2));
						System.out.printf("%14s", rs.getString(3));
						System.out.printf("%8d", rs.getInt(4));
					}
					System.out.println();

					while (rs.next()) {
						System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
								+ rs.getInt(4));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				} finally {
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
		} while (d <= 4);
	}

	public static void getEmployeesearchDetails() throws Exception {
		Scanner s = new Scanner(System.in);
		Scanner sc = new Scanner(System.in);
		try {
			int ch;
			do {
				System.out.println("Enter your choice :");
				System.out.println("1-Employee details whose joining date in between 2019 and 2020" + "\n"
						+ "2-Employee details whose designation  is trainee and salary < 10,000 to provide bonus of 20%"
						+ "\n" + "3-Employee with address details with overall experience of >20 years" + "\n"
						+ "4-Employee details whose experience  > 10 years in the current organization" + "\n"
						+ "5-Employees with address details,  who passed out of the same university.");
				ch = s.nextInt();

				switch (ch) {
				case 1:
					Connection con = JdbcConnection.getConnection();
					PreparedStatement stmt = null;
					ResultSet rs = null;
					logger.info("Employee details whose joining date in between 2019 and 2020");
					String sql = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department,exp.organisationName,exp.designation,exp.exp from employee e left join address a on e.addressid=a.addressid  left join education edu on e.eduid=edu.eduid  left join experience exp on e.expid=exp.expid where e.empdoj between 2019 and 2020";
					try {
						stmt = con.prepareStatement(sql);
						try {
							rs = stmt.executeQuery(sql);

						} catch (Exception e) {
							System.out.println(e);
						}
						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "  ");
						}

						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%.3f", rs.getDouble(3));
							System.out.printf("%12s", rs.getInt(4));
							System.out.printf("%12s", rs.getString(5));
							System.out.printf("%12s\t", rs.getString(6));
							System.out.printf("%4.2f", rs.getDouble(7));

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

							System.out.printf("%13s\t", rs.getString(21));
							System.out.printf("%14s", rs.getString(22));
							System.out.printf("%8d", rs.getInt(23));
						}
						System.out.println();
						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6)
									+ "\t\t" + rs.getDouble(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20)

									+ "\t\t" + rs.getString(21) + "\t\t" + rs.getString(22) + "\t\t" + rs.getInt(23));
						}

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
					break;
				case 2:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info(
							"Employee details whose designation  is trainee and salary < 10,000 to provide bonus of 20%");
					// String sql1 = "select * from employee where
					// empdesignation='trainee' and (empsalary<10000 and
					// empbonus=20)";
					String sqld = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department,exp.organisationName,exp.designation,exp.exp from employee e left join address a on e.addressid=a.addressid  left join education edu on e.eduid=edu.eduid  left join experience exp on e.expid=exp.expid  where e.empdesignation='trainee' and (e.empsalary<10000 and e.empbonus=20)";
					try {
						stmt = con.prepareStatement(sqld);
						try {
							rs = stmt.executeQuery(sqld);

						} catch (Exception e) {
							System.out.println(e);
						}
						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "  ");
						}

						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%.3f", rs.getDouble(3));
							System.out.printf("%12s", rs.getInt(4));
							System.out.printf("%12s", rs.getString(5));
							System.out.printf("%12s\t", rs.getString(6));
							System.out.printf("%4.2f", rs.getDouble(7));

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

							System.out.printf("%13s\t", rs.getString(21));
							System.out.printf("%14s", rs.getString(22));
							System.out.printf("%8d", rs.getInt(23));
						}
						System.out.println();
						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6)
									+ "\t\t" + rs.getDouble(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20)

									+ "\t\t" + rs.getString(21) + "\t\t" + rs.getString(22) + "\t\t" + rs.getInt(23));
						}

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
					break;
				case 3:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info("Employee with address details with overall experience of >20 years");
					String sqloe = " select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,exp.exp from employee e left join address a on e.addressid=a.addressid left join experience exp on e.expid=exp.expid where exp.exp>20";
					try {
						stmt = con.prepareStatement(sqloe);
						try {
							rs = stmt.executeQuery(sqloe);

						} catch (Exception e) {
							System.out.println(e);
						}
						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "  ");
						}

						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%.3f", rs.getDouble(3));
							System.out.printf("%12s", rs.getInt(4));
							System.out.printf("%12s", rs.getString(5));
							System.out.printf("%12s\t", rs.getString(6));
							System.out.printf("%4.2f", rs.getDouble(7));

							System.out.printf("%14s\t", rs.getString(8));
							System.out.printf("%12s", rs.getString(9));
							System.out.printf("%12s", rs.getString(10));
							System.out.printf("%12s", rs.getString(11));
							System.out.printf("%12s", rs.getString(12));
							System.out.printf("%12s", rs.getString(13));
							System.out.printf("%12d", rs.getInt(14));

							System.out.printf("%8d", rs.getInt(15));
						}
						System.out.println();
						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6)
									+ "\t\t" + rs.getDouble(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14) + "\t\t" + rs.getInt(23));
						}

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
					break;
				case 4:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info("Employee details whose experience> 10 years in the current organization");
					String sqlexp = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department,exp.organisationName,exp.designation,exp.exp from employee e left join address a on e.addressid=a.addressid  left join education edu on e.eduid=edu.eduid  left join experience exp on e.expid=exp.expid where exp.exp>10 and exp.organisationName='Ingrain';";
					try {
						stmt = con.prepareStatement(sqlexp);
						try {
							rs = stmt.executeQuery(sqlexp);

						} catch (Exception e) {
							System.out.println(e);
						}
						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "  ");
						}

						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%.3f", rs.getDouble(3));
							System.out.printf("%12s", rs.getInt(4));
							System.out.printf("%12s", rs.getString(5));
							System.out.printf("%12s\t", rs.getString(6));
							System.out.printf("%4.2f", rs.getDouble(7));

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

							System.out.printf("%13s\t", rs.getString(21));
							System.out.printf("%14s", rs.getString(22));
							System.out.printf("%8d", rs.getInt(23));
						}
						System.out.println();
						while (rs.next()) {
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6)
									+ "\t\t" + rs.getDouble(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20)

									+ "\t\t" + rs.getString(21) + "\t\t" + rs.getString(22) + "\t\t" + rs.getInt(23));
						}

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
					break;
				case 5:
					con = JdbcConnection.getConnection();
					stmt = null;
					rs = null;
					logger.info("Employees with address details,  who passed out of the same university");
					String sqledu = "select e.empid,e.empname,e.empsalary,e.empdoj,e.empdob,e.empdesignation,e.empbonus,a.houseno,a.street,a.locality,a.area,a.state,a.country,a.pin,edu.course,edu.university,edu.place,edu.marks,edu.yop,edu.department from employee e left join address a on e.addressid=a.addressid  left join education edu on e.eduid=edu.eduid where edu.university='jntuk'";
					try {
						stmt = con.prepareStatement(sqledu);
						try {
							rs = stmt.executeQuery(sqledu);

						} catch (Exception e) {
							System.out.println(e);
						}
						ResultSetMetaData resultSetMetadata = rs.getMetaData();
						int columnCount = resultSetMetadata.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							System.out.print(resultSetMetadata.getColumnName(i) + "  ");
						}

						while (rs.next()) {
							System.out.printf("\n%d", rs.getInt(1));
							System.out.printf("%10s\t", rs.getString(2));
							System.out.printf("%.3f", rs.getDouble(3));
							System.out.printf("%12s", rs.getInt(4));
							System.out.printf("%12s", rs.getString(5));
							System.out.printf("%12s\t", rs.getString(6));
							System.out.printf("%4.2f", rs.getDouble(7));

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
							System.out.println(rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getDouble(3)
									+ "\t\t" + rs.getInt(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6)
									+ "\t\t" + rs.getDouble(7)

									+ "\t\t" + rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10)
									+ "\t\t" + rs.getString(11) + "\t\t" + rs.getString(12) + "\t\t" + rs.getString(13)
									+ "\t\t" + rs.getInt(14)

									+ "\t\t" + rs.getString(15) + "\t\t" + rs.getString(16) + "\t\t" + rs.getString(17)
									+ "\t\t" + rs.getInt(18) + "\t\t" + rs.getInt(19) + "\t\t" + rs.getString(20));
						}

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
					break;
				default:
					System.out.println("invalid");
					break;
				}

			} while (ch <= 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
