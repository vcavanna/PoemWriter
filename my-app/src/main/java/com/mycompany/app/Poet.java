package com.mycompany.app;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

class Poet{
  static ArrayList<Word> Words;  // Will hold words from the book
  static HashMap<String, Word> wordMap; // Maps Strings to the right Word object 
  static HashMap<String, HashSet<String>> followMap;
  private static Poet instance;
  private static final String CMUPronLocation = "C:\\\\Users\\\\Vincent\\\\Documents\\\\computerPrograms\\\\poemWriter\\\\my-app\\\\src\\\\main\\\\java\\\\com\\\\mycompany\\\\app\\\\cmupron.txt";
  private static final String JaneAustenLocation = "C:\\Users\\Vincent\\Documents\\computerPrograms\\poemWriter\\my-app\\src\\main\\java\\com\\mycompany\\app\\JaneAusten.txt";
  
  // Add a HashMap mapping Strings s to the Word object having s as its word field
  // 1. Declare it as a field
  // 2. Instantiate it in main()
  // 3. Add an entry every time we create a new Word object
  public static void main(String[] args){
    
    // Read a book and make that our Words source
    // Create the Words ArrayList in memory
    
    long startTime = System.currentTimeMillis(); // Note the start time
    
    readABookMakeUnique("TaylorSwiftLyrics.txt");
    System.out.println("Time = " + (System.currentTimeMillis() - startTime));
    System.out.println("We read " + Words.size() + " words!");
    
    // Fill the Word objects with info from CMUpron
    readCMUPron("cmupron.txt");
    System.out.println("Read cmupron: Time = " + (System.currentTimeMillis() - startTime)); 
    
    // Fill the Word objects Part of Speech information
    readPOSFile("mpos.txt");
    System.out.println("Read mpos: Time = " + (System.currentTimeMillis() - startTime)); 
     
    
    writeFollowStory();
  }
  
  Poet() {
    readABookMakeUnique(JaneAustenLocation);
    readCMUPron(CMUPronLocation);
  }
  
  static void writeFollowStory(){   
    Random generator = new Random(); 

    for(int i = 0; i < 10; i++){ // 10 random lines
      int index = generator.nextInt(Words.size());
      String firstword = Words.get(index).text;
      System.out.print(firstword + " ");
      String currentword = firstword;
      for(int j = 0; j < 8; j++){ // 17 words per line
        HashSet<String> hs = followMap.get(currentword);
        index = generator.nextInt(hs.size());
        String nextword = (String)(hs.toArray()[index]);
        System.out.print(nextword + " ");
        currentword = nextword;
      }
      System.out.println(".");
    }
  }
  
  // Use the "void" keyword to say "this function has no return type,
  // probably operates by side-effects."
  static void readABook(String book){
    try{
      // Wraps a FileReader object around the book (on disk) 
      FileReader f = new FileReader(book);
      
      // Wraps a BufferedReader object around the FileReader
      BufferedReader reader = new BufferedReader(f);
      
      // read the book. Will read "null" when the book ends
      String x = reader.readLine();
      while(x != null){
        // "x" is a line. I want individual words!
        String[] wordsOnLine = x.split(" ");
        
        //System.out.println("Here comes the next line!");
        // System.out.println(Arrays.toString(wordsOnLine));
        for(int j = 0; j < wordsOnLine.length; j++){
          // Create a new Word object in memory
          Word w = new Word();
          // Fills the "word" field inside w with
          // the jth word on this line of text
          w.text = wordsOnLine[j].toLowerCase().
            replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
          // We use "\\" to escape special characters in a string
          Words.add(w);
        }
        x = reader.readLine();
      }
    } catch(IOException e){
      System.out.format("IOException %s\n", e);
    }
  }
  
  
  
  /**
   * Generate a Word object for each word in the file "book"
   * When this method finishes, each word appears only once, 
   * and they are in the Words list in alphabetical order.
   */
  static void readABookInsertUnique(String book){
    try{
      // Wraps a FileReader object around the book (on disk) 
      FileReader f = new FileReader(book);
      
      // Wraps a BufferedReader object around the FileReader
      BufferedReader reader = new BufferedReader(f);
      
      // read the book. Will read "null" when the book ends
      String x = reader.readLine();
      while(x != null){
        // That's a line. I want individual words!
        String[] wordsOnLine = x.split(" ");
        
        for(int j = 0; j < wordsOnLine.length; j++){
          // Create a new Word object in memory
          Word w = new Word();
          // Fills the "word" field inside w with
          // the jth word on this line of text
          w.text = wordsOnLine[j].toLowerCase().
            replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
          // We use "\\" to escape special characters in a string
          
          //if(!Words.contains(w)){
          if(Words.indexOf(w) == -1){
            Words.add(w);
            //if(Math.random() < 0.05) Words.sort();
            Collections.sort(Words);
          }
        }
        x = reader.readLine();
      }
    } catch(IOException e){
      System.out.format("IOException %s\n", e);
    }
  }
  
  
  
