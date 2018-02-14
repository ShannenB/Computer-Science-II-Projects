//Shannen Barrameda sib170130

package Calculator;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Main {
    private static int index1, index2;
    private static String operator = "";
    private static String num1 = "", num2 = "";
    
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter the expression to evaluate: ");
        String expression = input.nextLine();
        
    
        validate(expression);
        
       
    }
    
    public static void validate(String str){
       if(numSpaces(str)){
           index1 = str.indexOf(" ");                                           //finds index of first space in expression
           index2 = str.lastIndexOf(" ");                                       //finds index of second space in expression
           
           operator = str.substring(index1 + 1, index2);                        //stores operator
           
           num1 = str.substring(0, index1);
           num2 = str.substring(index2 + 1);
           
       }
       
    }
    
    //checks if str has more than two spaces
    private static boolean numSpaces(String str){
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.substring(i,i+1).contains(" "))
                count++;
                
        }
        if(count != 2)
            return false;
        else 
            return true;
    }
}
