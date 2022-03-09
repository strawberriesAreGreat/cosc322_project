import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ubc.cosc322.Graph;

class Test_Distance {

    @Test
    void test_allDistances(){

        int[][] board = {
                {1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        Graph.Node node = getBoardNode(board, 24);

        testNodeDistances(node, 4, 1, 3, 2);

    }

    @Test
    void test_allDistances_UnreachableNode(){
        int[][] board = {
                {1, 0, 0, 3, 0},
                {0, 0, 0, 3, 0},
                {0, 0, 0, 3, 0},
                {3, 2, 3, 3, 0},
                {0, 0, 0, 0, 0}
        };

        Graph.Node node = getBoardNode(board, 20);

        testNodeDistances(node, Integer.MAX_VALUE, Integer.MAX_VALUE, 1, 1);

    }

    @Test
    void test_allDistances_Walls(){
        int[][] board = {
                {1, 0, 0, 3, 0},
                {3, 3, 0, 3, 3},
                {0, 0, 0, 3, 0},
                {3, 0, 3, 3, 0},
                {2, 0, 0, 0, 3}
        };

        Graph.Node node = getBoardNode(board, 14);

        testNodeDistances(node, 8, 7, 5, 3);

    }

    @Test
    void test_allDistances_SplitBoard(){
        int[][] board = {
                {1, 0, 3, 0, 0},
                {0, 0, 3, 0, 0},
                {0, 0, 3, 0, 0},
                {0, 0, 3, 0, 0},
                {0, 0, 3, 0, 2}
        };

        Graph.Node node1 = getBoardNode(board, 20);
        Graph.Node node2 = getBoardNode(board, 4);

        testNodeDistances(node1, 4, 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
        testNodeDistances(node2, Integer.MAX_VALUE, Integer.MAX_VALUE, 4, 1);
    }

    @Test
    void test_allDistances_MultiplePlayers(){
        int[][] board = {
                {1, 0, 3, 0, 0},
                {0, 3, 3, 0, 0},
                {0, 3, 1, 0, 0},
                {3, 0, 3, 3, 0},
                {2, 3, 3, 0, 2}
        };

        Graph.Node node1 = getBoardNode(board, 4);
        Graph.Node node2 = getBoardNode(board, 10);

        testNodeDistances(node1, 2, 1, 4, 1);
        testNodeDistances(node2, 2, 1, 2, 2);

    }

    @Test
    void test_allDistances_LargeBoard(){
        int[][] board = {
                {0, 3, 0, 0, 3, 0, 0, 0, 0, 0},
                {3, 1, 0, 0, 0, 0, 0, 3, 0, 0},
                {3, 0, 3, 0, 0, 3, 0, 0, 3, 0},
                {0, 0, 3, 0, 0, 0, 3, 1, 0, 0},
                {0, 3, 0, 3, 0, 3, 0, 0, 0, 3},
                {0, 3, 0, 3, 0, 3, 0, 0, 0, 3},
                {0, 0, 2, 0, 3, 3, 0, 3, 0, 0},
                {0, 3, 3, 0, 0, 0, 3, 3, 0, 0},
                {0, 0, 0, 3, 3, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 0, 2, 0, 3, 0}
        };

        Graph.Node node1 = getBoardNode(board, 0);
        Graph.Node node2 = getBoardNode(board, 52);
        Graph.Node node3 = getBoardNode(board, 88);
        Graph.Node node4 = getBoardNode(board, 99);

        testNodeDistances(node1, 1, 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
        testNodeDistances(node2, 4, 3, 1, 1);
        testNodeDistances(node3, 5, 2, 2, 2);
        testNodeDistances(node4, 6, 3, 3, 2);
    }

    private Graph.Node getBoardNode(int[][] board, int index){
        Graph g = new Graph(board);
        return g.getNodes().get(index);
    }


    private void testNodeDistances(Graph.Node node, int expectedKDist1, int expectedQDist1, int expectedKDist2, int expectedQDist2){

        int actualKDist1 = node.getKdist1();
        int actualQDist1 = node.getQdist1();
        int actualKDist2 = node.getKdist2();
        int actualQDist2 = node.getQdist2();

        Assertions.assertEquals(expectedKDist1, actualKDist1);
        Assertions.assertEquals(expectedQDist1, actualQDist1);
        Assertions.assertEquals(expectedKDist2, actualKDist2);
        Assertions.assertEquals(expectedQDist2, actualQDist2);
    }

}
