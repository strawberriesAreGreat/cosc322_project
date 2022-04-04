import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ubc.cosc322.GameStateManager;
import ubc.cosc322.movement.Graph;
import ubc.cosc322.movement.heuristics.Heuristic;

class Test_Heuristic {

    private static final GameStateManager.Tile WHITE = GameStateManager.Tile.WHITE;
    private static final GameStateManager.Tile BLACK = GameStateManager.Tile.BLACK;

    @Test
    void test_calculateT_EqualBoard(){

        int[][] board = {
                {0, 0, 0},
                {1, 3, 2},
                {3, 3, 3}
        };

        Graph g = new Graph(board);

        float tWhite = Heuristic.calculateT(g, WHITE);
        float tBlack = Heuristic.calculateT(g, BLACK);

        Assertions.assertTrue(tWhite > 0);
        Assertions.assertTrue(tBlack < 0);
    }

    @Test
    void test_calculateT_WhiteFavoured(){
        int[][] board = {
                {0, 0, 0},
                {1, 0, 3},
                {0, 3, 2}
        };

        Graph g = new Graph(board);

        float tWhite = Math.abs(Heuristic.calculateT(g, WHITE));
        float tBlack = Math.abs(Heuristic.calculateT(g, BLACK));

        Assertions.assertTrue(tWhite > tBlack);
    }

    @Test
    void test_calculateT_BlackFavoured(){
        int[][] board = {
                {0, 0, 0},
                {2, 0, 3},
                {0, 3, 1}
        };

        Graph g = new Graph(board);

        float tWhite = Math.abs(Heuristic.calculateT(g, WHITE));
        float tBlack = Math.abs(Heuristic.calculateT(g, BLACK));

        Assertions.assertTrue(tWhite < tBlack);
    }

    @Test
    void test_calculateT_EqualBigBoard(){
        int[][] board = GameStateManager.getInitialBoardState();

        Graph g = new Graph(board);

        float tWhite = Heuristic.calculateT(g, WHITE);
        float tBlack = Heuristic.calculateT(g, BLACK);

        Assertions.assertTrue(tWhite > 0);
        Assertions.assertTrue(tBlack < 0);
    }

}
