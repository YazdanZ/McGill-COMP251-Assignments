package Q3;

import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		// keep track of visited nodes using a hashmap
		HashMap<Integer, Boolean> visited = new HashMap<> ();

		// pass the edges to the recursive function
		ArrayList<Edge> edges = graph.getEdges ();

		Stack<Integer> validPath = findPath ( source, destination, visited, edges );

		// is size of the path is 0 or 1, there are no valid paths
		if (validPath.size () > 1)
			return new ArrayList<> (validPath);
		return new ArrayList<> ();
	}

	private static Stack<Integer> findPath ( Integer source, Integer destination, HashMap<Integer, Boolean> visited, ArrayList<Edge> edges ) {
		Stack<Integer> path = new Stack<> ();

		// push current node
		path.push ( source );

		// set current node to discovered
		visited.put ( source, true );

		// stop if we reach destination (base case)
		if (source.equals ( destination ))
			return path;

		for (Edge edge : edges) {
			int u = edge.nodes[0]; // parent node
			int v = edge.nodes[1]; // child node (new source node)

			/* since we are not provided an adj list, we search
			 through all edges to find edges of the source node */
			if (u != source)
				continue;

			// check is node has been previously discovered
			if (!visited.containsKey ( v ) || !visited.get ( v )) {

				// solve sub problem recursively
				Stack<Integer> subPath = findPath ( v, destination, visited, edges );

				if (!subPath.isEmpty () && subPath.get ( subPath.size () - 1 ).equals ( destination )) {
					path.addAll ( subPath );
					return path;
				}
			}
		}
		path.pop ();
		return path;
	}


	public static String fordfulkerson( WGraph graph){
		int maxFlow = 0;

		// clone the graph
		WGraph residualGraph = new WGraph (graph);

		ArrayList<Integer> augmentingPath = pathDFS ( graph.getSource (), graph.getDestination (), residualGraph);

		// flow is 0 if source is destination or if no path exists
		if (augmentingPath.size () == 0 || graph.getSource () == graph.getDestination ())
			return answer ( maxFlow, graph );

		// while augmenting path exists
		while (augmentingPath.size () > 0) {

			// find maximum flow of the path
			int bottleNeck = getBottleNeckFlow ( residualGraph, augmentingPath );
			maxFlow += bottleNeck; // update max flow

			updateResidualGraph ( augmentingPath, residualGraph, bottleNeck );

			augmentingPath = pathDFS ( graph.getSource (), graph.getDestination (), residualGraph);
		}
		buildOriginalGraph ( graph, residualGraph );
		return answer ( maxFlow, graph );
	}

	public static int getBottleNeckFlow(WGraph residualGraph, ArrayList<Integer> augmentingPath){

		ArrayList<Edge> edges = new ArrayList<> ();

		for (int i = 0; i < augmentingPath.size () - 1; i++) {
			edges.add ( residualGraph.getEdge ( augmentingPath.get ( i ), augmentingPath.get ( i + 1 ) ) );
		}

		edges.sort ( ( e1, e2 ) -> e2.weight.compareTo ( e1.weight ) );
		return edges.get ( edges.size () - 1 ).weight;
	}

	private static void updateResidualGraph(ArrayList<Integer> augmentingPath, WGraph residualGraph, int bottleNeck) {
		// update residual capacities
		for (int i = 0; i < augmentingPath.size () - 1; i++) {
			int u = augmentingPath.get ( i );
			int v = augmentingPath.get ( i + 1 );

			// update forward edge weights
			residualGraph.getEdge ( u, v ).weight -= bottleNeck;

			// create backward edge if it does not exists
			if (residualGraph.getEdge ( v, u ) == null)
				residualGraph.addEdge ( new Edge ( v, u, 0 ) );

			// update backward edge weight
			residualGraph.getEdge ( v, u ).weight += bottleNeck;

			if (residualGraph.getEdge ( u, v ).weight == 0)
				residualGraph.getEdges ().remove ( residualGraph.getEdge ( u, v ) );

			if (residualGraph.getEdge (v, u).weight == 0)
				residualGraph.getEdges ().remove ( residualGraph.getEdge ( v, u ) );
		}
	}

	private static void buildOriginalGraph(WGraph originalGraph, WGraph residualGraph) {
		ArrayList<Edge> originalGraphEdges = originalGraph.getEdges ();
		for (Edge originalGraphEdge : originalGraphEdges) {
			int u = originalGraphEdge.nodes[0];
			int v = originalGraphEdge.nodes[1];

			Edge residualEdge = residualGraph.getEdge ( u, v );
			if (residualEdge == null) continue;
			originalGraphEdge.weight -= residualEdge.weight;
		}

	}

	private static String answer ( int maxFlow, WGraph graph) {
		String answer = "";
		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}
	

	 public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	    //System.out.println (pathDFS ( g.getSource (), g.getDestination (), g ));
	 }
}

