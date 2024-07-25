package org.stego.stego;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class FullImageSteganography {

    public static BufferedImage stegoPlusF(BufferedImage img, String msg, float procent1) {
        embedFullMessage(img, msg, procent1);
        return img;
    }

    private static void embedFullMessage(BufferedImage img, String msg, float procent1) {
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int rgb = img.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Обработка каждого канала (красный, зелёный, синий)
                red = modifyChannel(red, msgBytes, msgBitIndex, msgLength);
                msgBitIndex++;
                if (msgBitIndex >= msgLength) msgBitIndex = 0; // Повторяем сообщение, если оно короче изображения

                green = modifyChannel(green, msgBytes, msgBitIndex, msgLength);
                msgBitIndex++;
                if (msgBitIndex >= msgLength) msgBitIndex = 0; // Повторяем сообщение, если оно короче изображения

                blue = modifyChannel(blue, msgBytes, msgBitIndex, msgLength);
                msgBitIndex++;
                if (msgBitIndex >= msgLength) msgBitIndex = 0; // Повторяем сообщение, если оно короче изображения

                // Сохраняем изменённый пиксель
                rgb = (red << 16) | (green << 8) | blue;
                img.setRGB(x, y, rgb);
            }
        }
    }

    private static int modifyChannel(int channel, byte[] msgBytes, int msgBitIndex, int msgLength) {
        int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
        if (lsb == 0 && (channel % 2 != 0)) {
            // Если LSB сообщения равен 0 и канал нечётный, делаем его чётным
            channel = channel - 1;
        } else if (lsb == 1 && (channel % 2 == 0)) {
            // Если LSB сообщения равен 1 и канал чётный, делаем его нечётным
            channel = channel + 1;
        }
        return Math.min(255, Math.max(0, channel)); // Убедитесь, что значение канала находится в допустимом диапазоне
    }

    public static String extractFM(BufferedImage img, float procent1) {
        return extractFullMessage(img, procent1);
    }

    private static String extractFullMessage(BufferedImage img, float procent) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent);
        int messageLength = procImageWidth * imageHeight * 3 / 8;
        byte[] messageBytes = new byte[messageLength];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int rgb = img.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Извлечение LSB из каждого канала
                messageIndex = extractChannelLSB(red, messageBytes, messageIndex);
                messageIndex = extractChannelLSB(green, messageBytes, messageIndex);
                messageIndex = extractChannelLSB(blue, messageBytes, messageIndex);

                if (messageIndex / 8 >= messageBytes.length) {
                    return new String(messageBytes, StandardCharsets.UTF_8).trim();
                }
            }
        }
        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }

    private static int extractChannelLSB(int channel, byte[] messageBytes, int messageIndex) {
        if (messageIndex / 8 >= messageBytes.length) {
            return messageIndex; // Возвращаем текущий индекс, если он выходит за пределы массива
        }
        int lsb = channel & 1;
        messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
        return messageIndex + 1;
    }
}
