package ru.netology.graphics;

import ru.netology.graphics.image.ConvertToSymbols;
import ru.netology.graphics.server.GServer;


public class Main {
    public static void main(String[] args) throws Exception {
        ConvertToSymbols converter = new ConvertToSymbols(); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

         //Или то же, но с выводом на экран:
        String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);
    }
}