  /**
   * Generate a Word object for each word in the file "book"
   * When this method finishes, each word appears only once, 
   * and they are in the Words hashMap in no particular order.
   */
  static void readABookMakeUnique(String book){
    wordMap = new HashMap<String, Word>(); // Instantiate the map
    followMap = new HashMap<String, HashSet<String>>(); // Instantiate the map
    
    try{
      // Wraps a FileReader object around the book (on disk) 
      FileReader f = new FileReader(book);
      
      // Wraps a BufferedReader object around the FileReader
      BufferedReader reader = new BufferedReader(f);
      
      // read the book. Will read "null" when the book ends
      String previousWord = "";
      String word = "";
      String x = reader.readLine();
      while(x != null){
        x = x.replaceAll("-", " ");   // replace hyphens with spaces
        x = x.replaceAll(" +", " ");  // turn multiple spaces into one
        x = x.trim();                 // trim leading and trailing spaces
        // "x" is a line. I want individual words!
        String[] wordsOnLine = x.split(" ");
        
        for(int j = 0; j < wordsOnLine.length; j++){
          // Create a new Word object in memory
          // Fills the "word" field inside w with
          // the jth word on this line of text
          previousWord = word;
          word = wordsOnLine[j].toLowerCase().
            replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
          // We use "\\" to escape special characters in a string
          if(!wordMap.containsKey(word)){ // This will automatically prevent multiple copies
            Word w = new Word(word);
            wordMap.put(w.text, w);
          }
          
          // "he is" -> previousWord = "he", word = "is"
          if(!followMap.containsKey(previousWord))
            followMap.put(previousWord, new HashSet<String>());
          followMap.get(previousWord).add(word);
          
        }
        x = reader.readLine();
      }
      
    } catch(IOException e){
      System.out.format("IOException %s\n", e);
    }
    
    // Now make the Word list
    Words = new ArrayList<Word>();
    for(Word w : wordMap.values())
      Words.add(w);
  }
  
  
  
  /**
   * Reads the cmupron.txt file which contains pronunciation information
   * For each word in cmupron.txt, if that word is in our Words list,
   * we enhance the corresponding Word object with that information.
   */
  static void readCMUPron(String cmu){
    try{
      // Wraps a FileReader object around the book (on disk) 
      FileReader f = new FileReader(cmu);
      
      // Wraps a BufferedReader object around the FileReader
      BufferedReader reader = new BufferedReader(f);
      
      // read the book. Will read "null" when the book ends
      String x = reader.readLine();
      while(x != null){
        x = x.trim();
        String[] parts = x.split(" ");
        String word = parts[0].toLowerCase();
        if(word.indexOf("(") != -1) word = word.substring(0, word.length() - 3);
        if(wordMap.containsKey(word)){        // This means we found it!
          wordMap.get(word).addCMUPronInfo(x);
        }
        x = reader.readLine();
      }
    } catch (IOException e){
      System.out.format("IOException %s\n", e);
    }
  } 
  
  /**
   * Read the mpos.txt file to get parts of speech for our words
   * Each line of this file looks like: abbreviate*t
   * word, then *, then the part of speech.
   */
  static void readPOSFile(String filename){
    try { 
      FileReader f = new FileReader(filename);
      BufferedReader reader = new BufferedReader(f);
      String line = null;
      int linesRead = 0;
      while ((line = reader.readLine()) != null) {
        linesRead++;
        if(linesRead % 10000 == 0)
          System.out.print("" + (int)(linesRead * 100 / 232123) + "% "); 
        String[] parts = line.split("\\*");
        String word = parts[0];
        String pos  = parts[1];
        
        if(wordMap.containsKey(word)) {
          wordMap.get(word).partOfSpeech = pos;
        }
      }
      System.out.println("");
      reader.close();
      } catch (IOException x) {
      System.err.format("IOException: %s\n", x);
    }
  }
  
  
  
