/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.HashMap;
import adt.MapInterface;
import entity.Faculty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Name: Wong Yee En RDS2S2G3 22WMR13659
 */
public class FacultyDAO {
    
    private String fileName;
    
    public FacultyDAO(String fileName) {
        this.fileName = fileName;
    }
    
    public void saveToFile(MapInterface<String, Faculty> faculties) {
        File file = new File(fileName);
//        String filePath = file.getAbsolutePath();
//        System.out.println("File saved to: " + filePath);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(faculties);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public MapInterface<String, Faculty> retrieveFromFile() {
        File file = new File(fileName);
        MapInterface<String, Faculty> faculties = new HashMap<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            faculties = (HashMap<String, Faculty>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return faculties;
        }
    }
}
