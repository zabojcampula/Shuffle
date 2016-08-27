package shuffle;

import java.util.Random;

import javax.swing.JOptionPane;

class Coordinates {
	public int r;
	public int c;
}

public class ShuffleLogic {

	private Field[][] board = new Field[Board.BOARDSIZE][Board.BOARDSIZE];
	private Board history = new Board();
	private Coordinates space = new Coordinates();
	private ShuffleUI ui;
	private boolean isRunning = false;
	private boolean requestStop = false;
	private boolean oneStep = false;
	private Thread logicThread;
	
	public void setUI(ShuffleUI ui) {
		this.ui = ui;
	}

	public void randomize() {
		Coordinates cord = new Coordinates();
		for (cord.r = 0; cord.r < 4; ++cord.r) {
			for (cord.c = 0; cord.c < 4;  ++cord.c)  {
				board[cord.r][cord.c] = new Field();
				board[cord.r][cord.c].fixed = true;
			}
		}
		Random rand = new Random();
		
		for (int i = 15; i >= 0; --i) {
			int position = i == 0 ? i : rand.nextInt(i);
			int count = 0;
			cordloop:
			for (cord.r = 0; cord.r < 4; ++cord.r) {
				for (cord.c = 0; cord.c < 4;  ++cord.c)  {
					if (count == position && board[cord.r][cord.c].fixed) {
						board[cord.r][cord.c].Num = i;
						board[cord.r][cord.c].fixed = false;
						break cordloop;
					}
					if (board[cord.r][cord.c].fixed)  {
						count++;
					}
				}
			}
		}
		space.r = space.c = 3;
		history.store(board);
		showAll();
	}

	private void showAll() {
		Coordinates cord = new Coordinates();
		for (cord.r = 0; cord.r < 4; ++cord.r) {
			for (cord.c = 0; cord.c < 4;  ++cord.c)  {
				ui.showOne(cord.r, cord.c, board[cord.r][cord.c].Num, board[cord.r][cord.c].fixed);
			}
		}
	}

	private void nine() throws InterruptedException {
	    Coordinates c;
	    move(3,3);
	    c = whereIs(9);
	    while (c.r != 2 || c.c != 0) {
	    	hnis("DRRRULLL");
	    	c = whereIs(9);
	    }
	}
	
	private void ten() throws InterruptedException {
		Coordinates c = whereIs(10);
   	    if (c.c == 0) {
	       hnis("RRRDLULDRRULLL");
   	    }
   	    c = whereIs(10);
	    while (c.r != 2 || c.c != 1) {
	    	hnis("DRRULL");
	   	    c = whereIs(10);
	    }
	}
	
	private void eleven() throws InterruptedException {
		Coordinates c;
		c = whereIs(11);
        if (c.c == 0) {
        	hnis("RRRDLULDRRULLL");
        } else if (c.c == 1) {
        	hnis("RRRDLULDRULDRRULLL");
        } else {
        	while (c.c != 2 || c .r != 2) {
        		hnis("DRUL");
        		c = whereIs(11);
        	}
        }
	}
	
	private void twelwe() throws InterruptedException {
		Coordinates c = whereIs(12);
		switch(c.c) {
			case 0: hnis("RRRDLULDRULDLURDLURDRULDRRULLL");
			        break;
			case 1: hnis("RRRDLLULDRRRULLL");
			        break;
			case 2: hnis("RRDLULDRULDRRULL");
			        break;
		}
	}

	private void lastLine() throws InterruptedException {
        Coordinates c13 = whereIs(13);
        Coordinates c14 = whereIs(14);
        Coordinates c15 = whereIs(15);
        int switcher = (c13.c + 1) + (c14.c + 1) * 4 + (c15.c + 1) * 16;
		switch (switcher) {
			case 57: break;
			case 54:
			case 45:
			case 27: JOptionPane.showMessageDialog(null, "The position has no solution", "Shuffle", JOptionPane.INFORMATION_MESSAGE);
				     break;
			case 39: hnis("DRRRULLLDRRURDLLLURRRDLLLU");
					 break;
			case 30: hnis("DRRRULLLDRRRULDLLURRRDLLLU");
			         break;
			default: System.out.println("Err switcher = " + switcher);
				     throw new IllegalStateException();
		}
	}

