package com.javarush.task.task30.task3008;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static String readString() {
        String s = "";
        try {
            s = reader.readLine();
        } catch (IOException e) {
            System.out.println("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            s = readString();
        }
        return s;
    }
    public static int readInt() {
        int num = 0;
        try {
            num = Integer.parseInt(readString());
        } catch (NumberFormatException e) {
            System.out.println("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            num = readInt();
        }
        return num;
    }
}
