package boundary;

import entity.Student;
import java.util.InputMismatchException;
import java.util.Scanner;
import utility.MessageUI;

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
        System.out.println("1.  Add new Students");
        System.out.println("2.  Remove A Student");
        System.out.println("3.  Amend Student Details");
        System.out.println("4.  List All Students' Details");
        System.out.println("5.  Search Students For Registered Course");
        System.out.println("6.  Register for a Course (main, elective, resit, repeat)");
        System.out.println("7.  Remove a Student from a course (main, elective) Registration");
        System.out.println("8.  Calculate Fee Paid for Registered Courses");
        System.out.println("9.  Filter Students For Courses Based On Criteria");
        System.out.println("10. Student Report");
        System.out.println("11. Registration Report");
        System.out.println("0.  Back");

        int choice = getInputChoice();
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

        int choice = getInputChoice();
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

        int choice = getInputChoice();
        System.out.println();
        return choice;

    }

    public void listAllStudents(String outputStr) {
        System.out.println("\nList of Students:\n" + String.format("%-10s %-25s %-10s %-15s %-20s %-15s\n", "StudentID", "Student Name", "BOD", "Phone No", "Email", "Programme ID") + outputStr);
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
        System.out.println("Payment is rejected, registration failed!");

    }

    public void printRegCourseLabel(String courseID) {

        System.out.println("\n=========================================================================================");
        System.out.println("                     Students that are registered for " + courseID);
        System.out.println("=========================================================================================");
        System.out.printf("%-13s %-20s %-13s %-15s %-20s\n", "Student ID", "Name", "DOB", "Phone No", "Email");

    }

    public void printNotExist() {
        System.out.println("There is no student in this course or this course doesn't exists!");
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
        System.out.print("Enter DOB (eg. 12/02/2003): ");
        String DOB = scanner.nextLine();
        return DOB;
    }

    public String inputPhoneNo() {
        System.out.print("Enter Phone Number (eg. 016-1231123): ");
        String phoneNo = scanner.nextLine();
        return phoneNo;
    }

    public String inputEmail() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        return email;
    }

    public String inputProgrammeID() {

        System.out.print("Enter Programme ID: ");
        return scanner.nextLine();
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
        int amount = getInputChoice();
        System.out.println();
        return amount;
    }

    public double inputAmountTendered() {
        double amount = 0.0;
        boolean validInput = false;

        do {
            try {
                System.out.print("Enter amount tendered: RM ");
                amount = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                validInput = true; // Set validInput to true if input is successfully parsed
            } catch (InputMismatchException e) {
                MessageUI.displayInvalidInput();
                scanner.nextLine(); // Consume the invalid input
            }
        } while (!validInput);

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

    public String inputRegID() {
        System.out.print("Enter the Registraton ID: ");
        return scanner.nextLine();

    }

    public String inputIC() {
        System.out.print("Enter IC: ");
        return scanner.nextLine();
    }

    public void displayFeesCourse() {
        System.out.println("================================================================");
        System.out.println("                Fees Paid For Registered Courses");
        System.out.println("================================================================");
        System.out.printf("%-15s %-35s %-15s\n", "Course ID", "Course Name", "Fees Paid");
    }

    public int getCriteria() {

        System.out.println("\n==============================================================");
        System.out.println("      Filters students for courses based on criteria");
        System.out.println("==============================================================");
        System.out.println("1. Based on Program");
        System.out.println("2. Based on gender (Female)");
        System.out.println("3. Based on gender (Male)");
        System.out.println("0. Back");

        int choice = getInputChoice();;
        System.out.println();
        return choice;

    }

    public int getInputChoice() {
        boolean isInputChoiceValid = false;
        int choice = 0;
        do {
            System.out.print("Enter choice: ");
            try {
                isInputChoiceValid = true;
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                isInputChoiceValid = false;
                MessageUI.displayInvalidInput();
            }
        } while (!isInputChoiceValid);
        return choice;
    }



}
