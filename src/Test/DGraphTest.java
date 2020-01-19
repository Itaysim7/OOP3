package Test;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.nodeData;
import dataStructure.node_data;
import utils.Point3D;

public class DGraphTest 
{
	graph g;
	Point3D p0,p1,p2,p3,p4,p5,p6,p7;
	node_data v0,v1,v2,v3,v4,v5,v6;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{

	}

	@AfterClass
	public static  void tearDownAfterClass() throws Exception 
	{
		
	}

	@Before
	public void setUp() throws Exception 
	{
		g=new DGraph();
		p0=new Point3D(0,0,0);
		p1=new Point3D(1,1,1);
		p2=new Point3D(-1,-1,-1);
		p3=new Point3D(2,5,0);
		p4=new Point3D(-3,3,0);
		p5=new Point3D(3,-3,0);
		p6=new Point3D(-3,-3,0);
		v0=new nodeData(p0,0,null,0);
		v1=new nodeData(p1,0,null,0);
		v2=new nodeData(p2,0,null,0);
		v3=new nodeData(p3,0,null,0);
		v4=new nodeData(p4,0,null,0);
		v5=new nodeData(p5,0,null,0);
		v6=new nodeData(p6,0,null,0);
		g.addNode(v0);
		g.addNode(v1);
		g.addNode(v2);
		g.addNode(v3);
		g.addNode(v4);
		g.addNode(v5);
		g.addNode(v6);
		g.connect(v0.getKey(),v2.getKey(),0);
		g.connect(v3.getKey(),v4.getKey(),0);
		g.connect(v1.getKey(),v4.getKey(),0);
		g.connect(v3.getKey(),v2.getKey(),0);
		g.connect(v6.getKey(),v0.getKey(),0);
	}

	@After
	public void tearDown() throws Exception 
	{
		g.removeEdge(v0.getKey(),v2.getKey());
		g.removeEdge(v3.getKey(),v4.getKey());
		g.removeEdge(v1.getKey(),v4.getKey());
		g.removeEdge(v3.getKey(),v2.getKey());
		g.removeEdge(v6.getKey(),v0.getKey());

	}
	@Test
	public void testGetNode()
	{
		graph g=new DGraph();
		node_data v0=new nodeData(new Point3D(0,0,0),0,null,0);
		node_data v1=new nodeData(new Point3D(1,1,1),0,null,0);
		node_data v2=new nodeData(new Point3D(1,1,1),0,null,0);
		g.addNode(v0);
		g.addNode(v1);
		node_data v3=g.getNode(v2.getKey());
		assertEquals(g.getNode(v0.getKey()),v0);
		assertEquals(g.getNode(v1.getKey()),v1);
		assertEquals(null,v3);
	}

	@Test
	public void testGetEdge() 
	{
		graph g=new DGraph();
		node_data v0=new nodeData(new Point3D(0,0,0),0,null,0);
		node_data v1=new nodeData(new Point3D(1,1,1),0,null,0);
		node_data v2=new nodeData(new Point3D(2,2,2),0,null,0);
		node_data v3=new nodeData(new Point3D(-1,-2,-2),0,null,0);
		edge_data e1=g.getEdge(v0.getKey(), v1.getKey());//v0,v1 are not in the graph
		assertEquals(null,e1);
		g.addNode(v0);g.addNode(v1);g.addNode(v2);g.addNode(v3);
		e1=g.getEdge(v0.getKey(), v1.getKey());//v0,v1 are not connected
		assertEquals(null,e1);
		g.connect(v0.getKey(),v1.getKey(), 0);
		g.connect(v2.getKey(),v3.getKey(), 0);
		assertEquals(g.getEdge(v0.getKey(),v1.getKey()).getSrc(),v0.getKey());
		assertEquals(g.getEdge(v0.getKey(), v1.getKey()).getDest(),v1.getKey());
		assertEquals(g.getEdge(v2.getKey(),v3.getKey()).getSrc(),v2.getKey());
		assertEquals(g.getEdge(v2.getKey(),v3.getKey()).getDest(),v3.getKey());
	}

	@Test
	public void testAddNode() 
	{
		assertEquals(g.getNode(v0.getKey()),v0);
		assertEquals(g.getNode(v1.getKey()),v1);
		assertEquals(g.getNode(v2.getKey()),v2);
	}

