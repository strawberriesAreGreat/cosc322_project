package ubc.cosc322;

package local.gpu.aparapi.add;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ubc.cosc322.heuristics.Heuristic;
import ygraph.ai.smartfox.games.GameStateManager.Tile;

public class SearchTree {


    private int depth;
    private int numOfMoves;
    public int evaluation;
    private ArrayList<Node> frontier = new ArrayList<>();

    /**
     * SearchTree constructor
     * @param node: a SearchTreeNode to be added to the SearchTree
     */
    private SearchTree() {
 
    }

    public record MinimaxMove(Moves.Move move, float heuristic){}


    /**
     * AlphaBeta: performs AlphaBeta search on a Node
     * @param N: the root Node to be evaluated
     * @param D: the depth we are evaluating at
     * @param alpha: an Integer storing negative infinity, we attempt to maximize this in the function
     * @param beta: an Integer storing positive infinity, we attempt to minimize this in the function
     * @return: an int representing the weighting of the move
     */
    public static MinimaxMove AlphaBeta(Graph N, int D, float alpha, float beta, Tile player, Moves.Move lastMove) {
        if (D == 0 ) {
          float hval = Heuristic.calculateT(N, player);
          return new MinimaxMove(lastMove, hval);
        }

        float V;
        Moves.Move bestMove = null;
        Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
        
        
        
        if (player.isWhite()) {

            V = Integer.MIN_VALUE;

            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
                Moves.Move currentMove = entry.getKey();
                float chosenHeuristic = AlphaBeta(entry.getValue(), D - 1, alpha, beta, Tile.BLACK, currentMove).heuristic;
                if(V < chosenHeuristic){
                    V = chosenHeuristic;
                    bestMove = currentMove;
                }

                alpha = Math.max(alpha, V);
                if (beta <= alpha) {
                    break;
                }
            }

        } else {

            V = Integer.MAX_VALUE;

            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
                float chosenHeuristic = AlphaBeta(entry.getValue(), D - 1, alpha, beta, Tile.WHITE, entry.getKey()).heuristic;
                if(V > chosenHeuristic){
                    V = chosenHeuristic;
                    bestMove = entry.getKey();
                }

                beta = Math.max(alpha, V);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return new MinimaxMove(bestMove, V);
    }
    
    
    
//    private Moves.Move getMoveAfterAlphaBeta(Graph N,Tile player) {
//        Moves.Move bestMove = null;
//        Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
//
//    	if (player.isWhite()) {
//            int max = Integer.MIN_VALUE;
//
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//                if (max < entry.getValue().getHeuristicValue()) {
//                    max = (int) entry.getValue().getHeuristicValue();
//                    bestMove = entry.getKey();
//                }
//            }
//            System.out.print("best move white: " +  bestMove.toString() + " heuristic: " + max);
//            return bestMove;
//    	} else {
//            int min = Integer.MAX_VALUE;
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//                if (min > entry.getValue().getHeuristicValue()) {
//                	min = (int) entry.getValue().getHeuristicValue();
//                    bestMove = entry.getKey();
//                }
//            }
//            System.out.print("best move black: " +  bestMove.toString() + " heuristic: " + min);
//            return bestMove;
//    	}
//
//    } // end of getMoveAfterAlphaBeta

    
    public static Moves.Move performAlphaBeta(Graph node,Tile player, int depth) {
        
    	
    	
    	MinimaxMove chosenOne = AlphaBetaThreads(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player, null);
        
    	return chosenOne.move;
    }

   
    /**
     * expandFrontier: Depending on if the depth is even or odd, add the successor Nodes
     * If we have an even depth, then we know we are evaluating our nodes
     */
//    public void expandFrontier() {
//        ArrayList<Node> newFrontier = new ArrayList<>();
//        // See if we're at the very first root node
//        if (depth != 0) {
//            if (depth % 2 == 0) {
//                for (Node S : frontier)
//                    newFrontier.addAll(S.setSuccessors(true));
//            } else {
//                for (Node S : frontier)
//                    newFrontier.addAll(S.setSuccessors(false));
//            }
//        } else {
//            newFrontier.addAll(root.setSuccessors(true));
//        }
//
//        // Clear the old frontier, and add in the new SearchTreeNodes to use
//        frontier.clear();
//        for (Node S : newFrontier) {
//            // deepCopy in order to keep Object relationships
//        	Node newNode = new Node(S.gameRules.deepCopy());
//            frontier.add(newNode);
//        }
//        depth++;
//    }

    /**
     * performAlphaBeta: Calculates how far down into the SearchTree we will go,
     * and whether or not we're trying to maximize our move (we always are, so set as true)
     */

    /**
     * trimFrontier: performs AB pruning to reduce the number of Nodes evaluated
     */
//    public void trimFrontier() {
//        int avg = 0;
//        // Get the average value from all SearchTreeNodes in the frontier
//        for (Node S : frontier) {
////            S.setValue( h.calculateOurGoodness(S));
////            avg += S.getValue();
//        }
//        if (frontier.size() != 0)
//            avg = avg / frontier.size();
//
//        // If the nodes are below the average value, "cut" them
//        ArrayList<Node> toRemove = new ArrayList<>();
//        for (Node S : frontier) {
//            if (S.getValue() < avg) {
//                toRemove.add(S);
//            }
//        }
//
//        // "cuts" the SearchTreeNodes that were below the average value
//        for (Node S : toRemove) {
//            frontier.remove(S);
//        }
//    }

