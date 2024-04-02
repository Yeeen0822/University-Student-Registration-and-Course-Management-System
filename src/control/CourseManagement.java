/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import dao.*;
import boundary.*;
import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.InputMismatchException;
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

    SetInterface<String> programmesThatHasCourse_s = new ArraySet<>();

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
//                }
//                case 5 : {
//                    if (!isProgrammeMapEmpty()) {
//                        int choice2 = 0;
//                        do {
//                            choice2 = programmeManagementUI.getSearchMenuChoice();
//                            switch (choice2) {
//                                case 1 -> searchProgrammeByCode();
//                                case 2 -> searchProgrammeByName();
//                                case 0 -> isExit = true;
//                                default -> programmeManagementUI.displayInvalidChoice();
//                            }
//                            if (choice2 != 0)
//                                programmeManagementUI.pressEnterToContinue();
//
//                        } while (choice2 != 0);
//                    }
//
//                }
//                case 6 : {
//                    if (!isTutorialGroupMapEmpty())
//                        listAllTutorialGroup();
//                }
//                case 7 : createTutorialGroup();
                case 8: {
//                    listAllCoursesForProgramme();
                    break;
                }
//                case 9 : {
//                    if (!isProgrammeMapEmpty() && !isTutorialGroupMapEmpty()) {
//                        displayAllProgramme();
//                        removeTutorialGroupFromProgramme();
//                    }
//                }
//                case 10 : {
//                    if (!isProgrammeMapEmpty()) {
//                        displayAllProgramme();
//                        listAllTutorialGroupInProgramme();
//                    }
//                }
//                case 11 : {
//                    if (!isProgrammeMapEmpty()) {
//                        displayAllProgramme();
//                        generateProgrammeReport();
//                    }
//                }
//                case 12 : generateDummyData();
                case 0: {
                    courseManagementUI.displayExitMessage();
                    isExit = true;
                    break;
                }
                default:
                    courseManagementUI.displayInvalidChoice();
            }
