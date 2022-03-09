import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ubc.cosc322.Graph;
import ubc.cosc322.Moves;
import ygraph.ai.smartfox.games.GameStateManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        move1_arrow1.updateGraph(new Moves.Move(0, 1, 0), GameStateManager.Tile.WHITE);

        Graph move1_arrow2 = Graph.copy(initialGraph);
        move1_arrow2.updateGraph(new Moves.Move(0, 1, 3), GameStateManager.Tile.WHITE);

        Graph move1_arrow3 = Graph.copy(initialGraph);
        move1_arrow3.updateGraph(new Moves.Move(0, 1, 5), GameStateManager.Tile.WHITE);

        Graph move2_arrow1 = Graph.copy(initialGraph);
        move2_arrow1.updateGraph(new Moves.Move(0, 3, 0), GameStateManager.Tile.WHITE);

        Graph move2_arrow2 = Graph.copy(initialGraph);
        move2_arrow2.updateGraph(new Moves.Move(0, 3, 1), GameStateManager.Tile.WHITE);

        Graph move2_arrow3 = Graph.copy(initialGraph);
        move2_arrow3.updateGraph(new Moves.Move(0, 3, 6), GameStateManager.Tile.WHITE);

        Graph move3_arrow1 = Graph.copy(initialGraph);
        move3_arrow1.updateGraph(new Moves.Move(0, 6, 0), GameStateManager.Tile.WHITE);

        Graph move3_arrow2 = Graph.copy(initialGraph);
        move3_arrow2.updateGraph(new Moves.Move(0, 6, 3), GameStateManager.Tile.WHITE);

        Map<Moves.Move, Graph> moveMap = Moves.allMoves(initialGraph, GameStateManager.Tile.WHITE);

        Assertions.assertEquals(8, moveMap.size());
        Assertions.assertTrue(moveMap.containsValue(move1_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move1_arrow2));
        Assertions.assertTrue(moveMap.containsValue(move1_arrow3));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow2));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow3));
        Assertions.assertTrue(moveMap.containsValue(move3_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move3_arrow2));

    }

    @Test
    void test_allMoves_Black(){
        Graph move1_arrow1 = Graph.copy(initialGraph);
        move1_arrow1.updateGraph(new Moves.Move(2, 1, 3), GameStateManager.Tile.BLACK);

        Graph move1_arrow2 = Graph.copy(initialGraph);
        move1_arrow2.updateGraph(new Moves.Move(2, 1, 2), GameStateManager.Tile.BLACK);

        Graph move1_arrow3 = Graph.copy(initialGraph);
        move1_arrow3.updateGraph(new Moves.Move(2, 1, 5), GameStateManager.Tile.BLACK);

        Graph move2_arrow1 = Graph.copy(initialGraph);
        move2_arrow1.updateGraph(new Moves.Move(2, 5, 1), GameStateManager.Tile.BLACK);

        Graph move2_arrow2 = Graph.copy(initialGraph);
        move2_arrow2.updateGraph(new Moves.Move(2, 5, 2), GameStateManager.Tile.BLACK);

        Graph move2_arrow3 = Graph.copy(initialGraph);
        move2_arrow3.updateGraph(new Moves.Move(2, 5, 8), GameStateManager.Tile.BLACK);

        Graph move3_arrow1 = Graph.copy(initialGraph);
        move3_arrow1.updateGraph(new Moves.Move(2, 8, 2), GameStateManager.Tile.BLACK);

        Graph move3_arrow2 = Graph.copy(initialGraph);
        move3_arrow2.updateGraph(new Moves.Move(2, 8, 5), GameStateManager.Tile.BLACK);

        Map<Moves.Move, Graph> moveMap = Moves.allMoves(initialGraph, GameStateManager.Tile.BLACK);
        
        //Assertions.assertEquals(8, moveMap.size());
        Assertions.assertTrue(moveMap.containsValue(move1_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move1_arrow2));
        Assertions.assertTrue(moveMap.containsValue(move1_arrow3));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow2));
        Assertions.assertTrue(moveMap.containsValue(move2_arrow3));
        Assertions.assertTrue(moveMap.containsValue(move3_arrow1));
        Assertions.assertTrue(moveMap.containsValue(move3_arrow2));

    }

}
