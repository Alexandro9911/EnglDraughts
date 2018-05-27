package com.kspt.alexandr;

public final class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    Cell plus(Cell other) {
        return new Cell(x + other.x,y + other.y);
    }
    Cell plus2(Cell dir){
        return new Cell(x+ 2*dir.x, y + 2 * dir.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Cell) {
            Cell cell = (Cell) other;
            return x == cell.x && y == cell.y;
        }
        return false;
    }
    @Override
    public int hashCode() {
        int result = 11;
        result = 19 * result + x;
        return 19 * result + y;
    }
}
