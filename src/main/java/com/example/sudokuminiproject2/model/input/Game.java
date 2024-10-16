package com.example.sudokuminiproject2.model.input;

public class Game {
    public void game() {

    }
    public boolean isValidLength(String entryNumber){
        return entryNumber.length()==1;
    }

    public boolean isValidNumber(String entryNumber){
        try {
            int number = Integer.parseInt(entryNumber);
            if (number >= 1 && number <= 6){
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }
}
