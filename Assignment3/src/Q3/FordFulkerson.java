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

		// clone the original graph
		WGraph residualGraph = new WGraph (graph);

		// find an augmenting path
		ArrayList<Integer> augmentingPath = pathDFS ( graph.getSource (), graph.getDestination (), residualGraph);

		// flow is 0 if source is destination or if no path exists
		if (augmentingPath.isEmpty () || graph.getSource () == graph.getDestination ())
			return answer ( maxFlow, graph );

		// while augmenting path exists
		while (!augmentingPath.isEmpty ()) {
			// find maximum flow of the path
			int bottleNeck = getBottleNeckFlow ( residualGraph, augmentingPath );
			maxFlow += bottleNeck; // update max flow

			updateResidualGraph ( augmentingPath, residualGraph, bottleNeck );
			augmentingPath = pathDFS ( graph.getSource (), graph.getDestination (), residualGraph);
		}

		rebuildOriginalGraph ( graph, residualGraph );
		return answer ( maxFlow, graph );
	}

	/**
	 * Find bottle neck flow in the specified path of the residual graph
	 * @param residualGraph
	 * @param augmentingPath
	 * @return bottle neck flow
	 */
	public static int getBottleNeckFlow(WGraph residualGraph, ArrayList<Integer> augmentingPath){

		ArrayList<Edge> edges = new ArrayList<> ();

		for (int i = 0; i < augmentingPath.size () - 1; i++) {
			edges.add ( residualGraph.getEdge ( augmentingPath.get ( i ), augmentingPath.get ( i + 1 ) ) );
		}

		edges.sort ( ( e1, e2 ) -> e2.weight.compareTo ( e1.weight ) );
		return edges.get ( edges.size () - 1 ).weight;
	}

	/**
	 * Update the residual graph using the bottle neck value
	 * backward edges are also created if they don't already
	 * exit. We also remove edges with a weight of zero as
	 * pathDFS() also considers edges with a weight of zero.
	 *
	 * @param augmentingPath
	 * @param residualGraph
	 * @param bottleNeck
	 */
	private static void updateResidualGraph(ArrayList<Integer> augmentingPath, WGraph residualGraph, int bottleNeck) {
		// update residual capacities
		for (int i = 0; i < augmentingPath.size () - 1; i++) {
			int u = augmentingPath.get ( i );
			int v = augmentingPath.get ( i + 1 );

			Edge forwardEdge = residualGraph.getEdge ( u, v );
			Edge backwardEdge = residualGraph.getEdge ( v, u );

			// update forward edge weights
			forwardEdge.weight -= bottleNeck;

			// create backward edge if it does not exists
			if (backwardEdge == null) {
				residualGraph.addEdge ( new Edge ( v, u, 0 ) );
				backwardEdge = residualGraph.getEdge ( v, u );
			}

			// update backward edge weight
			residualGraph.getEdge ( v, u ).weight += bottleNeck;

			if (forwardEdge.weight == 0)
				residualGraph.getEdges ().remove ( forwardEdge );

			if (backwardEdge.weight == 0)
				residualGraph.getEdges ().remove ( backwardEdge );
		}
	}

	/**
	 * Update the flows in edges of the original graph
	 * by subtracting the edge capacity by the remaining
	 * capacity of the corresponding edge in the residual
	 * graph
	 *
	 * @param originalGraph
	 * @param residualGraph
	 */
	private static void rebuildOriginalGraph ( WGraph originalGraph, WGraph residualGraph) {
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

