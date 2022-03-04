package ubc.cosc322;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
                if(v.getValue() == Graph.WHITE && !dist1) {
                    start.setKdist1(dist);
                    start.setQdist1(0);
                    dist1 = true;
                }
                else if(v.getValue() == Graph.BLACK && !dist2) {
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

}
