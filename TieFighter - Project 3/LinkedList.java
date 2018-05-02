//Shannen Barrameda sib170130
//mergesort @line97 Main
//recursive 85-96
package TieFighter2;

public class LinkedList {
    
    private Node head;
    private Node tail;
    private int size;
    
    public LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    
    public LinkedList(Node node){
        head = node;
        tail = node;
        
        if (tail == null){
            size = 0;
        }
        else 
            size = 1;
    }
    
    public void setHead(Node newHead){
        head = newHead;
    }
    
    public Node getHead(){
        return head;
    }
    
    public void setTail(Node newTail){
        tail = newTail;
    }
    
    public Node getTail(){
        return tail;
    }
    
    
    //adding a node to the end of the LinkedList
    public void addLast(Node current){
       if(current == null){                                                  //throws nullPointerEx if argument is nullpointer
           throw new NullPointerException("");
       }
       else if(!isEmpty()){                                                     //if list has other elements new tail pointer is made
           Node prev = tail;                                                    //last element is now argument that is passed in      
           tail = current;                                      //head ptr still points to original; tail pointer points to last element(latest addition)
           prev.setNext(tail);
       }
       else{                                                                    //if list doesnt have other elements
           tail = current;                                         //firt element is the same as last element
           head = tail;                                                         //head and tail ptrs point to same element
       }
       size++;                                                                  //increment size with every addition of an element
    }
    
  

    //non recursive toString method
    @Override
    public String toString(){
        Node temp = this.getHead();
        String x = temp.getPayload().toString();
        
        if(temp == null || temp.getNext() == null){
            return null;
        }
        else{
           while(temp.getNext() != null){
                temp = temp.getNext();
                x += temp.getPayload().toString();
           }
        }
        return x;
    }
    
    //recursive toString method
    public String toString(Node node){
        if(node == null){
            return "";
        }
        if(node.getNext() == null){
            return node.getPayload().toString();                                //prints info of current node if the next is null
        }
        return node.getPayload().toString() + toString(node.getNext());
    }
    
    
       public boolean searchByArea(String area){
        boolean flag = false;
        Node temp = head;
  
        while(temp != null){
            if(Math.abs(temp.getPayload().getArea() - Double.parseDouble(area)) < .01){ //checks to see if area of pilot is close to area being looked for
                flag = true;
            }
            temp = temp.getNext();
        }
        return flag;
    }

    public boolean searchByName(String name){
        boolean flag = false;
        Node temp = head;
        
        while(temp != null){
            if(temp.getPayload().getPilotName().equals(name)){                      //check if value of pilot's name is equal to actual name
                flag = true;
            }
            temp = temp.getNext();
        }
        return flag;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public int size(){
        return size;
    }
    
    public Node sortedMergeByName(Node a, Node b) 
    {

        Node result = null;
        
        if (a == null)
            return b;
        if(b == null)
            return a;
        if (a.getPayload().getPilotName().compareTo(b.getPayload().getPilotName()) <= 0){           //compares each character in name
            result = a;
            result.setNext(sortedMergeByName(a.getNext(), b));
        }
        else{
            result = b;
            result.setNext(sortedMergeByName(a, b.getNext()));
        }
        
        return result;
    }
    
    public Node mergeSortByName(Node h) 
    {
        // Base case : if head is null
        if (h == null || h.getNext() == null)
        {
            return h;
        }
        
        Node middle = getMiddle(h);
        
        Node nextofMiddle = middle.getNext();
        
        middle.setNext(null);
        
        Node left = mergeSortByName(h);
        
        Node right = mergeSortByName(nextofMiddle);
        
        Node sortedList = sortedMergeByName(left, right);
        return sortedList;
    }
    public Node sortedMergeByArea(Node a, Node b){
        Node result = null;
        
        if (a == null)
            return b;
        if(b == null)
            return a;
        if(a.getPayload().getArea() <= b.getPayload().getArea()){
            result = a;
            result.setNext(sortedMergeByArea(a.getNext(), b));
        }
        else{
            result = b;
            result.setNext(sortedMergeByArea(a, b.getNext()));
        }
        
        return result;
    }
        
                   
            
   
 
    public Node mergeSortByArea(Node h) 
    {
        // Base case : if head is null
        if (h == null || h.getNext() == null)
        {
            return h;
        }
        
        Node middle = getMiddle(h);
        
        Node nextofMiddle = middle.getNext();
        
        middle.setNext(null);
        
        Node left = mergeSortByArea(h);
        
        Node right = mergeSortByArea(nextofMiddle);
        
        Node sortedList = sortedMergeByArea(left, right);
        return sortedList;
    }
    
    
   
    public Node getMiddle(Node head){
        if(head == null) { 
            return head; 
        }
        Node slow = head; 
        Node fast = head.getNext();

        while(fast != null) {
            fast = fast.getNext();
            if(fast != null){
            slow = slow.getNext(); 
            fast = fast.getNext();
            }
        }
        return slow;
}
   
   
   
    
//   
    //    
//    public void addFirst(Node current){
//        if(current == null){
//            throw new NullPointerException("");
//        }
//        else if(!isEmpty()){
//            Node newNode = current;
//            newNode.setNext(head);
//            head = newNode;
//        }
//        else{
//            head = current;
//            tail = head;
//        }
//        size++;
//        
//    }
    
//    
//    public void addAfter(Node prevNode, Node current){
//        if(current == null){
//            throw new NullPointerException("");
//        }
//        else{
//            Node newNode = current;
//            newNode.setNext(prevNode.getNext());
//            prevNode.setNext(newNode);
//        }
//        size++;
//    }
    
}
