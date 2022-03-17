package ubc.cosc322;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public SearchTree() {
 
    }


    /**
     * AlphaBeta: performs AlphaBeta search on a Node
     * @param N: the root Node to be evaluated
     * @param D: the depth we are evaluating at
     * @param alpha: an Integer storing negative infinity, we attempt to maximize this in the function
     * @param beta: an Integer storing positive infinity, we attempt to minimize this in the function
     * @param maxPlayer: boolean indicating whether or not we're attempting to maximize alpha
     * @return: an int representing the weighting of the move
     */
//    private float AlphaBeta(Graph N, int D, float alpha, float beta, Tile player) {
//        if (D == 0 ) {
//        	
//          float hval = Heuristic.calculateT(N, player);
//          N.setHeuristicValue(hval);
//          return hval;
//        	
//        	
//        }
//    	
//        if (player.isWhite()) {
//        	
//            float V = Integer.MIN_VALUE;
//            Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
//            
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//                V = Math.max(V, AlphaBeta(entry.getValue(), D - 1, alpha, beta, Tile.BLACK));
//                alpha = Math.max(alpha, V);
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//            N.setHeuristicValue(V);
//            return V;
//
//        } else {
//        	
//        	float V = Integer.MAX_VALUE;
//            Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//                V = Math.min(V, AlphaBeta(entry.getValue(), D - 1, alpha, beta, Tile.WHITE));
//                beta = Math.min(beta, V);
//                if (beta <= alpha)
//                    break;
//            }
//            N.setHeuristicValue(V);
//            return V;
//        }
//    }
//    
//    
    private float AlphaBeta(SearchTreeNode node, int D, float alpha, float beta, Tile player) {
        if (D == 0 ) {
        	
          float hval = Heuristic.calculateT(node.graph, player);
          node.graph.setHeuristicValue(hval);
          return hval;
        	
        	
        }
    	
        if (player.isWhite()) {
        	
            float V = Integer.MIN_VALUE;
//            Map<Moves.Move, Graph> movesMap = Moves.allMoves(N, player);
            
            
            for (SearchTreeNode child : node.children) {
                V = Math.max(V, AlphaBeta(child, D - 1, alpha, beta, Tile.BLACK));
                alpha = Math.max(alpha, V);
                if (beta <= alpha) {
                    break;
                }
            }
            node.graph.setHeuristicValue(V);
            return V;

        } else {
        	
        	float V = Integer.MAX_VALUE;
 
            for (SearchTreeNode child : node.children) {
                V = Math.min(V, AlphaBeta(child, D - 1, alpha, beta, Tile.WHITE));
                beta = Math.min(beta, V);
                if (beta <= alpha)
                    break;
            }
            node.graph.setHeuristicValue(V);
            return V;
        }
    }
    
    
    private Moves.Move getMoveAfterAlphaBeta(SearchTreeNode root) {
        Moves.Move bestMove = null;
    	
//    	if (root.player.isWhite()) {
//            float max = Integer.MIN_VALUE;
//
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//                if (max < entry.getValue().getHeuristicValue()) {
//                    max = entry.getValue().getHeuristicValue();
//                    bestMove = entry.getKey();
//                }
//            }
//            System.out.print("best move:" +  bestMove.toString());
//            return bestMove;
//    	} else {
//            float min = Integer.MAX_VALUE;
//            for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
//            	
//                if (min > entry.getValue().getHeuristicValue()) {
//                	min = entry.getValue().getHeuristicValue();
//                    bestMove = entry.getKey();
//                }
//            }
////            System.out.print("best move:" +  bestMove.toString());
//            return bestMove;
//    	}

    	
    	if (root.player.isWhite()) {
            float max = Integer.MIN_VALUE;

            for (SearchTreeNode node : root.children) {
                if (max < node.graph.getHeuristicValue()) {
                    max = node.graph.getHeuristicValue();
                    bestMove = node.move;
                }
            }
            System.out.print("best move:" +  bestMove.toString());
            return bestMove;
    	} else {
            float min = Integer.MAX_VALUE;
            for (SearchTreeNode node : root.children) {
            	
                if (min > node.graph.getHeuristicValue()) {
                    min = node.graph.getHeuristicValue();
                    bestMove = node.move;
                }
            }
//            System.out.print("best move:" +  bestMove.toString());
            return bestMove;
    	}
    	
    	
    } // end of getMoveAfterAlphaBeta

    
    public Moves.Move performAlphaBeta(SearchTreeNode root) {

//        AlphaBeta(node, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, player);
//        Moves.Move move = getMoveAfterAlphaBeta(node,player,movesMap);
//        return move;
    	AlphaBeta(root, root.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, root.player);
    	Moves.Move move = getMoveAfterAlphaBeta(root);
    	return move;
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



} // end of SearchTree