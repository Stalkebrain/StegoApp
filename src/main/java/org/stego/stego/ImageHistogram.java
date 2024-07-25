package org.stego.stego;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageHistogram {
    public static int[] hisogramm(BufferedImage image) {

        // Создание гистограммы
        int[] histogram = new int[256];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int brightness = new Color(image.getRGB(i, j)).getRed();
                histogram[brightness]++;
            }
        }
        return histogram;
    }
}