package com.mycompany.app;

// The Word Class
import java.util.ArrayList;

class Word implements Comparable<Word>{
  String text;  // The actual word
  int numberOfSyllables; // syllable count
  String partOfSpeech; // noun, verb, ...
  String etymology;  // where it came from
  ArrayList<String> phonemes; // [F, OW1, N, IY0, M, Z]"
  ArrayList<Integer> stresses; // [1, 0, 0] Victory
  
  
  // Build a word object to contain a given word
  /*
   public Word(String w){
   this.word = w;   // "this" means "the object just created."
   }*/
  
  
  // Build a word object from a given cmupron line, such as "ZOLLARS  Z AA1 L ER0 Z"
  public Word(String line){
    String[] parts = line.split(" ");
    this.text = parts[0].toLowerCase()
      .replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
    this.phonemes = new ArrayList<String>(); // Instantiating these objects in memory
    this.stresses = new ArrayList<Integer>();// Instantiating these objects in memory
    for(int i = 1; i < parts.length; i++){
      if(parts[i].length() < 1) continue;
      if(Character.isDigit(parts[i].charAt(parts[i].length() - 1))){ // vowels
        this.stresses.add(Integer.valueOf("" + parts[i].charAt(parts[i].length() - 1)));
        this.phonemes.add(parts[i].substring(0, parts[i].length() - 1));
      } else { // Not a vowel
        this.phonemes.add(parts[i]);
      }
    }
  }
  
  
  // default-ish constructor
  public Word(){
    this.numberOfSyllables = 0;
    this.phonemes = new ArrayList<String>(); // Instantiating these objects in memory
    this.stresses = new ArrayList<Integer>();// Instantiating these objects in memory
    this.partOfSpeech = "";
    this.etymology = "";
  }
  
  
  
  public String toString(){
    String rv = "";
    rv += "[" + this.text;
    rv += ", " + this.numberOfSyllables;
    rv += ", " + this.phonemes.toString();
    rv += ", " + this.stresses.toString();
    rv += ", " + this.partOfSpeech;
    rv += "]";
    
    return rv;
  }
  
  @Override
  public boolean equals(Object other){
    return this.text.equals(((Word)other).text);
  }
  
  
  
  public int compareTo(Word ww2){
    Word w1 = (Word)this;
    Word w2 = (Word)ww2;
    return w1.text.compareTo(w2.text);
  }
  
  
  
  // Takes a cmu line such as "ZOLLARS  Z AA1 L ER0 Z"
  // And endows the current Word object with phonemes and stresses from that line
  public void addCMUPronInfo(String cmu){
    String[] parts = cmu.split(" ");
    this.phonemes = new ArrayList<String>(); // Instantiating these objects in memory
    this.stresses = new ArrayList<Integer>();// Instantiating these objects in memory
    for(int i = 1; i < parts.length; i++){
      if(parts[i].length() < 1) continue;
      if(Character.isDigit(parts[i].charAt(parts[i].length() - 1))){ // vowels
        this.stresses.add(Integer.valueOf("" + parts[i].charAt(parts[i].length() - 1)));
        this.phonemes.add(parts[i].substring(0, parts[i].length() - 1));
      } else { // Not a vowel
        this.phonemes.add(parts[i]);
      }
    }
  }
  
  
  /**
   * We ask the Word object to return an ArrayList of its rhyme needs
   *
   * Our criterion for rhyming is that it should have the same phonemes as the
   * given line, from the last stressed vowel phoneme to the end.
   */
  public ArrayList<String> getRhymeNeeds() {
    int idx = this.stresses.size() - 1;
    int unstressCount = 0; // # of 0-stresses at the end of the word
    
    while(idx >= 0 && this.stresses.get(idx) == 0) { 
      unstressCount++;
      idx--;
    }
    //System.out.println(this.stresses + "unstressCount = " + unstressCount);
    // Work backwards in phonemes ArrayList, pass unstressCount-many vowel phonemes,
    // and then the last stressed vowel. We prepend each phoneme we encounter to the
    // rv ("return value") ArrayList of phonemes, which we will return when the loop ends.
    idx = this.phonemes.size() - 1;
    ArrayList<String> rv = new ArrayList<String>();
    while(idx >= 0 && unstressCount >= 0){
      if("AEIOUaeiou".indexOf(this.phonemes.get(idx).charAt(0)) != -1) {
        rv.add(0, this.phonemes.get(idx));
        unstressCount--;
        idx--;
      } else {
        rv.add(0, this.phonemes.get(idx));
        idx--;
      }
    }
    return rv;
  }
  
  
  /**
   * We ask the Word object to return an ArrayList of its rhyme needs
   * Last two phonemes
   */
  public ArrayList<String> getRhymeNeedsLastTwo() {
    int last = this.phonemes.size() - 1;
    ArrayList<String> rv = new ArrayList<String>();
    rv.add(this.phonemes.get(last-1));
    rv.add(this.phonemes.get(last));
    return rv;
  }
  
  
  /**
   * We ask the Word object to return an ArrayList of its rhyme needs
   * Last 50% of phonemes
   */
  public ArrayList<String> getRhymeNeeds50Percent() {
    int halfway = this.phonemes.size()/2;
    ArrayList<String> rv = new ArrayList<String>();
    for(int i = halfway; i <= this.phonemes.size() - 1; i++){
      rv.add(this.phonemes.get(i));
    } 
    return rv;
  }
  
  
  /**
   * Tells whether this word ends with the given phonemes
   * 
   * @param phonemes
   * @return
   */
  public boolean endsWith(ArrayList<String> phonemes) {
    if(this.phonemes.size() < phonemes.size())
      return false;
    
    int mylen = this.phonemes.size();
    int otherlen = phonemes.size();
    for(int i = 0; i < otherlen; i++){
      if(!this.phonemes.get(mylen - 1 - i).equals(phonemes.get(otherlen - 1 - i)))
        return false;
    }
    return true;
  }
  
  
}