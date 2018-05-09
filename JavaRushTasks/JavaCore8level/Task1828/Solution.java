package com.javarush.task.task18.task1828;

/* 
Прайсы 2
*/

import java.io.*;
import java.util.ArrayList;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fn = reader.readLine();
        reader.close();
        switch (args[0]) {
            case "-u" :
                update(fn, args);
                break;
            case "-d" :
                delete(fn, args);
                break;
        }      
    }

    public static void update (String fn, String[] args) throws Exception{
        ArrayList<String> list = readFileToList(fn);
        int id = Integer.parseInt(args[1]);
        int index = getRowIndex(list, id);
        if (index == -1)
            return;
        String newId = spacePad(String.valueOf(id), 8);
        String product = spacePad(args[2], 30);
        String price = spacePad(args[3], 8);
        String quantity = spacePad(args[4], 4);
        String s = newId + product + price + quantity;
        list.set(index, s);
        writeListToFile(list, fn);
    }

    public static void delete (String fn, String[] args) throws Exception{      
        ArrayList<String> list = readFileToList(fn);
        int id = Integer.parseInt(args[1]);
        int index = getRowIndex(list, id);
        if (index == -1)
            return;
        list.remove(index);
        writeListToFile(list, fn);
    }
    
    public static void writeListToFile(ArrayList<String> list, String fn) throws Exception {
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fn)));
        for (String str : list) {
            os.write(str + "\n");
        }
        os.close();
    }
    
    public static int getRowIndex(ArrayList<String> list, int id) {        
        for (int i = 0; i < list.size(); i++) {
            int currentId = Integer.parseInt(list.get(i).substring(0, 8).replace(" ", ""));
            if (id == currentId) {
                return i;
            }
        }
        return -1;
    }
    
    public static ArrayList<String> readFileToList(String fn) throws Exception{
        BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(fn)));
        ArrayList<String> list = new ArrayList<>();
        while (is.ready()) {
            list.add(is.readLine());
        }
        is.close();
        return list;
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
