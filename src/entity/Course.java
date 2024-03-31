/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.ArrayList;
import adt.ListInterface;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 */
public class Course implements Serializable {

    private String courseId;
    private String courseName;
    private ListInterface<String> status;
    private int creditHours;
    private String statusString;
    

    public Course() {
    }

    public Course(String courseId, String courseName, ListInterface<String> status, int creditHours) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.status = new ArrayList<>();
        this.creditHours = creditHours;
        this.statusString="";
        int count = 1;
        for(String s:status){
            
            if (count == status.getNumberOfEntries()){
                
                statusString += s ;
            }
            else{
                statusString += s + ",";
            }
            count++;
        }
    }


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public ListInterface<String> getStatus() {
        return status;
    }

    public void setStatus(ListInterface<String> status) {
        this.status = status;
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
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.courseId, other.courseId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    
    return String.format("%-15s%-35s%-30s%15d", courseId, courseName, statusString, creditHours);
}

}


