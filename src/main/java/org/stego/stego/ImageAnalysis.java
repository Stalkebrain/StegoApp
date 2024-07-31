package org.stego.stego;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Класс для анализа изображения
public class ImageAnalysis {
    public static class Data {
        private final int part;
        private final int uniqueShades;
        private final double chiSquaredRed;
        private final double chiSquaredGreen;
        private final double chiSquaredBlue;
        private final double pValueRed;
        private final double pValueGreen;
        private final double pValueBlue;

        public Data(int part, int uniqueShades, double chiSquaredRed, double chiSquaredGreen, double chiSquaredBlue, double pValueRed, double pValueGreen, double pValueBlue) {
            this.part = part;
            this.uniqueShades = uniqueShades;
            this.chiSquaredRed = chiSquaredRed;
            this.chiSquaredGreen = chiSquaredGreen;
            this.chiSquaredBlue = chiSquaredBlue;
            this.pValueRed = pValueRed;
            this.pValueGreen = pValueGreen;
            this.pValueBlue = pValueBlue;
        }

        public int getPart() {
            return part;
        }

        public int getUniqueShades() {
            return uniqueShades;
        }

        public double getChiSquaredRed() {
            return chiSquaredRed;
        }

        public double getChiSquaredGreen() {
            return chiSquaredGreen;
        }

        public double getChiSquaredBlue() {
            return chiSquaredBlue;
        }

        public double getPValueRed() {
            return pValueRed;
        }

        public double getPValueGreen() {
            return pValueGreen;
        }

        public double getPValueBlue() {
            return pValueBlue;
        }
    }

    public static Data[] AnalisisImage(BufferedImage img, int sliderValue, String direction) throws IOException {
        List<Data> dataList = new ArrayList<>();

        // Загрузка изображения
        BufferedImage image = img;

        // Получение размеров изображения
        int width = image.getWidth();
        int height = image.getHeight();

        // Размер одной части
        if (sliderValue == 0) {
            sliderValue = 1;
        }

        int partSize = width / sliderValue; // Всегда горизонтальное деление

        // Хи-квадрат для каждого канала
        double[] chiSquaredArrayRed = new double[sliderValue];
        double[] chiSquaredArrayGreen = new double[sliderValue];
        double[] chiSquaredArrayBlue = new double[sliderValue];
        int[] uniqueShadesArray = new int[sliderValue]; // Массив для хранения количества уникальных оттенков для каждой части

        for (int i = 0; i < sliderValue; i++) {
            // Создание карты частот для каждого канала
            HashMap<Integer, Integer> frequencyMapRed = new HashMap<>();
            HashMap<Integer, Integer> frequencyMapGreen = new HashMap<>();
            HashMap<Integer, Integer> frequencyMapBlue = new HashMap<>();

            // Подсчет частот оттенков в текущей части
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (x >= i * partSize && x < (i + 1) * partSize) {
                        int rgb = image.getRGB(x, y);
                        int red = (rgb >> 16) & 0xff;
                        int green = (rgb >> 8) & 0xff;
                        int blue = rgb & 0xff;

                        // Преобразование значений каждого канала в оттенки серого и подсчет частот
                        frequencyMapRed.put(red, frequencyMapRed.getOrDefault(red, 0) + 1);
                        frequencyMapGreen.put(green, frequencyMapGreen.getOrDefault(green, 0) + 1);
                        frequencyMapBlue.put(blue, frequencyMapBlue.getOrDefault(blue, 0) + 1);
                    }
                }
            }

            // Объединение всех частотных карт для получения общего количества уникальных оттенков
            HashMap<Integer, Integer> combinedFrequencyMap = new HashMap<>();
            combinedFrequencyMap.putAll(frequencyMapRed);
            combinedFrequencyMap.putAll(frequencyMapGreen);
            combinedFrequencyMap.putAll(frequencyMapBlue);

            int uniqueShades = combinedFrequencyMap.size();
            uniqueShadesArray[i] = uniqueShades; // Сохранение количества уникальных оттенков для текущей части

            // Применение формулы хи-квадрат для каждого канала
            double chiSquaredRed = computeChiSquared(frequencyMapRed);
            double chiSquaredGreen = computeChiSquared(frequencyMapGreen);
            double chiSquaredBlue = computeChiSquared(frequencyMapBlue);

            // Сохранение значений хи-квадрат для каждого канала
            chiSquaredArrayRed[i] = chiSquaredRed;
            chiSquaredArrayGreen[i] = chiSquaredGreen;
            chiSquaredArrayBlue[i] = chiSquaredBlue;
        }

        // Вывод результатов
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(128);

        // Вывод вероятностей для каждой части
        for (int i = 0; i < sliderValue; i++) {
            double chiSquaredRed = chiSquaredArrayRed[i];
            double chiSquaredGreen = chiSquaredArrayGreen[i];
            double chiSquaredBlue = chiSquaredArrayBlue[i];

            double pValueRed = 1 - chiSquaredDistribution.cumulativeProbability(chiSquaredRed);
            double pValueGreen = 1 - chiSquaredDistribution.cumulativeProbability(chiSquaredGreen);
            double pValueBlue = 1 - chiSquaredDistribution.cumulativeProbability(chiSquaredBlue);

            // Применение нормализации значений pValue
            pValueRed = normalizePValue(pValueRed);
            pValueGreen = normalizePValue(pValueGreen);
            pValueBlue = normalizePValue(pValueBlue);

            dataList.add(new Data(i + 1, uniqueShadesArray[i], chiSquaredRed, chiSquaredGreen, chiSquaredBlue, pValueRed, pValueGreen, pValueBlue));
        }

        return dataList.toArray(new Data[0]);
    }

    private static double normalizePValue(double pValue) {
        if (pValue < 0.2) {
            return 0;
        } else if (pValue > 0.999) {
            return 1;
        } else {
            return pValue;
        }
    }

    private static double computeChiSquared(HashMap<Integer, Integer> frequencyMap) {
        double chiSquared = 0;
        for (int j = 0; j < 128; j++) {
            double n2j = frequencyMap.getOrDefault(2 * j + 1, 0);
            double nj = frequencyMap.getOrDefault(2 * j, 0);
            double pj = (n2j + nj) / 2.0;
            if (pj != 0) { // Проверка, чтобы избежать деления на ноль
                chiSquared += Math.pow(n2j - pj, 2) / pj;
            }
        }
        return chiSquared;
    }
}
