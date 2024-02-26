package model;

import java.text.NumberFormat;

public class Interest {
	
	public static double simpleInterest(double principal, double rate, double years) {
       double amount = principal + (principal * (rate/100) * years);
       return amount;
	}
	
	public static double compoundInterest(double principal, double rate, double years) {
	       double amount = principal* Math.pow((1+rate/100), years);
	       return amount;
		}
	
	public static String formattedValue(double amount) {
	      String formattedValue = NumberFormat.getCurrencyInstance().format(amount);
	      return formattedValue;
		}
	
}
