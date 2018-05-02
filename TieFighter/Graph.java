//Shannen Barrameda sib170130
package TieFighter;

import java.util.*;

public class Graph {
    private int maxSize;
    private int numVertices;
    private int numConnections;
    private boolean marked[];
    private final LinkedList<WeightedEdge> adjacencyList[];
    private final ArrayList<Integer> vertices;

   
    public Graph(){
        maxSize = 10 + 1;
        numVertices = 0;
        adjacencyList = new LinkedList[maxSize];
        for(int i = 1; i < maxSize; ++i){
            adjacencyList[i] = new LinkedList();
        }
        vertices = new ArrayList();
        marked = new boolean[maxSize];
    }
    
    public Graph(int numNodes){
        maxSize = numNodes + 1;
        numVertices = 0;
        adjacencyList = new LinkedList[maxSize];
        for(int i = 1; i < maxSize; ++i){
            adjacencyList[i] = new LinkedList();
        }
        vertices = new ArrayList();
        marked = new boolean[maxSize];
    }
    public void insertVertex(int v){
        vertices.add(v);
    }
    
    public int getVertex(int index){
        return vertices.get(index);
    }
    public String printVertices(){
        return vertices.toString();
    }
    public int maxSize(){
        return maxSize;
    }
    public int size(){
        return numVertices;
    }
    
    public boolean isEmpty(){
        return numVertices == 0;
    }
    
    //makes sure that edge has not been placed into linkedlist before
    public boolean emptyV(int v, WeightedEdge e){
        if(adjacencyList[vertices.indexOf(v) + 1].contains(e.getEdge()) && 
                adjacencyList[vertices.indexOf(e.getEdge()) + 1].contains(v))
            return false;
        else
            return true;
                
    }
    public void insertEdge(int v, WeightedEdge e){
        adjacencyList[vertices.indexOf(v) + 1].add(e);
        
        if(emptyV(v,e))
            numVertices++;
    }
    
    public LinkedList<WeightedEdge> getNeighbors(int v){
        return adjacencyList[vertices.indexOf(v) + 1];
    }
    
    public void print(){
        for(int i = 1; i < adjacencyList.length; i++){
            System.out.println(vertices.get(i-1) + " " + adjacencyList[i]); 
        }
    }
    
    LinkedList<Integer> path = new LinkedList();                                //filled with vertices from dfs
    int pathLength = 0;
    
    //depth first traversal and check to make sure path given in routes file exists in graph
    public LinkedList<Integer> dfs(int v, LinkedList<Integer> path2) {
        numConnections++;
        path.add(v);
        marked[vertices.indexOf(v) + 1] = true;
        int next = 0;
        if(!path2.isEmpty() && vertices.contains(v)){
            next = path2.remove();                                              //removes individual values from queue (list of vertices to visit/check)
            for (WeightedEdge w : adjacencyList[vertices.indexOf(v) + 1]) {
                if (w.getEdge() == next){
                    pathLength += w.getWeight();                                //computes weight at each visited vertex
                    dfs(w.getEdge(), path2);
                }
            }
        }
        return path;
    }
    public int getPathLength(){
        return pathLength;
    }
    
    public void clearPathLength(){
        pathLength = 0;
    }
    public void clearPath(){
        path.clear();
    }
   
    //checks if the originalpath(path from routes file) is valid by checking if it is the same one as the postDFS path
    public boolean validPath(LinkedList<Integer> originalPath, LinkedList<Integer> postDFS){
        if(originalPath.size() != postDFS.size())                               //if sizes of the two linkedlists(Original desired path
            return false;                                                       //and linkedlist returned by dfs are different, immediately know
                                                                                //path can't possibly exist
        else{
            for(int i = 0; i < originalPath.size(); i++){
                if(originalPath.get(i) != postDFS.get(i))
                    return false;
            }
            return true;
        }    
        
    }
            
    
    // depth first search from v
    
    public LinkedList<Integer> dfs(int v) {
        numConnections++;
        path.add(v);
        marked[vertices.indexOf(v) + 1] = true;
       
        for (WeightedEdge w : adjacencyList[vertices.indexOf(v) + 1]) {
            if (!marked[vertices.indexOf(w.getEdge()) + 1]) {
                dfs(w.getEdge());
            }
        }
        return path;
    }
    
    public int getConnections(){
        return numConnections;
    }
}