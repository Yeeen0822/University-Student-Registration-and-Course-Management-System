/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import entity.*;
import adt.*;

/**
 *
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 */
public class CourseManagementUI {

    Scanner sc = new Scanner(System.in);

    // Menu
    public int getMenuChoice() {
        System.out.println("---------------------------------------------------");
        System.out.println("|              " + "Course Management Menu             |");
        System.out.println("|-------------------------------------------------|");
        System.out.println("| " + "1.  Add a programme to courses                  |");
        System.out.println("| " + "2.  Remove a programme from a course            |");
        System.out.println("| " + "3.  Add a new course to programmes              |");
        System.out.println("| " + "4.  Remove a course from a programme            |");
        System.out.println("| " + "5.  Search courses offered in a semester        |");
        System.out.println("| " + "6.  Ammend course details for a programme       |");
        System.out.println("| " + "7.  List courses taken by different faculties   |");
        System.out.println("| " + "8.  List all courses for a programme            |");
        System.out.println("| " + "9.  Summary Report 1                            |");
        System.out.println("| " + "10. Summary Report 2                            |");
        System.out.println("| " + "0.  Exit                                        |");
        System.out.println("---------------------------------------------------");
        int choice = getInputChoice();
        System.out.println();
        return choice;
    }

    public void displayAddProgrammeTitle() {
        System.out.println("---------------------------------------------------------------------");
        System.out.println("|                         Add a programme to courses                 |");
        System.out.println("|--------------------------------------------------------------------|");
    }

    public void listProgrammes(String outputStr) {
        System.out.printf("%-15s%-40s\n", "Programme ID", "Programme Name");
        System.out.println(outputStr);
    }

    public void listCourses(String outputStr) {
        System.out.printf("\n%-15s%-35s%-30s%15s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours");
        System.out.println(outputStr);
    }

    public String inputProgrammeID() {
        System.out.print("Enter Programme ID(999 to exit): ");
        return sc.nextLine();
    }

    public void displayProgrammeDoesNotExist() {
        System.out.println("Programme does not exist");

    }

    public void displayNoCourseInProgramme() {
        System.out.println("There is no course in this programme.\n");
    }

    public void listCoursesInProgramme(String outputStr) {
        System.out.printf("%-15s%-35s%-30s%15s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours");
        System.out.println(outputStr);
    }

    public String inputCourseID() {
        System.out.print("Enter Course ID(999 to exit): ");
        return sc.nextLine();
    }



    public void displayExitMessage() {
        System.out.println("Exiting...");
    }

    public void displayInvalidChoice() {
        System.out.println("Invalid choice");

    }

    public void displayNoMatchProgrammeID() {
        System.out.println("No match Programme ID found!");
    }

    public void displayNoMatchCourseID() {
        System.out.println("No match Course ID found!");
    }

    public void displayProgrammeIDFormatIncorrect() {
        System.out.println("Programme ID format is wrong!");
    }

    public void displayCourseIDFormatIncorrect() {
        System.out.println("Course ID format is wrong!");
    }
    
    public String inputCourseName() {
        System.out.print("Enter New Course Name: ");
        return sc.nextLine();
    }
    

    
    public void displayStatusChoice(){
        System.out.println("1. Main, Repeat, Resit, Elective");
        System.out.println("2. Main, Repeat, Resit");
        System.out.println("3. Main, Repeat");
        System.out.println("4. Main, Resit");
        
    }
    
    public int inputCourseStatusChoice(){
        System.out.print("Enter your choice (1/2/3/4): ");
        return sc.nextInt();
    }
    
    public int inputCreditHours(){
        System.out.print("Enter credit hours (3/4)");
        return sc.nextInt();
    }
    
    public void displayCreditHoursInvalid(){
        System.out.print("Only 3/4 is accepted");
    }
    

    public void displayProgrammeHasBeenAddedBefore(Programme programme) {
        System.out.println("Programme " + programme.getProgrammeId() + " has been added to this course before!");
    }

    public void displayProgrammeIsSuccessfullyAddedToCourse(Course course, Programme programme) {
        System.out.println("Programme of " + programme.getProgrammeName() + " is successfully added to course " + course.getCourseName() + "!");
        System.out.println("Programme's coursesmap:" + programme.getProgrammeCoursesMap());
        System.out.println("Course's programmemap:" +course.getCourseProgrammesMap());
        System.out.println("hi");
    }

    public void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    //Input
    public int getInputChoice() {
        boolean isInputChoiceValid = false;
        int choice = 0;
        do {
            System.out.print("Enter choice: ");
            try {
                isInputChoiceValid = true;
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                isInputChoiceValid = false;
                displayInvalidInput();
            }
        } while (!isInputChoiceValid);
        return choice;
    }

    public void displayInvalidInput() {
        System.out.println("Invalid input.");
    }

}
