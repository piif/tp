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
    private TriangleHelper triangleHelper;

    private class TriangleHelper {
        final int xDelta[] = { 0, 10, -10 };
        final int yDelta[] = { 0, 18,  18 };

        int[] xBorders  = new int[2*lines+3], yBorders  = new int[2*lines+3];

        TriangleHelper(int lines, int columns) {
            int rightDelta = 2*lines + 2 - 1;
            int rightZero = 10 + 11 * columns + 11;
            xBorders  = new int[rightDelta + 6];
            yBorders  = new int[rightDelta + 6];

            for (int i = 0; i < lines; i += 2) {
                // left zigzag
                xBorders[i] = 10 + 11;
                yBorders[i] = i*18;
                xBorders[i + 1] = 10;
                yBorders[i + 1] = (i+1)*18;
                // right zigzag
                xBorders[rightDelta - i] = rightZero;
                yBorders[rightDelta - i] = i*18;
                xBorders[rightDelta - i - 1] = rightZero - 11;
                yBorders[rightDelta - i - 1] = (i+1)*18;
            }
            // bottom line
            xBorders[lines] = 10 + 11;
            yBorders[lines] = lines*18;
            xBorders[lines + 1] = rightZero;
            yBorders[lines + 1] = lines*18;

            // outline lines
            xBorders[rightDelta + 1] = rightZero + 10;
            yBorders[rightDelta + 1] = 0;
            xBorders[rightDelta + 2] = rightZero + 10;
            yBorders[rightDelta + 2] = lines*18 + 10;
            xBorders[rightDelta + 3] = 0;
            yBorders[rightDelta + 3] = lines*18 + 10;
            xBorders[rightDelta + 4] = 0;
            yBorders[rightDelta + 4] = 0;
        }

        void draw(Graphics g, int c, int l, int color) {
            int x, y;
            int[] xPoints = new int[3], yPoints = new int[3];
            x = c * 11 + 10 + 10;
            if (l % 2 == c % 2) {
                y = l * 18;
                for (int i = 0; i < 3; i++) {
                    xPoints[i] = x + xDelta[i];
                    yPoints[i] = y + yDelta[i];
                }
            } else {
                y = l * 18 + 18;
                for (int i = 0; i < 3; i++) {
                    xPoints[i] = x - xDelta[i];
                    yPoints[i] = y - yDelta[i];
                }
            }
            if (color != -1) {
                g.setColor(Board.COLORS[color % Board.COLORS.length]);
                g.fillPolygon(xPoints, yPoints, 3);
            }
            g.setColor(Color.gray);
            g.drawPolygon(xPoints, yPoints, 3);
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
        triangleHelper = new TriangleHelper(lines, columns);

        setBackground(Color.DARK_GRAY);
        setSize(getWidth(), getHeight());
    }

    public int getWidth() {
        return 11 * columns + 11 + 20;
    }
    public int getHeight() {
        return lines*19 + 10;
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
        triangleHelper.drawBorders(g);

        for (int line = 0; line < lines; line++) {
            for (int col = 0; col < columns; col++) {
                // remove "if" to display dark blocks, but implies flickering
                if (tiles[line][col] != 0) {
                    triangleHelper.draw(g, col, line, tiles[line][col]);
                }
            }
        }
    }
}
