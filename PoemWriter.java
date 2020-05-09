import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;

class PoemWriter extends Poet {
  static Scanner input = new Scanner(System.in);
  static DSArrayList<Line> poem;
  static String[] arrayInput = null;
  static DSArrayList<String> rhymes;
  static DSArrayList<Integer> syllableCount;
  static HashMap<String, String> wordRhymes;
  static int lineEditing = 0;
  static boolean failsRhymeCheck;
  static boolean failsSyllableCheck;
  static boolean checkingRhyme = true;
  static boolean checkingSyllables = true;
  
  public static void main(String[] args) {
    Words = new DSArrayList<Word>();
    readABookMakeUnique("JaneAusten.txt");
    readCMUPron("cmupron.txt");
    poem = new DSArrayList<Line>();
    rhymes = new DSArrayList<String>();
    wordRhymes = new HashMap<String, String>();
    syllableCount = new DSArrayList<Integer>();
    System.out.println("Hi there. Type a line of poetry to begin. Type '/ help' to learn how to work this program.");
    takeInputs();
  }
  
  public static void takeInputs() {
    Boolean commandsPresent = false;
    Boolean continueNow;
    Boolean breakNow;

    while (true){                                        // Creates a new line or takes a command
      String myInput = input.nextLine();
      arrayInput = myInput.split(" ");
      continueNow = false;
      breakNow = false;
      
      for(int i = 0; i < arrayInput.length; i++) {
        if(continueNow == true){
          break;
        } else if (breakNow == true) {
          break;
        }
        
        if(arrayInput[i].equals("/")) {                      // If a command is inputted
          if(!(i == arrayInput.length - 1)) {
            String newCommand = arrayInput[i+1];
            if(newCommand.equals("help")) {                  // Presents information on commands
              help();
              myInput="";
            } else if(newCommand.equals("rhymesWith")) {     // Finds rhymes for the following word
              myInput = findRhyme(arrayInput, i + 2);
            } else if(newCommand.equals("save")) {           // Saves the file as a text file
              try{
                save();
              } catch (FileNotFoundException ex) {
                System.out.println(ex);
              }
              myInput = "";
            } else if(newCommand.equals("export")) {         // Exports the file as a txt file with only the lines, title, and author
              try{
                export();
              } catch (FileNotFoundException ex) {
                System.out.println(ex);
              }
              myInput = "";
            } else if(newCommand.equals("load")) {           // Loads a previously saved file
              load();
              myInput = "";
            } else if(newCommand.equals("import")) {         // Imports a poem format, such as Shakespearan Sonnet
              importPoem();
              myInput = "";
            } else if(newCommand.equals("rhymeScheme")) {    // Sets or changes the rhyme scheme of the poem
              rhymeScheme();
              myInput = "";
            } else if(newCommand.equals("syllableCount")) {  // Sets or changes the meter of the poem
              syllables();
              myInput = "";
            } else {                                         // If '/' isn't followed by a command.
              System.out.println("Failed input. " + newCommand + " is not a command. Input '/ help' for help.");
              myInput = "";
              continueNow = true;
            }
          } else {                                           // If '/' doesn't have a word after it.
            System.out.println("Failed input. '/' must be followed by a command. Input '/ help' for help.");
            continueNow = true;
          }
        }
        else {                                               // If a command isn't inputted
          
        }
      }
      
      if(!(myInput.equals(""))){
        Line l = new Line();
        l = addLineInfo(myInput, l);
        if(checkingRhyme)
          checkRhymeScheme(l);
        if(checkingSyllables)
          checkSyllableCount(l);
        if(failsRhymeCheck){
          System.out.println("failsRhymeCheck");
        } else if (failsSyllableCheck) {
          System.out.println("failsSyllableCheck");
        } else if(poem.size() <= lineEditing) {
          poem.add(l);
          printPoem();
          lineEditing++;
        } else {
          poem.set(lineEditing, l);
          lineEditing++;
        }
      }
      
      if(continueNow == true){
        continueNow = false;
        continue;
      } else if (breakNow == true) {
        breakNow = false;
        break;
      }
      
    }
  }
  
  public static void printPoem() {
    int limit = poem.size();
    for(int i = 0; i < limit; i++) {
      System.out.println(poem.get(i));
    }
  }
  
