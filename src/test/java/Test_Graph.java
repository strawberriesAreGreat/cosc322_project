import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ubc.cosc322.Graph;
import ubc.cosc322.Moves;
import ygraph.ai.smartfox.games.GameStateManager;

class Test_Graph {

    private static final int[][] board = {
            {0, 3, 3, 0, 0},
            {3, 1, 3, 3, 2},
            {3, 0, 0, 0, 0},
            {3, 3, 0, 1, 3},
            {0, 2, 3, 0, 0}
    };

    private Graph g;

    @BeforeEach
    void setup(){
        g = new Graph(board);
    }

    @Test
    void test_copy(){
        int[][] copyTestBoard = {
                {0, 3, 0},
                {3, 1, 1}
        };

        Graph original = new Graph(copyTestBoard);
        Graph deepCopy = Graph.copy(original);
        
        for(Graph.Node n : original.getNodes()){
            Graph.Node nCopy = deepCopy.getNodes().get(n.getIndex());

            Assertions.assertEquals(n.getIndex(), nCopy.getIndex());
            Assertions.assertEquals(n.getValue(), nCopy.getValue());
            Assertions.assertEquals(n.getKdist1(), nCopy.getKdist1());
            Assertions.assertEquals(n.getKdist2(), nCopy.getKdist2());
            Assertions.assertEquals(n.getQdist1(), nCopy.getQdist1());
            Assertions.assertEquals(n.getQdist2(), nCopy.getQdist2());

            Assertions.assertEquals(n.getEdges().size(), nCopy.getEdges().size());

            for(int i = 0; i < n.getEdges().size(); i++){
                Graph.Edge e = n.getEdges().get(i);
                Graph.Edge eCopy = nCopy.getEdges().get(i);

                Assertions.assertEquals(e.getNode().getIndex(), eCopy.getNode().getIndex());
                Assertions.assertEquals(e.getDirection(), eCopy.getDirection());
                Assertions.assertEquals(e.isEnabled(), eCopy.isEnabled());
            }

        }

    }

    @Test
    void test_equals(){
        Graph equal = new Graph(board);

        int[][] unequalBoard = {
                {0, 3, 3, 0, 0},
                {3, 0, 3, 3, 2},
                {3, 0, 1, 0, 0},
                {3, 3, 0, 1, 3},
                {0, 2, 3, 0, 0}
        };

        Graph notEqual = new Graph(unequalBoard);

        Assertions.assertEquals(g, equal);
        Assertions.assertNotEquals(g, notEqual);
    }

    @Test
    void test_createGraph(){

        int expectedSize = 25;
        Assertions.assertEquals(expectedSize, g.getNodes().size());

        Graph.Node node0 = g.getNodes().get(0);
        Graph.Node node12 = g.getNodes().get(12);

        int expectedEdgesNode0 = 3;
        int expectedEdgesNode12 = 8;
        Assertions.assertEquals(expectedEdgesNode0, node0.getEdges().size());
        Assertions.assertEquals(expectedEdgesNode12, node12.getEdges().size());

        int expectedEnabledNode0 = 0;
        int expectedEnabledNode12 = 3;

        Assertions.assertEquals(expectedEnabledNode0, getEnabledNodeCount(node0));
        Assertions.assertEquals(expectedEnabledNode12, getEnabledNodeCount(node12));
    }

    @Test
    void test_updateGraph(){
        g.updateGraph(new Moves.Move(6, 12, 14), GameStateManager.Tile.WHITE);

        Graph.Node currentNode = g.getNodes().get(6);
        Graph.Node nextNode = g.getNodes().get(12);
        Graph.Node arrowNode = g.getNodes().get(14);

        GameStateManager.Tile expectedCurrentValue = GameStateManager.Tile.EMPTY;
        GameStateManager.Tile expectedNextValue = GameStateManager.Tile.WHITE;
        GameStateManager.Tile expectedArrowValue = GameStateManager.Tile.FIRE;

        Assertions.assertEquals(expectedCurrentValue, currentNode.getValue());
        Assertions.assertEquals(expectedNextValue, nextNode.getValue());
        Assertions.assertEquals(expectedArrowValue, arrowNode.getValue());

        Graph.Node node = g.getNodes().get(13);
        int expectedEnabled = 1;
        Assertions.assertEquals(expectedEnabled, getEnabledNodeCount(node));

    }

    @Test
    void test_updateGraph_InvalidPlayer() {
        g.updateGraph(new Moves.Move(6, 12, 14), GameStateManager.Tile.FIRE);

        Graph.Node currentNode = g.getNodes().get(6);
        Graph.Node nextNode = g.getNodes().get(12);
        Graph.Node arrowNode = g.getNodes().get(14);

        GameStateManager.Tile expectedCurrentValue = GameStateManager.Tile.WHITE;
        GameStateManager.Tile expectedNextValue = GameStateManager.Tile.EMPTY;
        GameStateManager.Tile expectedArrowValue = GameStateManager.Tile.EMPTY;

        Assertions.assertEquals(expectedCurrentValue, currentNode.getValue());
        Assertions.assertEquals(expectedNextValue, nextNode.getValue());
        Assertions.assertEquals(expectedArrowValue, arrowNode.getValue());

        Graph.Node node = g.getNodes().get(13);
        int expectedEnabled = 3;
        Assertions.assertEquals(expectedEnabled, getEnabledNodeCount(node));

    }

    private int getEnabledNodeCount(Graph.Node node){
        int count = 0;

        for(Graph.Edge e: node.getEdges())
            if(e.isEnabled()) count++;

        return count;
    }

    @Test
    void test_getEdgeInDirection(){
        Graph.Node node = g.getNodes().get(12);

        Graph.Edge edgeNorth = node.getEdgeInDirection(Graph.Edge.Direction.NORTH);
        Graph.Edge edgeNorthEast = node.getEdgeInDirection(Graph.Edge.Direction.NORTH_EAST);
        Graph.Edge edgeEast = node.getEdgeInDirection(Graph.Edge.Direction.EAST);
        Graph.Edge edgeSouthEast = node.getEdgeInDirection(Graph.Edge.Direction.SOUTH_EAST);
        Graph.Edge edgeSouth = node.getEdgeInDirection(Graph.Edge.Direction.SOUTH);
        Graph.Edge edgeSouthWest = node.getEdgeInDirection(Graph.Edge.Direction.SOUTH_WEST);
        Graph.Edge edgeWest = node.getEdgeInDirection(Graph.Edge.Direction.WEST);
        Graph.Edge edgeNorthWest = node.getEdgeInDirection(Graph.Edge.Direction.NORTH_WEST);

        //getEdgeInDirection returns null for an edge pointing to a non-empty tile
        Assertions.assertNull(edgeNorth);
        Assertions.assertNull(edgeNorthEast);
        Assertions.assertNull(edgeSouthEast);
        Assertions.assertNull(edgeSouthWest);
        Assertions.assertNull(edgeNorthWest);

        GameStateManager.Tile expectedEastValue = GameStateManager.Tile.EMPTY;
        GameStateManager.Tile expectedSouthValue = GameStateManager.Tile.EMPTY;
        GameStateManager.Tile expectedWestValue = GameStateManager.Tile.EMPTY;

        Assertions.assertEquals(expectedEastValue, edgeEast.getNode().getValue());
        Assertions.assertEquals(expectedSouthValue, edgeSouth.getNode().getValue());
        Assertions.assertEquals(expectedWestValue, edgeWest.getNode().getValue());

    }

}
