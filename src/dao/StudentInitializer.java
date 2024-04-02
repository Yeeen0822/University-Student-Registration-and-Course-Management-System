/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.ArrayList;
import adt.ListInterface;
import entity.Registration;
import entity.Student;
import java.io.Serializable;

/**
 *
 * @author Jason
 */
public class StudentInitializer {

    private final StudentDAO studentDAO = new StudentDAO("students.dat");
    private ListInterface<Student> studentList = new ArrayList<>();

    public void initializeStudents() {

        studentList.add(new Student("S100","Yam Jason", "17/07/2003", "016-8962213", "jason@gmail.com"));
        studentList.add(new Student("S101","Wong Yee En", "22/08/2003", "016-8972213", "yee@gmail.com"));
        studentList.add(new Student("S102","Tee Yong Zheng", "22/12/2003", "016-8982213", "tee@gmail.com"));
        studentList.add(new Student("S103","Yue Zhi Jving", "03/03/2003", "016-8992213", "jving@gmail.com"));
        studentList.add(new Student("S104","Darren Tan Chia Yuan", "04/01/2003", "016-9962213", "darren@gmail.com"));
        studentList.add(new Student("S105","Lai Weng Lok", "12/05/2003", "016-8963213", "wank@gmail.com"));
        
        studentDAO.saveToFile(studentList);
        System.out.println("Data Initialized and saved to file.");
    }

}