	private void secondTwoLines() throws InterruptedException {
		nine();
		ten();
		eleven();
		twelwe();
		lastLine();
	}

	private void move(int r, int c) throws InterruptedException {
		Coordinates old = new Coordinates();
		while (r != space.r || c != space.c) {
			old.r = space.r;
			old.c = space.c;
			
			if (space.r != 3) {
				while (r > space.r && board[space.r + 1][space.c].fixed == false) {
					hnich('U');
				}
			}
			if (space.r != 0) {
				while (r < space.r && board[space.r - 1][space.c].fixed == false) {
					hnich('D');
				}
			}
			if (space.c != 3) {
				while (c > space.c && board[space.r][space.c + 1].fixed == false) {
					hnich('L');
				}
			}
			if (space.c != 0) {
				while (c < space.c && board[space.r][space.c - 1].fixed == false) {
					hnich('R');
				}
			}

			if (old.r == space.r && old.c == space.c) {
			      if (space.r == r &&  r != 3 && space.c + 2 == c)
			    	  hnis("ULLD");
			      else if (space.r == r && r != 3 &&  space.c - 2 == c)
			    	  hnis("URRD");
			      else if (space.r == r &&  r == 3 && space.c - 2 == c)
			    	  hnis("DRRU");
			      else if (space.r == r &&  r == 3 && space.c + 2 == c)
			    	  hnis("DLLU");
			      else if (space.r != 0 && space.r != 3 && space.c < 2 && space.r - 1 == r &&  space.c + 1 == c)
			    	  hnis("ULLDDR");
			      else if (space.r == r && r != 3)
			         move(r + 1, c);
			      else if (space.r == r && r == 3)
			    	  move(r - 1, c);
			      else if (space.c == c && c != 3)
			    	  move(r, c + 1);
			      else if (space.c == c && c == 3)
			      	move(r, c - 1);
			      else if (space.c == c - 1 && space.r == r + 1 && board[space.r][space.c + 1].fixed && space.c < 2)
			    	  hnis("DRRUUL");
			      else {
			    	  throw(new IllegalStateException());
			      }
			}
		}
	}

	private Coordinates whereIs(int n) {
		Coordinates c = new Coordinates();
		for (c.r = 0; c.r < 4; ++c.r) {
			for (c.c = 0; c.c < 4;  ++c.c)  {
				if (board[c.r][c.c].Num == n) {
					return c;
				}
			}
		}
		throw new IndexOutOfBoundsException();
	}
	
	private Coordinates wherePut(int n) {
		Coordinates c = new Coordinates();
	    switch(n) {
	    	case 1: c.r = 0; c.c = 0; break;
	    	case 2: c.r = 0; c.c = 1; break;
	    	case 3: c.r = 0; c.c = 2; break;
	    	case 4: c.r = 1; c.c = 2; break;
	    	case 5: c.r = 1; c.c = 0; break;
	    	case 6: c.r = 1; c.c = 1; break;
	    	case 7: c.r = 1; c.c = 2; break;
	    	case 8: c.r = 2; c.c = 2; break;
	    	default:
	    		throw new IndexOutOfBoundsException();
	    }
	    return c;
	}

