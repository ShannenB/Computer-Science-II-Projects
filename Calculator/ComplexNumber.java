package Calculator;

public class ComplexNumber extends Number{
    protected double imaginaryNum;
    public ComplexNumber(double realNum, double imaginaryNum){
        super(realNum);
        
        this.imaginaryNum = imaginaryNum;
        
    }
    
    public void changeImaginaryNum(double newNum){
        imaginaryNum = newNum;
    }
    
    public double getImaginaryNum(){
        return imaginaryNum;
    }
    
    @Override
    public String toString(){
        return imaginaryNum + "i";
    }
    
    
}
