package gameClient;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import Server.game_service;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;

public class createObjFromJson 
{
	private game_service game;
	
	public createObjFromJson(game_service game1) 
	{
		this.game=game1;
	}
	/**
	 * Update the game 
	 * @param game1
	 */
	public void update(game_service game1) 
	{
		this.game=game1;
	}

	///////for fruits////
	/**
	 * Create a list of fruits from the game
	 * @return the update List of fruits
	 */
	public List<Fruit> creatFruits()
	{
		List<Fruit> fruitstemp=new ArrayList<Fruit>();
		Iterator<String> f_iter=game.getFruits().iterator();
		while(f_iter.hasNext())
		{
			String fruit=f_iter.next();
			fruit=fruit.substring(9,fruit.length()-1);
			Fruit f=creatFruit(fruit);
			fruitstemp.add(f);//add to the list of fruit
		}
		//sort the array by value
		for(int i=0;i<fruitstemp.size();i++)
		{
			for(int j=fruitstemp.size()-1;j>i;j--)
			{
				if(fruitstemp.get(j).getValue()>fruitstemp.get(j-1).getValue())
				{
					Fruit temp=fruitstemp.get(j);
					fruitstemp.set(j, fruitstemp.get(j-1));
					fruitstemp.set(j-1, temp);
				}
			}
		}
		return fruitstemp;
	}
	/**
	 * The function create object of Fruit From at string of json
	 * @return Fruit 
	 * @param str - string type if json
	 */
	private Fruit creatFruit(String str) 
	{	
		Gson gson = new Gson();
		try
		{
			Fruit f=gson.fromJson(str, Fruit.class);
			return f;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for fruit");
		}
	}
	
	////for game server///////////
	
	/**
	 * The function create object of GameServer From at string of json
	 * @return GameServer 
	 * @param str - string type if json
	 */
	public static  GameServer creatGameServer(String str) 
	{	
		str=str.substring(14,str.length()-1);
		Gson gson = new Gson();
		try
		{
			GameServer game=gson.fromJson(str, GameServer.class);
			return game;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for GameServe");
		}
	}
	
	
	////for pacman///////////
	/**
	 * The function create object of Pacman From at string of json
	 * @return Pacman 
	 * @param str - string type if json
	 */
	private  Pacman creatRobot(String str) 
	{	
		str=str.substring(9,str.length()-1);
		Gson gson = new Gson();
		try
		{
			Pacman rob=gson.fromJson(str, Pacman.class);
			return rob;
		} 
		catch ( JsonSyntaxException  e) //default value for unreadable file
		{
			throw new RuntimeException("wrong format for Robot");
		}
	}
	/**
	 * Create a list of Pacman from the game
	 * @return the update List of Pacman
	 */
	public List<Pacman> creatRobotsList()
	{
		List<String> rob=game.getRobots();
		List<Pacman>robotemp=new ArrayList<Pacman>();
		for(int i=0;i<rob.size();i++)
		{
			Pacman itay = creatRobot(rob.get(i));
			robotemp.add(itay);
		}
		return robotemp;
	}
}
