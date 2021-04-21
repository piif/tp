import java.awt.*;  
import java.awt.event.*;
import javax.swing.Timer;

public class Game {
    public final int LINES = 30;
    public final int COLUMNS = 12;

    // i = init , r = running , e = end
    char status = 'i';
    Board board = null;
    Timer timer = null;
    Block currentBlock = null;
    int currentLine, currentColumn, currentPosition, ghostLine;

    public Game() {
        // draw main window
        Frame f= new Frame("Canvas");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

        KeyListener listener = new MyKeyListener();
        f.addKeyListener(listener);
		f.setFocusable(true);

        board = new Board(LINES, COLUMNS);
        f.add(board);

        f.pack();
        f.setVisible(true);
    }

    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            doStep();
        }
    };

	private class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            // System.out.println("keyPressed=" + key + "(" + e.getKeyCode() + ")");

            if ("Q".equals(key)) {
                quit();
                return;
            }

            switch (status) {
                case 'i':
                    if ("Space".equals(key)) {
                        start();
                    }
                break;
                case 'r':
                    switch(key) {
                        case "Left":
                            move(-1);
                        break;
                        case "Right":
                            move(+1);
                        break;
                        case "Up":
                            rotate();
                        break;
                        case "Down":
                            fallDown();
                        break;
                    }
                break;
            }
		}

        @Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

    private void start() {
        System.out.println("Start");
        newBlock();
        timer = new Timer(1000, taskPerformer);
        timer.start();
        status = 'r';
    }
    private void stop() {
        System.out.println("Gameover");
        timer.stop();
        timer = null;
        status = 'e';
    }
    private void newBlock() {
        currentBlock = Block.randomBlock();
        // System.out.println("peak block " + currentBlock);
        currentLine = -1;
        ghostLine = -1;
        if (!moveTo(0, COLUMNS / 2, 2)) {
            stop();
        }
    }
    private void move(int direction) {
        moveTo(currentPosition, currentColumn + direction, currentLine);
    }
    private void rotate() {
        moveTo((currentPosition + 1) % Block.POSITIONS_PER_BLOCK, currentColumn, currentLine);
    }
    private void fallDown() {
        moveTo(currentPosition, currentColumn, ghostLine);
        checkLines();
        newBlock();
    }
    private boolean moveTo(int p, int c, int l) {
        if (currentLine != -1) {
            board.removeBlock(currentBlock, currentPosition, currentColumn, currentLine);
        }
        if (ghostLine != currentLine) {
            board.removeBlock(currentBlock, currentPosition, currentColumn, ghostLine);
        }

        boolean result;
        if (board.checkBlock(currentBlock, p, c, l)) {
            currentPosition = p;
            currentColumn = c;
            currentLine = l;

            ghostLine = currentLine;
            while (board.checkBlock(currentBlock, currentPosition, currentColumn, ghostLine+1)) {
                ghostLine++;
            }
            if (ghostLine!=currentLine) {
                board.drawGhost(currentBlock, currentPosition, currentColumn, ghostLine);
            }

            result = true;
        } else {
            result = false;
        }

        if (currentLine != -1) {
            board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
        }
        board.repaint();
        return result;
    }

    private void doStep() {
        if (!moveTo(currentPosition, currentColumn, currentLine + 1)) {
            checkLines();
            newBlock();
        }
    }
    void checkLines() {
        timer.stop();
        // check if lines "around" current block are full. If yes, delete them by moving
        // one line down all line on top of it
        for (int l = currentLine - 2; l <= currentLine + 2; l++) {
            if (l >= LINES || l < 0) {
                continue;
            }
            if (board.lineIsFull(l)) {
                // System.out.println("line " + l + " is full");
                board.shiftDown(l);
            }
            if (board.halfLineIsFull(l)) {
                // System.out.println("half line " + l + " is full");
                board.shiftDownHalf(l);
            }
        }
        timer.restart();
    }
    private void quit() {
        System.out.println("Quit");
        System.exit(0);
    }

    public static void main(String argv[]) {
        /* Game game =*/ new Game();
    }
}
