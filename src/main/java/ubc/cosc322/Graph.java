package ubc.cosc322;

import ubc.cosc322.heuristics.Distance;
import ygraph.ai.smartfox.games.GameStateManager;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    /**
     * Creates an identical deep copy of the source graph
     * @param source
     * @return a copy of the source graph
     */
    public static Graph copy(Graph source){
        Graph copy = new Graph(source.nodes.size());
        for (Node n: source.getNodes()) {
            copy.nodes.add(Node.copy(n));
        }


        for (Node n: source.getNodes()) {
            int index = n.getIndex();

            for(Edge e: n.edges){
                int otherIndex = e.other.getIndex();

                Node copyNode = copy.nodes.get(index);
                copyNode.edges.add(Edge.copy(e, copy.nodes.get(otherIndex)));
            }

        }

        return copy;
    }

    private final List<Node> nodes;

    private Graph(int size){
        nodes = new ArrayList<>(size);
    }

    public Graph(int[][] board) {
        int rowLength = board.length;

        nodes = new ArrayList<>(rowLength * board[0].length);

        initializeGraph(board);

    }

    /**
     * Takes a player's move information and updates the graph accordingly.
     * @param currIndex The node index the player moved from
     * @param nextIndex The node index the player moved to
     * @param arrowIndex The node index the player fired an arrow at
     * @param player
     */
    public void updateGraph(int currIndex, int nextIndex, int arrowIndex, GameStateManager.Tile player){

        if(!player.isPlayer()) return;

        //Set current to empty
        Node currNode = nodes.get(currIndex);
        currNode.setValue(GameStateManager.Tile.EMPTY);

        //Enable edges for all connected nodes
        toggleConnectedNodeEdges(currNode, true);

        //Set next to player
        Node nextNode = nodes.get(nextIndex);
        nextNode.setValue(player);

        //Disable edges for all connected nodes
        toggleConnectedNodeEdges(nextNode, false);

        //Set arrow to arrow
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
                    addEdge(node, northNode, Edge.Direction.NORTH, northNode.isEmpty());
                }

                //Connect northeast node
                if(i - 1 >= 0 && j + 1 < board[0].length){
                    int northEast = index - board.length + 1;
                    Node northEastNode = nodes.get(northEast);
                    addEdge(node, northEastNode, Edge.Direction.NORTH_EAST, northEastNode.isEmpty());
                }

                //Connect east node
                if(j + 1 < board[0].length){
                    int east = index + 1;
                    Node eastNode = nodes.get(east);
                    addEdge(node, eastNode, Edge.Direction.EAST, eastNode.isEmpty());
                }

                //Connect southeast node
                if(i + 1 < board.length && j + 1 < board[0].length){
                    int southEast = index + board.length + 1;
                    Node southEastNode = nodes.get(southEast);
                    addEdge(node, southEastNode, Edge.Direction.SOUTH_EAST, southEastNode.isEmpty());
                }

                //Connect south node
                if(i + 1 < board.length){
                    int south = index + board.length;
                    Node southNode = nodes.get(south);
                    addEdge(node, southNode, Edge.Direction.SOUTH, southNode.isEmpty());
                }

                //Connect southwest node
                if(i + 1 < board.length && j - 1 >= 0){
                    int southWest = index + board.length - 1;
                    Node southWestNode = nodes.get(southWest);
                    addEdge(node, southWestNode, Edge.Direction.SOUTH_WEST, southWestNode.isEmpty());
                }

                //Connect west node
                if(j - 1 >= 0){
                    int west = index - 1;
                    Node westNode = nodes.get(west);
                    addEdge(node, westNode, Edge.Direction.WEST, westNode.isEmpty());
                }

                //Connect northwest node
                if(i - 1 >= 0 && j - 1 >= 0){
                    int northWest = index - board.length - 1;
                    Node northWestNode = nodes.get(northWest);
                    addEdge(node, northWestNode, Edge.Direction.NORTH_WEST, northWestNode.isEmpty());
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
        for(Node n : nodes) n.resetDistances();
        Distance.allDistances(this, GameStateManager.Tile.WHITE);
        Distance.allDistances(this, GameStateManager.Tile.BLACK);
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

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Graph)) return false;

        Graph g = (Graph) o;

        if(nodes.size() != g.nodes.size()) return false;

        for(int i = 0; i < nodes.size(); i++){
            if(!nodes.get(i).equals(g.getNodes().get(i))) return false;
        }

        return true;
    }

    public static class Edge {

        private static Edge copy(Edge source, Node otherCopy){
            return new Edge(otherCopy, source.direction, source.enabled);
        }

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

        public boolean isEnabled() { return enabled; }

    }

    public static class Node {

        private static Node copy(Node source){
            Node copy = new Node(source.index, source.value);
            copy.kdist1 = source.kdist1;
            copy.kdist2 = source.kdist2;
            copy.qdist1 = source.qdist1;
            copy.qdist2 = source.qdist2;

            return copy;
        }

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

            resetDistances();

            edges = new ArrayList<>();
        }

        /**
         * Sets all distances to Integer.MAX_VALUE
         */
        public void resetDistances(){
            qdist1 = Integer.MAX_VALUE;
            qdist2 = Integer.MAX_VALUE;
            kdist1 = Integer.MAX_VALUE;
            kdist2 = Integer.MAX_VALUE;
        }

        /**
         * Sets all distances to 0
         */
        public void zeroDistances(){
            qdist1 = 0;
            qdist2 = 0;
            kdist1 = 0;
            kdist2 = 0;
        }

        /**
         * Sets kDist and qDist to 0 for the given player
         * @param player
         */
        public void playerZeroDistances(GameStateManager.Tile player){
            if(player.isWhite()){
                qdist1 = 0;
                kdist1 = 0;
            }else if(player.isBlack()){
                qdist2 = 0;
                kdist2 = 0;
            }
        }

        /**
         * Returns an edge connected to the node in the given direction
         * @param dir
         * @return An edge in the specified direction,
         * or null if the edge is disabled or non-existent.
         */
        public Edge getEdgeInDirection(Edge.Direction dir){
            for(Edge e : edges){
                if (e.getDirection() == dir && e.enabled) return e;
            }
            return null;
        }

        public int getIndex(){ return index; }

        public boolean isEmpty(){
            return value.isEmpty();
        }

        public GameStateManager.Tile getValue() {
            return value;
        }

        public void setValue(GameStateManager.Tile value) {
            if(value.isFire()) zeroDistances();
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

        public List<Edge> getEdges(){
            return edges;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Node n && n.index == index && n.value == value;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
