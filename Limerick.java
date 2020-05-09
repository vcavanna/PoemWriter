/**
 * By "extending" the Poet class, our Limerick class inherits all the fields and methods
 * of the Poet class. (But not the private ones.)
 * 
 * Limerick is called a subclass of the Poet class.
 * 
 * Words of the day: inherit, extend, subclass, superclass
 * 
 * Subclasses:
 *   + Add functionality in the form of additional fields, additional methods
 *   + Replace existing methods by "overriding" the method 
 *     Do that by redefining it in the subclass
 * 
 * This is the heart of Object-Oriented Programming
 */

class Limerick extends Poet {
  /*
   * There once was a man from Nantucket   010 010 01(0)  A
   * Who kept all his cash in a bucket     010 010 01(0)  A (thanks, Max!)
   *   His daughter named Nan              01001          B 
   *   Ran away with a man                 101001         B
   * And as for the bucket, Nantucket      010 010 01(0)  A
   */
  
  public static void main(String[] args){
    Words = new DSArrayList<Word>();
    readABookMakeUnique("JaneAusten.txt");
    readCMUPron("cmupron.txt");
    
    String line1 = buildLineWithMeter("01001001", 2);
    String line2 = writeLineWithMeterAndRhymeswith("01001001", line1);
    String line3 = buildLineWithMeter("01001", 2);
    String line4 = writeLineWithMeterAndRhymeswith("101001", line3);
    String line5 = writeLineWithMeterAndRhymeswith("01001001", line1);
    System.out.println(line1);
    System.out.println(line2);
    System.out.println(line3);
    System.out.println(line4);
    System.out.println(line5);
  }
  
  
  private static String writeRandomFirstLine(){
    int numSyllables = 7 + (int)(Math.random() * 4); // 7-10
    
    // Build a 7-10 syllable stress pattern
    String stressPattern = "01001001";
    for(int i = 0; i < numSyllables-8; i++){
      if(Math.random() < 0.7){
        stressPattern += "0";
      } else {
        stressPattern += "1";
      }
    }
    System.out.println(stressPattern);
    String line1 = buildLineWithMeter(stressPattern);
    System.out.println(line1);
    
    return stressPattern;
  }
}





