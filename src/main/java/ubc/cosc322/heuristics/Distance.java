package ubc.cosc322.heuristics;

import ygraph.ai.smartfox.games.GameStateManager;

import java.util.*;

public class Distance {

    private record DistanceNode(Graph.Node node, GameStateManager.Tile relatedPlayer){

        @Override
        public boolean equals(Object o) { return o instanceof DistanceNode d && d.node.equals(node); }

    }

    public static void allDistances(Graph g, GameStateManager.Tile player){

        List<DistanceNode> searchList = new LinkedList<>();
        Set<Graph.Node> unvisited = new HashSet<>();

        //Get all nodes with a player on them
        for(Graph.Node n : g.getNodes()){
            //Skip fire nodes
            GameStateManager.Tile v = n.getValue();
            if(v.isFire()) continue;

            //Add all non-player nodes to unvisited set
            if(!v.isPlayer())
                unvisited.add(n);

            //Add player nodes to search list
            if(n.getValue() == player) {
                n.playerZeroDistances(n.getValue());
                searchList.add(new DistanceNode(n, n.getValue()));
            }
        }

        int qDist = 1;

        //While there are still unvisited notes search the board iteratively
        while(!unvisited.isEmpty()){
            searchList = allDistancesHelper(searchList, qDist++, unvisited);

            //If the returned search list is empty the remaining unvisited nodes must be unreachable
            if (searchList.isEmpty()) break;
        }

    }

    private static List<DistanceNode> allDistancesHelper(List<DistanceNode> nodes, int qDist, Set<Graph.Node> unvisited){

        //A list of all newly visited nodes this iteration
        List<DistanceNode> returnList = new LinkedList<>();

        for(DistanceNode start : nodes) {

            Graph.Node startNode = start.node;
            unvisited.remove(startNode);

            //Move in each direction 1 tile at a time
            for (Graph.Edge.Direction direction : Graph.Edge.Direction.values()) {

                Graph.Edge current = startNode.getEdgeInDirection(direction);

                //kDist is set to the starting nodes k distance from the player
                int kDist = 1;
                if(start.relatedPlayer.isWhite())
                    kDist = startNode.getKdist1();
                else if(start.relatedPlayer.isBlack())
                    kDist = startNode.getKdist2();

                //Keep moving until we hit a wall, fire tile or another player
                while(current!=null){
                    Graph.Node currentNode = current.getNode();

                    //Update distances
                    if(start.relatedPlayer().isWhite()){
                        if(currentNode.getQdist1() > qDist) currentNode.setQdist1(qDist);
                        if(currentNode.getKdist1() > kDist) currentNode.setKdist1(++kDist);
                    }else if(start.relatedPlayer().isBlack()){
                        if(currentNode.getQdist2() > qDist) currentNode.setQdist2(qDist);
                        if(currentNode.getKdist2() > kDist) currentNode.setKdist2(++kDist);
                    }

                    //Add the node to the return list if it was visited for the first time this iteration
                    if(unvisited.remove(currentNode))
                        returnList.add(new DistanceNode(currentNode, start.relatedPlayer));

                    //Move to the next tile
                    current = currentNode.getEdgeInDirection(direction);
                }


            }
        }

        return returnList;
    }

}
