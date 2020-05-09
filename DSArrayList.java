import java.util.Arrays;
import java.util.Comparator;

// To make a class generic (that is, has a type inside of it that
// can be declared when you write a program using this class)
// Use angle brackets in the class declaration, with a "type variable"
// inside the angle brackets
public class DSArrayList <E>{
  
  // We make these fields private, for "security."
  // Not cyber-ish-security, to prevent hacking,
  // but to keep our coders from hurting themselves.
  private E a[];         // Backing array
  private int length;    // Number of items in the DSArrayList
  private int lastPlace; // for memory
  
  public DSArrayList(){
    this.length = 0;
    a = (E[]) (new Object[5]);
    lastPlace = 0;
  }
  
  /**
   * Insert a new item at the end of the current items,
   * in the backing array a.
   */
  public void add(E newItem){
    // Check to see if we have space
    if(length >= a.length){ // No more room. Panic!
      //System.out.println("Doubling to size " + a.length * 2);
      // make a new array, double the size of our current backing array
      E[] newArray = (E[]) (new Object[2*a.length]);
      
      // copy from the old array into the new array
      for(int i = 0; i < length; i++)
        newArray[i] = a[i];
      
      // Update the backing array variable a to point to our new array
      this.a = newArray;
    }
    
    // Place the item newItem into a, the backing array
    a[length] = newItem;
    this.length++;
  }
  
  /**
   * Add a new item at index idx, moving the rest over
   */
  public void add(int idx, E item){
    E last = null;
    if(this.length > 0)
      last = a[this.length - 1];
    for(int i = this.length - 1; i > idx; i--){
      a[i] = a[i-1];
    }
    a[idx] = item;
    if(last != null){
      this.add(last);
    } else {
      this.length++;
    }
  }
      
  
  
  /**
   * Returns to the user an item at the given index in the backing array
   * @param 
   */
  public E get(int idx){
    return this.a[idx];
  }
  
  
  /**
   * Returns the # of items that have been added
   */
  public int size(){
    return this.length;
  }
  
  
  /**
   * Returns true if this DSArrayList contains the item
   */
  public boolean contains(E item){
    for(int i = 0; i < this.length; i++){
      if(a[i].equals(item))
        return true;
    }
    return false;   // Never returned "true," so it's not here
  }
  
  
  
  /**
   * Change an item at the given index idx, to the given value val
   * Returns the former value at that index
   */
  public E set(int idx, E val){
    // keep a user from hurting himself
    if(idx >= a.length || idx < 0){
      System.out.println("Trying to add into an illegal location");
      return null; // "null" in java means "reference to non-existent object"
    }
    E oldval = this.a[idx];
    this.a[idx] = val;
    return oldval;
  }
  
  
  
  /** 
   * Return a string representation of the object
   */
  public String toString(){
    //return Arrays.toString(this.a);
    String rv = "[";
    for(int i = 0; i < this.length; i++){
      rv += a[i];
      if(i < this.length - 1)
        rv += ", ";
    }
    rv += "]";
    return rv;
  }
  
  
  
  /**
   * Removes an item from the array
   */
  public int remove(){
    this.length--;
    return 0;
  }
  
  
  
  /**
   * Takes a Word object as input
   * Produces the location in the DSArrayList class for that Word object
   * 
   * Linear Search
   */
  public int indexOf(E w){
    for(int i = 0; i < this.length; i++){ 
      if(a[i].equals(w))  //if the item at index i is w, then return i;
        return i;
    }
    return -1;   // Secret code for "not found"
  }
  
  
  
  /**
   * Takes a Word object as input
   * Produces the location in the DSArrayList class for that Word object
   */
  public int indexOfWithMemory(E w){
    for(int i = lastPlace; i < this.length; i++){
      //if the item at index i is w, then return i;
      if(a[i].equals(w)){
        lastPlace = i;
        return i;
      }
    }
    return -1;   // Secret code for "not found"
  }
  
  
  
  /**
   * Sort the entries in the backing array.
   */
  public void sort(){
    Arrays.sort(this.a, 0, this.length, (a, b) -> ((Comparable)(a)).compareTo(b));
    // The (Comparable) above makes a promise to the Java compiler that "a" will have a compareTo method
  }
  
  
  
  /**
   * Search for an item
   * Assumes the backing array is already sorted
   */
  public int binarySearchIndexOf(E item){
    return binarySearchIndexOfRecursion(item, 0, this.size()-1);
  }
  
  
  
  /**
   * Make it more powerful by adding parameters, 
   * left and right endpoints of where to search
   * */
  public int binarySearchIndexOfRecursion(E item, int start, int end){
    // Base Case
    if(end < start)
      return -1;
    
    int midpoint = (start + end) / 2;  // Index of the middle of the array
    if(a[midpoint].equals(item))
      return midpoint;
    
    if(end == start) 
      return -1;   // Base case for subarray of size 1, and item is not there
    
    if(((Comparable)item).compareTo(a[midpoint]) > 0){ // X.compareTo(Y) returns something > 0 if X > Y
      return binarySearchIndexOfRecursion(item, midpoint + 1, end);
    } else {
      return binarySearchIndexOfRecursion(item, start, midpoint - 1);
    }
  }
  
  
  
  public static void main(String[] args){
    //Use Case
    DSArrayList al = new DSArrayList<String>();
    al.add("certain");
    al.add("slant");
    al.add("light");
    System.out.println(al.size()); // returns 3
    al.add("winter");
    al.add("afternoons");
    al.add("cathedral");
    System.out.println(al.get(1)); // return "slant"
    
    System.out.println(al);
    al.sort();
    System.out.println(al);
    System.out.println(al.binarySearchIndexOf("light"));
    System.out.println(al.binarySearchIndexOf("winter"));
    System.out.println(al.binarySearchIndexOf("afternoons"));
    System.out.println(al.binarySearchIndexOf("pug"));
    
    
    DSArrayList b = new DSArrayList<String>();
    String s = "a sudden thought of one so pale for love of her and all in vain so she was come through wind an rain be sure i looked up at her eyes happy and proud at last i knew";
    for(String ss : s.split(" "))
      b.add(ss);
    b.sort();
    System.out.println(b);
    System.out.println(b.binarySearchIndexOf("wind"));
    System.out.println(b.binarySearchIndexOfRecursion("love", 25, 37));
      
  }
}