package com.example.sudokuminiproject2.model.board;

public abstract class BoardAdapter implements IBoard{
    @Override
    public boolean isNumberByColumnAllowed(int numberSelected, int column, int row) {
        return true;
    }

    @Override
    public boolean isNumberByRowAllowed(int numberSelected, int column, int row) {
        return true;
    }

    @Override
    public boolean isNumberByBoxAllowed(int numberSelected, int column, int row) {
        return true;
    }

    @Override
    public void setNumberByIndex(int number, int column, int row){ }

    @Override
    public int getNumberByIndex(int column, int row){return -1;}

    @Override
    public String showBoard(){return "";}

}
