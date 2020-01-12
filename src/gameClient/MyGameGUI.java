package gameClient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.StdDraw;

public class MyGameGUI implements Runnable
{
	private game_service game;
	private DGraph g;
	private Graph_Algo ga;
	private double maxX=Double.NEGATIVE_INFINITY;
	private double maxY=Double.NEGATIVE_INFINITY;
	private double minX=Double.POSITIVE_INFINITY;
	private double minY=Double.POSITIVE_INFINITY;
	private String typegame;
	private List<Fruit> fruits;
	private List<Pacman> robots;

	public MyGameGUI()
	{
		StdDraw.enableDoubleBuffering();
		init();
		paint();
		fruits=creatFruits();
		drawFruits();
		if(typegame.equals("Automatic"))
			addRobotToTheGameAuto();
		else
			addRobotToTheGameByMouse();
		robots=creatRobotsList();
		drawRobot();
		Thread t=new Thread(this);
		t.start();
	}
	public void init()
	{
		StdDraw.show();
		//////choose level////
		Object level[]=new Object[24];
		for(int i=0;i<level.length;i++)
			level[i]=i;
		int scenario=(Integer)JOptionPane.showInputDialog(null,"Choose a level between 0-23","Level", JOptionPane.QUESTION_MESSAGE,null,level,null);
		game = Game_Server.getServer(scenario);
		//////choose game type////
		Object type[]=new Object[2];type[0]="by mouse";type[1]="Automatic";
		typegame=(String)JOptionPane.showInputDialog(null,"Choose type of game","type of game", JOptionPane.QUESTION_MESSAGE,null,type,null);
		//////create graph and algo graph////
		g=new DGraph();
		g.init(game.getGraph());
		ga=new Graph_Algo();
		ga.init(g);
		porpor();
	}
	public void draw()
	{
		StdDraw.clear();
		paint();
		fruits=creatFruits();
		drawFruits();
		robots=creatRobotsList();
		drawRobot();
		StdDraw.show();
	}
	public static void main(String[] a)
	{
		MyGameGUI m=new MyGameGUI();
	}
	
	public void porpor() 
	{	
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)
		{
			int point=verIter.next().getKey();
			if(g.getNode(point).getLocation().x()>maxX)
				maxX=g.getNode(point).getLocation().x();
			if(g.getNode(point).getLocation().y()>maxY)
				maxY=g.getNode(point).getLocation().y();
			if(g.getNode(point).getLocation().x()<minX)
				minX=g.getNode(point).getLocation().x();
			if(g.getNode(point).getLocation().y()<minY)
				minY=g.getNode(point).getLocation().y();	
		} 
		double epsilon=0.0025;
		StdDraw.setCanvasSize(600,600);
		minX=minX-epsilon;maxX=maxX+epsilon;
		minY=minY-epsilon/4;maxY=epsilon/4+maxY;
		StdDraw.setXscale(minX,maxX);
		StdDraw.setYscale(minY,maxY);
	}
