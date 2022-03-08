package ubc.cosc322;

import ygraph.ai.smartfox.games.GameStateManager;

import java.util.*;

public class Distance {

    public static void setDistances(Graph.Node start)
    {
        if(!start.isEmpty()) return;

        Queue<QueueNode> q = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        q.add(new QueueNode(start, 0));

        //Keep track of if the distances for white and black have been set
        boolean dist1 = false;
        boolean dist2 = false;

        while(!dist1 || !dist2){

            //If we've iterated through all the reachable nodes
            //and haven't set distances yet then set them to infinity and return
            if(q.isEmpty()){
                if(!dist1) {
                    start.setKdist1(Integer.MAX_VALUE);
                    start.setQdist1(Integer.MAX_VALUE);
                }
                if(!dist2) {
                    start.setKdist2(Integer.MAX_VALUE);
                    start.setQdist2(Integer.MAX_VALUE);
                }
                return;
            }

            QueueNode u = q.poll();
            int dist = u.dist + 1;

            for(Graph.Edge e : u.node.getEdges()){
                Graph.Node v = e.getNode();
                int index = v.getIndex();

                //If the node is the player we want return the distance
                if(v.getValue() == GameStateManager.Tile.WHITE && !dist1) {
                    start.setKdist1(dist);
                    start.setQdist1(0);
                    dist1 = true;
                }
                else if(v.getValue() == GameStateManager.Tile.BLACK && !dist2) {
                    start.setKdist2(dist);
                    start.setQdist2(0);
                    dist2 = true;
                }
                //Add the node to the queue if it hasn't been visited & is empty
                else if(!visited.contains(index) && v.isEmpty()){
                    QueueNode qNode = new QueueNode(v, dist);
                    if(!q.contains(qNode)) q.add(qNode);
                }

            }

            visited.add(u.node.getIndex());
        }
    }

    private record QueueNode(Graph.Node node, int dist) {

        @Override
        public boolean equals(Object o) {
            return o instanceof QueueNode q && q.node.equals(node);
        }

    }

    private record DistNode(Graph.Node node, GameStateManager.Tile relatedPlayer){

        @Override
        public boolean equals(Object o) { return o instanceof DistNode d && d.node.equals(node); }

    }


    public static void allDistances(Graph g, GameStateManager.Tile player){

        List<DistNode> searchList = new LinkedList<>();
        Set<Graph.Node> unvisited = new HashSet<>();

        //Get all nodes with a player on them
        for(Graph.Node n : g.getNodes()){
            //Skip fire nodes && unreachable nodes
            GameStateManager.Tile v = n.getValue();
            if(v.isFire()) continue;

            if(!v.isPlayer())
                unvisited.add(n);


            //Add player nodes to list
            if(n.getValue() == player) {
                n.setDistances(n.getValue());
                searchList.add(new DistNode(n, n.getValue()));
            }
        }

        int qDist = 1;
        while(!unvisited.isEmpty()){
            searchList = allDistancesHelper(searchList, qDist++, unvisited);
            if (searchList.isEmpty()) break;
        }

    }

    private static List<DistNode> allDistancesHelper(List<DistNode> nodes, int qDist, Set<Graph.Node> unvisited){

        List<DistNode> returnList = new LinkedList<>();

        for(DistNode start : nodes) {

            Graph.Node startNode = start.node;

            GameStateManager.Tile startNodeValue = startNode.getValue();
            unvisited.remove(startNode);


            for (Graph.Edge.Direction direction : Graph.Edge.Direction.values()) {
                Graph.Edge current = startNode.getEdgeInDirection(direction);
                int kDist = 1;
                if(start.relatedPlayer.isWhite())
                    kDist = startNode.getKdist1();
                else if(start.relatedPlayer.isBlack())
                    kDist = startNode.getKdist2();

                while(current!=null){
                    Graph.Node currentNode = current.getNode();
                    if(start.relatedPlayer().isWhite()){
                        if(currentNode.getQdist1() > qDist) currentNode.setQdist1(qDist);
                        if(currentNode.getKdist1() > kDist) currentNode.setKdist1(++kDist);
                    }else if(start.relatedPlayer().isBlack()){
                        if(currentNode.getQdist2() > qDist) currentNode.setQdist2(qDist);
                        if(currentNode.getKdist2() > kDist) currentNode.setKdist2(++kDist);
                    }

                    if(unvisited.remove(currentNode))
                        returnList.add(new DistNode(currentNode, start.relatedPlayer));


                    current = currentNode.getEdgeInDirection(direction);
                }


            }
        }

        return returnList;
    }

}
