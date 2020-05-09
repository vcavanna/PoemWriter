
import java.util.Scanner; 
import java.math.BigInteger;

public class QuizA { 
  
  public static void main(String[] args) { 
    BigInteger x = new BigInteger("6726372678426873267832467234672346723468723");
    BigInteger p1 = x.nextProbablePrime();
    BigInteger p2 = p1.nextProbablePrime();
    
    System.out.println(p1);
    System.out.println(p2);
    System.out.println(p1.multiply(p2));
  } 
}