	@Test
	public void testConnect() 
	{
		graph g=new DGraph();
		nodeData v0=new nodeData(new Point3D(-3,3,0),0,null,0);
		nodeData v1=new nodeData(new Point3D(-3,-3,0),0,null,0);
		nodeData v2=new nodeData(new Point3D(3,-3,0),0,null,0);
		nodeData v3=new nodeData(new Point3D(3,3,0),0,null,0);
		g.addNode(v0);g.addNode(v1);g.addNode(v2);g.addNode(v3);
		g.connect(v0.getKey(),v1.getKey(),0);g.connect(v1.getKey(),v2.getKey(),0);g.connect(v2.getKey(),v3.getKey(),0);g.connect(v3.getKey(),v0.getKey(),0);
		assertEquals(g.getEdge(v0.getKey(),v1.getKey()).getSrc(),v0.getKey());
		assertEquals(g.getEdge(v0.getKey(),v1.getKey()).getDest(),v1.getKey());
		assertEquals(g.getEdge(v1.getKey(),v2.getKey()).getSrc(),v1.getKey());
		assertEquals(g.getEdge(v1.getKey(),v2.getKey()).getDest(),v2.getKey());
		assertEquals(g.getEdge(v2.getKey(),v3.getKey()).getSrc(),v2.getKey());
		assertEquals(g.getEdge(v2.getKey(),v3.getKey()).getDest(),v3.getKey());
		assertEquals(g.getEdge(v3.getKey(),v0.getKey()).getSrc(),v3.getKey());
		assertEquals(g.getEdge(v3.getKey(),v0.getKey()).getDest(),v0.getKey());
	}

//	@Test
//	public void testGetV()
//	{
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetE() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testRemoveNode() 
	{
		v0=g.removeNode(v0.getKey());
		v1=g.removeNode(v1.getKey());
		v3=g.removeNode(v3.getKey());
		node_data v7=g.removeNode(v0.getKey());
		assertEquals(v7,null);
		assertEquals(g.nodeSize(),4);
		assertEquals(g.edgeSize(),0);
		g.addNode(v0);g.addNode(v1);g.addNode(v3);
		g.connect(v0.getKey(),v2.getKey(),0);g.connect(v3.getKey(),v4.getKey(),0);g.connect(v1.getKey(),v4.getKey(),0);g.connect(v3.getKey(),v2.getKey(),0);g.connect(v6.getKey(),v0.getKey(),0);
	}

	@Test
	public void testRemoveEdge() 
	{
		g.removeEdge(v0.getKey(),v2.getKey());
		g.removeEdge(v3.getKey(),v4.getKey());
		g.removeEdge(v1.getKey(),v4.getKey());
		g.removeEdge(v3.getKey(),v2.getKey());
		g.removeEdge(v6.getKey(),v0.getKey());
		assertEquals(g.edgeSize(),0);
		g.removeEdge(v0.getKey(),v2.getKey());
		edge_data e1=g.removeEdge(v0.getKey(),v2.getKey());//there is such edge
		assertEquals(e1,null);
		v0=g.removeNode(v0.getKey());
		edge_data e2=g.removeEdge(v0.getKey(),v2.getKey());//there is no such src vertex
		edge_data e3=g.removeEdge(v6.getKey(),v0.getKey());//there is no such dest vertex
		assertEquals(e2,null);
		assertEquals(e3,null);
		g.addNode(v0);
		g.connect(v0.getKey(),v2.getKey(),0);g.connect(v3.getKey(),v4.getKey(),0);g.connect(v1.getKey(),v4.getKey(),0);g.connect(v3.getKey(),v2.getKey(),0);g.connect(v6.getKey(),v0.getKey(),0);
	}
	@Test
	public void testNodeSize() 
	{
		assertEquals(g.nodeSize(),7);
		v0=g.removeNode(v0.getKey());
		assertEquals(g.nodeSize(),6);
		g.addNode(v0);
	}

	@Test
	public void testEdgeSize()
	{
		v0=g.removeNode(v0.getKey());
		assertEquals(g.edgeSize(),3);
		g.removeEdge(v3.getKey(),v4.getKey());
		g.removeEdge(v1.getKey(),v4.getKey());
		g.removeEdge(v3.getKey(),v2.getKey());
		assertEquals(g.edgeSize(),0);
		g.addNode(v0);
		g.connect(v0.getKey(),v2.getKey(),0);g.connect(v3.getKey(),v4.getKey(),0);g.connect(v1.getKey(),v4.getKey(),0);g.connect(v3.getKey(),v2.getKey(),0);g.connect(v6.getKey(),v0.getKey(),0);
		}
//
//	@Test
//	public void testGetMC() {
//		fail("Not yet implemented");
//	}

}
