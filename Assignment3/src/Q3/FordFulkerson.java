package Q3;

import java.util.*;
import java.io.File;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();

		// keep track of visited nodes
		HashMap<Integer, Boolean> visited = new HashMap<> ();
		ArrayList<Edge> edges = graph.getEdges ();

		if (findPath ( source, destination, edges, path, visited ))
			return path;
		else return new ArrayList<Integer> ();
	}

	private static boolean findPath(Integer source, Integer destination, ArrayList<Edge> edges, ArrayList<Integer> path, HashMap<Integer, Boolean> visited) {
		// set current node to discovered
		visited.put ( source, true );
		path.add ( source );

		if (source.equals ( destination ))
			return true;

		for (Edge edge : edges) {
			if (edge.nodes[0] != source)
				continue;
			if (!visited.containsKey ( edge.nodes[1] )) {
				if (findPath ( edge.nodes[1], destination, edges, path, visited))
					return true;
			}

		}
		path.remove ( path.size () - 1 );
		return false;
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
		 System.out.println(pathDFS (g.getSource (), g.getDestination (), g));
	 }
}

