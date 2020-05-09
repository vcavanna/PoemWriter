class Tester{
  public static void main(String[] args){
      Word w;			// w is of type Word, defined in our other file!
      // What is w at this point? Just a variable waiting to point
      // to an actual Word object

      
      w = new Word();
      w = new Word("ZOLLARS  Z AA1 L ER0 Z");
      w = new Word("ABNORMALITIES  AE2 B N AO0 R M AE1 L AH0 T IY0 Z");
      System.out.println(w);
  }

}
