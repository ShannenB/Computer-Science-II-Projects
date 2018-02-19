//Shannen Barrameda sib170130

package Calculator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
        
        static Number realNum1, realNum2;
        static ComplexNumber complexNum1, complexNum2;
        
        
    public static void main(String[] args) throws FileNotFoundException{
       
     
   
        File expressions = new File("sample_expressions.txt");
        File results = new File("results.txt");
        Scanner input = new Scanner(expressions);
        PrintWriter output = new PrintWriter(results);
        
     
        
        
        
        if(expressions.canRead() && results.canWrite()){
                output.printf("%-20.20s\t%-20.20s\n", "Expression", "Result");
            while(input.hasNext()){
                String expression = input.nextLine();
                validate("2.0i < 4-1i");
                
                
                output.printf("%-20.20s\t%-20.20s\n", expression, "answer");
                if(realNum1 != null )
                System.out.println("Num1 " +realNum1.toString());
                if(complexNum1 !=  null)
                    System.out.println(" " + complexNum1.toString() + "\n");
           
                if(realNum2 != null )
                System.out.println("Num2 " +realNum2.toString());
                if(complexNum2 !=  null)
                    System.out.println(" " + complexNum2.toString()+ "\n");

            }
          
       
        }
        
        input.close();
        output.close();
    }
    
    public static void validate(String str){
        int index1 = 0, index2 = 0, indexi_1 = 0, plusIndex_1 = 0,
                  indexi_2 = 0, plusIndex_2 = 0;
        String operator = "";
        String num1 = "", num2 = "";
        
        if(numSpaces(str)){
            index1 = str.indexOf(" ");                                           //finds index of first space in expression
            index2 = str.lastIndexOf(" ");                                       //finds index of second space in expression

            operator = str.substring(index1 + 1, index2);                        //stores operator

            num1 = str.substring(0, index1);
            num2 = str.substring(index2 + 1);
            indexi_1 = num1.indexOf("i");
            plusIndex_1 = num1.indexOf("+");
            indexi_2 = num2.indexOf("i");
            plusIndex_2 = num2.indexOf("+");
            //everything before first space
           
            if(!num1.contains("i"))                                             //case for a, -a
                realNum1 = new Number(Double.parseDouble(num1));
            else if(num1.contains("-")){                                                              //case for -a-bi, a-bi        
                complexNum1 = new ComplexNumber(Double.parseDouble(num1.substring(0,num1.lastIndexOf("-"))),
                        Double.parseDouble(num1.substring(num1.lastIndexOf("-"), indexi_1)));
            }
            else if(plusIndex_1 == -1){                                         //case for bi, -bi
                complexNum1 = new ComplexNumber(0, Double.parseDouble(num1.substring(0,indexi_1)));
            }
            else if(plusIndex_1 != -1){                                         //case for a+bi, -a+bi
                complexNum1 = new ComplexNumber(Double.parseDouble(num1.substring(0, plusIndex_1)),
                        Double.parseDouble(num1.substring(plusIndex_1 + 1, indexi_1)));
            }                                                                   
            
         
            if(!num2.contains("i"))                                             //case for a, -a
                realNum2 = new Number(Double.parseDouble(num2));
            else if(num2.contains("-")) {                                                              //case for -a-bi, a-bi        
                complexNum2 = new ComplexNumber(Double.parseDouble(num2.substring(0,num2.lastIndexOf("-"))),
                        Double.parseDouble(num2.substring(num2.lastIndexOf("-"), indexi_2)));
            }
            else if(plusIndex_2 == -1){                                         //case for bi, -bi
                complexNum2 = new ComplexNumber(0, Double.parseDouble(num2.substring(0, indexi_2)));
            }
            else if(plusIndex_2 != -1){                                         //case for a+bi, -a+bi
                complexNum2 = new ComplexNumber(Double.parseDouble(num2.substring(0, plusIndex_2)),
                        Double.parseDouble(num2.substring(plusIndex_2 + 1, indexi_2)));
            }                                                                   
            
   
       }
        


    }
    
    
    
    //checks if str has more than two spaces
    private static boolean numSpaces(String str){
        int count = 0;
        for(int i = 0; i < str.length(); i++){
            if(str.substring(i,i+1).contains(" "))
                count++;
                
        }
        if(count > 2)
            return false;
        else 
            return true;
    }
    
//    public static double computeArithmetic(Object number1, Object number2, String operator){
//        if(number1 instanceOf Number()){
//            if(number2 instanceOf Number()){
//                return number1
//        }
//        
//        }
//    }
//    
//    public static boolean computeRelational(Object number1, Object number2){
//        
//    }
    
    
}
