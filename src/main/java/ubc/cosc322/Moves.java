package ubc.cosc322;

import ygraph.ai.smartfox.games.GameStateManager;

import java.util.*;

public class Moves {

    /**
     * Generates all the possible moves for a given player and
     * returns them as a list of graphs with each new
     * potential board state
     * @param g
     * @param player
     * @return A list of graphs corresponding to each possible move
     */
    public static Map<Move, Graph> allMoves(Graph g, GameStateManager.Tile player){
        Map<Move, Graph> moveMap = new HashMap<>();

        List<Graph.Node> playerNodes = getPlayerNodes(g, player);

        for (Graph.Node current: playerNodes) {
            //Check all possible moves in each direction
            for(Graph.Edge.Direction nextDir : Graph.Edge.Direction.values()){

                Graph.Edge next = current.getEdgeInDirection(nextDir);

                while(next!=null){

                    //Add state where player shot where they moved from
                    Graph moveStateArrowAtCurrentIndex = Graph.copy(g);
                    Move moveArrowAtCurrentIndex = new Move(current.getIndex(), next.getNode().getIndex(), current.getIndex());
                    moveStateArrowAtCurrentIndex.updateGraph(moveArrowAtCurrentIndex, player);

                    moveMap.put(moveArrowAtCurrentIndex, moveStateArrowAtCurrentIndex);

                    //Check all possible arrow shows in each direction
                    for(Graph.Edge.Direction arrowDir : Graph.Edge.Direction.values()){

                        Graph.Edge arrow = next.getNode().getEdgeInDirection(arrowDir);

                        while(arrow!=null){

                            //Create a new board state
                            Graph moveState = Graph.copy(g);
                            Move move = new Move(current.getIndex(), next.getNode().getIndex(), arrow.getNode().getIndex());
                            moveState.updateGraph(move, player);

                            moveMap.put(move, moveState);

                            arrow = arrow.getNode().getEdgeInDirection(arrowDir);
                        }
                    }
                    next = next.getNode().getEdgeInDirection(nextDir);
                }

            }
        }
        
        return moveMap;
    }

    /**
     * Finds all nodes in a graph corresponding to a specified player
     * and returns them as a list of nodes.
     * @param g
     * @param player
     * @return List of player nodes
     */
    private static List<Graph.Node> getPlayerNodes(Graph g, GameStateManager.Tile player){
        List<Graph.Node> playerNodes = new LinkedList<>();
        //Determine all possible moves for each player
        for(Graph.Node n : g.getNodes()){
            if(n.getValue() == player)
                playerNodes.add(n);
        }

        return playerNodes;
    }

    public record Move(int currentIndex, int nextIndex, int arrowIndex){

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();

            ArrayList<Integer> curr = GameStateManager.indexToArrayList(currentIndex);
            ArrayList<Integer> next = GameStateManager.indexToArrayList(nextIndex);
            ArrayList<Integer> arrow = GameStateManager.indexToArrayList(arrowIndex);

            sb.append("QCurr: [").append(curr.get(0)).append(", ").append(toLetter(curr.get(1))).append("]\n");
            sb.append("QNext: [").append(next.get(0)).append(", ").append(toLetter(next.get(1))).append("]\n");
            sb.append("Arrow: [").append(arrow.get(0)).append(", ").append(toLetter(arrow.get(1))).append("]\n");

            return sb.toString();
        }

        private char toLetter(int x){
            return (char) (x + 'a' - 1);
        }

    }

}
