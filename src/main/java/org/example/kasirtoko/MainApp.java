package org.example.kasirtoko;

import javax.swing.*;
import java.awt.*;
import org.example.kasirtoko.view.*;
import org.example.kasirtoko.service.*;

public class MainApp extends JFrame {
//
    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    ProdukManager produkManager = new ProdukManager();
    TransaksiManager transaksiManager = new TransaksiManager();

    public MainApp() {
        setTitle("Aplikasi Kasir Toko");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.add(new DashboardView(cardLayout, container), "dashboard");
        container.add(new ProdukListView(cardLayout, container, produkManager), "produk");
        container.add(new ProdukFormView(cardLayout, container, produkManager), "produk_form");
        container.add(new KasirView(cardLayout, container, produkManager, transaksiManager), "kasir");
        container.add(new RiwayatTransaksiView(cardLayout, container, transaksiManager), "riwayat");

        add(container);
        cardLayout.show(container, "dashboard");
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainApp();
    }
}
