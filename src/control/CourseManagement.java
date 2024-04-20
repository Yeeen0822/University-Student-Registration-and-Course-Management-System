/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import dao.*;
import boundary.*;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import utility.MessageUI;

/**
 *
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 */
public class CourseManagement implements Serializable {

    private MapInterface<String, Faculty> facultyMap = new HashMap<>();
    private MapInterface<String, Programme> programmeMap = new HashMap<>(); //focs,fafb,...
    private MapInterface<String, Course> courseMap = new HashMap<>();
    private ListInterface<ProgrammeCourse> programmeCourseList = new ArrayList<>(); // [rds,aaa],[rda,bbb],....

    SetInterface<String> programmesThatHasCourses = new ArraySet<>();
    SetInterface<String> coursesThatHaveProgramme = new ArraySet<>();

    private ListInterface<String> selectedProgrammeList = new ArrayList<>(); //rds,.....(5)//function 
    //

    private final FacultyDAO facultyDAO = new FacultyDAO("faculties.dat");
    private final ProgrammeDAO programmeDAO = new ProgrammeDAO("programmes.dat");
    private final CourseDAO courseDAO = new CourseDAO("courses.dat");
    private final ProgrammeCourseDAO programmeCourseDAO = new ProgrammeCourseDAO("programmeCourses.dat");
    private final CourseManagementUI courseManagementUI = new CourseManagementUI();

    public CourseManagement() {
        facultyMap = facultyDAO.retrieveFromFile();
        programmeMap = programmeDAO.retrieveFromFile();
        courseMap = courseDAO.retrieveFromFile();
        programmeCourseList = programmeCourseDAO.retrieveFromFile();

    }

    public void start() {
        int choice = 0;
        boolean isExit;
        do {
            isExit = false;
            choice = courseManagementUI.getMenuChoice();
            switch (choice) {
                case 1: {
                    addProgrammetoCourses();
                    break;
                }

                case 2: {
                    removeProgrammeFromCourse();
                    break;
                }

                case 3: {
                    addNewCourseToProgrammes();
                    break;
                }
                case 4: {
                    removeCourseFromProgramme();
                    break;
                }

                case 5: {
                    searchCoursesOfferedInSemester();
                    break;
                }

                case 6: {
                    amendCourseDetailsForProgramme();
                    break;
                }
                case 7: {
                    listCoursesTakenByDifferentFaculties();
                    break;
                }
                case 8: {
                    listAllCoursesForAProgramme();
                    break;
                }

                case 9: {
                    summaryReport1();
                    break;
                }
                case 10: {
                    summaryReport2();
                    break;
                }

                case 0: {
                    courseManagementUI.displayExitMessage();
                    isExit = true;
                    break;
                }
                default:
                    courseManagementUI.displayInvalidChoice();
            }
        } while (choice != 0);
    }

    // TASK 1
    // Add a Prorgramme to Courses
    public void addProgrammetoCourses() {
        // Display Title first
        courseManagementUI.displayAddProgrammeTitle();

        // Display all programmes available
        displayAllProgrammes();

        String programmeID = validateInputProgrammeID();

        if (programmeID == null) {
            return;
        }

        Programme programme = programmeMap.get(programmeID);
        displayAllCourses();

        int totalCreditHours = calculateTotalCreditHours(programme); // Calculate total credit hours

        boolean continueToAdd = true;

        do {
            if (!continueToAdd) {
                return;
            }

            String courseID = validateInputCourseID();

            if (courseID == null) {
                continueToAdd = false;
            } else {
                Course course = courseMap.get(courseID);
                int newTotalCreditHours = totalCreditHours + course.getCreditHours(); // Calculate new total credit hours

                if (newTotalCreditHours <= 18) { // Check if adding the course exceeds the limit
                    ProgrammeCourse programmeCourse = new ProgrammeCourse(programme.getProgrammeId(), course.getCourseId());

                    if (programmeCourseList.contains(programmeCourse)) {
                        courseManagementUI.displayProgrammeHasBeenAddedBefore(programme);
                    } else {
                        programmeCourseList.add(programmeCourse);
                        programmeCourseDAO.saveToFile(programmeCourseList);
                        courseManagementUI.displayProgrammeIsSuccessfullyAddedToCourse(course, programme);
                        totalCreditHours = newTotalCreditHours; // Update total credit hours
                    }
                } else {
                    System.out.println("Exceed 18 total credit hours! Cant add anymore");
                    //courseManagementUI.displayExceedTotalCreditHoursLimit(programme); // Display message for exceeding limit
                }
            }
        } while (continueToAdd);
    }

    private int calculateTotalCreditHours(Programme programme) {
        int totalCreditHours = 0;
        for (ProgrammeCourse pc : programmeCourseList) {
            if (pc.getProgrammeID().equals(programme.getProgrammeId())) {
                Course course = courseMap.get(pc.getCourseID());
                totalCreditHours += course.getCreditHours();
            }
        }
        return totalCreditHours;
    }

    // FOR TASK 1
    public void displayAllProgrammes() {
        StringBuilder sb = new StringBuilder();
        for (Programme programme : programmeMap.values()) {
            sb.append(programme.toString());
            sb.append("\n");
        }
        courseManagementUI.listProgrammes(sb.toString());
    }

