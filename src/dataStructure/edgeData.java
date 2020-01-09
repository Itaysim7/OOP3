package dataStructure;

import java.io.Serializable;

public class edgeData implements edge_data , Serializable
{
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;

//	public edgeData() 
//	{
//		
//	}
	public edgeData(int s,int d,double w,String i,int t) 
	{
		this.src=s;
		this.dest=d;
		this.weight=w;
		this.info=i;
		this.tag=t;
	}

	@Override
	public int getSrc() 
	{
		return src;
	}

	@Override
	public int getDest() 
	{
		return dest;
	}

	@Override
	public double getWeight() 
	{
		return weight;
	}

	@Override
	public String getInfo() 
	{
		return info;
	}

	@Override
	public void setInfo(String s) 
	{
		info=s;
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
