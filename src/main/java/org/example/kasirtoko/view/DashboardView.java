package org.example.kasirtoko.view;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JPanel {
    public DashboardView(CardLayout card, JPanel c) {
        setLayout(new GridLayout(2,2,20,20));
        setBorder(BorderFactory.createEmptyBorder(80,120,80,120));

        JButton produk = new JButton("Manajemen Produk");
        JButton kasir = new JButton("Kasir");
        JButton riwayat = new JButton("Riwayat Transaksi");
        JButton keluar = new JButton("Keluar");

        produk.addActionListener(e -> card.show(c,"produk"));
        kasir.addActionListener(e -> card.show(c,"kasir"));
        riwayat.addActionListener(e -> card.show(c,"riwayat"));
        keluar.addActionListener(e -> System.exit(0));

        add(produk);
        add(kasir);
        add(riwayat);
        add(keluar);
    }
}
