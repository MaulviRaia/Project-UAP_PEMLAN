package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import org.example.kasirtoko.service.*;
import org.example.kasirtoko.util.FormatterUtil;

public class DashboardView extends JPanel {

    private final ProdukManager pm;
    private final TransaksiManager tm;

    private JLabel lblTotalProduk;
    private JLabel lblTotalPenjualan;
    private ChartPanel chart;

    public DashboardView(CardLayout card, JPanel c,
                         ProdukManager pm, TransaksiManager tm) {

        this.pm = pm;
        this.tm = tm;

        setLayout(new BorderLayout(20,20));
        setBackground(UIStyle.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        // ================= TOP CARD =================
        JPanel top = new JPanel(new GridLayout(1,2,20,20));
        top.setBackground(UIStyle.BG_MAIN);

        lblTotalProduk = new JLabel();
        lblTotalPenjualan = new JLabel();

        top.add(createInfoCard("Total Produk", lblTotalProduk, UIStyle.PRIMARY));
        top.add(createInfoCard("Total Penjualan", lblTotalPenjualan, new Color(0,123,255)));

        // ================= CHART =================
        chart = new ChartPanel(tm);
        chart.setPreferredSize(new Dimension(400,280));

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

        add(top,BorderLayout.NORTH);
        add(chart,BorderLayout.CENTER);
        add(menu,BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        lblTotalProduk.setText(String.valueOf(pm.getAll().size()));
        lblTotalPenjualan.setText(
                FormatterUtil.rupiah(tm.getTotalPendapatan())
        );
        chart.repaint();
    }

    private JPanel createInfoCard(String title, JLabel valueLabel, Color accent) {
        JPanel card = new JPanel(new BorderLayout(6,6));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(16,20,16,20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIStyle.BODY_FONT);
        lblTitle.setForeground(new Color(120,120,120));

        valueLabel.setFont(UIStyle.TITLE_FONT);
        valueLabel.setForeground(accent);

        card.add(lblTitle,BorderLayout.NORTH);
        card.add(valueLabel,BorderLayout.CENTER);
        return card;
    }

    // ================= CHART PANEL =================
    static class ChartPanel extends JPanel {
        private final TransaksiManager tm;
        private int[] data;
        private int hoverIndex = -1;

        ChartPanel(TransaksiManager tm) {
            this.tm = tm;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));

            addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    hoverIndex = getBarIndex(e.getX());
                    repaint();
                }
            });

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseExited(MouseEvent e) {
                    hoverIndex = -1;
                    repaint();
                }
            });
        }

        private int getBarIndex(int mouseX) {
            if (data == null || data.length == 0) return -1;
            int left = 60;
            int barW = (getWidth() - left - 30) / data.length;
            int idx = (mouseX - left) / barW;
            return (idx >= 0 && idx < data.length) ? idx : -1;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            data = tm.getPendapatanPerHari();
            if (data.length == 0) {
                g2.setFont(UIStyle.BODY_FONT);
                g2.setColor(Color.GRAY);
                g2.drawString("Belum ada data transaksi", 20, 30);
                return;
            }

            int w = getWidth();
            int h = getHeight();
            int baseY = h - 50;
            int leftX = 60;

            int max = 1;
            for (int d : data) max = Math.max(max, d);

            // ===== GRID + SCALE =====
            Font smallFont = UIStyle.BODY_FONT.deriveFont(Font.PLAIN, 11f);
            g2.setFont(smallFont);
            for (int i = 0; i <= 4; i++) {
                int y = baseY - (i * (baseY - 30) / 4);
                int value = max * i / 4;

                g2.setColor(new Color(230,230,230));
                g2.drawLine(leftX, y, w - 20, y);

                g2.setColor(new Color(130,130,130));
                g2.drawString(FormatterUtil.rupiah(value), 6, y + 4);
            }

            // ===== AXIS =====
            g2.setColor(new Color(180,180,180));
            g2.drawLine(leftX, 30, leftX, baseY);
            g2.drawLine(leftX, baseY, w - 20, baseY);

            int barW = (w - leftX - 30) / data.length;

            // ===== BAR =====
            for (int i = 0; i < data.length; i++) {
                int bh = (int)((double)data[i] / max * (baseY - 30));
                int x = leftX + i * barW + 8;
                int y = baseY - bh;

                g2.setColor(i == hoverIndex
                        ? UIStyle.PRIMARY.brighter()
                        : UIStyle.PRIMARY);

                g2.fillRoundRect(x, y, barW - 16, bh, 10, 10);

                // label hari
                g2.setColor(new Color(120,120,120));
                g2.drawString("H" + (i + 1),
                        x + (barW - 16) / 2 - 6,
                        baseY + 18);

                // tooltip
                if (i == hoverIndex) {
                    String tip = FormatterUtil.rupiah(data[i]);
                    int tw = g2.getFontMetrics().stringWidth(tip) + 10;

                    g2.setColor(new Color(40,40,40));
                    g2.fillRoundRect(x - 4, y - 28, tw, 20, 6,6);
                    g2.setColor(Color.WHITE);
                    g2.drawString(tip, x + 1, y - 14);
                }
            }

            // ===== LEGEND =====
            g2.setColor(UIStyle.PRIMARY);
            g2.fillRect(w - 170, 20, 14, 14);
            g2.setColor(new Color(90,90,90));
            g2.drawString("Pendapatan (Rp)", w - 145, 32);
        }
    }
}
