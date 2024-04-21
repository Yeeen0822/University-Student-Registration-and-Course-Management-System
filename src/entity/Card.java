/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jason
 */
public class Card extends Payment {

    private String cardNum;
    private String cardHolder;
    private String cardExp;
    private String cardCVV;

    public Card() {
    }

    public Card(String cardNum, String cardHolder, String cardExp, String cardCVV, double paymentAmount) {
        super(paymentAmount);
        this.cardNum = cardNum;
        this.cardHolder = cardHolder;
        this.cardExp = cardExp;
        this.cardCVV = cardCVV;
    }

    public static boolean vldCardNum(String cardNum) {
        //Regular expression for credit card number validation
        String regex = "^[0-9]{16}$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object
        Matcher matcher = pattern.matcher(cardNum);

        // Return true if the card number matches the regex pattern
        return matcher.matches();
    }

    //validate cardEXP
    public static boolean vldCardExp(String cardExp) {
        // Regular expression for a cardExp
        String cardExpRegex = "^(0[1-9]|1[0-2])/([3-9]\\d)$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(cardExpRegex);

        // Match the cardExp against the pattern
        Matcher matcher = pattern.matcher(cardExp);

        // Check if the cardExp matches the pattern
        return matcher.matches(); // True for valid, false for invalid
    }

    //validate cardCVV
    public static boolean vldCardCvv(String cardCVV) {
        // Regular expression for a card CVV
        String cardCVVRegex = "^\\d{3,4}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(cardCVVRegex);

        // Match the cardCVV against the pattern
        Matcher matcher = pattern.matcher(cardCVV);

        // Check if the card cvv matches the pattern
        return matcher.matches(); // True for valid, false for invalid
    }

    @Override
    public String toString() {
        return super.toString()
                + "PAYMENT METHOD: CARD\n"
                + "CARD HOLDER NAME: " + cardHolder
                + "=====================================================================\n"
                + "                         PAYMENT SUCCESSFUL\n"
                + "=====================================================================";
    }
}
