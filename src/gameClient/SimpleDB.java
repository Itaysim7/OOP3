package gameClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import obj.SavingScore;
/**
 * This class represents a simple example of using MySQL Data-Base.
 * Use this example for writing solution. 
 * @author boaz.benmoshe
 *
 */
public class SimpleDB {
	public static final String jdbcUrl="jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser="student";
	public static final String jdbcUserPassword="OOP2020student";
	private static int count =0;
	private static int maxlevel=0;
	private static SavingScore data []=new SavingScore[24];


	/**
	 * Simple main for demonstrating the use of the Data-base
	 * @param args
	 */
	public static void main(String[] args) {
		int id1 = 205666407;  // "dummy existing ID  
		int level = 23;
		initData();
		SavingScore userBestResult []=new SavingScore[24];
		for(int i=0;i<userBestResult.length;i++) {
			userBestResult[i]=new SavingScore(i,0,0);
		}
		ourLog(id1,userBestResult);
		printResult(userBestResult);
		placeInClass(id1,userBestResult);
		//			allUsers();
		//			printLog();
		//			String kml = getKML(id1,level);
		//			System.out.println("***** KML file example: ******");
		//			System.out.println(kml);
	}
	/** simply prints all the games as played by the users (in the database).
	 * 
	 */
	public static void printLog() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next())
			{
				System.out.println("Id: " + resultSet.getInt("UserID")+","+resultSet.getInt("levelID")+","+resultSet.getInt("moves")+","+resultSet.getDate("time"));
			}
			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * this function returns the KML string as stored in the database (userID, level);
	 * @param id
	 * @param level
	 * @return
	 */
	public static String getKML(int id, int level) {
		String ans = null;
		String allCustomersQuery = "SELECT * FROM Users where userID="+id+";";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			if(resultSet!=null && resultSet.next()) {
				ans = resultSet.getString("kml_"+level);
			}
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
	public static int allUsers() {
		int ans = 0;
		String allCustomersQuery = "SELECT * FROM Users;";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);		
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next()) {
				System.out.println("Id: " + resultSet.getInt("UserID"));
				ans++;
			}
			resultSet.close();
			statement.close();		
			connection.close();
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}






	/** simply prints all the games as played by the users (in the database).
	 * 
	 */
	public static void ourLog(int id,SavingScore userBestResult[])
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while(resultSet.next())
			{
				if(resultSet.getInt("UserID")==id) 
				{
					count++;
					int levelID=resultSet.getInt("levelID");
					if(levelID>maxlevel)								
						maxlevel=levelID;
					double score=resultSet.getDouble("score");
					int moves=resultSet.getInt("moves");
					if(score>userBestResult[levelID].getScore()&&score>=data[levelID].getScore()&&moves<=data[levelID].getMoves())
						userBestResult[levelID]=new SavingScore(levelID,score,moves);
				}
			}
			resultSet.close();
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private static void initData() 
	{
		data[0]=new SavingScore(0,145,290);
		data[1]=new SavingScore(1,450,580);
		data[3]=new SavingScore(3,720,580);
		data[5]=new SavingScore(5,570,500);
		data[9]=new SavingScore(9,510,580);
		data[11]=new SavingScore(11,1050,580);
		data[13]=new SavingScore(13,310,580);
		data[16]=new SavingScore(16,235,290);
		data[19]=new SavingScore(19,250,580);
		data[20]=new SavingScore(20,200,290);
		data[23]=new SavingScore(23,1000,1140);
		for(int i=0;i<data.length;i++)
		{
			if(data[i]==null)
				data[i]=new SavingScore(i,0,Integer.MAX_VALUE);
		}
	}
	private static void printResult(SavingScore [] userBestResult) 
	{
		System.out.println("Number of game by the user: "+count);
		if(userBestResult[maxlevel].getScore()>0)
		{
			if(maxlevel==23)
				System.out.println("The user succeeded all the level");
			else
				System.out.println("Your max level is:"+(maxlevel+1));
		}
		else
			System.out.println("Your max level is:"+maxlevel);
		for(int i=0;i<userBestResult.length;i++) {
			if(userBestResult[i].getScore()>0)
				System.out.println(userBestResult[i].toStringLevelScore());
		}
	}
	public static void placeInClass(int id,SavingScore userBestResult[]) 
	{
		int levels[]= {0,1,3,5,9,11,13,16,19,20,23};
		for(int i=0;i<levels.length;i++)
		{
			System.out.println(PlaceInClassInLevel(levels[i],id,userBestResult[levels[i]]));
		}
	}

	private static String PlaceInClassInLevel(int level,int id,SavingScore userBestResult) 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);	
			HashMap <Integer,Double> betterScore=new HashMap <Integer,Double>();
			while(resultSet.next())
			{
				double score=resultSet.getDouble("score");
				int moves=resultSet.getInt("moves");
				if(resultSet.getInt("userID")!=id&&level==resultSet.getInt("levelID")) 
				{
					if(score>userBestResult.getScore()&&score>=data[level].getScore()&&moves<=data[level].getMoves()) {
						if(!betterScore.containsKey(resultSet.getInt("userID")))
						{
							betterScore.put(resultSet.getInt("userID"), resultSet.getDouble("score"));
						}
					}
				}
			}
			resultSet.close();
			statement.close();		
			connection.close();

			return "in level "+level+" your place is "+(betterScore.size()+1);
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "couldnt run the function";

	}
	public void StringForGUI(int level,int id1) 
	{
		initData();
		SavingScore userBestResult []=new SavingScore[24];
		for(int i=0;i<userBestResult.length;i++) {
			userBestResult[i]=new SavingScore(i,0,0);
		}
		ourLog(id1,userBestResult);
		printResult(userBestResult);
		placeInClass(id1,userBestResult);
	}
}

