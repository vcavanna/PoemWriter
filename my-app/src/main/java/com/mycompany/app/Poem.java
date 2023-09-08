package com.mycompany.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Poem extends Poet { // TODO: Figure out a way to remove the "Poet" extension here.
    static Scanner input = new Scanner(System.in);
    int lineEditing = 0;
    ArrayList<String> rhymes;
    HashMap<String, String> wordRhymes;
    ArrayList<Integer> syllableCount;
    ArrayList<Line> lines;
    boolean checkingRhyme;
    boolean checkingSyllables;

    public Poem() {
        rhymes = new ArrayList<String>();
        wordRhymes = new HashMap<String, String>();
        syllableCount = new ArrayList<Integer>();
        lines = new ArrayList<Line>();
        checkingRhyme = true;
        checkingSyllables = true;
    }

    public void addLine(Line l) {
        lines.add(l);
    }

    public void addLine(String s) {
        Line l = new Line();
        l = addLineInfo(s, l);
        if(failsRhymeCheck(l)){
            System.out.println("failsRhymeCheck");
        } else if (failsSyllableCount(l)) {
            System.out.println("failsSyllableCheck");
        } else if(lines.size() <= lineEditing) {
            lines.add(l);
            printPoem();
            lineEditing++;
        } else {
            lines.set(lineEditing, l);
            lineEditing++;
        }
    }

    public static Line addLineInfo(String newLine, Line l) {
        Word newWord;
        String[] arrayLine = newLine.split(" ");
        l.line = newLine;
        l.wordCount = arrayLine.length;
        for(String w : arrayLine) {
            // Remove all the extra stuff, capitalizations, etc.
            if(wordMap.containsKey(w.toLowerCase().replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", ""))) {             // If a word object exists already, we take it
                newWord = wordMap.get(w.toLowerCase().replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", ""));
            } else {                                 // If no word object exists already, we create our own partial one and let the program know it's know accurate
                newWord = new Word();
                newWord.word = w;
                l.accurate = false;
            }
            l.wordData.add(newWord);
            l.syllableCount += newWord.stresses.size();
        }
        return l;
    }

    public void printPoem() {
        int limit = lines.size();
        for(int i = 0; i < limit; i++) {
          System.out.println(lines.get(i));
        }
      }

    public boolean failsRhymeCheck(Line l) {
        Word w = l.wordData.get(l.wordCount - 1);
        String checkInput = "";
        if (!checkingRhyme) {
            return false; // Passes rhymeCheck
        } else if(rhymes.size() == 0) {
            System.out.println("Do you want to set a rhyme scheme first? ('yes' / 'no')");
            System.out.println("Know that saying no means that PoemWriter won't track the rhyme scheme for this poem.");
            checkInput = input.nextLine();
            if(checkInput.equals("yes")) {
                System.out.println("Returning to main...");
                return true;
                
            } else {
                System.out.println("Removing the rhyme scheme requirement and returning back to main.");
                checkingRhyme = false;
                return false;
            }
        }else if(w.phonemes.size() == 0) {
            System.out.println("Can't find the word for " + w.word);
            System.out.println("Do you want to enter the line anyway? ('yes' / 'no')");
            System.out.println("Doing so will stop any automatic rhyming for the poem.");
            checkInput = input.nextLine();
            if(checkInput.equals("yes")) {
                checkingRhyme = false;
                return true;
            } else {
                return false;
            }
        } else if(!(rhymes.get(lineEditing) == null)) {
            wordRhymes.replace(rhymes.get(lineEditing), "", w.word);
            Word rhymingWord = wordMap.get(wordRhymes.get(rhymes.get(lineEditing)));
            System.out.println(rhymingWord);
            System.out.println(w);
            if(w.endsWith(rhymingWord.getRhymeNeeds())) {
            return false;
            } else {
            System.out.println("The word " + w.word + " and " + rhymingWord.word + " don't rhyme.");
            System.out.println("Do you want to keep the line anyway? (Type 'yes' if you want to override, type 'no' if you want to rewrite the line.)");
            checkInput = input.nextLine();
            if(checkInput.equals("yes")) {
                System.out.print("Overriding...");
                return false;
            } else {
                System.out.println("I'll take that as a no.");
                return true;
            }
            }
        } else {
            System.out.println("By all accounts, the poem is finished. You can export it, or you can start over.");
            return true;
        }
    }

    public boolean failsSyllableCount(Line l) {
        String checkInput = "";
        if (!checkingSyllables) {
            return false;
        } else if ((lines.size() == syllableCount.size()) && (lines.size() != 0)) {
            System.out.println("By all accounts, the poem is finished. You can export it, or you can start over.");
            return true;
        } else if(l.accurate) {
            if(!(syllableCount.size() == 0)) {
              if(l.syllableCount == syllableCount.get(lineEditing)) {  // If syllables line up
                  return false;
              } else {                                                 // If syllables don't line up
                  System.out.println("The syllable count you gave, " + syllableCount.get(lineEditing) + ", and the count for the line, " + l.syllableCount + ", don't match.");
                  return true;
              }
            } else {                                                   // If syllables haven't been set yet
              System.out.println("Do you want to set your syllable count first? ('yes' / 'no')");
              System.out.println("Know that saying no means that PoemWriter won't track the syllable count for this poem.");
              checkInput = input.nextLine();
              if(checkInput.equals("yes")) {
                  System.out.println("Returning to main to rewrite the line...");
                  return true;
              } else {
                  System.out.println("Removing the syllable count requirement...");
                  checkingSyllables = false;
                  return false;
              }
            }
        } else { // Not an accurate syllable count
            System.out.println("It seems that at least one of the words in the line isn't in our database.");
            System.out.println("Do you want to rewrite the lines to get a poem with a syllable count? ('yes' / 'no')");
            System.out.println("Know that saying no means that PoemWriter won't track the syllable count for this poem.");
            checkInput = input.nextLine();

            if(checkInput.equals("yes")) {
                System.out.println("Returning to main to rewrite the line...");
                return true;
            } else {
                System.out.println("Removing the syllable count requirement...");
                checkingSyllables = false;
                return false;
            }
        }
    }

  public String save() throws FileNotFoundException {
      String savedFile = "";
      
      for(int i = 0; i < lines.size(); i++) {
          savedFile += saveLine(lines.get(i));
      }
      savedFile += "/ done";
      return savedFile;
  }
  
  public static String saveLine(Line l) {
    String rv = "";
    rv += l.line + "\n";
    rv += l.rhymesWith + "\n";
    rv += l.syllableCount + "\n";
    rv += l.wordCount + "\n";
    for(int i = 0; i < l.wordData.size(); i++) {
      Word w = l.wordData.get(i);
      rv += w.word + "\n";
      rv += w.numberOfSyllables + "\n";
      rv += w.partOfSpeech + "\n";
      rv += w.etymology + "\n";
      rv += w.phonemes + "\n";
      rv += w.stresses + "\n";
    }
    rv += l.accurate + "\n";
    return rv;
  }

  public void rhymeScheme() {
    String rhymesInput = "";
    int i = 0;
    
    if(rhymes.size() == 0) {
      while(true) {
        System.out.println("What rhyme do you want line " + (i + 1) + " to have?");
        System.out.print("Options include: ");
        
        Object[] uniqueRhymesArray = wordRhymes.keySet().toArray();
        
        if(rhymes.size() > 0) {
          for(int j = 0; j < uniqueRhymesArray.length + 1; j++) {
            System.out.print("'" + "abcdefghijklmnopqrstuvwxyz".substring(j, j + 1) + "', ");
          }
        
        } else {
          System.out.println("'a'");
        }
        System.out.println("You can also just press enter to have it not rhyme, or type 'quit' when you've reached the last line.");
        rhymesInput = input.nextLine();
        
        if(rhymesInput.equals("quit")) {
          System.out.println("Quitting to main...");
          break;
        } else if ("abcdefghijklmnopqrstuvwxyz".substring(0, uniqueRhymesArray.length + 1).contains(rhymesInput)) {
          rhymes.add(rhymesInput);
          wordRhymes.put(rhymesInput, "");
          i++;
        } else {
          System.out.println("That isn't one of the options.");
        }        
        
        if ((syllableCount.size() == rhymes.size()) && (syllableCount.size() > 0)) {
          System.out.println("According to the information you entered previously, that's the last line of the poem. Quitting to main...");
          break;
        }
      }
    } else {
      System.out.println("You can't set the rhyme scheme if you've already started writing the poem.");
    }
  }

  public void syllables() {
    int i = 0;
    String syllablesInput = "";
    if(syllableCount.size() == 0) {
      while(true) {
        System.out.println("How many syllables do you want line " + (i + 1) + " to have?");
        System.out.println("You can also just press enter to not require a syllable count, or type 'quit' when you've reached the last line.");
        syllablesInput = input.nextLine();
        
        if(syllablesInput.equals("quit")) {
          System.out.println("Quitting to main...");
          break;
        } else if(isInteger(syllablesInput)) {
          syllableCount.add(Integer.parseInt(syllablesInput));
        } else {
          System.out.println("That is not a number.");
        }
        if ((rhymes.size() == syllableCount.size()) && (rhymes.size() > 0)) {
          System.out.println("According to the information you entered previously, that's the last line of the poem. Quitting to main...");
          break;
        }
        i++;
      }
    } else {
      System.out.println("You can't set the syllable count if you've already started writing the poem.");
    }
  }

  public static boolean isInteger(String input) {
    try {
      Integer.parseInt(input);
      return true;
    }
    catch(NumberFormatException e) {
      return false;
    }
  }
}