////////draw/////////////
	
	public void paint() 
	{	
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) 
		{
			int point=verIter.next().getKey();
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.020);
			StdDraw.point(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y());
			StdDraw.text(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y()+0.00025, (""+point));
			
			try {//in case point does not have edge the function getE return exception, and we do not want exception we just do not want it to paint
				for(Iterator<edge_data> edgeIter=g.getE(point).iterator();edgeIter.hasNext();) 
				{
					edge_data line=edgeIter.next();
					int dest=g.getNode(line.getDest()).getKey();
					int src=point;
					double weight=TwoNumAfter(line.getWeight());
					StdDraw.setPenColor(Color.DARK_GRAY);
					StdDraw.setPenRadius(0.005);
					StdDraw.line(g.getNode(src).getLocation().x(),g.getNode(src).getLocation().y(), g.getNode(dest).getLocation().x(),g.getNode(dest).getLocation().y());
					StdDraw.text(g.getNode(src).getLocation().x()+(g.getNode(dest).getLocation().x()-g.getNode(src).getLocation().x())/4,g.getNode(src).getLocation().y()+(g.getNode(dest).getLocation().y()-g.getNode(src).getLocation().y())/4,(""+weight));
					//draw yellow point that represent the destination of the edge
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setPenRadius(0.015);
					StdDraw.point(g.getNode(dest).getLocation().x()+(g.getNode(src).getLocation().x()-g.getNode(dest).getLocation().x())/10,g.getNode(dest).getLocation().y()+(g.getNode(src).getLocation().y()-g.getNode(dest).getLocation().y())/10);
				}
			}
			catch (Exception e) {}
		}
	}
	private double TwoNumAfter(double w) 
	{
		double tmp=w*100;
		int fin=(int) tmp;
		return (fin/100.0);
	}
	
	
	public void drawFruits()
	{
		for(int i=0;i<fruits.size();i++)
		{
			Fruit f=fruits.get(i);
			if(f.getType()==1)
				StdDraw.picture(f.getPos().x(), f.getPos().y(),"apple.png", 0.0005, 0.0005);
			else
				StdDraw.picture(f.getPos().x(), f.getPos().y(),"banana.png", 0.0005, 0.0005);
		}
	}
	
	public void drawRobot()
	{
		for(int i=0;i<robots.size();i++)
		{
			Pacman f=robots.get(i);
			StdDraw.setPenColor(Color.MAGENTA);
			StdDraw.setPenRadius(0.025);
			StdDraw.point(g.getNode(f.getSrc()).getLocation().x(),g.getNode(f.getSrc()).getLocation().y());		
		}
	}

	//////create object////
	
	private List<Fruit> creatFruits()
	{
		List<Fruit> fruitstemp=new ArrayList();
		Iterator<String> f_iter=game.getFruits().iterator();
		while(f_iter.hasNext())
		{
			String fruit=f_iter.next();
			fruit=fruit.substring(9,fruit.length()-1);
			Fruit f=creatFruit(fruit);
			fruitstemp.add(f);//add to the list of fruit
		}
		return fruitstemp;
	}
	private static Fruit creatFruit(String str) 
	{	
		Gson gson = new Gson();
		try
		{
			Fruit f=gson.fromJson(str, Fruit.class);
			return f;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for fruit");
		}
	}
	private static  GameServer creatGameServer(String str) 
	{	
		str=str.substring(14,str.length()-1);
		Gson gson = new Gson();
		try
		{
			GameServer game=gson.fromJson(str, GameServer.class);
			return game;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for GameServe");
		}
	}
	
	private static Pacman creatRobot(String str) 
	{	
		str=str.substring(9,str.length()-1);
		Gson gson = new Gson();
		try
		{
			Pacman rob=gson.fromJson(str, Pacman.class);
			return rob;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for Robot");
		}
	}
	public void addRobotToTheGameByMouse()
	{
		GameServer gameSer=creatGameServer(game.toString());
		int countRobot=gameSer.getRobots();
		int numOfVer=g.nodeSize();
		Object key[]=new Object[numOfVer];
		int j=0;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) 
		{
			int point=verIter.next().getKey();
			key[j]=point;j++;
		}
		for(int i=1;i<=countRobot;i++)
		{
			int v=(Integer)JOptionPane.showInputDialog(null,"choose a vertex to pud robot "+i,"Add robot",JOptionPane.QUESTION_MESSAGE,null,key,null);
			game.addRobot(v);
			StdDraw.setPenColor(Color.MAGENTA);
			StdDraw.setPenRadius(0.025);
			StdDraw.point(g.getNode(v).getLocation().x(),g.getNode(v).getLocation().y());
		}
	}
	private void addRobotToTheGameAuto()
	{
		GameServer gameSer=creatGameServer(game.toString());
		int countRobot=gameSer.getRobots();//number of robot in the game
		int countFruit=gameSer.getFruits();//number of fruit in the game
		int index=0;
		//put the robot near to the fruit
		while(countFruit>0&&countRobot>0)
		{
			int v=fruits.get(index).edge(g).getSrc();
			game.addRobot(v);
			countFruit--;countRobot--;index++;
		}
		//Create a list of the edge
		int numOfVer=g.nodeSize();
		int key[]=new int[numOfVer];
		int j=0;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) 
		{
			int point=verIter.next().getKey();
			key[j]=point;j++;
		}
		//if there is more robot than fruit put them in a random place
		while(countRobot>0)
		{
			int random=(int)(Math.random()*key.length);
			game.addRobot(key[random]);
			countRobot--;
		}
	}
	private List<Pacman> creatRobotsList()
	{
		List<String> rob=game.getRobots();
		List<Pacman>robotemp=new ArrayList();
		for(int i=0;i<rob.size();i++)
		{
			Pacman itay = creatRobot(rob.get(i));
//			
////			int dest=0;
//			try 
//			{
//				for(Iterator<edge_data> edgeIter=g.getE(itay.getSrc()).iterator();edgeIter.hasNext();)
//				{
//					edge_data e=edgeIter.next();
//					dest=e.getDest();
//					break;
//				}	
//			}
//			catch(NullPointerException e)
//			{}
//			itay.setDest(-1);
			robotemp.add(itay);
		}
		return robotemp;
	}
	@Override
	public void run() 
	{
		game.startGame();
		while(game.isRunning())
		{
				synchronized(this) 
				{
					moveRobots();
					draw();
				}
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
		}		
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}
	
	private  void moveRobots()
	{
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
				
					if(dest==-1) {	
						dest = nextNode(src);
						game.chooseNextEdge(rid, dest);
						System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						System.out.println(ttt);
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	private  int nextNode(int src) {
		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r) {itr.next();i++;}
		ans = itr.next().getDest();
		return ans;
	}


}
