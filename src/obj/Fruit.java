package obj;

import java.util.Iterator;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class Fruit 
{
	private double value;
	private int type;
	private String pos;

	public Fruit(double Value,int Type,String str)
	{
		this.value=value;
		this.type=type;
		this.pos=str;
	}
	public static void main(String[] args)
	{
		Fruit f=new Fruit(5.0,-1,"35.20273974670703,32.10439601193746,0.0");
		Point3D p=new Point3D(35.30273974670703,33.10439601193746,0.0);
		f.setPos(p);
	}
	public double getValue() 
	{
		return value;
	}
	public int getType() 
	{
		return type;
	}
	public Point3D getPos() 
	{
		Point3D p=new Point3D(pos);
		return p;
	}

	public void setPos(Point3D p) 
	{
		this.pos=""+p.x()+","+p.y()+","+p.z();
		System.out.println(pos);
	}
	public edge_data edge(graph g)
	{
		double min=Double.POSITIVE_INFINITY;
		edge_data efinal=null;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)
		{ 
			int v=verIter.next().getKey();
			try 
			{
				for(Iterator<edge_data> edgeIter=g.getE(v).iterator();edgeIter.hasNext();)
				{
					edge_data e=edgeIter.next();
					double dis1=this.getPos().distance2D(g.getNode(e.getSrc()).getLocation());
					double dis2=this.getPos().distance2D(g.getNode(e.getDest()).getLocation());
					double dis3=g.getNode(e.getSrc()).getLocation().distance2D(g.getNode(e.getDest()).getLocation());
					if(Math.abs((dis1+dis2)-dis3)<=min)
					{
						min=Math.abs((dis1+dis2)-dis3);
						efinal=e;
					}
				}
			}
			catch(NullPointerException e)
			{}
		}
		if(efinal.getSrc()-efinal.getDest()>0&&type==1)
			return efinal;
		if(efinal.getSrc()-efinal.getDest()<0&&type!=1)
			return efinal;
		efinal=g.getEdge(efinal.getDest(), efinal.getSrc());		
		return efinal;
	}



}