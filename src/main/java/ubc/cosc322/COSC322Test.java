
package ubc.cosc322;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.BoardGameModel;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.GameStateManager;
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
	
    private String userName = null;
    private String passwd = null;
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
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
    	Map<String, Object> detailsTest = new HashMap<String, Object>();

        ArrayList<Integer> curr = new ArrayList<>();
        curr.add(1);
        curr.add(4);

        ArrayList<Integer> next = new ArrayList<>();
        next.add(1);
        next.add(1);

        ArrayList<Integer> arrow = new ArrayList<>();
        arrow.add(10);
        arrow.add(10);

        detailsTest.put(AmazonsGameMessage.QUEEN_POS_CURR, curr);
        detailsTest.put(AmazonsGameMessage.QUEEN_POS_NEXT, next);
        detailsTest.put(AmazonsGameMessage.ARROW_POS, arrow);
        
        System.out.println(detailsTest.get("queen-position-current")); 
        this.gameClient.sendMoveMessage((ArrayList<Integer>) detailsTest.get("queen-position-current"),(ArrayList<Integer>) detailsTest.get("queen-position-next"), (ArrayList<Integer>) detailsTest.get("arrow-position"));
        gameGui.updateGameState(detailsTest);
        
		switch (messageType) {
			case GameMessage.GAME_STATE_BOARD -> gameGui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			case GameMessage.GAME_ACTION_MOVE ->  //gameGui.updateGameState(msgDetails);
													gameGui.updateGameState(detailsTest);
				
			
				default -> {
				System.out.println(messageType);
				System.out.println(detailsTest);
			}
		}
    	    	
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
