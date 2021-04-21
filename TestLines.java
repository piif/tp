import java.awt.*;  
import java.awt.event.*;

public class TestLines {
    public final int LINES = 10;
    public final int COLUMNS = 12;

    Board board = null;
    boolean half = false;

    public TestLines() {
        // draw main window
        Frame f= new Frame("Canvas Example");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

		KeyListener listener = new MyKeyListener();
		f.addKeyListener(listener);
		f.setFocusable(true);

        board = new Board(LINES, COLUMNS);
        for (int l = 0; l < LINES; l++) {
            for (int c = 0; c < COLUMNS; c++) {
                board.setTile(c, l, l+5 % 10 + 1);
            }
        }
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
                case "Space":
                    removeLine();
                break;
            }
		}

        @Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

    private void removeLine() {
        if (half) {
            board.shiftDownHalf(8);
        } else {
            board.shiftDown(8);
        }
        half = !half;
        board.repaint();
    }

    public static void main(String argv[]) {
        new TestLines();
    }
}
