package com.kspt.alexandr;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void eatTest() {
        Board board = new Board();
        Cell cellGreen = new Cell(1, 1);
        Cell cellRed = new Cell(2, 2);
        board.chips.put(cellGreen, Chip.GREEN);
        board.chips.put(cellRed, Chip.RED);
        board.eat(cellGreen, cellRed);
        assertEquals(false, board.chips.containsKey(cellRed));
        assertEquals(false, board.chips.containsValue(Chip.RED));
        assertEquals(true, board.chips.containsValue(Chip.GREEN));
    }

    @Test
    public void go2() {
        Board board = new Board();
        Cell start = new Cell(2, 2);
        board.chips.put(start, Chip.GREEN);
        Cell dir = new Cell(1, 1);
        board.go2(start, dir);
        assertEquals(false, board.chips.containsKey(start));
        assertEquals(true, board.chips.containsKey(start.plus2(dir)));
        assertEquals(false, board.chips.containsKey(start.plus(dir)));
        assertEquals(Chip.GREEN, board.chips.get(start.plus2(dir)));
    }

    @Test
    public void getDirectionTest() {
        Board board = new Board();
        Cell start1 = new Cell(1, 1);
        Cell end1 = new Cell(4, 4);
        Cell start2 = new Cell(3, 2);
        Cell end2 = new Cell(1, 4);
        assertEquals(new Cell(1, 1), board.getDirection(start1, end1));
        assertEquals(new Cell(-1, 1), board.getDirection(start2, end2));
    }

    @Test
    public void canEatTest() {
        Board board = new Board();
        Cell green = new Cell(1, 1);
        Cell red = new Cell(2, 2);
        Cell green2 = new Cell(0, 2);
        board.chips.put(green, Chip.GREEN);
        board.chips.put(red, Chip.RED);
        board.chips.put(green2, Chip.GREEN);
        assertEquals(true, board.canEat(green, red));
        assertEquals(false, board.canEat(green, green2));
    }

    @Test
    public void canEatInGeneralTest() {
        Board board = new Board();
        Cell green1 = new Cell(0, 0);
        Cell red = new Cell(1, 1);
        Cell green2 = new Cell(5, 6);
        board.chips.put(green1, Chip.GREEN);
        board.chips.put(red, Chip.RED);
        board.chips.put(green2, Chip.GREEN);
        assertEquals(true, board.canEatInGeneral(green1));
        assertEquals(false, board.canEatInGeneral(red));
    }

    @Test
    public void hasEnemyTest() {
        Board board = new Board();
        Cell green = new Cell(3, 2);
        Cell red = new Cell(2, 3);
        Cell red2 = new Cell(3, 5);
        board.chips.put(green, Chip.GREEN);
        board.chips.put(red, Chip.RED);
        board.chips.put(red2, Chip.RED);
        assertEquals(true, board.hasEnemy(green));
        assertEquals(false, board.hasEnemy(red2));
    }

    @Test
    public void makeQueenTest() {
        Board board = new Board();
        Cell green = new Cell(3, 7);
        board.chips.put(green, Chip.GREEN);
        board.makeQueen(green);
        assertEquals(true, board.chips.containsValue(Chip.GREENQUEEN));
        assertEquals(false, board.chips.containsValue(Chip.GREEN));
    }

    @Test
    public void goTest() {
        Board board = new Board();
        Cell start = new Cell(2, 2);
        board.chips.put(start, Chip.GREEN);
        Cell dir = new Cell(1, 1);
        board.go(start, dir);
        assertEquals(false, board.chips.containsKey(start));
        assertEquals(true, board.chips.containsKey(start.plus(dir)));
        assertEquals(Chip.GREEN, board.chips.get(start.plus(dir)));
    }

    @Test
    public void canGoThereTest() {
        Board board = new Board();
        Cell green = new Cell(3, 2);
        Cell red = new Cell(5, 2);
        Cell greenQueen = new Cell(6, 3);
        Cell redQueen = new Cell(1, 4);
        board.chips.put(green, Chip.GREEN);
        board.chips.put(red, Chip.RED);
        board.chips.put(redQueen, Chip.REDQUEEN);
        board.chips.put(greenQueen, Chip.GREENQUEEN);
        assertEquals(true, board.canGoThere(green, new Cell(1, 1)));
        assertEquals(false, board.canGoThere(green, new Cell(-1, -1)));
        assertEquals(true, board.canGoThere(red, new Cell(-1, -1)));
        assertEquals(false, board.canGoThere(red, new Cell(1, 1)));
        assertEquals(false, board.canGoThere(greenQueen, new Cell(-1, -1)));
        assertEquals(true, board.canGoThere(redQueen, new Cell(1, 1)));
    }

    @Test
    public void checkEaterTest() {
        Board board = new Board();
        Cell red1 = new Cell(1, 1);
        Cell green2 = new Cell(0, 0);
        board.chips.put(red1, Chip.RED);
        board.chips.put(green2, Chip.GREEN);
        assertEquals(green2, board.checkEater());
    }

    @Test
    public void eatMissEaterTest() {
        Board board = new Board();
        Cell red1 = new Cell(1, 1);
        Cell green2 = new Cell(0, 0);
        board.chips.put(red1, Chip.RED);
        board.chips.put(green2, Chip.GREEN);
        board.eatMissEater(board.checkEater());
        assertEquals(false, board.chips.containsValue(board.get(green2)));
    }
    @Test
    public void checkWinnerTest(){
        Board board = new Board();
        board.chips.put(new Cell(1, 7), Chip.RED);
        board.chips.put(new Cell(3, 7), Chip.RED);
        board.chips.put(new Cell(5, 7), Chip.RED);
        board.chips.put(new Cell(7, 7), Chip.REDQUEEN);
        board.chips.put(new Cell(0, 2), Chip.GREEN);
        board.chips.put(new Cell(2, 2), Chip.GREEN);
        board.chips.put(new Cell(4, 2), Chip.GREEN);
        String res = board.checkWinner();
        assertEquals("Winner: Red player", res);
    }

    @Test
    public void plus() {
        Cell cell = new Cell(1, 1);
        Cell other = new Cell(1, 1);
        assertEquals(new Cell(2, 2), cell.plus(other));
    }

    @Test
    public void plus2() {
        Cell cell = new Cell(2, 2);
        Cell dir = new Cell(1, 1);
        assertEquals(new Cell(4, 4), cell.plus2(dir));
    }
}