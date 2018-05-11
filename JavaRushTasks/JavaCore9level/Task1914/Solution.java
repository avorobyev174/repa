package com.javarush.task.task19.task1914;

/* 
Решаем пример
*/

//Задачу можно было решить чуть проще, методом split(" "), но я тренерую регулярные выражения

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static TestString testString = new TestString();

    public static void main(String[] args) {
        PrintStream c = System.out;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(os);
        System.setOut(s);
        testString.printSomething();
        String res = os.toString();
        System.setOut(c);
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(res);
        ArrayList<Integer> digits = new ArrayList<>();
        while (m.find()) {
            digits.add(Integer.parseInt(m.group())); //нашли все числа
        }
        String action = res.replaceAll("[^\\p{Punct}]", "").substring(0,1); //нашли знак */+/-
        int result = 0;
        switch (action) {
            case "+" : result = digits.get(0)+digits.get(1); break;
            case "-" : result = digits.get(0)-digits.get(1); break;
            case "*" : result = digits.get(0)*digits.get(1); break;
        }
        System.out.println(res.replaceAll("\\p{Cntrl}","") + result); //убрали переход на новую строку \n и вывели
    }

    public static class TestString {
        public void printSomething() {
            System.out.println("3 + 6 = ");
        }
    }
}

