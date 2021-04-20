import java.awt.*;  
import java.awt.event.*;

public class Test {
    public final int LINES = 30;
    public final int COLUMNS = 12;

    Board board = null;
    int currentLine = 3, currentColumn = COLUMNS / 2;
    int under = 0;

    public Test() {
        // draw main window
        Frame f= new Frame("Canvas Example");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

		KeyListener listener = new MyKeyListener();
		f.addKeyListener(listener);
		f.setFocusable(true);

        board = new Board(LINES, COLUMNS);
        board.setTile(currentColumn, currentLine, 1);
        f.add(board);

        f.pack();
        f.setVisible(true);
    }

	private class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            // System.out.println("keyPressed=" + key + "(" + e.getKeyCode() + ")");

            switch(key) {
                case "Q":
                    System.exit(0);
                break;
                case "Left":
                    move(-1, 0);
                break;
                case "Right":
                    move(+1, 0);
                break;
                case "Up":
                    move(0, -1);
                break;
                case "Down":
                    move(0, +1);
                break;
                case "C":
                    // clear all
                    clearAll();
                break;
                case "Space":
                    // set block
                    board.setTile(currentColumn, currentLine, 2);
                    under = 2;
                    board.repaint();            
                break;
                case "X":
                    // dump from current position as center
                    dump();
                break;
            }
		}

        @Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

    private void clearAll() {
        for (int l = 0; l < LINES; l++) {
            for (int c = 0; c < COLUMNS; c++) {
                board.setTile(c, l, 0);
            }
        }
        board.repaint();
    }

    private void dump() {
        boolean first = true;
        for (int l = 0; l < LINES; l++) {
            for (int c = 0; c < COLUMNS; c++) {
                int color = board.getTile(c, l);
                if (color != 0) {
                    if (first) {
                        first = false;
                    } else {
                        System.out.print(", ");
                    }
                    int dc = c - currentColumn, dl = l - currentLine;
                    System.out.print("{ " + dc + ", " + dl + " }");
                }
            }
        }
        System.out.println();
    }

    private boolean move(int dx, int dy) {
        if(currentColumn + dx < 0 || currentColumn + dx >= COLUMNS) {
            return false;
        }
        if(currentLine + dy < 0 || currentLine + dy >= LINES) {
            return false;
        }
        board.setTile(currentColumn, currentLine, under);
        currentColumn += dx;
        currentLine += dy;
        under = board.getTile(currentColumn, currentLine);
        board.setTile(currentColumn, currentLine, 1);
        board.repaint();
        return true;
    }

    public static void main(String argv[]) {
        new Test();
    }
}
