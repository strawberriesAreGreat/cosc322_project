import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ubc.cosc322.Graph;
import ubc.cosc322.Moves;
import ygraph.ai.smartfox.games.GameStateManager;

import java.util.List;

class Test_Moves {

    private final int[][] initialState = {
            {1, 0, 2},
            {0, 3, 0},
            {0, 3, 0}
    };
    
    private Graph initialGraph;

    @BeforeEach
    void setup(){
       initialGraph = new Graph(initialState);
    }

    @Test
    void test_allMoves_White(){

        Graph move1_arrow1 = Graph.copy(initialGraph);
        move1_arrow1.updateGraph(0, 1, 0, GameStateManager.Tile.WHITE);

        Graph move1_arrow2 = Graph.copy(initialGraph);
        move1_arrow2.updateGraph(0, 1, 3, GameStateManager.Tile.WHITE);

        Graph move1_arrow3 = Graph.copy(initialGraph);
        move1_arrow3.updateGraph(0, 1, 5, GameStateManager.Tile.WHITE);

        Graph move2_arrow1 = Graph.copy(initialGraph);
        move2_arrow1.updateGraph(0, 3, 0, GameStateManager.Tile.WHITE);

        Graph move2_arrow2 = Graph.copy(initialGraph);
        move2_arrow2.updateGraph(0, 3, 1, GameStateManager.Tile.WHITE);

        Graph move2_arrow3 = Graph.copy(initialGraph);
        move2_arrow3.updateGraph(0, 3, 6, GameStateManager.Tile.WHITE);

        Graph move3_arrow1 = Graph.copy(initialGraph);
        move3_arrow1.updateGraph(0, 6, 0, GameStateManager.Tile.WHITE);

        Graph move3_arrow2 = Graph.copy(initialGraph);
        move3_arrow2.updateGraph(0, 6, 3, GameStateManager.Tile.WHITE);

        List<Graph> moveList = Moves.allMoves(initialGraph, GameStateManager.Tile.WHITE);

        Assertions.assertEquals(8, moveList.size());
        Assertions.assertTrue(moveList.contains(move1_arrow1));
        Assertions.assertTrue(moveList.contains(move1_arrow2));
        Assertions.assertTrue(moveList.contains(move1_arrow3));
        Assertions.assertTrue(moveList.contains(move2_arrow1));
        Assertions.assertTrue(moveList.contains(move2_arrow2));
        Assertions.assertTrue(moveList.contains(move2_arrow3));
        Assertions.assertTrue(moveList.contains(move3_arrow1));
        Assertions.assertTrue(moveList.contains(move3_arrow2));

    }

    @Test
    void test_allMoves_Black(){
        Graph move1_arrow1 = Graph.copy(initialGraph);
        move1_arrow1.updateGraph(2, 1, 3, GameStateManager.Tile.BLACK);

        Graph move1_arrow2 = Graph.copy(initialGraph);
        move1_arrow2.updateGraph(2, 1, 2, GameStateManager.Tile.BLACK);

        Graph move1_arrow3 = Graph.copy(initialGraph);
        move1_arrow3.updateGraph(2, 1, 5, GameStateManager.Tile.BLACK);

        Graph move2_arrow1 = Graph.copy(initialGraph);
        move2_arrow1.updateGraph(2, 5, 1, GameStateManager.Tile.BLACK);

        Graph move2_arrow2 = Graph.copy(initialGraph);
        move2_arrow2.updateGraph(2, 5, 2, GameStateManager.Tile.BLACK);

        Graph move2_arrow3 = Graph.copy(initialGraph);
        move2_arrow3.updateGraph(2, 5, 8, GameStateManager.Tile.BLACK);

        Graph move3_arrow1 = Graph.copy(initialGraph);
        move3_arrow1.updateGraph(2, 8, 2, GameStateManager.Tile.BLACK);

        Graph move3_arrow2 = Graph.copy(initialGraph);
        move3_arrow2.updateGraph(2, 8, 5, GameStateManager.Tile.BLACK);

        List<Graph> moveList = Moves.allMoves(initialGraph, GameStateManager.Tile.BLACK);

        System.out.println(moveList.get(0));
        System.out.println(move1_arrow1);

        Assertions.assertEquals(8, moveList.size());
        Assertions.assertTrue(moveList.contains(move1_arrow1));
        Assertions.assertTrue(moveList.contains(move1_arrow2));
        Assertions.assertTrue(moveList.contains(move1_arrow3));
        Assertions.assertTrue(moveList.contains(move2_arrow1));
        Assertions.assertTrue(moveList.contains(move2_arrow2));
        Assertions.assertTrue(moveList.contains(move2_arrow3));
        Assertions.assertTrue(moveList.contains(move3_arrow1));
        Assertions.assertTrue(moveList.contains(move3_arrow2));

    }

}
