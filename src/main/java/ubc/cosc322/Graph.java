package ubc.cosc322;

import ygraph.ai.smartfox.games.GameStateManager;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final List<Node> nodes;
    private final int rowLength;

    public Graph(int[][] board) {
        rowLength = board.length;

        nodes = new ArrayList<>(rowLength * board[0].length);

        initializeGraph(board);

    }

    public void updateGraph(int currPosX, int currPosY, int nextPosX, int nextPosY, int arrowPosX, int arrowPosY, GameStateManager.Tile player){

        if(!player.isPlayer()) return;

        //Set current to empty
        int currIndex = currPosX * rowLength + currPosY;
        Node currNode = nodes.get(currIndex);
        currNode.setValue(GameStateManager.Tile.EMPTY);

        //Enable edges for all connected nodes
        toggleConnectedNodeEdges(currNode, true);

        //Set next to player
        int nextIndex = nextPosX * rowLength + nextPosY;
        Node nextNode = nodes.get(nextIndex);
        nextNode.setValue(player);

        //Disable edges for all connected nodes
        toggleConnectedNodeEdges(nextNode, false);

        //Set arrow to arrow
        int arrowIndex = arrowPosX * rowLength + arrowPosY;
        Node arrowNode = nodes.get(arrowIndex);
        arrowNode.setValue(GameStateManager.Tile.FIRE);

        //Disable edges for all connected nodes
        toggleConnectedNodeEdges(arrowNode, false);

        updateDistances();

    }

    private void initializeGraph(int[][] board){

        createNodes(board);

        //Connect nodes to their neighbors
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                int index = i * board.length + j;

                Node node = nodes.get(index);

                //Connect north node
                if(i - 1 >= 0){
                    int north = index - board.length;
                    Node northNode = nodes.get(north);
                    addEdge(node, northNode, Edge.Direction.NORTH, northNode.value.isEmpty());
                }

                //Connect northeast node
                if(i - 1 >= 0 && j + 1 < board[0].length){
                    int northEast = index - board.length + 1;
                    Node northEastNode = nodes.get(northEast);
                    addEdge(node, northEastNode, Edge.Direction.NORTH_EAST, northEastNode.value.isEmpty());
                }

                //Connect east node
                if(j + 1 < board[0].length){
                    int east = index + 1;
                    Node eastNode = nodes.get(east);
                    addEdge(node, eastNode, Edge.Direction.EAST, eastNode.value.isEmpty());
                }

                //Connect southeast node
                if(i + 1 < board.length && j + 1 < board[0].length){
                    int southEast = index + board.length + 1;
                    Node southEastNode = nodes.get(southEast);
                    addEdge(node, southEastNode, Edge.Direction.SOUTH_EAST, southEastNode.value.isEmpty());
                }

                //Connect south node
                if(i + 1 < board.length){
                    int south = index + board.length;
                    Node southNode = nodes.get(south);
                    addEdge(node, southNode, Edge.Direction.SOUTH, southNode.value.isEmpty());
                }

                //Connect southwest node
                if(i + 1 < board.length && j - 1 >= 0){
                    int southWest = index + board.length - 1;
                    Node southWestNode = nodes.get(southWest);
                    addEdge(node, southWestNode, Edge.Direction.SOUTH_WEST, southWestNode.value.isEmpty());
                }

                //Connect west node
                if(j - 1 >= 0){
                    int west = index - 1;
                    Node westNode = nodes.get(west);
                    addEdge(node, westNode, Edge.Direction.WEST, westNode.value.isEmpty());
                }

                //Connect northwest node
                if(i - 1 >= 0 && j - 1 >= 0){
                    int northWest = index - board.length - 1;
                    Node northWestNode = nodes.get(northWest);
                    addEdge(node, northWestNode, Edge.Direction.NORTH_WEST, northWestNode.value.isEmpty());
                }
            }
        }

        updateDistances();

    }

    private void createNodes(int[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                int index = i * board.length + j;
                Node n = new Node(index, GameStateManager.Tile.valueOf(board[i][j]));
                nodes.add(n);
            }
        }
    }

    private void addEdge(Node n1, Node n2, Edge.Direction direction, boolean enabled){
        n1.edges.add(new Edge(n2, direction, enabled));
    }

    private void toggleConnectedNodeEdges(Node n, boolean toggle) {
        for (Edge e : n.edges)
            for (Edge e2 : e.other.edges)
                if (e2.other == n)
                    e2.enabled = toggle;
    }

    private void updateDistances(){
        int size = nodes.size();
        for(Node n : nodes){
            if(n.isEmpty())
                Distance.setDistances(n);
            else {
                n.setKdist1(Integer.MAX_VALUE);
                n.setKdist2(Integer.MAX_VALUE);
                n.setQdist1(Integer.MAX_VALUE);
                n.setQdist2(Integer.MAX_VALUE);
            }
        }
    }

    public List<Node> getNodes(){
        return nodes;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Node n : nodes){
            sb.append("Node: ").append(n.index).append(" {").append(n.value).append("}").append(" Connected to: ");
            for(Edge e : n.edges) {
                if(e.enabled)
                    sb.append(e.other.index).append(" ").append(e.direction).append(", ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static class Edge {
        public enum Direction {
            NORTH("North"),
            NORTH_EAST("North East"),
            EAST("East"),
            SOUTH_EAST("South East"),
            SOUTH("South"),
            SOUTH_WEST("South West"),
            WEST("West"),
            NORTH_WEST("North West");

            public final String label;

            Direction(String direction) {
                this.label = direction;
            }

            @Override
            public String toString(){
                return label;
            }

        }

        private final Node other;
        private final Direction direction;
        private boolean enabled;

        public Edge(Node other, Direction direction, boolean enabled){
            this.other = other;
            this.direction = direction;
            this.enabled = enabled;
        }

        @Override
        public String toString(){
            return "Index:" + other.index + ", Value: "+ other.value + ", Direction: " + direction;
        }

        public Node getNode(){
            return other;
        }

        public Direction getDirection() {
            return direction;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

    }

    public static class Node {

        private final int index;
        private GameStateManager.Tile value;

        private int qdist1;
        private int qdist2;
        private int kdist1;
        private int kdist2;

        private final List<Edge> edges;

        public Node(int index, GameStateManager.Tile value){
            this.index = index;
            setValue(value);

            //Starting distance is "infinity"
            qdist1 = Integer.MAX_VALUE;
            qdist2 = Integer.MAX_VALUE;
            kdist1 = Integer.MAX_VALUE;
            kdist2 = Integer.MAX_VALUE;

            edges = new ArrayList<>();
        }

        public boolean isEmpty(){
            return value == GameStateManager.Tile.EMPTY;
        }

        public int getIndex() {return index;}

        public GameStateManager.Tile getValue() {
            return value;
        }

        public void setValue(GameStateManager.Tile value) {
            this.value = value;
        }

        public int getQdist1() {
            return qdist1;
        }

        public void setQdist1(int qdist1) {
            this.qdist1 = qdist1;
        }

        public int getQdist2() {
            return qdist2;
        }

        public void setQdist2(int qdist2) {
            this.qdist2 = qdist2;
        }

        public int getKdist1() {
            return kdist1;
        }

        public void setKdist1(int kdist1) {
            this.kdist1 = kdist1;
        }

        public int getKdist2() {
            return kdist2;
        }

        public void setKdist2(int kdist2) {
            this.kdist2 = kdist2;
        }

        public List<Edge> getEdges() {
            return edges;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Node n && n.index == index;
        }

    }



    public static void main(String[] args) {

        int[][] testBoard = {
                {0, 3, 0, 0, 1},
                {0, 3, 0, 0, 0},
                {0, 3, 0, 0, 0},
                {0, 3, 0, 3, 0},
                {0, 0, 0, 2, 0}
        };

        Graph g = new Graph(testBoard);
        //System.out.println(g + "\n");

        int node = 0;


        System.out.println("King Distance from node " + node + " to White (1): " + g.nodes.get(node).getKdist1());
        System.out.println("King Distance from node " + node + " to Black (2): " + g.nodes.get(node).getKdist2());
        System.out.println("-------------------------------");
        System.out.println("Queen Distance from node " + node + " to White (1): " + g.nodes.get(node).getQdist1());
        System.out.println("Queen Distance from node " + node + " to Black (2): " + g.nodes.get(node).getQdist2());

    }

}