	private void exchange(Coordinates sp, Coordinates num) throws InterruptedException {
		if (requestStop) {
			throw new InterruptedException();
		}
		Field f = board[num.r][num.c];
		board[num.r][num.c] = board[sp.r][sp.c];
		board[sp.r][sp.c] = f;
		ui.showOne(sp.r, sp.c, board[sp.r][sp.c].Num, board[sp.r][sp.c].fixed);
		ui.showOne(num.r, num.c, board[num.r][num.c].Num, board[sp.r][sp.c].fixed);
		sp.c = num.c;
		sp.r = num.r;
		if (oneStep) {
			synchronized(logicThread) {
				logicThread.wait();
			}
		} else {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}
	
	private boolean hnich(char pos) throws InterruptedException {
		Coordinates mov = new Coordinates();
	  	switch (pos) {
	  		case 'U':
	  			 if (space.r > 2)
	  				 return false;
	             else {
	                 mov.c = space.c;
	                 mov.r = space.r + 1;
	                 exchange(space , mov);
	                 return true;
	             }
	  		case 'D':
	  			if (space.r < 1)
	  				return false;
	            else {
	            	mov.c = space.c;
	                mov.r = space.r - 1;
	                exchange(space , mov);
	                return true;
	            }
	  		case 'R':
	  			if (space.c < 1)
	  				return false;
	            else
	            	mov.c = space.c - 1;
	                mov.r = space.r;
	                exchange(space , mov);
	                return true;
	  		case 'L':
	  			if (space.c > 2)
	  				return false;
	  			else
	  				mov.c = space.c + 1;
	                mov.r = space.r;
	                exchange(space , mov);
	                return true;
	  	}
	    return false;
	}

	private void hnis(String command) throws InterruptedException {
		for(int i = 0; i < command.length(); ++i) {
			hnich(command.charAt(i));
		}
	}

	private void firstTwoLines() throws InterruptedException {
		Coordinates where;
		Coordinates c;
		for (int i = 1; i <= 8; ++i) {
			where = wherePut(i);
			c = whereIs(i);
			board[c.r][c.c].fixed = true;
			while (where.r != c.r || where.c != c.c) {
				if (i % 4 == 0 && c.c == 3) {
					if (c.r + 1 == i / 4) {
						break;
					}
					if (c.r == i / 4 && space.c == 3 && space.r + 1 == i / 4) {
						hnich('U');
						break;
					}
				}
				c = whereIs(i);
				while (c.c > where.c) {
					move(c.r, c.c -1);
					hnich('L');
					c = whereIs(i);
				}
	    
				while (c.c < where.c) {
			        move(c.r, c.c + 1);
			        hnich('R');
					c = whereIs(i);
				}

				while (c.r > where.r) {
					c = whereIs(i);
			        move(c.r - 1, c.c);
			        hnich('U');
					c = whereIs(i);
				}

				while (c.r < where.r) {
			        move(c.r + 1, c.c);
			        hnich('D');
					c = whereIs(i);
				}
			}
			
			if (i == 4 && board[0][3].Num != 4 || i == 8 && (board[1][3].Num != 8)) {
				move(i / 4, 1);
				hnis("DLULDRRU");
			}
		}
	}


	private void executeLogic() {
	    isRunning= true;
		logicThread = new Thread() {
			@Override
			public void run() {
				requestStop = false;
				try {
					firstTwoLines();
					secondTwoLines();
				} catch (Exception e) {
					System.out.println("Exception caught in calculation " + e);
					e.printStackTrace();
				}
				isRunning = false;
			}
		};
		logicThread.start();
	}

	public void run() {
		if (!isRunning) {
			executeLogic();
		} else if (oneStep) {
			synchronized (logicThread) {
				oneStep = false;
				logicThread.notify();
			}
		}
	}

	public void stop() {
		if (isRunning) {
			synchronized (logicThread) {
				requestStop = true;
				if (oneStep) {
					oneStep = false;
					logicThread.notify();
				}
			}
		}
	}

	public void oneStep() {
		oneStep  = true;
		if (!isRunning)
			executeLogic();
		else
			synchronized(logicThread) {
				logicThread.notify();
			}
	}

	public void recover() {
		history.read(board);
		space.r = space.c = 3;
		showAll();
	}


}
