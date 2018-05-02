//Shannen Barrameda sib170130  
package TieFighter2;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        File input1 = new File("pilot_routes3.txt");
        File input2 = new File("commands3.txt");
        File output1 = new File("results.txt");
        File output2 = new File("pilot_areas.txt");
        PrintWriter firstOutput = new PrintWriter(output1);
        PrintWriter secondOutput = new PrintWriter(output2);
        
        LinkedList pilots = new LinkedList();
        String line, name;
        
        Pattern namePattern = Pattern.compile("(.+?) [ .\\-*\\d]+,[ .\\-*\\d]+");               //captures the name in each line
        BufferedReader br1 = new BufferedReader(new FileReader(input1));
     
        
        while(br1.ready()){
            if((line = br1.readLine()) != null){
                  Matcher matcher1 = namePattern.matcher(line);
                        if(matcher1.find()) {
                            name = matcher1.group(1);
                            if(validateNames(name) && validateCoordinates(line)){
                                    Payload pilot = new Payload(name);
                                    pilot.setArea(coordinates(line));
                                    Node newPilot = new Node(pilot);
                                    
                                    pilots.addLast(newPilot);                                   //adds each validated pilot to linkedlist
                                    
                            }
                            
                        }
                       
                    }   
                }
        
        
        
        
        BufferedReader br2 = new BufferedReader(new FileReader(input2));
        String flag = "not found";
        boolean last1 = false;                                           //to determine which last sort will print to output file
    
        while(br2.ready()){
            if(((line) = br2.readLine()) != null){
                if(validateCommands(line)){
                    Pattern commandsPattern = Pattern.compile("^(sort area dec|sort area asc|sort pilot asc|sort pilot dec)|(\\d+.\\d\\d+)|([\\w|\\'|\\\\-| ]+)$");       //captures appropriate command
                    Matcher matcher1 = commandsPattern.matcher(line);
                    if(matcher1.find()){
                        if(matcher1.group(1) != null && (!matcher1.group(1).contains("sort j") && !matcher1.group(1).contains("search"))){                                                    
                            if(matcher1.group(1).contains("area dec")){
                                
                                pilots.setHead(pilots.mergeSortByArea(pilots.getHead()));
                                //ensures that tail pointer is actually to tail of linkedlist
                                while(pilots.getTail().getNext() != null){
                                    pilots.setTail(pilots.getTail().getNext());
                                }
                                firstOutput.printf("%-20s Head: %s, Tail: %s\n",matcher1.group(1),
                                        pilots.getTail().getPayload().byArea(), pilots.getHead().getPayload().byArea());
                                //prints head and tail pointers
                                last1 = true; 
                            }
                            else
                                if(matcher1.group(1).contains("area asc")){
                                pilots.setHead(pilots.mergeSortByArea(pilots.getHead()));
                               while(pilots.getTail().getNext() != null){
                                    pilots.setTail(pilots.getTail().getNext());
                                }
                                firstOutput.printf("%-20s Head: %s, Tail: %s\n",matcher1.group(1),
                                        pilots.getHead().getPayload().byArea(),pilots.getTail().getPayload().byArea()); //prints head and tail pointers
                                last1 = true;
                            }
                            else if(matcher1.group(1).contains("pilot asc")){
                                pilots.setHead(pilots.mergeSortByName(pilots.getHead()));
                                while(pilots.getTail().getNext() != null){
                                    pilots.setTail(pilots.getTail().getNext());
                                }
                                firstOutput.printf("%-20s Head: %s, Tail: %s\n", matcher1.group(1), pilots.getHead().getPayload().byPilot(),
                                        pilots.getTail().getPayload().byPilot());                
                                last1 = false;
                            }
                            else{
                                pilots.setHead(pilots.mergeSortByName(pilots.getHead()));
                                while(pilots.getTail().getNext() != null){
                                    pilots.setTail(pilots.getTail().getNext());
                                }
                                firstOutput.printf("%-20s Head: %s, Tail: %s\n", matcher1.group(1), pilots.getTail().getPayload().byPilot(),
                                 pilots.getHead().getPayload().byPilot());      
                                last1 = false;
                            }
                            
                        }
                        else if(matcher1.group(2) != null ){                    //search by numbers group
                            if(pilots.searchByArea(matcher1.group(2))){
                                flag = "found";
                            }
                            firstOutput.printf("%-20s %-10s \n", matcher1.group(2), flag);
                            flag = "not found";
                            
                        }                                                       //search by names group
                        else if(matcher1.group(3) != null && (!matcher1.group(3).contains("sort j") &&  !matcher1.group(3).contains("search"))){
                            if(pilots.searchByName(matcher1.group(3))){
                                flag = "found";
                            }
                            firstOutput.printf("%-20s %-10s \n", matcher1.group(3), flag);    
                            flag = "not found";
                        }                   
                    }          
                }
            }
        }
        
        //determines which sort is last
        if(last1)
            pilots.setHead(pilots.mergeSortByArea(pilots.getHead()));
        else 
            pilots.setHead(pilots.mergeSortByName(pilots.getHead()));
           
            
        secondOutput.print(pilots.toString(pilots.getHead()));
       
        br1.close();
        br2.close();
        firstOutput.close();
        secondOutput.close();
    }
    
    public static boolean validateNames(String str) throws FileNotFoundException, IOException { 
        if(str == null)
            return false;
        else if(str.matches(".*[_\"(){}!@#$%^&*+=?].*")){
            return false;
        }
        else{
            Pattern namePattern = Pattern.compile("^[\\w|\'|\\-| ]+$");         //validates names are alphanumeric, hyphen or apostrophe
            return namePattern.matcher(str).matches();        
        }
        
    } 
    
    public static double coordinates(String str){
          
          ArrayList<Double> x = new ArrayList<Double>();
          ArrayList<Double> y = new ArrayList<Double>();
          
          Pattern coordinates = Pattern.compile("([-.\\d]+),([-.\\d]+)");                         //captures each coordinates  & validates only numbers as coordinates 
          
          Matcher matcher1 = coordinates.matcher(str);
          while(matcher1.find()){
                        
                     x.add(Double.parseDouble(matcher1.group(1)));
                     y.add(Double.parseDouble(matcher1.group(2)));      
          }
          
          return findArea(x,y);
            
    }
    
    public static boolean validateCoordinates(String str){
           boolean flag = true;
           Pattern comma = Pattern.compile("([\\-\\d.\\d,]+)");                                 //ensures that coordinates only have neg, a single period and a comma in between 
           Matcher commaMatcher = comma.matcher(str);
           while(commaMatcher.find()){
               if(!commaMatcher.group(1).contains(",")){
                   return false;

               }     
           }
           
           Pattern coordinates = Pattern.compile("( [\\-*\\w.\\w]+,[\\-*\\w.\\w]+)");            //validates space between name and coordinates              
           Pattern coordinates2 = Pattern.compile("^( [\\-*\\d.\\d]+),([\\-*\\d.\\d]+)$");       //validates only numbers as coordinates
           Matcher matcher1 = coordinates.matcher(str);
           while(matcher1.find()){
                if(matcher1.group(1).matches(".*[A-za-z].*"))
                    flag = false;
                Matcher matcher2 = coordinates2.matcher(matcher1.group(1));

                if(matcher2.find()){ 
                      if(matcher2.group(2).indexOf(".") != matcher2.group(2).lastIndexOf("."))   //checks for double periods in a single coordinate
                           flag = false;
                      if(matcher2.group(2).contains("-")){                                       //checks that negatives are only at beginning of coordinate
                          if(matcher2.group(2).indexOf("-") != 0 || matcher2.group(2).indexOf("-") != matcher2.group(2).indexOf(",")+1 )
                            flag = false;
                      }    
                }
            }              
        return flag;
    }
    
    public static boolean validateCommands(String str) throws FileNotFoundException,IOException{
        Pattern commandsPattern = Pattern.compile("^(sort area dec|sort area asc|sort pilot asc|sort pilot dec)|(\\d+.\\d\\d+)|([\\w|\\'|\\\\-| ]+)$");                                                                          //validates sort command, looking for a pilot, and searching by number
        return commandsPattern.matcher(str).matches();
    }

    public static double findArea(ArrayList<Double> x, ArrayList<Double> y){                                //calculates area based on area function given
        int n = 1;                                                                                                 
        double sum = (x.get(1) + x.get(0)) * (y.get(1) - y.get(0));                                             

        while (n < x.size()-1)                                                    
        {  
            sum += (x.get(n+1) + x.get(n)) * (y.get(n+1) - y.get(n));
            n++;                                                                                                    
        }

        return Math.abs(sum) * 0.5;    
        }

}
