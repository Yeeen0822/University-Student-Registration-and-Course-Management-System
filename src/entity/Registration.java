/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Jason
 */
public class Registration {

    public final static double courseRate = 50;
    private String regNum;
    private static int nextRegNum = 100;
    private Course course;
    private String type; // resit/repeat/main
    private Payment payment;
    public static double totalFees = 0;  //all the registation fees of all students combined
    public Registration() {

    }

    public Registration(Course course, String type, Payment payment) {
        this.regNum = "R" + nextRegNum++;
        this.course = course;
        this.type = type;
        this.payment = payment;
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
