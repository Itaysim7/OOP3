package gameClient;

import java.util.ArrayList;


import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import obj.Fruit;
import obj.Pacman;

/**
 * This class runs algorithms for moving robots in the graph
 * There are two algorithms : by mouse an by automatic thats moves the robot to the next close node
 * @author itay simhayev and lilach mor
 *
 */
public class AutoDrive 
{
	private game_service game;
	private DGraph g;
	private Graph_Algo ga;
	private List<Fruit> fruits;
	private List<Pacman> robots;

	public AutoDrive(game_service game1,List<Fruit> f,List<Pacman> p) 
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
	 * The function move the robots by algorithms
	 * each robot to the closest fruit
	 */
	
	public void moveRobots()
	{
		List<Integer> destList = new ArrayList<Integer>();
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) //for each robots find his next destination 
			{
				destList.add(robots.get(i).getDest());
			}
			for(int i=0;i<log.size();i++) //for each robots find his next destination 
			{
				try {
					int rid=robots.get(i).getId();
					int src=robots.get(i).getSrc();
					int dest=robots.get(i).getDest();
					if(dest==-1) 
					{	
						dest = bestNode(src,destList); //find the closet fruit
						destList.add(dest);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println((new JSONObject(log.get(i))).getJSONObject("Robot"));
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}		
	}
	/**
	 * find the fruits that is the closest to the robot in node src
	 * @param src
	 * @return the id of the next node
	 */
	private  int bestNode(int src,List<Integer>destList) 
	{
		double minpath=Double.POSITIVE_INFINITY;
		edge_data e=null;
		int destFinal=0;
		boolean isGetDest=false;
		for(int i=0;i<fruits.size();i++) //find the fruit that is closest to the src
		{
			double dist;List<node_data> node;int dest;
		 	e=fruits.get(i).edge(g);
		 	if(fruits.get(i).getType()==1) //if its apple
		 	{
		 		dist=ga.shortestPathDist(src, e.getSrc());
		 		node=ga.shortestPath(src, e.getSrc());
		 		if(node.size()==1)
		 			dest= e.getDest();
		 		else
		 			dest=node.get(1).getKey();
		 	}
		 	else//if its banana
		 	{
		 		dist=ga.shortestPathDist(src, e.getDest());
		 		node=ga.shortestPath(src, e.getDest());
		 		if(node.size()==1)
		 			dest= e.getSrc();
		 		else
		 			dest=node.get(1).getKey();
		 	}
			if(dist<minpath&&!destList.contains(dest))//check if its better result and if the dest is not in the list
			{ 
				 	isGetDest=true;
					minpath=dist;
					destFinal=dest;
			}
		}
		if(isGetDest)
			return destFinal;
		else //if there isnt a fruit available for this robots
		{
			e=fruits.get(0).edge(g);
			if(src==e.getSrc()) 
			{
				return e.getDest();
			}
			else
				if(src==e.getDest())
					return e.getSrc();
			List<node_data> node=ga.shortestPath(src, e.getSrc());
			return (node.get(1).getKey());
		}		
	}

}
