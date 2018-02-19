package Calculator;

public class ComplexNumber extends Number{
    protected double imaginaryNum;
    public ComplexNumber(double realNum, double imaginaryNum){
        super(realNum);
        
        this.imaginaryNum = imaginaryNum;
        
    }
    
    public void changeRealNum(double newRealNum){
        this.realNum = newRealNum;
    }
    
    public void changeImaginaryNum(double newNum){
        imaginaryNum = newNum;
    }
    
    public double getImaginaryNum(){
        return imaginaryNum;
    }
    
    @Override
    public String toString(){
        if (realNum == 0)
            return imaginaryNum + "i";
        else if (imaginaryNum > 0)
            return realNum + "+" + imaginaryNum + "i";
        else
            return realNum + "" + imaginaryNum + "i";
    }
    
    
}
