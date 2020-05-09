// All Java programs need to be in a class!
// Curly braces are used to make blocks of code
// They act, in some senses, like a single statement

// When you "compile" the Java program, it creates a .class
// with the same name as the class. 
//    So: MostSyllables.java -> MostSyllables.class
class MostSyllables {
  
  // The entry point for all Java programs is main
  public static void main(String[] args){
    System.out.println("Hi");
    // put 100000 random-ish numbers into a list.
    // Well, not a list. An array.
    // An array is contiguous block of memory broken into chunks
    //   all of the same size, indexed 0, 1, 2, ...
    
    // Declare the array
    int[] x = new int[100000];
    
    // fill the array
    for(int i = 0; i < 100000; i++){
      x[i] = (17005 + i*(i+5051) + i*i*(i+17)) % 500000;
    }
    
    // print the begining of the array
    for(int i = 0; i < 30; i++){
      System.out.println(x[i]);
    }
    
    // find the largest
    int max = x[0];
    for(int i = 0; i < 100000; i++){
      if(x[i] > max){
        max = x[i];
      }
    }
    
    // Print the max
    System.out.println("The max is " + max);
    
    
  }
  
  
}