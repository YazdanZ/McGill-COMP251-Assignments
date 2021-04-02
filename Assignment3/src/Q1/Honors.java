import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	public static int min_moves(int[][] board) {
		return 0; // placeholder
	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}
			
		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();
		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'
		
		// or you can test like this
		// int [][]board = {1,2,3}; // not actual example
		// int answer = min_moves(board); 
		// System.out.println(answer);
	}

}
