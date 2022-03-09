package ygraph.ai.smartfox.games;

import ubc.cosc322.Graph;
import ubc.cosc322.Moves;
import ubc.cosc322.heuristics.Heuristic;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

import java.util.*;


public class GameStateManager{

	//IMPORTANT
	//Moves are presented as 2 element Integer ArrayLists
	//The first int representing the Y value from bottom to top
	//The second int representing the X value from right to left
	//[10, 7] corresponds to [10, G] on the game board GUI.
	//[5, 3] corresponds to [5, C] etc...

	public enum Tile {
		EMPTY(0),
		WHITE(1),
		BLACK(2),
		FIRE(3);

		public final int id;
		private static final Map<Integer, Tile> map = new HashMap<>();

		Tile(int id) {
			this.id = id;
		}

		static {
			for (Tile tile : Tile.values()) {
				map.put(tile.id, tile);
			}
		}

		public static Tile valueOf(int id){
			return map.get(id);
		}

		public boolean isEmpty(){
			return this == EMPTY;
		}

		public boolean isPlayer(){
			return isWhite() || isBlack();
		}

		public boolean isWhite(){
			return this == WHITE;
		}

		public boolean isBlack(){
			return this == BLACK;
		}

		public boolean isFire(){
			return this == FIRE;
		}

	}

	private static final int ROW_LENGTH = 10;
	private static final int[][] INITIAL_BOARD_STATE = {

			{0, 0, 0, 2, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
	};

	public static int[][] getInitialBoardState(){
		return INITIAL_BOARD_STATE;
	}

	private Tile player;
	public void setPlayer(Tile p){
		if(p.isPlayer()){
			player = p;
		}
	}

	private Graph currentState;
	private Map<Moves.Move, Graph> movesMap;

	public GameStateManager(){
		currentState = new Graph(INITIAL_BOARD_STATE);
		movesMap = new HashMap<>();

		//Create minimax tree from initial board state

	}

	public static void timer() {
		// already a class for this
		// creates new instance of timer and sets a time limit
		int timeLimit = 20000;
		Timer time = new Timer();
		
		
		// timer that should make the move in 20 seconds
		time.schedule(new TimerTask() {
			@Override
			public void run() {
				// make moves during this time (call MakeMove method)
			}
			
		}, timeLimit);
		
		// cancels the timer once finished
		time.cancel();	
	}

	// Takes the message Details that are stored in the array and retrieves the position of:
	// Current X and Y of Queen
	public int getQueenCurrentIndex (Map<String, Object> msgDetails) {
		ArrayList<Integer> current = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		return (ROW_LENGTH - current.get(0)) * ROW_LENGTH + (current.get(1)-1);
	}

	public int getQueenNextIndex (Map<String, Object> msgDetails) {
		ArrayList<Integer> next = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		return (ROW_LENGTH - next.get(0)) * ROW_LENGTH + (next.get(1)-1);
	}

	public int getArrowIndex (Map<String, Object> msgDetails) {
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		return (ROW_LENGTH - arrow.get(0)) * ROW_LENGTH + (arrow.get(1)-1);
	}

	private ArrayList<Integer> indexToArrayList(int index){
		ArrayList<Integer> list = new ArrayList<>(2);
		//Y Position
		list.add((int) Math.ceil(ROW_LENGTH - ((float) index/ROW_LENGTH)));
		//X Position
		list.add((index % ROW_LENGTH) + 1);
		return list;
	}


	public void opponentMove(Map<String, Object> msgDetails){
		int currentIndex = getQueenCurrentIndex(msgDetails);
		int nextIndex = getQueenNextIndex(msgDetails);
		int arrowIndex = getArrowIndex(msgDetails);

		//
		if(player.isWhite())
			currentState.updateGraph(new Moves.Move(currentIndex, nextIndex, arrowIndex), Tile.BLACK);
		else
			currentState.updateGraph(new Moves.Move(currentIndex, nextIndex, arrowIndex), Tile.WHITE);
	}

	public Map<String, Object> makeMove() throws NullPointerException {
		movesMap = Moves.allMoves(currentState, player);

		float bestHeuristic = 0;
		Moves.Move bestMove = null;

		for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
			float h = Heuristic.calculateT(entry.getValue(), player);

			if ((player.isWhite() && h > bestHeuristic) || (player.isBlack() || h < bestHeuristic)) {
				bestHeuristic = h;
				bestMove = entry.getKey();
			}
		}

		if(bestMove == null) throw new NullPointerException();

		Map<String, Object> msgDetails = new HashMap<>();
		msgDetails.put(AmazonsGameMessage.QUEEN_POS_CURR, indexToArrayList(bestMove.currentIndex()));
		msgDetails.put(AmazonsGameMessage.Queen_POS_NEXT, indexToArrayList(bestMove.nextIndex()));
		msgDetails.put(AmazonsGameMessage.ARROW_POS, indexToArrayList(bestMove.arrowIndex()));

		currentState = movesMap.get(bestMove);

		return msgDetails;
	}


}
