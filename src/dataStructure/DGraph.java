package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Point3D;

public class DGraph implements graph, Serializable
{
    HashMap<Integer,node_data> vertices; 
    HashMap<Integer, HashMap<Integer,edge_data> > edges; //integer is the vertices and the arraylist save his edge
    int count;
    int mc;

    public DGraph() 
	{
    	vertices=new HashMap<Integer,node_data>();
    	edges=new HashMap<Integer, HashMap<Integer,edge_data> > ();
    	this.count=0;
    	this.mc=0;
	}
    public void init(String data)
    {
    	try {
		 	JSONObject graph = new JSONObject(data);
            JSONArray nodes = graph.getJSONArray("Nodes");
            JSONArray edges = graph.getJSONArray("Edges");

            int i;
            int s;
            for(i = 0; i < nodes.length(); ++i) {
                s = nodes.getJSONObject(i).getInt("id");
                String pos = nodes.getJSONObject(i).getString("pos");
                Point3D p = new Point3D(pos);
                this.addNode(new nodeData(s, p));
            }

            for(i = 0; i < edges.length(); ++i) {
                s = edges.getJSONObject(i).getInt("src");
                int d = edges.getJSONObject(i).getInt("dest");
                double w = edges.getJSONObject(i).getDouble("w");
                this.connect(s, d, w);
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }
    }
	/**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
	@Override
	public node_data getNode(int key)
	{
		if(vertices.containsKey(key))
		{
			return vertices.get(key);
		}
		else
			return null;
	}
	/**
	 * return the data of the edge (src,dest), null if none.
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return
	 */
	@Override
	public edge_data getEdge(int src, int dest)
	{
		if(edges.containsKey(src)&&edges.get(src).containsKey(dest))
		{
			return edges.get(src).get(dest);
		}
		else
			return null;
	}
	/**
	 * add a new node to the graph with the given node_data.
	 * Note: this method should run in O(1) time.
	 * @param n
	 */
	@Override
	public void addNode(node_data n) 
	{
			this.vertices.put( n.getKey(),n);
			mc++;
	}
	/**
	 * Connect an edge with weight w between node src to node dest.
	 * * Note: this method should run in O(1) time.
	 * @param src - the source of the edge.
	 * @param dest - the destination of the edge.
	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
	 */
	@Override
	public void connect(int src, int dest, double w)
	{
		if(src!=dest&&w>=0)//check if the edge is between different vertices and the weight is positive 
		{
			if(vertices.containsKey(src)&&vertices.containsKey(dest))//check if there is vertices src dest
			{
				if(!edges.containsKey(src)) //check if there is a hashmap for key src
				{
					HashMap<Integer, edge_data> edgesVer=new HashMap<Integer,edge_data> ();
					edges.put(src, edgesVer);
				}
				if(!edges.get(src).containsKey(dest))//check if the edge is already exist
				{
					count++;
					edges.get(src).remove(dest);
				}
				edge_data temp=new edgeData(src,dest,w,null,0);
				this.edges.get(src).put(dest, temp);
				mc++;
			}
		}
	}
	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * Note: this method should run in O(1) time.
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_data> getV()
	{
		return this.vertices.values();
	}
	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 * Note: this method should run in O(1) time.
	 * @return Collection<edge_data>
	 */
	@Override
	public Collection<edge_data> getE(int node_id) 
	{
//		if(edges.containsKey(node_id))
//		{
			return this.edges.get(node_id).values();
//		}
//		return null;
	}
	/**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method should run in O(n), |V|=n, as all the edges should be removed.
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */
	@Override
	public node_data removeNode(int key) 
	{
		if(vertices.containsKey(key)) //check if there is such vertex
		{
			int keyTemp;
			for (Entry<Integer, node_data> entry : vertices.entrySet())
			{
				keyTemp=entry.getKey();
				if(keyTemp!=key&&edges.containsKey(keyTemp) &&edges.get(keyTemp).containsKey(key))
				{
					edges.get(keyTemp).remove(key);
					count--;
					mc++;
				}
				if(keyTemp==key&&edges.containsKey(key))
				{
					mc=mc+edges.get(key).size();
					count=count-edges.get(key).size();
					edges.remove(key);
				}
			}
			mc++;
			return vertices.remove(key);
		}
		return null;
		
	}
	/**
	 * Delete the edge from the graph, 
	 * Note: this method should run in O(1) time.
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
	@Override
	public edge_data removeEdge(int src, int dest) 
	{
		if(vertices.containsKey(src)&&vertices.containsKey(dest))
		{
			if(edges.containsKey(src)&&edges.get(src).containsKey(dest))
			{
				mc++;
				count--;
				return edges.get(src).remove(dest);
			}
			else 
				return null;
		}
		else
			return null;
	}
	/** return the number of vertices (nodes) in the graph.
	 * Note: this method should run in O(1) time. 
	 * @return
	 */
	@Override
	public int nodeSize() 
	{
		return vertices.size();
	}
	/** 
	 * return the number of edges (assume directional graph).
	 * Note: this method should run in O(1) time.
	 * @return
	 */
	@Override
	public int edgeSize() 
	{
		return count;
	}
	/**
	 * return the Mode Count - for testing changes in the graph.
	 * @return
	 */
	@Override
	public int getMC() 
	{
		return mc;
	}
}
