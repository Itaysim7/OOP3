package gameClient;

import javax.swing.JOptionPane;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.graph;
import utils.StdDraw;

/**
 * This class represents a simple example for using the GameServer API:
 * the main file performs the following tasks:
 * 1. Creates a game_service [0,23] (line 36)
 * 2. Constructs the graph from JSON String (lines 37-39)
 * 3. Gets the scenario JSON String (lines 40-41)
 * 4. Prints the fruits data (lines 49-50)
 * 5. Add a set of robots (line 52-53) // note: in general a list of robots should be added
 * 6. Starts game (line 57)
 * 7. Main loop (should be a thread) (lines 59-60)
 * 8. move the robot along the current edge (line 74)
 * 9. direct to the next edge (if on a node) (line 87-88)
 * 10. prints the game results (after "game over"): (line 63)
 *  
 * @author boaz.benmoshe
 *
 */
public class SimpleGameClient {
	public static void main(String[] a)
	{
			//////choose level////
			Object level[]=new Object[24];
			for(int i=0;i<level.length;i++)
				level[i]=i;
			int scenario=(Integer)JOptionPane.showInputDialog(null,"Choose a level between 0-23","Level", JOptionPane.QUESTION_MESSAGE,null,level,null);
			game_service game = Game_Server.getServer(scenario);
			StdDraw.setGameNumber(scenario);
			//////choose game type////
			Object type[]=new Object[2];type[0]="by mouse";type[1]="Automatic";
			String typegame=(String)JOptionPane.showInputDialog(null,"Choose type of game","type of game", JOptionPane.QUESTION_MESSAGE,null,type,null);
			//////create graph and algo graph////
			DGraph g=new DGraph();
			g.init(game.getGraph());
			MyGameGUI gui=new MyGameGUI(game,scenario,typegame,g);

		}

}
