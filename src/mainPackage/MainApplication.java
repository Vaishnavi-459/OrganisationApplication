package mainPackage;

import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.log4j.BasicConfigurator;

import employeePackage.Employee;
import StudentPackage.Students;
import utilities.*;

public class MainApplication {
	static Logger logger = Logger.getLogger(MainApplication.class.getName());
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Scanner sc = new Scanner(System.in);
		Employee employee = new Employee();
		Students students = new Students();

		int choice;

		do {
			System.out
					.println("Main-Menu\n" + "enter 1 to enter OR view OR update OR delete OR search student details\n"
							+ "enter 2 to enter OR view OR update OR delete OR search employee details\n"
							+ "enter 3 to close the application\n");
			System.out.println("please enter your choice");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				int option;
				do {
					System.out.println("Student-Menu");
					System.out.println("enter 1  to  view  students details :");

					System.out.println("enter 2 to enter students details :");
					System.out.println("enter 3 to enter students address details :");
					System.out.println("enter 4 to enter students education details :");

					System.out.println("enter 5 to update students details:");
					System.out.println("enter 6 to delete students details");
					System.out.println("enter 7 to search students details");
					System.out.println("Please enter your choice:");
					option = sc.nextInt();
					switch (option) {
					case 1:
						logger.info("view  student details :");
						try {
							students.getStudentInsertDetails();
						} catch (Exception e) {
							e.printStackTrace();
							//logger.error("invalid input");
						}
						break;
					case 2:
						System.out.println("enter student details :");
						System.out.println("enter number of students:");
						int n = sc.nextInt();
						for (int i = 0; i < n; i++) {
							try {
								students.setStudentDetails();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						break;
					case 3:
						System.out.println(" enter student address details :");
						Address.setInsertAddress();
						break;
					case 4:
						System.out.println("enter student education details :");
						Education.setEducationInsertDetails();
						break;
					case 5:
						System.out.println("To update student details :");
						Students.getStdUpdateDetails();
						break;
					case 6:
						System.out.println("To delete student details");
						Students.getRemainingStudentDetails();
						break;
					case 7:
						System.out.println("To search student details");
						Students.getStudentsearchDetails();
						break;
					}
				} while (option <= 7);
				break;
			case 2:
				int option1;
				do {
					System.out.println("Employee-Menu");
					System.out.println("enter 1  to  view  Employee details :");
					System.out.println("enter 2 to enter  Employee  details :");
					System.out.println("enter 3 to enter  Employee  address details :");
					System.out.println("enter 4 to enter  Employee  education details :");
					System.out.println("enter 5 to enter  Employee  experience details :");
					System.out.println("enter 6 to update  Employee  details:");
					System.out.println("enter 7 to delete  Employee  details");
					System.out.println("enter 8 to search employee details");

					System.out.println("Please enter your choice:");
					option1 = sc.nextInt();
					switch (option1) {
					case 1:
						logger.info("view  Employee details :");
						System.out.println("Employee details:");
						try {
							employee.getEmployeeInsertDetails();
						} catch (Exception e2) {
							System.out.println(e2);
						}
						break;
					case 2:
						System.out.println("enter Employee  details :");
						int n1 = sc.nextInt();
						for (int i = 0; i < n1; i++) {
							try {
								employee.setEmployeeDetails();
								employee.calculateSalary();
							} catch (Exception se) {
								System.out.println("enter valid option");
							}
						}
						break;
					case 3:
						System.out.println(" enter Employee address details :");
						Address.setInsertAddress();
						break;
					case 4:
						System.out.println("enter Employee education details :");
						Education.setEducationInsertDetails();
						break;
					case 5:
						System.out.println("enter  Employee  experience details :");
						Experience.setInsertExperience();
						break;

					case 6:
						System.out.println("To update Employee details:");
						Employee.getEmpUpdateDetails();
						break;
					case 7:
						System.out.println("To delete student details");
						Employee.getRemainingEmpDetails();
						break;
					case 8:
						Employee.getEmployeesearchDetails();
						break;
					}
				} while (option1 <= 8);
				break;
			case 3:
				System.out.println("whole application completed");
				break;
			default:
				System.out.println("Exit");
			}
		} while (choice < 4 && choice > 0);

	}
}
