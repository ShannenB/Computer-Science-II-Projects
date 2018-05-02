//Shannen Barrameda sib170130
package TieFighter;
import java.util.*;

public class Pilots {
    String pilotName;
    LinkedList<Integer> desiredPath;
    boolean valid;
    int pathLength;
    
    public Pilots(){
      
    }
    public Pilots(String pilotName, LinkedList<Integer> desiredPath){
        this.pilotName = pilotName;
        this.desiredPath = desiredPath;
        valid = false;
        pathLength = 0;
    }
    
    public void setPathLength(int newLength){
        pathLength = newLength;
    }
    
    public void setValidity(boolean newValidity){
        valid = newValidity;
    }
    
    public void clearValidity(){
        valid = false;
    }
    
    //insertion sort
    public void numericalSort(ArrayList<Pilots> pilots){
        
        int n = pilots.size();
        for (int i=1; i<n; ++i)
        {
            Pilots key = pilots.get(i);
            int j = i-1;
            
            while(j >= 0 && pilots.get(j).compareTo(key) > 0){
                pilots.set(j+1, pilots.get(j));    
                j--;
            }
            
             pilots.set(j+1,key);
        }
    }
    
    //helpw with insertion sort
    //if path lengths are the same, sort alphabetically while ignoring the case of pilotName
    public int compareTo(Pilots otherPilot){
        int difference = this.pathLength - otherPilot.pathLength;
        
        if(difference == 0){
            difference = this.pilotName.compareToIgnoreCase(otherPilot.pilotName);
        }
        
        return difference;
    }
    
    
    @Override
    //formats output to be in a table with three columns
    public String toString(){
        String validity = "invalid";
        if(valid){
            validity = "valid";
            return String.format("%-20s %-10s %-10s", pilotName, pathLength, validity);
        }
        else{
            return String.format("%-20s %-10s %-10s", pilotName,"", validity);
        }    
    }
}
