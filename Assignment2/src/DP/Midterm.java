import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;


	public static void main ( String[] args ) {
		BufferedReader reader = new BufferedReader ( new InputStreamReader ( System.in ) );
		int chairs;
		try {
			chairs = Integer.valueOf ( reader.readLine () );
			penalization = new int[chairs];
			for (int i = 0; i < chairs; i++) {
				penalization[i] = Integer.valueOf ( reader.readLine () );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}
		int answer = lost_marks ( penalization );
		System.out.println ( answer );
	}

	public static int lost_marks ( int[] penalization ) {
		int n = penalization.length;

		dp_table = new int[n][n]; // rows = jumps, cols = seats

		ArrayList<Integer> scores = new ArrayList<> ();
		for (int lastJump = n - 1; lastJump > 0; lastJump--) {
			clearArray ( n );
			int score = recurseLostMarks ( n - 1, lastJump, penalization, lastJump - 1 );
			scores.add ( score );
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					dp_table[i][j] = Integer.MAX_VALUE;
		}
		return Collections.min ( scores );
	}

	private static void clearArray ( int n ) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				dp_table[i][j] = Integer.MAX_VALUE;
	}

	private static int recurseLostMarks ( int seat, int jump, int[] penalization, int initialJump ) {
		// if the seat is out of bounds
		// return a negative penalization to distinguish between a valid jump and an invalid one
		if (outOfBound ( seat, penalization ))
			return Integer.MAX_VALUE;

			// impossible path
		else if (seat != 0 && jump == 0)
			dp_table[jump][seat] = Integer.MAX_VALUE;

			// reaching starting point
		else if (seat == 0 && jump == 0)
			dp_table[jump][seat] = 0;

		else {
			int pen = penalization[seat];
			int forwardScore, backwardScore;

			if (outOfBound ( seat - jump, penalization ))
				forwardScore = Integer.MAX_VALUE;
			else {
				if (dp_table[jump - 1][seat - jump] != Integer.MAX_VALUE)
					forwardScore = dp_table[jump - 1][seat - jump];
				else
					forwardScore = recurseLostMarks ( seat - jump, jump - 1, penalization, initialJump );
			}
			if (outOfBound ( seat + jump, penalization ))
				backwardScore = Integer.MAX_VALUE;
			else {
				if (dp_table[jump][seat + jump] != Integer.MAX_VALUE)
					backwardScore = dp_table[jump][seat + jump];
				else
					backwardScore = recurseLostMarks ( seat + jump, jump, penalization, initialJump );
			}

			if (forwardScore != Integer.MAX_VALUE || backwardScore != Integer.MAX_VALUE)
				dp_table[jump][seat] = Math.min ( forwardScore, backwardScore ) + pen;
			else dp_table[jump][seat] = Integer.MAX_VALUE;
		}
		return dp_table[jump][seat];
	}

	private static boolean outOfBound ( int seat, int[] penalization ) {
		return seat >= penalization.length || seat < 0;
	}
}