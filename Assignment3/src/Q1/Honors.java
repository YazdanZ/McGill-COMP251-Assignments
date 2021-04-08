import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {

	private static class Tile {
		public int x, y;
		public Tile ( int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals ( Object o ) {
			if (this == o) return true;
			if (!(o instanceof Tile)) return false;
			Tile tile = (Tile) o;
			return x == tile.x && y == tile.y;
		}

		@Override
		public int hashCode () {
			return Objects.hash ( x, y );
		}
	}

	public static int min_moves(int[][] board) {
		// moves array to keep track of minimum distances to each cell
		int[][] moves = new int[board.length][board[0].length];

		// store the indexes of the nodes and add the source node (top left corner)
		Queue<Tile> nextNodes = new ArrayDeque<> ();
		nextNodes.add ( new Tile ( 0, 0 ) );

		while (!nextNodes.isEmpty ()) {
			Tile curr = nextNodes.poll ();

			ArrayList<Tile> adjCells = findAdjCells ( curr.x, curr.y, board );
			for (Tile adjCell : adjCells ) {
				int adjRow = adjCell.x;
				int adjCol = adjCell.y;

				if (moves[adjRow][adjCol] == 0 || moves[adjRow][adjCol] > moves[curr.x][curr.y] + 1) {
					moves[adjRow][adjCol] = moves[curr.x][curr.y] + 1;
					if (adjRow == board.length - 1 && adjCol == board[0].length - 1 )
						return moves[adjRow][adjCol];
					nextNodes.add ( adjCell );
				}
			}
		}
		return -1;
	}

	private static ArrayList<Tile> findAdjCells( int row, int col, int[][] board) {
		ArrayList<Tile> adjCells = new ArrayList<> (4);
		int cellValue = board[row][col];
		if (cellValue != 0) {
			if (isInBounds ( row + cellValue, col, board ))
				adjCells.add ( new Tile ( row + cellValue, col ) );
			if (isInBounds ( row, col + cellValue, board ))
				adjCells.add ( new Tile ( row, col + cellValue) );
			if (isInBounds ( row - cellValue,  col, board ))
				adjCells.add ( new Tile (row - cellValue, col) );
			if (isInBounds ( row, col - cellValue, board ))
				adjCells.add ( new Tile (row, col - cellValue) );
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
