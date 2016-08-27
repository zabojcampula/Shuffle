package shuffle;


public class Board {

	public static final int  BOARDSIZE = 4;
	
	private Field[][] board;
	
	{
		board = new Field[BOARDSIZE][BOARDSIZE];
		for (int r = 0; r < BOARDSIZE; r++)
			for (int c = 0;  c < BOARDSIZE; c++)
				board[r][c] = new Field();
	}

	static private void copy(Field[][] dst, Field[][] src) {
		for (int r = 0; r < BOARDSIZE; r++)
			for (int c = 0;  c < BOARDSIZE; c++) {
				dst[r][c].fixed = src[r][c].fixed;
				dst[r][c].Num   = src[r][c].Num;
			}
		
	}
	
	public void store(Field[][]  f) {
		copy(board, f);
	}
	
	public void read(Field[][] f) {
		copy(f, board);
	}
	
}
