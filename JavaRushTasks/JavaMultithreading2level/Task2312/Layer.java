package com.javarush.task.task23.task2312;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Layer extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        //Рисуем прямоугольник показывающий край поля справа
        g.fillRect(Room.game.getWidth() * 10, 0 , 10, (Room.game.getWidth() * 10) + 10);
        //Рисуем прямоугольник показывающий край поля снизу
        g.fillRect(0, Room.game.getHeight() * 10, (Room.game.getHeight() * 10) + 10, 10);
        //Рисуем прямоугольник показывающий мышь
        g.setColor(Color.GRAY);
        g.fillRect(Room.game.getMouse().getX() * 10, Room.game.getMouse().getY() * 10, 10, 10);
        ArrayList<SnakeSection> sections = Room.game.getSnake().getSections();
        g.setColor(Color.GREEN);
        for (SnakeSection section : sections) {
            g.fillRect(section.getX()*10, section.getY()*10, 10, 10);
        }
    }
}
