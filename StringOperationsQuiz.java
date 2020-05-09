import java.util.Scanner;
import java.math.BigInteger;

class StringOperationsQuiz{
  static Scanner s = new Scanner(System.in);
  
  public static void main(String[] args){
    
    int i = 1;
    while(Math.cos(i) < .999999){
      i++; // or i += 1, or i = i + 1
    }
    
    System.out.println("The first positive integer with cos >= .999999 is " + 
                       i + ", whose cosine is " + Math.cos(i));
    
    // Find the first power of 2 that ends in 123
    BigInteger power = BigInteger.ONE;
    while(power.intValue() != 132){
      power = power.multiply(new BigInteger("2")).mod(new BigInteger("1000"));
      System.out.println(power);
    }
    
    System.out.println("The first power of 2 that ends in 123 is " + power);
  }
}
