package org.stego.stego;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RSAnalysis {

    public static class RSData {
        private final SimpleStringProperty channel;
        private final SimpleStringProperty value;

        public RSData(String channel, String value) {
            this.channel = new SimpleStringProperty(channel);
            this.value = new SimpleStringProperty(value);
        }

        public String getChannel() {
            return channel.get();
        }

        public void setChannel(String channel) {
            this.channel.set(channel);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }
    }

    public static void rsMetod(BufferedImage img, ObservableList<RSData> rsDataList) throws Exception {
        double[] results = rsTest(img);
        rsDataList.add(new RSData("Красный канал", formatValue(results[0]*100)));
        rsDataList.add(new RSData("Зеленый канал", formatValue(results[1]*100)));
        rsDataList.add(new RSData("Синий канал", formatValue(results[2]*100)));
        rsDataList.add(new RSData("Среднее значение", String.valueOf(results[3]*100)));
    }

    private static String formatValue(double value) {
        return value < 4 ? "0.0" : String.valueOf(value);
    }

    public static double[] rsTest(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int bw = 2;
        int bh = 2;
        int[] mask = {1, 0, 0, 1};
        int[] invertMask = Arrays.stream(mask).map(x -> -x).toArray();

        int blocksInRow = width / bw;
        int blocksInCol = height / bh;

        int[][] r = new int[height][width];
        int[][] g = new int[height][width];
        int[][] b = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                r[y][x] = (rgb >> 16) & 0xFF;
                g[y][x] = (rgb >> 8) & 0xFF;
                b[y][x] = rgb & 0xFF;
            }
        }

        int[][] groupCounters = new int[3][12];

        for (int y = 0; y < blocksInCol; y++) {
            for (int x = 0; x < blocksInRow; x++) {
                int[] counterR = new int[bw * bh];
                int[] counterG = new int[bw * bh];
                int[] counterB = new int[bw * bh];

                int index = 0;
                for (int v = 0; v < bh; v++) {
                    for (int h = 0; h < bw; h++) {
                        counterR[index] = r[y * bh + v][x * bw + h];
                        counterG[index] = g[y * bh + v][x * bw + h];
                        counterB[index] = b[y * bh + v][x * bw + h];
                        index++;
                    }
                }

                groupCounters[0][getGroup(counterR, mask)]++;
                groupCounters[1][getGroup(counterG, mask)]++;
                groupCounters[2][getGroup(counterB, mask)]++;
                groupCounters[0][6 + getGroup(counterR, invertMask)]++;
                groupCounters[1][6 + getGroup(counterG, invertMask)]++;
                groupCounters[2][6 + getGroup(counterB, invertMask)]++;

                counterR = lsbFlip(counterR);
                counterG = lsbFlip(counterG);
                counterB = lsbFlip(counterB);

                groupCounters[0][3 + getGroup(counterR, mask)]++;
                groupCounters[1][3 + getGroup(counterG, mask)]++;
                groupCounters[2][3 + getGroup(counterB, mask)]++;
                groupCounters[0][9 + getGroup(counterR, invertMask)]++;
                groupCounters[1][9 + getGroup(counterG, invertMask)]++;
                groupCounters[2][9 + getGroup(counterB, invertMask)]++;
            }
        }

        double[] results = new double[4];
        double redResult = solve(groupCounters[0]);
        double greenResult = solve(groupCounters[1]);
        double blueResult = solve(groupCounters[2]);

        results[0] = redResult;
        results[1] = greenResult;
        results[2] = blueResult;
        results[3] = (redResult + greenResult + blueResult) / 3.0; // Усредненное значение

        return results;
    }

    public static int getGroup(int[] pix, int[] mask) {
        int[] flipPix = pix.clone();
        for (int i = 0; i < mask.length; i++) {
            if (mask[i] == 1) {
                flipPix[i] = flip(pix[i]);
            } else if (mask[i] == -1) {
                flipPix[i] = invertFlip(pix[i]);
            }
        }

        int d1 = smoothness(pix);
        int d2 = smoothness(flipPix);

        if (d1 > d2) {
            return 2; // 'S'
        } else if (d1 < d2) {
            return 0; // 'R'
        } else {
            return 1; // 'U'
        }
    }

    public static int flip(int val) {
        return (val & 1) == 1 ? val - 1 : val + 1;
    }

    public static int invertFlip(int val) {
        return (val & 1) == 1 ? val + 1 : val - 1;
    }

    public static int smoothness(int[] pix) {
        int s = 0;
        for (int i = 0; i < pix.length - 1; i++) {
            s += Math.abs(pix[i + 1] - pix[i]);
        }
        return s;
    }

    public static int[] lsbFlip(int[] pix) {
        return Arrays.stream(pix).map(x -> x ^ 1).toArray();
    }

    public static double solve(int[] groups) {
        int d0 = (groups[0] - groups[2]);
        int dm0 = (groups[6] - groups[8]);
        int d1 = (groups[3] - groups[5]);
        int dm1 = (groups[9] - groups[11]);

        double a = 2.0 * (d1 + d0);
        double b = (dm0 - dm1 - d1 - 3 * d0);
        double c = d0 - dm0;

        double D = b * b - 4 * a * c;

        if (D < 0) {
            return 0;
        }

        b *= -1;
        if (D == 0) {
            return (b / 2 / a) / (b / 2 / a - 0.5);
        }

        D = Math.sqrt(D);

        double x1 = (b + D) / 2 / a;
        double x2 = (b - D) / 2 / a;

        if (Math.abs(x1) < Math.abs(x2)) {
            return x1 / (x1 - 0.5);
        }

        return x2 / (x2 - 0.5);
    }
}
