package obj;

import utils.Point3D;

public class GameServer 
{
	private int fruits;
	private int moves;
	private int grade;
	private int robots;
	private String graph;


	public GameServer(int f,int m,int g,int r,String gr)
	{
		this.fruits=f;
		this.moves=m;
		this.grade=g;	
		this.robots=r;
		this.graph=gr;
	}
	public static void main(String[] args)
	{

	}
	public int getFruits() 
	{
		return fruits;
	}
	public int getMoves() 
	{
		return moves;
	}
	public int getRobots() 
	{
		return robots;
	}
	public int getGrade() 
	{
		return grade;
	}
	public String getGraph() 
	{
		return graph;
	}






}