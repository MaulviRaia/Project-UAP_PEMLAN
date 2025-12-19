package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    // 🎨 WARNA
    public static final Color PRIMARY = new Color(52,152,219);   // biru utama
    public static final Color SECONDARY = new Color(189,195,199);
    public static final Color DANGER = new Color(231,76,60);

    public static final Color BG_MAIN = new Color(245,246,250);
    public static final Color TEXT_DARK = new Color(33,33,33);

    // 🔤 FONT
    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 22);

    public static final Font BODY_FONT =
            new Font("Segoe UI", Font.PLAIN, 14);

    // 🔘 BUTTON
    public static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(PRIMARY);
        b.setForeground(Color.WHITE);
        b.setFont(BODY_FONT);
        b.setFocusPainted(false);
        return b;
    }

    public static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(SECONDARY);
        b.setFont(BODY_FONT);
        b.setFocusPainted(false);
        return b;
    }

    public static JButton dangerButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(DANGER);
        b.setForeground(Color.WHITE);
        b.setFont(BODY_FONT);
        b.setFocusPainted(false);
        return b;
    }
}
