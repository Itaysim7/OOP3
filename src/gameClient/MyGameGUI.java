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
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;
import utils.Point3D;
import utils.StdDraw;

public class MyGameGUI 
{
	private game_service game;
	private DGraph g;
	private int mc;
	private double maxX=Double.NEGATIVE_INFINITY;
	private double maxY=Double.NEGATIVE_INFINITY;
	private double minX=Double.POSITIVE_INFINITY;
	private double minY=Double.POSITIVE_INFINITY;
	public MyGameGUI(game_service game1,DGraph gg) 
	{
		this.game=game1;
		g =gg;
		this.mc=g.getMC();
	//	StdDraw.setGraph(g,game);
		porpor();
		paint();
		drawFruits();
		drawRobot();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	private Fruit creatFruit(String str) 
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
	private void drawFruits()
	{
		Iterator<String> f_iter=game.getFruits().iterator();
		while(f_iter.hasNext())
		{
			String fruit=f_iter.next();
			fruit=fruit.substring(9,fruit.length()-1);
			System.out.println(fruit);
			Fruit f=creatFruit(fruit);
			int type=f.getType();
			Point3D pos=f.getPos();
			if(type==1)
				StdDraw.picture(pos.x(), pos.y(),"apple.png", 0.0005, 0.0005);
			else
				StdDraw.picture(pos.x(), pos.y(),"banana.png", 0.0005, 0.0005);
		}
	}
	
	private  GameServer creatGameServer(String str) 
	{	
		str=str.substring(14,str.length()-1);
		System.out.println(str);
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
	
	public void drawRobot()
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
		List<String> rob=game.getRobots();
		for(int i=0;i<rob.size();i++)
		{
			System.out.println(rob.get(i));
			Pacman itay = creatRobot(rob.get(i));
			System.out.println(itay.getSrc());
		}

	}
	private node_data findNode(double x,double y) 
	{
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) {
			int nd=verIter.next().getKey();
			if(Math.abs(x-g.getNode(nd).getLocation().x())<=1&&Math.abs(y-g.getNode(nd).getLocation().y())<=1)
				return g.getNode(nd);
		}
		return null;
	}
	
	private Pacman creatRobot(String str) 
	{	
		str=str.substring(9,str.length()-1);
		System.out.println(str);
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
	

}
