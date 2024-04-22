package control;

import entity.*;
import adt.ArrayList;
import adt.ListInterface;
import adt.MapInterface;
import adt.SetInterface;
import boundary.StudentRegistrationManagementUI;
import dao.CourseDAO;
import dao.StudentDAO;
import java.util.Iterator;
import utility.MessageUI;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Name: Yam Jason RDS2Y2S2G3 22WMR13662
 */
public class StudentRegistrationManagement implements Serializable {

    private ListInterface<Student> studentList = new ArrayList<>();
    private final CourseManagement courseManagement;
    private final StudentDAO studentDAO = new StudentDAO("students.dat");
    private final StudentRegistrationManagementUI studentUI = new StudentRegistrationManagementUI();
    private final CourseDAO courseDAO = new CourseDAO("courses.dat");

    public static int studentEntries;
    public static int registrationEntries;

    public StudentRegistrationManagement() {

        courseManagement = new CourseManagement();
        studentList = studentDAO.retrieveFromFile();

    }

    public void mainMenu() {
        int choice = -1;

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
                    searchStudents();
                    break;
                case 6:
                    registrationEntries = getTotalRegistered();
                    register();
                    break;
                case 7:
//                    displayCourseRegistered();
                    removeFromCourse();
                    break;
                case 8:
                    calFeesRegCourse();
                    break;
                case 9:
                    filterStudents();
                    break;
                case 10:
                    generateReport1();
                    break;
                case 11:
                    generateReport2();
                    break;

                default:
                    MessageUI.displayInvalidChoiceMessage();
            }
        } while (choice != 0);
    }

    //Task 1
    public void addStudent() {

        String name = studentUI.inputStudentName();
        if (name.equals("999")) {
            return;
        }
        String DOB;
        boolean dobValid = false;
        do {
            DOB = studentUI.inputDOB();
            if (vldDOB(DOB)) {
                dobValid = true;
            } else {
                MessageUI.displayInvalidInput();
            }

        } while (!dobValid);

        String ic;
        boolean icValid = false;
        do {
            ic = studentUI.inputIC();
            if (vldIC(ic)) {
                icValid = true;
            } else {
                MessageUI.displayInvalidInput();
            }

        } while (!icValid);

        String phoneNo;
        boolean phoneValid = false;
        do {
            phoneNo = studentUI.inputPhoneNo();
            if (vldPhoneNumber(phoneNo)) {
                phoneValid = true;
            } else {
                MessageUI.displayInvalidInput();
            }
        } while (!phoneValid);

        String email;
        boolean emailValid = false;
        do {
            email = studentUI.inputEmail();
            if (vldEmail(email)) {
                emailValid = true;
            } else {
                MessageUI.displayInvalidInput();
            }
        } while (!emailValid);

        String programmeID;
        courseManagement.displayAllProgrammes();
        //remember to use return at the last point
        do {

            programmeID = studentUI.inputProgrammeID();
            if (courseManagement.getProgrammeMap().containsKey(programmeID)) {

                Student newStudent = new Student(name, DOB, ic, phoneNo, email, programmeID);
                studentList.add(newStudent);
                System.out.println("Student ID: " + newStudent.getStudentID());
                studentDAO.saveToFile(studentList);
                System.out.println("Student Sucessfully Added!");

                return;

            } else if (!programmeID.equals("999")) {
                System.out.println("Invalid Course ID!");
            }

        } while (!programmeID.equals("999"));

    }

    //Task 2
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

    //For listing all students
    public String getAllStudents() {
        String outputStr = "";
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            outputStr += studentList.getEntry(i) + "\n";
        }
        return outputStr;
    }

    //List all students (extra)
    public void displayStudents() {
        if (!studentList.isEmpty()) {
            studentUI.listAllStudents(getAllStudents());
        } else {
            System.out.println("Student list is empty, please add new student to view.");
        }

    }

    //Task 3
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
                            String DOB;
                            boolean dobValid = false;
                            do {
                                DOB = studentUI.inputDOB();
                                if (vldDOB(DOB)) {
                                    dobValid = true;
                                } else {
                                    MessageUI.displayInvalidInput();
                                }

                            } while (!dobValid);

                            student.setStudentDOB(DOB);
                            MessageUI.displayUpdateMessage();
                            break;
                        case 3:
                            String phoneNo;
                            boolean phoneValid = false;
                            do {
                                phoneNo = studentUI.inputPhoneNo();
                                if (vldPhoneNumber(phoneNo)) {
                                    phoneValid = true;
                                } else {
                                    MessageUI.displayInvalidInput();
                                }
                            } while (!phoneValid);

                            student.setPhoneNo(phoneNo);
                            MessageUI.displayUpdateMessage();
                            break;
                        case 4:
                            String email;
                            boolean emailValid = false;
                            do {
                                email = studentUI.inputEmail();
                                if (vldEmail(email)) {
                                    emailValid = true;
                                } else {
                                    MessageUI.displayInvalidInput();
                                }
                            } while (!emailValid);
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

    //Task 4
    public void searchStudents() {
        String courseID;
        boolean printLabel;
        boolean studentExists;

        do {
            printLabel = true;
            studentExists = false;
            courseID = studentUI.inputCourseID();

            for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
                Student student = studentList.getEntry(i);
                MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

                // Iterate through the keys (registration numbers) of the registered courses map for the current student
                for (String registrationNumber : registeredCourses.keys()) {
                    Registration registration = registeredCourses.get(registrationNumber);

                    // Check if the registration contains the specified course ID
                    if (registration.getCourse().getCourseId().equals(courseID)) {
                        studentExists = true;

                        if (printLabel) {
                            studentUI.printRegCourseLabel(courseID);
                            printLabel = false;
                        }
                        // If the student is registered for the course, you can perform further actions here
                        System.out.printf("%-13s %-20s %-13s %-15s %-20s\n", student.getStudentID(), student.getStudentName(), student.getStudentDOB(), student.getPhoneNo(), student.getStudentEmail());
                        // No need to continue searching other registrations for this student
                    }
                }

            }
            if (!studentExists && !courseID.equals("999")) {
                studentUI.printNotExist();
            }
            System.out.println("");
        } while (!courseID.equals("999"));

    }

    //Task 5
    public void register() {
        String studentId = studentUI.inputStudentID();

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            if (student.getStudentID().equals(studentId)) {
                System.out.println("Valid student ID!");

                int choice = 0;
                do {
                    choice = studentUI.getRegChoice(studentId);
                    switch (choice) {
                        case 0:
                            MessageUI.displayBackMessage();
                            break;
                        case 1:
                            //display courses that only matches with the programme

//                            courseManagement.displayAllCourses();
                            //type courseID and make payment
                            registerProcess(i);
                            break;
                        default:
                            MessageUI.displayInvalidChoiceMessage();

                    }

                } while (choice != 0);

                return;
            }
        }
        System.out.println("Student with ID " + studentId + " not found.");
    }

    //For task 5
    public void registerProcess(int studentIndex) {

        String courseID;
        String type;
        Course course;
        SetInterface<String> courseStatuses;
        boolean valid;

        boolean isValidType;
        Payment payment;
        String approve;
        MapInterface<String, Course> courseMap = courseManagement.getCourseMap();
        ListInterface<ProgrammeCourse> programmeCourseList = courseManagement.getProgrammeCourseList();
        ProgrammeCourse programmeCourse;
        int programmeCount = 0;

        //checks student's total credit hour
        int totalCreditHour = getTotalCreditHours(studentList.getEntry(studentIndex));
        System.out.println("Student's total credit hour in this semester: " + totalCreditHour);
        System.out.println("Max Credit Hour: 16");

        //stopping here
        // Iterate through the registered programme
        //shows courses that have connections with the student's program   *arraylist
        System.out.printf("\n%-15s%-35s%-30s%15s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours");
        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            programmeCourse = programmeCourseList.getEntry(i);
            // Check if the registration contains the given course ID
            if (programmeCourse.getProgrammeID().equals(studentList.getEntry(studentIndex).getProgrammeID())) {
                programmeCount++;

                System.out.printf("%-15s%-35s%-30s%15s\n", programmeCourse.getCourseID(),
                        courseManagement.getCourseMap().get(programmeCourse.getCourseID()).getCourseName(),
                        courseManagement.getCourseMap().get(programmeCourse.getCourseID()).getStatus(),
                        courseManagement.getCourseMap().get(programmeCourse.getCourseID()).getCreditHours());

            }

        }
        if (programmeCount == 0) {
            System.out.println("There is no avaliable courses to register for this student");
        } else {
            //remember to use return at the last point
            do {
                valid = false;
                courseID = studentUI.inputCourseID();

                for (ProgrammeCourse programmeCourse1 : programmeCourseList) {
                    // Check if both programmeID and courseID match the input
                    if (programmeCourse1.getProgrammeID().equals(studentList.getEntry(studentIndex).getProgrammeID())
                            && programmeCourse1.getCourseID().equals(courseID)) {
                        valid = true;

                        //checks if the course has been registered by the student 
                        if (isCourseAlreadyRegistered(studentList.getEntry(studentIndex), courseID)) {
                            System.out.println("This course is registered by the student!");
                        } else if (totalCreditHour + courseManagement.getCourseMap().get(programmeCourse1.getCourseID()).getCreditHours() > 16) {
                            System.out.println("Unable to register for this course!");
                            System.out.println("Max Credit Hour is 16!");
                            return;

                        } else {
                            System.out.println("Course Not registered by the student!");
                            course = courseManagement.getCourseMap().get(courseID);
                            courseStatuses = courseManagement.getCourseMap().get(courseID).getStatus();
                            // Get an iterator for the course statuses
                            Iterator<String> iterator;

                            do {
                                iterator = courseStatuses.getIterator();
                                isValidType = false;
                                type = studentUI.inputCourseType();

                                // Validate the type against the course statuses
                                while (iterator.hasNext()) {

                                    String status = iterator.next();
                                    if (type.equals(status)) {
                                        isValidType = true;
                                        break;
                                    }
                                }

                                //if the course type entered is valid
                                if (isValidType) {
                                    System.out.println("Course Type Valid!");
                                    // The type matches one of the course statuses

                                    generateBill(studentList.getEntry(studentIndex).getStudentID(),
                                            studentList.getEntry(studentIndex).getStudentName(), studentList.getEntry(studentIndex).getProgrammeID(),
                                            courseID, courseManagement.getCourseMap().get(courseID).getCourseName(), courseManagement.getCourseMap().get(courseID).getCreditHours(),
                                            courseManagement.getCourseMap().get(courseID).getCreditHours() * Registration.courseRate);

                                    // proceed to payment
                                    payment = payment(courseManagement.getCourseMap().get(courseID).getCreditHours() * Registration.courseRate);
                                    //test

                                    do {

                                        approve = studentUI.inputApprove();
                                        if (approve.equals("Y")) {
                                            //print the registration bill
                                            System.out.println(payment);

                                            //generate the registration object then add into that student
                                            Registration registration = new Registration(course, type, payment);

                                            //add into student registered courses map
                                            studentList.getEntry(studentIndex).getRegisteredCourses().put(registration.getRegNum(), registration);

                                            studentDAO.saveToFile(studentList);
                                            courseDAO.saveToFile(courseMap);


                                        } else if (approve.equals("N")) {
                                            studentUI.printRejectedPayment();
                                        } else {

                                            System.out.println("Invalid input!");
                                        }

                                    } while (!approve.equals("Y") && !approve.equals("N"));

                                    return;

                                } else if (!type.equals("999")) {
                                    System.out.println("Invalid course type for the selected course!");
                                }
                            } while (!type.equals("999"));

                        }

                    }

                }
                if (!courseID.equals("999") && !valid) {
                    System.out.println("Invalid Course ID!");
                }
            } while (!courseID.equals("999"));

        }

    }

    //Task 6
    public void removeFromCourse() {
        String studentId = studentUI.inputStudentID();

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            if (student.getStudentID().equals(studentId)) {
                System.out.println("Valid student ID!");
                // Get the registered courses of the student
                MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

                if (registeredCourses.isEmpty()) {
                    System.out.println("This student has not registered for any courses.");
                    return;
                } else {
                    System.out.println("====================================================================================================");
                    System.out.println("                      Courses registered by student with ID " + studentId + ":");
                    System.out.println("====================================================================================================");
                    System.out.println("Note: you can only remove a student from a course (main, elective) registration");
                    System.out.printf("%-17s %-10s %-40s %-15s %-13s\n", "Registration ID", "Course ID", "Course Name", "Credit Hours", "Course Type");
                    for (String regNum : registeredCourses.keys()) {
                        Registration registration = registeredCourses.get(regNum);

                        //print only if the registration is not a cancelled registration
                        if (!registration.isRegistrationIsCancelled()) {

                            System.out.printf("%-17s %-10s %-40s %-15s %-13s\n", regNum, registration.getCourse().getCourseId(), registration.getCourse().getCourseName(), registration.getCourse().getCreditHours(), registration.getType());
                        }

                    }

                    // Prompt user to enter course ID
                    String regID = studentUI.inputRegID();

                    // Remove the specified course ID if it exists in registeredCourses
                    if (registeredCourses.containsKey(regID) && !registeredCourses.get(regID).isRegistrationIsCancelled() && (registeredCourses.get(regID).getType().equals("Main") || registeredCourses.get(regID).getType().equals("Elective"))) {
                        registeredCourses.get(regID).setRegistrationIsCancelled(true);
                        System.out.println("Course Registration with register ID " + regID + " removed successfully.");

                        studentDAO.saveToFile(studentList);
                    } else if (registeredCourses.containsKey(regID) && registeredCourses.get(regID).isRegistrationIsCancelled()) {
                        System.out.println("This registration was cancelled before!");
                    } else if (registeredCourses.containsKey(regID) && !registeredCourses.get(regID).isRegistrationIsCancelled()) {
                        System.out.println("You cant remove this registration because it is not a main or elective registration");

                    } else {
                        System.out.println("Course Registration with register ID " + regID + " not found in the registered courses.");
                    }
                    return;
                }
            }
        }
        System.out.println("Student with ID " + studentId + " not found.");

    }

    //Task 7
    public void calFeesRegCourse() {
        MapInterface<String, Course> courseMap = courseManagement.getCourseMap();
        studentUI.displayFeesCourse();
        for (Course course : courseMap.values()) {
            System.out.printf("%-15s %-35s %.0f\n", course.getCourseId(), course.getCourseName(), course.getFeePaid());
        }

    }

    //Task 8
    public void filterStudents() {

        String courseID;
        boolean printLabel;
        boolean studentExists;
        int criteria;
        String programmeID;

        do {
            printLabel = true;
            studentExists = false;
            courseID = studentUI.inputCourseID();

            for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
                Student student = studentList.getEntry(i);
                MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

                // Iterate through the keys (registration numbers) of the registered courses map for the current student
                for (String registrationNumber : registeredCourses.keys()) {
                    Registration registration = registeredCourses.get(registrationNumber);

                    // Check if the registration contains the specified course ID
                    if (registration.getCourse().getCourseId().equals(courseID)) {

                        // IF TRUE THEN PROCEED WITH LOGIC
                        criteria = -1;
                        do {
                            try {
                                criteria = studentUI.getCriteria();

                                switch (criteria) {
                                    case 0:
                                        MessageUI.displayBackMessage();
                                        return;

                                    case 1: {

                                        programFilter(courseID);
                                        break;
                                    }
                                    case 2:
                                        //female filter
                                        femaleFilter(courseID);
                                        break;
                                    case 3:
                                        //male filter
                                        maleFilter(courseID);
                                        break;
                                    default:
                                        MessageUI.displayInvalidChoiceMessage();

                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer.");

                            }
                        } while (criteria != 0);

                    }
                }

            }
            if (!studentExists && !courseID.equals("999")) {
                studentUI.printNotExist();
            }
            System.out.println("");
        } while (!courseID.equals("999"));

    }

    //Task 9 (report 1)
    public void generateReport1() {
        int maleCount = 0;
        int femaleCount = 0;
        double malePercent;
        double femalePercent;
        String gender;
        System.out.println("=====================================================================================");
        System.out.println("                                  Student Report");
        System.out.println("=====================================================================================");
        System.out.printf("%-15s %-25s %-10s %-15s %-20s\n", "Student ID", "Student Name", "Gender", "Date Of Birth", "Programme ID");
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);

            // Check the last digit of the student's IC number
            String ic = student.getIc();
            int lastDigit = Character.getNumericValue(ic.charAt(ic.length() - 1));

            // Check if the last digit has a remainder when divided by 2
            boolean isMale = lastDigit % 2 != 0;

            if (isMale) {
                maleCount++;
                gender = "Male";
            } else {
                femaleCount++;
                gender = "Female";
            }
            System.out.printf("%-15s %-25s %-10s %-15s %-20s\n", student.getStudentID(), student.getStudentName(), gender, student.getStudentDOB(), student.getProgrammeID());

        }

        System.out.println("\nNumber of Male Students: " + maleCount);
        System.out.println("Number of Female Students: " + femaleCount);
        System.out.println("Total Students: " + (maleCount + femaleCount));

        malePercent = (double) maleCount / (femaleCount + maleCount);
        femalePercent = 1 - malePercent;
        System.out.println("Percentage of Male Students: " + String.format("%.2f", malePercent * 100) + "%");
        System.out.println("Percentage of Female Students: " + String.format("%.2f", femalePercent * 100) + "%");

    }

    //Task 9 (report 2)
    public void generateReport2() {
        //main,elective,resit,repeat
        int mainCount = 0;
        int electiveCount = 0;
        int resitCount = 0;
        int repeatCount = 0;
        int total;
        System.out.println("==================================================================================================");
        System.out.println("                                    Registration Report");
        System.out.println("==================================================================================================");
        System.out.printf("%-20s %-20s %-40s %-10s\n", "Registration ID", "Course ID", "Course Name", "Type");
        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);

            // Get the registered courses of the student
            MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

            // If registeredCourses is null, the course is not registered
            if (registeredCourses == null) {
                //do nothing
            } else {
                // Iterate through the registered courses
                for (Registration registration : registeredCourses.values()) {

                    // Check if the registration contains the given course ID
                    if (registration.getType().equals("Main")) {
                        mainCount++;
                    } else if (registration.getType().equals("Elective")) {
                        electiveCount++;
                    } else if (registration.getType().equals("Resit")) {
                        resitCount++;
                    } else {
                        repeatCount++;
                    }
                    System.out.println(registration);
                }

            }

        }
        total = mainCount + electiveCount + resitCount + repeatCount;
        if (total != 0) {
            System.out.println("\nNumber of Main Registrations: " + mainCount);
            System.out.println("Number of Elective Registrations: " + electiveCount);
            System.out.println("Number of Resit Registrations: " + resitCount);
            System.out.println("Number of Repeat Registrations: " + repeatCount);

            System.out.println("\nPercentage of Main Registrations: " + String.format("%.2f", ((double) mainCount / total) * 100) + "%");
            System.out.println("Percentage of Elective Registrations: " + String.format("%.2f", ((double) electiveCount / total) * 100) + "%");
            System.out.println("Percentage of Resit Registrations: " + String.format("%.2f", ((double) resitCount / total) * 100) + "%");
            System.out.println("Percentage of Repeat Registrations: " + String.format("%.2f", ((double) repeatCount / total) * 100) + "%");
        } else {
            System.out.println("There is no registration!");

        }

    }

    //For task 5 register process to make payment
    public Payment payment(double amountToPay) {
        Scanner s1 = new Scanner(System.in);

        //Make Payment
        int paymentNum = -1; // Initialize to an invalid value

        do {

            paymentNum = studentUI.inputPaymentOption(amountToPay);

            if (paymentNum < 1 || paymentNum > 2) {
                MessageUI.displayInvalidChoiceMessage();
            }

        } while (paymentNum < 1 || paymentNum > 2);

        //paymentAmount = event object's price
        //Create Card object if paymentNum = 1, 2 for cash
        if (paymentNum == 1) {

            //cardNum
            String cardNum = studentUI.inputCardNumber();
            while (Card.vldCardNum(cardNum) == false) {
                System.out.print("Invalid Card Number!\n");
                cardNum = studentUI.inputCardNumber();
            }

            //cardHolder
            String cardHolder = studentUI.inputCardHolder();

            //cardExp
            String cardExp = studentUI.inputCardExp();
            while (Card.vldCardExp(cardExp) == false) {
                System.out.print("Invalid Card Expiry Date!\n");
                cardExp = studentUI.inputCardExp();
            }

            //cardCVV
            String cardCVV = studentUI.inputCardCVV();
            while (Card.vldCardCvv(cardCVV) == false) {
                System.out.print("Invalid Card CVV!\n");
                cardCVV = studentUI.inputCardCVV();
            }
            //Create Payment Object
            Card payment = new Card(cardNum, cardHolder, cardExp, cardCVV, amountToPay);

            return payment;

        } else {

            //amount tendered
            double amountTendered = -1; // Initialize to an invalid value

            do {

                amountTendered = studentUI.inputAmountTendered();

                if ((amountTendered < amountToPay && amountTendered > 0) || amountTendered < 0) {

                    MessageUI.displayInvalidInput();
                }

            } while ((amountTendered < amountToPay && amountTendered > 0) || amountTendered < 0);

            //create cash object
            Cash payment = new Cash(amountTendered, amountToPay);

            return payment;

        }
    }

    // Method to check if the course is already registered by the student
    private static boolean isCourseAlreadyRegistered(Student student, String courseID) {
        // Get the registered courses of the student
        MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

        // If registeredCourses is null, the course is not registered
        if (registeredCourses == null) {
            return false;
        }

        // Iterate through the registered courses
        for (Registration registration : registeredCourses.values()) {
            // Check if the registration contains the given course ID
            if (registration.getCourse().getCourseId().equals(courseID)) {
                // Course already registered
                return true;
            }
        }
        // Course not registered
        return false;
    }

    //to get total credit hours to check if it's eligible for registration
    private int getTotalCreditHours(Student student) {
        int totalCreditHours = 0;
        // Get the registered courses of the student
        MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();
        // Iterate through the registered courses
        for (Registration registration : registeredCourses.values()) {

            totalCreditHours += registration.getCourse().getCreditHours();
        }
        return totalCreditHours;

    }

    // to get registrationEntries for registration ID purpose
    public int getTotalRegistered() {

        // Iterate over all students in the studentList
        int totalRegisteredCourses = 0;
        for (int i = 0; i < studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i + 1);

            // Get the registered courses for the current student
            MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

            // Add the number of registered courses for the current student to the total
            totalRegisteredCourses += registeredCourses.size();
        }

        System.out.println("Total registered courses across all students: " + totalRegisteredCourses);
        return totalRegisteredCourses;
    }

    //For task 8
    public void programFilter(String courseID) {

        boolean printLabel = true;
        int studCount = 0;
        String programmeID;

        programmeID = studentUI.inputProgrammeID();

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

            // Iterate through the keys (registration numbers) of the registered courses map for the current student
            for (String registrationNumber : registeredCourses.keys()) {
                Registration registration = registeredCourses.get(registrationNumber);

                // Check if the registration contains the specified course ID
                if (registration.getCourse().getCourseId().equals(courseID) && student.getProgrammeID().equals(programmeID)) {

                    if (printLabel) {
                        printLabel = false;
                        studCount++;
                        studentUI.printRegCourseLabel(courseID);

                    }
                    System.out.printf("%-13s %-20s %-13s %-15s %-20s\n", student.getStudentID(), student.getStudentName(), student.getStudentDOB(), student.getPhoneNo(), student.getStudentEmail());
//                       

                }
            }

        }
        if (studCount == 0) {
            System.out.println("There is no student in this course that meets the criteria!");
        }
    }

    //For task 8
    public void maleFilter(String courseID) {

        boolean printLabel = true;
        int studCount = 0;

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

            // Iterate through the keys (registration numbers) of the registered courses map for the current student
            for (String registrationNumber : registeredCourses.keys()) {
                Registration registration = registeredCourses.get(registrationNumber);

                // Check the last digit of the student's IC number
                String ic = student.getIc();
                int lastDigit = Character.getNumericValue(ic.charAt(ic.length() - 1));

                // Check if the last digit has a remainder when divided by 2
                boolean isMale = lastDigit % 2 != 0;

                // Check if the registration contains the specified course ID
                if (registration.getCourse().getCourseId().equals(courseID) && isMale) {

                    if (printLabel) {
                        studCount++;
                        printLabel = false;
                        studentUI.printRegCourseLabel(courseID);

                    }
                    System.out.printf("%-13s %-20s %-13s %-15s %-20s\n", student.getStudentID(), student.getStudentName(), student.getStudentDOB(), student.getPhoneNo(), student.getStudentEmail());
//                       

                }
            }

        }
        if (studCount == 0) {
            System.out.println("There is no student in this course that meets the criteria!");
        }
    }

    //For task 8
    public void femaleFilter(String courseID) {

        boolean printLabel = true;
        int studCount = 0;

        for (int i = 1; i <= studentList.getNumberOfEntries(); i++) {
            Student student = studentList.getEntry(i);
            MapInterface<String, Registration> registeredCourses = student.getRegisteredCourses();

            // Iterate through the keys (registration numbers) of the registered courses map for the current student
            for (String registrationNumber : registeredCourses.keys()) {
                Registration registration = registeredCourses.get(registrationNumber);

                // Check the last digit of the student's IC number
                String ic = student.getIc();
                int lastDigit = Character.getNumericValue(ic.charAt(ic.length() - 1));

                // Check if the last digit has a remainder when divided by 2
                boolean isMale = lastDigit % 2 != 0;

                // Check if the registration contains the specified course ID
                if (registration.getCourse().getCourseId().equals(courseID) && !isMale) {

                    if (printLabel) {
                        printLabel = false;
                        studCount++;
                        studentUI.printRegCourseLabel(courseID);

                    }
                    System.out.printf("%-13s %-20s %-13s %-15s %-20s\n", student.getStudentID(), student.getStudentName(), student.getStudentDOB(), student.getPhoneNo(), student.getStudentEmail());
//                       

                }
            }

        }
        if (studCount == 0) {
            System.out.println("There is no student in this course that meets the criteria!");
        }

    }

    //for registration
    public void generateBill(String ID, String name, String programmeID, String courseID, String courseName, int creditH, double fees) {
        System.out.println("\n================================================================================================");
        System.out.println("                                       STUDENT BILL");
        System.out.println("================================================================================================");
        System.out.println("Student ID: " + ID);
        System.out.println("Student Name: " + name);
        System.out.println("Programme: " + programmeID);
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-40s %-15s %-20s\n", "CourseID", "Course Name", "Credit Hours", "Fees");
        System.out.printf("%-10s %-40s %-15s %-20s\n", courseID, courseName, creditH, fees);

    }

    //to validate IC
    public static boolean vldIC(String IC) {
        String ICRegex = "^[0-9]{12}$";
        Pattern pattern = Pattern.compile(ICRegex);
        Matcher matcher = pattern.matcher(IC);
        return matcher.matches();
    }

    //to validate email
    public static boolean vldEmail(String email) {
        // Regular expression for a valid email address
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email against the pattern
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches(); // True for valid, false for invalid

    }

    //to validate date of birth
    public static boolean vldDOB(String dob) {
        // Regular expression for a valid date of birth (dd/MM/yyyy)
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(dobRegex);

        // Match the DOB against the pattern
        Matcher matcher = pattern.matcher(dob);

        // Check if the DOB matches the pattern
        return matcher.matches(); // True for valid, false for invalid
    }

    //to validate validate Phone Number
    public static boolean vldPhoneNumber(String phoneNumber) {
        // Regular expression for a Malaysian phone number starting with "01" followed by 8 digits
        String phoneRegex = "^(01[0-9])-[0-9]{7,8}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(phoneRegex);

        // Match the phone number against the pattern
        Matcher matcher = pattern.matcher(phoneNumber);

        // Check if the phone number matches the pattern
        return matcher.matches(); // True for valid, false for invalid
    }
}
