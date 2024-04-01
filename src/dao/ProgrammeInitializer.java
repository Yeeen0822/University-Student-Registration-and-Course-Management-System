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
public class ProgrammeInitializer {
    private final ProgrammeDAO programmeDAO = new ProgrammeDAO("programmes.dat");
    
    
    
    public void initializeProgrammes(){
         
        MapInterface <String,Programme> programmeMap = new HashMap<>();
        programmeMap.put("RDS", new Programme("RDS", "Bachelor of Computer Scicence (Data Science)"));
        programmeMap.put("RSW", new Programme("RSW", "Bachelor of Computer Scicence (Software Engineering)"));
        programmeMap.put("RIS", new Programme("RIS", "Bachelor of Computer Scicence (Interactive Software)"));
        programmeMap.put("DIT", new Programme("RIT", "Diploma in Information Technology"));
        programmeMap.put("DIS", new Programme("DIS", "Diploma in Information System"));
        programmeMap.put("RBA", new Programme("RBA", "Bachelor of Business Analytics"));
        programmeMap.put("RBF", new Programme("RBF", "Bachelor of Business and Finance"));
        programmeMap.put("RME", new Programme("RME", "Bachelor of Mechanical Engineering"));
        programmeMap.put("REE", new Programme("REE", "Bachelor of Electrical and Electronics Engineering"));
        programmeMap.put("RIA", new Programme("RIA", "Bachelor of Interior Architecture"));
        programmeMap.put("RQS", new Programme("RQS", "Bachelor of Quantity Surverying"));
        
        
        programmeDAO.saveToFile(programmeMap);
    }
}
