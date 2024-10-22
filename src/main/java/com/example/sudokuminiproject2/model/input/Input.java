package com.example.sudokuminiproject2.model.input;

public class Input {
    public void input() {

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
