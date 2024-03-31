/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Name: Wong Yee En RDS2Y2S2G3 22WMR13659
 */
public class Programme implements Serializable{

    private String programmeId;
    private String programmeName;
    private MapInterface<String, Course> programmeCoursesMap;

    public Programme() {
    }

    public Programme(String programmeId, String programmeName) {
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.programmeCoursesMap = new HashMap<>();
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public void setProgrammeName(String programmeName) {
        this.programmeName = programmeName;
    }

    public MapInterface<String, Course> getProgrammeCoursesMap() {
        return programmeCoursesMap;
    }

    public void setProgrammeCoursesMap(MapInterface<String, Course> programmeCoursesMap) {
        this.programmeCoursesMap = programmeCoursesMap;
    }

    public void addCourseToProgramme(String courseId, Course course) {
        programmeCoursesMap.put(courseId, course);
    }

    public void removeCourseFromProgramme(String courseId) {
        programmeCoursesMap.remove(courseId);
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
        final Programme other = (Programme) obj;
        if (!Objects.equals(this.programmeId, other.programmeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%-15s%-40s", programmeId, programmeName);
    }

}
