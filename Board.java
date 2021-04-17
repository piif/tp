import java.awt.*;  

class Board extends Canvas {
    public final int LINES=30;
    public final int COLUMNS=10;
    public final int BLOCK_SIZE=12;
    public final Color COLORS[] = {
        Color.BLACK, // none
        Color.yellow,
        Color.red,
        Color.green,
        Color.blue,
        Color.cyan,
        Color.orange
    };

    private short[][] tiles = new short[LINES][COLUMNS];

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
        g.setColor(Color.white);
        g.fill3DRect(0, 0, BLOCK_SIZE, getHeight()-BLOCK_SIZE, true);
        g.fill3DRect(getWidth()-BLOCK_SIZE, 0, BLOCK_SIZE, getHeight()-BLOCK_SIZE, true);
        g.fill3DRect(0, getHeight()-BLOCK_SIZE, getWidth(), BLOCK_SIZE, true);

        for (short line = 0; line < LINES; line++) {
            for (short col = 0; col < COLUMNS; col++) {
                if (tiles[line][col] != 0) {
                    block(g, col, line, COLORS[tiles[line][col]]);
                }
            }
        }
    }
}
