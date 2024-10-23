package com.example.sudokuminiproject2.model.board;

import java.util.List;

public abstract class BoardAdapter implements IBoard{

    @Override
    public int getNumberByIndex(int column, int row) {
        return 0;
    }

    @Override
    public String showBoard() {
        return "";
    }

}
