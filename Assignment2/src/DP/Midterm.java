import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;
	

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i=0; i< chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}

	public static int lost_marks(int[] penalization) {
		int n = penalization.length;
		dp_table = new int[n - 1][n]; // rows = jumps, cols = seats

		ArrayList<Integer> scores = new ArrayList<> ();
		for (int lastJump = n - 1; lastJump > 0; lastJump--) {
			int score = recursion ( n - 1, lastJump, penalization );
			scores.add ( score );
		}
		return Collections.min ( scores );
	}

	private static int recursion ( int seat, int jump, int[] penalization) {
		// if the seat is out of bounds
		// return a negative penalization to distinguish between a valid jump and an invalid one
		if (seat >= penalization.length || seat < 0)
			return 100000;

		// impossible paths
		if (seat != 0 && jump == 0)
			return 100000;

		// reaching the starting point
		if (seat == 0 && jump == 0)
			return 0;

		int pen = penalization[seat];

		int forwardScore = recursion ( seat - jump, jump - 1, penalization );
		int backwardScore = recursion ( seat + jump, jump, penalization );

		return Math.min ( forwardScore, backwardScore ) + pen;
	}

}
