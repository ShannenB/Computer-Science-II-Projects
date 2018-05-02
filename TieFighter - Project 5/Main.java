//Shannen Barrameda sib170130
package TieFighter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        File input = new File("galaxy1.txt");
        File input2 = new File("pilot_routes1.txt");
        File output = new File("patrols.txt");
        BufferedReader br = new BufferedReader(new FileReader(input));
        BufferedReader br1 = new BufferedReader(new FileReader(input));
        BufferedReader br2 = new BufferedReader(new FileReader(input2));
        PrintWriter printer = new PrintWriter(output);
       
        String line,str ="", temp = "";
        int initVertex, edge, weight, numVertices = 0;
        while(br.ready()){
            if(br.readLine() != null){            
                numVertices++;
            }
        }
        Graph graph = new Graph(numVertices);
        while(br1.ready()){
            if((line = br1.readLine()) != null){
                initVertex = Integer.parseInt(line.substring(0,line.indexOf(" ")));                         //parse initial vertex in line
                graph.insertVertex(initVertex);                                                             //adds vertex to graph
                
            
                if(line.charAt(line.length() - 1) != ' ')
                    line = line + " ";
                
                str = line.substring(line.indexOf(" ")+ 1);
                
                while(str != ""){
                    if(!str.contains(" ")){
                        break;
                    }
                    else{
                        temp = str.substring(0, str.indexOf(" "));
                        
                    }
                    
                    edge = Integer.parseInt(temp.substring(0,temp.indexOf(",")));                            //parses first value in pair as adjacent 
                    weight = Integer.parseInt(temp.substring(temp.indexOf(",") + 1));                        //parses second value as weight
                    
                    WeightedEdge wEdge = new WeightedEdge(edge, weight);

                    graph.insertEdge(initVertex, wEdge);                                                     //inserts each new edge with weight to graph
                    
                    if(!str.contains(" ")){                                                                  //removes already parsed part of string
                        str = "";
                    }
                    else{
                        str = str.substring(str.indexOf(" ") + 1);
                    }
                }
            }
        }
        
        String line2, pilotName = "";
        LinkedList<Integer> pathQueue = new LinkedList();
        Pattern namePattern = Pattern.compile("(.+? )[\\d]+");                   //pattern to capture valid names
        ArrayList<Pilots> pilotList = new ArrayList();
        Pilots pilot = new Pilots();
        
        while(br2.ready()){
            if((line2 = br2.readLine()) != null){
                Matcher matcher1 = namePattern.matcher(line2);
                if(matcher1.find()) {
                    pilotName = matcher1.group(1);                              
                    str = line2.substring(pilotName.length());  
                }
                
                if(line2.charAt(line2.length() - 1) != ' ')                     //adds space at the end to facilitate parsing if space dne
                    str = str + " ";
                
                while(str != ""){
                    if(!str.contains(" ")){
                        break;
                    }
                    else{
                        pathQueue.addLast(Integer.parseInt(str.substring(0, str.indexOf(" "))));
                    }
                    
                    if(!str.contains(" ")){
                        str = "";
                    }
                    else{
                        str = str.substring(str.indexOf(" ") + 1);
                    }
                }
                
                LinkedList<Integer> clone = (LinkedList<Integer>) pathQueue.clone();                //clones deisred path from routes file to prevent manipulation
                LinkedList<Integer> testPath = graph.dfs(pathQueue.remove(),pathQueue);             //checks if dfs is possible with desired path
                
                boolean validity = graph.validPath(clone, testPath);                                //checks if original path is the same as path returne by dfs
                
                pilot = new Pilots(pilotName, clone);
                
                pilot.setValidity(validity);
                if(!validity)
                    pilot.setPathLength(1*1000000*100000);
                else
                    pilot.setPathLength(graph.getPathLength());                                     //gets path length with given path
                
                pilotList.add(pilot);

                pathQueue.clear();                                                                  //reset queue, path and path length
                graph.clearPathLength();
                graph.clearPath();     
                
            }
        }
        
        
        pilot.numericalSort(pilotList);
        
        for(Pilots pilot1: pilotList)
            printer.print(pilot1 +"\n");
        
        br.close();
        br1.close();
        br2.close();
        printer.close();
    
    }

}
