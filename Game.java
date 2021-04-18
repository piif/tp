import java.awt.*;  
import java.awt.event.*;
import javax.swing.Timer;

public class Game {
    public final int LINES = 35;
    public final int COLUMNS = 12;

    // i = init , r = running , e = end
    char status = 'i';
    Board board = null;
    Timer timer = null;
    Block currentBlock = null;
    int currentLine, currentColumn, currentPosition;

    public Game() {
        // draw main window
        Frame f= new Frame("Canvas Example");  
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
            System.out.println("keyPressed=" + key + "(" + e.getKeyCode() + ")");

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
        System.out.println("peak block " + currentBlock);
        currentLine = 2;
        currentColumn = COLUMNS / 2;
        currentPosition = 0;
        if (board.checkBlock(currentBlock, currentPosition, currentColumn, currentLine)) {
            board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
            board.repaint();
        } else {
            stop();
        }
    }
    private void move(int direction) {
        moveTo(currentPosition, currentColumn + direction, currentLine);
    }
    private void rotate() {
        moveTo((currentPosition + 1) % 4, currentColumn, currentLine);
    }
    private void fallDown() {
        while (moveTo(currentPosition, currentColumn, currentLine + 1));
        newBlock();
    }
    private boolean moveTo(int p, int c, int l) {
        board.removeBlock(currentBlock, currentPosition, currentColumn, currentLine);
        boolean result;
        if (board.checkBlock(currentBlock, p, c, l)) {
            currentPosition = p;
            currentColumn = c;
            currentLine = l;
            result = true;
        } else {
            result = false;
        }
        board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
        board.repaint();
        return result;
    }
    private void doStep() {
        if (!moveTo(currentPosition, currentColumn, currentLine + 1)) {
            newBlock();
        }
    }

    private void quit() {
        System.out.println("Quit");
        System.exit(0);
    }

    public static void main(String argv[]) {
        /* Game game =*/ new Game();
    }
}
