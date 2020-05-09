import java.util.Scanner;
import java.util.Arrays;

public class Code{
  static Scanner scanner = new Scanner(System.in);
  
  public static void main(String[] args){
    // Read input for the test cases
    int N = scanner.nextInt();
    int[] a = new int[N];
    for(int i = 0; i < N; i++)
      a[i] = scanner.nextInt();
    int numDup = 0;
    
    // Your code here.
    for(int i = 0; i < N; i++){
      for(int x = i + 1; x <= N - 1; x++){
        if(a[i] == a[x]){
          boolean flag = false;
          for(int z = 0; z < i; z++){
            if(a[z] == a[i]){
              flag = true;
            }
          }
          
          
          
          if(flag == false){
            numDup = numDup + 1;
            System.out.println(i + "," + x);
          }else{
            flag = false;
          }
        }
      }
    }
    
    
    
    
    
    System.out.println("There are " + numDup + " #'s with duplicates.");
  }
}