  /**
   * Takes a string like "0101010101" and produces a string like:
   * How do I love thee, let me count the ways
   * 
   * Build 0101010101
   *       010 abducted  does fit --- has stress where we want it
   *       100 victory   does not fit
   *          1010101    is what is left to fit, after using abducted
   *          1 dog      Yes, let's use dog
   *           010101    is what is left after "victory dog"
   *           1 cat     Can we use "cat?" I say YES
   *            10101    left after using "abducted dog cat"
   *            10201    "abducted dog cat womanhood destroy"
   *            10001    "abducted dog cat worldliness award"
   *               01    After womanhood. Would "beginning (010)" fit this?
   *                     No! It's too long. 
   * Consider abducted. Stresses = 010
   * Can we start our line with "abducted?"
   * 
   * What we want is our found-word's stress to be at least as big 
   * as the desired pattern's stress.
   */
  public static String buildLineWithMeter(String stressPattern){
    Random generator = new Random(); // Will give different lines on different runs
    String poetryLine = "";
    
    while(!stressPattern.equals("")) { // will chop off prefixes as we find words
      // Get a random word
      int index = generator.nextInt(Words.size());
      Word randWord = Words.get(index); // This is a nice, random word
      
      if(randWord.stresses.size() > 0 && 
         stressPattern.length() >= randWord.stresses.size())
      {
        // Get the piece of the remaining stressPattern that our random 
        // word must match
        String requiredStresses = stressPattern.substring(0, randWord.stresses.size());
        if(stressesMatch(requiredStresses, randWord.stresses))
        {
          poetryLine = poetryLine + randWord.text + " ";
          // Now chop off a prefix consisting of all the stresses we just matched.
          stressPattern = stressPattern.substring(randWord.stresses.size());
        }
      }
    }
    return poetryLine;
  }
  
  
  
  /**
   * Version 2
   */
  public static String buildLineWithMeter(String stressPattern, int l){
    Random generator = new Random(); // Will give different lines on different runs
    String poetryLine = "";
    
    while(!stressPattern.equals("")) { // will chop off prefixes as we find words
      // Get a random word
      int index = generator.nextInt(Words.size());
      Word randWord = Words.get(index); // This is a nice, random word
      
      if((randWord.stresses.size() >= l || Math.random() < 0.02) && 
         randWord.stresses.size() > 0 &&
         stressPattern.length() >= randWord.stresses.size())
      {
        // Get the piece of the remaining stressPattern that our random 
        // word must match
        String requiredStresses = stressPattern.substring(0, randWord.stresses.size());
        if(stressesMatch(requiredStresses, randWord.stresses))
        {
          poetryLine = poetryLine + randWord.text + " ";
          // Now chop off a prefix consisting of all the stresses we just matched.
          stressPattern = stressPattern.substring(randWord.stresses.size());
        }
      }
    }
    return poetryLine;
  }
  
  
  /**
   * Tells whether the stresses in the proposedWord are at least as great
   * as the stresses indicated in the needs string.
   */
  private static boolean stressesMatch(String needs, ArrayList<Integer> proposedWord){
    for(int i = 0; i < needs.length(); i++){ // Search for a mismatch
      if(proposedWord.get(i) < Integer.valueOf("" + needs.charAt(i))){   // if there's a problem...
        return false;
      }
    } 
    return true;
  }
  
  
  
  
  /**
   * Writes a line with the given meter, such that the end rhymes with the target string
   * @param meter  The meter to generate
   * @param target The string to rhyme with, can be several words --- we use the last
   *
   * @return A line of poetry of the type described.
   */
  static String writeLineWithMeterAndRhymeswith(String meter, String target){
    // Get last word of target "I love to have breaks after classes"
    String[] targetWords = target.split(" ");
    String lastWord = targetWords[targetWords.length - 1];
    
    // Get the rhyme needs from the last word: [AE S IH Z]
    // Consult Words to find the Word object whose word is lastWord
    // int lastWordIndex = Words.binarySearchIndexOf(new Word(lastWord));
    // Word w = Words.get(lastWordIndex);
    Word w = wordMap.get(lastWord);
    ArrayList<String> rhymeNeeds = w.getRhymeNeeds();
    
    // Search for a Word object that rhymes with rhymeNeeds, and 
    // satisfies the stress pattern we are looking for.
    Word match = findWordMatchingStressesAndPhonemes(meter, rhymeNeeds);
    
    // suppose we find "tresspasses"
    // suppose meter = 100100 100
    int numRemainingSyllables = meter.length() - match.stresses.size();
    String remainingMeter = meter.substring(0, numRemainingSyllables); // 100100
    String initialLine = buildLineWithMeter(remainingMeter, 2);
    
    return initialLine + match.text;
  }
  
  
  static Word findWordMatchingStressesAndPhonemes(String meter, ArrayList<String> rhymeNeeds){
    int numWords = Words.size();
    int startIdx = (int)(Math.random() * numWords); // random starting place for search
    for(int i = 0; i < numWords; i++){
      Word candidate = Words.get((startIdx + i) % numWords);
      if(candidate.stresses.size() > meter.length()) continue; // Not enough stresses      
      String needs = meter.substring(meter.length() - candidate.stresses.size());
      if(candidate.endsWith(rhymeNeeds) && stressesMatch(needs, candidate.stresses))
        return candidate;
    }
    
    return Words.get(17);  // Default word
  }


  public static Poet getInstance() {
    if (instance == null) {
      instance = new Poet();
    }
    return instance;
  }

  public Word getWord(String w) {
      return wordMap.get(w.toLowerCase().replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", ""));
  }
}
