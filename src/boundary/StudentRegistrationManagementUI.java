package boundary;

import entity.Student;
import java.util.Scanner;

/**
 *
 * @author Jason
 */
public class StudentRegistrationManagementUI {

    Scanner scanner = new Scanner(System.in);

    public int getMenuChoice() {
        System.out.println("\n=====================================================================");
        System.out.println("                   Student Registration Management");
        System.out.println("=====================================================================");
        System.out.println("1. Add new Students");
        System.out.println("2. Remove A Student");
        System.out.println("3. Amend Student Details");
        System.out.println("4. List All Students' Details");
        System.out.println("5. Search Student For Registered Course");
        System.out.println("6. Register for a Course (main, elective, resit, repeat)");
        System.out.println("7. Remove a Student from a course (main, elective) Registration");
        System.out.println("8. Calculate Fee Paid for Registered Courses");
        System.out.println("9. Filter Students For Courses Based On Criteria");
        System.out.println("10. Generate Summary Reports");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;
    }

    public int getAmendChoice(String studentID) {
        System.out.println("\n=====================================================================");
        System.out.println("                   Student Details Ammendment");
        System.out.println("=====================================================================");
        System.out.println("Student ID: " + studentID);
        System.out.println("1. Change Name");
        System.out.println("2. Change Date of Birth");
        System.out.println("3. Change Phone Number");
        System.out.println("4. Change Email");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;

    }

    public int getRegChoice(String studentID) {
        System.out.println("\n=====================================================================");
        System.out.println("                  Course Registration for Students");
        System.out.println("=====================================================================");
        System.out.println("Student ID: " + studentID);
        System.out.println("1. Register for a course");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        return choice;

    }

    public void listAllStudents(String outputStr) {
        System.out.println("\nList of Students:\n" + String.format("%-10s %-25s %-10s %-15s %-20s\n", "StudentID", "Student Name", "BOD", "Phone No", "Email") + outputStr);
    }

    public void studentListCriteria(String outputStr) {
        System.out.println("\nStudent List:\n" + outputStr);
    }

    public void printStudentDetails(Student student) {
        System.out.println("Student Details");
        System.out.println("Student ID:" + student.getStudentID());
        System.out.println("Student Name: " + student.getStudentName());
        System.out.println("Student BOD: " + student.getStudentDOB());
        System.out.println("Phone Number: " + student.getPhoneNo());
        System.out.println("Email: " + student.getStudentEmail());
    }

    public void printRejectedPayment() {
        System.out.println("Payment is rejected!");
    }

    public String inputStudentID() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        return studentID;
    }

    public String inputStudentName() {
        System.out.print("Enter Student name: ");
        String name = scanner.nextLine();
        return name;
    }

    public String inputDOB() {
        System.out.print("Enter DOB: ");
        String DOB = scanner.nextLine();
        return DOB;
    }

    public String inputPhoneNo() {
        System.out.print("Enter Phone Number: ");
        String phoneNo = scanner.nextLine();
        return phoneNo;
    }

    public String inputEmail() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        return email;
    }

    public Student inputStudentDetails() {
        String name = inputStudentName();
        String DOB = inputDOB();
        String phoneNo = inputPhoneNo();
        String email = inputEmail();
        return new Student(name, DOB, phoneNo, email);

    }

    public String inputCourseID() {
        System.out.print("Enter Course ID (999 to exit): ");
        return scanner.nextLine();
    }

    public String inputCourseType() {
        System.out.print("Enter Course Type (999 to exit): ");
        return scanner.nextLine();
    }

    public String inputApprove() {
        System.out.print("Approve payment? (Y/N):  ");

        return scanner.nextLine();
    }

    public int inputPaymentOption(double amountToPay) {
        System.out.print("\nTotal: RM" + String.format("%.2f", amountToPay)
                + "\nPayment Options:\n"
                + "1. Card\n"
                + "2. Cash\n");
        System.out.print("Select a Payment Option (1-2): ");
        int amount = scanner.nextInt();
        scanner.nextLine();
        return amount;
    }

    public double inputAmountTendered() {
        System.out.print("\nEnter amount tendered: RM ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        return amount;
    }

    public String inputCardNumber() {
        System.out.print("\nEnter Card Number (16Digits): ");
        return scanner.nextLine();

    }

    public String inputCardHolder() {
        System.out.print("Enter Card Holder Name: ");
        return scanner.nextLine();
    }

    public String inputCardExp() {
        System.out.print("Enter Card Expiry Date eg.(12/30): ");
        return scanner.nextLine();
    }

    public String inputCardCVV() {
        System.out.print("Enter Card CVV: ");
        return scanner.nextLine();
    }

}
