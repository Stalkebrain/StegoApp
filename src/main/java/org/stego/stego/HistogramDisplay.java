//package org.stego.stego;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.Arrays;
//
//public class HistogramDisplay extends Canvas {
//    static int[] values;
//
//    @Override
//    public void paint(Graphics g) {
//        int width = (getWidth() - 40) / values.length; // Расстояние между столбцами
//        // Ширина столбца гистограммы
//        int columnWidth = 10;
//        int columnPadding = (width - columnWidth) / 2; // Отступы с каждой стороны столбца
//        boolean firstNonZeroFound = false; // Флаг для отслеживания первого ненулевого столбца
//
//        for (int i = 0; i < values.length; i++) {
//            if (values[i] == 0 && !firstNonZeroFound) {
//                continue; // Пропускаем нулевые столбцы до первого ненулевого
//            }
//            firstNonZeroFound = true; // Первый ненулевой столбец найден, флаг устанавливается в true
//
//            // Установите нужный масштаб
//            double scale = 0.03;
//            int value = (int)(values[i] * scale);
//            int columnX = i * width + columnPadding;
//            int newColumnWidth = columnWidth / 2; // Делим ширину столбца пополам
//            g.setColor(Color.BLACK);
//            g.fillRect(columnX, getHeight() - value - 30, newColumnWidth, value); // Рисуем столбец гистограммы черным цветом
//
//            g.setColor(new Color(i, i, i));
//            g.fillRect(columnX, getHeight() - 20, columnWidth, 20); // Рисуем прямоугольник с оттенком серого
//        }
//    }
//
//    public void displayHisto(int[] boba) {
//        this.values = boba;
//        System.out.println(Arrays.toString(values));
//
//        HistogramDisplay histogram = new HistogramDisplay();
//        JFrame frame = new JFrame("Гистограмма");
//
//        // Устанавливаем окно на весь экран
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//
//        frame.add(histogram);
//        frame.setVisible(true);
//
//        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
