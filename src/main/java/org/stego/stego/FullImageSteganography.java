package org.stego.stego;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;

public class FullImageSteganography {

    public static BufferedImage stegoPlusF(BufferedImage img, String msg, float procent1, String channel) {
        if (channel.equalsIgnoreCase("Красный")) {
            embedMessageInChannel(img, msg, procent1, 16);
        } else if (channel.equalsIgnoreCase("Зелёный")) {
            embedMessageInChannel(img, msg, procent1, 8);
        } else if (channel.equalsIgnoreCase("Синий")) {
            embedMessageInChannel(img, msg, procent1, 0);
        } else if (channel.equalsIgnoreCase("Все каналы")) {
            embedFullMessage(img, msg, procent1);
        } else {
            embedFullMessage(img, msg, procent1);
        }
        return img;
    }

    private static void embedFullMessage(BufferedImage img, String msg, float procent1) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int pixel = img.getRGB(x, y);
                for (int shift = 16; shift >= 0; shift -= 8) {
                    int channelValue = (pixel >> shift) & 0xFF;
                    int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                    channelValue = (channelValue & 0xFE) | lsb;
                    pixel = (pixel & ~(0xFF << shift)) | (channelValue << shift);
                    msgBitIndex++;
                    if (msgBitIndex == msgLength) msgBitIndex = 0; // Повторное встраивание сообщения
                }
                img.setRGB(x, y, pixel);
            }
        }
    }

    private static void embedMessageInChannel(BufferedImage img, String msg, float procent1, int shift) {
        byte[] msgBytes = msg.getBytes();
        int msgBitIndex = 0;
        int msgLength = msgBytes.length * 8;
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1);

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int pixel = img.getRGB(x, y);
                int channelValue = (pixel >> shift) & 0xFF;
                int lsb = (msgBitIndex < msgLength) ? (msgBytes[msgBitIndex / 8] >> (7 - (msgBitIndex % 8))) & 1 : 0;
                channelValue = (channelValue & 0xFE) | lsb;
                pixel = (pixel & ~(0xFF << shift)) | (channelValue << shift);
                img.setRGB(x, y, pixel);
                msgBitIndex++;
                if (msgBitIndex == msgLength) msgBitIndex = 0; // Повторное встраивание сообщения
            }
        }
    }

    public static String extractFM(BufferedImage img, float procent1, String channel) {
        if (channel.equalsIgnoreCase("Красный")) {
            return extractMessageFromChannel(img, procent1, 16);
        } else if (channel.equalsIgnoreCase("Зелёный")) {
            return extractMessageFromChannel(img, procent1, 8);
        } else if (channel.equalsIgnoreCase("Синий")) {
            return extractMessageFromChannel(img, procent1, 0);
        } else {
            return extractFullMessage(img, procent1);
        }
    }

    private static String extractFullMessage(BufferedImage img, float procent1) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1);
        byte[] messageBytes = new byte[procImageWidth * imageHeight * 3 / 8];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int pixel = img.getRGB(x, y);
                for (int shift = 16; shift >= 0; shift -= 8) {
                    int lsb = (pixel >> shift) & 1;
                    messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
                    messageIndex++;
                    if (messageIndex / 8 == messageBytes.length) {
                        return new String(messageBytes, StandardCharsets.UTF_8).trim();
                    }
                }
            }
        }
        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }

    private static String extractMessageFromChannel(BufferedImage img, float procent1, int shift) {
        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int procImageWidth = (int) (imageWidth * procent1);
        byte[] messageBytes = new byte[procImageWidth * imageHeight / 8];
        int messageIndex = 0;

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < procImageWidth; x++) {
                int pixel = img.getRGB(x, y);
                int lsb = (pixel >> shift) & 1;
                messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
                messageIndex++;
                if (messageIndex / 8 == messageBytes.length) {
                    return new String(messageBytes, StandardCharsets.UTF_8).trim();
                }
            }
        }
        return new String(messageBytes, StandardCharsets.UTF_8).trim();
    }
}
