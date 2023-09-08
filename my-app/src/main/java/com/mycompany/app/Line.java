package com.mycompany.app;

import java.util.ArrayList;

class Line{
  String line;
  String rhymesWith;
  Integer syllableCount;
  Integer wordCount;
  ArrayList<Word> wordData;
  Boolean accurate;
  
  // Build a Line object in memory.  
  public Line() {
    this.line = "";
    this.rhymesWith = "";
    this.syllableCount = 0;
    this.wordData = new ArrayList<Word>();
    this.accurate = true;
  }
  
  public String toString(){
    String rv = this.line;
    
    return rv;
  }
}