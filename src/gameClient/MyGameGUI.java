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
	private int scenario;
	private static int count=0;
	private List<Fruit> fruits;
	private List<Pacman> robots;

	public MyGameGUI()
	{
		StdDraw.enableDoubleBuffering();
		init();
		paint();
		fruits=creatFruits();
		drawFruits();
		StdDraw.show();
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
		//////choose level////
		Object level[]=new Object[24];
		for(int i=0;i<level.length;i++)
			level[i]=i;
		scenario=(Integer)JOptionPane.showInputDialog(null,"Choose a level between 0-23","Level", JOptionPane.QUESTION_MESSAGE,null,level,null);
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
		minY=minY-epsilon/2;maxY=epsilon/2+maxY;
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
//					StdDraw.text(g.getNode(src).getLocation().x()+(g.getNode(dest).getLocation().x()-g.getNode(src).getLocation().x())/4,g.getNode(src).getLocation().y()+(g.getNode(dest).getLocation().y()-g.getNode(src).getLocation().y())/4,(""+weight));
					//draw yellow point that represent the destination of the edge
//					StdDraw.setPenColor(Color.YELLOW);
//					StdDraw.setPenRadius(0.015);
//					StdDraw.point(g.getNode(dest).getLocation().x()+(g.getNode(src).getLocation().x()-g.getNode(dest).getLocation().x())/10,g.getNode(dest).getLocation().y()+(g.getNode(src).getLocation().y()-g.getNode(dest).getLocation().y())/10);
				}
			}
			catch (Exception e) {}
		}
		long time=game.timeToEnd();
		GameServer result=creatGameServer(game.toString());
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.020);
		StdDraw.text(minX+(maxX-minX)*0.85,minY+(maxY-minY)*0.95, "The time is:"+time/1000);
		StdDraw.text(minX+(maxX-minX)*0.15,minY+(maxY-minY)*0.95, "Level:"+scenario);
		StdDraw.text(minX+(maxX-minX)*0.50,minY+(maxY-minY)*0.95, "grade:"+result.getGrade());
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
		count++;
		for(int i=0;i<robots.size();i++)
		{
			Pacman f=robots.get(i);
			StdDraw.setPenColor(Color.RED);
			StdDraw.setPenRadius(0.025);
			if(count%2==0)
				StdDraw.picture(f.getPos().x(), f.getPos().y(),"pacmanopen.png", 0.001, 0.001);
			else
				StdDraw.picture(f.getPos().x(), f.getPos().y(),"pacmanclose.png", 0.001, 0.001);

//			StdDraw.point(f.getPos().x(),f.getPos().y());	
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
		//sort the array by value
		for(int i=0;i<fruitstemp.size();i++)
		{
			for(int j=fruitstemp.size()-1;j>i;j--)
			{
				if(fruitstemp.get(j).getValue()>fruitstemp.get(j-1).getValue())
				{
					Fruit temp=fruitstemp.get(j);
					fruitstemp.set(j, fruitstemp.get(j-1));
					fruitstemp.set(j-1, temp);
				}
			}
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
		int index=0;int v;
		//put the robot near to the fruit
		while(countFruit>0&&countRobot>0)
		{
			int typefruit=fruits.get(index).getType();
			if(typefruit==1)
				v=fruits.get(index).edge(g).getSrc();
			else
				v=fruits.get(index).edge(g).getDest();
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
			robotemp.add(itay);
		}
		return robotemp;
	}
	@Override
	public void run() 
	{
		game.startGame();
		int index=0;
		while(game.isRunning())
		{
				synchronized(this) 
				{
					moveRobots();
					if(index%4==0)
						draw();
					index++;
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


}
