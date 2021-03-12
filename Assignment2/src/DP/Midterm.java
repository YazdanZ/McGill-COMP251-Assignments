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
		for (int lastJump = n - 1; lastJump >= 1; lastJump--) {
			for (int i  = 0; i < lastJump; i++)
				Arrays.fill ( dp_table[i], -1 );
			int score = recurseLostMarks ( n - 1, lastJump, penalization );
			scores.add ( score );
		}
		return Collections.min ( scores );
	}

	private static int recurseLostMarks ( int seat, int jump, int[] penalization ) {
		// impossible path
		if (seat != 0 && jump == 0)
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
				if (dp_table[jump - 1][seat - jump] != -1)
					forwardScore = dp_table[jump - 1][seat - jump];
				else
					forwardScore = recurseLostMarks ( seat - jump, jump - 1, penalization);
			}
			if (outOfBound ( seat + jump, penalization ))
				backwardScore = Integer.MAX_VALUE;
			else {
				if (dp_table[jump][seat + jump] != -1)
					backwardScore = dp_table[jump][seat + jump];
				else
					backwardScore = recurseLostMarks ( seat + jump, jump, penalization);
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