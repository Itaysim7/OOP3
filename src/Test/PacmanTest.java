package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import obj.Pacman;
import utils.Point3D;

class PacmanTest {

	static game_service game;
	static Pacman p;
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
		game.addRobot(0);
		cofj =new createObjFromJson(game);
		p=new Pacman(1,1,1,2,1,"3,3,0");
		g=new DGraph();
		g.init(game.getGraph());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPacman() {
		Pacman p1=new Pacman(1,1,1,2,1,"3,3,0");
		assertNotNull(p1);
	}

	@Test
	void testGetId() {
		assertEquals(p.getId(),1);
	}

	@Test
	void testGetValue() {
		assertEquals(p.getValue(),1);
	}

	@Test
	void testGetSrc() {
		assertEquals(p.getSrc(),1);
	}

	@Test
	void testGetDest() {
		assertEquals(p.getDest(),2);
	}

	@Test
	void testGetSpeed() {
		assertEquals(p.getSpeed(),1);
	}

	@Test
	void testGetPos() {
		Point3D expected=new Point3D("3,3,0");
		assertEquals(p.getPos(),expected);
	}

	@Test
	void testSetSrc() {
		p.setSrc(3);
		assertEquals(p.getSrc(),3);
	}

	@Test
	void testSetDest() {
		p.setDest(4);
		assertEquals(p.getDest(),4);
	}

	@Test
	void testSetPos() {
		Point3D expected=new Point3D("3,4,0");
		p.setPos(expected);
		assertEquals(p.getPos(),expected);
		
	}

	@Test
	void testEdge() {
		List <Pacman> p1=cofj.creatRobotsList();
		p=p1.get(0);
		assertEquals(p.edge(g).getSrc(),10);
		assertEquals(p.edge(g).getDest(),0);
		
	}

}
