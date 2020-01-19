package Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;

public class Graph_AlgoTest {
	static graph g=new DGraph();
	static Graph_Algo ga=new Graph_Algo();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		ga.init(g);
		for(int i=0;i<5;i++)
		{
			Point3D p=new Point3D(i,i,i);
			node_data a=new nodeData(p,0,null,0);
			g.addNode(a);
		}
		g.connect(0,1,2);
		g.connect(3,0,12);
		g.connect(0,3,12);
		g.connect(1,2,7);
		g.connect(1,4,5);
		g.connect(2,4,1);
		g.connect(4,3,3);
		g.connect(2,3,3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception 
	{

 	}

	@After
	public void tearDown() throws Exception 
	{
		
	}

	@Test
	public void testInitGraph() {
		ga.init(g);
	}

	@Test
	public void testInitString()
	{
		try
		{
		ga.save("graph.txt");
		ga.init("graph.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void testSave() {
		try
		{
			ga.save("graph.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testIsConnected() 
	{
		ga.init(g);
		assertTrue(ga.isConnected());
		g.removeEdge(0,1);
		assertFalse(ga.isConnected());
		g.connect(0, 1, 2);
	}

	@Test
	public void testShortestPathDist()
	{
		ga.init(g);
		assertEquals(10,ga.shortestPathDist(0, 3),0.01);
		g.removeEdge(0,1);
		g.removeEdge(0,3);
		assertEquals(Double.POSITIVE_INFINITY,ga.shortestPathDist(0, 3),10);
		g.connect(0, 1, 2);
		g.connect(0, 3, 12);
		assertEquals(19,ga.shortestPathDist(3, 4),0.01);
		g.connect(3, 4, 6);
		assertEquals(6,ga.shortestPathDist(3, 4),0.01);
		g.removeEdge(3, 4);
	}

	@Test
	public void testShortestPath() 
	{
		 List<Integer> expected=new ArrayList <Integer>();
		 expected.add(0);expected.add(1);expected.add(4);expected.add(3);
		 List<Integer> actual=new ArrayList <Integer>();
		 List<node_data> actualnode=ga.shortestPath(0,3);
		 for(int i=0;i<actualnode.size();i++)
		 {
			 actual.add(actualnode.get(i).getKey());
		 }
		 assertEquals(expected,actual);
		 
		 expected.remove(0);expected.remove(0);expected.remove(0);expected.remove(0);
		 actualnode.remove(0);actualnode.remove(0);actualnode.remove(0);actualnode.remove(0);
		 actual.remove(0);actual.remove(0);actual.remove(0);actual.remove(0);
		 expected.add(3);expected.add(0);expected.add(1);expected.add(4);
		 actualnode=ga.shortestPath(3,4);
		 for(int i=0;i<actualnode.size();i++)
		 {
			 actual.add(actualnode.get(i).getKey());
		 }
		 assertEquals(expected,actual);
		 
		 expected.remove(0);expected.remove(0);expected.remove(0);expected.remove(0);
		 actualnode.remove(0);actualnode.remove(0);actualnode.remove(0);actualnode.remove(0);
		 actual.remove(0);actual.remove(0);actual.remove(0);actual.remove(0);
		 expected=null;
	}

	@Test
	public void testTSP()
	{
		List<Integer> send=new ArrayList <Integer>();
		send.add(0);send.add(1);send.add(4);send.add(3);
		List<node_data> actualnode=new ArrayList  <node_data>();
		System.out.println(ga.isConnected());
		actualnode=ga.TSP(send);
		List<Integer> actual=new ArrayList <Integer>();
		for(int i=0;i<actualnode.size();i++)
		{
			actual.add(actualnode.get(i).getKey());
			System.out.println(actualnode.get(i).getKey());
		}

	}

	@Test
	public void testCopy() 
	{
		graph newG=ga.copy();		
		assertEquals(newG.getMC(),g.getMC());
	}
}
