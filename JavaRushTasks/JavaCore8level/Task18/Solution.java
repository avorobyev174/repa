package com.javarush.task.task18.task1827;

/* 
Прайсы
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fn = reader.readLine();
        reader.close();
        if (!args[0].equals("-c"))
            return;
        BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(fn)));
        ArrayList<String> list = new ArrayList<>();
        while (is.ready()) {
            list.add(is.readLine());
        }
        is.close();
        int maxId = 0;
        for (String s : list) {
            int id = Integer.parseInt(s.substring(0, 8).replace(" ", ""));
            if (maxId < id)
                maxId = id;
        }
        int id = maxId + 1;
        String newId = spacePad(String.valueOf(id), 8);
        String product = spacePad(args[1], 30);
        String price = spacePad(args[2], 8);
        String quantity = spacePad(args[3], 4);
        String newLine = newId + product + price + quantity;
        list.add(newLine);
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fn, true)));
        os.write("\n" + newLine);
        os.close();
    }

     public static String spacePad(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        if (str.length() < len) {
            for (int i = str.length(); i < len; i++) {
                sb.append(" ");
            }
        } else {
            return str.substring(0, len);
        }
         return sb.toString();
     }
}
