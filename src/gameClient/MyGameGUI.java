package gameClient;

import java.awt.Color;
import java.util.Iterator;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.StdDraw;

public class MyGameGUI implements Runnable  
{
	private game_service game;
	private DGraph g;
	private int mc;
	public MyGameGUI(game_service game1) 
	{
		this.game=game1;
		g = new DGraph();
		g.init(game1.getGraph());
		this.mc=g.getMC();
		this.paint();
		this.drawFruits();
		this.drawRobot();
		Thread change=new Thread(this);
		change.start();
	}

	public static void main(String[] args) 
	{
		
	}

	@Override
	public void run() 
	{
		while(game.isRunning())
		{
				synchronized(this) 
				{
					if(mc!=g.getMC())
					{
						mc=g.getMC();
						this.paint();
					}
				}
				try
				{
					Thread.sleep(500);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
		}
		
	}
	private void paint() 
	{	

		double maxX=Double.NEGATIVE_INFINITY;
		double maxY=Double.NEGATIVE_INFINITY;
		double minX=Double.POSITIVE_INFINITY;
		double minY=Double.POSITIVE_INFINITY;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)
		{
			node_data point=verIter.next();
			if(point.getLocation().x()>maxX)
				maxX=point.getLocation().x();
			if(point.getLocation().y()>maxY)
				maxY=point.getLocation().y();
			if(point.getLocation().x()<minX)
				minX=point.getLocation().x();
			if(point.getLocation().y()<minY)
				minY=point.getLocation().y();	
		} 
		double epsilon=0.0025;
		StdDraw.setCanvasSize(600,600);
		StdDraw.setXscale(minX-epsilon,maxX+epsilon);
		StdDraw.setYscale(minY-epsilon,epsilon+maxY);

		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) 
		{
			node_data point=verIter.next();
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.setPenRadius(0.020);
			StdDraw.point(point.getLocation().x(),point.getLocation().y());
			StdDraw.text(point.getLocation().x(),point.getLocation().y()+epsilon/10, (""+point.getKey()));
			
			try {//in case point does not have edge the function getE return exception, and we do not want exception we just do not want it to paint
				for(Iterator<edge_data> edgeIter=g.getE(point.getKey()).iterator();edgeIter.hasNext();) 
				{
					edge_data line=edgeIter.next();
					node_data dest=g.getNode(line.getDest());
					node_data src=point;
					double weight=TwoNumAfter(line.getWeight());
					StdDraw.setPenColor(Color.DARK_GRAY);
					StdDraw.setPenRadius(0.005);
					StdDraw.line(src.getLocation().x(),src.getLocation().y(), dest.getLocation().x(),dest.getLocation().y());
					StdDraw.text(src.getLocation().x()+(dest.getLocation().x()-src.getLocation().x())/4,src.getLocation().y()+(dest.getLocation().y()-src.getLocation().y())/4,(""+weight));
					//draw yellow point that represent the destination of the edge
					StdDraw.setPenColor(Color.YELLOW);
					StdDraw.setPenRadius(0.015);
					StdDraw.point(dest.getLocation().x()+(src.getLocation().x()-dest.getLocation().x())/10,dest.getLocation().y()+(src.getLocation().y()-dest.getLocation().y())/10);
				}
			}
			catch (Exception e) {}
		}
	}
	private static double TwoNumAfter(double w) {
		double tmp=w*100;
		int fin=(int) tmp;
		return (fin/100.0);
	}
	private void drawFruits()
	{
		Iterator<String> f_iter = game.getFruits().iterator();
		while(f_iter.hasNext())
		{
			String fruit=f_iter.next();
			boolean t=true;int type=0;double posLeft=0;double posRight=0;
			for(int i=0;i<fruit.length()&&t;i++)
			{
				if(fruit.charAt(i)=='t'&&fruit.charAt(i+1)=='y'&&fruit.charAt(i+2)=='p')
				{//find the type
					fruit=fruit.substring(i+6,fruit.length());
					int index=fruit.indexOf(',');
					type=Integer.parseInt(fruit.substring(0,index));
					i=0;
				}
				if(fruit.charAt(i)=='p'&&fruit.charAt(i+1)=='o'&&fruit.charAt(i+2)=='s')
				{//find the position 
					t=false;
					fruit=fruit.substring(i+6,fruit.length()-3);
					int index=fruit.indexOf(',');
					posLeft=Double.parseDouble(fruit.substring(0,index));
					fruit=fruit.substring(index+1,fruit.length());
					index=fruit.indexOf(',');
					posRight=Double.parseDouble(fruit.substring(0,index));
					if(type==1)
						StdDraw.picture(posLeft, posRight,"apple.png", 0.00075, 0.00075);
					else
						StdDraw.picture(posLeft, posRight,"banana.png", 0.00075, 0.00075);
				}

			}
		}
	}
	private void drawRobot()
	{
		String robots = game.toString();
		boolean t=true;int countRobot=0;
		int edgeSize=g.nodeSize();
		for(int i=0;i<robots.length()&&t;i++)
		{
			if(robots.charAt(i)=='r'&&robots.charAt(i+1)=='o'&&robots.charAt(i+2)=='b')
			{
				t=false;
				int index=robots.indexOf('}');
				countRobot=Integer.parseInt(robots.substring(i+8, index));
			}
		}
		for(int i=0;i<countRobot;i++)
		{
			int random=(int) (Math.random()*edgeSize);
			game.addRobot(random);
			StdDraw.setPenColor(Color.MAGENTA);
			StdDraw.setPenRadius(0.02);
			StdDraw.point(g.getNode(random).getLocation().x(),g.getNode(random).getLocation().y());
		}
	}

}
