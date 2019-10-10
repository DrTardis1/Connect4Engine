import java.util.LinkedList;

public class Node {

    private Node parent;
    private LinkedList<Node> children;
    private int[] state;
    private int value;

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

    public void addChild(Node child){
        children.add(child);
        /*
        if(children.size() == 0)
            this.children.add(child);

        else{
            for(int i = 0; i < children.size(); i++){
                if(children.get(i).getValue() < child.getValue())
                    children.add(child);
            }
        }*/

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
}
