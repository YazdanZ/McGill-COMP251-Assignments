import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		// keep track of visited nodes using a hashmap
		HashMap<Integer, Boolean> visited = new HashMap<> ();

		// pass the edges to the recursive function
		ArrayList<Edge> edges = graph.getEdges ();

		Stack<Integer> validPath = findPath ( source, destination, edges, visited );

		// is size of the path is 0 or 1, there are no valid paths
		if (validPath.size () > 1)
			return new ArrayList<> (validPath);
		return new ArrayList<> ();
	}

	private static Stack<Integer> findPath ( Integer source, Integer destination, ArrayList<Edge> edges, HashMap<Integer, Boolean> visited ) {
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
			if (u != source || edge.weight <= 0)
				continue;

			// check is node has been previously discovered
			if (!visited.containsKey ( v ) || !visited.get ( v )) {

				// solve sub problem recursively
				Stack<Integer> subPath = findPath ( v, destination, edges, visited );

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

