package Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gameClient.createObjFromJson;
import obj.Fruit;
import utils.Point3D;

class FruitsTest {
	static game_service game;
	static Fruit f;
	static DGraph g;
	static createObjFromJson cofj;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		game=Game_Server.getServer(1);
		cofj =new createObjFromJson(game);
		f=new Fruit (1,1,"3,3,0");
		g=new DGraph();
		g.init(game.getGraph());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testFruit() {
		Fruit f1=new Fruit (1,1,"3,3,0");
		assertNotNull(f1);
	}

	@Test
	public void testGetValue() {
		double v=f.getValue();
		double expected=1.0;
		assertEquals(expected,v,0.0001);
	}

	@Test
	public void testGetType() {
		int t=f.getType();
		int expected=1;
		System.out.println(t);
		System.out.println(expected);
		assertEquals(expected,t);
	}

	@Test
	public void testGetPos() {
		Point3D p=f.getPos();
		Point3D expected=new Point3D("3,3,0");
		assertEquals(expected,p);
		
	}

	@Test
	public void testSetPos() {
		Point3D expected=new Point3D("3,4,0");
		f.setPos(expected);
		Point3D p=f.getPos();
		assertEquals(expected,p);
	}

	@Test
	public void testEdge() {
		f=cofj.creatFruits().get(0);
		assertEquals(f.edge(g).getSrc(),3);
		assertEquals(f.edge(g).getDest(),4);
		
	}

}
