//Shannen Barrameda sib170130
package TieFighter2;

public class Node<T> {
    private T payloadObj;                          //will hold payload obj in program
    private Node next;
    private Node prev;
  
    public Node(){
        
    }
    
    public Node(T payloadObj){
        this.payloadObj = payloadObj;
        this.next = null;
        this.prev = null;
    }
    
    public void setPayload(T payload){
        payloadObj = payload;
    }
    public Payload getPayload(){
        return (Payload)payloadObj;
    }
    
    public void setNext(Node newNext){
        next = newNext;
    }

    public Node getNext(){
        return next;
    }
    
    public void setPrev(Node newPrev){
        prev = newPrev;
    }
    
    public Node getPrev(){
        return prev;
    }
    
    public String toString(){
        return ((Payload)payloadObj).toString();
    }
      
    
}
