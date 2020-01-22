package obj;
/**
 * this class saving the score and the move for each level
 * @author lilach mor and itay simhayev
 *
 */
public class SavingScore
{
	private int level;
	private double score; 
	private int moves;
	
	public SavingScore(int level1,double score1,int moves1)
	{
		level=level1;
		score=score1;
		moves=moves1;
	}
	/**
	 * 
	 * @return how many move been done on this level
	 */
	public int getMoves() {
		return moves;
	}
	/**
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * 
	 * @return the score of the level
	 */
	public double getScore() {
		return score;
	}
	/**
	 * 
	 * @return string of the score in the level
	 */
	public String toStringLevelScore() {
		return "The best score for level "+level+" is "+score;
	}
}