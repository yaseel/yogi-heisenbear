package model.level.tile;

public class TileVariantCalculator {

    public static void calculateVariants(Tile[][] grid, int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Tile tile = grid[row][col];
                if (tile.getType() == Tile.Type.AIR)
                    continue;

                boolean hasTop = hasMatchingNeighbor(grid, row - 1, col, tile.getType(), height, width);
                boolean hasBottom = hasMatchingNeighbor(grid, row + 1, col, tile.getType(), height, width);
                boolean hasLeft = hasMatchingNeighbor(grid, row, col - 1, tile.getType(), height, width);
                boolean hasRight = hasMatchingNeighbor(grid, row, col + 1, tile.getType(), height, width);

                int variant = determineVariant(hasTop, hasBottom, hasLeft, hasRight);
                tile.setVariant(variant);
            }
        }
    }

    private static boolean hasMatchingNeighbor(Tile[][] grid, int row, int col, Tile.Type type, int height, int width) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return false;
        }
        return grid[row][col].getType() == type;
    }

    private static int determineVariant(boolean top, boolean bottom, boolean left, boolean right) {
        // corners
        if (!top && !left && bottom && right) return Tile.VARIANT_TOP_LEFT;
        if (!top && !right && bottom && left) return Tile.VARIANT_TOP_RIGHT;
        if (!bottom && !left && top && right) return Tile.VARIANT_BOTTOM_LEFT;
        if (!bottom && !right && top && left) return Tile.VARIANT_BOTTOM_RIGHT;

        // horizontal
        if (!top && !bottom && !left && right) return Tile.VARIANT_HORIZONTAL_LEFT;
        if (!top && !bottom && left && right) return Tile.VARIANT_HORIZONTAL_CENTER;
        if (!top && !bottom && left && !right) return Tile.VARIANT_HORIZONTAL_RIGHT;

        // vertical
        if (!top && bottom && !left && !right) return Tile.VARIANT_VERTICAL_TOP;
        if (top && bottom && !left && !right) return Tile.VARIANT_VERTICAL_CENTER;
        if (top && !bottom && !left && !right) return Tile.VARIANT_VERTICAL_BOTTOM;

        // isolated tile
        return Tile.VARIANT_SINGLE;
    }
}
