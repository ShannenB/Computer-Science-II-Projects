//Shannen Barrameda sib170130
//CS 4341.HON Digital Logic
//source: https://www.geeksforgeeks.org/insertion-sort
package quinemcclusky;

import java.util.*;
import java.io.*;
//4 4 5 6 8 9 10 13 d 0 7 15
public class QuineMcClusky {
    public static int NUM_VAR;
    public static ArrayList<String> minTermsBit = new ArrayList<String>();
    public static ArrayList<String> minTermsInt = new ArrayList<String>();
    public static ArrayList<String> implicants = new ArrayList<String>();
    public static ArrayList<String> epis = new ArrayList<String>();
    public static ArrayList<String> dominatingRows = new ArrayList<String>();
    public static ArrayList<Boolean> record = new ArrayList<Boolean>();
    public static Map<String, Boolean> map;
//    public static ArrayList<String> dominatingRows = new ArrayList<String>();
    public static boolean table[][];
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
       
        String input = scan.nextLine();
        String[] array = input.split(" ");
        int numOfMin = 0, numOfDC = 0;
        NUM_VAR = Integer.parseInt(array[0]);
        
        //if array contains "d", check for don't cares
        //otherwise skip and create matrix of size of array
        if(input.contains("d")){
            for(int i = 1; i < array.length; i++){
                if(!array[i].equals("d")){
                    numOfMin++;
                }
                else{           
                    break;
                }
            }
            numOfDC = array.length - numOfMin - 1;
        }
        else{
            numOfMin = array.length;
        }
        
        
        
        /* for each string in the array 
        convert the string to int then to binary        */
        for(int i = 1; i < array.length; i++){
            if(!array[i].equals("d")){
                minTermsInt.add(array[i]);
                minTermsBit.add(toBinary(Integer.parseInt(array[i])));
            }
        }
        
        table = new boolean[numOfMin - numOfDC][numOfMin - numOfDC];
        int n = minTermsInt.size();
        sortBySetBitCount(minTermsBit, n); 
        printArr(minTermsInt, n); 
        printArr(minTermsBit,n);

        
        
    }
    
   /* function to sort number of 1's in binary digit */
    private static void insertionSort(ArrayList<String> arr, ArrayList<Integer> aux, int n){
        for(int i  = 1; i < n; i++){
            int key1 = aux.get(i);
            int key2;
            if(arr.get(i) != null)
                key2 = Integer.parseInt(arr.get(i));
            else
                key2 = -1;
            
            int j = i-1;
            
            while(j >= 0 && aux.get(j) > key1){
                aux.set(j+1, aux.get(j));
                arr.set(j+1, arr.get(j));
            
                j--;
            }
            aux.set(j+1, key1);
            arr.set(j+1,addBits(Integer.toString(key2)));
            
        }
    }
    private static void printArr(ArrayList<String> arr, int n) 
    { 
        for (String s: arr)
            System.out.println(s + " "); 
        
    } 
    private static void sortBySetBitCount(ArrayList<String> arr, int n){
        ArrayList<Integer> aux = new ArrayList<Integer>();
        
        for(int i = 0; i < n; i++){
            aux.add(countBits(arr.get(i)));
        }
        
        insertionSort(arr, aux, n);
    }
   /* function to count number of bits*/
    private static int countBits(String a){
        int count = 0;
        if(a == null)
            return -1;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == '1')
                count++;
        }        
        return count;
    }
   /*to check for bit difference between bit strings*/
   private static boolean checkDiff(String bit1, String bit2){
       int count = 0;
     
       for(int index = 0; index < 16; index++){
           if(bit1.charAt(index) != bit2.charAt(index)){
               count++;
           }
       }
       
       return (count == 1);
   } 
   /*returns string with "-" where bit1 differs from bit2*/
   private static String diffBit(String bit1, String bit2){
       StringBuilder result = new StringBuilder(bit1);
       for(int index = 0; index < bit1.length() ; index++){
           if(bit1.charAt(index) != bit2.charAt(index)){ 
              result.setCharAt(index, '-');
           }
       }
       return result.toString();
   }
   /*returns decimal value passed in as binary value
   returned as 16 bits, though insignificant bits
   */
   private static String toBinary(int decimal){
       StringBuilder binary = new StringBuilder();
       while(decimal > 0){
           binary.append(decimal % 2);
           decimal = decimal / 2;
       }            
       return addBits(binary.toString());
   }  
   private static String addBits(String str){
       StringBuilder binary = new StringBuilder(str);
       
       int i = 0;
       
       while(binary.toString().length() < NUM_VAR){
           binary.append("0");
       }
       
       return binary.reverse().toString();
   }  
   private void makeTable(String[] col, int start, int n){
       
   }

}
