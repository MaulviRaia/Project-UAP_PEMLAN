package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    // ===== COLOR =====
    public static final Color BG_MAIN = Color.WHITE;
    public static final Color PRIMARY = new Color(33, 150, 243);     // biru
    public static final Color DANGER = new Color(229, 57, 53);       // merah
    public static final Color SECONDARY = new Color(120, 144, 156);  // abu
    public static final Color TEXT_DARK = new Color(40, 40, 40);

    // ===== FONT =====
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // ===== BUTTON STYLE =====
    public static JButton primaryButton(String text) {
        return styledButton(text, PRIMARY);
    }

    public static JButton dangerButton(String text) {
        return styledButton(text, DANGER);
    }

    public static JButton secondaryButton(String text) {
        return styledButton(text, SECONDARY);
    }

    private static JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(NORMAL_FONT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
