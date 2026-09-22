package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class WelcomePanel extends JPanel {

    private static final String LOGO_PATH = "/images/logo-unifagoc.png";
    private static final int LOGO_SIZE = 168;

    public WelcomePanel() {
        super(new GridBagLayout());
        setBackground(Theme.YELLOW);

        JLabel title = new JLabel("CONTROLE DE CLIENTES", SwingConstants.CENTER);
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.DARK_GREEN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(createLogoLabel(), gbc);
    }

    private JLabel createLogoLabel() {
        URL url = getClass().getResource(LOGO_PATH);
        if (url == null) {
            JLabel fallback = new JLabel("Logo não encontrada: " + LOGO_PATH);
            fallback.setFont(Theme.LABEL_FONT);
            return fallback;
        }
        Image image = new ImageIcon(url).getImage()
                .getScaledInstance(LOGO_SIZE, LOGO_SIZE, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(image));
    }
}
