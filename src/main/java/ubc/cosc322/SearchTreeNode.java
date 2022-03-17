package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;

import ubc.cosc322.Moves.Move;
import ygraph.ai.smartfox.games.GameStateManager.Tile;

public class SearchTreeNode {
	Graph graph;
	Moves.Move move;
	Tile player;
	ArrayList<SearchTreeNode> children = new ArrayList<>();
	int depth;
	
	public SearchTreeNode(Moves.Move move, Graph graph,Tile player,int depth) {
		this.graph = graph;
		this.move = move;
		this.depth = depth;
		this.player = player;
		if(depth > 0)
		{
			Map<Moves.Move, Graph> movesMap = Moves.allMoves(graph, player);
			for (Map.Entry<Moves.Move, Graph> entry : movesMap.entrySet()) {
				children.add(new SearchTreeNode(entry.getKey(), entry.getValue(), player, depth-1));
			}
			depth -=1;
		}


	} // end TreeNode constructor




	
}
