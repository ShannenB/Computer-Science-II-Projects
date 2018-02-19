//Shannen Barrameda sib170130
package Calculator;

public class Number {
    public double realNum;
    
    public Number(double value){
        this.realNum = value;
    }
    
    public double getValue(){
        return realNum;
    }
     
    public void changeValue(double newValue){
        realNum = newValue;
    }
    
    @Override
    public String toString(){
        return "" + realNum;
    }
    
  
}