  public static void help() {
    String helpInput;
    while(true) {
      System.out.println("Type 'overview' to learn about the different available commands, or type 'commands' to learn specific commands. Type 'exit' to return to writing.");
      helpInput = input.nextLine();
      if(helpInput.equals("overview")) {
        System.out.println("PoemWriter does things. Check commands. Returning back to the options...");
      } if (helpInput.equals("commands")) {
        System.out.println("Here are all of the commands.");
        System.out.println("help          - Creates this menu. Invoked by putting '/ help' in the line.\n");
        System.out.println("rhymesWith    - Finds something that rhymes with another word. Invoked by putting '/ rhymesWith' and then the word that you want to find a rhyme for.");
        System.out.println("                The program will give a variety of options for words that rhyme with the given word. You can choose which of those words you'd like to use by typing the chosen word,");
        System.out.println("                In which case that word will be added to the line, or you can type '/' to exit without picking an option.\n");
        System.out.println("save          - Will save the program as a file so that you can edit it later with all poetry details such as rhyme scheme, etc. included.");
        System.out.println("                Doesn't save as a txt file, but instead as something easily loaded by PoemWriter. Invoked by typing '/ save'.\n");
        System.out.println("export        - Saves just the lines as a txt file. Does not keep poetry details. Invoked by typing '/ export'.\n");
        System.out.println("load          - Loads a previously saved file. Invoked by typing '/ load' and then selecting the file.\n");
        System.out.println("import        - Imports a poem and adopts its style to the poetry, so you can model a new poem off of its style of poetry.\n");
        System.out.println("rhymeScheme   - Sets the rhyme scheme for future editing.\n");
        System.out.println("syllableCount - Sets the number of syllables for future editing.");
        System.out.println("Press any key to go back to the help menu.");
        helpInput = input.nextLine();
      } else if (helpInput.equals("exit")) {
        System.out.println("Exiting help menu...");
        break;
      } else {
        System.out.println(helpInput + " is not an option.");
      }
    }
  }
  
  public static String findRhyme(String[] lineArray, int rhymerPlace) {
    String lastWord = lineArray[rhymerPlace];
    String lastWordSimple = lastWord.toLowerCase().replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
    String completeInput;
    String simpleInput;
    String newLine = "";
    
    if(wordMap.containsKey(lastWordSimple)) {
      Word w = wordMap.get(lastWordSimple);
      DSArrayList<String> rhymeNeeds = w.getRhymeNeeds();
      HashSet<String> possibleWords = new HashSet<String>();
      
      for(int i = 0; i < 10; i++){
        int numWords = Words.size();
        int startIdx = (int)(Math.random() * numWords); // random starting place for search
        for(int j = 0; j < numWords; j++){
          Word candidate = Words.get((startIdx + j) % numWords);
          if(candidate.endsWith(rhymeNeeds))
            possibleWords.add(candidate.word);
        }
      }
      
      String[] arrayWords = new String[possibleWords.size()];
      possibleWords.toArray(arrayWords);
      
      System.out.println("Here are some possible rhymes:");
      for(String s : arrayWords) {
        System.out.println(s);
      }
      System.out.println("Do you want any of these rhymes? Type the word if so, type anything else to quit.");
      completeInput = input.nextLine();
      simpleInput = completeInput.toLowerCase().
        replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
      if(possibleWords.contains(simpleInput)) {
        for(int i = 0; i < rhymerPlace - 2; i++) {
          newLine+=lineArray[i]+" ";
        }
        newLine+=completeInput + " ";
        for(int i = rhymerPlace + 1; i < lineArray.length; i++) {
          newLine+=lineArray[i]+" ";
        }
        arrayInput = newLine.split(" ");
        return newLine;
      }
      System.out.println("Not one of the rhyming words. Quiting...");
      return "";
    } else {
      System.out.println("Can't find a rhyme for " + lastWord + ". Please use a different word. Quiting...");
      return "";
    }
  }
  
