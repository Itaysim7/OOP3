package obj;

import java.util.Iterator;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

/**
 * This class represent a fruit type of apple(1) and banana(-1)
 * each fruit has value type and position
 * @author itay simhayev and lilach mor
 */
public class Fruit 
{
	private double value;
	private int type;
	private String pos;

	public Fruit(double Value,int Type,String str)
	{
		this.value=Value;
		this.type=Type;
		this.pos=str;
	}
	/**
	 * @return the value of the fruit type of double
	 */
	public double getValue() 
	{
		return value;
	}
	/**
	 * @return the type of the fruit type of integer
	 */
	public int getType() 
	{
		return type;
	}
	/**
	 * @return the position of the fruit type of Point3D
	 */
	public Point3D getPos() 
	{
		Point3D p=new Point3D(pos);
		return p;
	}
	/**
	 * The function set the position of the fruit
	 */
	public void setPos(Point3D p) 
	{
		this.pos=""+p.x()+","+p.y()+","+p.z();
		System.out.println(pos);
	}
	/**
	 * The function find the edge in graph g where the fruit is located
	 * @param g- graph
	 * @return edge_data the edge that the fruit located
	 */
	public edge_data edge(graph g)
	{
		double min=Double.POSITIVE_INFINITY;
		edge_data efinal=null;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) //Go over all the vertices and find there edges
		{ 
			int v=verIter.next().getKey();
			try 
			{
				for(Iterator<edge_data> edgeIter=g.getE(v).iterator();edgeIter.hasNext();)//Go over all the edges
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
		if(efinal.getSrc()-efinal.getDest()<0)//if id of src is bigger than id of dest and the type of fruit is apple
			return efinal;
		efinal=g.getEdge(efinal.getDest(), efinal.getSrc());		
		return efinal;
	}


}