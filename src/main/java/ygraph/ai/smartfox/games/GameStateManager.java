package ygraph.ai.smartfox.games;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class GameStateManager{

	// takes the message Details that are stored in the array and retrieves the position of:
	// Current X and Y of Queen
	// Should have mapping to queen_pos_curr, queen_pos_next, and arrow_pos
	public int getQueenXCurrent (Map<String, Object> msgDetails) {
		ArrayList<Integer> queenpos = new ArrayList<>();
		queenpos =(ArrayList<Integer>) msgDetails.get("queen-position-current");
		
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
	public void MakeMove (Map<String, Object> msgDetails, BaseGameGUI gameGui, GameClient gameClient, ArrayList<Integer> queencurrent, ArrayList<Integer> queennext, ArrayList<Integer> arrowposition) {
		//as long as we can make a new move...
		if (GameMessage.GAME_ACTION_MOVE.equals("cosc322.game-action.move")) {
			gameClient.sendMoveMessage(queencurrent, queennext, arrowposition); //send game info to server
			gameGui.updateGameState(msgDetails); //update gui
			
		}
		
	}

	

}
