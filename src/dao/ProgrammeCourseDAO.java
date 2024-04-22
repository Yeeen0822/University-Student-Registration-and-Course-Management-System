/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.*;
import adt.ArrayList;
import adt.ListInterface;
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
public class ProgrammeCourseDAO {

    private String fileName;

    public ProgrammeCourseDAO(String fileName) {
        this.fileName = fileName;
    }

    public void saveToFile(ListInterface<ProgrammeCourse> programmeCourseList) {
        File file = new File(fileName);
        try {
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(programmeCourseList);
            ooStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nFile not found");
        } catch (IOException ex) {
            System.out.println("\nCannot save to file");
        }
    }

    public ListInterface<ProgrammeCourse> retrieveFromFile() {
        File file = new File(fileName);
        ListInterface<ProgrammeCourse> programmeCourseList = new ArrayList<>();
        try {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            programmeCourseList = (ArrayList<ProgrammeCourse>) (oiStream.readObject());
            oiStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo such file.");
        } catch (IOException ex) {
            System.out.println("\nCannot read from file.");
        } catch (ClassNotFoundException ex) {
            System.out.println("\nClass not found.");
        } finally {
            return programmeCourseList;
        }
    }
}
