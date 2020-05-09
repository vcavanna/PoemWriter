import java.util.HashMap;
import java.util.Set;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class numberWords{
    //It occured to me that I probably didn't need hashmaps for this, as the sorted list already has the number data in the index... 
    //Oh well. It was good practice.
    static HashMap<Integer, String> numberToAlphaInt = new HashMap<>(); //Maps the ordered number to the number in alphabetical order
    static HashMap<String, Integer> WordToNumber = new HashMap<>(); //A guide mapping each integer to its written word
    static DSArrayList<DSArrayList<Integer>> Cycles = new DSArrayList<>(); //Stores the cycles
    static int startingIndex = 17; //should be the first number in the txt file

    public static void main(String[] args){
        //Use the txt file to fill numberToAlphaInts and WordToNumber
        readAndFill("numberWords.txt");
        System.out.println(numberToAlphaInt);
        //Find the cycles and fill Cycles
        getCycles(numberToAlphaInt);
    }

    static void readAndFill(String book){
        try{
          // Wraps a FileReader object around the book (on disk) 
          FileReader f = new FileReader(book);
          // Wraps a BufferedReader object around the FileReader
          BufferedReader reader = new BufferedReader(f);
          
          DSArrayList<String> alphabeticIntegers = new DSArrayList<>();
          // read the book. Will read "null" when the book ends
          String x = reader.readLine();
          while(x != null){
            int firstSpace = x.indexOf(" ");
            int num = Integer.parseInt(x.substring(0, firstSpace));
            String word = x.substring(firstSpace + 1);
            WordToNumber.put(word, num);
            alphabeticIntegers.add(word);
            x = reader.readLine();
          }
          alphabeticIntegers.sort();
          for(int i = 0; i < alphabeticIntegers.size(); i++){
              numberToAlphaInt.put(i + startingIndex, alphabeticIntegers.get(i));
          }
        } catch(IOException e){
          System.out.format("IOException %s\n", e);
        }
      }

      static void getCycles(HashMap<Integer, String> map){
          while(map.size() > 0){
            Set<Integer> keySet = map.keySet();
            ArrayList<Integer> listOfKeys = new ArrayList<Integer>(keySet);
            int index = listOfKeys.get(0);

            DSArrayList<Integer> cycle = new DSArrayList<>();
            int next = WordToNumber.get(map.get(index));
            do{
                cycle.add(next);
                index = next;
                next = WordToNumber.get(map.get(index));
                map.remove(index);
            } while(next != cycle.get(0));
            System.out.println(cycle);
            Cycles.add(cycle);
          } 
      }
}
