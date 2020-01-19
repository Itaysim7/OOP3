package Testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.createObjFromJson;
import obj.Fruit;
import obj.GameServer;
import obj.Pacman;

class CreateTest {
	static game_service game;
	static createObjFromJson cofj;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		 game=Game_Server.getServer(0);
		 cofj =new createObjFromJson(game);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void testCreateObjFromJson() {
        cofj =new createObjFromJson(game);
		assertNotNull(cofj);
	}

	@Test
	public void testUpdate() {
		List<Fruit> before=cofj.creatFruits();
		game_service game1=Game_Server.getServer(1);
		cofj.update(game1);
		List<Fruit> after=cofj.creatFruits();
		assertNotEquals(before,after);
	}

	@Test
	public void testCreatFruits() {
		List<Fruit> f=cofj.creatFruits();
		assertNotNull(f);
	}

	@Test
	public void testCreatGameServer() {
		GameServer gs =cofj.creatGameServer(game.toString());
		assertNotNull(gs);
		
	}

	@Test
	public void testCreatRobotsList() {
		List <Pacman> p=cofj.creatRobotsList();
		assertNotNull(p);
	}

}
