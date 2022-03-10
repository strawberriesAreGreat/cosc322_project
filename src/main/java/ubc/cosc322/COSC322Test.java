
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;
import ygraph.ai.smartfox.games.*;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gameGui = null;
	private GameStateManager gameStateManager;

    private String userName = null;
    private String passwd = null;
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test bot = new COSC322Test(args[0], args[1]);
    	
    	if(bot.getGameGUI() == null) {
    		bot.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(bot::Go);
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gameGui = new BaseGameGUI(this);

		//Initialize GameStateManager
		this.gameStateManager = new GameStateManager();
    }
 


    @Override
    public void onLogin() {
		System.out.println("Congratualations!!! "
				+ "I am called because the server indicated that the login is successfully");
		System.out.println("The next step is to find a room and join it: "
				+ "the gameClient instance created in my constructor knows how!");

		userName = getGameClient().getUserName();
		if(gameGui != null){
			gameGui.setRoomInformation(gameClient.getRoomList());
		}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document.

		switch (messageType) {
			case GameMessage.GAME_STATE_BOARD -> gameGui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			case GameMessage.GAME_ACTION_MOVE ->  {
				//Update our GUI to reflect opponents move
				gameGui.updateGameState(msgDetails);

				//Update GameStateManager board state
				gameStateManager.opponentMove(msgDetails);

				//Make our move
				try {
					Map<String, Object> moveDetails = gameStateManager.makeMove();
					gameGui.updateGameState(moveDetails);
					gameClient.sendMoveMessage(moveDetails);
				} catch (InterruptedException e){
					e.printStackTrace();
					return false;
				}
			}
			case GameMessage.GAME_ACTION_START -> {

				//Make a move if the bot starts as white
				String white = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
				if(white.equalsIgnoreCase(userName)){
					gameStateManager.setPlayer(GameStateManager.Tile.WHITE);

					//Make our move
					try {
						Map<String, Object> moveDetails = gameStateManager.makeMove();

						gameGui.updateGameState(moveDetails);
						gameClient.sendMoveMessage(moveDetails);
					} catch (InterruptedException e){
						e.printStackTrace();
						return false;
					}

				} else {
					gameStateManager.setPlayer(GameStateManager.Tile.BLACK);
				}
			}
			default -> System.out.println("Unhandled message type.");
		}

		System.out.println("MSG Type:" + messageType);
		System.out.println("MSG Details: " + msgDetails);
    	    	
    	return true;
    }
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return gameGui;
	}

	@Override
	public void connect() {
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
