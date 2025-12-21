package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;
import org.example.kasirtoko.service.*;
import org.example.kasirtoko.util.FormatterUtil;

public class DashboardView extends JPanel {

    public DashboardView(CardLayout card, JPanel c,
                         ProdukManager pm, TransaksiManager tm) {

        setLayout(new BorderLayout(20,20));
        setBackground(UIStyle.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        // ================= TOP CARD =================
        JPanel top = new JPanel(new GridLayout(1,2,20,20));
        top.setBackground(UIStyle.BG_MAIN);

        top.add(createInfoCard(
                "Total Produk",
                String.valueOf(pm.getAll().size()),
                UIStyle.PRIMARY
        ));

        top.add(createInfoCard(
                "Total Penjualan",
                FormatterUtil.rupiah(tm.getTotalPendapatan()),
                new Color(0, 123, 255)
        ));

        // ================= GRAFIK =================
        JPanel chart = new ChartPanel(tm);
        chart.setPreferredSize(new Dimension(400,260));

        // ================= MENU =================
        JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        menu.setBackground(UIStyle.BG_MAIN);

        JButton produk = UIStyle.primaryButton("Manajemen Produk");
        JButton kasir = UIStyle.primaryButton("Kasir");
        JButton riwayat = UIStyle.primaryButton("Riwayat Transaksi");
        JButton keluar = UIStyle.dangerButton("Keluar");

        produk.addActionListener(e -> card.show(c,"produk"));
        kasir.addActionListener(e -> card.show(c,"kasir"));
        riwayat.addActionListener(e -> card.show(c,"riwayat"));
        keluar.addActionListener(e -> System.exit(0));

        menu.add(produk);
        menu.add(kasir);
        menu.add(riwayat);
        menu.add(keluar);

        // ================= ADD =================
        add(top,BorderLayout.NORTH);
        add(chart,BorderLayout.CENTER);
        add(menu,BorderLayout.SOUTH);
    }

    // ================= INFO CARD =================
    private JPanel createInfoCard(String title, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(8,8));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(16,20,16,20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIStyle.BODY_FONT);
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(UIStyle.TITLE_FONT);
        lblValue.setForeground(accent);

        card.add(lblTitle,BorderLayout.NORTH);
        card.add(lblValue,BorderLayout.CENTER);

        return card;
    }

    // ================= GRAFIK PANEL =================
    static class ChartPanel extends JPanel {
        private final TransaksiManager tm;

        ChartPanel(TransaksiManager tm) {
            this.tm = tm;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int width = getWidth();
            int height = getHeight();

            int[] data = tm.getPendapatanPerHari();
            if (data.length == 0) {
                g2.drawString("Belum ada data transaksi", 20, 30);
                return;
            }

            int max = 1;
            for (int d : data) max = Math.max(max, d);

            int barWidth = (width - 40) / data.length;
            int baseY = height - 40;

            g2.setColor(UIStyle.PRIMARY);

            for (int i = 0; i < data.length; i++) {
                int barHeight = (int)((double)data[i] / max * (height - 80));
                int x = 20 + i * barWidth;
                int y = baseY - barHeight;

                g2.fillRoundRect(x, y, barWidth - 10, barHeight, 8, 8);
            }
        }
    }
}