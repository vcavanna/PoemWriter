package com.mycompany.app;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.ArrayList;

class PoemWriter extends Poet {
  private static final String CMUPronLocation = "C:\\\\Users\\\\Vincent\\\\Documents\\\\computerPrograms\\\\poemWriter\\\\my-app\\\\src\\\\main\\\\java\\\\com\\\\mycompany\\\\app\\\\cmupron.txt";
  private static final String JaneAustenLocation = "C:\\Users\\Vincent\\Documents\\computerPrograms\\poemWriter\\my-app\\src\\main\\java\\com\\mycompany\\app\\JaneAusten.txt";
  static Scanner input = new Scanner(System.in);
  static String[] wordsInInput = null; // TODO: G19 (now fixed)
  static Poem poem;
  
  public static void main(String[] args) {
    Words = new ArrayList<Word>();
    readABookMakeUnique(JaneAustenLocation);
    readCMUPron(CMUPronLocation);
    poem = new Poem();
    System.out.println("Hi there. Type a line of poetry to begin. Type '/ help' to learn how to work this program."); // TODO: G8 & G5 here. Create a session object and initiating script
    takeInputs();
  }
  
  // TODO: G30 Three nested else statements! It clearly does more than one thing!
  public static void takeInputs() { // TODO: G20. It starts the entire program, it doesn't just take inputs. Again justifies a separate function.
    Boolean continueNow;
    Boolean breakNow;

    while (true){                                        // Creates a new line or takes a command
      String myInput = input.nextLine();
      wordsInInput = myInput.split(" ");
      continueNow = false; // TODO: G19... what does this mean??
      breakNow = false; // TODO: G19... what does this variable mean?
      
      for(int i = 0; i < wordsInInput.length; i++) { // TODO: G19: i is used all throughout this large script.
        if(continueNow == true){
          break;
        } else if (breakNow == true) {
          break;
        }
        
        if(wordsInInput[i].equals("/")) {                      // If a command is inputted
          if(!(i == wordsInInput.length - 1)) {
            String newCommand = wordsInInput[i+1];
            // TODO: G5. Could replace with a case statement and enums.
            if(newCommand.equals("help")) {                  // Presents information on commands
              help();
              myInput="";
            } else if(newCommand.equals("rhymesWith")) {     // Finds rhymes for the following word
              myInput = findRhyme(wordsInInput, i + 2);
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
              poem.rhymeScheme(); // TODO: G20: rhymeScheme method sets the rhymeScheme, it doesn't "get" the rhymeScheme.
              myInput = "";
            } else if(newCommand.equals("syllableCount")) {  // Sets or changes the meter of the poem
              poem.syllables(); // TODO: G20: syllables method doesn't return syllables.
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
          // TODO: G9
        }
      }
      
      if(!(myInput.equals(""))){
        poem.addLine(myInput);
      }
      
      // TODO: G23 Consider polymorphism for "command" objects?
      if(continueNow == true){
        continueNow = false;
        continue;
      } else if (breakNow == true) {
        breakNow = false;
        break;
      }
      
    }
  }
  
  // TODO: G30: Prints help and navigates help
  public static void help() {
    String helpInput;
    while(true) {
      System.out.println("Type 'overview' to learn about the different available commands, or type 'commands' to learn specific commands. Type 'exit' to return to writing.");
      helpInput = input.nextLine();
      if(helpInput.equals("overview")) { // TODO: G28 make a function to process the conditionals
        System.out.println("PoemWriter does things. Check commands. Returning back to the options...");
        // TODO: G9. Not really dead code, but should be. No one wants this ^^ print statement
      } if (helpInput.equals("commands")) {
        // TODO: G5. So many print statement lines! Why?!?
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
        // TODO: G8 :Actually important statement is buried under a swath of print statements.
        helpInput = input.nextLine();
      } else if (helpInput.equals("exit")) {
        System.out.println("Exiting help menu...");
        break;
      } else {
        System.out.println(helpInput + " is not an option.");
      }
    }
  }
  
  // TODO: G19: What is rhymerplace?
  // TODO: G6: Couldn't this hypothetically be a method in the Line class?
  // TODO: G30: Function finds a rhyme, does I/O user interaction, does flow control, and saves the line into the poem.
  public static String findRhyme(String[] lineArray, int rhymerPlace) {
    // TODO: G19: Is it a given that the rhyming word is the lastWord?
    String lastWord = lineArray[rhymerPlace];
    // TODO: G8 Can take this into a separate function
    String lastWordSimple = lastWord.toLowerCase().replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
    String completeInput;
    String simpleInput;
    String newLine = "";
    
    // TODO: G28 take out this boolean
    if(wordMap.containsKey(lastWordSimple)) {
      Word w = wordMap.get(lastWordSimple);
      ArrayList<String> rhymeNeeds = w.getRhymeNeeds();
      HashSet<String> possibleWords = new HashSet<String>();
      
      // TODO: G25: Replace 10 with a meaningful constant
      // TODO: G16: What does the iterator do, why does it only do up to 10? Fix with constants?
      // TODO: G19: Define i with a meaningful name.
      for(int i = 0; i < 10; i++){
        int numWords = Words.size();
        // TODO: G32: Why a random starting place?
        int startIdx = (int)(Math.random() * numWords); // random starting place for search
        for(int j = 0; j < numWords; j++){
          // TODO: G21: I have no idea what the below line *actually* does. It references an inherited class, and the variable is too vague.
          Word candidate = Words.get((startIdx + j) % numWords);
          // TODO: G28 Replace with a function.. possibly not just the if conditional but the following statement too
          if(candidate.endsWith(rhymeNeeds))
            possibleWords.add(candidate.text);
        }
      }
      
      // TODO: G30: This could be extrapolated to a simple function that gets the array of words.
      String[] arrayWords = new String[possibleWords.size()];
      possibleWords.toArray(arrayWords);
      // TODO: G30: This can be turned into a I/O control flow function with some serious thought given to the algorithm.
      System.out.println("Here are some possible rhymes:");
      for(String s : arrayWords) {
        System.out.println(s);
      }
      System.out.println("Do you want any of these rhymes? Type the word if so, type anything else to quit.");
      completeInput = input.nextLine();
      // TODO: G5: Haven't I seen this line before? Extrapolate to a function!
      simpleInput = completeInput.toLowerCase().
        replaceAll("[)(\\[\\]!,.?{} :;\"\\'\\-]", "");
      // TODO: G28.
      // TODO: G5. I'm sure I've used this conditional before.
      if(possibleWords.contains(simpleInput)) {
        // TODO: G8: This could easily be a method to simplify the cognitive load of reading this function. Let's call the method "insertRhymingWordInLine"
        for(int i = 0; i < rhymerPlace - 2; i++) {
          newLine+=lineArray[i]+" ";
        }
        newLine+=completeInput + " ";
        for(int i = rhymerPlace + 1; i < lineArray.length; i++) {
          newLine+=lineArray[i]+" ";
        }
        wordsInInput = newLine.split(" ");
        // TODO: G11 The below line for when a valid rhyme isn't chosen has a print statement. Shouldn't this path too?
        return newLine;
      }
      System.out.println("Not one of the rhyming words. Quiting...");
      // TODO: G16: WHY does it return a null line?
      return "";
    } else {
      System.out.println("Can't find a rhyme for " + lastWord + ". Please use a different word. Quiting...");
      // TODO: G16: WHY does it return a null line?
      return "";
    }
  }
  
  // TODO: G20: Given what the function actually does, it would be more useful to say saveSession. Which in turn raises the below comment.
  // TODO: G13: The below function belongs in a "session" class which I haven't created yet.
  public static void save() throws FileNotFoundException {
    // TODO: G19: Is it savedFile, or fileText? The file isn't saved *yet*, is it?
    String savedFile = "";
    String fileName;
    
    while(true) { // TODO: Make this a function
      System.out.println("What do you want to name your new file? (Type 'quit' to quit.)");
      // TODO: G35 The filename could be put into a configuration file for the poem.
      fileName = input.nextLine();
      try {
        if(fileName.equals("quit")) {
          System.out.println("Quitting...");
          // TODO: G2: Oh my gosh. It doesn't actually quit.
        }
        // TODO: G32: Why does the file get named a .txt file? One would think that's the name of the exported poem.
        File newFile = new File(fileName+".txt");
        if (newFile.createNewFile()) {
          System.out.println("File saved: " + newFile.getName());
          break;
        }
        // TODO: G17: The ability to delete an old file does not belong in a function called saveFile.
        // TODO: ??: Using errors as control flow might be ideal here. But I don't know the Heuristic for that.
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
    
    // TODO: G30: This is the one thing that the function should do. Take input, save the input, relegate the rest to other functions.
    PrintWriter out = new PrintWriter(fileName + ".txt");
    poem.save();
    out.println(savedFile);
    out.close();
  }
  
  // TODO: G20: This function doesn't save a line, it formats lines in the format that a session would need.
  // TODO: G17: Obviously the line object should be able to format itself.
  // TODO: G18: See the above for what to do instead: "when in doubt, make the function nonstatic"
  public static String saveLine(Line l) {
    // TODO: G16: This is hard. Serializing needs to be clear so deserializing functions can follow it easily.
    String rv = "";
    rv += l.text + "\n";
    rv += l.rhymesWith + "\n";
    rv += l.syllableCount + "\n";
    rv += l.wordCount + "\n";
    for(int i = 0; i < l.wordData.size(); i++) {
      Word w = l.wordData.get(i);
      rv += w.text + "\n";
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
    
    // TODO: G21: WHY is there a while statement here? What part of the code makes this necessary? Very difficult to see and bug prone.
    while(true) {
      // TODO: G5: Why ask for the name of the poem twice?
      // TODO: G35: Data on the name, author, title, etc. should clearly be reserved for the session object level.
      
      // TODO: G5: Each of these "take inputs" are quite similar
      System.out.println("What do you want to name your finished poem? (Type 'quit' to quit.)");
      // TODO: G2: Setting the fileName with the input means that for the rest of this scope, fileName can be "quit". Very confusing.
      fileName = input.nextLine();
      if(fileName.equals("quit")) {
          System.out.println("Quitting...");
          quit = true;
          break;
        }
      System.out.println("Who is the author? (Type 'quit' to quit.)");
      // TODO: G2: Setting the author with the input means that for the rest of this scope, author can be "quit". Very confusing.
      author = input.nextLine();
      if(author.equals("quit")) {
          System.out.println("Quitting...");
          quit = true;
          break;
        }
      System.out.println("What is the title of the poem? (Type 'quit' to quit.)");
      // TODO: G2: Setting the title with the input means that for the rest of this scope, title can be "quit". Very confusing.
      title = input.nextLine();
      try {
        // TODO: G11: Inconsistently putting the "quit" check in here just confuses developers.
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
      
      for(int i = 0; i < poem.lines.size(); i++) {
        savedFile += poem.lines.get(i).text + "\n";
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
      
      poem = new Poem();
      
      try{
        // Wraps a FileReader object around the book (on disk) 
        FileReader f = new FileReader(loadInput + ".txt");
        
        // Wraps a BufferedReader object around the FileReader
        BufferedReader reader = new BufferedReader(f);
        
        String x = reader.readLine();
        
        while(!(x.equals("/ done"))){
          Line l = new Line();
          l.text = x;
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
            w.text = x;
            
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
          poem.lines.add(l);
          System.out.println("Successfully loaded poem. Returning to main...");
          poem.printPoem();
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
    if(poem.lines.size() < 1){
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
          poem.wordRhymes.put("a", "");
          poem.wordRhymes.put("b", "");
          poem.wordRhymes.put("c", "");
          poem.wordRhymes.put("d", "");
          poem.wordRhymes.put("e", "");
          poem.wordRhymes.put("f", "");
          poem.wordRhymes.put("g", "");
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("a");
            poem.rhymes.add("b");
          }
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("c");
            poem.rhymes.add("d");
          }
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("e");
            poem.rhymes.add("f");
          }
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("g");
          }
          System.out.println("Successfully imported the rhyme scheme for " + importInput + ". Leaving to main...");
          break;
        } else if(importInput.equals("italian")) {
          poem.wordRhymes.put("a", "");
          poem.wordRhymes.put("b", "");
          poem.wordRhymes.put("c", "");
          poem.wordRhymes.put("d", "");
          
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("a");
            poem.rhymes.add("b");
            poem.rhymes.add("b");
            poem.rhymes.add("a");
          }
          
          for(int i = 0; i < 2; i++) {
            poem.rhymes.add("c");
            poem.rhymes.add("d");
            poem.rhymes.add("c");
          }
          System.out.println("Successfully imported the rhyme scheme for " + importInput + ". Leaving to main...");
          break;
        } else if(importInput.equals("limerick")) {
          poem.wordRhymes.put("a", "");
          poem.wordRhymes.put("b", "");
          
          poem.rhymes.add("a");
          poem.rhymes.add("a");
          poem.rhymes.add("b");
          poem.rhymes.add("b");
          poem.rhymes.add("a");
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
}