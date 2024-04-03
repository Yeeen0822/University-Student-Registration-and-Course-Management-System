/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author wongy
 */
public class ProgrammeCourse implements Serializable {

    String programmeID;
    String courseID;

    public ProgrammeCourse() {
    }

    public ProgrammeCourse(String programmeID, String courseID) {
        this.programmeID = programmeID;
        this.courseID = courseID;
    }

    public String getProgrammeID() {
        return programmeID;
    }

    public void setProgrammeID(String programmeID) {
        this.programmeID = programmeID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProgrammeCourse other = (ProgrammeCourse) obj;
        return Objects.equals(this.programmeID, other.programmeID)
                && Objects.equals(this.courseID, other.courseID);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s ", programmeID, courseID);
    }

}
