package com.mycompany.app;

import java.util.ArrayList;

class Line{
  String text;
  String rhymesWith;
  Integer syllableCount;
  Integer wordCount;
  ArrayList<Word> wordData;
  Boolean accurate;
  static Poet poet = Poet.getInstance();
  
  // Build a Line object in memory.  
  public Line() {
    this.text = "";
    this.rhymesWith = "";
    this.syllableCount = 0;
    this.wordData = new ArrayList<Word>();
    this.accurate = true;
  }

  public Line(String s) {
    String[] words = s.split(" ");
    this.text = s;
    this.wordCount = words.length;
    this.wordData = new ArrayList<Word>();
    this.syllableCount = 0;
    this.accurate = true;
    // Build wordData
    Word word;
    for(String wordStr : words) {
      word = poet.getWord(wordStr);
      if(word == null){ // Word not found in poet map
        word = new Word();
        word.text = wordStr;
        this.accurate = false;
      }
      this.wordData.add(word);
      this.syllableCount += word.stresses.size();
    }
  }
  
  public String toString(){
    String rv = this.text;
    
    return rv;
  }
}