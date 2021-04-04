import java.util.*;
import java.io.File;

public class FordFulkerson {
	private static boolean foundPath = false;

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();

		// keep track of visited nodes
		HashMap<Integer, Boolean> visited = new HashMap<> ();
		ArrayList<Edge> edges = graph.getEdges ();

		path.add ( source );
		findPath ( source, destination, edges, path, visited );

		// if the path contains only the source node, no path exists
		if (path.size () > 1)
			return path;
		else return new ArrayList<> ();
	}

	private static void findPath(Integer source, Integer destination, ArrayList<Edge> edges, ArrayList<Integer> path, HashMap<Integer, Boolean> visited) {

		// set current node to discovered
		visited.put ( source, true );

		if (source.equals ( destination )) {
			// a path is found
			visited.put ( source, false );
			foundPath = true;
			return;
		}

		for (Edge edge : edges) {
			if (edge.nodes[0] != source)
				continue;
			if (!visited.containsKey ( edge.nodes[1] ) || !visited.get ( edge.nodes[1] )) {
				path.add ( edge.nodes[1] );
				findPath ( edge.nodes[1], destination, edges, path, visited );

				// get out of all recursive calls as soon as a path is found
				if (foundPath) return;
				path.remove ( Integer.valueOf ( edge.nodes[1] ) );
			}
		}
		visited.put ( source, false );
	}


	public static String fordfulkerson( WGraph graph){
		String answer="";
//		int maxFlow = 0;
//
//		/* YOUR CODE GOES HERE		*/
//
//		answer += maxFlow + "\n" + graph.toString();
		return answer;
	}
	

	 public static void main(String[] args){
		String file = args[0];
		File f = new File(file);
		WGraph g = new WGraph(file);
	    System.out.println(fordfulkerson(g));
	 }
}

