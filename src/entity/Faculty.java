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
 * @author Name: Wong Yee En RDS2S2G3 22WMR13659
 */
public class Faculty implements Serializable {

    private String facultyId;
    private String facultyName;
    private MapInterface<String, Programme> facultyProgrammesMap;

    public Faculty() {
    }

    public Faculty(String facultyId, String facultyName) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyProgrammesMap = new HashMap<>();
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public MapInterface<String, Programme> getFacultyProgrammesMap() {
        return facultyProgrammesMap;
    }

    public void setFacultyProgrammesMap(MapInterface<String, Programme> facultyProgrammesMap) {
        this.facultyProgrammesMap = facultyProgrammesMap;
    }

    public void addProgrammeToFaculty(String programmeID, Programme programme) {
        facultyProgrammesMap.put(programmeID, programme);
    }

    public void removeProgrammeFromFaculty(String programmeID) {
        facultyProgrammesMap.remove(programmeID);
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
        final Faculty other = (Faculty) obj;
        if (!Objects.equals(this.facultyId, other.facultyId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-40s", facultyId, facultyName);
    }
}
