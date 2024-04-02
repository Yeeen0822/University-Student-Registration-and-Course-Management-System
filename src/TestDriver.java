
import control.*;
import dao.*;
import java.util.Scanner;
import utility.MessageUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Jason
 */
public class TestDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //do it once to initialize student data 
//        StudentInitializer studentInitializer = new StudentInitializer();
//        studentInitializer.initializeStudents();
//
//
//        CourseInitializer courseInitializer = new CourseInitializer();
//        courseInitializer.initializeCourses();
//
//
//        ProgrammeInitializer programmeInitializer = new ProgrammeInitializer();
//        programmeInitializer.initializeProgrammes();
//
//
//        FacultyInitializer facultyInitializer = new FacultyInitializer();
//        facultyInitializer.initializeFaculties();

        
        

//        courseManagement.intializeDummyData();
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt(); 
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 0:
                    //to exit
                    MessageUI.displayExitMessage();
                    break;
                case 1:
                    StudentRegistrationManagement studentRegistration = new StudentRegistrationManagement();
                    studentRegistration.mainMenu();
                    break;
                case 2:
                    CourseManagement courseManagement = new CourseManagement();
                    courseManagement.start();
                    break;
                default:
                    MessageUI.displayInvalidChoiceMessage();
            }

        } while (choice != 0);

    }

    public static void displayMenu() {
        System.out.println("\n=====================================================================");
        System.out.println("University Student Registration and Course Management Systems");
        System .out.println("=====================================================================");
        System.out.println("1. Student Registration Management");
        System.out.println("2. Course Management");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");

    }
}
