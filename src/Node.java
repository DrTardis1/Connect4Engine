import java.util.LinkedList;

public class Node {

    private Node parent;
    private LinkedList<Node> children;
    private int[][] state;

    //Used to store evaluation function's rating of this game state
    private int value;

    public Node(){
        parent = null;
        children = new LinkedList<>();
        state = new int[6][7];
        value = 0;
    }

    public Node(Node parent){
        this.parent = parent;
        children = new LinkedList<>();
        state = new int[6][7];
        value = 0;
    }

    public void setParent(Node parent){this.parent = parent;}

    public Node getParent(){return parent;}

    public void addChild(Node child){this.children.add(child);}

    public LinkedList<Node> getChildren(){return children;}

    public void setValue(int value){this.value = value;}

    public int getValue(){return value;}

    public Node getBestValue(){
        Node temp = children.getFirst();
        for(Node current : children)
            if(current.getValue() > temp.getValue())
                temp = current;

        return temp;
    }
}
