package ui;

import javax.swing.*;
import java.awt.*;

public final class Theme {

    public static final Color YELLOW = new Color(255, 255, 51);
    public static final Color DARK_GREEN = new Color(0, 100, 0);
    public static final Color RED = new Color(190, 30, 30);
    public static final Color BLUE = new Color(30, 90, 170);

    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    public static final Font SUBTITLE_FONT = new Font("Arial", Font.BOLD, 16);
    public static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 13);
    public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 12);

    private Theme() {
    }

    public static Icon dot(Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(x, y, 10, 10);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 10;
            }

            @Override
            public int getIconHeight() {
                return 10;
            }
        };
    }
}
