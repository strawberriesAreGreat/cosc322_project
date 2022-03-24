package ubc.cosc322.players;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

public class HumanTest {

    public static void main(String[] args) {
        GamePlayer human = new Team11Bot("r", "test");

        if(human.getGameGUI() == null) {
            human.Go();
        }
        else {
            BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(human::Go);
        }
    }


}