    // FOR TASK 1
    public void displayAllCourses() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courseMap.values()) {
            sb.append(course.toString());
            sb.append("\n");
        }
        courseManagementUI.listCourses(sb.toString());
    }

    // FOR TASK 1
    private String validateInputProgrammeID() {
        String programmeID = null;
        boolean isValidFormat = false;
        boolean programmeIDExist = false;
        String regexProgrammeID = "[A-Z]{3}";
        do {
            System.out.println("");
            try {
                programmeID = courseManagementUI.inputProgrammeID().toUpperCase();

                if (!programmeID.equals("999")) {
                    if (programmeID.matches(regexProgrammeID)) {
                        isValidFormat = true;
                        if (programmeMap.containsKey(programmeID)) {
                            programmeIDExist = true;
                        } else {
                            courseManagementUI.displayNoMatchProgrammeID();
                        }
                    } else {
                        courseManagementUI.displayProgrammeIDFormatIncorrect();
                    }
                } else {
                    programmeID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !programmeIDExist);
        return programmeID;
    }

    // FOR TASK 1
    private String validateInputCourseID() {
        String courseID = null;
        boolean isValidFormat = false;
        boolean courseIDExist = false;
        String regexCourseID = "[A-Z]{4}\\d{4}";
        do {
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID().toUpperCase();

                if (!courseID.equals("999")) {
                    if (courseID.matches(regexCourseID)) {
                        isValidFormat = true;
                        if (courseMap.containsKey(courseID)) {
                            courseIDExist = true;
                        } else {
                            courseManagementUI.displayNoMatchCourseID();
                        }
                    } else {
                        courseManagementUI.displayCourseIDFormatIncorrect();
                    }
                } else {
                    courseID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !courseIDExist);
        return courseID;
    }

    //Task 4
    //Remove a course from a programme
    public void removeCourseFromProgramme() {
        courseManagementUI.displayRemoveCourseTitle();

        // if no any record in bridge table, display error message, exit this method
        if (programmeCourseList.isEmpty()) {
            courseManagementUI.noRecordFoundInBridgeTableMsg();
            return;
        }
        // if has record, display the programme(s) that inside the bridge table only,
        //because only when the bridge table has the entry about the programme, user can remove the programme from a course
        courseManagementUI.displayOnlyProgrammesBelowHaveCourses();

        displayProgrammesThatHasCourses();
        String programmeID = validateInputProgrammeIDThatHasCourse();

        if (programmeID == null) {
            return;
        }

        Programme selectedProgramme = programmeMap.get(programmeID);

        SetInterface<String> coursesOfSelectedProgramme = new ArraySet<>();
        courseManagementUI.displayCoursesOfSelectProgrammeNote();
        displayCoursesOfSelectedProgramme(selectedProgramme, coursesOfSelectedProgramme);

        String courseID = validateInputCourseIdOfSelectedProgramme(coursesOfSelectedProgramme);

        //if valid courseID is keyed in
        if (courseID != null) {
            Course selectedCourse = courseMap.get(courseID);
            ProgrammeCourse programmeCourseToBeRemoved = new ProgrammeCourse(programmeID, courseID);

            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
                ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
                if (programmeCourseToBeRemoved.equals(programmeCourse)) {
                    programmeCourseList.remove(i);
                    programmesThatHasCourses.remove(selectedProgramme.getProgrammeId());
                    coursesThatHaveProgramme.remove(selectedCourse.getCourseId());
                    coursesOfSelectedProgramme.remove(selectedCourse.getCourseId());
                    courseManagementUI.removedCourseFromProgrammeSuccessMsg(selectedCourse, selectedProgramme);
//                    System.out.println("Course " + selectedCourse.getCourseName() + "is removed successfully from programme " + selectedProgramme.getProgrammeName());
                    programmeCourseDAO.saveToFile(programmeCourseList);
//                    System.out.println(programmeCourseList);
                    return;
                    //if the entry that want to be removed is found, exit method
                }
            }
            //if the entry that want to be removed is not found
            //display error message
            System.out.println("The course is not in the programme.");

        }

    }

    // FOR TASK 4
    private String validateInputCourseIdOfSelectedProgramme(SetInterface<String> coursesOfSelectedProgramme) {
        String courseID = null;
        boolean isValidFormat = false;
        boolean courseIDExist = false;
        String regexCourseID = "[A-Z]{4}\\d{4}";
        do {
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID().toUpperCase();

                if (!courseID.equals("999")) {
                    if (courseID.matches(regexCourseID)) {
                        isValidFormat = true;
                        if (coursesOfSelectedProgramme.contains(courseID)) {
                            courseIDExist = true;
                        } else {
                            courseManagementUI.displayCourseDontHaveThisProgramme();
                        }
                    } else {
                        courseManagementUI.displayCourseIDFormatIncorrect();
                    }
                } else {
                    courseID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !courseIDExist);
        return courseID;
    }

    // FOR TASK 4
    private String validateInputProgrammeIDThatHasCourse() {
        String programmeID = null;
        boolean isValidFormat = false;
        boolean programmeIDExist = false;
        String regexProgrammeID = "[A-Z]{3}";
        do {
            System.out.println("");
            try {
                programmeID = courseManagementUI.inputProgrammeID().toUpperCase();

                if (!programmeID.equals("999")) {
                    if (programmeID.matches(regexProgrammeID)) {
                        isValidFormat = true;
                        if (programmesThatHasCourses.contains(programmeID)) {
                            programmeIDExist = true;
                        } else {
                            courseManagementUI.displayThisProgrammeDontHaveAnyCourse();
                        }
                    } else {
                        courseManagementUI.displayProgrammeIDFormatIncorrect();
                    }
                } else {
                    programmeID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !programmeIDExist);
        return programmeID;
    }

    //FUNCTION FOR TASK 4
    public void displayProgrammesThatHasCourses() {

//        CourseManagement courseManagement = new CourseManagement();
        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
            String programmeID = programmeCourse.getProgrammeID();
            programmesThatHasCourses.add(programmeID);
            coursesThatHaveProgramme.add(programmeCourse.getCourseID());

        }

        String sb = "";
        for (int i = 0; i < programmesThatHasCourses.getNumberOfEntries(); i++) {

            sb += (programmeMap.get(programmesThatHasCourses.getEntry(i)) + "\n");
        }

        courseManagementUI.listProgrammes(sb);
    }

    //FUNCTION FOR TASK 4
    public void displayCoursesOfSelectedProgramme(Programme selectedProgramme, SetInterface<String> coursesOfSelectedProgramme) {

//        CourseManagement courseManagement = new CourseManagement();
        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
            String programmeID = programmeCourse.getProgrammeID();
            if (selectedProgramme.getProgrammeId().equals(programmeID)) {
                coursesOfSelectedProgramme.add(programmeCourse.getCourseID());
            }
        }

        String sb = "";
        for (int i = 0; i < coursesOfSelectedProgramme.getNumberOfEntries(); i++) {

            sb += (courseMap.get(coursesOfSelectedProgramme.getEntry(i)) + "\n");
        }

        courseManagementUI.listCourses(sb);
    }

    public SetInterface<String> selectStatusChoice() {

        int statusChoice = validateInputStatusChoice();

        SetInterface<String> status;
        SetInterface<String> status1 = new ArraySet<>();
        status1.add("Main");
        status1.add("Repeat");
        status1.add("Resit");
        status1.add("Elective");

        SetInterface<String> status2 = new ArraySet<>();
        status2.add("Main");
        status2.add("Repeat");
        status2.add("Resit");

        SetInterface<String> status3 = new ArraySet<>();
        status3.add("Main");
        status3.add("Repeat");

        SetInterface<String> status4 = new ArraySet<>();
        status4.add("Main");
        status4.add("Resit");

        switch (statusChoice) {
            case 1:
                status = status1;
                break;
            case 2:
                status = status2;
                break;
            case 3:
                status = status3;
                break;
            default:
                status = status4;
                break;
        }

        return status;
    }

    //TASK 3
    public void addNewCourseToProgrammes() {
        // Display Title first
        courseManagementUI.displayAddNewCourseTitle();

        String courseID = validateInputCourseIDForNew();

        if (courseID == null) {
            return;
        }

        String courseName = courseManagementUI.inputCourseName();

        courseManagementUI.displayStatusChoice();

        SetInterface<String> status = selectStatusChoice();

        int creditHours = validateInputCreditHours();

        Course course = new Course(courseID, courseName, status, creditHours);

        courseManagementUI.displayEnterProgrammeIDTitle();
        displayAllProgrammes();

        boolean continueAddCourse = true;
        do {
            String programmeID = validateInputProgrammeID();

            if (programmeID == null) {
                continueAddCourse = false;
            } else {

                int totalCreditHours = calculateTotalCreditHours(programmeMap.get(programmeID)); // Calculate total credit hours for the programme

                if (totalCreditHours + creditHours <= 18) { // Check if adding the course exceeds the limit

                    if (!courseMap.containsKey(courseID)) {
                        courseMap.put(courseID, course);
                        courseDAO.saveToFile(courseMap);
                    }
                    ProgrammeCourse programmeCourse = new ProgrammeCourse(programmeID, courseID);
                    if (!programmeCourseList.contains(programmeCourse)) {
                        programmeCourseList.add(programmeCourse);
                        courseManagementUI.newCourseAddedMsg(programmeID);
                        programmeCourseDAO.saveToFile(programmeCourseList);
                    } else {
                        courseManagementUI.alreadyAddedBeforeMsg(programmeID);
                    }
                } else {
                    System.out.println("Exceeds 18 total credit hours!");
                    //courseManagementUI.displayExceedTotalCreditHoursLimit(); // Display message for exceeding limit
                }
            }
        } while (continueAddCourse);
    }

    // FOR TASK 3
    private String validateInputCourseIDForNew() {

//        CourseManagement courseManagement = new CourseManagement();
        String courseID = null;
        boolean isValidFormat;
        boolean courseIDExist;
        String regexCourseID = "[A-Z]{4}\\d{4}"; //check format of input
        do {
            isValidFormat = false;
            courseIDExist = false;
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID();

                if (!courseID.equals("999")) { //999 to exit
                    if (courseID.matches(regexCourseID)) { //validate the format of the input here
                        isValidFormat = true;

                        if (courseMap.containsKey(courseID)) {
                            courseIDExist = true;                 // forbade the course registration since the ID exists
//                            System.out.println("IT EXISTS! TYPE AGAIN!");
                            courseManagementUI.courseIDExistErrorMsg();
                        } else {
                            //input statement here
//                            System.out.println("the right path");
                        }
                    } else {
                        courseManagementUI.displayCourseIDFormatIncorrectAndExample();
                    }
                } else {
                    courseID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || courseIDExist);
        return courseID;
    }

    // FOR TASK 3   
    private int validateInputStatusChoice() {
        int statusChoice = 0;
        boolean isValidInput = false;
        do {
            try {
                statusChoice = Integer.parseInt(courseManagementUI.inputCourseStatusChoice());
                if (statusChoice >= 1 && statusChoice <= 4) {
                    isValidInput = true;
                } else {
                    System.out.println("Only choose 1 to 4!");
                }

            } catch (NumberFormatException e) {
                isValidInput = false;
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidInput);
        return statusChoice;
    }

    // FOR TASK 3
    private int validateInputCreditHours() {
        int creditHours = 0;
        boolean isValidInput = false;
        do {
            try {
                creditHours = Integer.parseInt(courseManagementUI.inputCreditHours());
                if (creditHours >= 3 && creditHours <= 4) {
                    isValidInput = true;
                } else {
                    System.out.println("Only choose 3 or 4!");
                }

            } catch (NumberFormatException e) {
                isValidInput = false;
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidInput);
        return creditHours;
    }

    public MapInterface<String, Course> getCourseMap() {
        return courseMap;
    }

    public MapInterface<String, Programme> getProgrammeMap() {
        return programmeMap;
    }

    public ListInterface<ProgrammeCourse> getProgrammeCourseList() {
        return programmeCourseList;
    }

    // TASK 2
    // Remove a course from a programme
    public void removeProgrammeFromCourse() {
        courseManagementUI.displayRemoveProgrammeTitle();

        // if no any record in bridge table, display error message, exit this method
        if (programmeCourseList.isEmpty()) {
            courseManagementUI.noRecordFoundInBridgeTableMsg();
            return;
        }

        courseManagementUI.displayOnlyCoursesBelowHaveProgrammes();
        displayCoursesThatHasProgrammes();
        String courseID = validateInputCourseIDThatHaveProgramme();

        if (courseID == null) {
            return;
        }

        Course selectedCourse = courseMap.get(courseID);

        SetInterface<String> programmesOfSelectedCourse = new ArraySet<>();

        courseManagementUI.displayProgrammesOfSelectCourseNote();
        displayProgrammesOfSelectedCourse(selectedCourse, programmesOfSelectedCourse);

        String programmeID = validateInputProgrammeIdOfSelectedCourse(programmesOfSelectedCourse);

        //if valid courseID is keyed in
        if (programmeID != null) {
            Programme selectedProgramme = programmeMap.get(programmeID);
            ProgrammeCourse programmeCourseToBeRemoved = new ProgrammeCourse(programmeID, courseID);

            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
                ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
                if (programmeCourseToBeRemoved.equals(programmeCourse)) {
                    programmeCourseList.remove(i);
                    programmesThatHasCourses.remove(selectedProgramme.getProgrammeId());
                    coursesThatHaveProgramme.remove(selectedCourse.getCourseId());
                    programmesOfSelectedCourse.remove(selectedProgramme.getProgrammeId());
                    courseManagementUI.removeSuccessfullyFromCourseMsg(selectedProgramme, selectedCourse);
//                    System.out.println("Programme " + selectedProgramme.getProgrammeName() + "is removed successfully from course " + selectedCourse.getCourseName() + "!\n");
                    programmeCourseDAO.saveToFile(programmeCourseList);

                    return;
                    //if the entry that want to be removed is found, exit method
                }
            }
            //if the entry that want to be removed is not found
            //display error message
            System.out.println("The programme is not in the course.");

        }

    }

    private String validateInputProgrammeIdOfSelectedCourse(SetInterface<String> programmesOfSelectedCourse) {
        String programmeID = null;
        boolean isValidFormat = false;
        boolean programmeIDExist = false;
        String regexProgrammeID = "[A-Z]{3}";
        do {
            System.out.println("");
            try {
                programmeID = courseManagementUI.inputProgrammeID().toUpperCase();

                if (!programmeID.equals("999")) {
                    if (programmeID.matches(regexProgrammeID)) {
                        isValidFormat = true;
                        if (programmesOfSelectedCourse.contains(programmeID)) {
                            programmeIDExist = true;
                        } else {
                            courseManagementUI.displayProgrammeDontHaveThisCourse();
                        }
                    } else {
                        courseManagementUI.displayProgrammeIDFormatIncorrect();
                    }
                } else {
                    programmeID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !programmeIDExist);
        return programmeID;
    }

    public void displayProgrammesOfSelectedCourse(Course selectedCourse, SetInterface<String> programmesOfSelectedCourse) {

        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
            String courseID = programmeCourse.getCourseID();
            if (selectedCourse.getCourseId().equals(courseID)) {
                programmesOfSelectedCourse.add(programmeCourse.getProgrammeID());
            }
        }

        String sb = "";
        for (int i = 0; i < programmesOfSelectedCourse.getNumberOfEntries(); i++) {

            sb += (programmeMap.get(programmesOfSelectedCourse.getEntry(i)) + "\n");
        }

        courseManagementUI.listProgrammes(sb);
    }

    private String validateInputCourseIDThatHaveProgramme() {
        String courseID = null;
        boolean isValidFormat = false;
        boolean courseIDExist = false;
        String regexCourseID = "[A-Z]{4}\\d{4}";
        do {
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID().toUpperCase();

                if (!courseID.equals("999")) {
                    if (courseID.matches(regexCourseID)) {
                        isValidFormat = true;
                        if (coursesThatHaveProgramme.contains(courseID)) {
                            courseIDExist = true;
                        } else {
                            courseManagementUI.displayThisCourseDontHaveAnyProgramme();
                        }
                    } else {
                        courseManagementUI.displayCourseIDFormatIncorrect();
                    }
                } else {
                    courseID = null;
                    break;

                }

            } catch (InputMismatchException e) {
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidFormat || !courseIDExist);
        return courseID;
    }

    public void displayCoursesThatHasProgrammes() {

        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
            String courseID = programmeCourse.getCourseID();
            coursesThatHaveProgramme.add(courseID);
            programmesThatHasCourses.add(programmeCourse.getProgrammeID());

        }

        String sb = "";
        for (int i = 0; i < coursesThatHaveProgramme.getNumberOfEntries(); i++) {

            sb += (courseMap.get(coursesThatHaveProgramme.getEntry(i)) + "\n");
        }

        courseManagementUI.listCourses(sb);

    }

    //TASK 5
    public void searchCoursesOfferedInSemester() {
        String fuzzyInput;
        do {
            courseManagementUI.displaySearchCoursesTitle();
            fuzzyInput = courseManagementUI.inputFuzzy().toUpperCase(); // Convert input to uppercase
            if (!fuzzyInput.equals("999")) {
                boolean matchFound = false;
                if (fuzzyInput.matches("[A-Z]{4}\\d{4}")) {
                    Course matchingCourse = courseMap.get(fuzzyInput);
                    if (matchingCourse != null) {
                        courseManagementUI.listCourses(matchingCourse.toString());
                        matchFound = true;
                    }
                } else {
                    for (Course course : courseMap.values()) {
                        if (course.getCourseId().toUpperCase().matches(".*" + fuzzyInput + ".*")
                                || course.getCourseName().toUpperCase().matches(".*" + fuzzyInput + ".*")) {
                            courseManagementUI.listCoursesPrefix(course.toString());
                            matchFound = true;
                        }
                    }
                }
                // If no matches found, display a message
                if (!matchFound) {
                    courseManagementUI.displayNoMatchCourse();
                }
                System.out.println("\n\n");
            }
        } while (!fuzzyInput.equals("999"));
    }

    // TASK 6
    public void amendCourseDetailsForProgramme() {

        courseManagementUI.displayAmendTitle();
        if (programmeCourseList.isEmpty()) {
            courseManagementUI.noRecordFoundInBridgeTableMsg();
            return;
        }
        // if has record, display the programme(s) that inside the bridge table only,
        //because only when the bridge table has the entry about the programme, user can remove the programme from a course

        courseManagementUI.displayOnlyProgrammesBelowHaveCourses();
        displayProgrammesThatHasCourses();
        String programmeID = validateInputProgrammeIDThatHasCourse();

        if (programmeID == null) {
            return;
        }

        Programme selectedProgramme = programmeMap.get(programmeID);

        SetInterface<String> coursesOfSelectedProgramme = new ArraySet<>();

        courseManagementUI.displayCoursesOfSelectProgrammeNote();
        displayCoursesOfSelectedProgramme(selectedProgramme, coursesOfSelectedProgramme);

        String courseID = validateInputCourseIdOfSelectedProgramme(coursesOfSelectedProgramme);

        //if valid courseID is keyed in
        if (courseID != null) {
            //locate the course in HashMap
            Course course = courseMap.get(courseID);

            int choice = 0;
            do {
                choice = courseManagementUI.getAmendChoice(courseID);
                switch (choice) {
                    case 999:
                        MessageUI.displayBackMessage();
                        return;
                    case 1:
                        String courseName = courseManagementUI.inputCourseName();
                        course.setCourseName(courseName);
                        MessageUI.displayUpdateMessage();
                        break;
                    case 2:
                        courseManagementUI.displayStatusChoice();
                        SetInterface<String> status = selectStatusChoice();
                        course.setStatus(status);
                        MessageUI.displayUpdateMessage();
                        break;
                    case 3:
                        int creditHours = validateInputCreditHours();
                        course.setCreditHours(creditHours);
                        MessageUI.displayUpdateMessage();
                        break;
                    default:
                        MessageUI.displayInvalidChoiceMessage();

                }

                courseDAO.saveToFile(courseMap);

            } while (choice != 999);

        }

    }

    // TASK 7
    // List courses taken by different faculties
    public void listCoursesTakenByDifferentFaculties() {

        for (String facultyID : facultyMap.keys()) {
            SetInterface<String> coursesTakenByFaculty = new ArraySet<>();
            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {

                if (facultyMap.get(facultyID).getFacultyProgrammesMap().containsKey(programmeCourseList.getEntry(i).getProgrammeID())) {
                    coursesTakenByFaculty.add(programmeCourseList.getEntry(i).getCourseID());
                }
            }

            courseManagementUI.displayDifferentFacultiesTitle(facultyMap.get(facultyID).getFacultyName());

            if (!coursesTakenByFaculty.isEmpty()) {

                StringBuilder sb = new StringBuilder();

                Iterator ite = coursesTakenByFaculty.getIterator();
                while (ite.hasNext()) {
                    sb.append(courseMap.get(ite.next().toString()));
                    sb.append("\n");
                }

                courseManagementUI.listCourses(sb.toString());
            } else {
                System.out.println("\nNo courses taken by this faculty.\n");
            }
        }

    }

    //TASK 8
    // List all courses for a programme
    public void listAllCoursesForAProgramme() {
        courseManagementUI.displayListAllCoursesForAProgrammeTitle();
        courseManagementUI.displayOnlyProgrammesBelowHaveCourses();
        displayProgrammesThatHasCourses();
        String programmeID = validateInputProgrammeIDThatHasCourse();

        if (programmeID == null) {
            return;
        }
        SetInterface<String> coursesIDInAProgramme = new ArraySet<>();
        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            if (programmeCourseList.getEntry(i).getProgrammeID().equals(programmeID)) {
                coursesIDInAProgramme.add(programmeCourseList.getEntry(i).getCourseID());
            }
        }

        Iterator ite = coursesIDInAProgramme.getIterator();
        StringBuilder sb = new StringBuilder();

        while (ite.hasNext()) {
            sb.append(courseMap.get(ite.next().toString()));
            sb.append("\n");

        }

        courseManagementUI.displayCoursesInSpecificProgrammeTitle(programmeID);
        courseManagementUI.listCoursesInProgramme(sb.toString());
    }

    //SUMMARY REPORT 1
    public void summaryReport1() {
        courseManagementUI.displaySummaryReportTitle();

        if (programmeCourseList.isEmpty()) {
            System.out.println("\nThere is no any record yet!\n");
            courseManagementUI.endSummaryReport();
            return;
        }

        int numberOfCourses = 0;
        int numberOfMain = 0, numberOfRepeat = 0, numberOfResit = 0, numberOfElective = 0;

        int maxNumberOfTakenByProgrammes = 0;
        int minNumberOfTakenByProgrammes = Integer.MAX_VALUE;
        ListInterface<String> coursesWithMaxProgramme = new ArrayList<>();
        ListInterface<String> coursesWithMinProgramme = new ArrayList<>();

        int maxNumberOfFaculty = 0;
        int minNumberOfFaculty = Integer.MAX_VALUE;
        ListInterface<String> courseWithMaxFaculty = new ArrayList<>();
        ListInterface<String> courseWithMinFaculty = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        for (Course course : courseMap.values()) {
            int numberOfTakenByProgrammes = 0;
            SetInterface<String> takenByFaculty = new ArraySet<>();
            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
                if (programmeCourseList.getEntry(i).getCourseID().equals(course.getCourseId())) {
                    numberOfTakenByProgrammes++;

                    for (Faculty faculty : facultyMap.values()) {
                        if (faculty.getFacultyProgrammesMap().containsKey(programmeCourseList.getEntry(i).getProgrammeID())) {
                            takenByFaculty.add(faculty.getFacultyId());

                        }
                    }

                }
            }

            sb.append(++numberOfCourses);
            sb.append("\t");
            sb.append(course.toString());
            sb.append("\t\t");
            sb.append(numberOfTakenByProgrammes);
            sb.append(" / ");
            sb.append(takenByFaculty.getNumberOfEntries());
            sb.append("\n");

            SetInterface<String> status = course.getStatus();

            Iterator ite = status.getIterator();
            while (ite.hasNext()) {
                String currentStatus = (String) ite.next(); // Get the current status
                switch (currentStatus) {
                    case "Main" ->
                        numberOfMain++;
                    case "Resit" ->
                        numberOfResit++;
                    case "Repeat" ->
                        numberOfRepeat++;
                    case "Elective" ->
                        numberOfElective++;
                    default -> {
                    }
                }
            }

            if (numberOfTakenByProgrammes > maxNumberOfTakenByProgrammes) {
                maxNumberOfTakenByProgrammes = numberOfTakenByProgrammes;
                coursesWithMaxProgramme.clear();
                coursesWithMaxProgramme.add(course.getCourseId());

            } else if (numberOfTakenByProgrammes == maxNumberOfTakenByProgrammes) {
                coursesWithMaxProgramme.add(course.getCourseId()); // Add the course to the set if it has the same minimum number of programmes
            }

            if (numberOfTakenByProgrammes < minNumberOfTakenByProgrammes) {
                minNumberOfTakenByProgrammes = numberOfTakenByProgrammes;
                coursesWithMinProgramme.clear(); // Clear the previous set
                coursesWithMinProgramme.add(course.getCourseId()); // Add the new course to the set
            } else if (numberOfTakenByProgrammes == minNumberOfTakenByProgrammes) {
                coursesWithMinProgramme.add(course.getCourseId()); // Add the course to the set if it has the same minimum number of programmes
            }

            if (takenByFaculty.getNumberOfEntries() > maxNumberOfFaculty) {
                maxNumberOfFaculty = takenByFaculty.getNumberOfEntries();
                courseWithMaxFaculty.clear();
                courseWithMaxFaculty.add(course.getCourseId());
            } else if (takenByFaculty.getNumberOfEntries() == maxNumberOfFaculty) {
                courseWithMaxFaculty.add(course.getCourseId());
            }

            if (takenByFaculty.getNumberOfEntries() < minNumberOfFaculty) {
                minNumberOfFaculty = takenByFaculty.getNumberOfEntries();
                courseWithMinFaculty.clear();
                courseWithMinFaculty.add(course.getCourseId());
            } else if (takenByFaculty.getNumberOfEntries() == minNumberOfFaculty) {
                courseWithMinFaculty.add(course.getCourseId());
            }

        }
        courseManagementUI.listCoursesSummaryReport(sb.toString());
        courseManagementUI.displaySummaryReport1Middle(numberOfCourses, numberOfMain, numberOfResit, numberOfRepeat, numberOfElective);

        // if there are more than 1 courses with the same highest number of programmes offered, 
        Iterator coursesWithMaxProgrammesIte = coursesWithMaxProgramme.iterator();
        StringBuilder maxProgrammeString = new StringBuilder();
        int num = 0;
        while (coursesWithMaxProgrammesIte.hasNext()) {
            String courseId = coursesWithMaxProgrammesIte.next().toString();
            maxProgrammeString.append("\n");
            maxProgrammeString.append(++num);
            maxProgrammeString.append(".  <");
            maxProgrammeString.append(courseMap.get(courseId).getCourseId());
            maxProgrammeString.append("> ");
            maxProgrammeString.append(courseMap.get(courseId).getCourseName());
            maxProgrammeString.append("\n");
        }

//        System.out.println("Highest Programmes Offered: [" + maxNumberOfTakenByProgrammes + " Programmes] \n" + ss);
        courseManagementUI.displayHighestNoOfProgrammes(maxNumberOfTakenByProgrammes, maxProgrammeString);
        courseManagementUI.displayLineSummaryReport();

        Iterator coursesWithMinProgrammesIte = coursesWithMinProgramme.iterator();
        StringBuilder minProgrammeString = new StringBuilder();
        int no = 0;
        while (coursesWithMinProgrammesIte.hasNext()) {
            String courseId = coursesWithMinProgrammesIte.next().toString();
            minProgrammeString.append("\n");
            minProgrammeString.append(++no);
            minProgrammeString.append(".  <");
            minProgrammeString.append(courseMap.get(courseId).getCourseId());
            minProgrammeString.append("> ");
            minProgrammeString.append(courseMap.get(courseId).getCourseName());
            minProgrammeString.append("\n");
        }

        courseManagementUI.displayLowestNoOfProgrammes(minNumberOfTakenByProgrammes, minProgrammeString);
        

        Iterator coursesWithMaxFacultyIte = courseWithMaxFaculty.iterator();
        StringBuilder maxFacultyString = new StringBuilder();
        int indexMaxFaculty = 0;
        while (coursesWithMaxFacultyIte.hasNext()) {
            String courseId = coursesWithMaxFacultyIte.next().toString();
            maxFacultyString.append("\n");
            maxFacultyString.append(++indexMaxFaculty);
            maxFacultyString.append(".  <");
            maxFacultyString.append(courseMap.get(courseId).getCourseId());
            maxFacultyString.append("> ");
            maxFacultyString.append(courseMap.get(courseId).getCourseName());
            maxFacultyString.append("\n");
        }

        courseManagementUI.displayHighestNoOfFaculties(maxNumberOfFaculty, maxFacultyString);
        courseManagementUI.displayLineSummaryReport();

        Iterator coursesWithMinFacultyIte = courseWithMinFaculty.iterator();
        StringBuilder minFacultyString = new StringBuilder();
        int indexMinFaculty = 0;
        while (coursesWithMinFacultyIte.hasNext()) {
            String courseId = coursesWithMinFacultyIte.next().toString();
            minFacultyString.append("\n");
            minFacultyString.append(++indexMinFaculty);
            minFacultyString.append(".  <");
            minFacultyString.append(courseMap.get(courseId).getCourseId());
            minFacultyString.append("> ");
            minFacultyString.append(courseMap.get(courseId).getCourseName());
            minFacultyString.append("\n");
        }

        courseManagementUI.displayLowestNoOfFaculties(minNumberOfFaculty, minFacultyString);

        courseManagementUI.endSummaryReport();
    }

    public void summaryReport2() {
        courseManagementUI.displaySummaryReportTitle();
        if (programmeCourseList.isEmpty()) {
            System.out.println("\nThere is no any record yet!\n");
            courseManagementUI.endSummaryReport();
            return;
        }

        int maxTotalCreditHours = 0;
        int minTotalCreditHours = Integer.MAX_VALUE;
        int maxTotalCourses = 0; // Variable to track the maximum total courses
        int minTotalCourses = Integer.MAX_VALUE; // Variable to track the minimum total courses
        ListInterface<String> programmesWithMaxTotalCredit = new ArrayList<>();
        ListInterface<String> programmesWithMinTotalCredit = new ArrayList<>();
        ListInterface<String> programmesWithMaxTotalCourses = new ArrayList<>(); // List for programmes with the maximum total courses
        ListInterface<String> programmesWithMinTotalCourses = new ArrayList<>(); // List for programmes with the minimum total courses

        int noOfProgrammesUnderFOCS = 0;
        int noOfProgrammesUnderFOET = 0;
        int noOfProgrammesUnderFAFB = 0;
        int noOfProgrammesUnderFOAS = 0;

        StringBuilder sb = new StringBuilder();
        for (Programme programme : programmeMap.values()) {
            int totalCreditHours = 0;
            int totalCourses = 0; // New variable to count total courses for each program
            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
                if (programmeCourseList.getEntry(i).getProgrammeID().equals(programme.getProgrammeId())) {
                    totalCreditHours += courseMap.get(programmeCourseList.getEntry(i).getCourseID()).getCreditHours();
                    totalCourses++; // Increment the total courses count
                }
            }

            sb.append(programme.toString());
            sb.append("\t\t");
            sb.append(totalCreditHours);
            sb.append("\t\t");
            sb.append(totalCourses);
            sb.append("\n");

            // Check for max and min total credit hours
            if (totalCreditHours > maxTotalCreditHours) {
                maxTotalCreditHours = totalCreditHours;
                programmesWithMaxTotalCredit.clear(); // Clear the previous set
                programmesWithMaxTotalCredit.add(programme.getProgrammeId()); // Add the new programme to the set
            } else if (totalCreditHours == maxTotalCreditHours) {
                programmesWithMaxTotalCredit.add(programme.getProgrammeId()); // Add the programme to the set if it has the same maximum total credit hours
            }

            if (totalCreditHours < minTotalCreditHours) {
                minTotalCreditHours = totalCreditHours;
                programmesWithMinTotalCredit.clear(); // Clear the previous set
                programmesWithMinTotalCredit.add(programme.getProgrammeId()); // Add the new programme to the set
            } else if (totalCreditHours == minTotalCreditHours) {
                programmesWithMinTotalCredit.add(programme.getProgrammeId()); // Add the programme to the set if it has the same minimum total credit hours
            }

            // Check for max and min total courses
            if (totalCourses > maxTotalCourses) {
                maxTotalCourses = totalCourses;
                programmesWithMaxTotalCourses.clear(); // Clear the previous set
                programmesWithMaxTotalCourses.add(programme.getProgrammeId()); // Add the new programme to the set
            } else if (totalCourses == maxTotalCourses) {
                programmesWithMaxTotalCourses.add(programme.getProgrammeId()); // Add the programme to the set if it has the same maximum total courses
            }

            if (totalCourses < minTotalCourses) {
                minTotalCourses = totalCourses;
                programmesWithMinTotalCourses.clear(); // Clear the previous set
                programmesWithMinTotalCourses.add(programme.getProgrammeId()); // Add the new programme to the set
            } else if (totalCourses == minTotalCourses) {
                programmesWithMinTotalCourses.add(programme.getProgrammeId()); // Add the programme to the set if it has the same minimum total courses
            }

            for (Faculty faculty : facultyMap.values()) {
                if (faculty.getFacultyProgrammesMap().containsKey(programme.getProgrammeId())) {
                    switch (faculty.getFacultyId()) {
                        case "FOCS" ->
                            noOfProgrammesUnderFOCS++;
                        case "FOET" ->
                            noOfProgrammesUnderFOET++;
                        case "FAFB" ->
                            noOfProgrammesUnderFAFB++;
                        case "FOAS" ->
                            noOfProgrammesUnderFOAS++;
                        default -> {
                        }
                    }

                }
            }
        }

        courseManagementUI.listProgrammesSummaryReport2(sb.toString());
        courseManagementUI.displayTotalNoOfProgrammes(programmeMap);
        courseManagementUI.summaryReport2Middle(noOfProgrammesUnderFOCS, noOfProgrammesUnderFOET, noOfProgrammesUnderFAFB, noOfProgrammesUnderFOAS);

        courseManagementUI.displayProgrammeWithHighestCreditHr(maxTotalCreditHours);
        for (String programmeId : programmesWithMaxTotalCredit) {
            Programme programme = programmeMap.get(programmeId);
            System.out.println("-  <" + programme.getProgrammeId() + "> " + programme.getProgrammeName() + "\n");

        }
        courseManagementUI.displayLineSummaryReport();

        courseManagementUI.displayProgrammeWithLowestCreditHr(minTotalCreditHours);
        for (String programmeId : programmesWithMinTotalCredit) {
            Programme programme = programmeMap.get(programmeId);
            System.out.println("-  <" + programme.getProgrammeId() + "> " + programme.getProgrammeName() + "\n");
        }
        courseManagementUI.displayLineSummaryReport();

        // Display programmes with the highest and lowest total courses
        courseManagementUI.displayProgrammeWithMostCourses(maxTotalCourses);
        for (String programmeId : programmesWithMaxTotalCourses) {
            Programme programme = programmeMap.get(programmeId);
            System.out.println("-  <" + programme.getProgrammeId() + "> " + programme.getProgrammeName() + "\n");
        }

        courseManagementUI.displayLineSummaryReport();
        courseManagementUI.displayProgrammeWithLeastCourses(minTotalCourses);
        for (String programmeId : programmesWithMinTotalCourses) {
            Programme programme = programmeMap.get(programmeId);
            System.out.println("-  <" + programme.getProgrammeId() + "> " + programme.getProgrammeName() + "\n");
        }

        courseManagementUI.displayLineSummaryReport();
        courseManagementUI.endSummaryReport();
    }

}
