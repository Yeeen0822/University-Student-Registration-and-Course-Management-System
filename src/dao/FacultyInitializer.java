/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import adt.*;
import entity.*;
/**
 *
 * @author wongy
 */
public class FacultyInitializer {
    
    private final ProgrammeDAO programmeDAO = new ProgrammeDAO("programmes.dat");
    private MapInterface<String, Programme> programmeMap = new HashMap<>();
    
    public FacultyInitializer(){
        programmeMap = programmeDAO.retrieveFromFile();
    }
    
    private final FacultyDAO facultyDAO = new FacultyDAO("faculties.dat");
    public void initializeFaculties(){
        MapInterface<String,Faculty> facultyMap = new HashMap<>();
        facultyMap.put("FOCS", new Faculty("FOCS", "Faculty of Computer Science and Information Technology"));
        facultyMap.put("FAFB", new Faculty("FAFB", "Faculty of Accountancy, Finance and Business"));
        facultyMap.put("FOET", new Faculty("FOET", "Faculty of Electronic Engineering"));
        facultyMap.put("FOAS", new Faculty("FOAS", "Faculty of Applied Science"));
       
        
        facultyMap.get("FOCS").addProgrammeToFaculty("RDS", programmeMap.get("RDS"));
        facultyMap.get("FOCS").addProgrammeToFaculty("RSW", programmeMap.get("RSW"));
        facultyMap.get("FOCS").addProgrammeToFaculty("RIS", programmeMap.get("RIS"));
        facultyMap.get("FOCS").addProgrammeToFaculty("DIT", programmeMap.get("DIT"));
        facultyMap.get("FOCS").addProgrammeToFaculty("DIS", programmeMap.get("DIS"));
        
        facultyMap.get("FAFB").addProgrammeToFaculty("RBA", programmeMap.get("RBA"));
        facultyMap.get("FAFB").addProgrammeToFaculty("RBF", programmeMap.get("RBF"));
        
        facultyMap.get("FOET").addProgrammeToFaculty("RME", programmeMap.get("RME"));
        facultyMap.get("FOET").addProgrammeToFaculty("REE", programmeMap.get("REE"));
        
        facultyMap.get("FOAS").addProgrammeToFaculty("RIA", programmeMap.get("RIA"));
        facultyMap.get("FOAS").addProgrammeToFaculty("RQS", programmeMap.get("RQS"));
        
        facultyDAO.saveToFile(facultyMap);
        System.out.println("Faculty data is initialized successfully");
    }
    
    
//    public static void main(String args[]){
//        
//        FacultyInitializer f = new FacultyInitializer();
//        MapInterface<String,Faculty> facultyMap = f.initializeFaculties();
//        System.out.println(facultyMap);
//    }
}
