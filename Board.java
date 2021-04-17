import java.awt.*;  

class Board extends Canvas {
    public final int LINES=30;
    public final int COLUMNS=10;
    public final int BLOCK_SIZE=12;

    private int[][] blocks = new int[LINES][COLUMNS];

    private static final long serialVersionUID = 1L;

    public Board() {
        setBackground (Color.DARK_GRAY);
        setSize(getWidth(), getHeight());
    }

    public int getWidth() {
        return BLOCK_SIZE * (COLUMNS + 2);
    }
    public int getHeight() {
        return BLOCK_SIZE * (LINES + 2);
    }
    
    private void block(Graphics g, int col, int line, Color color) {
        g.setColor(color);
        g.fill3DRect(col*BLOCK_SIZE, line*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
    }

    public void paint(Graphics g) {  
        block(g, 1, 1, Color.YELLOW);
        block(g, 1, 2, Color.YELLOW);
        block(g, 1, 3, Color.YELLOW);
        block(g, 1, LINES, Color.GREEN);
        block(g, COLUMNS, 1, Color.BLUE);
        block(g, COLUMNS, LINES, Color.RED);
    }
}
