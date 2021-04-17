import java.awt.*;  

class Board extends Canvas {
    public final int TILE_SIZE=12;
    public final Color COLORS[] = {
        Color.yellow,
        Color.red,
        Color.green,
        Color.blue,
        Color.cyan,
        Color.orange,
        Color.pink
    };

    public final short lines, columns;
    private short[][] tiles;

    public Board(short lines, short columns) {
        this.lines = lines;
        this.columns = columns;
        tiles = new short[lines][columns];

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

    public void drawBlock(Block block, short pos, int col, int line) {
        Block.Position position = block.positions[pos];
        for (short t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            tiles[line+tile.dy][col+tile.dx] = block.color;
        }
    }

    public boolean checkBlock(Block block, short pos, short col, short line) {
        Block.Position position = block.positions[pos];
        for (short t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            short l = (short)(line + tile.dy) , c = (short)(col + tile.dx);
            if (l < 0 || l >= columns) {
                return false;
            }
            if (c < 0 || c >= lines ) {
                return false;
            }
            if (tiles[line+tile.dy][col+tile.dx] != 0) {
                return false;
            }
        }
        return true;
    }

    public void paint(Graphics g) {  
        g.setColor(Color.white);
        g.fill3DRect(0, 0, TILE_SIZE, getHeight()-TILE_SIZE, true);
        g.fill3DRect(getWidth()-TILE_SIZE, 0, TILE_SIZE, getHeight()-TILE_SIZE, true);
        g.fill3DRect(0, getHeight()-TILE_SIZE, getWidth(), TILE_SIZE, true);

        for (short line = 0; line < lines; line++) {
            for (short col = 0; col < columns; col++) {
                if (tiles[line][col] != 0) {
                    drawTile(g, col, line, COLORS[(tiles[line][col]-1) % COLORS.length]);
                }
            }
        }
    }
}
