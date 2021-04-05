package Q3;

import java.util.*;
import java.io.File;

public class FordFulkerson {
	private static boolean foundPath = false;

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		// keep track of visited nodes using this hashmap
		HashMap<Integer, Boolean> visited = new HashMap<> ();

		// pass the edges to the recursive function
		ArrayList<Edge> edges = graph.getEdges ();

		ArrayList<Integer> validPath = findPath ( source, destination, edges, new ArrayList<> (), visited );

		// is size of the path is 0 or 1, there are no valid paths
		if (validPath.size () > 1)
			return validPath;
		return new ArrayList<> ();
	}

	private static ArrayList<Integer> findPath(Integer source, Integer destination, ArrayList<Edge> edges, ArrayList<Integer> path, HashMap<Integer, Boolean> visited) {
		path = new ArrayList<> ();
		path.add ( source );

		// set current node to discovered
		visited.put ( source, true );

		if (source.equals ( destination ))
			return path;

		for (Edge edge : edges) {
			if (edge.nodes[0] != source || edge.weight <= 0)
				continue;

			if (!visited.containsKey ( edge.nodes[1] ) || !visited.get ( edge.nodes[1] )) {
				ArrayList<Integer> smallPath = findPath ( edge.nodes[1], destination, edges, path, visited );
				if (!smallPath.isEmpty () && smallPath.contains ( destination )) {
					path.addAll ( smallPath );
				}
			}
			if (path.contains ( destination ))
				return path;
		}
		return new ArrayList<> ();
	}


	public static String fordfulkerson( WGraph graph){
		String answer="";
		int maxFlow = 0;

		// clone the graph
		WGraph residualGraph = new WGraph (graph);


		// while augmenting path exists
		while (pathDFS ( graph.getSource (), graph.getDestination (), residualGraph ).size () > 0) {
			int flowCapacity = Integer.MAX_VALUE;

		}



		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}
	

	 public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	    System.out.println (pathDFS ( g.getSource (), g.getDestination (), g ));
	 }
}

