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
    
    private final FacultyDAO facultyDAO = new FacultyDAO("faculties.dat");
    public void initializeFaculties(){
        MapInterface<String,Faculty> facultyMap = new HashMap<>();
        facultyMap.put("FOCS", new Faculty("FOCS", "Faculty of Computer Science and Information Technology"));
        facultyMap.put("FAFB", new Faculty("FAFB", "Faculty of Accountancy, Finance and Business"));
        facultyMap.put("FOET", new Faculty("FOET", "Faculty of Electronic Engineering"));
        facultyMap.put("FOAS", new Faculty("FOAS", "Faculty of Applied Science"));
       
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
