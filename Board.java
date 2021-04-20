import java.awt.*;  

class Board extends Canvas {
    public final int TILE_SIZE=18;
    public final Color COLORS[] = {
        Color.darkGray,
        Color.yellow,
        Color.red,
        Color.green,
        Color.blue,
        Color.cyan,
        Color.orange,
        Color.pink
    };

    public final int lines, columns;
    private int[][] tiles;

    public Board(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        tiles = new int[lines][columns];

        setBackground (Color.DARK_GRAY);
        setSize(getWidth(), getHeight());
    }

    public int getWidth() {
        return TILE_SIZE * (columns + 2);
    }
    public int getHeight() {
        return TILE_SIZE * (lines + 2);
    }
    
    private void drawTile(Graphics g, int col, int line, Color color) {
        g.setColor(color);
        g.fill3DRect(col*TILE_SIZE, line*TILE_SIZE, TILE_SIZE, TILE_SIZE, true);
    }

    public void setTile(int col, int line, int color) {
        tiles[line][col] = color;
    }

    public int getTile(int col, int line) {
        return tiles[line][col];
    }

    public void drawBlock(Block block, int pos, int col, int line) {
        // System.out.println("drawBlock "+pos+", "+col+", "+line);
        Block.Position position = block.positions[pos];
        for (int t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            tiles[line+tile.dy][col+tile.dx] = block.color;
        }
    }

    public void removeBlock(Block block, int pos, int col, int line) {
        // System.out.println("removeBlock "+pos+", "+col+", "+line);
        Block.Position position = block.positions[pos];
        for (int t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            tiles[line+tile.dy][col+tile.dx] = 0;
        }
    }

    public boolean checkBlock(Block block, int pos, int col, int line) {
        Block.Position position = block.positions[pos];
        for (int t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            int l = line + tile.dy , c = col + tile.dx;
            if (l < 0 || l >= lines) {
                return false;
            }
            if (c < 0 || c >= columns) {
                return false;
            }
            if (tiles[line+tile.dy][col+tile.dx] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean lineIsFull(int line) {
        for (int c = 0; c < columns ; c++) {
            if (tiles[line][c] == 0) {
                return false;
            }
        }
        return true;
    }

    public void shiftDown(int line) {
        if (line > 0) {
            System.arraycopy(tiles, 0, tiles, 1, line);
        }
        tiles[0] = new int[columns];
    }

    public void paint(Graphics g) {  
        g.setColor(Color.white);
        g.fill3DRect(0, TILE_SIZE, TILE_SIZE, getHeight()-2*TILE_SIZE, true);
        g.fill3DRect(getWidth()-TILE_SIZE, TILE_SIZE, TILE_SIZE, getHeight()-2*TILE_SIZE, true);
        g.fill3DRect(0, getHeight()-TILE_SIZE, getWidth(), TILE_SIZE, true);

        for (int line = 0; line < lines; line++) {
            for (int col = 0; col < columns; col++) {
                // remove "if" to display dark blocks, but implies flickering
                if (tiles[line][col] != 0) {
                    drawTile(g, col+1, line+1, COLORS[(tiles[line][col]) % COLORS.length]);
                }
            }
        }
    }
}
