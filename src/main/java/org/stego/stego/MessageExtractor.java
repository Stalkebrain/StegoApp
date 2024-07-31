//package org.stego.stego;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MessageExtractor {
//
//    public static class MessageData {
//        private final float percent;
//        private final String message;
//        private final String channel;
//
//        public MessageData(float percent, String message, String channel) {
//            this.percent = percent;
//            this.message = message;
//            this.channel = channel;
//        }
//
//        public float getPercent() {
//            return percent;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public String getChannel() {
//            return channel;
//        }
//    }
//
//    public static MessageData[] extractMessages(BufferedImage stegoImage) {
//        List<MessageData> messageDataList = new ArrayList<>();
//        String[] channels = {"Red", "Green", "Blue", "All"};
//
//        for (String channel : channels) {
//            for (float percent = 0; percent <= 1; percent += 0.001) {
//                String extractedMessage = extractMessage(stegoImage, percent, channel);
//                String previewMessage = extractedMessage.length() > 30 ? extractedMessage.substring(0, 30) : extractedMessage;
//                messageDataList.add(new MessageData(percent, previewMessage, channel));
//            }
//        }
//
//        return messageDataList.toArray(new MessageData[0]);
//    }
//
//    private static String extractMessage(BufferedImage img, float percent, String channel) {
//        int imageWidth = img.getWidth();
//        int imageHeight = img.getHeight();
//        int procImageWidth = (int) (imageWidth * percent);
//        byte[] messageBytes = new byte[procImageWidth * imageHeight];
//        int messageIndex = 0;
//
//        for (int y = 0; y < imageHeight; y++) {
//            for (int x = 0; x < procImageWidth; x++) {
//                int pixel = img.getRGB(x, y);
//
//                // Извлечение бита в зависимости от канала
//                int channelValue = 0;
//                switch (channel) {
//                    case "Red":
//                        channelValue = (pixel >> 16) & 0xFF; // Красный канал
//                        break;
//                    case "Green":
//                        channelValue = (pixel >> 8) & 0xFF;  // Зеленый канал
//                        break;
//                    case "Blue":
//                        channelValue = pixel & 0xFF;         // Синий канал
//                        break;
//                    case "All":
//                        channelValue = ((pixel >> 16) & 0xFF) | ((pixel >> 8) & 0xFF) | (pixel & 0xFF); // Все каналы
//                        break;
//                }
//
//                int lsb = channelValue & 1;
//                if (messageIndex / 8 < messageBytes.length) {
//                    messageBytes[messageIndex / 8] = (byte) ((messageBytes[messageIndex / 8] << 1) | lsb);
//                }
//                messageIndex++;
//                if (messageIndex / 8 >= messageBytes.length) {
//                    break;
//                }
//            }
//        }
//        return new String(messageBytes).trim();
//    }
//}
