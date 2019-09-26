public class Tree {

    private Node root;

    public Tree(){
        root = new Node();
    }

    public Tree(Node root){
        this.root = root;
    }

    public Node getRoot(){return root;}

    public void setRoot(Node root){this.root = root;}

    public int getDepth(Node root){
        int depth = 0;

        if(root == null)
            return 0;

        if(root.getChildren().size() == 0)
            return 1;

        for(Node child: root.getChildren())
            depth = Math.max(depth, getDepth(child));

        return depth +1;

    }
}