/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import java.util.Scanner;
import entity.*;
import adt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        System.out.println("| " + "6.  Amend course details for a programme        |");
        System.out.println("| " + "7.  List courses taken by different faculties   |");
        System.out.println("| " + "8.  List all courses for a programme            |");
        System.out.println("| " + "9.  Courses Summary Report                      |");
        System.out.println("| " + "10. Programme Summary Report                    |");
        System.out.println("| " + "0.  Exit                                        |");
        System.out.println("---------------------------------------------------");
        int choice = getInputChoice();
        System.out.println();
        return choice;
    }

    public void displayAddProgrammeTitle() {
        System.out.println("======================================================================");
        System.out.println("                         Add a programme to courses                   ");
        System.out.println("======================================================================");
    }

    public void displayRemoveProgrammeTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                                 Remove a programme from a course                                ");
        System.out.println("=================================================================================================");
    }

    public void displayOnlyCoursesBelowHaveProgrammes() {
        System.out.println("                              << Only courses below have programmes  >>                          ");
        System.out.println("=================================================================================================");
    }

    public void displayOnlyProgrammesBelowHaveCourses() {
        System.out.println("                              << Only programmes below have courses  >>                          ");
        System.out.println("=================================================================================================");
    }

    public void displayCoursesOfSelectProgrammeNote() {
        System.out.println("=================================================================================================");
        System.out.println("                              << Only courses of selected programme >>                          ");
        System.out.println("=================================================================================================");
    }

    public void displayProgrammesOfSelectCourseNote() {
        System.out.println("=================================================================================================");
        System.out.println("                              << Only programmes of selected course >>                          ");
        System.out.println("=================================================================================================");
    }

    public void displayListAllCoursesForAProgrammeTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                                 List all courses for a programme                                ");
        System.out.println("=================================================================================================");
    }

    public void displayRemoveCourseTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                                 Remove a course from a programme                                ");
        System.out.println("=================================================================================================");
    }

    public void displayAddNewCourseTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                                 Add a new course to programmes                                  ");
        System.out.println("=================================================================================================");
    }

    public void displayAmendTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                               Amend course details for a programme                              ");
        System.out.println("=================================================================================================");
    }

    public void displayDifferentFacultiesTitle(String facultyName) {
        System.out.println("==================================================================================================");
        System.out.println("                     " + facultyName + "               ");
        System.out.println("==================================================================================================");
    }

    public void listProgrammes(String outputStr) {
        System.out.printf("%-15s%-40s\n", "Programme ID", "Programme Name");
        System.out.println(outputStr);
    }

    public void listProgrammesSummaryReport2(String outputStr) {
        System.out.printf("\n%-15s%-60s%-20s%-20s\n", "Programme ID", "Programme Name", "Total Credit Hours", "Total Courses");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(outputStr);
    }

    public void listFaculties(String outputStr) {
        System.out.printf("%-10s %-40s\n", "Faculty ID", "Faculty Name");
        System.out.println(outputStr);
    }

    public void listCourses(String outputStr) {
        courseTitle();
        System.out.println(outputStr);
    }

    public void courseTitle() {
        System.out.printf("\n%-15s%-35s%-30s%15s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours");
    }

    public void listCoursesPrefix(String outputStr) {
        System.out.println(outputStr);
    }

    public void listCoursesSummaryReport(String outputStr) {
        System.out.printf("\n\t%-15s%-35s%-30s%-15s%-20s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours", "Programmes/Faculties Offered");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(outputStr);
    }

    public String inputProgrammeID() {
        System.out.print("Enter Programme ID(999 to exit): ");
        return sc.nextLine();
    }

    public void displayProgrammeDoesNotExist() {
        System.out.println("Programme does not exist!");

    }

    public int getAmendChoice(String courseID) {
        System.out.println("\n=====================================================================");
        System.out.println("                   Course Details Amendment");
        System.out.println("=====================================================================");
        System.out.println("Course ID: " + courseID);
        System.out.println("1. Change Course Name");
        System.out.println("2. Change Status");
        System.out.println("3. Change Credit Hours");
        System.out.println("999. Back");
        int choice = getInputChoice();
        System.out.println();
        return choice;

    }

    public void displayNoCourseInProgramme() {
        System.out.println("There is no course in this programme.\n");
    }

    public void listCoursesInProgramme(String outputStr) {
        System.out.printf("\n%-15s%-35s%-30s%15s\n", "Course ID", "Course Name", "Status(s)", "Credit Hours");
        System.out.println(outputStr);
    }

    public void displayCoursesInSpecificProgrammeTitle(String programmeId) {
        System.out.println("=================================================================================================");
        System.out.println("                                   Courses in programme " + programmeId);
        System.out.println("=================================================================================================");
    }

    public String inputCourseID() {
        System.out.print("Enter Course ID(999 to exit): ");
        return sc.nextLine();
    }

    public String inputFuzzy() {
        System.out.print("Enter Course ID/Name (999 to exit): ");
        return sc.nextLine();
    }

    public void displaySearchCoursesTitle() {
        System.out.println("=================================================================================================");
        System.out.println("                              Search courses offered in a semester                               ");
        System.out.println("=================================================================================================");
        System.out.println("           <<You can enter complete/part of the Course ID / Course Name to fuzzy search>>        ");
        System.out.println("=================================================================================================");
    }

    public String inputFuzzyCourseID() {
        System.out.print("Enter Course ID(999 to exit): ");
        return sc.nextLine();
    }

    public String inputFacultyID() {
        System.out.print("Enter Faculty ID(999 to exit): ");
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

    public void displayNoMatchCourse() {
        System.out.println("No match Course found!");
    }

    public void displayNoMatchFacultyID() {
        System.out.println("No match Faculty ID found!");
    }

    public void displayProgrammeIDFormatIncorrect() {
        System.out.println("Programme ID format is wrong!");
    }

    public void displayCourseIDFormatIncorrectAndExample() {
        System.out.println("Course ID format is wrong!");
        System.out.println("It must be 4 capital letters and 4 digits.");
        System.out.println("Eg: BACS1113");
    }

    public void displayCourseIDFormatIncorrect() {
        System.out.println("Course ID format is wrong!");
    }

    public void displayThisCourseDontHaveAnyProgramme() {
        System.out.println("This course does not have any programme.");
    }

    public void displayThisProgrammeDontHaveAnyCourse() {
        System.out.println("This programme does not have any course.");
    }

    public void displayProgrammeDontHaveThisCourse() {
        System.out.println("This programme does not have this course.");
    }

    public void displayCourseDontHaveThisProgramme() {
        System.out.println("This course does not have this programme.");
    }

    public void displayFacultyIDFormatIncorrect() {
        System.out.println("Course ID format is wrong!");
        System.out.println("Eg: FOCS");
    }

    public String inputCourseName() {
        System.out.print("Enter Course Name: ");
        return sc.nextLine();
    }

    public void displayStatusChoice() {
        System.out.println("Select combination of status(s) to offer");
        System.out.println("----------------------------------------");
        System.out.println("1. Main, Repeat, Resit, Elective");
        System.out.println("2. Main, Repeat, Resit");
        System.out.println("3. Main, Repeat");
        System.out.println("4. Main, Resit");
    }

    public String inputCourseStatusChoice() {
        System.out.print("Enter your choice (1/2/3/4): ");
        return sc.nextLine();
    }

    public String inputCreditHours() {
        System.out.print("Enter credit hours (3/4):");
        return sc.nextLine();
    }

    public void displayCreditHoursInvalid() {
        System.out.print("Only 3/4 is accepted");
    }

    public void displayProgrammeHasBeenAddedBefore(Programme programme) {
        System.out.println("Programme " + programme.getProgrammeId() + " has been added to this course before!");
    }

    public void displayProgrammeIsSuccessfullyAddedToCourse(Course course, Programme programme) {
        System.out.println("Programme of " + programme.getProgrammeId() + " is successfully added to course " + course.getCourseId() + "!");
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

    public void displaySummaryReportTitle() {
        LocalDateTime currentTime = LocalDateTime.now();

        // Define the desired date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, M/d/yyyy, hh:mma");

        // Format the current time using the defined format
        String formattedTime = currentTime.format(formatter);

        System.out.println("======================================================================================================================================");
        System.out.println("\t\t\t\tTUNKU ABDUL RAHMAN UNIVERSITY OF MANAGEMENT AND TECHNOLOGY");
        System.out.println("\t\t\t\t\tCOURSE MANAGEMENT SUBSYSTEM");
        System.out.println("");
        System.out.println("\t\t\t\t\t   COURSE SUMMARY REPORT ");
        System.out.println("\t\t\t\t\t   -----------------------");
        System.out.println("");
        System.out.println("Generated at: " + formattedTime);

    }

    public void displaySummaryReport1Middle(int numberOfCourses, int numberOfMain, int numberOfResit, int numberOfRepeat, int numberOfElective) {
        System.out.println("Total " + numberOfCourses + " courses: " + numberOfMain + " Main | " + numberOfResit + " Resit | " + numberOfRepeat + " Repeat | " + numberOfElective + " Elective");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void displayHighestNoOfProgrammes(int maxNumberOfTakenByProgrammes, StringBuilder maxProgrammeString) {
        System.out.println("Highest Programmes Offered: [" + maxNumberOfTakenByProgrammes + " Programmes] \n" + maxProgrammeString);
    }

    public void displayLowestNoOfProgrammes(int minNumberOfTakenByProgrammes, StringBuilder minProgrammeString) {
        System.out.println("Lowest Programmes Offered: [" + minNumberOfTakenByProgrammes + " Programmes] \n" + minProgrammeString);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void displayHighestNoOfFaculties(int maxNumberOfFaculty, StringBuilder maxFacultyString) {
        System.out.println("Highest Faculties Offered: [" + maxNumberOfFaculty + " Faculties] \n" + maxFacultyString);

    }

    public void displayLowestNoOfFaculties(int minNumberOfFaculty, StringBuilder minFacultyString) {
        System.out.println("Lowest Faculties Offered: [" + minNumberOfFaculty + " Faculties] \n" + minFacultyString);

    }

    public void endSummaryReport() {
        System.out.println("\t\t\t\t\tEND OF THE COURSE SUMMARY REPORT");
        System.out.println("======================================================================================================================================");
    }

    public void displayTotalNoOfProgrammes(MapInterface<String, Programme> programmeMap) {
        System.out.println("Total number of programmes: " + programmeMap.size());
    }

    public void summaryReport2Middle(int noOfProgrammesUnderFOCS, int noOfProgrammesUnderFOET, int noOfProgrammesUnderFAFB, int noOfProgrammesUnderFOAS) {
        System.out.println("FOCS: " + noOfProgrammesUnderFOCS);
        System.out.println("FOET: " + noOfProgrammesUnderFOET);
        System.out.println("FAFB: " + noOfProgrammesUnderFAFB);
        System.out.println("FOAS: " + noOfProgrammesUnderFOAS);
    }

    public void displayProgrammeWithHighestCreditHr(int maxTotalCreditHours) {
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Programme(s) with Highest Total Credit Hours: " + maxTotalCreditHours + "\n");
    }

    public void displayLineSummaryReport() {
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void displayChangedName(String prevName, String newName) {
        System.out.println("Name is changed from '" + prevName + "' to '" + newName + "'.");
    }

    public void displayChangedStatus(String prevStatus, String newStatus) {
        System.out.println("Status is changed from '" + prevStatus + "' to '" + newStatus + "'.");
    }
    
    public void displayChangedCreditHours(int prevCrHrs, int newCrHrs) {
        System.out.println("Credit hours is changed from '" + prevCrHrs + "' to '" + newCrHrs + "'.");
    }

    public void displayProgrammeWithLowestCreditHr(int minTotalCreditHours) {
        System.out.println("Programme(s) with Lowest Total Credit Hours: " + minTotalCreditHours + "\n");
    }

    public void displayProgrammeWithMostCourses(int maxTotalCourses) {
        System.out.println("Programme(s) with Highet Number of Courses: " + maxTotalCourses + "\n");
    }

    public void displayProgrammeWithLeastCourses(int minTotalCourses) {
        System.out.println("Programme(s) with Lowest Number of Courses: " + minTotalCourses + "\n");
    }

    public void removeSuccessfullyFromCourseMsg(Programme selectedProgramme, Course selectedCourse) {
        System.out.println("Programme " + selectedProgramme.getProgrammeId() + " is removed successfully from course " + selectedCourse.getCourseId() + "!\n");
    }

    public void noRecordFoundInBridgeTableMsg() {
        System.out.println("There is no record found.");
    }

    public void courseIDExistErrorMsg() {
        System.out.println("It has been used! Type again!");
    }

    public void displayEnterProgrammeIDTitle() {
        System.out.println("===================================================================");
        System.out.println("        Enter Programme ID that you want to add the course         ");
        System.out.println("===================================================================");
    }

    public void newCourseAddedMsg(String programmeID) {
        System.out.println("New course is successfully added to programme " + programmeID + "!");
    }

    public void alreadyAddedBeforeMsg(String programmeID) {
        System.out.println("It has been added to programme " + programmeID + " before!");
    }

    public void displayNoThisCourse() {
        System.out.println("This course does not exist!");
    }

    public void removedCourseFromProgrammeSuccessMsg(Course selectedCourse, Programme selectedProgramme) {
        System.out.println("Course " + selectedCourse.getCourseId() + " is removed successfully from programme " + selectedProgramme.getProgrammeId() + "!\n");
    }

}
