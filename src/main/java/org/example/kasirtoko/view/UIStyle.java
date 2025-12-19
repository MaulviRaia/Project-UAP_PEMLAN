package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;

public class UIStyle {

    public static final Font TITLE_FONT  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public static final Color PRIMARY = new Color(33, 150, 243);
    public static final Color SUCCESS = new Color(76, 175, 80);
    public static final Color DANGER  = new Color(244, 67, 54);
    public static final Color BG      = new Color(245, 247, 250);

    // ===== METHOD YANG TADI HILANG =====
    public static void styleButton(JButton btn) {
        btn.setFont(NORMAL_FONT);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
