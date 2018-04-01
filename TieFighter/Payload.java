/* Shannen Barrameda sib170130 */
package TieFighter;

public class Payload implements Comparable<Payload>{
    private String pilotName;
    private double patrolArea;
    private boolean flag;                       //tracks comparison
    
    public Payload(String pilotName){
        this.pilotName = pilotName;
        patrolArea = 0;
        flag = false;
    }
    
    public double getArea(){
        return patrolArea;
    }
    
    public void setArea(double newArea){
        patrolArea = newArea;
    }
    
    public void setPilotName(String newName){
        pilotName = newName;
    }
    
    public String getPilotName(){
        return pilotName;
    }
    
    public void setFlag(boolean newFlag){
        flag = newFlag;
    }
    
    public boolean getFlag(){
        return flag;
    }
    
    @Override
    public int compareTo(Payload otherPayload){
        return this.compareTo(otherPayload);
    }
    
    @Override
    public String toString(){
        return String.format("%-20s\t\t%.2f\r\n" , pilotName, patrolArea);
    }
    
    public String byPilot(){
        return String.format("%s",pilotName);
    }
    
    public String byArea(){
        return String.format("%.2f",patrolArea);
    }
}
