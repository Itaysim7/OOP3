package gameClient;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;

public class KML_Logger implements Runnable{
	private int gameNumber;
	private List<Fruit> fruits;
	private List<Pacman> robots;
	private game_service game;
	private DGraph g;
	private String theRobots="";
	private String theFruits="";
	private String theGraph="";
	private createObjFromJson create;
	private long timeOfGame;
	private boolean firstRun=true;
	private String start="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" + 
			"  <Document>\r\n" ;
	private String icon="<Style id=\"paddle-a\">\r\n" + 
			"      <IconStyle>\r\n" + 
			"        <Icon>\r\n" + 
			"          <href>http://maps.google.com/mapfiles/kml/paddle/A.png</href>\r\n" + 
			"        </Icon>\r\n" + 
			"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" + 
			"      </IconStyle>\r\n" + 
			"    </Style>";
	private String end="</Document>\r\n" + 
			"</kml>";

	/**
	 * save the kml file 
	 * @param kml
	 * @throws IOException
	 */
	public void save(String kml) throws IOException {
		FileWriter fw = new FileWriter(gameNumber+".kml");  
		PrintWriter outs = new PrintWriter(fw);
		outs.println(kml);
		outs.close(); 
		fw.close();
	}
	/**
	 * make kml for the graph
	 */
	private void theGraph() {
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) {
			node_data nd=verIter.next();
			theGraph+="<Placemark>"+
					"<Style id=\"grn-blank\">\r\n" + 
					"      <IconStyle>\r\n" + 
					"        <Icon>\r\n" + 
					"          <href>http://maps.google.com/mapfiles/kml/paddle/grn-blank.png\r\n" + 
					"</href>\r\n" + 
					"        </Icon>\r\n" + 
					"      </IconStyle>\r\n" + 
					"    </Style>"+
					"<Point>\r\n" + 
					"      <coordinates>"+nd.getLocation().x()+","+nd.getLocation().y()+",0</coordinates>\r\n" + 
					"    </Point>"+
					"</Placemark>";
			try {
				for(Iterator<edge_data> edgeIter=g.getE(nd.getKey()).iterator();edgeIter.hasNext();) {
					edge_data ed=edgeIter.next();
					theGraph+="<name>polygon.kml</name>\r\n" + 
							"\r\n" + 
							"	<Style id=\"orange-5px\">\r\n" + 
							"		<LineStyle>\r\n" + 
							"			<color>ff00aaff</color>\r\n" + 
							"			<width>5</width>\r\n" + 
							"		</LineStyle>\r\n" + 
							"	</Style>\r\n" + 
							"\r\n" + 
							"\r\n" + 
							"	<Placemark>\r\n" + 
							"\r\n" + 
							"		<name>A polygon</name>\r\n" + 
							"		<styleUrl>#orange-5px</styleUrl>\r\n" + 
							"\r\n" + 
							"		<LineString>\r\n" + 
							"\r\n" + 
							"			<tessellate>1</tessellate>\r\n" + 
							"			<coordinates>\r\n" + 
							nd.getLocation().x()+","+nd.getLocation().y()+",0\r\n"+
							g.getNode(ed.getDest()).getLocation().x()+","+g.getNode(ed.getDest()).getLocation().y()+",0\r\n"+
							"			</coordinates>\r\n" + 
							"\r\n" + 
							"		</LineString>\r\n" + 
							"\r\n" + 
							"	</Placemark>";
				}
			}
			catch(Exception e) {}
		}

	}

	/** 
	 * The function paint the robots by the algorithm in a GUI window (just in the beginning of the game) 
	 * The algorithm choose for each robot the place of the source fruit with the biggest value
	 */
	private void addRobotToTheGameAuto()
	{
		GameServer gameSer=createObjFromJson.creatGameServer(game.toString());
		int countRobot=gameSer.getRobots();//number of robot in the game
		int countFruit=gameSer.getFruits();//number of fruit in the game
		int index=0;int v;
		while(countFruit>0&&countRobot>0)//put the robot near to the fruit
		{
			int typefruit=fruits.get(index).getType();
			if(typefruit==1)
				v=fruits.get(index).edge(g).getSrc();
			else
				v=fruits.get(index).edge(g).getDest();
			game.addRobot(v);
			countFruit--;countRobot--;index++;
		}
		//Create a list of the edge
		int numOfVer=g.nodeSize();
		int key[]=new int[numOfVer];
		int j=0;
		for(Iterator<node_data> verIter=g.getV().iterator();verIter.hasNext();) 
		{
			int point=verIter.next().getKey();
			key[j]=point;j++;
		}
		while(countRobot>0)	//if there is more robot than fruit put them in a random place
		{
			int random=(int)(Math.random()*key.length);
			game.addRobot(key[random]);
			countRobot--;
		}
	}
	/**
	 * init with the game number
	 */
	public void initTheGame() {
		game = Game_Server.getServer(gameNumber);
		g=new DGraph();
		g.init(game.getGraph());
		theGraph();
		create=new createObjFromJson(game);
		fruits=create.creatFruits();
		addRobotToTheGameAuto();
		robots=create.creatRobotsList();
		Thread t=new Thread(this);
		t.start();
	}
	/**
	 * while the game is running update the place of the robot and fruit and send to the ConverseToKml function to make the kml for every chang
	 * when the game end send to the save function to save the kml file with all we write.
	 */
	@Override
	public void run() {
		game.startGame();
		algoForGui a=new algoForGui(game, fruits, robots);

		while(game.isRunning())
		{
			a.update(game, fruits, robots);
			synchronized(this) 
			{
				if(this.firstRun) {
					if(game.timeToEnd()>30000) 
						this.timeOfGame=60000;
					else
						this.timeOfGame=30000;
					this.firstRun=false;
				}
				a.moveRobots();
				create.update(game);
				fruits=create.creatFruits();
				robots=create.creatRobotsList();
				for(int i=0;i<robots.size();i++) 
					theRobots+=robotConverseToKml(robots.get(i),game.timeToEnd());
				for(int i=0;i<fruits.size();i++) 
					theFruits+=fruitConverseToKml(fruits.get(i),game.timeToEnd());
			}
		}
		synchronized(this) 
		{
			String kml=start+icon+theGraph+theFruits+theRobots+end;
			try {
				save(kml);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * make kml for the fruit by the time
	 */
	public String robotConverseToKml(Pacman p,long timeToEnd) {
		long time=(this.timeOfGame-timeToEnd)/1000;

		String result="<Placemark>\r\n" + 
				"      <TimeSpan>\r\n" +
				"     <begin>"+time+"</begin>\r\n" + 
				"        <end>"+(time+1)+"</end>"+
				" </TimeSpan>\r\n" + 
				"<Style id=\"lodging\">\r\n" + 
				"      <IconStyle>\r\n" + 
				"        <Icon>\r\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/shapes/lodging.png\r\n" + 
				"</href>\r\n" + 
				"        </Icon>\r\n" + 
				"      </IconStyle>\r\n" + 
				"    </Style>"+
				"      <Point>\r\n" + 
				"        <coordinates>"+p.getPos().x()+","+p.getPos().y()+",0 </coordinates>\r\n" + 
				"      </Point>\r\n" + 
				"    </Placemark>";
		return result;
	}

	/**
	 * make kml for the fruit by the time
	 */
	public String fruitConverseToKml(Fruit f,long timeToEnd) {
		long time=(this.timeOfGame-timeToEnd)/1000;
		//		System.out.println(this.timeOfGame);
		//		System.out.println(timeToEnd);
		//		System.out.println(time);
		String result;
		if(f.getType()==1) {
			result="<Placemark>\r\n" + 
					"      <TimeSpan>\r\n" +
					"     <begin>"+time+"</begin>\r\n" + 
					"        <end>"+(time+1)+"</end>"+
					" </TimeSpan>\r\n" +  
					"<Style id=\"electronics\">\r\n" + 
					"      <IconStyle>\r\n" + 
					"        <Icon>\r\n" + 
					"          <href>http://maps.google.com/mapfiles/kml/shapes/electronics.png\r\n" + 
					"\r\n" + 
					"</href>\r\n" + 
					"        </Icon>\r\n" + 
					"      </IconStyle>\r\n" + 
					"    </Style>"+
					"      <Point>\r\n" + 
					"        <coordinates>"+f.getPos().x()+","+f.getPos().y()+",0 </coordinates>\r\n" + 
					"      </Point>\r\n" + 
					"    </Placemark>";
		}
		else {
			result="<Placemark>\r\n" + 
					"      <TimeSpan>\r\n" +
					"     <begin>"+time+"</begin>\r\n" + 
					"        <end>"+(time+1)+"</end>"+
					" </TimeSpan>\r\n" + 
					"<Style id=\"movies\">\r\n" + 
					"      <IconStyle>\r\n" + 
					"        <Icon>\r\n" + 
					"          <href>http://maps.google.com/mapfiles/kml/shapes/movies.png\r\n" + 
					"\r\n" + 
					"</href>\r\n" + 
					"        </Icon>\r\n" +  
					"      </IconStyle>\r\n" + 
					"    </Style>"+
					"      <Point>\r\n" + 
					"        <coordinates>"+f.getPos().x()+","+f.getPos().y()+",0 </coordinates>\r\n" + 
					"      </Point>\r\n" + 
					"    </Placemark>";
		}
		return result;
	}

	public void setGameNumber(int n) {gameNumber=n;}

	public static void main(String[] args) {
		for(int i=0;i<24;i++) {
			KML_Logger kmll= new KML_Logger();
			kmll.setGameNumber(i);
			kmll.initTheGame();
		}
		//		KML_Logger kmll= new KML_Logger();
		//		kmll.setGameNumber(0);
		//		kmll.initTheGame();

	}


}
