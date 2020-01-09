package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class nodeData implements node_data, Serializable
{
	private static int nextID = 0;
	private Point3D location;
	private int key;
	private double weight;
	private String info;
	private int tag;

	public nodeData() 
	{
		this.key=nextID;
		this.location=null;
		this.weight=0;
		this.info="";
		this.tag=0;
		nextID++;
	}
	public nodeData(Point3D p,double w,String i,int t) 
	{
		this.key=nextID;
		this.location=p;
		this.weight=w;
		this.info=i;
		this.tag=t;
		nextID++;
	}


	public nodeData(int s, Point3D p)
	{
		this.key=s;
		this.location=p;
		this.weight=0;
		this.info="";
		this.tag=0;
		nextID++;
	}
	@Override
	public int getKey() 
	{
		return this.key;
	}

	@Override
	public Point3D getLocation() 
	{
		if(location!=null)
			return this.location;
		else
			return null;
	}

	@Override
	public void setLocation(Point3D p) 
	{
		Point3D temp=new Point3D(p);
		this.location=temp;
	}
	@Override
	public double getWeight() 
	{
		return this.weight;
	}
	@Override
	public void setWeight(double w) 
	{
		this.weight=w;	
	}

	@Override
	public String getInfo() 
	{
		return this.info;
	}

	@Override
	public void setInfo(String s) 
	{
		this.info=s;
	}

	@Override
	public int getTag() 
	{
		return this.tag;
	}

	@Override
	public void setTag(int t) 
	{
		if(t>=0&&t<=2)//0=white,1=gray,2=black. if it not one of the tag stay the same
		{
			this.tag=t;
		}
		
	}

}
