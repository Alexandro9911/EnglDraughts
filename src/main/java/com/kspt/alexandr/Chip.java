package com.kspt.alexandr;

public enum Chip {
    GREEN, RED, REDQUEEN, GREENQUEEN;

    public Chip turnQueen(Chip chip) {
        Chip answ =GREEN;
        if (chip == GREEN){
            answ =  GREENQUEEN;
        }
        if (chip == RED){
            answ = REDQUEEN;
        }
        return answ;
    }
}
