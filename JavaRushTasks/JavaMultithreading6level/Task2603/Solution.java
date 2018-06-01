package com.javarush.task.task26.task2603;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
Убежденному убеждать других не трудно
*/
public class Solution {

    public static void main(String[] args) {
        List<Soldier> soldiers = new ArrayList<>();
        soldiers.add(new Soldier("Ivanov", 170, 100));
        soldiers.add(new Soldier("Petrov", 180, 80));
        soldiers.add(new Soldier("Sidorov", 175, 65));
        soldiers.add(new Soldier("Vorobyev", 175, 60));
        Comparator<Soldier> compareByHeight = new Comparator<Soldier>() {
            public int compare(Soldier o1, Soldier o2) {
                return o1.height - o2.height;
            }
        };
        Comparator<Soldier> compareByWeight = new Comparator<Soldier>() {
            public int compare(Soldier o1, Soldier o2) {
                return o1.weight - o2.weight;
            }
        };
        Comparator<Soldier> compareByName = new Comparator<Soldier>() {
            public int compare(Soldier o1, Soldier o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        CustomizedComparator customizedComparator = new CustomizedComparator(new Comparator[]{compareByWeight});
        Collections.sort(soldiers, customizedComparator);
        for (Soldier soldier :  soldiers) {
            System.out.println(String.format("Name = %s, height = %d, weight = %d", soldier.name, soldier.height, soldier.weight));
        }
    }

    public static class CustomizedComparator<T> implements Comparator<T>{
        private Comparator<T>[] comparators;

        public CustomizedComparator(Comparator<T>... comparators) {
            this.comparators = comparators;
        }

        @Override
        public int compare(Object o1, Object o2) {
            int result = 0;
            for (Comparator comparator : comparators) {
                result = comparator.compare(o1, o2);
                if (result != 0) {
                    break;
                }
            }
            return result;
        }
    }

    public static class Soldier{
        private String name;
        private int height, weight;

        public Soldier(String name, int height, int weight) {
            this.name = name;
            this.height = height;
            this.weight = weight;
        }
    }
}
