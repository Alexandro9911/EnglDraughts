package com.kspt.alexandr;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class Board {

    private final int width;
    private final int height;

    @NotNull
    private final Map<Cell, Chip> chips = new HashMap<Cell, Chip>();


    public Board(int width, int height) {
        this.width = width;
        this.height =  height;
    }

    public Board() {
        this(8, 8);
    }

    @Nullable
    public Chip get(int x, int y) {
        return get(new Cell(x, y));
    }

    @Nullable
    public Chip get(Cell cell) {
        return chips.get(cell);
    }

    public boolean hasFreeCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (get(x, y) == null) return true;
            }
        }
        return false;
    }

    public List<Cell> listCellsWithChip() {
        List<Cell> answ = new ArrayList<Cell>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (get(x, y) != null) {
                    Cell cell = new Cell(x, y);
                    answ.add(cell);
                }
            }
        }
        return answ;
    }

    String checkWinner() {
        int greenCount = 0;
        int redCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (get(x, y) == Chip.GREEN) {
                    greenCount++;
                } else {
                    redCount++;
                }
            }
        }
        if (redCount > greenCount) return "Winner: Green player";
        else return "Winner: Red player";
    }


    static private final Cell[] DIRECTIONS = new Cell[]{
            new Cell(1, 1), new Cell(1, -1),
            new Cell(1, -1), new Cell(-1, -1)
    };

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

