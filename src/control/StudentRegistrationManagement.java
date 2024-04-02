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

/**
 *
 * @author Jason
 */
public class StudentRegistrationManagement implements Serializable {

    private ListInterface<Student> studentList = new ArrayList<>();

    private StudentDAO studentDAO = new StudentDAO("students.dat");
    private StudentRegistrationManagementUI studentUI = new StudentRegistrationManagementUI();

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

        courseID = studentUI.inputCourseID();
        if (courseManagement.getCourseMap().containsKey(courseID)) {
            System.out.println("ID matches");
            type = studentUI.inputCourseType();

            Course course = courseManagement.getCourseMap().get(courseID);
            System.out.println(course);
            SetInterface<String> courseStatuses = courseManagement.getCourseMap().get(courseID).getStatus();
            System.out.println("test: "+courseStatuses);

// Get an iterator for the course statuses
            Iterator<String> iterator = courseStatuses.getIterator();


            // Validate the type against the course statuses
            boolean isValidType = false;
            while (iterator.hasNext()) {

                String status = iterator.next();
                System.out.println("Comparing with status: " + status); // Print each status being compared
                if (type.equals(status)) {
                    isValidType = true;
                    break;
                }
            }

            if (isValidType) {
                System.out.println("Valid!");
                        
                // The type matches one of the course statuses
                // Proceed with your logic
            } else {
                System.out.println("Invalid course type for the selected course!");
                // You might want to handle this case, perhaps prompt the user again for a valid course type
            }
        } else {
            System.out.println("Invalid Course ID!");
        }
//        do {
//            choice = studentUI.getAmendChoice(studentId);
//            switch (choice) {
//                case 0:
//                    MessageUI.displayBackMessage();
//                    break;
//                case 1:
//                    String studentName = studentUI.inputStudentName();
//                    student.setStudentName(studentName);
//                    MessageUI.displayUpdateMessage();
//                    break;
//
//                default:
//                    MessageUI.displayInvalidChoiceMessage();
//
//            }
//
//        } while (choice != 0);

    }

    public void removeFromCourse() {

    }

    public void calFeesRegCourse() {

    }

    public void filterStudents() {

    }

    public void generateReport() {

    }

}
