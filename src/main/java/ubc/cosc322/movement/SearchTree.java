package ubc.cosc322.movement;


import ubc.cosc322.GameStateManager;
import ubc.cosc322.movement.heuristics.Heuristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SearchTree {




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
    public static MinimaxMove AlphaBeta(Map<Moves.Move, Graph> miniMap, Graph N, int D, float alpha, float beta, GameStateManager.Tile player, Moves.Move lastMove) {
       
    	float V;
    	
    	if (D == 0 ) {
          float hval = Heuristic.calculateT(N, player);
          return new MinimaxMove(lastMove, hval);
        }

        Moves.Move bestMove = null;
        Map<Moves.Move, Graph> movesMap;
        if(miniMap == null) {
        	 movesMap = Moves.allMoves(N, player);
        }else {
        	movesMap = miniMap;
        }
        
        if (player.isWhite()) {
        	
        	V = Integer.MIN_VALUE;
        
            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
            	
            	
                Moves.Move currentMove = entry.getKey();
                float chosenHeuristic = AlphaBeta(null, entry.getValue(), D - 1, alpha, beta, GameStateManager.Tile.BLACK, currentMove).heuristic;

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
                float chosenHeuristic = AlphaBeta(null, entry.getValue(), D - 1, alpha, beta, GameStateManager.Tile.WHITE, entry.getKey()).heuristic;
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

    
    public static Moves.Move performAlphaBeta(Graph node, GameStateManager.Tile player, int depth) {
    	  	
    	MinimaxMove chosenOne = AlphaBetaT(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player, null);
        System.out.println(chosenOne.move);
    	return chosenOne.move;
   
    }

 public static MinimaxMove AlphaBetaT(Graph N, int D, float alpha, float beta, GameStateManager.Tile player, Moves.Move lastMove) {
	 if (D == 0 ) {
       float hval = Heuristic.calculateT(N, player);
       return new MinimaxMove(lastMove, hval);
     }
     int threads = 8;
        Moves.Move bestMove = null;
        Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);       
        HashMap<Moves.Move, Graph>[] subMaps = new HashMap[threads];
        MinMaxthread[] threadArray = new MinMaxthread[threads];
        List<Future> allFutures = new ArrayList<Future>();
        MinMaxthread tResult = null;

        
        float V = 0;         
        if (player.isWhite()) 	{ V = Integer.MIN_VALUE; } 
        else 					{ V = Integer.MAX_VALUE; }


        
        int i = 0; 
        for(Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()){
        	
        	if(subMaps[i] == null) {
        		subMaps[i] = new HashMap<>();
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
	
	        	Future<Object> future = service.submit(new MinMaxthread(subMaps[x],N,D,alpha, beta, player,null));
	        	allFutures.add(future);
	        
	    }
	        
	        
	   for(int t = 0; t < threads; t++) {
		   
		   Future<MinMaxthread> future = allFutures.get(t);
		   try {
			   tResult = future.get();
		   }catch(Exception e) {
       			e.printStackTrace();
       	   }
        
		   float chosenHeuristic = tResult.heuristic;
		   
       
		   if (player.isWhite() && V < chosenHeuristic ) {
               
                	V = chosenHeuristic;
                    bestMove = tResult.bestMove;
                     
		   }else if(V > chosenHeuristic){

                	V = chosenHeuristic;
                    bestMove = tResult.bestMove;
                    
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
	GameStateManager.Tile player;
	Moves.Move lastMove;
	Moves.Move bestMove;
	float heuristic;
	Map<Moves.Move, Graph> subMap;


	MinMaxthread(Map<Moves.Move, Graph> miniMap, Graph N, int D, float alpha, float beta, GameStateManager.Tile player, Moves.Move lastMove ){

		this.N = N;
		this.D = D;
		this.alpha = alpha;
		this.beta = beta; 
		this.player = player;
		this.lastMove = lastMove; 
		this.subMap = miniMap;
       
	}


	public Object call() throws Exception {
		 SearchTree.MinimaxMove bestSubMove = SearchTree.AlphaBeta(subMap, N,D,alpha,beta, player ,null);

	       this.heuristic = bestSubMove.heuristic();
	       this.bestMove = bestSubMove.move();
		return this;
		
				
	}
	
	
}