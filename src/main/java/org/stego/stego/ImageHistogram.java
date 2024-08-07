package org.stego.stego;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHistogram {
    public enum ColorChannel {
        RED, GREEN, BLUE
    }

    public static int[] getHistogram(BufferedImage image, ColorChannel channel) {
        int[] histogram = new int[256];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color color = new Color(image.getRGB(i, j));
                int value;
                switch (channel) {
                    case RED:
                        value = color.getRed();
                        break;
                    case GREEN:
                        value = color.getGreen();
                        break;
                    case BLUE:
                        value = color.getBlue();
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported color channel");
                }
                histogram[value]++;
            }
        }
        return histogram;
    }
}
