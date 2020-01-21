package obj;

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
	
	public int getMoves() {
		return moves;
	}
	public int getLevel() {
		return level;
	}
	public double getScore() {
		return score;
	}
	public String toStringLevelScore() {
		return "The best score for level "+level+" is "+score;
	}
}