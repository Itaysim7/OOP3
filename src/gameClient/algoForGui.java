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

public class algoForGui 
{
	private game_service game;
	private DGraph g;
	private Graph_Algo ga;
	private List<Fruit> fruits;
	private List<Pacman> robots;
	private String typegame;
	private int destNode=-1;
	private int destByMouse;
	private Pacman move;

	public algoForGui(game_service game1,List<Fruit> f,List<Pacman> p,String t) 
	{
		this.game=game1;
		fruits=f;
		robots=p;
		g=new DGraph();
		g.init(game.getGraph());
		ga=new Graph_Algo();
		ga.init(g);
		typegame=t;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public  void moveRobots()
	{
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++)
			{
				try {
					int rid=robots.get(i).getId();
					int src=robots.get(i).getSrc();
					int dest=robots.get(i).getDest();
					if(dest==-1) 
					{	
						dest = bestNode(src);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println((new JSONObject(log.get(i))).getJSONObject("Robot"));
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		ga.resetTagEdge();
	}
	/**
	 * find the fruits that is the closest to the robot in node src
	 * @param src
	 * @return
	 */
	private  int bestNode(int src) 
	{
		double minpath=Double.POSITIVE_INFINITY;
		int dest=0;int placeFruit=0;
		boolean isGetDest=false;
		for(int i=0;i<fruits.size();i++)
		{
			if(fruits.get(i).edge(g).getTag()==0&&ga.shortestPathDist(src, fruits.get(i).edge(g).getSrc())<minpath)
			{
					placeFruit=i;
				 	isGetDest=true;
					minpath=ga.shortestPathDist(src, fruits.get(i).edge(g).getSrc());
					if(src==fruits.get(i).edge(g).getSrc()) 
					{
						dest=fruits.get(i).edge(g).getDest();
					}
					else
						dest=fruits.get(i).edge(g).getSrc();
			}
		}
		if(!isGetDest)
		{
			System.out.println("is get dest");
			if(src==fruits.get(0).edge(g).getSrc()) 
			{
				dest=fruits.get(0).edge(g).getDest();
			}
			else
				dest=fruits.get(0).edge(g).getSrc();
		}
		fruits.get(placeFruit).edge(g).setTag(1);
		g.getEdge(fruits.get(placeFruit).edge(g).getDest(), fruits.get(placeFruit).edge(g).getSrc()).setTag(1);
		List<node_data> node=ga.shortestPath(src, dest);
		return node.get(1).getKey();
	}
	
	////////////by mouse////////
	public void moveRobotsbyMouse(game_service game1)
	{
		List<String> log = game1.move();
		if(log!=null) 
		{
			long t = game1.timeToEnd();
			if(StdDraw.isMousePressed())
			{
				double x=StdDraw.mouseX();double y=StdDraw.mouseY();
				destNode=findNode(x,y);
			}
			if(destNode!=-1)
			{
				double minDis=Double.POSITIVE_INFINITY;int index=0;
				for(int i=0;i<log.size();i++)
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
				game1.chooseNextEdge(move.getId(), destByMouse);
				System.out.println("src:"+move.getSrc());
				System.out.println("dest:"+destByMouse);


//				try {
//						System.out.println("Turn to node: "+destByMouse+"  time to end:"+(t/1000));
//						System.out.println((new JSONObject(log.get(index))).getJSONObject("Robot"));
//					}
//					catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
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
