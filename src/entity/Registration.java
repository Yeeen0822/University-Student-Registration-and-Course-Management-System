/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import control.StudentRegistrationManagement;
import java.io.Serializable;

/**
 *
 * @author Jason
 */
public class Registration implements Serializable {

    public final static double courseRate = 500;

    private boolean registrationIsCancelled;
    private Course course;
    private String type; // resit/repeat/main
    private Payment payment;
    private String regNum;
    private static int nextRegNum;
    

    public Registration() {

    }

    public Registration(Course course, String type, Payment payment) {
        registrationIsCancelled = false;
        nextRegNum = StudentRegistrationManagement.registrationEntries++;
        this.regNum = "R" + (nextRegNum + 100);
        this.course = course;
        this.type = type;
        this.payment = payment;
        
        //add payment into course
        course.addFeePaid(payment.getPaymentAmount());
    }

    public boolean isRegistrationIsCancelled() {
        return registrationIsCancelled;
    }

    public void setRegistrationIsCancelled(boolean registrationIsCancelled) {
        this.registrationIsCancelled = registrationIsCancelled;
    }

    public String getRegNum() {
        return regNum;
    }

    public static double getCourseRate() {
        return courseRate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
