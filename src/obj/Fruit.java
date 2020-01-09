package obj;

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



}