package obj;

import java.util.Iterator;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This class represent a Pacman(robot)
 * each Pacman has value(grade until now), src ,dest ,speed ,position
 * @author itay simhayev and lilach mor
 */
public class Pacman 
{
	private int id;
	private double value;
	private int src;
	private int dest;
	private double speed;
	private String pos;

	public Pacman(int Id,int Value,int Src,int Dest,double Speed,String str)
	{
		this.id=Id;
		this.value=Value;
		this.src=Src;
		this.dest=Dest;
		this.speed=Speed;
		this.pos=str;
	}
	/**
	 * @return the id of the pacman type of integer
	 */
	public int getId() 
	{
		return id;
	}
	/**
	 * @return the value of the pacman type of double
	 */
	public double getValue() 
	{
		return value;
	}
	/**
	 * @return the id node of src of the pacman type of integer
	 */
	public int getSrc() 
	{
		return src;
	}
	/**
	 * @return the id node of dest of the pacman type of integer
	 */
	public int getDest() 
	{
		return dest;
	}
	/**
	 * @return the speed of the pacman type of double
	 */
	public double getSpeed() 
	{
		return speed;
	}
	/**
	 * @return the position of the pacman type of Point3D
	 */
	public Point3D getPos() 
	{
		Point3D p=new Point3D(pos);
		return p;
	}
	/**
	 * The function set the src of the pacman
	 */
	public void setSrc(int src) 
	{
		this.src=src;
	}
	/**
	 * The function set the dest of the pacman
	 */
	public void setDest(int dest) 
	{
		this.dest=dest;
	}
	/**
	 * The function set the position of the pacman
	 */
	public void setPos(Point3D p) 
	{
		this.pos=""+p.x()+","+p.y()+","+p.z();
	}
	/**
	 * The function find the edge in graph g where the pacman is located
	 * @param g- graph
	 * @return edge_data the edge that the pacman located
	 */
	public edge_data edge(graph g)
	{
		double min=Double.POSITIVE_INFINITY;
		edge_data efinal=null;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();)//Go over all the vertices and find there edges
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
		return efinal;
	}



}