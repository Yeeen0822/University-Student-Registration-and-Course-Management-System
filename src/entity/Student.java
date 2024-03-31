/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ArrayList;
import adt.HashMap;
import adt.ListInterface;
import adt.MapInterface;
import java.io.Serializable;

/**
 *
 * @author Jason
 */
public class Student implements Serializable {

    private static int nextStudentID = 100; //106 cuz 5 students will get initialized
    private String studentID;
    private String studentName;
    private String studentDOB;
    private String phoneNo;
    private String studentEmail;
    private MapInterface<String, Registration> registeredCourses;

    public Student() {
    }

    public Student(String studentName, String studentBOD, String phoneNo, String studentEmail) {
        this.studentName = studentName;
        this.phoneNo = phoneNo;
        this.studentDOB = studentBOD;
        this.studentEmail = studentEmail;
        this.studentID = "S" + nextStudentID++;

    }

    //for initiaization purpose
    public Student(String studentID, String studentName, String studentBOD, String phoneNo, String studentEmail) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.phoneNo = phoneNo;
        this.studentDOB = studentBOD;
        this.studentEmail = studentEmail;
        nextStudentID++;


    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(String studentBOD) {
        this.studentDOB = studentBOD;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public MapInterface<String, Registration> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(MapInterface<String, Registration> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public void addRegisteredCourses(String regNum, Registration registration) {
        registeredCourses.put(regNum, registration);
    }

    public void removeRegisteredCourses(String regNum) {
        registeredCourses.remove(regNum);
    }
    @Override
    public String toString() {
        return String.format("%-10s %-25s %-10s %-15s %-20s", studentID, studentName, studentDOB, phoneNo, studentEmail);

    }

}
