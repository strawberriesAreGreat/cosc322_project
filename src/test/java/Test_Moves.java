import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ubc.cosc322.movement.Graph;
import ubc.cosc322.movement.Moves;
import ubc.cosc322.GameStateManager;

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
    void test_allMoves_EdgeCase(){
        int[][] edgeState = {
                {0, 0, 0, 3, 0},
                {0, 3, 3, 2, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 2, 0},
                {1, 0, 3, 3, 0}
        };

        Graph graph = new Graph(edgeState);

        Moves.Move validMoveWhite1 = new Moves.Move(11, 14, 12);
        Moves.Move validMoveWhite2 = new Moves.Move(11, 14, 11);
        Moves.Move validMoveBlack1 = new Moves.Move(18, 17, 19);
        Moves.Move validMoveBlack2 = new Moves.Move(18, 17, 18);

        Map<Moves.Move, Graph> moveMapWhite = Moves.allMoves(graph, GameStateManager.Tile.WHITE);
        Map<Moves.Move, Graph> moveMapBlack = Moves.allMoves(graph, GameStateManager.Tile.BLACK);

        Assertions.assertTrue(moveMapWhite.containsKey(validMoveWhite1));
        Assertions.assertTrue(moveMapWhite.containsKey(validMoveWhite2));
        Assertions.assertTrue(moveMapBlack.containsKey(validMoveBlack1));
        Assertions.assertTrue(moveMapBlack.containsKey(validMoveBlack2));
    }


    @Test
    void test_allMoves_invalidMoves(){

        int [][] board = {
                {1, 0, 0, 3, 0, 0},
                {0, 3, 3, 3, 0, 0}
        };

        Graph graph = new Graph(board);

        Moves.Move validMove1 = new Moves.Move(0, 2, 1);

        //Move into fire
        Moves.Move invalidMove1 = new Moves.Move(0, 3, 1);

        //Move over fire
        Moves.Move invalidMove2 = new Moves.Move(0, 4, 5);

        //Shoot over fire
        Moves.Move invalidMove3 = new Moves.Move(0, 2, 5);

        Map<Moves.Move, Graph> moveMap = Moves.allMoves(graph, GameStateManager.Tile.WHITE);
        Assertions.assertEquals(7, moveMap.size());
        Assertions.assertTrue(moveMap.containsKey(validMove1));
        Assertions.assertFalse(moveMap.containsKey(invalidMove1));
        Assertions.assertFalse(moveMap.containsKey(invalidMove2));
        Assertions.assertFalse(moveMap.containsKey(invalidMove3));

    }

    @Test
    void test_allMoves_White(){

        Moves.Move move1_arrow1 = new Moves.Move(0, 1, 0);
        Moves.Move move1_arrow2 = new Moves.Move(0, 1, 3);
        Moves.Move move1_arrow3 = new Moves.Move(0, 1, 5);
        Moves.Move move2_arrow1 = new Moves.Move(0, 3, 0);
        Moves.Move move2_arrow2 = new Moves.Move(0, 3, 1);
        Moves.Move move2_arrow3 = new Moves.Move(0, 3, 6);
        Moves.Move move3_arrow1 = new Moves.Move(0, 6, 0);
        Moves.Move move3_arrow2 = new Moves.Move(0, 6, 3);

        Map<Moves.Move, Graph> moveMap = Moves.allMoves(initialGraph, GameStateManager.Tile.WHITE);

        Assertions.assertEquals(8, moveMap.size());
        Assertions.assertTrue(moveMap.containsKey(move1_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move1_arrow2));
        Assertions.assertTrue(moveMap.containsKey(move1_arrow3));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow2));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow3));
        Assertions.assertTrue(moveMap.containsKey(move3_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move3_arrow2));

    }

    @Test
    void test_allMoves_Black(){

        Moves.Move move1_arrow1 = new Moves.Move(2, 1, 3);
        Moves.Move move1_arrow2 = new Moves.Move(2, 1, 2);
        Moves.Move move1_arrow3 = new Moves.Move(2, 1, 5);
        Moves.Move move2_arrow1 = new Moves.Move(2, 5, 1);
        Moves.Move move2_arrow2 = new Moves.Move(2, 5, 2);
        Moves.Move move2_arrow3 = new Moves.Move(2, 5, 8);
        Moves.Move move3_arrow1 = new Moves.Move(2, 8, 2);
        Moves.Move move3_arrow2 = new Moves.Move(2, 8, 5);

        Map<Moves.Move, Graph> moveMap = Moves.allMoves(initialGraph, GameStateManager.Tile.BLACK);
        
        Assertions.assertEquals(8, moveMap.size());
        Assertions.assertTrue(moveMap.containsKey(move1_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move1_arrow2));
        Assertions.assertTrue(moveMap.containsKey(move1_arrow3));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow2));
        Assertions.assertTrue(moveMap.containsKey(move2_arrow3));
        Assertions.assertTrue(moveMap.containsKey(move3_arrow1));
        Assertions.assertTrue(moveMap.containsKey(move3_arrow2));

    }

}
