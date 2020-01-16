package obj;

/**
 * This class represent a GameServer 
 * each GameServer has number of fruits, number of moves, grade, number of robots, and string that represent graph
 * @author itay simhayev and lilach mor
 */
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
	/**
	 * @return the number of the fruit in gameServer
	 */
	public int getFruits() 
	{
		return fruits;
	}
	/**
	 * @return the number of the moves in gameServer
	 */
	public int getMoves() 
	{
		return moves;
	}
	/**
	 * @return the number of the robors in gameServer
	 */
	public int getRobots() 
	{
		return robots;
	}
	/**
	 * @return the grade of the game
	 */
	public int getGrade() 
	{
		return grade;
	}
	/**
	 * @return a String represent a graph type of Json
	 */
	public String getGraph() 
	{
		return graph;
	}

}