package org.stego.stego;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class FullImageSteganography {

    public static BufferedImage stegoPlusF(BufferedImage img, String msg, float procent1) {
        embedFullMessage(img, msg, procent1);
        return img;
    }

    private static void embedFullMessage(BufferedImage img, String msg, float procent1) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procimageWidth = (int) (imageWidth * procent1);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procimageWidth; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                // Применяем правило стеганографии
                if (lsb == 0 && (pixel % 2 != 0)) {
                    // Если LSB сообщения равен 0 и оттенок нечётный, делаем его чётным
                    pixel = pixel - 1;
                } else if (lsb == 1 && (pixel % 2 == 0)) {
                    // Если LSB сообщения равен 1 и оттенок чётный, делаем его нечётным
                    pixel = pixel + 1;
                }
                // Устанавливаем новый пиксель
                int rgb = (pixel << 16) | (pixel << 8) | pixel;
                img.setRGB(x, y, rgb);
                msgBitIndex++;
                if (msgBitIndex == msgLength) msgBitIndex = 0; // Повторяем сообщение, если оно короче изображения
            }
        }
    }

    public static String extractFM(BufferedImage img, float procent1){
        return extractFullMessage(img, procent1);
    }

    private static String extractFullMessage(BufferedImage img, float procent) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procimageWidth = (int) (imageWidth * procent);
        byte[] messageBytes = new byte[procimageWidth * imageHeight];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procimageWidth; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = pixel & 1;
                messageBytes[messageIndex / 8] = (byte)((messageBytes[messageIndex / 8] << 1) | lsb);
                messageIndex++;
                if (messageIndex / 8 == messageBytes.length) {
                    break; // Прекращаем чтение, если достигли конца массива
                }
            }
        }
        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }
}
