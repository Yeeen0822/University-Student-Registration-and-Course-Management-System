/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.ProgrammeCourse;

/**
 *
 * @author wongy
 */
public class ProgrammeCourseInitializer {
    
    private final ProgrammeCourseDAO programmeCourseDAO = new ProgrammeCourseDAO("programmeCourses.dat");
    
    public void initializeProgrammeCourses(){
        ListInterface<ProgrammeCourse> programmeCourseList = new ArrayList<>();
        
        programmeCourseDAO.saveToFile(programmeCourseList);
    }
}
