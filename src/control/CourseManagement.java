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
import java.util.Scanner;

/**
 *
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 */
public class CourseManagement implements Serializable {

    private MapInterface<String, Faculty> facultyMap = new HashMap<>();
    private MapInterface<String, Programme> programmeMap = new HashMap<>();
    private MapInterface<String, Course> courseMap = new HashMap<>();

    private final FacultyDAO facultyDAO = new FacultyDAO("faculties.dat");
    private final ProgrammeDAO programmeDAO = new ProgrammeDAO("programmes.dat");
    private final CourseDAO courseDAO = new CourseDAO("courses.dat");
    private final CourseManagementUI courseManagementUI = new CourseManagementUI();

    public CourseManagement() {
        facultyMap = facultyDAO.retrieveFromFile();
        programmeMap = programmeDAO.retrieveFromFile();
        courseMap = courseDAO.retrieveFromFile();
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
                    listAllCoursesForProgramme();
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

//    public void intializeDummyData() {
//
//        // Step 1  ( FOCS,FAFB,FOET,FOAS )
//        //Initialize 4 faculties
//        facultyMap.put("FOCS", new Faculty("FOCS", "Faculty of Computer Science and Information Technology"));
//        facultyMap.put("FAFB", new Faculty("FAFB", "Faculty of Accountancy, Finance and Business"));
//        facultyMap.put("FOET", new Faculty("FOET", "Faculty of Electronic Engineering"));
//        facultyMap.put("FOAS", new Faculty("FOAS", "Faculty of Applied Science"));
//
//        //Step 2 (FOCS)
//        //Initialize 5 programmes under faculty of FOCS into programmeMap 
//        programmeMap.put("RDS", new Programme("RDS", "Bachelor of Computer Scicence (Data Science)"));
//        programmeMap.put("RSW", new Programme("RSW", "Bachelor of Computer Scicence (Software Engineering)"));
//        programmeMap.put("RIS", new Programme("RIS", "Bachelor of Computer Scicence (Interactive Software)"));
//        programmeMap.put("DIT", new Programme("RIT", "Diploma in Information Technology"));
//        programmeMap.put("DIS", new Programme("DIS", "Diploma in Information System"));
//
//        // Step 3 (FOCS)
//        // Add Programmes to Faculty (FOCS)
//        // So FOCS has 5 programmes 
//        facultyMap.get("FOCS").addProgrammeToFaculty("RDS", programmeMap.get("RDS"));
//        facultyMap.get("FOCS").addProgrammeToFaculty("RSW", programmeMap.get("RSW"));
//        facultyMap.get("FOCS").addProgrammeToFaculty("RIS", programmeMap.get("RIS"));
//        facultyMap.get("FOCS").addProgrammeToFaculty("DIT", programmeMap.get("DIT"));
//        facultyMap.get("FOCS").addProgrammeToFaculty("DIS", programmeMap.get("DIS"));
//
//        //Step 2 (FAFB)
//        //Initialize 2 programmes under faculty of FAFB into programmeMap 
//        programmeMap.put("RBA", new Programme("RBA", "Bachelor of Business Analytics"));
//        programmeMap.put("RBF", new Programme("RBF", "Bachelor of Business and Finance"));
////
////        // Step 3 (FAFB)
////        // Add Programmes to Faculty (FAFB)
////        // So FAFB has 2 programmes 
//        facultyMap.get("FAFB").addProgrammeToFaculty("RBA", programmeMap.get("RBA"));
//        facultyMap.get("FAFB").addProgrammeToFaculty("RBF", programmeMap.get("RBF"));
////
//        //Step 2 (FOET)
//        //Initialize 2 programmes under faculty of FOET into programmeMap 
//        programmeMap.put("RME", new Programme("RME", "Bachelor of Mechanical Engineering"));
//        programmeMap.put("REE", new Programme("REE", "Bachelor of Electrical and Electronics Engineering"));
////
////        // Step 3 (FOET)
////        // Add Programmes to Faculty (FOET)
////        // So FOET has 2 programmes 
//        facultyMap.get("FOET").addProgrammeToFaculty("RME", programmeMap.get("RME"));
//        facultyMap.get("FOET").addProgrammeToFaculty("REE", programmeMap.get("REE"));
//
//        // Step 2 (FOAS)
//        //Initialize 2 programmes under faculty of FOAS into programmeMap 
//        programmeMap.put("RIA", new Programme("RIA", "Bachelor of Interior Architecture"));
//        programmeMap.put("RQS", new Programme("RQS", "Bachelor of Quantity Surverying"));
//
////        // Step 3 (FOAS)
//        facultyMap.get("FOAS").addProgrammeToFaculty("RIA", programmeMap.get("RIA"));
//        facultyMap.get("FOAS").addProgrammeToFaculty("RQS", programmeMap.get("RQS"));
//
//        SetInterface<String> mainResit = new ArraySet<>();
//        mainResit.add("Main");
//        mainResit.add("Resit");
//
//        SetInterface<String> mainResitRepeat = new ArraySet<>();
//        mainResitRepeat.add("Main");
//        mainResitRepeat.add("Resit");
//        mainResitRepeat.add("Repeat");
//
//        SetInterface<String> mainRepeat = new ArraySet<>();
//        mainRepeat.add("Main");
//        mainRepeat.add("Repeat");
//
//        SetInterface<String> mainResitRepeatElective = new ArraySet<>();
//        mainResitRepeatElective.add("Main");
//        mainResitRepeatElective.add("Resit");
//        mainResitRepeatElective.add("Repeat");
//        mainResitRepeatElective.add("Elective");
//
//        // Initialize Courses
//        courseMap.put("BAIT1023", new Course("BAIT1023", "Web Design and Development", mainResit, 3));
//        courseMap.put("BACS1053", new Course("BACS1053", "Database Management", mainResitRepeat, 4));
//        courseMap.put("BACS2023", new Course("BACS2023", "Object-Oriented Programming", mainResitRepeatElective, 4));
//        courseMap.put("BJEL1023", new Course("BJEL1023", "Academic English", mainResit, 3));
//        courseMap.put("BJEL1013", new Course("BJEL1013", "English For Tertiary Studies", mainRepeat, 3));
//        courseMap.put("BFAI2133", new Course("BFAI2133", "Introduction to Economy", mainResitRepeatElective, 4));
//
//        facultyDAO.saveToFile(facultyMap);
//        programmeDAO.saveToFile(programmeMap);
//        courseDAO.saveToFile(courseMap);
//        System.out.println("Dummy data generated and saved to file.");
//    }
    // TASK 1
    // Add a Prorgramme to Courses
    public void addProgrammetoCourses() {
        //Display Title first
        courseManagementUI.displayAddProgrammeTitle();
        //Display all programmes available
        displayAllProgrammes();
        String programmeID = validateInputProgrammeID();
        if (programmeID == null) {
            start();
        } else {
            Programme programme = programmeMap.get(programmeID);
            displayAllCourses();
            boolean continueToAdd = true;
            do {
                String courseID = validateInputCourseID();
                if (courseID == null) {
                    continueToAdd = false;
                } else {
                    Course course = courseMap.get(courseID);
                    if (!course.getCourseProgrammesMap().isEmpty()) {
                        if (course.getCourseProgrammesMap().containsKey(programme.getProgrammeId())) {
                            courseManagementUI.displayProgrammeHasBeenAddedBefore(programme);
                        } else {
                            course.addProgramme(programme);
                            courseManagementUI.displayProgrammeIsSuccessfullyAddedToCourse(course, programme);
                        }
                    } else {
                        course.addProgramme(programme);
                        courseManagementUI.displayProgrammeIsSuccessfullyAddedToCourse(course, programme);
                    }

//                    if (programme.getProgrammeCoursesMap().containsKey(courseID)) {
//                        courseManagementUI.displayCourseHasBeenAddedBefore(course);
//                    } else {
//                        programme.addCourse(course);
//                        courseManagementUI.displayCourseIsSuccessfullyAddedToProgramme(course, programme);
//                    }
                }

            } while (continueToAdd);
            courseDAO.saveToFile(courseMap);
        }
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
    public void listAllCoursesForProgramme() {

        displayAllProgrammes();
        String programmeID = validateInputProgrammeID();

        if (programmeID == null) {
            start();
        } else {
            if (programmeMap.get(programmeID).getProgrammeCoursesMap().isEmpty()) {
                courseManagementUI.displayNoCourseInProgramme();
                return;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Course course : programmeMap.get(programmeID).getProgrammeCoursesMap().values()) {
            sb.append(course.toString());
            sb.append("\n");
        }
        courseManagementUI.listCoursesInProgramme(sb.toString());
    }

    private String validateInputCourseIDForNew() {
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

    public void addNewCourseToProgrammes() {
        String courseID = validateInputCourseIDForNew();
        Scanner sc = new Scanner(System.in);
        if (courseID == null) {
            start();
        } else {
            String courseName = courseManagementUI.inputCourseName();
            courseManagementUI.displayStatusChoice();

            int statusChoice = 0;
            boolean isValidStatusChoice;

            do {
                isValidStatusChoice = true;
                statusChoice = 0;
                System.out.println("");

                try {
                  
                    statusChoice = courseManagementUI.inputCourseStatusChoice();
                    sc.nextLine();

                    if (statusChoice >= 1 && statusChoice <= 4) {
                        

                    } else {
                        courseManagementUI.displayInvalidChoice();
                        isValidStatusChoice = false;

                    }

                } catch (InputMismatchException e) {
                    isValidStatusChoice = false;
                    courseManagementUI.displayInvalidChoice();
                    sc.nextLine();
                }

            } while (!isValidStatusChoice);

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

            int creditHrs = 0;
            boolean isValidCreditHrs;
            do {
                isValidCreditHrs = false;
                System.out.println("");
                try {
                    creditHrs = courseManagementUI.inputCreditHours();
                    if (creditHrs == 3 || creditHrs == 4) {
                        isValidCreditHrs = true;
                    } else {
                        courseManagementUI.displayCreditHoursInvalid();
                    }

                } catch (NumberFormatException e) {
                    isValidCreditHrs = false;
                    courseManagementUI.displayInvalidChoice();
                }

            } while (!isValidCreditHrs);

            Course course = new Course(courseID, courseName, status, creditHrs);

            displayAllProgrammes();

            boolean continueAddCourse = true;
            do {
                String programmeID = validateInputProgrammeID();
                if (programmeID == null) {
                    continueAddCourse = false;
                } else {
                    programmeMap.get(programmeID).addCourse(course);
                    courseMap.put(courseID, course);
                    courseDAO.saveToFile(courseMap);
                }
            } while (continueAddCourse);

        }

        // input course name,
        // input status , arrayset, give choices, choose, if not integer then re-prompt
        //input credit hours, if not integer then re-prompt 3/4
        //Course is made
        // display programmes
        // input programme id
        // programme.getcourseMap.put
        // then while loop to continue the same process 
    }

    public MapInterface<String, Course> getCourseMap() {
        return courseMap;
    }

}
