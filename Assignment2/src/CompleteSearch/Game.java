import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {

	Board sudoku;

	public class Cell{
		private int row = 0;
		private int column = 0;

		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}

	public class Region{
		private Cell[] matrix;
		private int num_cells;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}

	}

	public class Board{
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;

		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}

		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}
		public void setValues(int[][] values) {
			board_values = values;
		}

	}

    /**
     * Solve sudoku using recursive backtracking
     * @return
     */
	public int[][] solver() {
	    solvePuzzle (0, 0);
		return sudoku.getValues();
	}

	private boolean solvePuzzle(int regionIndex, int cellIndex) {

		// go to the next region if all cells of a region have been visited
		if (cellIndex == this.sudoku.getRegion ( regionIndex ).num_cells) {
			regionIndex++;
			cellIndex = 0;
		}

		// we're done if we visit all the regions
		// puzzle solved!
		if (regionIndex == this.sudoku.num_regions )
			return true;

		Region currentRegion = this.sudoku.getRegion ( regionIndex );
		Cell currentCell = currentRegion.getCells ()[cellIndex];

		int row = currentCell.getRow ();
		int col = currentCell.getColumn ();

		// go to next cell if cell is not empty
		// empty cells represented by -1
		if (this.sudoku.getValues ()[row][col] != -1)
			return solvePuzzle ( regionIndex, cellIndex + 1 );

		// each region contains the number 1 to num_cells
		for (int guess = 1; guess <= currentRegion.num_cells; guess++) {
			if (isValid (row, col, currentRegion, guess)){
				this.sudoku.setValue ( row, col, guess );

				// check condition of the next cell
				if (solvePuzzle ( regionIndex, cellIndex + 1 )){
					return true;
				}
			}
			// the guess was wrong
			this.sudoku.setValue ( row, col, -1);
		}

		// can't solve the given puzzle
		return false;
    }

    /**
     * Check if a board configuration is valid
     * @return validity of the board configuration
     */
    private boolean isValid(int row, int col, Region region, int guess) {
		ArrayList<Integer> regionValues = new ArrayList<> ();
		for (Cell cell : region.matrix) {
			regionValues.add (this.sudoku.getValue (cell.getRow (), cell.getColumn ()));
		}

		// check if the guess already exists in the region
		if (regionValues.contains ( guess ))
			return false;

		// check if adjacent cells contain the guess
		if (getNeighborValues ( row, col ).contains ( guess ))
			return false;

	    return true;
    }

	/**
	 * get a list of values of neighbors cells
	 * @param row
	 * @param col
	 * @return list of values of neighbors cells
	 */
    private ArrayList<Integer> getNeighborValues(int row, int col){
    	ArrayList<Integer> neighbors = new ArrayList<> ();

    	// add all the neighbors
    	neighbors.add ( getCellCheckBounds ( row - 1, col - 1 ) );
    	neighbors.add ( getCellCheckBounds ( row - 1, col ) );
    	neighbors.add ( getCellCheckBounds ( row - 1, col +1 ) );
    	neighbors.add ( getCellCheckBounds ( row, col - 1 ) );
    	neighbors.add ( getCellCheckBounds ( row, col + 1 ) );
    	neighbors.add ( getCellCheckBounds ( row + 1, col - 1) );
    	neighbors.add ( getCellCheckBounds ( row + 1, col) );
    	neighbors.add ( getCellCheckBounds ( row + 1, col + 1) );

    	return  neighbors;
	}

	
	private int getCellCheckBounds(int row, int col) {
    	if (isInBounds(row, col))
    		return this.sudoku.getValue ( row, col );
    	return 0;
	}

    private boolean isInBounds(int row, int col) {
    	if (row >= this.sudoku.num_rows || row < 0)
    		return false;
    	if (col >= this.sudoku.num_columns || col < 0)
    		return false;
    	return true;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}



}


