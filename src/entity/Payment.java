/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jason
 */
public class Payment {

    private LocalDate paymentDate;
    private double paymentAmount;


    public Payment() {
    }

    public Payment(double paymentAmount) {
        paymentDate = LocalDate.now();
        this.paymentAmount = paymentAmount;

    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }


    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.print("+------------------------------+\n");
        System.out.print("|           RECEIPT            |\n");
        System.out.print("+------------------------------+\n");
        return "DATE: " + getPaymentDate().format(dateFormatter) + "\n"
                + "TOTAL AMOUNT    : RM " + String.format("%.2f", getPaymentAmount()) + "\n";

    }
}