//            if (!isExit) {
//                courseManagementUI.pressEnterToContinue();
//            }
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
            start();
            return;
        }

        Programme programme = programmeMap.get(programmeID);
        displayAllCourses();

        boolean continueToAdd = true;

        do {
            if (!continueToAdd) {
                programmeCourseDAO.saveToFile(programmeCourseList);
                return;
            }

            String courseID = validateInputCourseID();

            if (courseID == null) {
                continueToAdd = false;
            } else {
                Course course = courseMap.get(courseID);
                ProgrammeCourse programmeCourse = new ProgrammeCourse(programme.getProgrammeId(), course.getCourseId());

                if (programmeCourseList.contains(programmeCourse)) {
                    courseManagementUI.displayProgrammeHasBeenAddedBefore(programme);
                } else {
                    programmeCourseList.add(programmeCourse);
                    courseManagementUI.displayProgrammeIsSuccessfullyAddedToCourse(course, programme);
                }
            }
        } while (continueToAdd);
        System.out.println(programmeCourseList);
    }

    //Task 2
    //Remove a programme from a course
    public void removeProgrammeFromCourse() {
        courseManagementUI.displayRemoveProgrammeTitle();

//        CourseManagement courseManagement = new CourseManagement();
        // if no any record in bridge table, display error message, exit this method
        if (programmeCourseList.isEmpty()) {
            System.out.println("There is no record found.");
            return;
        }
        // if has record, display the programme(s) that inside the bridge table only,
        //because only when the bridge table has the entry about the programme, user can remove the programme from a course

        displayProgrammesThatHasCourse_s();
        String programmeID = validateInputProgrammeIDForTask2();

        if (programmeID == null) {
            start();
            return;
        }

        Programme selectedProgramme = programmeMap.get(programmeID);

        SetInterface<String> coursesOfSelectedProgramme = new ArraySet<>();
        displayCoursesOfSelectedProgramme(selectedProgramme, coursesOfSelectedProgramme);

        String courseID = validateInputCourseIDForTask2(coursesOfSelectedProgramme);

        //if valid courseID is keyed in
        if (courseID != null) {
            Course selectedCourse = courseMap.get(courseID);
            ProgrammeCourse programmeCourseToBeRemoved = new ProgrammeCourse(programmeID, courseID);

            for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
                ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
                if (programmeCourseToBeRemoved.equals(programmeCourse)) {
                    programmeCourseList.remove(i);
                    programmesThatHasCourse_s.remove(selectedProgramme.getProgrammeId());
                    coursesOfSelectedProgramme.remove(selectedCourse.getCourseId());
                    System.out.println("Programme " + selectedProgramme.getProgrammeName() + "is removed successfully from course " + selectedCourse.getCourseName());
                    programmeCourseDAO.saveToFile(programmeCourseList);
                    System.out.println(programmeCourseList);
                    return;
                    //if the entry that want to be removed is found, exit method
                }
            }
            //if the entry that want to be removed is not found
            //display error message
            System.out.println("The programme is not in the course.");

        }

    }

    // FOR TASK 2
    private String validateInputCourseIDForTask2(SetInterface<String> coursesOfSelectedProgramme) {
        String courseID = null;
        boolean isValidFormat = false;
        boolean courseIDExist = false;
        String regexCourseID = "[A-Z]{4}\\d{4}";
        do {
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID();

                if (!courseID.equals("999")) {
                    if (courseID.matches(regexCourseID)) {
                        isValidFormat = true;
                        if (coursesOfSelectedProgramme.contains(courseID)) {
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

    // FOR TASK 2
    private String validateInputProgrammeIDForTask2() {
        String programmeID = null;
        boolean isValidFormat = false;
        boolean programmeIDExist = false;
        String regexProgrammeID = "[A-Z]{3}";
        do {
            System.out.println("");
            try {
                programmeID = courseManagementUI.inputProgrammeID();

                if (!programmeID.equals("999")) {
                    if (programmeID.matches(regexProgrammeID)) {
                        isValidFormat = true;
                        if (programmesThatHasCourse_s.contains(programmeID)) {
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

    //FUNCTION FOR TASK 2 
    public void displayProgrammesThatHasCourse_s() {

//        CourseManagement courseManagement = new CourseManagement();
        for (int i = 1; i <= programmeCourseList.getNumberOfEntries(); i++) {
            ProgrammeCourse programmeCourse = programmeCourseList.getEntry(i);
            String programmeID = programmeCourse.getProgrammeID();
            programmesThatHasCourse_s.add(programmeID);
        }

        String sb = "";
        for (int i = 0; i < programmesThatHasCourse_s.getNumberOfEntries(); i++) {

            sb += (programmeMap.get(programmesThatHasCourse_s.getEntry(i)) + "\n");
        }

        courseManagementUI.listProgrammes(sb);
    }

    //FUNCTION FOR TASK 2
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

    public void displayAllProgrammes() {
        StringBuilder sb = new StringBuilder();
        for (Programme programme : programmeMap.values()) {
            sb.append(programme.toString());
            sb.append("\n");
        }
        courseManagementUI.listProgrammes(sb.toString());
    }

    public void displayAllCourses() {
        StringBuilder sb = new StringBuilder();
        for (Course course : courseMap.values()) {
            sb.append(course.toString());
            sb.append("\n");
        }
        courseManagementUI.listCourses(sb.toString());
    }

    private String validateInputProgrammeID() {
        String programmeID = null;
        boolean isValidFormat = false;
        boolean programmeIDExist = false;
        String regexProgrammeID = "[A-Z]{3}";
        do {
            System.out.println("");
            try {
                programmeID = courseManagementUI.inputProgrammeID();

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

    private String validateInputCourseID() {
        String courseID = null;
        boolean isValidFormat = false;
        boolean courseIDExist = false;
        String regexCourseID = "[A-Z]{4}\\d{4}";
        do {
            System.out.println("");
            try {
                courseID = courseManagementUI.inputCourseID();

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

//    private String validateProgrammeIDExist(String programmeID) {
//        
//        while (!programmeMap.containsKey(programmeID)) {
//            courseManagementUI.displayProgrammeDoesNotExist();
//            programmeID = validateInputProgrammeID();
//        }
//        return programmeID;
//    }
    //Task 8 done
//    public void listAllCoursesForProgramme() {
//
//        displayAllProgrammes();
//        String programmeID = validateInputProgrammeID();
//
//        if (programmeID == null) {
//            start();
//        } else {
//            if (programmeMap.get(programmeID).getProgrammeCoursesMap().isEmpty()) {
//                courseManagementUI.displayNoCourseInProgramme();
//                return;
//            }
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (Course course : programmeMap.get(programmeID).getProgrammeCoursesMap().values()) {
//            sb.append(course.toString());
//            sb.append("\n");
//        }
//        courseManagementUI.listCoursesInProgramme(sb.toString());
//    }
    private String validateInputCourseIDForNew() {
        
        CourseManagement courseManagement = new CourseManagement();
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
                            System.out.println("IT EXISTS!! TYPE AGAIN!");
                        } else {
                            courseManagementUI.displayNoMatchCourseID();
                            //input statement here
                            System.out.println("the right path");
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
        } while (!isValidFormat || courseIDExist);
        return courseID;
    }

    // TASK 3
    public void addNewCourseToProgrammes() {
        // 1.Accept new courseID
        // - if the input courseID is the same with the existing course's id
        // - display courseID already be taken, reprompt
        // - else  break the reprompt loop
        Scanner sc = new Scanner(System.in);
        String courseID = validateInputCourseIDForNew();

        if (courseID == null) {
            start();
            return;
        }
        String courseName = courseManagementUI.inputCourseName();
        courseManagementUI.displayStatusChoice();

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

        if (statusChoice == 1) {
            status = status1;
        } else {
            if (statusChoice == 2) {
                status = status2;
            } else {
                if (statusChoice == 3) {
                    status = status3;
                } else {
                    status = status4;
                }
            }
        }

        int creditHours = validateInputCreditHours();

        Course course = new Course(courseID, courseName, status, creditHours);
        displayAllProgrammes();

        boolean continueAddCourse = true;
        do {
            String programmeID = validateInputProgrammeID();

            if (programmeID == null) {
                continueAddCourse = false;
            } else {
                if (!courseMap.containsKey(courseID)) {
                    courseMap.put(courseID, course);
                    courseDAO.saveToFile(courseMap);
                    System.out.println(courseMap);
                }

                ProgrammeCourse programmeCourse = new ProgrammeCourse(programmeID, courseID);
                if (!programmeCourseList.contains(programmeCourse)) {
                    programmeCourseList.add(programmeCourse);
                    System.out.println("New course is successfully added to programme " + programmeID);
                    System.out.println(programmeCourseList);
                    programmeCourseDAO.saveToFile(programmeCourseList);
                }else{
                    System.out.println("It has been added to programme " + programmeID + "before!");
                }

            }
        } while (continueAddCourse);

    }
    
    // FOR TASK 3   
    private int validateInputStatusChoice() {
        int statusChoice = 0;
        boolean isValidInput = false;
        do {
            try {
                statusChoice = Integer.parseInt(courseManagementUI.inputCourseStatusChoice());
                if(statusChoice >= 1 && statusChoice <= 4){
                    isValidInput = true;
                }else{
                    System.out.println("Only choose 1 to 4!");
                }
                
            } catch (NumberFormatException e) {
                isValidInput = false;
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidInput);
        return statusChoice;
    }
    
    private int validateInputCreditHours() {
        int creditHours = 0;
        boolean isValidInput = false;
        do {
            try {
                creditHours = Integer.parseInt(courseManagementUI.inputCreditHours());
                if(creditHours >= 3 && creditHours <= 4){
                    isValidInput = true;
                }else{
                    System.out.println("Only choose 3 or 4!");
                }
                
            } catch (NumberFormatException e) {
                isValidInput = false;
                courseManagementUI.displayInvalidInput();
            }
        } while (!isValidInput);
        return creditHours;
    }

//    public void addNewCourseToProgrammes() {
//        String courseID = validateInputCourseIDForNew();
//        Scanner sc = new Scanner(System.in);
//        if (courseID == null) {
//            start();
//        } else {
//            String courseName = courseManagementUI.inputCourseName();
//            courseManagementUI.displayStatusChoice();
//
//            int statusChoice = 0;
//            boolean isValidStatusChoice;
//
//            do {
//                isValidStatusChoice = true;
//                statusChoice = 0;
//                System.out.println("");
//
//                try {
//
//                    statusChoice = courseManagementUI.inputCourseStatusChoice();
//                    if (statusChoice >= 1 && statusChoice <= 4) {
//
//                    } else {
//                        courseManagementUI.displayInvalidChoice();
//                        isValidStatusChoice = false;
//
//                    }
//
//                } catch (InputMismatchException e) {
//                    isValidStatusChoice = false;
//                    courseManagementUI.displayInvalidChoice();
//                    sc.nextLine();
//                }
//
//            } while (!isValidStatusChoice);
//
//            SetInterface<String> status;
//            SetInterface<String> status1 = new ArraySet<>();
//            status1.add("Main");
//            status1.add("Repeat");
//            status1.add("Resit");
//            status1.add("Elective");
//
//            SetInterface<String> status2 = new ArraySet<>();
//            status2.add("Main");
//            status2.add("Repeat");
//            status2.add("Resit");
//
//            SetInterface<String> status3 = new ArraySet<>();
//            status3.add("Main");
//            status3.add("Repeat");
//
//            SetInterface<String> status4 = new ArraySet<>();
//            status4.add("Main");
//            status4.add("Resit");
//
//            if (statusChoice == 1) {
//                status = status1;
//            } else {
//                if (statusChoice == 2) {
//                    status = status2;
//                } else {
//                    if (statusChoice == 3) {
//                        status = status3;
//                    } else {
//                        status = status4;
//                    }
//                }
//            }
//
//            int creditHrs = 0;
//            boolean isValidCreditHrs;
//            do {
//                isValidCreditHrs = false;
//                System.out.println("");
//                try {
//                    creditHrs = courseManagementUI.inputCreditHours();
//                    if (creditHrs == 3 || creditHrs == 4) {
//                        isValidCreditHrs = true;
//                    } else {
//                        courseManagementUI.displayCreditHoursInvalid();
//                    }
//
//                } catch (NumberFormatException e) {
//                    isValidCreditHrs = false;
//                    courseManagementUI.displayInvalidChoice();
//                }
//
//            } while (!isValidCreditHrs);
//
//            Course course = new Course(courseID, courseName, status, creditHrs);
//
//            displayAllProgrammes();
//
//            boolean continueAddCourse = true;
//            do {
//                String programmeID = validateInputProgrammeID();
//                if (programmeID == null) {
//                    continueAddCourse = false;
//                } else {
//                    programmeMap.get(programmeID).addCourse(course);
//                    courseMap.put(courseID, course);
//                    courseDAO.saveToFile(courseMap);
//                }
//            } while (continueAddCourse);
//
//        }
//
//        // input course name,
//        // input status , arrayset, give choices, choose, if not integer then re-prompt
//        //input credit hours, if not integer then re-prompt 3/4
//        //Course is made
//        // display programmes
//        // input programme id
//        // programme.getcourseMap.put
//        // then while loop to continue the same process 
//    }
    public MapInterface<String, Course> getCourseMap() {
        return courseMap;
    }

}
