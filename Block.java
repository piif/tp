/**
 * this is one basic hextris block, described by a list of relative tiles
 *  coordinates, foreach block position
 */
public class Block {
    public static final int POSITIONS_PER_BLOCK=6;
    public static final int TILES_PER_BLOCK=4;

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
            tiles= new Tile[TILES_PER_BLOCK];
            for(int t=0; t<TILES_PER_BLOCK; t++) {
                tiles[t] = new Tile(tileArray[t][0], tileArray[t][1]);
            }
        }
    }

    public final Position positions[];
    public final int color;

    public static final Block blocks[];
    
    static {
        int allBlocks[][][][] = {
            // Y
            {
                { { -1,  0 }, { 0, 0 }, {  1, 0 }, { 0, 1 } },
                { {  0, -1 }, { 0, 0 }, { -1, 1 }, { 1, 1 } },
                { { -1,  0 }, { 0, 0 }, {  1, 0 }, { 0, 1 } },
                { {  0, -1 }, { 0, 0 }, { -1, 1 }, { 1, 1 } },
                { { -1,  0 }, { 0, 0 }, {  1, 0 }, { 0, 1 } },
                { {  0, -1 }, { 0, 0 }, { -1, 1 }, { 1, 1 } }
            },

            // O
            {
                { {  0, -1 }, {  1, -1 }, {  0,  0 }, {  1,  0 } },
                { { -1, -1 }, {  0, -1 }, {  1, -1 }, {  0,  0 } },
                { { -1, -1 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -1, -1 }, { -1,  0 }, {  0,  0 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, {  0,  1 } },
                { {  1, -1 }, {  0,  0 }, {  1,  0 }, {  0,  1 } }
            },

            // L
            {
                { {  0, -2 }, {  0, -1 }, {  0,  0 }, {  1,  0 } },
                { { -2, -1 }, { -1, -1 }, {  1, -1 }, {  0,  0 } },
                { {  0, -1 }, { -1,  0 }, {  0,  0 }, { -2,  1 } },
                { { -1, -1 }, {  0,  0 }, {  0,  1 }, {  0,  2 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, {  2,  1 } },
                { {  1, -1 }, {  2, -1 }, {  0,  0 }, {  0,  1 } }
            },

            // J
            {
                { {  0, -2 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -2, -1 }, { -1, -1 }, {  0,  0 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, { -2,  1 } },
                { {  1, -1 }, {  0,  0 }, {  0,  1 }, {  0,  2 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  2,  1 } },
                { { -1, -1 }, {  1, -1 }, {  2, -1 }, {  0,  0 } }
            },

            // C
            {
                { {  0, -2 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -2, -1 }, { -1, -1 }, {  0,  0 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, { -2,  1 } },
                { {  1, -1 }, {  0,  0 }, {  0,  1 }, {  0,  2 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  2,  1 } },
                { { -1, -1 }, {  1, -1 }, {  2, -1 }, {  0,  0 } }
            },

            // I
            {
                { {  0, -2 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -2, -1 }, { -1, -1 }, {  0,  0 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, { -2,  1 } },
                { {  1, -1 }, {  0,  0 }, {  0,  1 }, {  0,  2 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  2,  1 } },
                { { -1, -1 }, {  1, -1 }, {  2, -1 }, {  0,  0 } }
            },

            // S
            {
                { {  1, -1 }, {  0,  0 }, { -1,  1 }, {  0,  1 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  1,  1 } },
                { { -1, -1 }, {  1, -1 }, {  0,  0 }, {  2,  0 } },
                { {  1, -2 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -1, -2 }, { -1, -1 }, {  0,  0 }, {  0,  1 } },
                { { -2,  0 }, { -1,  0 }, {  0,  0 }, {  1,  0 } }
            },

            // Z
            {
                { {  1, -1 }, {  0,  0 }, { -1,  1 }, {  0,  1 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  1,  1 } },
                { { -1, -1 }, {  1, -1 }, {  0,  0 }, {  2,  0 } },
                { {  1, -2 }, {  0, -1 }, { -1,  0 }, {  0,  0 } },
                { { -1, -2 }, { -1, -1 }, {  0,  0 }, {  0,  1 } },
                { { -2,  0 }, { -1,  0 }, {  0,  0 }, {  1,  0 } }               
            },

            // b
            {
                { {  0, -1 }, {  0,  0 }, {  0,  1 }, {  1,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, {  1,  1 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, { -1,  1 } },
                { {  0, -1 }, { -1,  0 }, {  0,  0 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, { -1,  1 }, {  1,  1 } },
                { {  0,  0 }, {  1,  0 }, { -1,  1 }, {  0,  1 } },               
            },

            // d
            {
                { {  0, -1 }, {  0,  0 }, { -1,  1 }, {  0,  1 } },
                { { -1,  0 }, {  0,  0 }, {  0,  1 }, {  1,  1 } },
                { {  0,  0 }, {  1,  0 }, { -1,  1 }, {  1,  1 } },
                { {  0, -1 }, {  0,  0 }, {  1,  0 }, {  0,  1 } },
                { {  0, -1 }, { -1,  0 }, {  0,  0 }, {  1,  1 } },
                { { -1,  0 }, {  0,  0 }, {  1,  0 }, { -1,  1 } }
            }
        };
        blocks = new Block[allBlocks.length];
        for (int b=0; b<allBlocks.length; b++) {
            blocks[b] = new Block(b+1, allBlocks[b]);
        }
    }

    private Block(int color, int positionsArray[][][]) {
        this.color = color;
        positions = new Position[POSITIONS_PER_BLOCK];
        for (int p=0; p<POSITIONS_PER_BLOCK; p++) {
            positions[p] = new Position(positionsArray[p]);
        }
    }

    public static Block randomBlock() {
        return blocks[(int)(Math.random() * blocks.length)];
    }
}