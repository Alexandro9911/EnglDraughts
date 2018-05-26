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

    /*  public void EatInDirect(Cell currCell, Cell objectOfEating) {
          Chip currChip = get(currCell);
          Chip eatingChip = get(objectOfEating);
          Cell direction = getDirection(currCell, objectOfEating);
          Cell end = new Cell(2*direction.getX(), 2*direction.getY());
          if(hasEnemy(currCell, direction) && canGoInDir(currCell, end)){
              chips.remove(objectOfEating);
              objectOfEating = null;
              Chip newChip = get(end);
              newChip = currChip;

          }
      }
   */
    public Cell getDirection(Cell start, Cell end) {
        Cell answ = new Cell(0, 0);
        int dirX = end.getX() - start.getX();
        int dirY = end.getY() - start.getY();
        Cell dir = new Cell(dirX, dirY);
        if (checkVectorDir(dir)) {
            answ = dir;
        }
        return answ;
    }

    public boolean canEat(Cell currCell) {
        boolean answ = false;
        for (Cell direction : DIRECTIONS) {
            Cell end = new Cell(2 * direction.getX(), 2 * direction.getY());
            if (!chips.containsKey(end) && (hasEnemy(currCell, direction))) {
                answ = true;
                break;
            }
        }
        return answ;
    }

    private boolean hasEnemy(Cell currCell, Cell dir) {
        boolean answ = false;
        Chip currChip = get(currCell);
        for (Cell direction : DIRECTIONS) {
            Chip potencialEnemy = get(direction);
            if (currChip != potencialEnemy) {
                answ = true;
                break;
            }
        }
        return answ;
    }

    public void makeQueen(Cell current) {
        Chip chip = get(current);
        int x = current.getX();
        int y = current.getY();
        if (chip == Chip.GREEN && y == 8) {
            chip = Chip.GREENQUEEN;
        } else {
            if (chip == Chip.RED && y == 1) {
                chip = Chip.REDQUEEN;
            }
        }
    }

  /*  public void go(Cell curr, Cell dir) {
        if (canGoinGeneral(curr)) {
            Cell next = curr.plus(dir);
            Chip chip = get(curr);
            newChip = currChip;
            chips.remove(curr);
            curr = null;
        }
    } */

    public boolean canGoInDir(Cell current, Cell dir) {
        boolean answ = false;
        Cell next = current.plus(dir);
        return answ;
    }

    private boolean canGoinGeneral(Cell current) {
        boolean answ = false;
        for (Cell direction : DIRECTIONS) {
            Cell next = current.plus(direction);
            if (!chips.containsKey(next)) {
                if (get(current) == Chip.GREEN && (direction == DIRECTIONS[0] || direction == DIRECTIONS[1])) {
                    answ = true;
                }
                if (get(current) == Chip.RED && (direction == DIRECTIONS[3] || direction == DIRECTIONS[2])) {
                    answ = true;
                }
            }

        }
        return answ;
    }

    public Cell checkEater() {
        boolean answ = false;
        Cell missedEater = new Cell(100, 100);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = new Cell(x, y);
                if (canEat(cell)) {
                    answ = true;
                    missedEater = cell;
                    break;
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
            cell = null;
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
            new Cell(-1, 1), new Cell(1, 1),
            new Cell(-1, -1), new Cell(1, -1)
    };

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

