package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageUtils {
    public static Icon tintIcon(Icon icon, Color color) {
        if (icon == null) return null;
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        if (w <= 0 || h <= 0) return icon;
        
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        icon.paintIcon(null, g2, 0, 0);
        g2.dispose();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = img.getRGB(x, y);
                int alpha = (argb >> 24) & 0xff;
                if (alpha > 0) {
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int newArgb = (alpha << 24) | (r << 16) | (g << 8) | b;
                    img.setRGB(x, y, newArgb);
                }
            }
        }
        return new ImageIcon(img);
    }
}
