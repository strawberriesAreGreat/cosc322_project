package ubc.cosc322;

import java.util.Random;

public class Heuristic {

    //TODO Move to appropriate class
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    private static Random heuristicRandom = new Random();

    public static float calculateT(Square[][] board, int turn){
        float w = W(board);

        float f1 = F1(w);
        float f2 = F2(w);
        float f3 = F3(w);
        float f4 = F4(w);

        double magnitude = Math.sqrt(f1*f1 + f2*f2 + f3*f3 + f4*f4);

        float t1 = 0;
        float t2 = 0;

        for(Square[] squares : board){
            for(Square s : squares){
                if(s.getValue() != Square.EMPTY) continue;
                t1 += calculateTi(turn, s.getQdist1(), s.getQdist2());
                t2 += calculateTi(turn, s.getKdist1(), s.getKdist2());
            }
        }

        float c1 = C1(board);
        float c2 = C2(board);

        double p1 = (f1/magnitude) * t1;
        double p2 = (f2/magnitude) * c1;
        double p3 = (f3/magnitude) * c2;
        double p4 = (f4/magnitude) * t2;

        return (float) (p1 + p2 + p3 + p4);
    }

    //TODO Needs implementation
    public static int qDist(){
        return 1+ heuristicRandom.nextInt(5);
    }

    //TODO Needs implementation
    public static int kDist(){
        return 1+ heuristicRandom.nextInt(10);
    }

    private static float calculateTi(int player, int dist1, int dist2){
        float k = 1/5f;

        //n = m = infinity
        if(dist1 == Integer.MAX_VALUE && dist2 == Integer.MAX_VALUE) return 0;
        //n = m < infinity
        else if(dist1 == dist2) return player == PLAYER_1 ? k : -k;
        //n < m (player 1 is closer)
        else if(dist1 < dist2) return 1;
        //n > m (player 2 is closer)
        else return -1;
    }

    private static float C1(Square[][] board){
        float sum = 0;
        for (Square[] squares : board) {
            for (Square s : squares) {
                sum += Math.pow(2, -s.getQdist1()) - Math.pow(2, -s.getQdist2());
            }
        }

        return 2 * sum;
    }

    private static float C2(Square[][] board){
        float sum = 0;
        for (Square[] squares : board) {
            for (Square s : squares) {
                float difference = (s.getKdist2() - s.getKdist1()) / 6f;
                sum += Math.min(1, Math.max(-1, difference));
            }
        }

        return sum;
    }

    private static float W(Square[][] board){
        float sum = 0;
        for (Square[] squares : board) {
            for (Square s : squares) {
                sum += Math.pow(2, -Math.abs(s.getQdist1()-s.getQdist2()));
            }
        }

        return sum;
    }

    /*TODO Functions need implementation
        Should fluctuate in importance over the course of the game
    */

    //Increasingly important during the game
    //Gives good estimates of expected territory shortly before filling phase
    private static float F1(float w){
        return w;
    }

    //Supports positional play in the opening
    //Smooths transition between the beginning and later phases of the game
    private static float F2(float w){
        return w;
    }

    //Supports positional play in the opening
    //Smooths transition between the beginning and later phases of the game
    private static float F3(float w){
        return w;
    }

    //Most important at the beginning of the game
    private static float F4(float w){
        return w;
    }

    public static void main(String[] args) {
        Square[][] board = new Square[3][3];

        int n = 1000;
        float avg = 0;
        int player = PLAYER_1;

        for(int x = 0; x < n; x++) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    Square s = new Square(Square.EMPTY);
                    s.setQdist1(qDist());
                    s.setQdist2(qDist());
                    s.setKdist1(kDist());
                    s.setKdist2(kDist());

                    board[i][j] = s;
                }
            }
            float t = calculateT(board, player);
            player = player == PLAYER_1 ? PLAYER_2 : PLAYER_1;

            avg += t;
        }

        avg = avg/n;

        System.out.println("AVG T: " + avg);


    }








}
