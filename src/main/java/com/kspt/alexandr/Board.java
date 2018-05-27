package com.kspt.alexandr;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public class Board {

    private final int width;
    private final int height;

    @NotNull
    public Map<Cell, Chip> chips = new HashMap<Cell, Chip>();


    public Board(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void eat(Cell currCell, Cell objectOfEating) {
        Cell direction = getDirection(currCell, objectOfEating);
        if (canEat(currCell, objectOfEating) && personalTraectory(get(currCell), direction)) {
            chips.remove(objectOfEating);
            go2(currCell, direction);
        }
    }

    public Cell getDirection(Cell start, Cell end) {
        Cell answ = new Cell(0, 0);
        int startX = start.getX();
        int startY = start.getY();
        if (end.getX() != startX && end.getY() != startY) {
            if (end.getX() > startX && end.getY() > startY) {
                return new Cell(1, 1);
            }
            if (end.getX() < startX && end.getY() > startY) {
                return new Cell(-1, 1);
            }
            if (end.getX() < startX && end.getY() < startY) {
                return new Cell(-1, -1);
            }
            if (end.getX() > startX && end.getY() < startY) {
                return new Cell(1, -1);
            }
        }
        return answ;
    }

    public boolean canEat(Cell currCell, Cell objectOfEating) {
        boolean answ = false;
        Cell dir = getDirection(currCell, objectOfEating);
        Cell end = currCell.plus2(dir);
        if (correctCoord(end) && !chips.containsKey(end) && hasEnemy(currCell) && personalTraectory(get(currCell), dir)) {
            answ = true;
        }
        return answ;
    }

    public boolean canEatInGeneral(Cell currCell) {
        boolean answ = false;
        for (Cell direction : DIRECTIONS) {
            Cell end = currCell.plus2(direction);
            if (!chips.containsKey(end) && canEat(currCell, currCell.plus(direction))) {
                answ = true;
                break;
            }
        }
        return answ;
    }

    private boolean correctCoord(Cell cell) {
        boolean answ = false;
        if ((cell.getX() >= 0 && cell.getX() <= 7) && (cell.getY() >= 0 && cell.getY() <= 7)) {
            answ = true;
        }
        return answ;
    }

    boolean hasEnemy(Cell currCell) {
        boolean answ = false;
        Chip currChip = get(currCell);
        for (Cell dir : DIRECTIONS) {
            Cell enemy = currCell.plus(dir);
            Chip enemyChip = get(enemy);
            Cell end = currCell.plus2(dir);
            if (chips.containsKey(enemy) && currChip != enemyChip && !chips.containsKey(end)) {
                answ = true;
            }
        }
        return answ;
    }

    public void makeQueen(Cell current) {
        Chip chip = get(current);
        int x = current.getX();
        int y = current.getY();
        if (chip == Chip.GREEN && y == 7) {
            chip = chip.turnQueen(chip);
        } else {
            if (chip == Chip.RED && y == 0) {
                chip = chip.turnQueen(chip);
            }
        }
        Cell newCell = new Cell(current.getX(), current.getY());
        chips.remove(current);
        chips.put(newCell, chip);
    }

    public void go(Cell curr, Cell dir) {
        Chip chip = get(curr);
        Cell newCell = curr.plus(dir);
        if (correctCoord(newCell) && canGoThere(curr, dir)) {
            chips.remove(curr);
            chips.put(newCell, chip);
        }
    }

    public void go2(Cell curr, Cell dir) {
        Cell end = curr.plus2(dir);
        Chip chip = get(curr);
        if (correctCoord(end) && canGoThere(curr, dir)) {
            chips.put(end, chip);
            chips.remove(curr);
        }
    }

    public boolean canGoThere(Cell current, Cell dir) {
        boolean answ = false;
        Cell next = current.plus(dir);
        if (!chips.containsKey(next) && checkVectorDir(dir)) {
            if (get(current) == Chip.GREEN && ((dir.getX() == 1 && dir.getY() == 1) || dir.getX() == -1 && dir.getY() == 1)) {
                answ = true;
            }
            if (get(current) == Chip.RED && ((dir.getX() == -1 && dir.getY() == -1) || dir.getX() == 1 && dir.getY() == -1)) {
                answ = true;
            }
            if ((get(current) == Chip.REDQUEEN || get(current) == Chip.GREENQUEEN)) {
                answ = true;
            }
        }
        return answ;
    }

    private boolean personalTraectory(Chip chip, Cell dir) {
        boolean answ = false;
        if (chip == Chip.GREEN && (dir.equals(new Cell(1, 1)) || dir.equals(new Cell(-1, 1)))) {
            answ = true;
        }
        if (chip == Chip.RED && (dir.equals(new Cell(-1, -1)) || dir.equals(new Cell(1, -1)))) {
            answ = true;
        }
        if ((chip == Chip.GREENQUEEN || chip == Chip.REDQUEEN)) {
            answ = true;
        }
        return answ;
    }


    public Cell checkEater() {
        boolean answ = false;
        Cell missedEater = null;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = new Cell(x, y);
                if (canEatInGeneral(cell)) {
                    answ = true;
                    missedEater = cell;

                }
            }
            if (!answ) {
                break;
            }

        }
        return missedEater;
    }

    public void eatMissEater(Cell cell) {
        if (cell.equals(checkEater())) {
            chips.remove(cell);
        }
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

    private boolean checkVectorDir(Cell dir) {
        boolean answ = false;
        for (Cell direction : DIRECTIONS) {
            if (dir.equals(direction)) {
                answ = true;
                break;
            }
        }
        return answ;
    }

    static private final Cell[] DIRECTIONS = new Cell[]{
            new Cell(1, 1), new Cell(-1, 1),
            new Cell(-1, -1), new Cell(1, -1)
    };


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void startGame() {
        chips.put(new Cell(1, 7), Chip.RED);
        chips.put(new Cell(3, 7), Chip.RED);
        chips.put(new Cell(5, 7), Chip.RED);
        chips.put(new Cell(7, 7), Chip.RED);
        chips.put(new Cell(0, 6), Chip.RED);
        chips.put(new Cell(2, 6), Chip.RED);
        chips.put(new Cell(4, 6), Chip.RED);
        chips.put(new Cell(6, 6), Chip.RED);
        chips.put(new Cell(1, 5), Chip.RED);
        chips.put(new Cell(3, 5), Chip.RED);
        chips.put(new Cell(5, 5), Chip.RED);
        chips.put(new Cell(7, 5), Chip.RED);

        chips.put(new Cell(0, 2), Chip.GREEN);
        chips.put(new Cell(2, 2), Chip.GREEN);
        chips.put(new Cell(4, 2), Chip.GREEN);
        chips.put(new Cell(6, 2), Chip.GREEN);
        chips.put(new Cell(1, 1), Chip.GREEN);
        chips.put(new Cell(3, 1), Chip.GREEN);
        chips.put(new Cell(5, 1), Chip.GREEN);
        chips.put(new Cell(7, 1), Chip.GREEN);
        chips.put(new Cell(0, 0), Chip.GREEN);
        chips.put(new Cell(2, 0), Chip.GREEN);
        chips.put(new Cell(4, 0), Chip.GREEN);
        chips.put(new Cell(6, 0), Chip.GREEN);
    }
}

