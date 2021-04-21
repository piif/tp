import java.awt.*;  

class Board extends Canvas {
    public final int TILE_SIZE=18;
    static public final Color COLORS[] = {
        null,
        Color.yellow,
        Color.red,
        Color.green,
        Color.blue,
        Color.cyan,
        Color.orange,
        Color.pink,
        Color.magenta,
        Color.lightGray,
        Color.green,
        Color.white
    };
    private HexagonHelper hexagonHelper;

    private class HexagonHelper {
        final int xDelta[] = { 0, 11, 15, 11,  0, -4};
        final int yDelta[] = { 0,  0,  9, 18, 18,  9};

        int[] xBorders  = new int[2*lines+3], yBorders  = new int[2*lines+3];

        HexagonHelper(int lines, int columns) {
            int rightDelta = 4*lines + 2*columns - 1;
            int rightZero = 15 * columns + 6;
            xBorders  = new int[rightDelta + 6];
            yBorders  = new int[rightDelta + 6];

            for (int i = 0; i < lines; i++) {
                // left zigzag
                xBorders[i*2] = 10;
                yBorders[i*2] = i*18;
                xBorders[i*2 + 1] = 6;
                yBorders[i*2 + 1] = i*18 + 9;
                // right zigzag
                xBorders[rightDelta - i*2] = rightZero;
                yBorders[rightDelta - i*2] = i*18 + 9;
                xBorders[rightDelta - i*2 - 1] = rightZero + 4;
                yBorders[rightDelta - i*2 - 1] = i*18 + 18;
            }
            // bottom line
            int y = lines*18;
            for (int i=2*lines, x = 10; i < 2*(lines+columns); i+=4, x+=30) {
                xBorders[i] = x;
                yBorders[i] = y;
                xBorders[i + 1] = x+11;
                yBorders[i + 1] = y;
                xBorders[i + 2] = x+11+4;
                yBorders[i + 2] = y+9;
                xBorders[i + 3] = x+11+4+11;
                yBorders[i + 3] = y+9;
            }

            // outline lines
            xBorders[rightDelta + 1] = rightZero;
            yBorders[rightDelta + 1] = 0;
            xBorders[rightDelta + 2] = rightZero+10;
            yBorders[rightDelta + 2] = 0;
            xBorders[rightDelta + 3] = rightZero+10;
            yBorders[rightDelta + 3] = lines*18 + 10;
            xBorders[rightDelta + 4] = 0;
            yBorders[rightDelta + 4] = lines*18 + 10;
            xBorders[rightDelta + 5] = 0;
            yBorders[rightDelta + 5] = 0;
        }

        void draw(Graphics g, int c, int l, int color) {
            int x, y;
            if (c % 2 == 0) {
                x = c * 15 + 10;
                y = l * 18;
            } else {
                x = (c-1) * 15 + 25;
                y = l * 18 + 9;
            }
            int[] xPoints = new int[6], yPoints = new int[6];
            for (int i = 0; i < 6; i++) {
                xPoints[i] = x+xDelta[i];
                yPoints[i] = y+yDelta[i];
            }
            if (color != -1) {
                g.setColor(Board.COLORS[color % Board.COLORS.length]);
                g.fillPolygon(xPoints, yPoints, 6);
            }
            g.setColor(Color.gray);
            g.drawPolygon(xPoints, yPoints, 6);
        }

        void drawBorders(Graphics g) {
            g.setColor(Color.white);
            g.fillPolygon(xBorders, yBorders, xBorders.length);
        }
    }

    public final int lines, columns;
    private int[][] tiles;

    public Board(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        tiles = new int[lines][columns];
        hexagonHelper = new HexagonHelper(lines, columns);

        setBackground(Color.DARK_GRAY);
        setSize(getWidth(), getHeight());
    }

    public int getWidth() {
        return 30 * columns/2 + 21;
    }
    public int getHeight() {
        return lines*18 + 10;
    }

    public void setTile(int col, int line, int color) {
        tiles[line][col] = color;
    }

    public int getTile(int col, int line) {
        return tiles[line][col];
    }

    private void drawBlock(Block block, int pos, int col, int line, int color) {
        // System.out.println("drawBlock "+pos+", "+col+", "+line);
        Block.Position position = block.positions[pos];
        for (int t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            int l = line+tile.dy , c = col+tile.dx;
            if (col % 2 == 1 && c % 2 == 0) {
                l++;
            }
            tiles[l][c] = color;
        }
    }
    public void drawBlock(Block block, int pos, int col, int line) {
        drawBlock(block, pos, col, line, block.color);
    }
    public void drawGhost(Block block, int pos, int col, int line) {
        drawBlock(block, pos, col, line, -1);
    }
    public void removeBlock(Block block, int pos, int col, int line) {
        drawBlock(block, pos, col, line, 0);
    }

    public boolean checkBlock(Block block, int pos, int col, int line) {
        Block.Position position = block.positions[pos];
        for (int t=0; t<4; t++) {
            Block.Tile tile = position.tiles[t];
            int l = line + tile.dy , c = col + tile.dx;
            if (col % 2 == 1 && c % 2 == 0) {
                l++;
            }
            if (l < 0 || l >= lines) {
                return false;
            }
            if (c < 0 || c >= columns) {
                return false;
            }
            if (tiles[l][c] != 0) {
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

    public boolean halfLineIsFull(int line) {
        for (int c = 0; c < columns ; c++) {
            if (tiles[line - (c%2)][c] == 0) {
                return false;
            }
        }
        return true;
    }

    public void shiftDownHalf(int line) {
        if (line > 0) {
            for (int c = 1; c < columns ; c+=2) {
                tiles[line - 1][c] = tiles[line][c];
            }
            System.arraycopy(tiles, 0, tiles, 1, line);
        }
        tiles[0] = new int[columns];
    }

    public void paint(Graphics g) {  
        hexagonHelper.drawBorders(g);

        for (int line = 0; line < lines; line++) {
            for (int col = 0; col < columns; col++) {
                // remove "if" to display dark blocks, but implies flickering
                if (tiles[line][col] != 0) {
                    hexagonHelper.draw(g, col, line, tiles[line][col]);
                }
            }
        }
    }
}
