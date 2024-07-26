package org.stego.stego;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class FullImageSteganography {

    public static BufferedImage stegoPlusF(BufferedImage img, String msg, float procent1, String channel) {
        embedFullMessage(img, msg, procent1, channel);
        return img;
    }

    private static void embedFullMessage(BufferedImage img, String msg, float procent1, String channel) {
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

                // Встраивание в выбранные каналы
                if ("Красный".equals(channel) || "Все каналы".equals(channel)) {
                    red = modifyChannel(red, msgBytes, msgBitIndex, msgLength);
                    msgBitIndex++;
                }
                if ("Зелёный".equals(channel) || "Все каналы".equals(channel)) {
                    green = modifyChannel(green, msgBytes, msgBitIndex, msgLength);
                    msgBitIndex++;
                }
                if ("Синий".equals(channel) || "Все каналы".equals(channel)) {
                    blue = modifyChannel(blue, msgBytes, msgBitIndex, msgLength);
                    msgBitIndex++;
                }

                // Повторение сообщения, если оно короче изображения
                if (msgBitIndex >= msgLength) {
                    msgBitIndex = 0;
                }

                // Сохраняем изменённый пиксель
                rgb = (red << 16) | (green << 8) | blue;
                img.setRGB(x, y, rgb);
            }
        }
    }

    private static int modifyChannel(int channel, byte[] msgBytes, int msgBitIndex, int msgLength) {
        int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
        if (lsb == 0 && (channel % 2 != 0)) {
            channel = channel - 1;
        } else if (lsb == 1 && (channel % 2 == 0)) {
            channel = channel + 1;
        }
        return Math.min(255, Math.max(0, channel));
    }

    public static String extractFM(BufferedImage img, float procent1, String channel, int maxChars) {
        return extractFullMessage(img, procent1, channel, maxChars);
    }

    private static String extractFullMessage(BufferedImage img, float procent1, String channel, int maxChars) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1); // Процент изображения
        int maxMessageLength = Math.min((procImageWidth * imageHeight * 3 + 7) / 8, maxChars); // Ограничиваем длину сообщения
        byte[] messageBytes = new byte[maxMessageLength];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int rgb = img.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Извлечение LSB из выбранных каналов
                if ("Красный".equals(channel) || "Все каналы".equals(channel)) {
                    messageIndex = extractChannelLSB(red, messageBytes, messageIndex);
                }
                if ("Зелёный".equals(channel) || "Все каналы".equals(channel)) {
                    messageIndex = extractChannelLSB(green, messageBytes, messageIndex);
                }
                if ("Синий".equals(channel) || "Все каналы".equals(channel)) {
                    messageIndex = extractChannelLSB(blue, messageBytes, messageIndex);
                }

                if (messageIndex >= maxMessageLength * 8) {
                    return new String(messageBytes, StandardCharsets.UTF_8).trim();
                }
            }
        }
        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }

    private static int extractChannelLSB(int channel, byte[] messageBytes, int messageIndex) {
        if (messageIndex >= messageBytes.length * 8) {
            return messageIndex;
        }
        int lsb = channel & 1;
        messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
        return messageIndex + 1;
    }
}
