package com.javarush.task.task19.task1918;

/* 
Знакомство с тегами
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader fileReader = new BufferedReader(new FileReader(reader.readLine()));
             ){
            StringBuilder sb = new StringBuilder();
            while (fileReader.ready()) {
                sb.append(fileReader.readLine());
            }
            String tag = args[0];
            int tagLength = tag.length()+3;
            Pattern pBegin = Pattern.compile("<"+tag);
            Pattern pEnd = Pattern.compile("</"+tag+">");
            Matcher m = pBegin.matcher(sb);
            ArrayList<Integer> listBegin = new ArrayList<>();
            while(m.find()) {
                listBegin.add(m.start());//заполняем лист индексами вхождения открывающего тага
            }
            ArrayList<Integer> listEnd = new ArrayList<>();
            m = pEnd.matcher(sb);
            while (m.find()) {
                listEnd.add(m.start());//заполняем лист индексами вхождения закрывающего тага
            }
            HashMap<Integer, String> map = new HashMap<>();//карта : ключ - индекс входа открывающего тага(для вывода в консоль по порядку), строка - таг с его потрахами
            String s = sb.toString().replace("\\n|\\r", "");//на всякий убрал все лишние символы переноса
            while (listEnd.size() != 0) {
                int idxEnd = listEnd.get(0);//получаем индекс закрытия первого тага, ищем индекс его открытия, для того чтобы вырезать целиком
                String str = s.substring(0, idxEnd + tagLength); //не забываем учитывать длинну самого тага tagLength
                int idxBegin = 0;
                for (int i = 0; i < listBegin.size(); i++) {
                    if (listBegin.get(i) < idxEnd) {
                        idxBegin = listBegin.get(i); //нашли индекс открытия
                    }
                }
                map.put(s.indexOf(str.substring(idxBegin)), str.substring(idxBegin));
                //удаляем найденные вхождения индексов
                listEnd.remove(0);
                listBegin.remove((Integer)idxBegin);
            }
            ArrayList<Integer> pos = new  ArrayList<>(map.keySet());
            Collections.sort(pos); //сортируем полученные позиции вхождения открывающего тага в порядке возрастания для вывода в консоль
            for(Integer position : pos) {
                for (Map.Entry<Integer, String> e : map.entrySet()) {
                    if (e.getKey().intValue() == position.intValue()) {
                        System.out.println(e.getValue());
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
