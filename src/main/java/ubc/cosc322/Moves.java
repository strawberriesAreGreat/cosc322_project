package ubc.cosc322;

import ygraph.ai.smartfox.games.GameStateManager;

import java.util.LinkedList;
import java.util.List;

public class Moves {

    public static List<Graph> allMoves(Graph g, GameStateManager.Tile player){
        List<Graph> moveList = new LinkedList<>();

        List<Graph.Node> playerNodes = getPlayerNodes(g, player);

        for (Graph.Node current: playerNodes) {
            //Check all possible moves in each direction
            for(Graph.Edge.Direction nextDir : Graph.Edge.Direction.values()){

                Graph.Edge next = current.getEdgeInDirection(nextDir);

                while(next!=null){

                    //Add state where player shot where they moved from
                    Graph moveStateArrowAtCurrentIndex = Graph.copy(g);
                    moveStateArrowAtCurrentIndex.updateGraph(current.getIndex(), next.getNode().getIndex(), current.getIndex(), player);
                    moveList.add(moveStateArrowAtCurrentIndex);

                    //Check all possible arrow shows in each direction
                    for(Graph.Edge.Direction arrowDir : Graph.Edge.Direction.values()){

                        Graph.Edge arrow = next.getNode().getEdgeInDirection(arrowDir);

                        while(arrow!=null){

                            //Create a new board state
                            Graph moveState = Graph.copy(g);
                            moveState.updateGraph(current.getIndex(), next.getNode().getIndex(), arrow.getNode().getIndex(), player);
                            moveList.add(moveState);

                            arrow = arrow.getNode().getEdgeInDirection(arrowDir);
                        }
                    }
                    next = next.getNode().getEdgeInDirection(nextDir);
                }

            }
        }
        
        return moveList;
    }

    private static List<Graph.Node> getPlayerNodes(Graph g, GameStateManager.Tile player){
        List<Graph.Node> playerNodes = new LinkedList<>();
        //Determine all possible moves for each player
        for(Graph.Node n : g.getNodes()){
            if(n.getValue() == player)
                playerNodes.add(n);
        }

        return playerNodes;
    }



}
