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
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n; j++)
				dp_table[i][j] = Integer.MAX_VALUE;

		ArrayList<Integer> scores = new ArrayList<> ();
		for (int lastJump = n - 1; lastJump > 0; lastJump--) {
			int score = recursion ( n - 1, lastJump, penalization, lastJump - 1 );
			scores.add ( score );
		}
		return Collections.min ( scores );
	}

	private static int recursion ( int seat, int jump, int[] penalization, int initialJump) {
		// if the seat is out of bounds
		// return a negative penalization to distinguish between a valid jump and an invalid one

		if (outOfBound ( seat, penalization ))
			return Integer.MAX_VALUE;

			// impossible path
		else if (seat != 0 && jump == 0)
			dp_table[initialJump][seat] = Integer.MAX_VALUE;

			// reaching starting point
		else if (seat == 0 && jump == 0)
			dp_table[initialJump][seat] = 0;

		else {
			int pen = penalization[seat];
			int forwardScore, backwardScore;

			if (outOfBound ( seat - jump, penalization ))
				forwardScore = Integer.MAX_VALUE;
			else {
				if (dp_table[initialJump][seat - jump] != Integer.MAX_VALUE )
					forwardScore = dp_table[initialJump][seat - jump];
				else
					forwardScore = recursion ( seat - jump, jump - 1, penalization, initialJump );
			}
			if (outOfBound ( seat + jump, penalization ))
				backwardScore = Integer.MAX_VALUE;
			else {
				if (dp_table[initialJump][seat + jump] != Integer.MAX_VALUE)
					backwardScore = dp_table[initialJump][seat + jump];
				else
					backwardScore = recursion ( seat + jump, jump, penalization, initialJump );
			}

			if (forwardScore != Integer.MAX_VALUE || backwardScore != Integer.MAX_VALUE )
				dp_table[initialJump][seat] = Math.min ( forwardScore, backwardScore ) + pen;
			else dp_table[initialJump][seat] = Integer.MAX_VALUE;
		}
		return dp_table[initialJump][seat];
	}

	private static boolean outOfBound ( int seat, int[] penalization) {
		return seat >= penalization.length || seat < 0;
	}
}