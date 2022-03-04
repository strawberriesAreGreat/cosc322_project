package ubc.cosc322;

import ygraph.ai.smartfox.games.GameStateManager;

public class Heuristic {

    public static float calculateT(Graph board, GameStateManager.Tile turn){
        float w = calculateW(board);

        float f1 = function1(w);
        float f2 = function2(w);
        float f3 = function3(w);
        float f4 = function4(w);

        double magnitude = Math.sqrt(f1*f1 + f2*f2 + f3*f3 + f4*f4);

        float t1 = 0;
        float t2 = 0;

        for(Graph.Node n : board.getNodes()){
                if(!n.getValue().isEmpty()) continue;
                t1 += calculateTi(turn, n.getQdist1(), n.getQdist2());
                t2 += calculateTi(turn, n.getKdist1(), n.getKdist2());
        }

        float c1 = calculateC1(board);
        float c2 = calculateC2(board);

        double p1 = (f1/magnitude) * t1;
        double p2 = (f2/magnitude) * c1;
        double p3 = (f3/magnitude) * c2;
        double p4 = (f4/magnitude) * t2;

        System.out.println("t1: " + t1 + ", t2: " + t2 + ", c1: " + c1 + ", c2: " + c2 + ", w: " + w + ", magnitude: " + magnitude);

        return (float) (p1 + p2 + p3 + p4);
    }

    private static float calculateTi(GameStateManager.Tile player, int dist1, int dist2){
        float k = 1/5f;

        //n = m = infinity
        if(dist1 == Integer.MAX_VALUE && dist2 == Integer.MAX_VALUE) return 0;
        //n = m < infinity
        else if(dist1 == dist2) return player == GameStateManager.Tile.WHITE ? k : -k;
        //n < m (player 1 is closer)
        else if(dist1 < dist2) return 1;
        //n > m (player 2 is closer)
        else return -1;
    }

    private static float calculateC1(Graph board){
        float sum = 0;
        for (Graph.Node n : board.getNodes()) {
            sum += Math.pow(2, -n.getQdist1()) - Math.pow(2, -n.getQdist2());
        }

        return 2 * sum;
    }

    private static float calculateC2(Graph board){
        float sum = 0;
        for (Graph.Node n : board.getNodes()) {
            float difference = (n.getKdist2() - n.getKdist1()) / 6f;
            sum += Math.min(1, Math.max(-1, difference));
        }

        return sum;
    }

    private static float calculateW(Graph board){
        float sum = 0;
        for (Graph.Node n: board.getNodes()) {
            sum += Math.pow(2, -Math.abs(n.getQdist1()-n.getQdist2()));
        }
        return sum;
    }

    /*TODO Functions need implementation
        Should fluctuate in importance over the course of the game
    */

    //Increasingly important during the game
    //Gives good estimates of expected territory shortly before filling phase
    private static float function1(float w){
        return w;
    }

    //Supports positional play in the opening
    //Smooths transition between the beginning and later phases of the game
    private static float function2(float w){
        return w;
    }

    //Supports positional play in the opening
    //Smooths transition between the beginning and later phases of the game
    private static float function3(float w){
        return w;
    }

    //Most important at the beginning of the game
    private static float function4 (float w){
        return w;
    }

    public static void main(String[] args) {
        Graph g = new Graph(GameStateManager.INITIAL_BOARD_STATE);

        int n = 1000;
        float avg = 0;
        GameStateManager.Tile player = GameStateManager.Tile.WHITE;

        for(int x = 0; x < n; x++) {
            float t = calculateT(g, player);
            player = player == GameStateManager.Tile.WHITE ? GameStateManager.Tile.BLACK : GameStateManager.Tile.WHITE;

            avg += t;
        }

        avg = avg/n;

        System.out.println("AVG T: " + avg);


    }

}
