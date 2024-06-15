package ru.netology.graphics.image;

public class ChangeColor implements TextColorSchema {

    char[] symbols = {'#', '$', '@', '%', '*', '+', '-', '\''};
//    String[] symbols = {"#", "$", "@", "%", "*", "+", "-", "'"};

    @Override
    public char convert(int color) {
        return symbols[(color / 32) % symbols.length];
    }
}
