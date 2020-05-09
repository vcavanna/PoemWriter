class Address{
  // Make an array in memory, 10 Word objects
  static Word[] words = new Word[10];
  
  public static void main(String[] args){
    for(int i = 0; i < 10; i++){
      words[i] = new Word("H" + i);
      System.out.println(words[i]);
    }
  }
}