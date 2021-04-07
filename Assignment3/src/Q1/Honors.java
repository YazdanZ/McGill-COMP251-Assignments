import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	public static int min_moves(int[][] board) {
		// moves array to keep track of minimum distances to each cell
		int[][] moves = new int[board.length][board[0].length];

		// store the indexes of the nodes and add the source node (top left corner)
		Queue<Integer> nextNodes = new ArrayDeque<> ();
		nextNodes.add ( 0 );
		nextNodes.add ( 0 );

		while (!nextNodes.isEmpty ()) {
			int currRow = nextNodes.poll ();
			int currCol = nextNodes.poll ();


			ArrayList<int[]> adjCells = findAdjCells ( currRow, currCol, board );
			for (int[] adjCell : adjCells ) {
				int adjRow = adjCell[0];
				int adjCol = adjCell[1];

				if (moves[adjRow][adjCol] == 0 || moves[adjRow][adjCol] > moves[currRow][currCol] + 1) {
					moves[adjRow][adjCol] = moves[currRow][currCol] + 1;
					if (adjRow == board.length - 1 && adjCol == board[0].length - 1 )
						return moves[adjRow][adjCol];
					nextNodes.add ( adjRow );
					nextNodes.add ( adjCol );
				}
			}
		}
		return -1;
	}

	private static ArrayList<int[]> findAdjCells(int row, int col, int[][] board) {
		ArrayList<int[]> adjCells = new ArrayList<> (4);
		int cellValue = board[row][col];
		if (cellValue != 0) {
			if (isInBounds ( row + cellValue, col, board ))
				adjCells.add ( new int[] {row + cellValue, col} );
			if (isInBounds ( row, col + cellValue, board ))
				adjCells.add ( new int[] {row, col + cellValue} );
			if (isInBounds ( row - cellValue,  col, board ))
				adjCells.add ( new int[] {row - cellValue, col} );
			if (isInBounds ( row, col - cellValue, board ))
				adjCells.add ( new int[] {row, col - cellValue} );
		}
		return adjCells;
	}

	private static boolean isInBounds(int row, int col, int[][] board) {
		if (row >= 0 && row < board.length && col >= 0 && col < board[0].length)
			if (row == board.length - 1 && col == board[0].length - 1)
				return true;
			else return board[row][col] != 0;
		return false;
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
