package org.stego.stego;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualAttack {

    public static void main(String[] args) throws IOException {
        String stegoImagePath = "src/main/resources/рисунок/123-stego.bmp";
        String visualAttackImagePath = "src/main/resources/рисунок/123-stego-attack.bmp";

        BufferedImage stegoImage = ImageIO.read(new File(stegoImagePath));
        BufferedImage visualAttackImage = visualizeLSB(stegoImage);
        ImageIO.write(visualAttackImage, "bmp", new File(visualAttackImagePath));
        System.out.println("Визуальная атака выполнена и сохранена.");

    }

    public static BufferedImage visualizeLSB(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage lsbImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = img.getRGB(x, y) & 0xFF;
                int lsb = pixel & 1; // Извлекаем младший значащий бит
                lsb = lsb * 255; // Увеличиваем его значение для визуализации
                int rgb = (lsb << 16) | (lsb << 8) | lsb;
                lsbImage.setRGB(x, y, rgb);
            }
        }
        return lsbImage;
    }
}