  public static void save() throws FileNotFoundException {
    String savedFile = "";
    String fileName;
    
    while(true) {
      System.out.println("What do you want to name your new file? (Type 'quit' to quit.)");
      fileName = input.nextLine();
      try {
        if(fileName.equals("quit")) {
          System.out.println("Quitting...");
        }
        File newFile = new File(fileName+".txt");
        if (newFile.createNewFile()) {
          System.out.println("File saved: " + newFile.getName());
          break;
        }
        
        System.out.println("File already exists. Do you want to save anyway (type 'yes') or not (type 'no')?");
        String myInput = input.nextLine();
        if(myInput.equals("yes")) {
          System.out.println("Wiping old file and saving the new...");
          newFile.delete();
          newFile.createNewFile();
          break;
        } else if(myInput.equals("no")) {
          System.out.println("Keeping the old file.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      
      
    }
    
    PrintWriter out = new PrintWriter(fileName + ".txt");
    
    for(int i = 0; i < poem.size(); i++) {
      savedFile += saveLine(poem.get(i));
    }
    savedFile += "/ done";
    out.println(savedFile);
    out.close();
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
  
  public static void export() throws FileNotFoundException{
    String savedFile = "";
    String fileName = "";
    String author = "";
    String title = "";
    Boolean quit = false;
    
    while(true) {
      System.out.println("What do you want to name your finished poem? (Type 'quit' to quit.)");
      fileName = input.nextLine();
      if(fileName.equals("quit")) {
          System.out.println("Quitting...");
          quit = true;
          break;
        }
      System.out.println("Who is the author? (Type 'quit' to quit.)");
      author = input.nextLine();
      if(author.equals("quit")) {
          System.out.println("Quitting...");
          quit = true;
          break;
        }
      System.out.println("What is the title of the poem? (Type 'quit' to quit.)");
      title = input.nextLine();
      try {
        if(title.equals("quit")) {
          System.out.println("Quitting...");
          quit = true;
          break;
        }
        File newFile = new File(fileName+".txt");
        if (newFile.createNewFile()) {
          System.out.println("File created: " + newFile.getName());
          break;
        }
        
        System.out.println("File already exists. Do you want to save anyway (type 'yes') or not (type 'no')?");
        String myInput = input.nextLine();
        if(myInput.equals("yes")) {
          System.out.println("Wiping old file and saving the new...");
          newFile.delete();
          newFile.createNewFile();
          break;
        } else if(myInput.equals("no")) {
          System.out.println("Keeping the old file.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
    
    if(!quit) {
      PrintWriter out = new PrintWriter(fileName + ".txt");
      
      savedFile += " ~ " + title + " ~ \n";
      savedFile += "  by " + author + "\n";
      
      for(int i = 0; i < poem.size(); i++) {
        savedFile += poem.get(i).line + "\n";
      }
      out.println(savedFile);
      out.close();
    }
  }
  
  public static void load() {
    String loadInput = "";
    Boolean quit = false;
    
    while(true) {
      System.out.println("What would you like to load? (Type quit to quit.) (.txt extension isn't necessary.)");
      System.out.println(" . . . Understand that continuing erases your current poem if you don't quit.");
      loadInput = input.nextLine();
      if(loadInput.equals("quit")){
        System.out.println("Quitting...");
        quit = true;
        break;
      }
      
      poem = new DSArrayList<Line>();
      
      try{
        // Wraps a FileReader object around the book (on disk) 
        FileReader f = new FileReader(loadInput + ".txt");
        
        // Wraps a BufferedReader object around the FileReader
        BufferedReader reader = new BufferedReader(f);
        
        String x = reader.readLine();
        
        while(!(x.equals("/ done"))){
          Line l = new Line();
          l.line = x;
          x = reader.readLine();
          
          l.rhymesWith = x;
          x = reader.readLine();
          
          l.syllableCount = Integer.parseInt(x);
          x = reader.readLine();
          
          l.wordCount = Integer.parseInt(x);
          
          for(int i = 0; i < l.wordCount; i++) {
            Word w = new Word();
            String[] xSplit;
            
            x = reader.readLine();
            w.word = x;
            
            x = reader.readLine();
            w.numberOfSyllables = Integer.parseInt(x);
            
            x = reader.readLine();
            if(!(x.equals("null"))) {
              w.partOfSpeech = x;
            } else {
              w.partOfSpeech = null;
            }
            
            x = reader.readLine();
            if(!(x.equals("null"))) {
              w.etymology = x;
            } else {
              w.etymology = null;
            }
            
            x = reader.readLine();
            x = x.replaceAll("[\\[\\]]", "");
            xSplit = x.split(", ");
            for(String s : xSplit) {
              w.phonemes.add(s);
            }
            
            x = reader.readLine();
            x = x.replaceAll("[\\[\\]]", "");
            xSplit = x.split(", ");
            if(!(xSplit[0].equals("")))
            for(String s : xSplit) {
              w.stresses.add(Integer.parseInt(s));
            }
            
            l.wordData.add(w);
          }
          
          x = reader.readLine();
          l.accurate = Boolean.parseBoolean(x);
          x = reader.readLine();
          poem.add(l);
          System.out.println("Successfully loaded poem. Returning to main...");
          printPoem();
          quit = true;
        }
      } catch(IOException e){
        System.out.println("There isn't a file called " + loadInput + ".txt here. Returning to the loading menu...");
      }
      if(quit){
        break;
      }
    }
    
  }
  
  public static void importPoem() {
    if(poem.size() < 1){
      String importInput = "";
      System.out.println("What kind of poem would you like to import?");
      System.out.println("Type 'options' to see what options you have. Type 'quit' to quit.");
      while(true) {
        importInput = input.nextLine();
        if(importInput.equals("options")) {
          System.out.println("English Sonnet - to input, type 'english'");
          System.out.println("... Comprised of three quatrains and a concluding couplet, rhyming abab cdcd efef gg");
          System.out.println("    Commonly associated with Shakespeare.\n");
          System.out.println("Italian Sonnet - to input, type 'italian'");
          System.out.println("... Comprised of an eight-line stanza and six line stanza rhyming ABBAABBA and CDCDCD");
          System.out.println("    Commonly associated with Petrarch / called a Petrarchan sonnet.\n");
          System.out.println("Limerick       - to input, type 'limerick'");
          System.out.println("... Comprised of five lines, rhyming AABBA.\n");
          System.out.println("Choose a poem style or type 'quit'.");
        } else if(importInput.equals("quit")) {
          System.out.println("Quitting...");
          break;
        } else if(importInput.equals("english")) {
          wordRhymes.put("a", "");
          wordRhymes.put("b", "");
          wordRhymes.put("c", "");
          wordRhymes.put("d", "");
          wordRhymes.put("e", "");
          wordRhymes.put("f", "");
          wordRhymes.put("g", "");
          for(int i = 0; i < 2; i++) {
            rhymes.add("a");
            rhymes.add("b");
          }
          for(int i = 0; i < 2; i++) {
            rhymes.add("c");
            rhymes.add("d");
          }
          for(int i = 0; i < 2; i++) {
            rhymes.add("e");
            rhymes.add("f");
          }
          for(int i = 0; i < 2; i++) {
            rhymes.add("g");
          }
          System.out.println("Successfully imported the rhyme scheme for " + importInput + ". Leaving to main...");
          break;
        } else if(importInput.equals("italian")) {
          wordRhymes.put("a", "");
          wordRhymes.put("b", "");
          wordRhymes.put("c", "");
          wordRhymes.put("d", "");
          
          for(int i = 0; i < 2; i++) {
            rhymes.add("a");
            rhymes.add("b");
            rhymes.add("b");
            rhymes.add("a");
          }
          
          for(int i = 0; i < 2; i++) {
            rhymes.add("c");
            rhymes.add("d");
            rhymes.add("c");
          }
          System.out.println("Successfully imported the rhyme scheme for " + importInput + ". Leaving to main...");
          break;
        } else if(importInput.equals("limerick")) {
          wordRhymes.put("a", "");
          wordRhymes.put("b", "");
          
          rhymes.add("a");
          rhymes.add("a");
          rhymes.add("b");
          rhymes.add("b");
          rhymes.add("a");
          System.out.println("Successfully imported the rhyme scheme for " + importInput + ". Leaving to main...");
          break;
        } else {
          System.out.println("That's not an option.");
        }
      }
    } else {
      System.out.println("You can't import a poem style if you've already started writing.");
    }
  }
  
  public static void rhymeScheme() {
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
  
  public static void syllables() {
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
  
  public static void checkRhymeScheme(Line l) {
    Word w = l.wordData.get(l.wordCount - 1);
    int numWords = Words.size();
    String checkInput = "";
    
    if(w.phonemes.size() == 0) {
      System.out.println("Can't find the word for " + w.word);
      System.out.println("Do you want to enter the line anyway? ('yes' / 'no')");
      System.out.println("Doing so will stop any automatic rhyming for the poem.");
      checkInput = input.nextLine();
      if(checkInput.equals("yes")) {
        failsRhymeCheck = true;
        checkingRhyme = false;
      } else {
        failsRhymeCheck = true;
      }
    } else if(!(rhymes.get(lineEditing) == null)) {
      wordRhymes.replace(rhymes.get(lineEditing), "", w.word);
      Word rhymingWord = wordMap.get(wordRhymes.get(rhymes.get(lineEditing)));
      System.out.println(rhymingWord);
      System.out.println(w);
      if(w.endsWith(rhymingWord.getRhymeNeeds())) {
        failsRhymeCheck = false;
      } else {
        System.out.println("The CMUPron file that *Dr. Robert Hochberg* gave us says that " + w.word + " and " + rhymingWord.word + " don't rhyme.");
        System.out.println("Do you want to keep the line anyway? (Type 'yes' if you want to override, type 'no' if you want to rewrite the line.)");
        checkInput = input.nextLine();
        if(checkInput.equals("yes")) {
          System.out.print("Overriding...");
          failsRhymeCheck = false;
        } else {
          System.out.println("I'll take that as a no.");
          failsRhymeCheck = true;
           
        }
      }
    } else {
      if(poem.size() == 0) {
        System.out.println("Do you want to set a rhyme scheme first? ('yes' / 'no')");
        System.out.println("Know that saying no means that PoemWriter won't track the rhyme scheme for this poem.");
        checkInput = input.nextLine();
        if(checkInput.equals("yes")) {
          System.out.println("Returning to main...");
          failsRhymeCheck = true;
          
        } else {
          System.out.println("Removing the rhyme scheme requirement...");
          failsRhymeCheck = false;
          checkingRhyme = false;
          
        }
      } else {
        System.out.println("By all accounts, the poem is finished. You can export it, or you can start over.");
        failsRhymeCheck = true;
      }
    }
  }
  
  public static void checkSyllableCount(Line l) {
    String checkInput = "";
    if ((poem.size() == syllableCount.size()) && (poem.size() != 0)) {
        System.out.println("By all accounts, the poem is finished. You can export it, or you can start over.");
        failsSyllableCheck = true;
    } else if(l.accurate) {
      if(!(syllableCount.get(lineEditing) == null)) {
        if(l.syllableCount == syllableCount.get(lineEditing)) {  // If syllables line up
          failsSyllableCheck = false;
        } else {                                                 // If syllables don't line up
          System.out.println("The syllable count you gave, " + syllableCount.get(lineEditing) + ", and the count for the line, " + l.syllableCount + ", don't match.");
          failsSyllableCheck = true;
        }
      } else {                                                   // If syllables haven't been set yet
        System.out.println("Do you want to set your syllable count first? ('yes' / 'no')");
        System.out.println("Know that saying no means that PoemWriter won't track the syllable count for this poem.");
        checkInput = input.nextLine();
        if(checkInput.equals("yes")) {
          System.out.println("Returning to main to rewrite the line...");
          failsSyllableCheck = true;
          
        } else {
          System.out.println("Removing the syllable count requirement...");
          failsSyllableCheck = false;
          checkingSyllables = false;
          
        }
      }
    } else if(poem.size() == 0) {
      System.out.println("It seems that at least one of the words in the line isn't in our database.");
      System.out.println("Do you want to rewrite the lines to get a poem with a syllable count? ('yes' / 'no')");
      System.out.println("Know that saying no means that PoemWriter won't track the syllable count for this poem.");
      checkInput = input.nextLine();
      if(checkInput.equals("yes")) {
        System.out.println("Returning to main to rewrite the line...");
        failsSyllableCheck = true;
        
      } else {
        System.out.println("Removing the syllable count requirement...");
        failsSyllableCheck = false;
        checkingSyllables = false;
        
      }
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