    /**
     * makeMoveOnRoot: Moves the queen from its initial position (root), to its new position
     * @param qCurrentPos: a Queen object containing the new best Queen move
     * @param a: an Arrow object that is associated with the best Queen move
     */
//    public void makeMoveOnRoot(Queen qCurrentPos, Arrow a) {
//        numOfMoves++;
//        // Adds an arrow to the board
//        root.gameRules.addArrow(a);
//
//        // Check whether or not the queen is the opponent's, or ours
//        if (qCurrentPos.isOpponent) {
//            for (Queen Q : root.gameRules.enemy) {
//                if (Q.row == qCurrentPos.previousRow && Q.col == qCurrentPos.previousCol) {
//                    Q.moveQueen(qCurrentPos.row, qCurrentPos.col);
//                }
//
//            }
//        } else {
//            for (Queen Q : root.gameRules.friend) {
//                if (Q.row == qCurrentPos.previousRow && Q.col == qCurrentPos.previousCol) {
//                    Q.moveQueen(qCurrentPos.row, qCurrentPos.col);
//                }
//            }
//        }
//        root.gameRules.updateAfterMove();
//        this.clearTree();
//    }

    /**
     * makeMove: performs expansion/trimming, alpha beta, and performs our move
     * @return: SearchTreeNode containing our best possible Queen move
     */
//    public Node makeMove() {
//        /* "Thresholding" based off the number of moves we have
//            in order to increase our likelyhood of winning
//         */
//        if(numOfMoves <= 20) {
//            this.expandFrontier();
//            this.trimFrontier();
//        }
//
//        else if(numOfMoves > 20 && numOfMoves <= 30) {
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//
//        }
//        else if(numOfMoves > 30 && numOfMoves <= 45) {
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//
//        }
//        else if(numOfMoves > 45 && numOfMoves <= 60) {
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//        }
//        else if(numOfMoves > 60) {
//            this.expandFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//            this.expandFrontier();
//            this.trimFrontier();
//
//        }
//
//        this.performAlphaBeta();
//        Node bestMove = this.getMoveAfterAlphaBeta();
//        this.makeMoveOnRoot(bestMove.getQueen(), bestMove.getArrowShot());
//        return bestMove;
//    } // end of makeMove

    
    private static  MinimaxMove AlphaBetaThreads(Graph N, int D, float alpha, float beta, Tile player, Moves.Move lastMove) {
        
    	if (D == 0 ) {
          float hval = Heuristic.calculateT(N, player);
          return new MinimaxMove(lastMove, hval);
        }

        float V;
        Moves.Move bestMove = null;
        Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
        
        int threads = 8;
        int i = 0; 
        
        HashMap<Moves.Move, Graph>[] subMaps = new HashMap[threads];
        MinMaxthread[] threadArray = new MinMaxthread[threads];
        List<Future> allFutures = new ArrayList<Future>();
        
        for(Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()){
        	
        	if(subMaps[i] == null) {
        		subMaps[i] = new HashMap<Moves.Move, Graph>();
        	}
        	
            subMaps[i].put(entry.getKey(), entry.getValue());
            if(i<threads-1) {
            i++;
            }else {
            	i = 0;
            }
        }
             
        ExecutorService service = Executors.newFixedThreadPool(threads);
        
        for(int x = 0; x < threads; x ++ ) {
        	System.out.println(" starting up thread " +x);

        	Future<Object> future = service.submit(new MinMaxthread(subMaps[i],N,D,alpha, beta,player,null));
        	allFutures.add(future);
        
        }
    	System.out.println("Started all futures");
        if (player.isWhite()) {

            V = Integer.MIN_VALUE;
        } else {

            V = Integer.MAX_VALUE;
        }
        
        // now block until all threads return heuristic calculations
        // for their subMovesMap
        for(int y = 0; y < threads; y++) {
        	System.out.println(" Getting " +y +"'s future");
        	Future<MinMaxthread> future = allFutures.get(y);
        	System.out.println(" Thread " +y + "  got future!");
        	try {
        		
        		threadArray[i] = future.get();
        		if (player.isWhite()) {
	        		if(V < threadArray[i].heuristic){
	                    V =  threadArray[i].heuristic;
	                    bestMove =  threadArray[i].bestMove;
	                }
	        	  	  alpha = Math.max(alpha, V);
	                  if (beta <= alpha) {
	                      break;
	                  }
        		}else {
        			if(V > threadArray[i].heuristic){
	                    V =  threadArray[i].heuristic;
	                    bestMove =  threadArray[i].bestMove;
	                }
        			  beta = Math.max(alpha, V);
                      if (beta <= alpha) {
                          break;
                      }
	        	}
      
        	}catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        service.shutdown();
        return new MinimaxMove(bestMove, V);
    }
   
    
} // end of SearchTree


class MinMaxthread implements Callable<Object>{
	
	SearchTree sTree;
	Graph N;
	int D; 
	Float alpha, beta;
	Tile player;
	Moves.Move lastMove;
	Moves.Move bestMove;
	float heuristic;


	MinMaxthread(Map<Moves.Move, Graph> miniMap, Graph N, int D, float alpha, float beta, Tile player, Moves.Move lastMove ){

		this.N = N;
		this.D = D;
		this.alpha = alpha;
		this.beta = beta; 
		this.player = player;
		this.lastMove = lastMove; 
       
	}


	public Object call() throws Exception {
		 System.out.println(" thread " + Thread.currentThread() + " has started ");
	       ubc.cosc322.SearchTree.MinimaxMove bestSubMove = SearchTree.AlphaBeta(N,D,alpha,beta,player,lastMove);
	      
	       this.heuristic = bestSubMove.heuristic();
	       this.bestMove = bestSubMove.move();
		 System.out.println(" thread " + Thread.currentThread() + " is finished ");
		return this;
		//.sTree.AlphaBeta(N, D, alpha, beta, player, null).heuristic();
				
	}
	
	
}