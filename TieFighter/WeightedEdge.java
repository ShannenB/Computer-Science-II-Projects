//Shannen Barrameda sib170130
package TieFighter;

public class WeightedEdge {
    private int edge;
    private int weight;
    
    public WeightedEdge(int e, int weight){
        edge = e;
        this.weight = weight;
    }
    
    public int getEdge(){
        return edge;
    }
    
    public int getWeight(){
        return weight;
    }
    
    @Override
    public String toString(){
        return edge + "";
    }
}
