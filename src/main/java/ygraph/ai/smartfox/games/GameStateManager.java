package ygraph.ai.smartfox.games;

import java.util.*;


public class GameStateManager{

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


	public GameStateManager(){
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

	// takes the message Details that are stored in the array and retrieves the position of:
	// Current X and Y of Queen
	public int getQueenXCurrent (Map<String, Object> msgDetails) {
		ArrayList<Integer> queenpos = (ArrayList<Integer>) msgDetails.get("queen-position-current");

		return queenpos.get(0);
	}
	
	public int getQueenYCurrent (Map<String, Object> msgDetails) {
		ArrayList<Integer> queenpos = (ArrayList<Integer>) msgDetails.get("queen-position-current");
		return queenpos.get(1);
	}
	// ---------------------
	
	// Current X and Y of Player Black
	public int getPlayerBlackX (Map<String, Object> msgDetails) {
		ArrayList<Integer> black = (ArrayList<Integer>) msgDetails.get("player-black");

		return black.get(0);
	}
	
	public int getPlayerBlackY (Map<String, Object> msgDetails) {
		ArrayList<Integer> black = (ArrayList<Integer>) msgDetails.get("player-black");
		return black.get(1);
	}
	
	// ---------------------
	
	// Current X and Y of Player white
	public int getPlayerWhiteX (Map<String, Object> msgDetails) {
		ArrayList<Integer> white = (ArrayList<Integer>) msgDetails.get("player-white");
		return white.get(0);
	}
	
	public int getPlayerWhiteY (Map<String, Object> msgDetails) {
		ArrayList<Integer> white = (ArrayList<Integer>) msgDetails.get("player-white");
		return white.get(1);
	}
	
	// ---------------------
	
	// Current X and Y of Arrow
	public int getArrowPosX (Map<String, Object> msgDetails) {
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get("arrow-position");
		return arrow.get(0);
	}
	
	public int getArrowPosY (Map<String, Object> msgDetails) {
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get("arrow-position");
		return arrow.get(1);
	}

	// ---------------------
	
	
	// Makes a Move and then updates the GUI
	public void MakeMove (Map<String, Object> msgDetails, BaseGameGUI gameGui) {
		//gets the string representation of the play
		String move = GameMessage.GAME_ACTION_MOVE;
		
		// TODO: should add the new position here if the coordinate is free
		
		GameStateManager.updateGui(msgDetails, gameGui);
	}
	
	// Updates the GUI
	public static void updateGui (Map<String, Object> msgDetails, BaseGameGUI gameGui) {
		gameGui.updateGameState(msgDetails);
	}
	
	// Checks for an open position
	public boolean openPos (int x, int y) {
		ArrayList<Integer> coor = new ArrayList<>();
		coor.add(x);
		coor.add(y);
		
		// TODO: check if this coordinate is occupied
		
		return false;
	}
	

}
