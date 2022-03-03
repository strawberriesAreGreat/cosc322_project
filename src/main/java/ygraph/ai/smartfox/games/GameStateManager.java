package ygraph.ai.smartfox.games;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ygraph.ai.smartfox.games.BaseGameGUI;

public class GameStateManager{

	public static int[][] INITIAL_BOARD_STATE = {
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


	public static void timer() { // already a class for this
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
		ArrayList<Integer> queenpos = new ArrayList<>();
		queenpos = (ArrayList<Integer>) msgDetails.get("queen-position-current");
		
		int x = queenpos.get(0);
		
		return x;
	}
	
	public int getQueenYCurrent (Map<String, Object> msgDetails) {
		ArrayList<Integer> queenpos = new ArrayList<>();
		queenpos = (ArrayList<Integer>) msgDetails.get("queen-position-current");
		
		int y = queenpos.get(1);
		
		return y;
	}
	// ---------------------
	
	// Current X and Y of Player Black
	public int getPlayerBlackX (Map<String, Object> msgDetails) {
		ArrayList<Integer> black = new ArrayList<>();
		black = (ArrayList<Integer>) msgDetails.get("player-black");
		
		int x = black.get(0);
		
		return x;
	}
	
	public int getPlayerBlackY (Map<String, Object> msgDetails) {
		ArrayList<Integer> black = new ArrayList<>();
		black = (ArrayList<Integer>) msgDetails.get("player-black");
		
		int y = black.get(1);
		
		return y;
	}
	
	// ---------------------
	
	// Current X and Y of Player white
	public int getPlayerWhiteX (Map<String, Object> msgDetails) {
		ArrayList<Integer> white = new ArrayList<>();
		white = (ArrayList<Integer>) msgDetails.get("player-white");
		
		int x = white.get(0);
		
		return x;
	}
	
	public int getPlayerWhiteY (Map<String, Object> msgDetails) {
		ArrayList<Integer> white = new ArrayList<>();
		white = (ArrayList<Integer>) msgDetails.get("player-white");
		
		int y = white.get(1);
		
		return y;
	}
	
	// ---------------------
	
	// Current X and Y of Arrow
	public int getArrowPosX (Map<String, Object> msgDetails) {
		ArrayList<Integer> arrow = new ArrayList<>();
		arrow = (ArrayList<Integer>) msgDetails.get("arrow-position");
		
		int x = arrow.get(0);
		
		return x;
	}
	
	public int getArrowPosY (Map<String, Object> msgDetails) {
		ArrayList<Integer> arrow = new ArrayList<>();
		arrow = (ArrayList<Integer>) msgDetails.get("arrow-position");
		
		int y = arrow.get(1);
		
		return y;
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
