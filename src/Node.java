import java.util.LinkedList;

public class Node {

    private Node parent;
    private LinkedList<Node> children;
    private int[] state;
    private int value;
    private int colNum;

    public Node(){
        parent = null;
        children = new LinkedList<>();
        state = new int[42];
        value = 0;
    }

    public Node(Node parent){
        this.parent = parent;
        children = new LinkedList<>();
        state = parent.getState().clone();
        value = 0;
    }

    public Node(int[] state){
        parent = null;
        children = new LinkedList<>();
        this.state = state;
        value = 0;
    }

    public void setParent(Node parent){this.parent = parent;}

    public Node getParent(){return parent;}

    public void setColNum(int colNum){this.colNum = colNum;}

    public int getColNum(){return colNum;}

    public void addChild(Node child){
        children.add(child);
    }

    public LinkedList<Node> getChildren(){return children;}

    public void setValue(int value){this.value = value;}

    public int getValue(){return value;}

    public Node getFirstChild(){
        return children.getFirst();
    }

    public int[] getState(){return state;}

    public void setState(int[] state){this.state = state;}

    public void deleteChildren(){children = new LinkedList<>();}

    public int getDepth(Node root){
        int depth = 0;

        if(root == null)
            return 0;

        if(root.getChildren().size() == 0)
            return 1;

        for(Node child : root.getChildren())
            depth = Math.max(depth, getDepth(child));

        return depth +1;

    }
}