package gameClient;

import java.util.Iterator;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.node_data;
import obj.Fruit;
import obj.Pacman;
import utils.StdDraw;

/**
 * This class runs algorithms for moving robots in the graph
 * There are two algorithms : by mouse an by automatic thats moves the robot to the next close node
 * @author itay simhayev and lilach mor
 *
 */
public class ManualDrive 
{
	private game_service game;
	private DGraph g;
	private Graph_Algo ga;
	private List<Fruit> fruits;
	private List<Pacman> robots;
	private int destNode=-1;
	private int destByMouse;
	private Pacman move;

	public ManualDrive(game_service game1,List<Fruit> f,List<Pacman> p) 
	{
		this.game=game1;
		fruits=f;
		robots=p;
		g=new DGraph();
		g.init(game.getGraph());
		ga=new Graph_Algo();
		ga.init(g);
	}
	/**
	 * Update the game, fruit and pacman
	 * @param game1 - the update game
	 * @param f - the update list of fruits
	 * @param p - the update list of Pacman
	 */
	public  void update(game_service game1,List<Fruit> f,List<Pacman> p)
	{
		this.game=game1;
		fruits=f;
		robots=p;
	}
	
	/**
	 * The function move the robots by mouse 
	 * moves the closest robots to the node that pressed until the robots arrives to the node
	 */
	public  void moveRobotsbyMouse()
	{
		List<String> log = game.move();
		if(log!=null) 
		{
			long t = game.timeToEnd();
			if(StdDraw.isMousePressed())//check if there is new pressed
			{
				double x=StdDraw.mouseX();double y=StdDraw.mouseY();
				destNode=findNode(x,y);
			}
			if(destNode!=-1)//if the mouse pressed at least one time
			{
				double minDis=Double.POSITIVE_INFINITY;int index=0;
				for(int i=0;i<log.size();i++) //find the closest robots to the press
				{
					int src=robots.get(i).getSrc();
					if(ga.shortestPathDist(src, destNode)<minDis) 
					{	
						minDis=ga.shortestPathDist(src, destNode);
						move=robots.get(i);
						index=i;
					}
				}
				if(ga.shortestPath(move.getSrc(),destNode).size()==1)
					destByMouse=destNode;
				else
					destByMouse=ga.shortestPath(move.getSrc(),destNode).get(1).getKey();
				game.chooseNextEdge(move.getId(), destByMouse);
				try {
						System.out.println("Turn to node: "+destByMouse+"  time to end:"+(t/1000));
						System.out.println((new JSONObject(log.get(index))).getJSONObject("Robot"));
					}
					catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * The function find the node that is closest to the press 
	 * @param x- the coordinate of X that Pressed
	 * @param y- the coordinate of Y that Pressed
	 * @return the id of the node
	 */
	private int findNode(double x,double y) 
	{
		double minDis=Double.POSITIVE_INFINITY;
		int src=0;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)
		{
			int nd=verIter.next().getKey();
			if(Math.abs(x-g.getNode(nd).getLocation().x())+Math.abs(y-g.getNode(nd).getLocation().y())<minDis)
			{
				minDis=Math.abs(x-g.getNode(nd).getLocation().x())+Math.abs(y-g.getNode(nd).getLocation().y());
				src=nd;
			}
		}
		return src;
	}

}
