class Seventeens {
 
  // The entry point for all Java programs is main
  public static void main(String[] args){

    // Find a number that is
    //   * a multiple of 17, and
    //   * the sum of its digits is a multiple of 17
    //   * Can't also have this ---> and it's prime!
    // LESSON: A little math goes a long way!
    
    // Loop through the multiples of 17. That's a while loop.
    int number = 17;
    while(true) {
      if(digitSumIsMultOf17(number)){
        System.out.println(number);
        break;
      }
      
      number += 17; // Add 17 to the number variable
    }
  }
  
  // functions in java (methods) are at the same level
  // within the class, as the main() method.
  
  boolean digitSumIsMultOf17(number)
}