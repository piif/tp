import java.awt.*;  
import java.awt.event.*;

public class TestBlocks {
    public final int LINES = 10;
    public final int COLUMNS = 12;

    Board board = null;
    int currentBlockNumber = 0;
    Block currentBlock = null;
    int currentLine = LINES / 2, currentColumn = COLUMNS / 2, currentPosition = 0;

    public TestBlocks() {
        // draw main window
        Frame f= new Frame("Canvas Example");  
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);

		KeyListener listener = new MyKeyListener();
		f.addKeyListener(listener);
		f.setFocusable(true);

        board = new Board(LINES, COLUMNS);
        currentBlock = Block.getBlock(currentBlockNumber);
        board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
        board.setTile(currentColumn, currentLine, 11);
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
                    moveOf(0, -1, 0);
                break;
                case "Right":
                    moveOf(0, +1, 0);
                break;
                case "Up":
                    moveOf(+1, 0, 0);
                break;
                case "Down":
                    moveOf(-1, 0, 0);
                break;
                case "Page Up":
                    changeBlock(-1);
                break;
                case "Page Down":
                    changeBlock(+1);
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

    private void dump() {
        boolean first = true;
        System.out.print("{ ");
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
                    System.out.print("{ " + (dc >= 0 ? " " : "") + dc + ", " + (dl >= 0 ? " " : "") + dl + " }");
                }
            }
        }
        System.out.println(" },");
    }

    private void changeBlock(int db) {
        board.removeBlock(currentBlock, currentPosition, currentColumn, currentLine);
        int newBlockNumber = (currentBlockNumber + Block.BLOCK_COUNT + db) % Block.BLOCK_COUNT;
        Block newBlock = Block.getBlock(newBlockNumber);
        if (board.checkBlock(newBlock, 0, currentColumn, currentLine)) {
            currentBlockNumber = newBlockNumber;
            currentBlock = newBlock;
            currentPosition = 0;
            System.out.println("Changed to block " + currentBlockNumber);
        } else {
            System.out.println("Can't change to next block");
        }
        board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
        board.setTile(currentColumn, currentLine, 11);
        board.repaint();
    }

    private void moveOf(int dp, int dc, int dl) {
        board.removeBlock(currentBlock, currentPosition, currentColumn, currentLine);
        int newPosition = (currentPosition + Block.POSITIONS_PER_BLOCK + dp) % Block.POSITIONS_PER_BLOCK;
        if (board.checkBlock(currentBlock, newPosition, currentColumn+dc, currentLine+dl)) {
            currentPosition = newPosition;
            currentColumn += dc;
            currentLine += dl;
        }
        board.drawBlock(currentBlock, currentPosition, currentColumn, currentLine);
        board.setTile(currentColumn, currentLine, 11);
        board.repaint();
    }

    public static void main(String argv[]) {
        new TestBlocks();
    }
}
