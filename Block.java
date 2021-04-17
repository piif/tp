/**
 * this is one basic tetris block, described by a list of relative tiles
 *  coordinates, foreach 4 block positions
 */
public class Block {
    public class Tile {
        public int dx, dy;
        Tile(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    public class Position {
        final Tile[] tiles;

        Position(int tileArray[][]) {
            tiles= new Tile[4];
            for(int t=0; t<4; t++) {
                tiles[t] = new Tile(tileArray[t][0], tileArray[t][1]);
            }
        }
    }

    public final Position positions[];
    public final int color;

    public static final Block blocks[];
    
    static {
        int allBlocks[][][][] = {
            // bar
            {
                { { 0, 0 }, {  0, -1 }, {  0,  1 }, { 0, 2 } },
                { { 0, 0 }, { -1,  0 }, {  1,  0 }, { 2, 0 } },
                { { 0, 0 }, {  0, -2 }, {  0, -1 }, { 0, 1 } },
                { { 0, 0 }, { -2,  0 }, { -1,  0 }, { 1, 0 } },
            },
            // square
            {
                { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } },
                { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }
            },
            // T
            {
                { { 0, 0 }, { 0, -1 }, { -1, 0 }, { 1, 0 } },
                { { 0, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } },
                { { 0, 0 }, { 0,  1 }, { -1, 0 }, { 1, 0 } },
                { { 0, 0 }, { 0, -1 }, {  1, 0 }, { 0, 1 } }
            },
            // s
            {
                { { 0, 0 }, { 0,  1 }, {  1, 0 }, { -1,  1 } },
                { { 0, 0 }, { 0, -1 }, {  1, 0 }, {  1,  1 } },
                { { 0, 0 }, { 0,  1 }, {  1, 0 }, { -1,  1 } },
                { { 0, 0 }, { 0,  1 }, { -1, 0 }, { -1, -1 } }
            },
            // z
            {
                { { 0, 0 }, { -1,  0 }, {  0, 1 }, { 1,  1 } },
                { { 0, 0 }, {  0,  1 }, {  1, 0 }, { 1, -1 } },
                { { 0, 0 }, { -1,  0 }, {  0, 1 }, { 1,  1 } },
                { { 0, 0 }, {  0,  1 }, { -1, 0 }, { -1, -1 } },
            },
            // L
            {
                { { 0, 0 }, {  0, -1 }, { 1,  0 }, { 2,  0 } },
                { { 0, 0 }, { -1,  0 }, { 0, -1 }, { 0, -2 } },
                { { 0, 0 }, { -2,  0 }, { -1, 0 }, { 0,  1 } },
                { { 0, 0 }, {  1, 0 }, {  0,  1 }, { 0,  2 } }
            },
            // inverted L
            {
                { { 0, 0 }, {  1,  0 }, { 2,  0 }, { 0,  1 } },
                { { 0, 0 }, {  1,  0 }, { 0, -1 }, { 0, -2 } },
                { { 0, 0 }, { -2,  0 }, { -1, 0 }, { 0, -1 } },
                { { 0, 0 }, { -1, 0 }, {  0,  1 }, { 0,  2 } }
            }
        };
        blocks = new Block[allBlocks.length];
        for (int b=0; b<allBlocks.length; b++) {
            blocks[b] = new Block(b+1, allBlocks[b]);
        }
    }

    private Block(int color, int positionsArray[][][]) {
        this.color = color;
        positions = new Position[4];
        for (int p=0; p<4; p++) {
            positions[p] = new Position(positionsArray[p]);
        }
    }

    public static Block randomBlock() {
        return blocks[(int)(Math.random() * blocks.length)];
    }
}