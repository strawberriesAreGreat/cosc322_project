package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
//variables
    private Node <T> parent = null;
    private List<Node<T>> children = new ArrayList<> ();
    private T heuristic = null;


//constructors
    public Node(T heuristic) {
        this.heuristic = heuristic;
    }
    public Node( Node<T> parent, T heuristic ) {
        this.parent = parent;
        this.heuristic = heuristic;
    }


//is the current node the root or a leaf
    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }


//Setters
  public void setParent( Node<T> parent ) {
        parent.appendChild(this);
        this.parent = parent;
    }
    public void setHeuristic(T heuristic) {
        this.heuristic = heuristic;
    }


//Getters
    public List<Node<T>> getChildren() {
        return children;
    }
    
    public T getHeuristic() {
        return this.heuristic;
    }

//create, append, and delete 
    public void createChild(T heuristic) {
        Node<T> kid = new Node<>(heuristic);
        kid.setParent(this);
        this.children.add(kid);
    }

    public void appendChild(Node<T> kid) {
        kid.setParent(this);
        this.children.add(kid);
    }

    public void deleteParent() {
        this.parent = null;
    }
}