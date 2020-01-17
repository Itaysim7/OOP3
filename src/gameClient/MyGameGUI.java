package gameClient;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;

import utils.StdDraw;
/**
 * This class represents a GUI window of game 
 * The GUI represents the moves in the graph of the robots and the fruits
 * @author itay simhayev and lilach mor
 */
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
	private createObjFromJson create;

	public MyGameGUI()
	{
		StdDraw.enableDoubleBuffering();
		init();
		paint();
		create=new createObjFromJson(game);
		fruits=create.creatFruits(); //create a list of fruits
		drawFruits();
		StdDraw.show();
		if(typegame.equals("Automatic"))
			addRobotToTheGameAuto();
		else
			addRobotToTheGameByMouse();
		robots=create.creatRobotsList(); //create a list of robots
		drawRobot();
		Thread t=new Thread(this);
		t.start();
	}
	/** 
	 * The function gets from the user the level and type of game that he choose 
	 * init the graph and the Proportions of the gui
	 */
	private void init()
	{
		//////choose level////
		Object level[]=new Object[24];
		for(int i=0;i<level.length;i++)
			level[i]=i;
		scenario=(Integer)JOptionPane.showInputDialog(null,"Choose a level between 0-23","Level", JOptionPane.QUESTION_MESSAGE,null,level,null);
		game = Game_Server.getServer(scenario);
		StdDraw.setGameNumber(scenario);
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
	/** 
	 * The function update the GUI by the data of the current game
	 */
	private void draw()
	{
		StdDraw.clear();
		paint();
		create.update(game);
		fruits=create.creatFruits();
		drawFruits();
		robots=create.creatRobotsList();
		drawRobot();
		StdDraw.show();
	}
	/** 
	 * The function set the proportions of the GUI window
	 */
	private void porpor() 
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
	/** 
	 * The function paint the graph of the game in GUI window
	 */
	private void paint() 
	{	
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) //paint all the the vertices in the graph
		{
			int point=verIter.next().getKey();
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.020);
			StdDraw.point(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y());
			StdDraw.text(g.getNode(point).getLocation().x(),g.getNode(point).getLocation().y()+0.00025, (""+point));
			
			try {//in case point does not have edge the function getE return exception, and we do not want exception we just do not want it to paint
				for(Iterator<edge_data> edgeIter=g.getE(point).iterator();edgeIter.hasNext();) 
				{ //paint all the edges in the graph
					edge_data line=edgeIter.next();
					int dest=g.getNode(line.getDest()).getKey();
					int src=point;
					StdDraw.setPenColor(Color.DARK_GRAY);
					StdDraw.setPenRadius(0.005);
					StdDraw.line(g.getNode(src).getLocation().x(),g.getNode(src).getLocation().y(), g.getNode(dest).getLocation().x(),g.getNode(dest).getLocation().y());
				}
			}
			catch (Exception e) {}
		}
		long time=game.timeToEnd();
		GameServer result=createObjFromJson.creatGameServer(game.toString());
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.020);
		StdDraw.text(minX+(maxX-minX)*0.85,minY+(maxY-minY)*0.95, "The time is:"+time/1000);//paint the time on the window GUI
		StdDraw.text(minX+(maxX-minX)*0.15,minY+(maxY-minY)*0.95, "Level:"+scenario);//paint the level on the window GUI
		StdDraw.text(minX+(maxX-minX)*0.50,minY+(maxY-minY)*0.95, "grade:"+result.getGrade());//paint the grade on the window GUI
	}
	/** 
	 * The function paint the fruits of the game in a GUI window from list of fruits
	 */
	private void drawFruits()
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
	/** 
	 * The function paint the fruits of the game in a GUI window from list of robots
	 */
	private void drawRobot()
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
		}
	}
	/** 
	 * The function paint the robots by the choose of the user in a GUI window (just in the beginning of the game) 
	 */
	private void addRobotToTheGameByMouse()
	{
		GameServer gameSer=createObjFromJson.creatGameServer(game.toString());
		int countRobot=gameSer.getRobots();
		int numOfVer=g.nodeSize();
		Object key[]=new Object[numOfVer];
		int j=0;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) //create a list of the vertices
		{
			int point=verIter.next().getKey();
			key[j]=point;j++;
		}
		for(int i=1;i<=countRobot;i++) //for each robot choose his place in the graph
		{
			int v=(Integer)JOptionPane.showInputDialog(null,"choose a vertex to pud robot "+i,"Add robot",JOptionPane.QUESTION_MESSAGE,null,key,null);
			game.addRobot(v);
			StdDraw.setPenColor(Color.MAGENTA);
			StdDraw.setPenRadius(0.025);
			StdDraw.point(g.getNode(v).getLocation().x(),g.getNode(v).getLocation().y());
		}
	}
	/** 
	 * The function paint the robots by the algorithm in a GUI window (just in the beginning of the game) 
	 * The algorithm choose for each robot the place of the source fruit with the biggest value
	 */
	private void addRobotToTheGameAuto()
	{
		GameServer gameSer=createObjFromJson.creatGameServer(game.toString());
		int countRobot=gameSer.getRobots();//number of robot in the game
		int countFruit=gameSer.getFruits();//number of fruit in the game
		int index=0;int v;
		while(countFruit>0&&countRobot>0)//put the robot near to the fruit
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
		while(countRobot>0)	//if there is more robot than fruit put them in a random place
		{
			int random=(int)(Math.random()*key.length);
			game.addRobot(key[random]);
			countRobot--;
		}
	}
	@Override
	public void run() 
	{	
		game.startGame();
		algoForGui a=new algoForGui(game, fruits, robots);
		while(game.isRunning())
		{
			a.update(game, fruits, robots);
				synchronized(this) 
				{
					if(typegame=="Automatic")
						a.moveRobots();
					else	
						a.moveRobotsbyMouse();
					draw();

				}
		}		
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}
}