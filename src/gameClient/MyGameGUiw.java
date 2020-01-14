package gameClient;

import java.awt.Color;
import java.awt.Robot;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUiw 
{
	private game_service game;
	private DGraph g;
	private double maxX=Double.NEGATIVE_INFINITY;
	private double maxY=Double.NEGATIVE_INFINITY;
	private double minX=Double.POSITIVE_INFINITY;
	private double minY=Double.POSITIVE_INFINITY;
	private static List<Fruit> fruits;
	private static List<Pacman> robots;
	public MyGameGUiw(game_service game,DGraph gg, List<Fruit> fruits, List<Pacman> robots) 
	{
		this.fruits=fruits;
		this.robots=robots;
		this.game=game;
		g =gg;

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



	
	

	
	
//	private static int mousePress()
//	{
//		boolean t=true;
//		while(t)
//		{
//			double x=StdDraw.mouseX();
//			double y=StdDraw.mouseX();
//			if(x!=0&&y!=0)
//			{
//				
//			}
//		}
//		
//		while(x!=0)
//
//		System.out.println("x:"+x+" y:"+y);
//		return (Integer) null;
//		
//	}
//	private node_data findNode(double x,double y) 
//	{
//		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) {
//			int nd=verIter.next().getKey();
//			if(Math.abs(x-g.getNode(nd).getLocation().x())<=1&&Math.abs(y-g.getNode(nd).getLocation().y())<=1)
//				return g.getNode(nd);
//		}
//		return null;
//	}
	
//	
//	@Override
//	public void run() 
//	{
//		while(true)
//		{
//				synchronized(this) 
//				{
//					for(int i=0;i<robots.size();i++)
//					{
//						Pacman r=robots.get(i);
//						System.out.println("pos: "+r.getPos());
//						System.out.println("dest "+g.getNode(r.getDest()).getLocation());
//						System.out.println(" ");
//
//						if(r.getPos().close2equals(g.getNode(r.getDest()).getLocation()))
//						{
//							System.out.println("true");
//							r.setSrc(r.getDest());
//							r.setDest(shortestPathRobot(r));
//						}
//						int src=r.getSrc();int dest=r.getDest();
//						double pace=(g.getEdge(src, dest).getWeight())/r.getSpeed()*10;
//						Point3D p =new Point3D(r.getPos().x()+(g.getNode(dest).getLocation().x()-g.getNode(src).getLocation().x())/pace,r.getPos().y()+(g.getNode(dest).getLocation().y()-g.getNode(src).getLocation().y())/pace);
//						r.setPos(p);
//					}
//					System.out.println(game.toString());
//					System.out.println(game.getRobots()); 
//
//
//				}
//				try
//				{
//					Thread.sleep(100);
//				}
//				catch(InterruptedException e)
//				{
//					e.printStackTrace();
//				}
//		}
//	}


}
