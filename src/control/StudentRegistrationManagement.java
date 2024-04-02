
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import entity.*;
import adt.ArrayList;
import adt.ArraySet;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import adt.SetInterface;
import boundary.StudentRegistrationManagementUI;
import dao.StudentDAO;
import java.util.Iterator;
import utility.MessageUI;
import dao.StudentInitializer;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Jason
 */
public class StudentRegistrationManagement implements Serializable {

    private ListInterface<Student> studentList = new ArrayList<>();

    private StudentDAO studentDAO = new StudentDAO("students.dat");
    private StudentRegistrationManagementUI studentUI = new StudentRegistrationManagementUI();
    public static int studentEntries;

    public StudentRegistrationManagement() {

        
        studentList = studentDAO.retrieveFromFile();

    }

    public void mainMenu() {
        int choice = 0;
        do {
            choice = studentUI.getMenuChoice();
            switch (choice) {
                case 0:
                    //to exit
                    MessageUI.displayBackMessage();
                    break;
                case 1:
                    studentEntries = studentList.getNumberOfEntries();

                    addStudent();
                    break;
                case 2:

                    removeStudent();
                    break;
                case 3:

                    amendStudent();
                    break;
                case 4:
                    displayStudents();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    register();
                    break;
                case 7:
                    removeFromCourse();
                    break;
                case 8:
                    calFeesRegCourse();
                    break;
                case 9:
                    filterStudents();
                    break;
                case 10:
                    generateReport();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    public void addStudent() {
        Student newStudent = studentUI.inputStudentDetails();
        studentList.add(newStudent);
        studentDAO.saveToFile(studentList);
    }

    public void removeStudent() {
        String studentId = studentUI.inputStudentID();
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            if (student.getStudentID().equals(studentId)) {
                studentList.remove(i);
                System.out.println("Student with ID " + studentId + " removed successfully.");
                studentDAO.saveToFile(studentList);
                return;
            }
        }
        System.out.println("Student with ID " + studentId + " not found.");
    }

    public String getAllStudents() {
        String outputStr = "";
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            outputStr += studentList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    public void displayStudents() {
        if (!studentList.isEmpty()) {
            studentUI.listAllStudents(getAllStudents());
        } else {
            System.out.println("Student list is empty, please add new student to view.");
        }

    }

    public void amendStudent() {
        String studentId = studentUI.inputStudentID();
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            if (student.getStudentID().equals(studentId)) {
                int choice = 0;
                do {
                    choice = studentUI.getAmendChoice(studentId);
                    switch (choice) {
                        case 0:
                            MessageUI.displayBackMessage();
                            break;
                        case 1:
                            String studentName = studentUI.inputStudentName();
                            student.setStudentName(studentName);
                            MessageUI.displayUpdateMessage();
                            break;
                        case 2:
                            String studentDOB = studentUI.inputDOB();
                            student.setStudentDOB(studentDOB);
                            MessageUI.displayUpdateMessage();
                            break;
                        case 3:
                            String phoneNo = studentUI.inputPhoneNo();
                            student.setPhoneNo(phoneNo);
                            MessageUI.displayUpdateMessage();
                            break;
                        case 4:
                            String email = studentUI.inputEmail();
                            student.setStudentEmail(email);
                            MessageUI.displayUpdateMessage();
                            break;
                        default:
                            MessageUI.displayInvalidChoiceMessage();

                    }

                } while (choice != 0);
                studentDAO.saveToFile(studentList);

                return;
            }
        }
        System.out.println("Student with ID " + studentId + " not found.");
    }

    public void searchStudent() {

    }

    public void register() {
        String studentId = studentUI.inputStudentID();
        CourseManagement courseManagement = new CourseManagement();

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            if (student.getStudentID().equals(studentId)) {
                System.out.println("matches!");
                int choice = 0;
                do {
                    choice = studentUI.getRegChoice(studentId);
//                    courseManagement.getCourseMap().containsKey(studentId)
                    switch (choice) {
                        case 0:
                            MessageUI.displayBackMessage();
                            break;
                        case 1:
                            //display courses
                            courseManagement.displayAllCourses();
                            //type courseID and make payment
                            registerProcess();
                            break;
                        default:
                            MessageUI.displayInvalidChoiceMessage();

                    }

                } while (choice != 0);
                studentDAO.saveToFile(studentList);

                return;
            }
        }
        System.out.println("Student with ID " + studentId + " not found.");
    }

    public void registerProcess() {
        CourseManagement courseManagement = new CourseManagement();
        String courseID;
        String type;
        Course course;
        SetInterface<String> courseStatuses = new ArraySet<>();
        boolean isValidType;
        Payment payment;

        //remember to use return at the last point
        do {
            courseID = studentUI.inputCourseID();
            if (courseManagement.getCourseMap().containsKey(courseID)) {
                course = courseManagement.getCourseMap().get(courseID);
                courseStatuses = courseManagement.getCourseMap().get(courseID).getStatus();
                // Get an iterator for the course statuses
                Iterator<String> iterator = courseStatuses.getIterator();

                do {
                    type = studentUI.inputCourseType();

                    // Validate the type against the course statuses
                    isValidType = false;
                    while (iterator.hasNext()) {

                        String status = iterator.next();
                        if (type.equals(status)) {
                            isValidType = true;
                            break;
                        }
                    }

                    if (isValidType) {
                        System.out.println("Valid!");
                        // The type matches one of the course statuses
                        // proceed to payment
                        payment = payment(courseManagement.getCourseMap().get(courseID).getCreditHours() * Registration.courseRate);
                        //test
                        System.out.print("Approve payment? (Y/N)");
     
                        System.out.println(payment);

                        return;

                    } else if (!type.equals("999")) {
                        System.out.println("Invalid course type for the selected course!");
                    }
                } while (!type.equals("999"));

            } else if (!courseID.equals("999")) {
                System.out.println("Invalid Course ID!");
            }

        } while (!courseID.equals("999"));

    }

    public void removeFromCourse() {

    }

    public void calFeesRegCourse() {

    }

    public void filterStudents() {

    }

    public void generateReport() {

    }

    public Payment payment(double amountToPay) {
        Scanner s1 = new Scanner(System.in);

        //Make Payment
        System.out.print("\nTotal: RM" + String.format("%.2f", amountToPay)
                + "\nPayment Options:\n"
                + "1. Card\n"
                + "2. Cash\n");

        int paymentNum = -1; // Initialize to an invalid value

        do {
            try {
                System.out.print("Select a Payment Option (1-2): ");
                paymentNum = s1.nextInt();
                s1.nextLine(); // Consume the newline character left in the input buffer

                if (paymentNum < 1 || paymentNum > 2) {
                    System.out.println("\nInvalid Input! Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                // Handle the exception (non-integer input)
                System.out.println("\nInvalid Input! Please enter a valid integer (1 or 2).");
                s1.nextLine(); // Consume the invalid input
            }
        } while (paymentNum < 1 || paymentNum > 2);

        //paymentAmount = event object's price
        //Create Card object if paymentNum = 1, 2 for cash
        if (paymentNum == 1) {

            //cardNum
            System.out.print("\nEnter Card Number: ");
            String cardNum = s1.nextLine();
            while (Card.vldCardNum(cardNum) == false) {
                System.out.print("Invalid Card Number!\n"
                        + "Enter Card Number: ");
                cardNum = s1.nextLine();
            }

            //cardHolder
            System.out.print("Enter Card Holder Name: ");
            String cardHolder = s1.nextLine();

            //cardExp
            System.out.print("Enter Card Expiry Date eg.(12/30): ");
            String cardExp = s1.nextLine();
            while (Card.vldCardExp(cardExp) == false) {
                System.out.print("Invalid Card Expiry Date!\n"
                        + "Enter Card Expiry Date eg.(12/30): ");
                cardExp = s1.nextLine();
            }

            //cardCVV
            System.out.print("Enter Card CVV: ");
            String cardCVV = s1.nextLine();
            while (Card.vldCardCvv(cardCVV) == false) {
                System.out.print("Invalid Card CVV!\n"
                        + "Enter Card CVV: ");
                cardCVV = s1.nextLine();
            }
            //Create Payment Object
            Card payment = new Card(cardNum, cardHolder, cardExp, cardCVV, amountToPay);



            return payment;

        } else {

            //amount tendered
            double amountTendered = -1; // Initialize to an invalid value

            do {
                try {
                    System.out.print("\nEnter amount tendered: RM ");
                    amountTendered = s1.nextDouble();

                    if ((amountTendered < amountToPay && amountTendered > 0) || amountTendered < 0) {
                        s1.nextLine(); // Consume the newline character left in the input buffer
                        System.out.print("\nInvalid input!\n");
                    }
                } catch (InputMismatchException e) {
                    // Handle the exception (non-numeric input)
                    System.out.println("\nInvalid Input! Please enter a valid numeric value.");
                    s1.nextLine(); // Consume the invalid input
                }
            } while ((amountTendered < amountToPay && amountTendered > 0) || amountTendered < 0);

            s1.nextLine();

            //create cash object
            Cash payment = new Cash(amountTendered, amountToPay);
            //if amount tendered >= event.getPrice(), make the paid = true, if 0 = paid = false

//            else {
//
//                System.out.println(payment);
//            }
            return payment;

        }
    }

}
